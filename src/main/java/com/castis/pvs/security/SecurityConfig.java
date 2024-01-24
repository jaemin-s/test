package com.castis.pvs.security;

import com.castis.pvs.config.MvcConfig;
import com.castis.pvs.security.service.MobileUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ MvcConfig.class })
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	MobileUserDetailsServiceImpl userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/api/**").permitAll() // 인증이 필요 없는 API 경로 설정
			.antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**","/api-docs/**")
			.permitAll()
			.anyRequest().authenticated()
			.and()
			.httpBasic()
			.and()
			.csrf().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoding());
	}
	
	@Bean
	public PasswordEncoding getPasswordEncoder() {
		return new PasswordEncoding();
	}


}

