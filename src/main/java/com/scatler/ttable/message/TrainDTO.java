package com.scatler.ttable.message;

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
    private Integer id;
    private String name;
    private Date arrival;
}
