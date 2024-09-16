package com.example.application;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

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
        //cm.name = "test"; //Uncomment for test
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

    @GET
    @Path("/listOrder")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String listOrder() {
        
        Package p1 = new Package(70071, 17.0f, 17.1f, 7.7f, "Should be First");
        Package p3 = new Package(70077, 77.0f, 17.7f, 7.7f, "Should be Second");
        Package p4 = new Package(70007, 70.0f, 10.7f, 0.7f, "Should not be returned");
        
        
        em.persist(p1);
        em.persist(p3);
        em.persist(p4);

        List<?> results = em.createQuery("SELECT o FROM Package o WHERE (o.height<?1) ORDER BY o.height DESC, o.length").setParameter(1,8.0).setMaxResults(2).getResultList();

        em.remove(p1);
        em.remove(p3);
        em.remove(p4);

        return results.toString();

    }

    @GET
    @Path("/uuid")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String uuid() {

        PurchaseOrder p1 = new PurchaseOrder("Purchase Order");

        
        em.persist(p1);
        em.flush();
        UUID uuid = p1.id;
        em.refresh(p1);
        List<?> result = em.createQuery("SELECT p FROM PurchaseOrder p WHERE p.id=?1").setParameter(1, uuid).getResultList();
        em.remove(p1);


        return result.toString();

    }


    @GET
    @Path("/entityExists")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String entityExists() {

        Package p1 = new Package(70071, 17.0f, 17.1f, 7.7f, "Should be First");
        Package p2 = new Package(70071, 18.0f, 17.1f, 7.7f, "Should be First");

        em.persist(p1);
        em.persist(p2);
        em.flush();
        em.refresh(p1);

        em.remove(p1);
        em.remove(p2);


        return "SUCCESS";

    }


    @GET
    @Path("/validation")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String validation() {
        ValidationEntity ve = new ValidationEntity(null, 6);

        em.persist(ve);

        return "SUCCESS";
    }

}
