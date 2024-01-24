package com.infy.gameszone.record;

import java.time.LocalDate;
import java.util.Objects;

import com.infy.gameszone.dto.SlotDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

public record SlotAvailabilityRecord(SlotDTO slot, Boolean isBooked,
        @JsonFormat(pattern = "dd-MM-yyyy") LocalDate forDate) {

    public SlotAvailabilityRecord {
        Objects.requireNonNull(slot);
        Objects.requireNonNull(isBooked);
        Objects.requireNonNull(forDate);
    }
}
