package com.racing.analyzer.backend.entities;

import com.racing.analyzer.backend.commands.ICommandHandler;
import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "livetiming")
@NoArgsConstructor
public class LiveTiming extends BaseEntity {

    @Id
    @NotNull
    @Column(name = "id")
    @Access(AccessType.PROPERTY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private long id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Column(name = "number")
    @Getter
    @Setter
    private int number;

    @Column(name = "cls")
    @Getter
    @Setter
    private String cls;

    @Column(name = "position")
    @Getter
    @Setter
    private int position;

    @Column(name = "lasttime")
    @Getter
    @Setter
    private long lastTime;

    @Column(name = "besttime")
    @Getter
    @Setter
    private long bestTime;

    @Column(name = "nationality")
    @Getter
    @Setter
    private String nationality;

    @Column(name = "pit")
    @Getter
    @Setter
    private boolean inPit;

    @Column(name = "car")
    @Getter
    @Setter
    private String car;

    @Column(name = "state")
    @Getter
    @Setter
    private LiveTimingState state;

    @Column(name = "sectorone")
    @Getter
    @Setter
    private long sectorOne;

    @Column(name = "sectortwo")
    @Getter
    @Setter
    private long sectorTwo;

    @Column(name = "sectorthree")
    @Getter
    @Setter
    private long sectorThree;

    @CreationTimestamp
    @Column(name = "creation")
    @Getter
    private Timestamp creation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id")
    @Getter
    @Setter
    private Race race;

    @Builder
    public LiveTiming(long id, String name, int number, String cls, int position, long lastTime, long bestTime, String nationality,
                      boolean inPit, String car, LiveTimingState state, long sectorOne, long sectorTwo, long sectorThree, Race race) {
        this.id = id;
        this.position = position;
        this.name = name;
        this.number = number;
        this.cls = cls;
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

    @Override
    protected Collection<ICommandHandler> getCommandHandlers() {
        return Arrays.asList();
    }
}


