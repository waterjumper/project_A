package com.company.project.service.impl.cooke;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.common.utils.PasswordUtils;
import com.company.project.entity.SysDept;
import com.company.project.entity.SysUser;
import com.company.project.entity.cooke.CUser;
import com.company.project.mapper.cooke.CUserMapper;
import com.company.project.pojo.CUseChangePwdReq;
import com.company.project.pojo.CUserLoginReq;
import com.company.project.pojo.CUserQueryReq;
import com.company.project.service.HttpSessionService;
import com.company.project.service.cooke.CUserService;
import com.company.project.vo.resp.LoginRespVO;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * CUserServiceImpl
 *
 * @date：2024/4/4$ 11:51
 */
@Service
@Slf4j
public class CUserServiceImpl extends ServiceImpl<CUserMapper, CUser> implements CUserService {

    @Resource
    private CUserMapper cUserMapper;

    @Resource
    private HttpSessionService httpSessionService;

    @Override
    public boolean register(CUser vo) {
        CUser selectCUser = cUserMapper.selectOne(Wrappers.<CUser>lambdaQuery().eq(CUser::getPhone, vo.getPhone()));
        if (!Objects.isNull(selectCUser)) {
            throw new BusinessException("The phone number is registered！");
        }

        CUser newCUser = new CUser();
        newCUser.setPhone(vo.getPhone());
        newCUser.setUsername(vo.getUsername());
        newCUser.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(vo.getPassword(), newCUser.getSalt());
        newCUser.setPassword(encode);

        return cUserMapper.insert(newCUser) > 0;
    }

    @Override
    public LoginRespVO login(CUserLoginReq vo) {
        CUser selectCUser = cUserMapper.selectOne(Wrappers.<CUser>lambdaQuery().eq(CUser::getPhone, vo.getPhone()));

        if (Objects.isNull(selectCUser)) {
            throw new BusinessException(BaseResponseCode.NOT_ACCOUNT);
        }

        if (!PasswordUtils.matches(selectCUser.getSalt(), vo.getPassword(), selectCUser.getPassword())) {
            throw new BusinessException(BaseResponseCode.PASSWORD_ERROR);
        }

        LoginRespVO respVO = new LoginRespVO();
        respVO.setUsername(selectCUser.getUsername());
        respVO.setPhone(selectCUser.getPhone());

        String token = httpSessionService.createTokenAndUser(selectCUser, Lists.newArrayList("c_user"),
                Sets.newHashSet());
        respVO.setAccessToken(token);

        return respVO;
    }

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<CUser> userPageList(CUserQueryReq vo) {
        Page<CUser> page = new Page<>(vo.getPage(), vo.getLimit());

        LambdaQueryWrapper<CUser> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(vo.getUsername())) {
            queryWrapper.like(CUser::getUsername, vo.getUsername());
        }
        if (!StringUtils.isEmpty(vo.getPhone())) {
            queryWrapper.eq(CUser::getPhone, vo.getPhone());
        }

        queryWrapper.orderByDesc(CUser::getId);
        return cUserMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean logout() {
        httpSessionService.getCurrentUserIdAndCheck();

        httpSessionService.abortUserByToken();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return true;
    }

    @Override
    public boolean updatePwd(CUseChangePwdReq vo) {
        if (!vo.getNewPassword().equals(vo.getConfirmNewPassword())) {
            throw new BusinessException("The two input passwords are inconsistent");
        }

        Long currentUserId = httpSessionService.getCurrentUserIdAndCheck();
        CUser selectCUser = cUserMapper.selectOne(Wrappers.<CUser>lambdaQuery().eq(CUser::getId, currentUserId));
        if (Objects.isNull(selectCUser)) {
            throw new BusinessException(BaseResponseCode.ILLEGAL_REQUEST);
        }

        String salt = selectCUser.getSalt();
        String encodePassword = PasswordUtils.encode(vo.getPassword(), salt);
        if (!encodePassword.equals(selectCUser.getPassword())) {
            throw new BusinessException("Old password error");
        }

        String encodeNewPassword = PasswordUtils.encode(vo.getNewPassword(), salt);
        if (encodeNewPassword.equals(selectCUser.getPassword())) {
            throw new BusinessException("The new password cannot be the same as the old password");
        }

        CUser updateCUser = new CUser();
        updateCUser.setId(selectCUser.getId());
        updateCUser.setPassword(encodeNewPassword);

        return cUserMapper.updateById(updateCUser) > 0;
    }
}
