package com.echo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.echo.dao.CUserDao;
import com.echo.entity.CUser;
import com.echo.service.CUserService;
import org.springframework.stereotype.Service;

/**
 * c端用户(CUser)表服务实现类
 *
 * @author makejava
 * @since 2024-04-22 13:08:50
 */
@Service("cUserService")
public class CUserServiceImpl extends ServiceImpl<CUserDao, CUser> implements CUserService {

}

