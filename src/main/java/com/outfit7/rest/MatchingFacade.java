package com.outfit7.rest;

import com.outfit7.entity.User;
import com.outfit7.services.OpponentsService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/matching")
public class MatchingFacade {

    @Inject
    OpponentsService opponentsService;

    @GET
    @Path("/classic/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> matchOpponents(@PathParam("userId") String userId) {
        return opponentsService.matchOpponents(userId);
    }

    @GET
    @Path("/ranked/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    // Should return 5 random opponents for user userId or return error (with HTTP status code 404?)
    public List<User> matchOpponentsRanked(@PathParam("userId") String userId) {
        return opponentsService.matchOpponentsRanked(userId);
    }

}