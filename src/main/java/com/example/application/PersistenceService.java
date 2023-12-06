package com.example.application;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RequestScoped
@Path("/crew")
public class PersistenceService {

    @PersistenceContext(name = "persistence")
    private EntityManager em;
    
    @GET
    @Path("/columnSpecifiedMoreThanOnce")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String columnSpecifiedMoreThanOnce() {
        
        Ship s = new Ship();
        s.name = "Liberty Saucer";
        s.size = Ship.Size.small;

        CrewMember cm = new CrewMember();       
        cm.name = "test";
        cm.crewID = 1231;
        cm.ship = s;

        em.persist(cm);


        return "SUCCESS";
    }
}
