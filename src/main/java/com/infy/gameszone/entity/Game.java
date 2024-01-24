package com.infy.gameszone.entity;

import java.util.ArrayList;
import java.util.List;

import com.infy.gameszone.dto.GameDTO;
import com.infy.gameszone.dto.SlotDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameId;

    @NotNull
    @Column(unique = true)
    private String gameName;

    private String image;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gameId")
    private List<Slot> slots;

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

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public GameDTO toDto() {
        //
        GameDTO gameDTO = new GameDTO();
        //
        gameDTO.setGameId(this.gameId);
        gameDTO.setGameName(this.gameName);
        gameDTO.setImage(this.image);
        //
        return gameDTO;
    }

    public GameDTO toDto(boolean includeSlots) {
        //
        GameDTO gameDTO = new GameDTO();
        //
        List<SlotDTO> slotDTOs = new ArrayList<SlotDTO>();
        //
        gameDTO.setGameId(this.gameId);
        gameDTO.setGameName(this.gameName);
        gameDTO.setImage(this.image);
        //
        if (this.slots != null && includeSlots)
            this.slots.stream().forEach(slot -> slotDTOs.add(slot.toDto()));
        //
        if (includeSlots)
            gameDTO.setSlots(slotDTOs);
        //
        return gameDTO;
    }

    @Override
    public String toString() {
        return "Game [gameId=" + gameId + ", gameName=" + gameName + ", image=" + image + ", slots=" + slots + "]";
    }

}
