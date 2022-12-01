package com.outfit7.services;

import com.outfit7.entity.User;
import com.outfit7.entity.exception.EntityNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClassicMatchingService extends MatchingService {

    protected static Predicate<User> filterByPowerLevel(User currentUser) {
        return opponent ->
                opponent.getPowerLevel() <= currentUser.getPowerLevel() + 15
                        && opponent.getPowerLevel() >= currentUser.getPowerLevel() - 15;
    }

    protected List<User> matchOpponents(User currentUser) {
        List<User> opponents = userService.getAll().stream()
                .filter(opponent -> !opponent.getId().equals(currentUser.getId()))
                .filter(filterByPowerLevel(currentUser))
                .filter(distinctByKey(User::getPlayerName))
                .collect(Collectors.toList());
        if (opponents.size() == 0) throw new EntityNotFoundException("No classic opponents found for user with id " + currentUser.getId());
        return opponents;
        /*
        I changed filter sequence to avoid potential opponents being filtered out, by placing
        filter(distinctByKey(User::getPlayerName)) at the end of filter sequence.

        If distinctByKey is not the last filter, it can filter out potential opponents. E.g. if we have two opponents
        with the same playerName and first one doesn't meet powerLevel requirements, but the second one does,
        then second one would be filtered out first in distinctByKey(User::getPlayerName) and then the first one
        would be filtered out in filterByPowerLevel, resulting in both being filtered out.

        If filtering by playerName is done after filtering by powerLevel, then filterByPowerLevel removes the player,
        that doesn't meet powerLevel requirements, but distinctByKey(User::getPlayerName) leaves the other player in
        List and returns it as one of opponents.
         */
    }
}
