package com.infy.gameszone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.infy.gameszone.entity.Slot;

@Repository
public interface SlotRepository extends CrudRepository<Slot, Integer> {

    @Query(value = "SELECT * FROM slots s WHERE s.game_id = :gameId", nativeQuery = true)
    List<Slot> findByGameId(@Param("gameId") Integer gameId);

    @Query(value = "SELECT * FROM slots s WHERE s.game_id = :gameId AND s.slot_id = :slotId", nativeQuery = true)
    Optional<Slot> findByGameIdAndSlotId(@Param("gameId") Integer gameId, @Param("slotId") Integer slotId);

    @Query(value = "SELECT s FROM Slot s WHERE s.gameId = :gameId AND s.slotName LIKE %:nameQuery% AND s.location LIKE %:locationQuery%", countQuery = "SELECT COUNT(s) FROM Slot s WHERE s.gameId = :gameId AND s.slotName LIKE %:nameQuery% AND s.location LIKE %:locationQuery%")
    Page<Slot> findBySlotQuery(
            @Param("gameId") Integer gameId,
            @Param("nameQuery") String nameQuery,
            @Param("locationQuery") String locationQuery,
            Pageable pageable);
}
