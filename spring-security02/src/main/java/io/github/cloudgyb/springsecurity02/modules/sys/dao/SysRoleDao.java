package io.github.cloudgyb.springsecurity02.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cloudgyb.springsecurity02.modules.sys.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import org.springframework.stereotype.Repository;

/**
 * @author cloudgyb
 * 2021/9/4 21:50
 */
@Repository
@Mapper
public interface SysRoleDao extends BaseMapper<SysRole> {
}
