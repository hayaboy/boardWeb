package com.BoardWeb.board.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Autowired
	private SecurityUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {
		
		security.userDetailsService(userDetailsService);
		
		security.authorizeRequests().antMatchers("/", "/system/**").permitAll(); // /와 /system으로 시작하는 경로에는 인증되지 않은 모든 사용자가 접근
		security.authorizeRequests().antMatchers("/board/**").authenticated(); // board로 시작하는 경로에는 인증된 사용자만 접근
		security.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN"); // admin으로 시작하는 경로에는 ADMIN 권한만
		
		security.csrf().disable();
		security.formLogin().loginPage("/system/login").defaultSuccessUrl("/board/getBoardList", true);
		security.exceptionHandling().accessDeniedPage("/system/accessDenied");
		security.logout().logoutUrl("/system/logout").invalidateHttpSession(true).logoutSuccessUrl("/");
		
		
	}
	
	//스프링 시큐리티에서 비밀번호 암호화
		@Bean  
		public PasswordEncoder passwordEncoder() {
			return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		}
	
}
