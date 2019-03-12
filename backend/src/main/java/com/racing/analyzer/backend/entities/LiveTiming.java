package com.racing.analyzer.backend.entities;

import com.racing.analyzer.backend.builders.LiveTimingBuilder;
import com.racing.analyzer.backend.commands.ICommandHandler;
import com.racing.analyzer.backend.enums.LiveTimingState;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "LiveTiming")
public class LiveTiming extends BaseEntity {

    @Id
    @Column(name = "id")
    @Access(AccessType.PROPERTY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private int number;

    @Column(name = "cls")
    private String cls;

    @Column(name = "position")
    private int position;

    @Column(name = "lasttime")
    private Long lastTime;

    @Column(name = "besttime")
    private Long bestTime;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "inPit")
    private boolean inPit;

    @Column(name = "car")
    private String car;

    @Column(name = "state")
    private LiveTimingState state;

    @Column(name = "sectorOne")
    private Long sectorOne;

    @Column(name = "sectorTwo")
    private Long sectorTwo;

    @Column(name = "sectorThree")
    private Long sectorThree;

    @CreationTimestamp
    @Column(name = "creationTimestamp")
    private Timestamp creationTimestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id")
    private Race race;

    public LiveTiming(String name, int number, String cls, int position, Long lastTime, Long bestTime,
                      String nationality, boolean inPit, String car, LiveTimingState state,
                      Long sectorOne, Long sectorTwo, Long sectorThree, Race race) {
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

    public Long getId() {
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

    public Long getLastTime() {
        return lastTime;
    }

    public Long getBestTime() {
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

    public Long getSectorOne() {
        return sectorOne;
    }

    public Long getSectorTwo() {
        return sectorTwo;
    }

    public Long getSectorThree() {
        return sectorThree;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public Race getRace() {
        return race;
    }

    public boolean areSectorsFilledIn() {
        return (sectorOne != -1) && (sectorTwo != -1) && (sectorThree != -1);
    }

    public static LiveTimingBuilder getBuilder() {
        return new LiveTimingBuilder();
    }

    @Override
    protected Collection<ICommandHandler> getCommandHandlers() {
        return Arrays.asList();
    }
}
