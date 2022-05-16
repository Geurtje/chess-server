package com.example.chess.game.util;

import com.example.chess.game.Game;
import com.example.chess.game.piece.Piece;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The GameVisualizer is a small utility to print the status of a chess board in textual format to make
 * it easier to see what is the status of a board. Example output for a fresh board:
 *
 * <pre>
 * #######################
 * Game: test-game
 * Current player: WHITE
 * #######################
 *
 *   AㅤBㅤCㅤDㅤEㅤFㅤGㅤH
 * 8|♜|♞|♝|♛|♚|♝|♞|♜|8
 * 7|♟|♟|♟|♟|♟|♟|♟|♟|7
 * 6|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|6
 * 5|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|5
 * 4|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|4
 * 3|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|3
 * 2|♙|♙|♙|♙|♙|♙|♙|♙|2
 * 1|♖|♘|♗|♕|♔|♗|♘|♖|1
 *   AㅤBㅤCㅤDㅤEㅤFㅤGㅤH
 * </pre>
 *
 */
@UtilityClass
public class GameVisualizer {

    private static final String COLUMNS = "  AㅤBㅤCㅤDㅤEㅤFㅤGㅤH\n";

    private static final Map<Piece, String> PIECE_SYMBOL_MAP = new HashMap<>();
    static {
        PIECE_SYMBOL_MAP.put(Piece.WHITE_PAWN, "♙");
        PIECE_SYMBOL_MAP.put(Piece.BLACK_PAWN, "♟");
        PIECE_SYMBOL_MAP.put(Piece.WHITE_ROOK, "♖");
        PIECE_SYMBOL_MAP.put(Piece.BLACK_ROOK, "♜");
        PIECE_SYMBOL_MAP.put(Piece.WHITE_KNIGHT, "♘");
        PIECE_SYMBOL_MAP.put(Piece.BLACK_KNIGHT, "♞");
        PIECE_SYMBOL_MAP.put(Piece.WHITE_BISHOP, "♗");
        PIECE_SYMBOL_MAP.put(Piece.BLACK_BISHOP, "♝");
        PIECE_SYMBOL_MAP.put(Piece.WHITE_QUEEN, "♕");
        PIECE_SYMBOL_MAP.put(Piece.BLACK_QUEEN, "♛");
        PIECE_SYMBOL_MAP.put(Piece.WHITE_KING, "♔");
        PIECE_SYMBOL_MAP.put(Piece.BLACK_KING, "♚");
    }

    public static String visualizeGame(Game game) {
        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append("#######################\n")
                .append("Game: ").append(game.getId()).append("\n")
                .append("Current player: ").append(game.getCurrentPlayer()).append("\n")
                .append("#######################\n\n")
                .append(COLUMNS);

        Piece[][] board = game.getBoard();
        for (int i = board.length-1; i >= 0; i--) {
            Piece[] row = board[i];
            strBuilder.append(i+1).append("|")
                    .append(String.join("|", convertPiecesForDisplay(row)))
                    .append("|").append(i+1).append("\n");
        }

        return strBuilder.append(COLUMNS).toString();
    }

    private static Collection<String> convertPiecesForDisplay(Piece[] row) {
        return Arrays.stream(row)
                .map(piece -> piece != null ? getSymbolForPiece(piece) : "ㅤ")
                .collect(Collectors.toList());
    }

    private static String getSymbolForPiece(Piece p) {
        return PIECE_SYMBOL_MAP.computeIfAbsent(p, piece -> "<<NO SYMBOL CONFIGURED FOR " + piece + ">>");
    }
}
