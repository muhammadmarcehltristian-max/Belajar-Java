import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Kasir {
    
    static final int DISKON_MEMBER = 15;
    static final int DISKON_NON_MEMBER = 0;

    static class Item {
        String nama;
        long harga;
        int jumlah;
        long total;
        long diskon;

        Item(String nama, long harga, int jumlah) {
            this.nama = nama;
            this.harga = harga;
            this.jumlah = jumlah;
            this.total = harga * jumlah;
            this.diskon = 0; // Diskon akan dihitung nanti
        }
        long totalBersih() {
            return total - diskon;
        }
    }

    static String formatRupiah(long angka) {
        return "Rp " + String.format("%,d", angka).replace(',', '.');
    }

    // untuk membaca angka dengan validasi, akan diulangi terus sampai input benar
    static long bacaAngka(Scanner input, String pesan) {
        while (true) {
            System.out.print(pesan);
            String teks = input.nextLine().trim();
            try {
                return Long.parseLong(teks);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        }
    }

    // tempat untuk menampilkan pesanan barang dan untuk memesan barang
    static void tampilkanMenu(String[] nama, long[] harga) {
        System.out.println("\n=====Daftar Barang=====");
        for (int i = 0; i < nama.length; i++) {
            System.out.println((i + 1) + ". " + nama[i] + " - " + formatRupiah(harga[i]));
        }
        System.out.println("0. Selesai & bayar");
        System.out.println((nama.length + 1) + ". Batalkan pesanan");
        System.out.println("======================");
    }

    // tempat untuk menampilkan keranjang belanjaan
    static void tampilkanKeranjang(List<Item> keranjang) {
        System.out.println("\n=====Keranjang Belanja=====");
        if (keranjang.isEmpty()) {
            System.out.println("Keranjang kosong.");
        } else {
            for (int i = 0; i < keranjang.size(); i++) {
                Item item = keranjang.get(i);
                System.out.println((i + 1) + ". " + item.nama + " - " + formatRupiah(item.harga) +
                        " x " + item.jumlah + " = " + formatRupiah(item.total) +
                        " (Diskon: " + formatRupiah(item.diskon) + ")");
            }
        }
        System.out.println("============================");
    }

    // tempat untuk menghitung total belanjaan
    static long hitungTotal(List<Item> keranjang) {
        long total = 0;
        for (Item item : keranjang) {
            total += item.totalBersih();
        }
        return total;
    }

    static void prosesPembayaran(Scanner input, List<Item> keranjang, boolean isMember) {
        long totalBelanja = hitungTotal(keranjang);
        long diskonMember = isMember ? totalBelanja * DISKON_MEMBER / 100 : 0;
        long diskonNonMember = !isMember ? totalBelanja * DISKON_NON_MEMBER / 100 : 0;
        long totalDiskon = diskonMember + diskonNonMember;
        long totalBayar = totalBelanja - totalDiskon;

        System.out.println("\n=====Rincian Pembayaran=====");
        System.out.println("Total Belanja: " + formatRupiah(totalBelanja));
        if (isMember) {
            System.out.println("Diskon Member (" + DISKON_MEMBER + "%): " + formatRupiah(diskonMember));
        } else {
            System.out.println("Diskon Non-Member (" + DISKON_NON_MEMBER + "%): " + formatRupiah(diskonNonMember));
        }
        System.out.println("Total Diskon: " + formatRupiah(totalDiskon));
        System.out.println("Total Bayar: " + formatRupiah(totalBayar));

        long Uangdibayar;
        while (true) {
                Uangdibayar = bacaAngka(input, "Masukkan uang yang dibayarkan (0 = batal): ");
                if (Uangdibayar == 0) {
                    System.out.println("Pembayaran dibatalkan.");
                    return;
                }
                if (Uangdibayar >= totalBayar) {
                    break;
                }
                System.out.println("Uang kurang " + formatRupiah(totalBayar - Uangdibayar) + ", mohon coba lagi!");
            }
    
            long kembalian = Uangdibayar - totalBayar;

            System.out.println("\n=====Struk Pembayaran=====");
            for (Item item : keranjang) {
                System.out.println(item.nama + " | " + item.jumlah + " x " + formatRupiah(item.harga) +
                        " = " + formatRupiah(item.total) +
                        " (Diskon: " + formatRupiah(item.diskon) + ")");
            }
            System.out.println("---------------------------");
            System.out.println("Total Belanja: " + formatRupiah(totalBelanja));
            if (totalDiskon > 0) {
                System.out.println("Anda Hemat: -" + formatRupiah(totalDiskon));
            } else {
                System.out.println("Maaf, Anda belum mendapat diskon.");
            }
            System.out.println("Total Bayar: " + formatRupiah(totalBayar));
            System.out.println("Uang Dibayar: " + formatRupiah(Uangdibayar));
            System.out.println("Kembalian: " + formatRupiah(kembalian));
            System.out.println("============================");
            System.out.println("Terima kasih telah berbelanja!");
            keranjang.clear();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] namaBarang = {"Beras", "Gula", "Minyak", "Telur", "Daging", "Sayur", "Buah", "Roti", "Susu", "Kopi", "Teh", "Mie Instan", "Biskuit", "Cokelat", "Es Krim"};
        long[] hargaBarang = {12000, 15000, 20000, 25000, 30000, 5000, 10000, 8000, 12000, 15000, 10000, 5000, 8000, 15000, 20000};
        List<Item> keranjang = new ArrayList<>();

        System.out.println("Selamat datang di program kasir!");
        System.out.print("Apakah Anda member? (y/n): ");
        boolean isMember = input.nextLine().trim().equalsIgnoreCase("y");

        while (true) {
            tampilkanMenu(namaBarang, hargaBarang);
            int pilihan = (int) bacaAngka(input, "Masukkan nomor barang yang ingin dibeli: ");

            if (pilihan == 0) {
                prosesPembayaran(input, keranjang, isMember);
                break;
            } else if (pilihan == namaBarang.length + 1) {
                System.out.println("Pesanan dibatalkan.");
                keranjang.clear();
                break;
            } else if (pilihan >= 1 && pilihan <= namaBarang.length) {
                String nama = namaBarang[pilihan - 1];
                long harga = hargaBarang[pilihan - 1];
                int jumlah = (int) bacaAngka(input, "Masukkan jumlah barang: ");
                if (jumlah <= 0) {
                    System.out.println("Jumlah harus lebih dari 0.");
                    continue;
                }
                Item item = new Item(nama, harga, jumlah);
                keranjang.add(item);
                tampilkanKeranjang(keranjang);
            } else {
                System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }
        input.close();
    }
}