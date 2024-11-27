public class Position {
    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isValid() {
        return row >= 0 && row < 8 && column >= 0 && column < 8;
    }

    public Position add(int deltaRow, int deltaColumn) {
        return new Position(row + deltaRow, column + deltaColumn);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return 31 * row + column;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }

    public String toChessNotation() {
        char columnChar = (char) ('a' + column);
        int rowNumber = 8 - row;
        return "" + columnChar + rowNumber;
    }

    public static Position fromChessNotation(String notation) {
        if (notation == null || notation.length() != 2) {
            throw new IllegalArgumentException("Invalid chess notation");
        }
        char columnChar = notation.charAt(0);
        char rowChar = notation.charAt(1);

        int column = columnChar - 'a';
        int row = 8 - Character.getNumericValue(rowChar);

        if (row < 0 || row >= 8 || column < 0 || column >= 8) {
            throw new IllegalArgumentException("Invalid chessboard position");
        }
        return new Position(row, column);
    }
}
