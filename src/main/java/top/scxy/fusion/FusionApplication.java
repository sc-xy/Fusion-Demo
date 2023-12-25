package top.scxy.fusion;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("top.scxy.fusion.mapper")
@EnableAsync
@EnableCaching
public class FusionApplication {
	public static void main(String[] args) {
		SpringApplication.run(FusionApplication.class, args);
	}
}
