package com.racing.analyzer.backend.entities;

import com.racing.analyzer.backend.commands.ICommandHandler;
import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "livetiming")
@Data
@Builder
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

    public boolean areSectorsFilledIn() {
        return (sectorOne != -1) && (sectorTwo != -1) && (sectorThree != -1);
    }

    @Override
    protected Collection<ICommandHandler> getCommandHandlers() {
        return Arrays.asList();
    }
}

