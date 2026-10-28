public class Bioskop {
    public static void main(String[] args) {
        // Array 1 dimensi
        String[] studio = {"Studio 1", "Studio 2", "Studio 3", "Studio 4"};
        int[] kursiTerisi = {35, 48, 20, 50};
        int kapasitas = 50;

        // Menyimpan indeks studio dengan kursi terisi paling banyak
        int indeksTerbanyak = 0;

        System.out.println("=== DATA KURSI STUDIO BIOSKOP ===");

        // Perulangan untuk memproses seluruh studio
        for (int i = 0; i < studio.length; i++) {
            int kursiTersedia = kapasitas - kursiTerisi[i];
            String status;

            // Percabangan untuk menentukan kondisi kursi
            if (kursiTersedia <= 5) {
                status = "Hampir Penuh";
            } else {
                status = "Masih Tersedia";
            }

            // Menampilkan hasil pemeriksaan setiap studio
            System.out.println(studio[i]);
            System.out.println("  Kursi terisi   : " + kursiTerisi[i]);
            System.out.println("  Kursi tersedia : " + kursiTersedia);
            System.out.println("  Status         : " + status);
            System.out.println();

            // Mencari studio dengan kursi terisi terbanyak
            if (kursiTerisi[i] > kursiTerisi[indeksTerbanyak]) {
                indeksTerbanyak = i;
            }
        }

        System.out.println("Studio dengan kursi terisi paling banyak: "
                + studio[indeksTerbanyak] + " (" + kursiTerisi[indeksTerbanyak] + " kursi)");
    }
}