package com.racing.analyzer.backend.dto.livetiming;

import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LiveTimingDTO {

    private long id;
    private String name;
    private int number;
    private String car;
    private String cls;
    private int position;
    private LiveTimingState state;
    private String nationality;
    private boolean inPit;
    private long lastTime;
    private long bestTime;
    private long sectorOne;
    private long sectorTwo;
    private long sectorThree;
    private long race;

    @Builder
    public LiveTimingDTO(long id, String name, int number, String car, String cls, int position, LiveTimingState state,
                         String nationality, boolean inPit, long lastTime, long bestTime, long sectorOne,
                         long sectorTwo, long sectorThree, long race) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.car = car;
        this.cls = cls;
        this.position = position;
        this.state = state;
        this.nationality = nationality;
        this.inPit = inPit;
        this.lastTime = lastTime;
        this.bestTime = bestTime;
        this.sectorOne = sectorOne;
        this.sectorTwo = sectorTwo;
        this.sectorThree = sectorThree;
        this.race = race;
    }
}
