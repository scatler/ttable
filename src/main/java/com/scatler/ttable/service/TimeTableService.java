package com.scatler.ttable.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TimeTableService {
    @PersistenceContext
    private EntityManager entityManager;

}
