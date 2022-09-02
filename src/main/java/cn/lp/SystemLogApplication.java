package cn.lp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "cn.lp.mapper")
public class SystemLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemLogApplication.class, args);
    }

}
