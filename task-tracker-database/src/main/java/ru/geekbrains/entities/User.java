package ru.geekbrains.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "users")
@Data
public class User
{

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "firstname")
  private String firstname;

  @Column(name = "lastname")
  private String lastname;

  @Column(name = "email")
  private String email;

  @ManyToMany
  @JoinTable(name = "users_roles",
			 joinColumns = @JoinColumn(name = "user_id"),
			 inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> roles;


  public User()
  {
  }

}