package com.seller.util.config;/**
 * created by sheting on 2018/12/10
 */
import com.seller.util.constant.CookieConstant;
import com.seller.util.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @program: sell
 * @description:
 * @author: ljm
 * @create: 2018-12-10 10:58
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 登录session key
     */
    public final static String SESSION_KEY = "user";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
   }


    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry
                .addInterceptor(getSecurityInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/**",
                        "/js/**",
                        "/fonts/**",
                        "/img/**",
                        "/font-awesome-4.7.0/**",
                        "/icons-reference/**",
                        "/error",
                        "/index",
                        "/seller/login",
                        "/seller/index",
                        "/"
                );


//        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
//
//        // 排除配置
//        addInterceptor.excludePathPatterns("/error");
//        addInterceptor.excludePathPatterns("/");
//        addInterceptor.excludePathPatterns("/img/**");
//        addInterceptor.excludePathPatterns("/index");
//
////         拦截配置
//        addInterceptor.addPathPatterns("/**");

    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            Cookie[] cookies = request.getCookies();
            Cookie cookie = cookies[0];
            for (int i = 1; i < cookies.length; i++) {
                if(cookies[i].getName().equals(CookieConstant.TOKEN)){
                    cookie = cookies[i];
                    break;
                }
            }
            String Token = String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue());
            if(!redisTemplate.opsForValue().get(Token).equals(null)){
                return true;
            }
            String url = "/index";
            response.sendRedirect(url);
            return false;
        }
    }
}
