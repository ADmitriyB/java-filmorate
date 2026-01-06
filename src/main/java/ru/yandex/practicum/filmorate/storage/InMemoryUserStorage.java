package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public User addUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ConditionsNotMetException("Имейл должен быть указан и содержать символ @");
        }
        if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ConditionsNotMetException("Логин не может быть пустым или содержать пробелы");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            //throw new ConditionsNotMetException("Имя пользователя не может быть пустым");
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ConditionsNotMetException("дата рождения не может быть в будущем");
        }
        user.setId(getNextId());
        users.put(user.getId(), user);
        System.out.println("Количество пользователей: " + users.size() + getAllUsers());
        return user;

    }

    @Override
    public void removeUser(Integer userId) {
        users.remove(userId);
    }

    @Override
    public User updateUser(User newUser) {
        if (newUser.getId() == null || !users.containsKey(newUser.getId())) {
            throw new UserNotFoundException("Id не найден");
        }
        if (users.values().stream().anyMatch(u -> u.getEmail().equals(newUser.getEmail()) && !u.getId().equals(newUser.getId()))) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }

        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            if (!(newUser.getEmail() == null || newUser.getEmail().isBlank() || !newUser.getEmail().contains("@"))) {
                oldUser.setEmail(newUser.getEmail());
            }
            if (!(newUser.getLogin() == null || newUser.getLogin().isBlank() || newUser.getLogin().contains(" "))) {
                oldUser.setLogin(newUser.getLogin());
            }
            if (!(newUser.getName() == null || newUser.getName().isBlank())) {
                oldUser.setName(newUser.getName());
            }
            if (newUser.getBirthday().isBefore(LocalDate.now())) {
                oldUser.setBirthday(newUser.getBirthday());


            }
            return oldUser;
        } else {
            throw new NotFoundException("Пользователь с ID = " + newUser.getId() + " не найден");
        }
    }

    @Override
    public User findUserById(Integer userId) {
        if (users.containsKey(userId)) {
            return users.get(userId);
        }
        throw new NotFoundException("Пользователь с ID = " + userId + " не найден");
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public Collection<User> findUsersByName(String name) {
        Collection<User> matchingUsers = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getName().equalsIgnoreCase(name)) {
                matchingUsers.add(user);
            }
        }
        return matchingUsers;
    }

    @Override
    public List<User> findUsersByEmail(String email) {
        List<User> matchingUsers = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                matchingUsers.add(user);
            }
        }
        return matchingUsers;
    }

    @Override
    public int countUsers() {
        return users.size();
    }

    // вспомогательный метод для генерации идентификатора нового поста
    private Integer getNextId() {
        int currentMaxId = users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}


