package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.friend.ConflictFriendIdException;
import ru.yandex.practicum.filmorate.exeption.friend.IdLessThanOneException;
import ru.yandex.practicum.filmorate.exeption.user.DateOfBirthCannotBeInTheFutureException;
import ru.yandex.practicum.filmorate.exeption.user.UserDoesntExistException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage{
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Set<Integer>> friends = new HashMap<>();

    @Override
    public User add(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Birthday can't be in future or current date ");
            throw new DateOfBirthCannotBeInTheFutureException("Дата рождение не может быть текущей или будущей датой");
        } else if (user.getName().isEmpty() | user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.getNextId();
        users.put(user.getId(), user);
        log.info("Operation success: Created new user" + user.getLogin());
        return user;
    }

    @Override
    public User update(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Birthday can't be in future or current date ");
            throw new DateOfBirthCannotBeInTheFutureException("Date can't be in future");
        } else if (user.getName().isEmpty() | user.getName().isBlank()) {
            user.setName(user.getLogin());
        } else if (!users.containsKey(user.getId())) {
            log.error("User with " + " " + user.getId() + " not in the system ");
            throw new UserDoesntExistException("can't find user for update");
        }
        users.put(user.getId(), user);
        log.info("Operation success: User updated " + user.getLogin());
        return user;
    }

    @Override
    public User delete(User user) {
        users.remove(user.getId());
        return user;
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public User getUserById(int userId) {
        if (!users.containsKey(userId)) {
            log.error("Can't found user with " + " " + userId);
            throw new UserDoesntExistException("Can't found user with " + " " + userId);
        }
        return users.get(userId);
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        if (Objects.equals(userId, friendId)) {
            log.error("You can't be different to yourself");
            throw new ConflictFriendIdException("You can't be different to yourself");
        } else if (userId < 1 || friendId < 1) {
            log.error("Id can't be less then 1 ");
            throw new IdLessThanOneException("Some id less than 1" + " " + userId + " or " + friendId);
        }
        checkUserExist(userId);
        checkUserExist(friendId);
        if (!friends.containsKey(userId)) {
            friends.put(userId, new HashSet<>());
        }
        friends.get(userId).add(friendId);
        if (!friends.containsKey(friendId)) {
            friends.put(friendId, new HashSet<>());
        }
        friends.get(friendId).add(userId);
    }

    public List<User> getFriends(Integer userId) {
        checkUserExist(userId);
        Set<Integer> friendIds = friends.getOrDefault(userId, Collections.emptySet());
        return friendIds.stream()
                //здесь заменил выражение -> (x -> users.get(x))
                .map(users::get).collect(Collectors.toList());
    }

    public List<User> getCommonFriendList(Integer userId, Integer otherId) {
        checkUserExist(userId);
        checkUserExist(otherId);
        Set<User> intersectIds = new HashSet<>(getFriends(userId));
        intersectIds.retainAll(getFriends(otherId));
        return new ArrayList<>(intersectIds);
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        friends.get(userId).remove(friendId);
        friends.get(friendId).remove(userId);
    }

    private void checkUserExist(Integer id) {
        if (!users.containsKey(id)) {
            log.error("Can't found user with " + " " + id);
            throw new UserDoesntExistException("Can't found user with " + " " + id);
        }
    }

}
