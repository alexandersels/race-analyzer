package com.racing.analyzer.backend.dto.statistics;

import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoundDTO {

    private long lapTime;
    private boolean inPit;
    private long sectorOneTime;
    private long sectorTwoTime;
    private long sectorThreeTime;
    private int position;
    private LiveTimingState state;

    @Builder
    public RoundDTO(long lapTime, boolean inPit, long sectorOneTime, long sectorTwoTime, long sectorThreeTime, int position, LiveTimingState state) {
        this.lapTime = lapTime;
        this.inPit = inPit;
        this.sectorOneTime = sectorOneTime;
        this.sectorTwoTime = sectorTwoTime;
        this.sectorThreeTime = sectorThreeTime;
        this.position = position;
        this.state = state;
    }
}
