package io.github.cloudgyb.springsecurity02.modules.sys.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysRole {

    private Integer id;
    private String name;
    private String remark;
    private Date createTime;
    private Date updateTime;

}
