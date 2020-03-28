package ru.geekbrains.dto;


import lombok.Data;
import ru.geekbrains.validation.PasswordConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class UserDTO {

    @NotBlank (message = "обязательное поле")
    @Size (min = 4, max = 30, message = "количество символов должно быть от 4 до 30")
    public String username;

    @NotBlank (message = "обязательное поле")
    @Size (min = 8, max = 80, message = "количество символов должно быть от 8 до 80")
    @PasswordConstraint
    public String password;

    @NotBlank (message = "обязательное поле")
    public String matchingPassword;

    @NotBlank (message = "обязательное поле")
    @Size (max = 50, message = "количество символов должно быть не больше 50")
    public String firstname;

    @NotBlank (message = "обязательное поле")
    @Size (max = 50, message = "количество символов должно быть не больше 50")
    public String lastname;

    @Email (message = "некорректный адрес электронной почты")
    @Size (max = 50, message = "количество символов должно быть не больше 50")
    public String email;

}