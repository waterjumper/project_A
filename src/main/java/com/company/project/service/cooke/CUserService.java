package com.company.project.service.cooke;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.SysUser;
import com.company.project.entity.cooke.CUser;
import com.company.project.pojo.CUseChangePwdReq;
import com.company.project.pojo.CUserLoginReq;
import com.company.project.pojo.CUserQueryReq;
import com.company.project.vo.resp.LoginRespVO;


/**
 * c端用户Service
 */
public interface CUserService extends IService<CUser> {


    /**
     * 注册
     *
     * @param vo
     */
    boolean register(CUser vo);


    /**
     * 登陆
     *
     * @param req
     * @return LoginRespVO
     */
    LoginRespVO login(CUserLoginReq req);



    IPage<CUser> userPageList(CUserQueryReq vo);

    boolean logout();

    boolean updatePwd(CUseChangePwdReq vo);
}
