package com.racing.analyzer.backend.entities;

import com.racing.analyzer.backend.builders.LiveTimingBuilder;
import com.racing.analyzer.backend.commands.ICommandHandler;
import com.racing.analyzer.backend.enums.LiveTimingState;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "livetiming")
public class LiveTiming extends BaseEntity {

    @Id
    @Column(name = "id")
    @Access(AccessType.PROPERTY)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id")
    private Race race;

    private LiveTiming() { }

    public LiveTiming(String name, int number, String cls, int position, long lastTime, long bestTime,
                      String nationality, boolean inPit, String car, LiveTimingState state,
                      long sectorOne, long sectorTwo, long sectorThree, Race race) {
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

    public static LiveTimingBuilder getBuilder() {
        return new LiveTimingBuilder();
    }

    public long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getCls() {
        return cls;
    }

    public int getPosition() {
        return position;
    }

    public long getLastTime() {
        return lastTime;
    }

    public long getBestTime() {
        return bestTime;
    }

    public String getNationality() {
        return nationality;
    }

    public boolean isInPit() {
        return inPit;
    }

    public String getCar() {
        return car;
    }

    public LiveTimingState getState() {
        return state;
    }

    public long getSectorOne() {
        return sectorOne;
    }

    public long getSectorTwo() {
        return sectorTwo;
    }

    public long getSectorThree() {
        return sectorThree;
    }

    public Timestamp getCreation() {
        return creation;
    }

    public Race getRace() {
        return race;
    }

    public boolean areSectorsFilledIn() {
        return (sectorOne != -1) && (sectorTwo != -1) && (sectorThree != -1);
    }

    @Override
    protected Collection<ICommandHandler> getCommandHandlers() {
        return Arrays.asList();
    }
}
