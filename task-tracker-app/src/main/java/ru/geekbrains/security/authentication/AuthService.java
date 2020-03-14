package ru.geekbrains.security.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.entities.Role;
import ru.geekbrains.entities.User;
import ru.geekbrains.repositories.RoleRepository;
import ru.geekbrains.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Service("authService")
public class AuthService
		implements UserDetailsService
{

  private UserRepository userRepo;
  private RoleRepository roleRepo;
  private PasswordEncoder pswEncoder;


  @Autowired
  public void setUserRepository(UserRepository repo)
  {
	userRepo = repo;
  }


  @Autowired
  public void setRoleRepository(RoleRepository repo)
  {
	roleRepo = repo;
  }


  @Autowired
  public void setPasswordEncoder(PasswordEncoder enc)
  {
	pswEncoder = enc;
  }


  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username)
  throws UsernameNotFoundException
  {
	Optional<User> user = userRepo.findByUsername(username);

	if (!user.isPresent())
	  throw new UsernameNotFoundException("Invalid username or password");

	User usr = user.get();
	String un = usr.getUsername();
	String pw = usr.getPassword();
	List<Role> roles = usr.getRoles();
	List<GrantedAuthority> auths = mapRolesToAuthorities(roles);

	return new org.springframework.security.core.userdetails.User(un, pw, auths);
  }


  private List<GrantedAuthority> mapRolesToAuthorities(List<Role> roles)
  {
	return roles.stream()
				.map(x -> new SimpleGrantedAuthority(x.getName()))
				.collect(toList());
  }

}