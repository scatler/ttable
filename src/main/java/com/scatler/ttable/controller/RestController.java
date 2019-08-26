package com.scatler.ttable.controller;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class RestController {
    public static void requestUpdate() {
        Client client = ClientBuilder.newClient();
        String name = client.target("http://localhost:8080/webapi/update")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);
    }
}
