package com.racing.analyzer.backend.mappers;

import com.racing.analyzer.backend.dto.livetiming.CreateLiveTimingDTO;
import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TestMapper {

    @Mapping(target = "driverName", source = "name")
    @Mapping(target = "raceId", source = "race.id")
    LiveTimingDTO toDto(LiveTiming timing);

    @Mapping(target = "name", source = "driverName")
    LiveTiming fromDto(CreateLiveTimingDTO dto);

}
