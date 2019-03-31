package com.racing.analyzer.backend.dto.statistics;

import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoundDTO {

    private long lapTime;
    private boolean inPit;
    private long sectorOneTime;
    private long sectorTwoTime;
    private long sectorThreeTime;
    private int position;
    private LiveTimingState state;

}
