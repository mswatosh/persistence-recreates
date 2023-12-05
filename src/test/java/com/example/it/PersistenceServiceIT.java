package com.example.it;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.net.Socket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;

public class PersistenceServiceIT {

    private static String baseURL;
    
    private Client client;
    private Response response;

    @BeforeAll
    public static void init() {             
        String port = System.getProperty("http.port");
        baseURL = "http://localhost:" + port + "/";
    }

    @BeforeEach
    public void setup() {
      client = ClientBuilder.newClient();
      assumeTrue(isPostgresAvailable(), "Postgres is not Available");
    }

    @AfterEach
    public void teardown() {
            client.close();
    }


    @Test
    public void test1() {
        response = client.target(baseURL + "db/crew/test1").request().get();
        System.out.println(response);
    }


    private static boolean isPostgresAvailable() {
        return checkHostAndPort("localhost", 5432);
    }
    
    private static boolean checkHostAndPort(String host, int port) {
        try (Socket s = new Socket(host, port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
