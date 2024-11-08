public class Bishop extends Piece {
    public Bishop(PieceColor color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board) {
        int rowDiff = Math.abs(position.getRow() - newPosition.getRow());
        int colDiff = Math.abs(position.getColumn() - newPosition.getColumn());

        // Bishops move diagonally, so rowDiff must equal colDiff
        if (rowDiff != colDiff) {
            return false;
        }

        // Determine the direction of movement for rows and columns
        int rowStep = newPosition.getRow() > position.getRow() ? 1 : -1;
        int colStep = newPosition.getColumn() > position.getColumn() ? 1 : -1;

        // Check each position along the path to ensure it’s empty
        for (int i = 1; i < rowDiff; i++) {
            int checkRow = position.getRow() + i * rowStep;
            int checkCol = position.getColumn() + i * colStep;

            if (board[checkRow][checkCol] != null) { // Path is blocked
                return false;
            }
        }

        // Final destination can be empty or contain an opponent's piece
        Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
        return destinationPiece == null || destinationPiece.getColor() != this.getColor();
    }
}
