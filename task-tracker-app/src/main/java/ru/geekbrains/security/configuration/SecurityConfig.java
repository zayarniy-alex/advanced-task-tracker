package ru.geekbrains.security.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)

public class SecurityConfig
		extends WebSecurityConfigurerAdapter
{


  private UserDetailsService authService;


  @Autowired
  @Qualifier("authService")
  public void setAuthService(UserDetailsService service)
  {
	authService = service;
  }


  @Bean
  public PasswordEncoder passwordEncoder()
  {
	return new BCryptPasswordEncoder();
  }


  @Bean
  public DaoAuthenticationProvider authProvider()
  {
	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	provider.setUserDetailsService(authService);
	provider.setPasswordEncoder(passwordEncoder());

	return provider;
  }


  @Override
  protected void configure(AuthenticationManagerBuilder builder)
  {
	builder.authenticationProvider(authProvider());
  }


  @Override
  protected void configure(HttpSecurity http)
  throws Exception
  {
	http.authorizeRequests()
		.antMatchers("/").hasAnyRole("USER")
		.and()
		.formLogin()
		.usernameParameter("username")
		.passwordParameter("password")
		.loginProcessingUrl("/auth")
		.loginPage("/login")
		.failureUrl("/loginfail")
		.defaultSuccessUrl("/")
		.permitAll()
		.and()
		.logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/login")
		.permitAll();
  }

}