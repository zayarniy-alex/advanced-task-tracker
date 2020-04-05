package ru.geekbrains.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Data
@NoArgsConstructor
@Table(name = "password_reset_tokens")
public class PasswordResetToken
{

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "token")
  private String token;

  @Column(name = "expiry_date", columnDefinition = "TIMESTAMP")
  private Date expiryDate;

  @OneToOne
  @JoinColumn(nullable = false, name = "user_id")
  private User user;

  private static final int EXPIRATION_TIME_IN_SEC = 60 * 60;


  public PasswordResetToken(String _token, User _user)
  {
	token = _token;
	user = _user;
	Date now = Calendar.getInstance().getTime();
	long time = now.getTime() + EXPIRATION_TIME_IN_SEC * 1000;
	expiryDate = new Date(time);
  }

}