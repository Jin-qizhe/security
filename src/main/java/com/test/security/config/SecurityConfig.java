package com.test.security.config;

import com.test.security.filter.AuthenticationEntryPointFilter;
import com.test.security.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author jin
 * @Date 2022/12/7 14:26
 * @Description TODO
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //token认证过滤器
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    //认证失败处理类
    @Autowired
    private AuthenticationEntryPointFilter authenticationEntryPointFilter;

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //ajax请求拦截忽略
                .csrf().disable()
                //过滤请求
                .authorizeRequests()
                // 对于login接口允许匿名访问
                .antMatchers("/login", "/doLogin", "/test", "/index").permitAll()
                //对于所有静态资源允许匿名访问
                .antMatchers("/css/**", "/js/**", "/image/**", "/layuiadmin/**","/favicon.ico").permitAll()
                //swagger文档
                .antMatchers("/swagger-ui.html", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/doc.html").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        // 添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //认证失败处理类
        httpSecurity.exceptionHandling().authenticationEntryPoint(authenticationEntryPointFilter);
    }

    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 强散列哈希加密实现
     * 解决There is no PasswordEncoder mapped for the id "null" 问题
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
