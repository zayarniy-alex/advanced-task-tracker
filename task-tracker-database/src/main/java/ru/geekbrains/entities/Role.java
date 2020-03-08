package ru.geekbrains.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "roles")
@Data
public class Role
{

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @ManyToMany
  @JoinTable(name = "users_roles",
			 joinColumns = @JoinColumn(name = "role_id"),
			 inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private List<User> users;


  public Role()
  {
  }

}