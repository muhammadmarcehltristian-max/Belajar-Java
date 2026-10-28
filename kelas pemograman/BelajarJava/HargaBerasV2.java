import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HargaBerasV2 {

    static final int MIN_KARUNG_DISKON = 15;
    static final int PERSEN_DISKON = 10;

    // Satu baris pesanan di keranjang
    static class Item {
        String nama;
        long hargaPerKarung;
        int jumlah;
        long total;
        long diskon;

        Item(String nama, long hargaPerKarung, int jumlah) {
            this.nama = nama;
            this.hargaPerKarung = hargaPerKarung;
            this.jumlah = jumlah;
            this.total = hargaPerKarung * jumlah;
            this.diskon = (jumlah >= MIN_KARUNG_DISKON) ? total * PERSEN_DISKON / 100 : 0;
        }

        long totalBersih() {
            return total - diskon;
        }
    }

    static String formatRupiah(long angka) {
        return "Rp " + String.format("%,d", angka).replace(',', '.');
    }

    // Baca angka per baris dengan validasi, ulangi terus sampai input benar
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

    static void tampilkanMenu(String[] nama, long[] harga) {
        System.out.println("\n=====Daftar beras=====");
        for (int i = 0; i < nama.length; i++) {
            System.out.println((i + 1) + ". " + nama[i] + " - " + formatRupiah(harga[i]));
        }
        System.out.println("0. Selesai & bayar");
        System.out.println((nama.length + 1) + ". Batalkan pesanan");
        System.out.println("======================");
        System.out.println("(Diskon " + PERSEN_DISKON + "% untuk pembelian >= "
                + MIN_KARUNG_DISKON + " karung per jenis beras)");
    }

    static void tampilkanKeranjang(List<Item> keranjang) {
        if (keranjang.isEmpty()) {
            return;
        }
        System.out.println("\nKeranjang saat ini:");
        for (int i = 0; i < keranjang.size(); i++) {
            Item it = keranjang.get(i);
            System.out.println("  " + (i + 1) + ". " + it.nama + " x " + it.jumlah
                    + " karung = " + formatRupiah(it.totalBersih()));
        }
    }

    static void prosesPembayaran(Scanner input, List<Item> keranjang) {
        long totalSebelum = 0;
        long totalDiskon = 0;
        for (Item it : keranjang) {
            totalSebelum += it.total;
            totalDiskon += it.diskon;
        }
        long totalBayar = totalSebelum - totalDiskon;

        System.out.println("\nHarga sebelum diskon : " + formatRupiah(totalSebelum));
        System.out.println("Total diskon         : " + formatRupiah(totalDiskon));
        System.out.println("Total yang harus dibayar: " + formatRupiah(totalBayar));

        long uangDibayar;
        while (true) {
            uangDibayar = bacaAngka(input, "Masukkan uang yang dibayarkan (0 = batal): ");
            if (uangDibayar == 0) {
                System.out.println("Pembayaran dibatalkan.");
                return;
            }
            if (uangDibayar >= totalBayar) {
                break;
            }
            System.out.println("Uang kurang " + formatRupiah(totalBayar - uangDibayar) + ", mohon coba lagi!");
        }

        long kembalian = uangDibayar - totalBayar;

        System.out.println("\n========== STRUK ==========");
        for (Item it : keranjang) {
            System.out.println(it.nama + " | " + it.jumlah + " karung x " + formatRupiah(it.hargaPerKarung));
            System.out.println("   Subtotal : " + formatRupiah(it.total));
            if (it.diskon > 0) {
                System.out.println("   Diskon " + PERSEN_DISKON + "% : -" + formatRupiah(it.diskon));
            }
        }
        System.out.println("---------------------------");
        System.out.println("Sebelum diskon : " + formatRupiah(totalSebelum));
        if (totalDiskon > 0) {
            System.out.println("Total diskon   : -" + formatRupiah(totalDiskon));
        } else {
            System.out.println("Maaf, Anda belum mendapat diskon.");
        }
        System.out.println("Total bayar    : " + formatRupiah(totalBayar));
        System.out.println("Uang dibayar   : " + formatRupiah(uangDibayar));
        System.out.println("Kembalian      : " + formatRupiah(kembalian));
        System.out.println("===========================");
        System.out.println("Terima kasih telah memesan!");
        keranjang.clear();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String[] nama = {"A1", "PW", "Walet", "Ayam"};
        long[] harga = {790000, 750000, 750000, 760000};

        List<Item> keranjang = new ArrayList<>();
        boolean lanjut = true;

        while (lanjut) {
            tampilkanMenu(nama, harga);
            tampilkanKeranjang(keranjang);

            long pilihan = bacaAngka(input, "Masukkan nomor beras: ");

            if (pilihan == 0) {
                if (keranjang.isEmpty()) {
                    System.out.println("Terima kasih telah berkunjung.");
                    lanjut = false;
                } else {
                    prosesPembayaran(input, keranjang);
                    if (keranjang.isEmpty()) { // pembayaran berhasil -> selesai
                        lanjut = false;
                    }
                }
            } else if (pilihan == nama.length + 1) {
                keranjang.clear();
                System.out.println("Pesanan dibatalkan.");
                lanjut = false;
            } else if (pilihan >= 1 && pilihan <= nama.length) {
                int idx = (int) pilihan - 1;
                long jumlah = bacaAngka(input, "Masukkan jumlah karung: ");
                if (jumlah <= 0) {
                    System.out.println("Jumlah harus lebih dari 0.");
                } else if (jumlah > 100000) {
                    System.out.println("Jumlah terlalu besar.");
                } else {
                    keranjang.add(new Item(nama[idx], harga[idx], (int) jumlah));
                    System.out.println("Ditambahkan ke keranjang.");
                }
            } else {
                System.out.println("Nomor tidak valid, silakan coba lagi.");
            }
        }

        input.close();
    }
}