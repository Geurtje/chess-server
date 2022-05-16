package com.example.chess.game.piece;

import com.example.chess.game.model.Player;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Piece {
    WHITE_PAWN(Player.WHITE, PieceType.PAWN),
    WHITE_ROOK(Player.WHITE, PieceType.ROOK),
    WHITE_KNIGHT(Player.WHITE, PieceType.KNIGHT),
    WHITE_BISHOP(Player.WHITE, PieceType.BISHOP),
    WHITE_QUEEN(Player.WHITE, PieceType.QUEEN),
    WHITE_KING(Player.WHITE, PieceType.KING),
    BLACK_PAWN(Player.BLACK, PieceType.PAWN),
    BLACK_ROOK(Player.BLACK, PieceType.ROOK),
    BLACK_KNIGHT(Player.BLACK, PieceType.KNIGHT),
    BLACK_BISHOP(Player.BLACK, PieceType.BISHOP),
    BLACK_QUEEN(Player.BLACK, PieceType.QUEEN),
    BLACK_KING(Player.BLACK, PieceType.KING);

    public final Player owner;
    public final PieceType pieceType;

    @Override
    public String toString() {
        return owner + " " + pieceType;
    }
}
