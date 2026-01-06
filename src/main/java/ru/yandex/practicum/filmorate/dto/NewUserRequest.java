package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class NewUserRequest {

    private Integer id;
    @Email
    private String email;
    @Pattern(regexp = "^\\S+$", message = "Логин не должен содержать пробелов")
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
}

