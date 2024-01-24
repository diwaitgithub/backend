package com.infy.gameszone.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GameDTO {

    private Integer gameId;

    @NotNull(message = "{game.gamename.absent}")
    @Size(max = 25, min = 3, message = "{game.gamename.invalid}")
    private String gameName;

    private String image;

    private List<SlotDTO> slots;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<SlotDTO> getSlots() {
        return slots;
    }

    public void setSlots(List<SlotDTO> slotDTOs) {
        this.slots = slotDTOs;
    }

    @Override
    public String toString() {
        return "GameDTO [gameId=" + gameId + ", gameName=" + gameName + ", image=" + image + ", slots=" + slots + "]";
    }

}
