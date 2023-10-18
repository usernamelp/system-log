package cn.lp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Comparator;

/**
 * @author lipeng
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserDTO {

    private Integer id;

    private String username;

    private String password;

    private Integer status;

    private Integer age;

}
