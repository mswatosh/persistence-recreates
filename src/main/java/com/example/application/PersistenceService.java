package com.example.application;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/crew")
public class PersistenceService {
    
    @GET
    @Path("/test1")
    @Produces(MediaType.TEXT_PLAIN)
    public String test1() {


        return "SUCCESS";
    }
}
