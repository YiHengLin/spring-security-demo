package com.ov.security.config;

import com.ov.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ov.security.JwtUserDetailsService;
import com.ov.security.filter.AuthenticateJwtTokenFilter;
import com.ov.security.util.JwtTokenUtil;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private AuthenticationEntryPoint exceptionHandler;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	
	@Value("${jwt.route.authentication}")
	private String authRoute;

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(){
    	return new CustomAuthenticationProvider();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
	  }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	// Custom authentication
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.authenticationProvider(customAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		    // When Spring Security exceptions caught by ExceptionTranslationFilter, 
			// AuthenticationEntryPoint will be launched
			.exceptionHandling().authenticationEntryPoint(exceptionHandler)
			.and()
			// Since we use JWTs, it should not create session
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()	
			.antMatchers("/h2-console/**/**").permitAll()
			.antMatchers("/auth/**").permitAll()
			.anyRequest().authenticated();
		
		
		// Custom filter verifying JWTs
		AuthenticateJwtTokenFilter authenticateJwtTokenFilter= new AuthenticateJwtTokenFilter(jwtUserDetailsService, jwtTokenUtil);
		http.addFilterBefore(authenticateJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) {
		web
			.ignoring()
			.antMatchers(HttpMethod.POST, authRoute)
			.and()
			.ignoring()
			.antMatchers("/h2-console/**/**");
	}

}
