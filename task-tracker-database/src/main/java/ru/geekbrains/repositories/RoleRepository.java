package ru.geekbrains.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.entities.Role;


public interface RoleRepository
		extends JpaRepository<Role, Long>
{

}