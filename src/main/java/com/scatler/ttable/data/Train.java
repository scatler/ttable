package com.scatler.ttable.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Simple entity.
 *
 * @author Roberto Cortez
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    @SequenceGenerator(name = "id", sequenceName = "id")
    private Long id;
    private String name;
    private Date arrival;
}
