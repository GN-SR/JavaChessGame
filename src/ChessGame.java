import java.util.List;
import java.util.ArrayList;

public class ChessGame {
    private ChessBoard board;
    private boolean whiteTurn = true; // White starts the game
    private Position selectedPosition;

    public ChessGame() {
        this.board = new ChessBoard();
    }

    public ChessBoard getBoard() {
        return this.board;
    }

    public void resetGame() {
        this.board = new ChessBoard();
        this.whiteTurn = true;
    }

    public PieceColor getCurrentPlayerColor() {
        return whiteTurn ? PieceColor.WHITE : PieceColor.BLACK;
    }

    public boolean isPieceSelected() {
        return selectedPosition != null;
    }

    public boolean handleSquareSelection(int row, int col) {
        if (selectedPosition == null) {
            Piece selectedPiece = board.getPiece(row, col);
            if (selectedPiece != null && selectedPiece.getColor() == getCurrentPlayerColor()) {
                selectedPosition = new Position(row, col);
                return false;
            }
        } else {
            Position targetPosition = new Position(row, col);
            boolean moveMade = makeMove(selectedPosition, targetPosition);
            selectedPosition = null;
            return moveMade;
        }
        return false;
    }

    public boolean makeMove(Position start, Position end) {
        Piece movingPiece = board.getPiece(start.getRow(), start.getColumn());

        // Ensure the piece belongs to the current player
        if (movingPiece == null || movingPiece.getColor() != getCurrentPlayerColor()) {
            return false;
        }

        // Simulate the move
        Piece temp = board.getPiece(end.getRow(), end.getColumn());
        board.setPiece(end.getRow(), end.getColumn(), movingPiece);
        board.setPiece(start.getRow(), start.getColumn(), null);

        // Check if the move leaves the king in check
        if (isInCheck(movingPiece.getColor())) {
            // Undo the move if it leaves the king in check
            board.setPiece(start.getRow(), start.getColumn(), movingPiece);
            board.setPiece(end.getRow(), end.getColumn(), temp);
            return false;
        }

        // Finalize the move
        whiteTurn = !whiteTurn;
        return true;
    }

    public boolean isInCheck(PieceColor kingColor) {
        Position kingPosition = findKingPosition(kingColor);
        for (int row = 0; row < board.getBoard().length; row++) {
            for (int col = 0; col < board.getBoard()[row].length; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null && piece.getColor() != kingColor) {
                    if (piece.isValidMove(kingPosition, board.getBoard())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Position findKingPosition(PieceColor color) {
        for (int row = 0; row < board.getBoard().length; row++) {
            for (int col = 0; col < board.getBoard()[row].length; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece instanceof King && piece.getColor() == color) {
                    return new Position(row, col);
                }
            }
        }
        throw new RuntimeException("King not found, which should never happen.");
    }

    public boolean isCheckmate(PieceColor kingColor) {
        if (!isInCheck(kingColor)) {
            return false;
        }

        // Check if the king can move to a safe square
        Position kingPosition = findKingPosition(kingColor);
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                if (rowOffset == 0 && colOffset == 0) continue;
                Position newPosition = kingPosition.add(rowOffset, colOffset);
                if (newPosition.isValid() && board.getPiece(kingPosition.getRow(), kingPosition.getColumn()).isValidMove(newPosition, board.getBoard())
                        && !wouldBeInCheckAfterMove(kingColor, kingPosition, newPosition)) {
                    return false;
                }
            }
        }

        // Check if any piece can block or capture the attacker
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null && piece.getColor() == kingColor) {
                    List<Position> legalMoves = getLegalMovesForPieceAt(new Position(row, col));
                    for (Position move : legalMoves) {
                        if (!wouldBeInCheckAfterMove(kingColor, new Position(row, col), move)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true; // No valid moves, checkmate
    }


    private boolean isPositionOnBoard(Position position) {
        return position.getRow() >= 0 && position.getRow() < board.getBoard().length &&
                position.getColumn() >= 0 && position.getColumn() < board.getBoard()[0].length;
    }

    private boolean wouldBeInCheckAfterMove(PieceColor kingColor, Position from, Position to) {
        Piece temp = board.getPiece(to.getRow(), to.getColumn());
        board.setPiece(to.getRow(), to.getColumn(), board.getPiece(from.getRow(), from.getColumn()));
        board.setPiece(from.getRow(), from.getColumn(), null);

        boolean inCheck = isInCheck(kingColor);

        board.setPiece(from.getRow(), from.getColumn(), board.getPiece(to.getRow(), to.getColumn()));
        board.setPiece(to.getRow(), to.getColumn(), temp);

        return inCheck;
    }

    public List<Position> getLegalMovesForPieceAt(Position position) {
        Piece selectedPiece = board.getPiece(position.getRow(), position.getColumn());
        if (selectedPiece == null) {
            return new ArrayList<>();
        }

        List<Position> legalMoves = new ArrayList<>();
        for (int row = 0; row < board.getBoard().length; row++) {
            for (int col = 0; col < board.getBoard()[row].length; col++) {
                Position target = new Position(row, col);
                if (makeMove(position, target)) {
                    legalMoves.add(target);
                    makeMove(target, position); // Undo the move
                }
            }
        }
        return legalMoves;
    }
}
