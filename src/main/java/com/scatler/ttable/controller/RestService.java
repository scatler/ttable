package com.scatler.ttable.controller;

import com.scatler.ttable.dto.StationTimeTableWrapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class RestService {
    public StationTimeTableWrapper requestUpdate(Integer stationId) {
        Client client = ClientBuilder.newClient();
        return client.target("http://localhost:9999/update/" + stationId)
                .request("application/json")
                .get(StationTimeTableWrapper.class);
    }
}
