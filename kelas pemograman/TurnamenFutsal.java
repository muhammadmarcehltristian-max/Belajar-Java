public class TurnamenFutsal {
    public static void main(String[] args) {
        // Array 2 dimensi: baris = pertandingan, kolom = [Tim 1, Tim 2]
        int[][] skor = {
            {3, 1},   // Pertandingan 1
            {2, 2},   // Pertandingan 2
            {1, 4}    // Pertandingan 3
        };

        int totalGol = 0;

        System.out.println("=== HASIL PERTANDINGAN TURNAMEN FUTSAL ===");

        // Nested for: perulangan luar untuk pertandingan, perulangan dalam untuk tim
        for (int i = 0; i < skor.length; i++) {
            System.out.println("Pertandingan " + (i + 1));

            for (int j = 0; j < skor[i].length; j++) {
                System.out.println("  Skor Tim " + (j + 1) + " : " + skor[i][j]);
                totalGol += skor[i][j];
            }

            // Percabangan untuk menentukan hasil pertandingan
            if (skor[i][0] > skor[i][1]) {
                System.out.println("  Hasil        : Tim 1 Menang");
            } else if (skor[i][1] > skor[i][0]) {
                System.out.println("  Hasil        : Tim 2 Menang");
            } else {
                System.out.println("  Hasil        : Pertandingan Seri");
            }

            System.out.println();
        }

        System.out.println("Total seluruh gol dari 3 pertandingan: " + totalGol);
    }
}