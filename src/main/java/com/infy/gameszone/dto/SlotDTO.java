package com.infy.gameszone.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SlotDTO {

    private Integer slotId;

    @NotNull(message = "{slot.slotname.absent}")
    @Size(max = 16, min = 2, message = "{slot.slotname.invalid}")
    private String slotName;

    @NotNull(message = "{slot.starttime.absent}")
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?$", message = "{slot.starttime.invalid}")
    @Temporal(TemporalType.TIME)
    private String startTime;

    @NotNull(message = "{slot.endtime.absent}")
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?$", message = "{slot.endtime.invalid}")
    @Temporal(TemporalType.TIME)
    private String endTime;

    private String location;

    private String gameId;

    public void setAll(SlotDTO slot) {
        setSlotId(slot.getSlotId());
        setSlotName(slot.getSlotName());
        setStartTime(slot.getStartTime().toString());
        setEndTime(slot.getEndTime().toString());
        setLocation(slot.getLocation());
        setGameId(slot.getGameId());
    }

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
        return convertStringToLocalTime(startTime);
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return convertStringToLocalTime(endTime);
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getGameId() {
        return (gameId == null) ? null : Integer.parseInt(gameId);
    }

    public void setGameId(Integer gameId) {
        this.gameId = (gameId == null) ? null : gameId.toString();

    }

    public LocalTime convertStringToLocalTime(String time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm[:ss]");
        LocalTime formatedLocalTime = LocalTime.parse(time.toString(), dateTimeFormatter);
        return formatedLocalTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((slotId == null) ? 0 : slotId.hashCode());
        result = prime * result + ((slotName == null) ? 0 : slotName.hashCode());
        result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
        result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((gameId == null) ? 0 : gameId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SlotDTO other = (SlotDTO) obj;
        if (slotId == null) {
            if (other.slotId != null)
                return false;
        } else if (!slotId.equals(other.slotId))
            return false;
        if (slotName == null) {
            if (other.slotName != null)
                return false;
        } else if (!slotName.equals(other.slotName))
            return false;
        if (startTime == null) {
            if (other.startTime != null)
                return false;
        } else if (!startTime.equals(other.startTime))
            return false;
        if (endTime == null) {
            if (other.endTime != null)
                return false;
        } else if (!endTime.equals(other.endTime))
            return false;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (gameId == null) {
            if (other.gameId != null)
                return false;
        } else if (!gameId.equals(other.gameId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SlotDTO [slotId=" + slotId + ", slotName=" + slotName + ", startTime=" + startTime + ", endTime="
                + endTime + ", location=" + location + ", gameId=" + gameId + "]";
    }

}
