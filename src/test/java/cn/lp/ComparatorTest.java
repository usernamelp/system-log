package cn.lp;

import cn.hutool.core.date.DateUtil;
import cn.lp.entity.AgeComparator;
import cn.lp.entity.SysUserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lipeng
 * @since 1.0
 */
public class ComparatorTest {
    public static void main(String[] args) {
        ArrayList<SysUserDTO> userDTOS = new ArrayList<>();
        SysUserDTO userDTO = new SysUserDTO();
        userDTO.setAge(10);
        SysUserDTO dto = new SysUserDTO();
        dto.setAge(12);
        userDTOS.add(userDTO);
        userDTOS.add(dto);
        userDTOS.sort(new AgeComparator());
        System.out.println(userDTOS);


        ArrayList<String> list = new ArrayList<>();
        list.add("2023-10-18 11:26:11");
        list.add("2023-10-18 11:49:13");
        list.add("2023-10-17 11:49:13");
        list.add("2023-10-19 11:49:13");
        System.out.println("排序前："+list);

        List<String> collect = list.stream().sorted((a, b) -> {
            if(DateUtil.parse(a).getTime() - DateUtil.parse(b).getTime()>0){
                return -1;
            }
            return 0;
        }).collect(Collectors.toList());
        System.out.println("排序后："+collect);
    }
}
