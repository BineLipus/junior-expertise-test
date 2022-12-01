package com.outfit7.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.outfit7.entity.User;
import com.outfit7.services.UserService;

@Path("/user")
public class UserFacade {

    @Inject
    UserService userService;

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User get(@PathParam("userId") String userId) {
        return userService.get(userId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAll() {
        return userService.getAll();
    }

}