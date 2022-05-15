package com.example.chess.game.model;

import com.example.chess.piece.Piece;
import com.example.chess.piece.PieceType;
import lombok.Getter;

@Getter
public class Game {

    private final String id;
    private Player currentPlayer;
    private final Piece[][] board;

    public Game(String gameId) {
        this.id = gameId;
        this.board = newBoard();
        this.currentPlayer = Player.WHITE;
    }

    public void rotateCurrentPlayer() {
        if (currentPlayer == Player.WHITE) {
            currentPlayer = Player.BLACK;
        }
        else {
            currentPlayer = Player.WHITE;
        }
    }

    private static Piece[][] newBoard() {
        return new Piece[][]{
                new Piece[]{
                        new Piece(PieceType.ROOK, Player.WHITE),
                        new Piece(PieceType.KNIGHT, Player.WHITE),
                        new Piece(PieceType.BISHOP, Player.WHITE),
                        new Piece(PieceType.QUEEN, Player.WHITE),
                        new Piece(PieceType.KING, Player.WHITE),
                        new Piece(PieceType.BISHOP, Player.WHITE),
                        new Piece(PieceType.KNIGHT, Player.WHITE),
                        new Piece(PieceType.ROOK, Player.WHITE)
                },
                new Piece[]{
                        new Piece(PieceType.PAWN, Player.WHITE),
                        new Piece(PieceType.PAWN, Player.WHITE),
                        new Piece(PieceType.PAWN, Player.WHITE),
                        new Piece(PieceType.PAWN, Player.WHITE),
                        new Piece(PieceType.PAWN, Player.WHITE),
                        new Piece(PieceType.PAWN, Player.WHITE),
                        new Piece(PieceType.PAWN, Player.WHITE),
                        new Piece(PieceType.PAWN, Player.WHITE)
                },
                new Piece[8],
                new Piece[8],
                new Piece[8],
                new Piece[8],
                new Piece[]{
                        new Piece(PieceType.PAWN, Player.BLACK),
                        new Piece(PieceType.PAWN, Player.BLACK),
                        new Piece(PieceType.PAWN, Player.BLACK),
                        new Piece(PieceType.PAWN, Player.BLACK),
                        new Piece(PieceType.PAWN, Player.BLACK),
                        new Piece(PieceType.PAWN, Player.BLACK),
                        new Piece(PieceType.PAWN, Player.BLACK),
                        new Piece(PieceType.PAWN, Player.BLACK)
                },
                new Piece[]{
                        new Piece(PieceType.ROOK, Player.BLACK),
                        new Piece(PieceType.KNIGHT, Player.BLACK),
                        new Piece(PieceType.BISHOP, Player.BLACK),
                        new Piece(PieceType.QUEEN, Player.BLACK),
                        new Piece(PieceType.KING, Player.BLACK),
                        new Piece(PieceType.BISHOP, Player.BLACK),
                        new Piece(PieceType.KNIGHT, Player.BLACK),
                        new Piece(PieceType.ROOK, Player.BLACK)
                }
        };
    }

}
