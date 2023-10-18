package cn.lp.entity;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author lipeng
 * @since 1.0
 */
@Data
public class SysUserDTO {

    private Integer id;

    private String username;

    private String password;

    private Integer status;

    private Integer age;
}
