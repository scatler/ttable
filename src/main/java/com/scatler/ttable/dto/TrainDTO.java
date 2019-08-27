package com.scatler.ttable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class TrainDTO {
    private Integer trainId;
    private Integer routeId;
    private String routeName;
    private Date arrivalTime;
}
