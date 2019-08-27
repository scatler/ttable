package com.scatler.ttable.rest;

import com.scatler.ttable.dto.StationTimeTableWrapper;
import com.scatler.ttable.dto.TimeTableBean;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

@Stateless
@ApplicationPath("/resources")
@Path("train")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrainResource extends Application {
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    TimeTableBean timeTableBean;

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public StationTimeTableWrapper getTrainList() {
        System.out.println(timeTableBean.getTimeTableWrapper().getList());
        return timeTableBean.getTimeTableWrapper();
    }
}
