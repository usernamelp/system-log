package cn.lp.test;

import lombok.Data;

import java.io.Serializable;

@Data
public class IpLocation implements Serializable {

    private String ip;

    private String country;

    private String province;

    private String city;

    private String isp;
}
