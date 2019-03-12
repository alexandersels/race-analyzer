package com.racing.analyzer.backend.dto;

public class RoundDTO {

    public long lapTime;
    public boolean inPit;
    public long sectorOneTime;
    public long sectorTwoTime;
    public long sectorThreeTime;

    @Override
    public boolean equals(Object o) {

        if (o == null) {
            return false;
        }

        if (!(o instanceof RoundDTO)) {
            return false;
        }

        RoundDTO dto = (RoundDTO) o;

        if (lapTime != dto.lapTime) {
            return false;
        } else if (inPit != dto.inPit) {
            return false;
        } else if (sectorOneTime != dto.sectorOneTime) {
            return false;
        } else if (sectorTwoTime != dto.sectorTwoTime) {
            return false;
        } else if (sectorThreeTime != dto.sectorThreeTime) {
            return false;
        } else {
            return true;
        }

    }
}
