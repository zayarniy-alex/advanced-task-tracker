package ru.geekbrains.dto;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class EmailDTO
{

  @NotBlank(message = "обязательное поле")
  @Size(min = 4, max = 30, message = "количество символов должно быть от 4 до 30")
  public String username;

  @NotBlank(message = "обязательное поле")
  @Size(max = 50, message = "количество символов должно быть не больше 50")
  @Email(message = "некорректный адрес электронной почты")
  public String email;

}