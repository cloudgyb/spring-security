package io.github.cloudgyb.springsecurity02.modules.sys.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cloudgyb.springsecurity02.modules.sys.dto.SysUserRoleDTO;
import io.github.cloudgyb.springsecurity02.modules.sys.entity.SysUser;
import io.github.cloudgyb.springsecurity02.modules.sys.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import org.springframework.stereotype.Repository;

/**
 * @author cloudgyb
 * 2021/9/4 21:50
 */
@Repository
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {
    @Select("   select ur.id as id,ur.user_id as userId,ur.role_id as roleId, r.name as roleName from sys_user_role as ur "
            + "     left join sys_role as r on ur.role_id=r.id "
            + " where ur.user_id=#{userId}")
    List<SysUserRoleDTO> selectByUserId(Integer userId);
}
