package com.infy.gameszone.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.infy.gameszone.constants.BookingStatus;
import com.infy.gameszone.dto.BookingDTO;
import com.infy.gameszone.dto.OptionDTO;
import com.infy.gameszone.exception.GameszoneException;
import com.infy.gameszone.record.SlotAvailabilityRecord;

public interface BookingService {

        BookingDTO createNewBooking(BookingDTO bookingDTO) throws GameszoneException;

        BookingDTO updateBookingStatus(Integer bookingId, BookingStatus status) throws GameszoneException;

        SlotAvailabilityRecord fetchSlotAvailability(Integer slotId, Integer gameId, LocalDate forDate)
                        throws GameszoneException;

        List<SlotAvailabilityRecord> fetchGameSlotsWithAvailabilityStatus(Integer gameId, LocalDate forDate)
                        throws GameszoneException;

        Set<OptionDTO> getBookingEnabledDates() throws GameszoneException;

        Page<BookingDTO> searchBookings(
                        String forDate,
                        String bookingStatus,
                        String bookingId,
                        String userId,
                        String gameId,
                        Integer pageNo,
                        Integer resultsPerPage, String sort, List<String> includes)
                        throws GameszoneException;
}
