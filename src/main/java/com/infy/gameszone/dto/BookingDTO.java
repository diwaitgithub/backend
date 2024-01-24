package com.infy.gameszone.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.infy.gameszone.constants.BookingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public class BookingDTO {

    private Integer bookingId;

    @NotNull(message = "{booking.bookingdate.absent}")
    @FutureOrPresent(message = "{booking.bookingdate.past}")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate forDate;

    private LocalDateTime transactionDate;

    private BookingStatus bookingStatus;

    @NotNull(message = "{booking.gameid.absent}")
    private Integer gameId;

    @NotNull(message = "{booking.slotid.absent}")
    private Integer slotId;

    @NotNull(message = "{booking.userid.absent}")
    private Integer userId;

    private GameDTO game;

    private SlotDTO slot;

    private UserDTO user;

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDate getForDate() {
        return forDate;
    }

    public void setForDate(LocalDate bookingDate) {
        this.forDate = bookingDate;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public GameDTO getGame() {
        return game;
    }

    public void setGame(GameDTO game) {
        this.game = game;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SlotDTO getSlot() {
        return slot;
    }

    public void setSlot(SlotDTO slot) {
        this.slot = slot;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BookingDTO [bookingId=" + bookingId + ", forDate=" + forDate + ", transactionDate=" + transactionDate
                + ", bookingStatus=" + bookingStatus + ", gameId=" + gameId + ", slotId=" + slotId + ", userId="
                + userId + ", game=" + game + ", slot=" + slot + ", user=" + user + "]";
    }

}
