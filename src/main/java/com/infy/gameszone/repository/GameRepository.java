package com.infy.gameszone.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.infy.gameszone.entity.Game;
import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {
    Optional<Game> findByGameName(String gameName);

    @Query(value = "SELECT g FROM Game g WHERE g.gameName LIKE %:query%", countQuery = "SELECT count(g) FROM Game g WHERE g.gameName LIKE %:query%")
    Page<Game> searchGameByName(@Param("query") String nameQuery, Pageable pageable);
}
