package com.racing.analyzer.backend.mappers;

import com.racing.analyzer.backend.dto.livetiming.CreateLiveTimingDTO;
import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface TestMapper {

    @Mapping(target = "driverName", source = "name")
    @Mapping(target = "raceId", source = "race.id")
    LiveTimingDTO toDto(LiveTiming timing);

    @Mapping(target = "name", source = "driverName")
    @Mapping(target = "race", source = "race", qualifiedByName = "toEntity")
    LiveTiming fromDto(CreateLiveTimingDTO dto);

    @Named("toEntity")
    default Race toEntity(long id) {
        return Race.builder()
                   .id(id)
                   .build();
    }

}
