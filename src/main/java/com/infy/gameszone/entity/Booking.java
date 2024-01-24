package com.infy.gameszone.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.infy.gameszone.constants.BookingStatus;
import com.infy.gameszone.dto.BookingDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;

    @NotNull
    private LocalDate forDate;

    @NotNull
    private LocalDateTime transactionDate;

    @NotNull
    @Column(columnDefinition = "ENUM('APPROVED', 'REQUESTED','REJECTED','CANCELLED') default 'REQUESTED'")
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "gameId")
    private Game game;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "slotId")
    private Slot slot;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookingDTO tDto() {

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setBookingId(bookingId);
        bookingDTO.setForDate(forDate);
        bookingDTO.setTransactionDate(transactionDate);
        bookingDTO.setBookingStatus(bookingStatus);
        bookingDTO.setGameId(game.getGameId());
        bookingDTO.setSlotId(slot.getSlotId());
        bookingDTO.setUserId(user.getUserId());
        bookingDTO.setGame(game.toDto());
        bookingDTO.setSlot(slot.toDto());
        bookingDTO.setUser(user.toDto());

        return bookingDTO;
    }

    public BookingDTO tDto(List<String> includes) {
        if (includes != null)
            includes.stream().map(string -> string.toLowerCase());

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setBookingId(bookingId);
        bookingDTO.setForDate(forDate);
        bookingDTO.setTransactionDate(transactionDate);
        bookingDTO.setBookingStatus(bookingStatus);
        bookingDTO.setGameId(game.getGameId());
        bookingDTO.setSlotId(slot.getSlotId());
        bookingDTO.setUserId(user.getUserId());

        if (includes != null && includes.contains("game"))
            bookingDTO.setGame(game.toDto());

        if (includes != null && includes.contains("slot"))
            bookingDTO.setSlot(slot.toDto());

        if (includes != null && includes.contains("user"))
            bookingDTO.setUser(user.toDto());

        return bookingDTO;
    }
}
