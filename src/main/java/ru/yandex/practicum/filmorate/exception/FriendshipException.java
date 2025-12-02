package ru.yandex.practicum.filmorate.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class FriendshipException extends RuntimeException {
    private final Long user1Id;
    private final Long user2Id;
}
