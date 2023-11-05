
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
