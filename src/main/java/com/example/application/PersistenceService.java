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


        em.persist(cm);


        return "SUCCESS";
    }


    /*
     * Test a finding or removing an entity when one hasn't been persisted.
     */
    @GET
    @Path("/findOrDeleteBeforePersist")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String findOrDeleteBeforePersist() {
        
        FODCrewMember cm = new FODCrewMember();       
        cm.name = "test";
        cm.crewID = 74566;

        em.createQuery("SELECT c FROM CrewMember c ORDER BY c.name", CrewMember.class).getResultList();

        em.find(CrewMember.class, 74566);

        em.remove(cm);


        return "SUCCESS";
    }


    @GET
    @Path("/modifyEntity")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String modifyEntity() {
        /* 
        To test this, run it once, and then manually modify the
        type of name to Rank in SimpleCrewMember
        
        crewID will need to be changed to a different int
        */

        SimpleCrewMember cm = new SimpleCrewMember();       
        cm.name = "test"; //Rank.Captain;
        cm.crewID = 1223;

        em.persist(cm);

        return "SUCCESS";
    }

}
