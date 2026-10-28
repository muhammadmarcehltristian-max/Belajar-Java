import java.util.Scanner;

public class BiayaPengisian {

    static final int TARIF_PER_KWH = 1500;

    // Method menerima jumlah energi (kWh), mengembalikan total biaya (Rp)
    static int hitungBiaya(int energi) {
        return energi * TARIF_PER_KWH;
    }

    // Format angka menjadi "Rp27.000"
    static String formatRupiah(int angka) {
        return "Rp" + String.format("%,d", angka).replace(',', '.');
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Masukkan jumlah energi (kWh): ");
        int energi = input.nextInt();

        int biaya = hitungBiaya(energi);

        System.out.println("Biaya pengisian: " + formatRupiah(biaya));

        input.close();
    }
}