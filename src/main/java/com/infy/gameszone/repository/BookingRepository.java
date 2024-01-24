package com.infy.gameszone.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.infy.gameszone.entity.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {

        @Query(value = "SELECT * FROM bookings b WHERE b.for_date = ?1 AND b.game_id = ?2 AND b.slot_id = ?3 AND b.booking_status IN ('APPROVED', 'REQUESTED')", nativeQuery = true)
        Optional<Booking> findByDateGameIdSlotIdBooked(LocalDate forDate, Integer gameId, Integer slotId);

        @Query(value = "SELECT * FROM bookings b WHERE b.for_date = ?1 AND b.game_id = ?2 AND b.slot_id = ?3", nativeQuery = true)
        List<Booking> findByQuery(LocalDate forDate, Integer gameId, Integer slotId);

        @Query(value = "SELECT * FROM bookings b WHERE b.for_date = ?1 AND b.game_id = ?2 AND b.slot_id = ?3 AND b.booking_status IN(?4)", nativeQuery = true)
        List<Booking> findByQuery(
                        LocalDate forDate,
                        Integer gameId,
                        Integer slotId,
                        List<String> status);

        @Query(value = "SELECT * FROM bookings b WHERE b.booking_id LIKE %:bookingId% AND b.for_date LIKE %:forDate% AND b.booking_status LIKE %:bookingStatus% AND b.user_id LIKE %:userId% AND b.game_id LIKE %:gameId%", countQuery = "SELECT count(*) FROM bookings b WHERE b.booking_id LIKE %:bookingId% AND b.for_date LIKE %:forDate% AND b.booking_status LIKE %:bookingStatus% AND b.user_id LIKE %:userId% AND b.game_id LIKE %:gameId%", nativeQuery = true)
        Page<Booking> findByBookingQuery(
                        @Param("forDate") LocalDate forDate,
                        @Param("bookingStatus") String bookingStatus,
                        @Param("bookingId") String bookingId,
                        @Param("userId") String userId,
                        @Param("gameId") String gameId,
                        Pageable pageable);

        @Query(value = "SELECT * FROM bookings b WHERE b.booking_id LIKE %:bookingId% AND b.booking_status LIKE %:bookingStatus% AND b.user_id LIKE %:userId% AND b.game_id LIKE %:gameId%", countQuery = "SELECT count(*) FROM bookings b WHERE b.booking_id LIKE %:bookingId% AND b.booking_status LIKE %:bookingStatus% AND b.user_id LIKE %:userId% AND b.game_id LIKE %:gameId%", nativeQuery = true)
        Page<Booking> findByBookingQuery(
                        @Param("bookingStatus") String bookingStatus,
                        @Param("bookingId") String bookingId,
                        @Param("userId") String userId,
                        @Param("gameId") String gameId,
                        Pageable pageable);

}
