package com.example.chess.game.http;

import com.example.chess.game.GameService;
import com.example.chess.game.exception.ChessException;
import com.example.chess.game.Game;
import com.example.chess.game.model.MovePieceResult;
import com.example.chess.game.util.GameVisualizer;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/chess/game/")
@AllArgsConstructor
public class GameRestController {

    private GameService gameService;

    @RequestMapping(
            method = RequestMethod.POST,
            path = "create",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Game createNewGame() {
        return gameService.startNewGame();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{gameId}/state",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Game getGameState(@PathVariable String gameId) throws ChessException {
        return gameService.getGameState(gameId);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{gameId}/visualize",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ResponseBody
    public String visualizeGame(@PathVariable String gameId) throws ChessException {
        return GameVisualizer.visualizeGame(gameService.getGameState(gameId));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{gameId}/moves",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Collection<String> getPieceMoves(@PathVariable String gameId, @RequestParam("location") String location) throws ChessException {
        return gameService.getPossibleMovesForLocation(gameId, location);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/{gameId}/move",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public MovePieceResult movePiece(@PathVariable String gameId, @RequestBody MovePieceRequest request) throws ChessException {
        return gameService.movePiece(gameId, request.locationFrom, request.locationTo);
    }
}
