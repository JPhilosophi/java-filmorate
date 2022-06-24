package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage{
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Set<Integer>> friends = new HashMap<>();
    private static int userId = 1;

    @Override
    public User create(User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User delete(User user) {
        users.remove(user.getId());
        return user;
    }

    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
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
        Set<Integer> friendIds = friends.getOrDefault(userId, Collections.emptySet());
        return friendIds.stream()
                //здесь заменил выражение -> (x -> users.get(x))
                .map(users::get).collect(Collectors.toList());
    }

    public List<User> getCommonFriendList(Integer userId, Integer otherId) {
        Set<User> intersectIds = new HashSet<>(getFriends(userId));
        intersectIds.retainAll(getFriends(otherId));
        return new ArrayList<>(intersectIds);
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        friends.get(userId).remove(friendId);
        friends.get(friendId).remove(userId);
    }


    public int getNextId() {
        return userId++;
    }
}
