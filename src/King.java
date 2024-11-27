public class King extends Piece {
    public King(PieceColor color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board) {
        int rowDiff = Math.abs(position.getRow() - newPosition.getRow());
        int colDiff = Math.abs(position.getColumn() - newPosition.getColumn());

        // Ensure the move is only one square in any direction
        boolean isOneSquareMove = rowDiff <= 1 && colDiff <= 1 && !(rowDiff == 0 && colDiff == 0);
        if (!isOneSquareMove) {
            return false;
        }

        // Check if the destination square is occupied by a friendly piece
        Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
        if (destinationPiece != null && destinationPiece.getColor() == this.getColor()) {
            return false;
        }

        // Ensure the destination square is not under attack
        return !isSquareUnderAttack(newPosition, board);
    }

    private boolean isSquareUnderAttack(Position position, Piece[][] board) {
        PieceColor opponentColor = this.color == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;

        // Check every piece on the board
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColor() == opponentColor) {
                    if (piece.isValidMove(position, board)) {
                        return true; // Square is under attack
                    }
                }
            }
        }

        return false; // Square is safe
    }
}
