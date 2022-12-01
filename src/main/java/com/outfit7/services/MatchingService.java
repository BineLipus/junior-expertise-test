package com.outfit7.services;

import com.outfit7.entity.User;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
public abstract class MatchingService {

    /*
        Every MatchingService should be implemented in its own way, but all should have these functions in common.
        I used abstract class instead of interface because, MatchingServices need to implement their own
        matchOpponents(User currentUser) function, but don't need to implement other functions, because they are all
        the same for every implementation. Non static functions can still be overriden if needed.
    */

    @Inject
    UserService userService;

    protected static Predicate<User> distinctByKey(Function<User, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t)); // when matching opponents, we should get all distinct opponents by
        // their name instead of their ids, which should be unique anyway.
    }

    public List<User> retrieveOpponents(String userId) {
        User currentUser = userService.get(userId);
        log.debug("Found user: '{}'", currentUser);
        return matchOpponents(currentUser);
    }

    protected abstract List<User> matchOpponents(User currentUser);
}
