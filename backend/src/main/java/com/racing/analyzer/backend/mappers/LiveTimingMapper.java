package com.racing.analyzer.backend.mappers;

import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LiveTimingMapper {

    @Mapping(source = "race.id", target = "race")
    LiveTimingDTO toDto(LiveTiming liveTiming);

}
