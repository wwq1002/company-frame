package com.yingxue.lesson;

import com.yingxue.lesson.config.CrossOriginfilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.yingxue.lesson.mapper")
public class CompanyFrameApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyFrameApplication.class, args);
    }
    @Bean
    public CrossOriginfilter crossOriginFilter(){
            return new CrossOriginfilter();
    }
    @Bean
    public FilterRegistrationBean getFilterRegistrationBean(){
            FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
            /**
                 * 设置过滤器
                 */
            filterRegistrationBean.setFilter(crossOriginFilter());
            /**
                 * 拦截路径
                 */
            filterRegistrationBean.addUrlPatterns("/api/*");
            /**
                 * 设置名称
                 */
            filterRegistrationBean.setName("CrossOriginFilter");
            return filterRegistrationBean;
    }

}
