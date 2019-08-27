package com.scatler.ttable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class TimeTableBean {

    StationTimeTableWrapper timeTableWrapper;

    @PostConstruct
    public void afterCreate() {
        System.out.println("TimeTable bean created TEST");
    }
}
