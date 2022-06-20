package ru.yandex.practicum.filmorate.exeption.friend;

public class ConflictFriendIdException extends RuntimeException{
    public ConflictFriendIdException(String message) {
        super(message);
    }
}
