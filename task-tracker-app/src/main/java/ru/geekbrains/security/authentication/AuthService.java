package ru.geekbrains.security.authentication;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("authService")
public class AuthService
		implements UserDetailsService
{

  @Override
  public UserDetails loadUserByUsername(String s)
  throws UsernameNotFoundException
  {
	return null;
  }

}