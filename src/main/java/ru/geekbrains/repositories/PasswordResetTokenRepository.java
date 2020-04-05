package ru.geekbrains.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.PasswordResetToken;


@Repository
public interface PasswordResetTokenRepository
		extends JpaRepository<PasswordResetToken, Long>
{

  PasswordResetToken findByToken(String token);

}