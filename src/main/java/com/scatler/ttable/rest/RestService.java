package com.scatler.ttable.rest;

import com.scatler.ttable.dto.StationTimeTableWrapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class RestService {
    public StationTimeTableWrapper requestUpdate(Integer stationId) {
        Client client = ClientBuilder.newClient();
        StationTimeTableWrapper stationTimeTableWrapper = client.target("http://localhost:8080/update/" + stationId)
                .request("application/json")
                .get(StationTimeTableWrapper.class);
        return stationTimeTableWrapper;
    }
}
