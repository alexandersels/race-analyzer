package com.racing.analyzer.backend.dto.statistics;

import java.util.Collection;

public class DriverDTO {

    public int number;
    public String name;
    public String car;
    public Collection<RoundDTO> rounds;
    public int pitStops;
    public long bestLap;

}
