package com.outfit7.services;

import com.outfit7.entity.User;
import com.outfit7.entity.exception.EntityNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
public class RankedMatchingService extends MatchingService {

    protected static Predicate<User> filterByRank(User currentUser) {
        return opponent -> Math.abs(opponent.getRank() - currentUser.getRank()) <= 100;
    }

    @Override
    protected List<User> matchOpponents(User currentUser) {
        List<User> opponents = userService.getAll().stream()
                .filter(opponent -> !opponent.getId().equals(currentUser.getId())) // don't match the user against himself
                .filter(filterByRank(currentUser))          // filter out by absolute rank difference
                .filter(distinctByKey(User::getPlayerName)) // filter out duplicates by name
                .collect(Collectors.toList());
        /*
        I changed filter sequence to avoid potential opponents being filtered out, by placing
        filter(distinctByKey(User::getPlayerName)) at the end of filter sequence.

        If distinctByKey is not the last filter, it can filter out potential opponents. E.g. if we have two opponents
        with the same playerName and first one doesn't meet rank requirements, but the second one does,
        then the second one would be filtered out first in distinctByKey(User::getPlayerName) and then the first one
        would be filtered out in filterByRank, resulting in both being filtered out.

        If filtering by playerName is done after filtering by rank, then filterByRank removes the player,
        that doesn't meet rank requirements, but distinctByKey(User::getPlayerName) leaves the other player in
        List and returns it as one of opponents.
         */

        if (opponents.size() < 5) throw new EntityNotFoundException("No ranked opponents found for user with id " + currentUser.getId());
        //return (opponents.size() >= 5 ? opponents.subList(0, 5) : null);
        Collections.shuffle(opponents);
        return opponents.subList(0, 5);
    }
}
