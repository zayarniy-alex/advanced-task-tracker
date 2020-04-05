package ru.geekbrains.dto;


import lombok.Data;
import ru.geekbrains.validation.PasswordConstraint;

import javax.validation.constraints.NotBlank;


@Data
public class NewPasswordDTO
{

  @NotBlank(message = "обязательное поле")
  @PasswordConstraint
  public String newpassword;

  @NotBlank(message = "обязательное поле")
  public String matchingPassword;

}