package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@Data
@EqualsAndHashCode(of = "email")
public class User {
    private Long id;
    private String email;
    private String login;
    private String name;
    private Instant birthday;
}
