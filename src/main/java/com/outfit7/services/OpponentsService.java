package com.outfit7.services;

import com.outfit7.entity.User;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@Slf4j
@ApplicationScoped
public class OpponentsService {

    @Inject
    ClassicMatchingService classicMatchingService;

    @Inject
    RankedMatchingService rankedMatchingService;

    public List<User> matchOpponents(String userId) {
        log.info("Running classic matcher for userId: '{}'", userId);
        return classicMatchingService.retrieveOpponents(userId);
    }

    public List<User> matchOpponentsRanked(String userId) {
        log.info("Running ranked matcher for userId: '{}'", userId);
        return rankedMatchingService.retrieveOpponents(userId);
    }

}
