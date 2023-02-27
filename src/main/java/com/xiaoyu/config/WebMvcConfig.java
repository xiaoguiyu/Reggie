package com.xiaoyu.config;

import com.xiaoyu.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/9/25 17:10
 * mvc的配置
 */

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 设置静态资源映射
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        log.info("开始进行静态资源映射");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
    }

    /**
     * 扩展mvc的消息转换器
     * @param converters 消息转换器集合
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        log.info("消息转换器启用...");
        // 创建消息转换器
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        // 设置消息转换器, 使用jackson将java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        // 将设置后的消息转换器添加mvc的转换器集合中
        converters.add(0, messageConverter);

    }

    /**
     * 添加登录拦截器
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        // 将自定义的 登录拦截器添加的mvc框架中
        InterceptorRegistration interceptor = registry.addInterceptor(new LoginHandlerInterceptor());
        // 将所有的请求都拦截
        interceptor.addPathPatterns("/**");
        // 将登录页面和请求 和静态资源放行
        interceptor.excludePathPatterns("/", "/employee/login");
        interceptor.excludePathPatterns("/employee/logout");
        interceptor.excludePathPatterns("/backend/**");
        interceptor.excludePathPatterns("/front/**");
        // 让用户页面的短线验证和登录请求放行
        interceptor.excludePathPatterns("/user/login");
        interceptor.excludePathPatterns("/user/sendMsg");
    }



}
