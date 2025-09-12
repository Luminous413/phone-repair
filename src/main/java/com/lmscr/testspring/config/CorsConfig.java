package com.lmscr.testspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    /**
     * 跨域配置
     *
     * @return 跨域过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        // 1. 配置跨域规则
        CorsConfiguration config = new CorsConfiguration();
        // 允许携带Cookie
        config.setAllowCredentials(true);
        // 允许前端域名（按需修改）
        config.addAllowedOriginPattern("http://127.0.0.1:8080");
        // 允许所有请求头
        config.addAllowedHeader("*");
        // 允许所有请求方法（GET/POST/PUT等）
        config.addAllowedMethod("*");

        // 2. 应用跨域规则到所有接口
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 匹配所有接口路径
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
