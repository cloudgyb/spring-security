package io.github.cloudgyb.springsecurity02.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cloudgyb.springsecurity02.modules.sys.dao.SysUserDao;
import io.github.cloudgyb.springsecurity02.modules.sys.entity.SysUser;

import org.springframework.stereotype.Service;

/**
 * @author cloudgyb
 * 2021/9/5 12:24
 */
@Service
public class SysUserService extends ServiceImpl<SysUserDao, SysUser> {

    public SysUser getUserByUserName(String userName) {
        final QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().eq("username",userName);
        return getOne(queryWrapper);
    }
}
