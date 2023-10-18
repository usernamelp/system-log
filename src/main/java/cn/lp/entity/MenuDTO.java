package cn.lp.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MenuDTO {

    private String id;

    private String parentId;

    private String name;

    private String url;

    private String type;

    private String icon;

    private String sort;

    private Date createTime;

    private Date updateTime;

    private List<MenuDTO> children;

    public static SysUserDTO convert(String name){
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setUsername(name);
        return sysUserDTO;
    }

}