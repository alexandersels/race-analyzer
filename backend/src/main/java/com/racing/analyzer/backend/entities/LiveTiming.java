package com.racing.analyzer.backend.entities;

import com.racing.analyzer.backend.commands.ICommandHandler;
import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @NotNull
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


