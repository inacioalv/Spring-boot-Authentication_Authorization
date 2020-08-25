package br.com.inacio.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class security extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private UserDetailsService userSecurityService;
	
	@Autowired
	private JWTUtil jwtutil;
	
	//EndPoints que serão liberados
	public static final String[] PUBLIC_ENDPOINTS={
			"/Usuario/**"
			
		
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		Liberar H2
//		http.headers().frameOptions().disable();
		
		//csrf: não deixar Tokens na cache e session
		http.cors().and().csrf().disable();
		
		http.authorizeRequests()
		.antMatchers(PUBLIC_ENDPOINTS).permitAll()
		.anyRequest().authenticated();
		http.addFilter(new JWtAuthenticationFilter(authenticationManager(), jwtutil) );
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtutil,userSecurityService) );
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	
	@Bean
	CorsConfigurationSource configurationSource() {
		final UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth ) throws Exception {
		auth.userDetailsService(userSecurityService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
