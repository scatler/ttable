package com.scatler.ttable.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationTimeTable implements Serializable {
    private Integer trainId;
    private Integer routeId;
    private String routeName;
    @JsonFormat(pattern = "HH:mm:ss")
    private Date arrivalTime;
}
