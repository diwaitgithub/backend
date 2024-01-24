package com.infy.gameszone.entity;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.infy.gameszone.dto.SlotDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "slots")
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer slotId;

    @NotNull
    private String slotName;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    private String location;

    private Integer gameId;

    @OneToMany(mappedBy = "slot", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Booking> bookings = new HashSet<>();

    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public SlotDTO toDto() {
        SlotDTO slotDTO = new SlotDTO();
        //
        slotDTO.setSlotId(this.slotId);
        slotDTO.setSlotName(this.slotName);
        slotDTO.setStartTime(this.startTime.toString());
        slotDTO.setEndTime(this.endTime.toString());
        slotDTO.setLocation(this.location);
        slotDTO.setGameId(this.gameId);
        //
        return slotDTO;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    @Override
    public String toString() {
        return "Slot [slotId=" + slotId + ", slotName=" + slotName + ", startTime=" + startTime + ", endTime=" + endTime
                + ", location=" + location + ", gameId=" + gameId + "]";
    }

}
