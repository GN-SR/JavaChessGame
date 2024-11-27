import javax.swing.*;
import java.awt.*;

public class ChessSquareComponent extends JButton {
    private final int row;
    private final int col;

    public ChessSquareComponent(int row, int col) {
        this.row = row;
        this.col = col;
        initButton();
    }

    private void initButton() {
        setPreferredSize(new Dimension(64, 64));

        // Alternate square coloring for the chessboard
        if ((row + col) % 2 == 0) {
            setBackground(Color.LIGHT_GRAY);
        } else {
            setBackground(new Color(205, 133, 63));
        }

        // Align text centrally and set font
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setFont(new Font("Serif", Font.BOLD, 36));

        // Remove extra borders and focus painting for better visuals
        setBorderPainted(false);
        setFocusPainted(false);
        setMargin(new Insets(0, 0, 0, 0));
    }

    // Method to set the piece symbol and its color
    public void setPieceSymbol(String symbol, Color color) {
        setText(symbol);
        setForeground(color);
    }

    // Method to clear the piece symbol
    public void clearPieceSymbol() {
        setText("");
    }
}
