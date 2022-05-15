package com.example.chess.game;

import com.example.chess.game.exception.ChessException;
import com.example.chess.game.model.Game;
import com.example.chess.game.model.MovePieceRequest;
import com.example.chess.game.model.MovePieceResult;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chess/game/")
@AllArgsConstructor
public class GameController {

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
    public String visualizeGameState(@PathVariable String gameId) throws ChessException {
        return GameStateVisualizer.visualizeGameBoard(gameService.getGameState(gameId));
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
