import java.awt.Color;
import java.awt.Graphics;

/**
 * The Board class models the ROWS-by-COLS game-board.
 */
public class Board {
    // Package access
    // Composes of 2D array of ROWS-by-COLS Cell instances
    Cell[][] cells;

    /** Constructor to initialize the game board */
    public Board() {
        // Allocate the array
        cells = new Cell[GameMain.ROWS][GameMain.COLS];
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                // Allocate element of array
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    /** Initialize (or re-initialize) the game board */
    public void init() {
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                // Clear the cell content
                cells[row][col].clear();
            }
        }
    }

    /** Return true if it is a draw (i.e., no more EMPTY cell) */
    public boolean isDraw() {
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                if (cells[row][col].content == Seed.EMPTY) {
                    // An empty seed found, not a draw, exit
                    return false;
                }
            }
        }
        return true; // No empty cell, it's a draw
    }

    /** Return true if the player with "seed" has won after placing at (seedRow, seedCol) */
    public boolean hasWon(Seed seed, int seedRow, int seedCol) {
        return (cells[seedRow][0].content == seed && cells[seedRow][1].content == seed
                && cells[seedRow][2].content == seed || cells[0][seedCol].content == seed
                && cells[1][seedCol].content == seed && cells[2][seedCol].content == seed
                || seedRow == seedCol && cells[0][0].content == seed && cells[1][1].content == seed
                && cells[2][2].content == seed || seedRow + seedCol == 2 && cells[0][2].content == seed
                && cells[1][1].content == seed && cells[2][0].content == seed);
    }

    /** Paint itself on the graphics canvas, given the Graphics context */
    public void paint(Graphics g) {
        // Draw the grid-lines
        g.setColor(Color.GRAY);
        for (int row = 1; row < GameMain.ROWS; ++row) {
            g.fillRect(0, GameMain.CELL_SIZE * row - GameMain.GRID_WIDTH_HALF, GameMain.CANVAS_WIDTH - 1,
                    GameMain.GRID_WIDTH);
        }
        for (int col = 1; col < GameMain.COLS; ++col) {
            g.fillRect(GameMain.CELL_SIZE * col - GameMain.GRID_WIDTH_HALF, 0, GameMain.GRID_WIDTH,
                    GameMain.CANVAS_HEIGHT - 1);
        }

        // Draw all the cells
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col].paint(g); // Ask the cell to paint itself
            }
        }
    }
}
