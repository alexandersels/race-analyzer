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
@Table(name = "LiveTiming")
public class LiveTiming extends BaseEntity {

    @Id
    @Column(name = "id")
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
    private long lastTime;

    @Column(name = "besttime")
    private long bestTime;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "inPit")
    private boolean inPit;

    @Column(name = "car")
    private String car;

    @Column(name = "state")
    private LiveTimingState state;

    @Column(name = "sectorOne")
    private long sectorOne;

    @Column(name = "sectorTwo")
    private long sectorTwo;

    @Column(name = "sectorThree")
    private long sectorThree;

    @CreationTimestamp
    @Column(name = "creationTimestamp")
    private Timestamp creationTimestamp;

    public LiveTiming(String name, int number, String cls, int position, long lastTime, long bestTime,
                      String nationality, boolean inPit, String car, LiveTimingState state,
                      long sectorOne, long sectorTwo, long sectorThree) {
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
    }

    public static LiveTimingBuilder getBuilder() {
        return new LiveTimingBuilder();
    }

    public Long getId() {
        return id;
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

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public boolean areSectorsFilledIn() {
        return (sectorOne != -1) && (sectorTwo != -1) && (sectorThree != -1);
    }

    @Override
    public String toString() {
        return "Position: " + position + ", Number: " + number + ", Name: " + name + ", CLS: " + cls +
                ", Last: " + lastTime + ", Best: " + bestTime + ", Nat: " + nationality + " ,Car: " + car +
                " ,State:" + state + " ,Sector One:" + sectorOne + " ,Sector Two:" + sectorTwo + " ,Sector Three:" + sectorThree;
    }

    @Override
    protected Collection<ICommandHandler> getCommandHandlers() {
        return Arrays.asList();
    }
}
