package cn.lp.entity;

import java.util.Comparator;

/**
 * @author lipeng
 * @since 1.0
 */
public class AgeComparator implements Comparator<SysUserDTO> {
    @Override
    public int compare(SysUserDTO o1, SysUserDTO o2) {
        return o2.getAge() - o1.getAge();
    }
}
