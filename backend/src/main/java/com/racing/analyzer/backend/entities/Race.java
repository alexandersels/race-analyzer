package com.racing.analyzer.backend.entities;

import com.racing.analyzer.backend.dto.race.UpdateRaceDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "race")
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Race extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "recording")
    private boolean recording;

    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "race", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LiveTiming> timings;

    @Builder
    public Race(long id, String name, boolean recording, String url, List<LiveTiming> timings) {
        this.id = id;
        this.name = name;
        this.recording = recording;
        this.url = url;
        this.timings = timings;
    }

    public void update(UpdateRaceDTO updateRaceDTO) {
        if (!name.equals(updateRaceDTO.getName())) {
            name = updateRaceDTO.getName();
        }
        if (!url.equals(updateRaceDTO.getUrl())) {
            url = updateRaceDTO.getUrl();
        }
    }
}
