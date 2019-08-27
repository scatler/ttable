package com.scatler.ttable.dao;

import com.scatler.ttable.dto.StationTimeTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeTableDao {
    private Map<Integer, StationTimeTable> map = new HashMap<>();
}
