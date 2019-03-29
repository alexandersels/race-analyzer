package com.racing.analyzer.backend.mappers;

import com.racing.analyzer.backend.dto.race.RaceDTO;
import com.racing.analyzer.backend.entities.Race;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RaceMapper {

    RaceDTO toDto(Race race);

}
