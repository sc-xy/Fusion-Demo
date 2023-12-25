package top.scxy.fusion.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {
    // 异步线程池配置
    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 核心线程数
        executor.setMaxPoolSize(20); // 最大线程数
        executor.setQueueCapacity(200); // 等待队列容量
        executor.setKeepAliveSeconds(60); // 空闲线程存活时间
        executor.setThreadNamePrefix("asyncExecutor-"); // 线程名前缀
        return executor;
    }
}
