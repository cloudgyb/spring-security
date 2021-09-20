package io.github.cloudgyb.springsecurity02.modules.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author cloudgyb
 * 2021/9/5 12:59
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysUserRoleDTO {
    private Integer id;
    private Integer userId;
    private Integer roleId;
    private String roleName;
}
