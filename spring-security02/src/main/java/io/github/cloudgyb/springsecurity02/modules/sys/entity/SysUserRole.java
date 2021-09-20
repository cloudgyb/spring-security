package io.github.cloudgyb.springsecurity02.modules.sys.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysUserRole {

    private Integer id;
    private Integer userId;
    private Integer roleId;

}
