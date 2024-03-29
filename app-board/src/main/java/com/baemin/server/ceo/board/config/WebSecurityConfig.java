package com.baemin.server.ceo.board.config;

import com.baemin.server.ceo.board.security.JwtAuthenticationFilter;
import com.baemin.server.ceo.board.security.JwtTokenProvider;
import com.baemin.server.ceo.core.code.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http
                .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
                .csrf().disable() // csrf 보안 토큰 disable처리.
                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
                .and()
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .antMatchers( "/post" ).authenticated() // 로그인을 해야 게시글을 작성
                .antMatchers( "/admin/**" ).hasRole( Role.ADMIN.name() )
                .antMatchers( "/**" ).permitAll()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore( new JwtAuthenticationFilter( jwtTokenProvider ),
                        UsernamePasswordAuthenticationFilter.class )
                .logout()
                .logoutUrl( "/logout" )
                .logoutSuccessUrl( "/login" );
        ;
        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
    }

}
