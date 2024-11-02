public class ChessGame {
    private ChessBoard board;
    private boolean whiteTurn = true;

    public ChessGame(){
        this.board = new ChessBoard();
    }
    public boolean makeMove(Position start, Position end){
        Piece movingPiece = board.getPiece(start.getRow(), start.getColumn());
        if(movingPiece == null || movingPiece.getColor() != (whiteTurn ? PieceColor.WHITE : PieceColor.BLACK)){
            return false;
        }
        if(movingPiece.isValidMove(end, board.getBoard())){
            board.movePiece(start, end);
            whiteTurn = !whiteTurn;
            return true;
        }
        return false;
    }
}