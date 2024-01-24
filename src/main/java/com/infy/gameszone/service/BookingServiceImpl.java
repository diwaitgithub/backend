package com.infy.gameszone.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.gameszone.constants.BookingStatus;
import com.infy.gameszone.dto.BookingDTO;
import com.infy.gameszone.dto.GameDTO;
import com.infy.gameszone.dto.OptionDTO;
import com.infy.gameszone.dto.SlotDTO;
import com.infy.gameszone.entity.Booking;
import com.infy.gameszone.entity.Game;
import com.infy.gameszone.entity.Slot;
import com.infy.gameszone.entity.User;
import com.infy.gameszone.exception.GameszoneException;
import com.infy.gameszone.record.SlotAvailabilityRecord;
import com.infy.gameszone.repository.BookingRepository;
import com.infy.gameszone.repository.GameRepository;
import com.infy.gameszone.repository.SlotRepository;
import com.infy.gameszone.repository.UserRepository;

@Service(value = "bookingService")
@Transactional
public class BookingServiceImpl implements BookingService {

    @Value("${app.config.bookings.max-enabled-days:#{2}}")
    private Integer MAX_ENABLED_DAYS;

    @Value("#{T(java.time.LocalTime).parse('${app.config.bookings.opening-time:00:33}', T(java.time.format.DateTimeFormatter).ofPattern('HH:mm'))}")
    private LocalTime BOOKINGS_OPENING_TIME;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public BookingDTO createNewBooking(BookingDTO bookingDTO) throws GameszoneException {

        // check if the slot of booking game is already booked on required day or not
        // fetch slotAvailabilityRecord
        SlotAvailabilityRecord slotAvailabilityRecord = fetchSlotAvailability(bookingDTO.getSlotId(),
                bookingDTO.getGameId(), bookingDTO.getForDate());

        // if already booked throw error
        if (slotAvailabilityRecord.isBooked())
            throw new GameszoneException("BookingService.SLOT_ALREADY_BOOKED");

        // if not then book slot
        Booking booking = new Booking();

        Optional<Game> game = gameRepository.findById(bookingDTO.getGameId());

        Optional<Slot> slot = slotRepository.findById(bookingDTO.getSlotId());

        Optional<User> user = userRepository.findByUserIdWithOutPassword(bookingDTO.getUserId());

        booking.setForDate(bookingDTO.getForDate());
        booking.setTransactionDate(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.REQUESTED);
        booking.setGame(game.orElseThrow(() -> new GameszoneException("GameService.GAME_NOT_FOUND")));
        booking.setSlot(slot.orElseThrow(() -> new GameszoneException("SlotService.SLOT_NOT_FOUND")));
        booking.setUser(user.orElseThrow(() -> new GameszoneException("UserService.USER_NOT_FOUND")));

        return bookingRepository.save(booking).tDto();
    }

    @Override
    public SlotAvailabilityRecord fetchSlotAvailability(Integer slotId, Integer gameId, LocalDate forDate)
            throws GameszoneException {

        Optional<Booking> booking = bookingRepository
                .findByDateGameIdSlotIdBooked(
                        forDate,
                        gameId,
                        slotId);

        SlotDTO slot = new SlotDTO();

        Optional<Slot> optionalSlot = slotRepository.findByGameIdAndSlotId(gameId, slotId);

        if (optionalSlot.isPresent()) {
            slot.setAll(optionalSlot.get().toDto());
        } else {
            throw new GameszoneException("BookingService.SLOT_NOT_FOUND_GAMEID_SLOTID");

        }

        // availability
        SlotAvailabilityRecord slotAvailabilityRecord = new SlotAvailabilityRecord(slot, booking.isPresent(),
                forDate);

        return slotAvailabilityRecord;
    }

    @Override
    public List<SlotAvailabilityRecord> fetchGameSlotsWithAvailabilityStatus(Integer gameId, LocalDate forDate)
            throws GameszoneException {

        GameDTO gameDTO = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameszoneException("BookingService.GAME_NOT_FOUND")).toDto(true);

        List<SlotAvailabilityRecord> slotsWithAvailabilityStatus = new ArrayList<SlotAvailabilityRecord>();

        if (gameDTO.getSlots() != null && !gameDTO.getSlots().isEmpty())
            for (SlotDTO slotDTO : gameDTO.getSlots()) {
                try {
                    SlotAvailabilityRecord slotAvailabilityRecord = fetchSlotAvailability(slotDTO.getSlotId(), gameId,
                            forDate);
                    slotsWithAvailabilityStatus.add(slotAvailabilityRecord);
                } catch (Exception e) {
                    continue;
                }
            }

        return slotsWithAvailabilityStatus;
    }

    @Override
    public Page<BookingDTO> searchBookings(
            String forDate,
            String bookingStatus,
            String bookingId,
            String userId,
            String gameId,
            Integer pageNo,
            Integer resultsPerPage,
            String sort,
            List<String> includes)
            throws GameszoneException {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Pageable pageable = PageRequest.of(pageNo, resultsPerPage, Sort.by(
                sort.split("\\.")[1].equals("asc")
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC,
                sort.split("\\.")[0]));

        Page<BookingDTO> bookingsPage;

        if (forDate.equals("")) {
            bookingsPage = bookingRepository
                    .findByBookingQuery(
                            bookingStatus,
                            bookingId,
                            userId,
                            gameId,
                            pageable)
                    .map(booking -> booking.tDto(includes));
        } else {
            bookingsPage = bookingRepository
                    .findByBookingQuery(
                            LocalDate.parse(forDate, dateTimeFormatter),
                            bookingStatus,
                            bookingId,
                            userId,
                            gameId,
                            pageable)
                    .map(booking -> booking.tDto(includes));
        }

        return bookingsPage;
    }

    @Override
    public BookingDTO updateBookingStatus(Integer bookingId, BookingStatus status) throws GameszoneException {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new GameszoneException("BookingService.BOOKING_NOT_FOUND"));

        if ((!booking.getBookingStatus().equals(BookingStatus.REQUESTED)))
            throw new GameszoneException("BookingService.UNPERMITTED_OPERATION");

        switch (status) {
            case APPROVED:
                booking.setBookingStatus(BookingStatus.APPROVED);
                break;
            case REJECTED:
                booking.setBookingStatus(BookingStatus.REJECTED);
                break;
            case CANCELLED:
                if (SecurityContextHolder.getContext().getAuthentication().getName()
                        .equals(booking.getUser().getUserName())) {
                    booking.setBookingStatus(BookingStatus.CANCELLED);
                } else {
                    throw new GameszoneException("BookingService.UNAUTHORIZED_OPERATION");
                }
                break;
            default:
                throw new GameszoneException("BookingService.UNPERMITTED_OPERATION");
        }

        return booking.tDto();
    }

    @Override
    public Set<OptionDTO> getBookingEnabledDates() throws GameszoneException {

        Set<OptionDTO> options = new LinkedHashSet<OptionDTO>();

        LocalDate currenDate = LocalDate.now();

        LocalDate toBeEnabledTill = currenDate
                .plusDays(LocalTime.now().isAfter(BOOKINGS_OPENING_TIME) ? MAX_ENABLED_DAYS : MAX_ENABLED_DAYS - 1);

        currenDate.datesUntil(toBeEnabledTill).forEach((date) -> {
            options.add(new OptionDTO(date.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")),
                    date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        });

        return options;
    }

}
