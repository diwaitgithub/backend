package com.infy.gameszone.service;

import org.springframework.data.domain.Page;

import com.infy.gameszone.dto.GameDTO;
import com.infy.gameszone.entity.Slot;
import com.infy.gameszone.exception.GameszoneException;

public interface GameService {

    /**
     * @param gameDTO
     * @return GameDTO
     * @see GameDTO
     * @throws GameszoneException
     */
    GameDTO createNewGame(GameDTO gameDTO) throws GameszoneException;

    /**
     * @param gameId
     * @param includeSlots
     * @return GameDTO
     * @see GameDTO
     * @throws GameszoneException
     */
    GameDTO getGame(Integer gameId, boolean includeSlots) throws GameszoneException;

    /**
     * @param gameName
     * @param includeSlots
     * @return GameDTO
     * @see GameDTO
     * @throws GameszoneException
     */
    GameDTO getGame(String gameName, boolean includeSlots) throws GameszoneException;

    /**
     * @param gameName
     * @param pageNo
     * @param resultsPerPage
     * @param includeSlots
     * @return Page<GameDTO>
     * @see GameDTO
     * @throws GameszoneException
     */
    Page<GameDTO> searchGames(String gameName, Integer pageNo, Integer resultsPerPage, String sort,
            boolean includeSlots)
            throws GameszoneException;

    /**
     * @param gameId
     * @param newGameName
     * @return GameDTO
     * @see GameDTO
     * @throws GameszoneException
     */
    GameDTO updateGameNameById(Integer gameId, String newGameName) throws GameszoneException;

    /**
     * @param gameId
     * @param newGameImage
     * @return GameDTO
     * @see GameDTO
     * @throws GameszoneException
     */
    GameDTO updateGameImageById(Integer gameId, String newGameImage) throws GameszoneException;

    /**
     * @param gameId
     * @param newSlot
     * @throws GameszoneException
     */
    void addGameSlot(Integer gameId, Slot newSlot) throws GameszoneException;

    void deleteGame(Integer gameId) throws GameszoneException;
}
