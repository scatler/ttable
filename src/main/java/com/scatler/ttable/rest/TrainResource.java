package com.scatler.ttable.rest;

import com.scatler.ttable.data.Train;
import com.scatler.ttable.message.TrainDTO;
import com.scatler.ttable.message.TrainsWrapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@ApplicationPath("/resources")
@Path("train")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrainResource extends Application {
    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public TrainsWrapper getTrainList() {
        List<Train> trains = entityManager.createQuery("select t from Train t").getResultList();
        ModelMapper modelMapper = new ModelMapper();
        java.lang.reflect.Type targetListType = new TypeToken<List<TrainDTO>>() {
        }.getType();
        List<TrainDTO> dtos = modelMapper.map(trains, targetListType);
        TrainsWrapper trainsWrapper = new TrainsWrapper(dtos);
        return trainsWrapper;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TrainDTO getPerson(@PathParam("id") Long id) {
        Train train = entityManager.find(Train.class, id);
        ModelMapper modelMapper = new ModelMapper();
        TrainDTO dto = modelMapper.map(train, TrainDTO.class);
        return dto;
    }
}
