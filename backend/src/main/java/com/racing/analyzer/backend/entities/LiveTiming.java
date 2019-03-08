package com.racing.analyzer.backend.entities;

import com.racing.analyzer.backend.builders.LiveTimingBuilder;
import com.racing.analyzer.backend.commands.ICommandHandler;

import javax.persistence.*;
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

    public LiveTiming(String name, int number, String cls, int position, long lastTime, long bestTime, String nationality, boolean inPit, String car) {
        this.name = name;
        this.number = number;
        this.cls = cls;
        this.position = position;
        this.lastTime = lastTime;
        this.bestTime = bestTime;
        this.nationality = nationality;
        this.inPit = inPit;
        this.car = car;
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

    @Override
    protected Collection<ICommandHandler> getCommandHandlers() {
        return Arrays.asList();
    }

    public static LiveTimingBuilder getBuilder() {
        return new LiveTimingBuilder();
    }

    @Override
    public String toString() {
        return "Position: " + position + ", Number: " + number + ", Name: " + name + ", CLS: " + cls +
                ", Last: " + lastTime + ", Best: " + bestTime + ", Nat: " + nationality + " ,Car: " + car;
    }

}
