package com.ov.security.config;

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
	private AuthenticationEntryPoint exceptionHannder;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Value("${jwt.route.authentication}")
	private String authRoute;
	
	
	//  Customization of the authentication
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(jwtUserDetailsService) // Add authentication based upon the customized UserDetailsService.
            .passwordEncoder(passwordEncoder());
    }
	
	@Bean
	  public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }
	
    /* 
     * Since we expose customized UserDetailsService bean, AuthenticationManagerConfiguration will not take effect.
     * Need to override the authenticationManagerBean to expose the AuthenticationManager as a bean
     * https://github.com/spring-projects/spring-boot/issues/11136#issuecomment-347373711
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		
		http
		    // When Spring Security exceptions caught by ExceptionTranslationFilter, 
			// AuthenticationEntryPoint will be launched
			.exceptionHandling().authenticationEntryPoint(exceptionHannder)
			.and()
			// Since we use JWT token, it should not create session
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()	
			.antMatchers("/h2-console/**/**").permitAll()
			.antMatchers("/auth/**").permitAll()
			.anyRequest().authenticated();
		
		
		// Custom filter verifying of JWT token
		AuthenticateJwtTokenFilter authenticateJwtTokenFilter= new AuthenticateJwtTokenFilter(jwtUserDetailsService, jwtTokenUtil);
		
		http.addFilterBefore(authenticateJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web
			.ignoring()
	        .antMatchers(HttpMethod.POST, authRoute)
	        .and()
			.ignoring()
			.antMatchers("/h2-console/**/**");
	}
	
	
	
	
	
	
	
	

}
