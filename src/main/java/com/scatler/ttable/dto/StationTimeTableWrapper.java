package com.scatler.ttable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationTimeTableWrapper implements Serializable {
    private List<StationTimeTable> list;
}
