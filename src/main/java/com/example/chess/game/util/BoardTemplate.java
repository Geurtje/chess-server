package com.example.chess.game.util;

import com.example.chess.game.piece.Piece;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BoardTemplate {

    public static Piece[][] createNewBoard() {
        return new Piece[][]{
                new Piece[]{
                        Piece.WHITE_ROOK,
                        Piece.WHITE_KNIGHT,
                        Piece.WHITE_BISHOP,
                        Piece.WHITE_QUEEN,
                        Piece.WHITE_KING,
                        Piece.WHITE_BISHOP,
                        Piece.WHITE_KNIGHT,
                        Piece.WHITE_ROOK
                },
                new Piece[]{
                        Piece.WHITE_PAWN,
                        Piece.WHITE_PAWN,
                        Piece.WHITE_PAWN,
                        Piece.WHITE_PAWN,
                        Piece.WHITE_PAWN,
                        Piece.WHITE_PAWN,
                        Piece.WHITE_PAWN,
                        Piece.WHITE_PAWN
                },
                new Piece[8],
                new Piece[8],
                new Piece[8],
                new Piece[8],
                new Piece[]{
                        Piece.BLACK_PAWN,
                        Piece.BLACK_PAWN,
                        Piece.BLACK_PAWN,
                        Piece.BLACK_PAWN,
                        Piece.BLACK_PAWN,
                        Piece.BLACK_PAWN,
                        Piece.BLACK_PAWN,
                        Piece.BLACK_PAWN
                },
                new Piece[]{
                        Piece.BLACK_ROOK,
                        Piece.BLACK_KNIGHT,
                        Piece.BLACK_BISHOP,
                        Piece.BLACK_QUEEN,
                        Piece.BLACK_KING,
                        Piece.BLACK_BISHOP,
                        Piece.BLACK_KNIGHT,
                        Piece.BLACK_ROOK
                }
        };
    }
}
