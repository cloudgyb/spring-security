package io.github.cloudgyb.springsecurity02.modules.sys.entity;


import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysUser implements Serializable {

  private Integer id;
  private String username;
  private String password;
  private String phone;
  private String email;
  private Byte status;
  private Date createTime;
  private Date updateTime;

}
