
Mempelajari kode yang telah dibuat:
Analisis Kode:

Struktur Proyek:
Proyek terdiri dari beberapa kelas terpisah, yang masing-masing memiliki tanggung jawabnya sendiri (State, Seed, GameState, Cell, Board, dan GameMain).

Enum (State, Seed, dan GameState):
Penggunaan enum memungkinkan definisi konstan untuk state permainan (State), isi sel (Seed), dan status permainan (GameState).

Kelas Cell:
Mewakili satu sel dalam papan permainan.
Dapat menggambar isi sel (X atau O) pada canvas.

Kelas Board:
Mewakili papan permainan Tic-Tac-Toe.
Menyediakan metode untuk menginisialisasi, menggambar papan permainan, memeriksa apakah ada pemenang, dan apakah permainan berakhir seri (draw).

Kelas GameMain:
Kelas ini menggabungkan komponen-komponen permainan.
Mengatur GUI, menerima input dari pengguna melalui mouse, dan menanggapi aksi-aksi permainan.
Memantau status permainan (siapa giliran, apakah ada pemenang, atau apakah permainan seri).


Kesimpulan:
Penggunaan Enum:
Pemilihan menggunakan enum untuk merepresentasikan state dan isi sel sangat tepat. Enum memungkinkan Anda mendefinisikan set terbatas dari nilai-nilai konstan, yang mempermudah pemahaman dan pemeliharaan kode.

Desain Terpisah:
Desain kode yang memisahkan fungsionalitas ke dalam kelas-kelas terpisah memudahkan pemahaman dan pemeliharaan kode. Setiap kelas memiliki tanggung jawab spesifiknya sendiri.

Antarmuka Pengguna:
Antarmuka pengguna permainan (GUI) diimplementasikan dengan baik menggunakan Java Swing. Papan permainan dan pesan status ditampilkan dengan jelas kepada pengguna.

Interaktivitas:
Permainan memungkinkan interaksi pengguna melalui klik mouse. Ketika pengguna memilih sebuah sel, permainan merespons dengan memperbarui papan permainan dan menampilkan status terkini.

Perbaikan Potensial:
Satu area yang dapat diperbaiki adalah memberikan lebih banyak umpan balik kepada pengguna, misalnya, dengan menampilkan pesan konfirmasi ketika permainan berakhir (siapa yang menang atau jika seri). Hal ini akan memberikan pengalaman pengguna yang lebih baik.
Dengan demikian, kode tersebut merupakan implementasi yang baik dari permainan Tic-Tac-Toe dalam bahasa Java, memanfaatkan konsep-konsep dasar pemrograman objek dan antarmuka pengguna grafis. Ada potensi untuk memperluas dan meningkatkan permainan ini dengan penambahan fitur atau peningkatan antarmuka pengguna.

State.java:
// State.java



/**

 * Enumeration for the various states of the game

 */

public enum State {

    PLAYING, DRAW, CROSS_WON, NOUGHT_WON

}


Seed.java:
/**
 * Enumeration for the seeds and cell contents
 */
public enum Seed {
    // to save as "Seed.java"
    EMPTY, CROSS, NOUGHT
}


GameState.java:
// GameState.java

/**
 * Enumeration for the various states of the game
 */
public enum GameState {
    PLAYING, DRAW, CROSS_WON, NOUGHT_WON
}


Cell.java:
import java.awt.*;

public class Cell {
    // Content of this cell (Seed.EMPTY, Seed.CROSS, or Seed.NOUGHT)
    Seed content;
    int row, col; // Row and column of this cell

    /** Constructor to initialize this cell with the specified row and col */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        clear(); // Clear content
    }

    /** Clear this cell's content to EMPTY */
    public void clear() {
        content = Seed.EMPTY;
    }

    /** Paint itself on the graphics canvas, given the Graphics context */
    public void paint(Graphics g) {
        // Use Graphics2D which allows us to set the pen's stroke
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(GameMain.SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); // Graphics2D only

        // Draw the Seed if it is not empty
        int x1 = col * GameMain.CELL_SIZE + GameMain.CELL_PADDING;
        int y1 = row * GameMain.CELL_SIZE + GameMain.CELL_PADDING;
        
        if (content == Seed.CROSS) {
            g2d.setColor(Color.RED);
            int x2 = (col + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
            int y2 = (row + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
            g2d.drawLine(x1, y1, x2, y2);
            g2d.drawLine(x2, y1, x1, y2);
        } else if (content == Seed.NOUGHT) {
            g2d.setColor(Color.BLUE);
            g2d.drawOval(x1, y1, GameMain.SYMBOL_SIZE, GameMain.SYMBOL_SIZE);
        }
    }
}


Board.java:
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

GameMain.java:
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameMain extends JPanel {
    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final String TITLE = "Tic Tac Toe";
    public static final int CELL_SIZE = 100;
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int GRID_WIDTH = 8;
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;

    private Board board;
    private GameState currentState;
    private Seed currentPlayer;
    private JLabel statusBar;

    public GameMain() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int rowSelected = mouseY / CELL_SIZE;
                int colSelected = mouseX / CELL_SIZE;

                if (currentState == GameState.PLAYING) {
                    if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS
                            && board.cells[rowSelected][colSelected].content == Seed.EMPTY) {
                        board.cells[rowSelected][colSelected].content = currentPlayer;
                        updateGame(currentPlayer, rowSelected, colSelected);
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                    }
                } else {
                    initGame();
                }
                repaint();
            }
        });

        statusBar = new JLabel(" ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.PAGE_END);
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

        board = new Board();
        initGame();
    }

    public void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board.cells[row][col].content = Seed.EMPTY;
            }
        }
        currentState = GameState.PLAYING;
        currentPlayer = Seed.CROSS;
    }

    public void updateGame(Seed theSeed, int row, int col) {
        if (board.hasWon(theSeed, row, col)) {
            currentState = (theSeed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
        } else if (board.isDraw()) {
            currentState = GameState.DRAW;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        board.paint(g);

        if (currentState == GameState.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
        } else if (currentState == GameState.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == GameState.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == GameState.NOUGHT_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(TITLE);
            frame.setContentPane(new GameMain());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
