package ru.geekbrains.dto;


import lombok.Data;
import ru.geekbrains.validation.PasswordConstraint;

import javax.validation.constraints.NotBlank;


@Data
public class UpdatePasswordDTO
{

  @NotBlank(message = NOT_BLANK_MSG)
  public String oldpassword;

  @NotBlank(message = NOT_BLANK_MSG)
  @PasswordConstraint
  public String newpassword;

  @NotBlank(message = NOT_BLANK_MSG)
  public String matchingPassword;

  private static final String NOT_BLANK_MSG = "обязательное поле";

}