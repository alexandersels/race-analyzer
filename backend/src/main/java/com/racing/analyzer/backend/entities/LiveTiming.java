package com.racing.analyzer.backend.entities;

import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

@Entity
@Table(name = "livetiming")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LiveTiming extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private int number;

    @Column(name = "cls")
    private String cls;

    @Column(name = "position")
    private int position;

    @Column(name = "lasttime")
    private long lastTime;

    @Column(name = "besttime")
    private long bestTime;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "pit")
    private boolean inPit;

    @Column(name = "car")
    private String car;

    @Column(name = "state")
    private LiveTimingState state;

    @Column(name = "sectorone")
    private long sectorOne;

    @Column(name = "sectortwo")
    private long sectorTwo;

    @Column(name = "sectorthree")
    private long sectorThree;

    @CreationTimestamp
    @Column(name = "creation")
    private Timestamp creation;

    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;

    @Builder
    public LiveTiming(long id, String name, int number, String cls, int position, long lastTime, long bestTime,
                      String nationality, boolean inPit, String car, LiveTimingState state, long sectorOne,
                      long sectorTwo, long sectorThree, Race race) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.cls = cls;
        this.position = position;
        this.lastTime = lastTime;
        this.bestTime = bestTime;
        this.nationality = nationality;
        this.inPit = inPit;
        this.car = car;
        this.state = state;
        this.sectorOne = sectorOne;
        this.sectorTwo = sectorTwo;
        this.sectorThree = sectorThree;
        this.race = race;
    }

    public boolean areSectorsFilledIn() {
        return (sectorOne != -1) && (sectorTwo != -1) && (sectorThree != -1);
    }

}


