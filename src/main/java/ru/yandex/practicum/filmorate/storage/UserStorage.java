package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User addUser(User user);

    void removeUser(long userId);

    User updateUser(User user);

    User findUserById(long userId);

    Collection<User> getAllUsers();

    Collection<User> findUsersByName(String name);

    Collection<User> findUsersByEmail(String email);

    int countUsers();
}
