package com.studyolle.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/","/login", "/sign-up","check-email-token","email-login"
                ,"check-email-login","check-email-login","login-link").permitAll()
                .mvcMatchers(HttpMethod.GET,"/profile/*").permitAll()
                .anyRequest().authenticated();
        
        //formLogin 기능 활성화 (사용가능)
        //http.formLogin()
        http.formLogin()
                .loginPage("/login")  //우리가 만든 로그인페이지 사용 가능 (내가 커스텀한 로그인 페이지)
                .permitAll();  // 접근권한ALL가능 (로그인ㅇ,로그인x 모두 가능)

        //기본적으로 logout기능은 켜져있음
        http.logout()
                .logoutSuccessUrl("/");  //로그아웃 성공했을 때 어디로 이동할지
    }

//    security에 이미지가 걸림 // static resources는 걸리지 않도
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/node_modules/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
