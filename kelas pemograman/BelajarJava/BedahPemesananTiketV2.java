
import java.util.Scanner;

public class BedahPemesananTiketV2 {

    // === Upgrade #3: logic diskon dipisah jadi method sendiri (DRY) ===
    static double getPersenDiskon(char kategori) {
        if (kategori == 'A') return 0.20;
        else if (kategori == 'L') return 0.15;
        else return 0.0; // Dewasa atau default = tanpa diskon
    }

    // === Upgrade #1: validasi kategori pakai loop, dipisah jadi method ===
    static char inputKategoriValid(Scanner scanner) {
        char kategori;
        while (true) {
            System.out.print("Kategori penonton (A: Anak, D: Dewasa, L: Lansia): ");
            kategori = scanner.next().charAt(0);

            if (kategori == 'A' || kategori == 'D' || kategori == 'L') {
                break;
            } else {
                System.out.println("Kategori tidak valid, coba lagi.");
            }
        }
        return kategori;
    }

    // === Upgrade #1: validasi angka > 0 pakai loop, dipisah jadi method ===
    static int inputAngkaPositif(Scanner scanner, String label) {
        int angka;
        while (true) {
            System.out.print(label);
            angka = scanner.nextInt();
            if (angka > 0) {
                break;
            } else {
                System.out.println("Nilai harus lebih dari 0, coba lagi.");
            }
        }
        return angka;
    }

    static double inputHargaPositif(Scanner scanner, String label) {
        double harga;
        while (true) {
            System.out.print(label);
            harga = scanner.nextDouble();
            if (harga > 0) {
                break;
            } else {
                System.out.println("Harga harus lebih dari 0, coba lagi.");
            }
        }
        return harga;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Selamat datang di sistem pemesanan tiket bioskop!");
        System.out.println("Silakan masukkan data pemesanan Anda.");

        System.out.println("\n===== STRUK TIKET =====");

        double totalSebelumDiskon = 0;
        double totalSetelahDiskon = 0;

        System.out.print("Berapa jenis tiket yang ingin dipesan? ");
        int totalJenis = scanner.nextInt();
        scanner.nextLine();

        String[] namaFilm = new String[totalJenis];
        int[] jumlahTiket = new int[totalJenis];
        char[] kategoriTiket = new char[totalJenis];      // Upgrade #6: simpan kategori tiap item
        double[] hargaSetelahDiskon = new double[totalJenis]; // Upgrade #6: simpan hasil tiap item

        for (int i = 1; i <= totalJenis; i++) {
            System.out.println("\n===== TIKET " + i + " =====");

            System.out.print("Judul film: ");
            namaFilm[i - 1] = scanner.nextLine();

            jumlahTiket[i - 1] = inputAngkaPositif(scanner, "Jumlah tiket: ");

            char kategori = inputKategoriValid(scanner);
            kategoriTiket[i - 1] = kategori;

            double persenDiskon = getPersenDiskon(kategori); // dihitung sekali, dipakai berkali-kali
            System.out.println("Diskon diterima: " + (int) (persenDiskon * 100) + "%");

            double hargaTiket = inputHargaPositif(scanner, "Harga tiket per orang: ");

            double subtotal = hargaTiket * jumlahTiket[i - 1];
            System.out.printf("Subtotal: %.2f%n", subtotal); // Upgrade #5: format 2 desimal

            double totalHarga = subtotal - (subtotal * persenDiskon);
            System.out.printf("Harga setelah diskon: %.2f%n", totalHarga);

            hargaSetelahDiskon[i - 1] = totalHarga;

            totalSebelumDiskon += subtotal;
            totalSetelahDiskon += totalHarga;

            scanner.nextLine();
        }

        // === Upgrade #4: validasi uang dibayar tidak boleh kurang dari total ===
        double uangDibayar;
        while (true) {
            System.out.println("\nMasukkan jumlah uang dibayar:");
            uangDibayar = scanner.nextDouble();
            if (uangDibayar >= totalSetelahDiskon) {
                break;
            } else {
                System.out.printf("Uang tidak cukup! Total yang harus dibayar: %.2f. Coba lagi.%n", totalSetelahDiskon);
            }
        }

        System.out.println("\n============================");
        System.out.println("Total jenis tiket dipesan: " + totalJenis);
        System.out.printf("Total harga sebelum diskon: %.2f%n", totalSebelumDiskon);
        System.out.printf("Total harga setelah diskon: %.2f%n", totalSetelahDiskon);
        System.out.printf("Anda hemat: %.2f%n", totalSebelumDiskon - totalSetelahDiskon);
        System.out.printf("Uang dibayar: %.2f%n", uangDibayar);
        System.out.printf("Kembalian: %.2f%n", uangDibayar - totalSetelahDiskon);

        // === Upgrade #6: ringkasan detail tiap tiket ===
        System.out.println("\n===== RINGKASAN PEMESANAN =====");
        for (int i = 0; i < totalJenis; i++) {
            System.out.printf("%d. %s (%d tiket, kategori %c) - Rp%.2f%n",
                    i + 1, namaFilm[i], jumlahTiket[i], kategoriTiket[i], hargaSetelahDiskon[i]);
        }

        System.out.println("\n===== TERIMA KASIH TELAH MEMESAN =====");
        scanner.close();
    }
}