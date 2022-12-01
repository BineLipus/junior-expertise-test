package com.outfit7.services;

import static com.outfit7.json.JsonUtils.deserializeToList;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.outfit7.entity.User;
import com.outfit7.entity.exception.EntityNotFoundException;

import lombok.SneakyThrows;

@ApplicationScoped
public class UserService {

    private static final String USER_DB = "/users/users.json";

    @SneakyThrows
    public List<User> getAll() {
        return deserializeToList(UserService.class.getResourceAsStream(USER_DB), User.class);
    }

    public User get(String userId) {
        return getAll().stream()
                .filter(user -> userId.equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User does not exist"));
    }

}
