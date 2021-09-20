package io.github.cloudgyb.springsecurity02.modules.sys.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cloudgyb.springsecurity02.modules.sys.dao.SysUserRoleDao;
import io.github.cloudgyb.springsecurity02.modules.sys.dto.SysUserRoleDTO;
import io.github.cloudgyb.springsecurity02.modules.sys.entity.SysUserRole;

import org.springframework.stereotype.Service;

/**
 * @author cloudgyb
 * 2021/9/5 13:01
 */
@Service
public class SysUserRoleService extends ServiceImpl<SysUserRoleDao, SysUserRole> {

    public List<String> getUserRolesStrByUserId(Integer userId){
        final List<SysUserRoleDTO> sysUserRoleDTOS = getUserRolesByUserId(userId);
        if(sysUserRoleDTOS == null)
            return Collections.emptyList();
        return sysUserRoleDTOS
                .stream()
                .map(SysUserRoleDTO::getRoleName)
                .collect(Collectors.toList());
    }

    public List<SysUserRoleDTO> getUserRolesByUserId(Integer userId){
        return baseMapper.selectByUserId(userId);
    }
}
