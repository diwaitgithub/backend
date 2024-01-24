package com.infy.gameszone.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.gameszone.dto.GameDTO;
import com.infy.gameszone.entity.Game;
import com.infy.gameszone.entity.Slot;
import com.infy.gameszone.exception.GameszoneException;
import com.infy.gameszone.repository.GameRepository;

@Service(value = "gameService")
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public GameDTO createNewGame(GameDTO gameDTO) throws GameszoneException {

        // Check if Game with same name already exists
        Optional<Game> gameNameExist = gameRepository.findByGameName(gameDTO.getGameName());
        if (gameNameExist.isPresent()) {
            throw new GameszoneException("GameService.GAMENAME_ALREADY_EXIST");
        }

        // if not exits then create new game
        Game game = new Game();
        //
        game.setGameName(gameDTO.getGameName());
        game.setImage(gameDTO.getImage());
        //
        return gameRepository.save(game).toDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameDTO getGame(Integer gameId, boolean includeSlots) throws GameszoneException {
        //
        Optional<Game> game = gameRepository.findById(gameId);
        //
        if (game.isPresent()) {
            //
            return game.get().toDto(includeSlots);
        } else {
            //
            throw new GameszoneException("GameService.GAME_NOT_FOUND");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameDTO getGame(String gameName, boolean includeSlots) throws GameszoneException {
        //
        Optional<Game> game = gameRepository.findByGameName(gameName);
        //
        if (game.isPresent()) {
            return game.get().toDto(includeSlots);
        } else {
            //
            throw new GameszoneException("GameService.GAME_NOT_FOUND");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<GameDTO> searchGames(String gameName, Integer pageNo, Integer resultsPerPage, String sort,
            boolean includeSlots)
            throws GameszoneException {

        Pageable pageable = PageRequest.of(
                pageNo,
                resultsPerPage,
                Sort.by(
                        sort.split("\\.")[1].equals("asc")
                                ? Sort.Direction.ASC
                                : Sort.Direction.DESC,
                        sort.split("\\.")[0]));

        Page<GameDTO> gamesPage = gameRepository.searchGameByName(gameName, pageable)
                .map(game -> game.toDto(includeSlots));

        return gamesPage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameDTO updateGameNameById(Integer gameId, String newGameName) throws GameszoneException {

        // Check if GameName already exits
        Optional<Game> gameNameExits = gameRepository.findByGameName(newGameName);
        //
        if (gameNameExits.isPresent() && !(gameNameExits.get().getGameId().equals(gameId))) {
            throw new GameszoneException("GameService.GAMENAME_ALREADY_EXIST");
        }
        //
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        //
        Game gameToBeUpdated = optionalGame.orElseThrow(() -> new GameszoneException("GameService.GAMEID_NOT_FOUND"));
        //
        gameToBeUpdated.setGameName(newGameName);
        //
        return gameToBeUpdated.toDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameDTO updateGameImageById(Integer gameId, String newGameImage) throws GameszoneException {
        //
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        //
        Game gameToBeUpdated = optionalGame.orElseThrow(() -> new GameszoneException("GameService.GAMEID_NOT_FOUND"));
        //
        gameToBeUpdated.setImage(newGameImage);
        //
        return gameToBeUpdated.toDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addGameSlot(Integer gameId, Slot newSlot) throws GameszoneException {
        //
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        //
        Game game = optionalGame.orElseThrow(() -> new GameszoneException("GameService.GAME_NOT_FOUND"));
        //
        game.getSlots().add(newSlot);
    }

    @Override
    public void deleteGame(Integer gameId) throws GameszoneException {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameszoneException("GameService.GAME_NOT_FOUND"));

        gameRepository.delete(game);
    }

}
