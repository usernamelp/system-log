package cn.lp.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author lipeng
 * @since 1.0
 */
@Data
@Table(name = "sys_log")
public class SysLogEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "operation")
    private String operation;

    @Column(name = "method")
    private String method;

    @Column(name = "uri")
    private String uri;

    @Column(name = "ip")
    private String ip;

    @Column(name = "params")
    private String params;

    @Column(name = "operator_name")
    private String operatorName;

    @Column(name = "use_time")
    private Double useTime;


}
