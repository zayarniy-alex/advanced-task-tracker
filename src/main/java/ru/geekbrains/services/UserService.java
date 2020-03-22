package ru.geekbrains.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.UserDTO;
import ru.geekbrains.entities.Role;
import ru.geekbrains.entities.User;
import ru.geekbrains.repositories.RoleRepository;
import ru.geekbrains.repositories.UserRepository;

import java.util.Optional;


@Service
public class UserService
{

  private UserRepository userRepo;
  private RoleRepository roleRepo;
  private PasswordEncoder pswEncoder;
  private Role userRole;
  private static final String ROLE_PREFIX = "ROLE_";
  private static final String USER_ROLE_TITLE = ROLE_PREFIX + "USER";


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


  public void registrateUser(UserDTO user)
  {
	if (!isExistUserRole())
	  throw new UserRoleNotFoundInDB();

	User newUser = new User();
	newUser.setUsername(user.username);
	String pswhash = pswEncoder.encode(user.password);
	newUser.setPassword(pswhash);
	newUser.setFirstname(user.firstname);
	newUser.setLastname(user.lastname);
	newUser.setEmail(user.email);
	newUser.setRole(userRole);

	userRepo.save(newUser);
  }


  public boolean isExistUser(UserDTO user)
  {
	Optional<User> userFromDB = userRepo.findByUsername(user.username);
	return userFromDB.isPresent();
  }


  private boolean isExistUserRole()
  {
	Optional<Role> role = roleRepo.findByTitle(USER_ROLE_TITLE);
	if (role.isPresent())
	{
	  userRole = role.get();
	  return true;
	}
	else
	{
	  return false;
	}
  }


  private static class UserRoleNotFoundInDB
		  extends RuntimeException
  {

	UserRoleNotFoundInDB()
	{
	  super("роль " + USER_ROLE_TITLE + " отсутствует в БД");
	}

  }

}