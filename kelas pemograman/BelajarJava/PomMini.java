import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class PomMini {

    // ===================== DATA BBM =====================
    static final Scanner input = new Scanner(System.in);
    static String[] namaBBM = {"Pertamax", "Pertamax Turbo", "Pertalite", "Dexlite"};
    static double[] hargaBBM = {15000, 20000, 10000, 13000};
    static double[] stokBBM = {500, 300, 800, 400};
    static double[] terjualLiter = new double[4];
    static double[] terjualRupiah = new double[4];

    // ===================== DATA LAIN =====================
    static final String PIN_ADMIN = "1234";
    static final double DISKON_MEMBER = 0.03;   // 3%
    static final double DISKON_VOLUME = 0.02;   // 2% jika total >= 20 liter
    static final double BATAS_VOLUME = 20;

    static int nomorTransaksi = 1;
    static int jumlahTransaksi = 0;
    static double pendapatanHarian = 0;
    static double totalDiskonDiberikan = 0;
    static ArrayList<String> riwayat = new ArrayList<>();

    static final DateTimeFormatter FORMAT_WAKTU = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    // ===================== MAIN =====================
    public static void main(String[] args) {
        boolean jalan = true;
        while (jalan) {
            System.out.println("\n==========================================");
            System.out.println("        SELAMAT DATANG DI POM MINI        ");
            System.out.println("==========================================");
            System.out.println("1. Beli BBM");
            System.out.println("2. Lihat Daftar Harga & Stok");
            System.out.println("3. Riwayat Transaksi");
            System.out.println("4. Laporan Penjualan");
            System.out.println("5. Menu Admin");
            System.out.println("0. Keluar");
            System.out.println("==========================================");
            int pilih = bacaInt("Pilih menu: ", 0, 5);

            switch (pilih) {
                case 1: transaksi(); break;
                case 2: tampilkanDaftar(); break;
                case 3: tampilkanRiwayat(); break;
                case 4: laporanPenjualan(); break;
                case 5: menuAdmin(); break;
                case 0:
                    if (bacaYN("Yakin ingin keluar? (y/n): ")) {
                        System.out.println("Terima kasih telah menggunakan POM Mini. Sampai jumpa!");
                        jalan = false;
                    }
                    break;
            }
        }
        input.close();
    }

    // ===================== TRANSAKSI =====================
    static void transaksi() {
        ArrayList<Integer> keranjangJenis = new ArrayList<>();
        ArrayList<Double> keranjangLiter = new ArrayList<>();

        boolean berbelanja = true;
        while (berbelanja) {
            System.out.println("\n---------- MENU PEMBELIAN ----------");
            System.out.println("1. Tambah BBM ke keranjang");
            System.out.println("2. Lihat keranjang");
            System.out.println("3. Hapus item dari keranjang");
            System.out.println("4. Bayar (checkout)");
            System.out.println("0. Batalkan transaksi");
            int pilih = bacaInt("Pilih: ", 0, 4);

            switch (pilih) {
                case 1:
                    tambahItem(keranjangJenis, keranjangLiter);
                    break;
                case 2:
                    tampilkanKeranjang(keranjangJenis, keranjangLiter);
                    break;
                case 3:
                    hapusItem(keranjangJenis, keranjangLiter);
                    break;
                case 4:
                    if (keranjangJenis.isEmpty()) {
                        System.out.println("Keranjang masih kosong, tambahkan BBM dulu!");
                    } else if (checkout(keranjangJenis, keranjangLiter)) {
                        berbelanja = false;
                    }
                    break;
                case 0:
                    if (bacaYN("Batalkan transaksi ini? (y/n): ")) {
                        System.out.println("Transaksi dibatalkan.");
                        berbelanja = false;
                    }
                    break;
            }
        }
    }

    static void tambahItem(ArrayList<Integer> jenis, ArrayList<Double> liter) {
        System.out.println("\n=== PILIH BAHAN BAKAR ===");
        for (int i = 0; i < namaBBM.length; i++) {
            System.out.printf("%d. %-15s %s/liter (sisa stok: %.1f L)%n",
                    i + 1, namaBBM[i], rupiah(hargaBBM[i]), sisaStok(i, jenis, liter));
        }
        int kategori = bacaInt("Masukkan kategori (1-" + namaBBM.length + "): ", 1, namaBBM.length) - 1;

        double tersedia = sisaStok(kategori, jenis, liter);
        if (tersedia <= 0) {
            System.out.println("Maaf, stok " + namaBBM[kategori] + " habis!");
            return;
        }
        double jumlah = bacaDouble("Masukkan berapa liter (maks " + tersedia + "): ", 0.1, tersedia);

        jenis.add(kategori);
        liter.add(jumlah);
        System.out.printf("Ditambahkan: %s %.2f L = %s%n",
                namaBBM[kategori], jumlah, rupiah(Math.round(hargaBBM[kategori] * jumlah)));
    }

    static double sisaStok(int idx, ArrayList<Integer> jenis, ArrayList<Double> liter) {
        double terpakai = 0;
        for (int i = 0; i < jenis.size(); i++) {
            if (jenis.get(i) == idx) terpakai += liter.get(i);
        }
        return stokBBM[idx] - terpakai;
    }

    static void tampilkanKeranjang(ArrayList<Integer> jenis, ArrayList<Double> liter) {
        if (jenis.isEmpty()) {
            System.out.println("Keranjang masih kosong.");
            return;
        }
        System.out.println("\n---------------- KERANJANG ----------------");
        System.out.printf("%-3s %-15s %8s %15s%n", "No", "BBM", "Liter", "Subtotal");
        double subtotal = 0;
        for (int i = 0; i < jenis.size(); i++) {
            double sub = Math.round(hargaBBM[jenis.get(i)] * liter.get(i));
            subtotal += sub;
            System.out.printf("%-3d %-15s %8.2f %15s%n", i + 1, namaBBM[jenis.get(i)], liter.get(i), rupiah(sub));
        }
        System.out.println("-------------------------------------------");
        System.out.printf("%-27s %15s%n", "TOTAL SEMENTARA", rupiah(subtotal));
    }

    static void hapusItem(ArrayList<Integer> jenis, ArrayList<Double> liter) {
        if (jenis.isEmpty()) {
            System.out.println("Keranjang masih kosong.");
            return;
        }
        tampilkanKeranjang(jenis, liter);
        int no = bacaInt("Nomor item yang dihapus (0 = batal): ", 0, jenis.size());
        if (no == 0) return;
        System.out.println("Item " + namaBBM[jenis.get(no - 1)] + " dihapus.");
        jenis.remove(no - 1);
        liter.remove(no - 1);
    }

    // return true jika transaksi selesai (lunas), false jika kembali ke menu pembelian
    static boolean checkout(ArrayList<Integer> jenis, ArrayList<Double> liter) {
        tampilkanKeranjang(jenis, liter);

        // hitung subtotal & total liter
        double subtotal = 0, totalLiter = 0;
        for (int i = 0; i < jenis.size(); i++) {
            subtotal += Math.round(hargaBBM[jenis.get(i)] * liter.get(i));
            totalLiter += liter.get(i);
        }

        // diskon
        boolean member = bacaYN("\nApakah anda member POM Mini? (y/n): ");
        double diskonMember = member ? Math.round(subtotal * DISKON_MEMBER) : 0;
        double diskonVolume = totalLiter >= BATAS_VOLUME ? Math.round(subtotal * DISKON_VOLUME) : 0;
        double totalDiskon = diskonMember + diskonVolume;
        long totalBayar = Math.round(subtotal - totalDiskon);

        System.out.println("\n--------- RINGKASAN PEMBAYARAN ---------");
        System.out.printf("%-22s %15s%n", "Subtotal", rupiah(subtotal));
        if (diskonMember > 0) System.out.printf("%-22s %15s%n", "Diskon Member (3%)", "-" + rupiah(diskonMember));
        if (diskonVolume > 0) System.out.printf("%-22s %15s%n", "Diskon >=20 L (2%)", "-" + rupiah(diskonVolume));
        System.out.printf("%-22s %15s%n", "TOTAL BAYAR", rupiah(totalBayar));
        System.out.println("----------------------------------------");

        if (!bacaYN("Lanjut ke pembayaran? (y/n): ")) {
            return false; // kembali ke menu pembelian
        }

        // metode pembayaran
        System.out.println("\nMetode pembayaran:");
        System.out.println("1. Tunai");
        System.out.println("2. Debit / QRIS (uang pas)");
        int metode = bacaInt("Pilih metode: ", 1, 2);
        String namaMetode;
        long uangDibayar;
        long kembalian = 0;

        if (metode == 1) {
            namaMetode = "Tunai";
            while (true) {
                uangDibayar = bacaLong("Masukkan uang yang dibayarkan: ", 0);
                if (uangDibayar >= totalBayar) {
                    kembalian = uangDibayar - totalBayar;
                    break;
                }
                System.out.println("Uang kurang " + rupiah(totalBayar - uangDibayar) + ", mohon coba lagi!");
                if (!bacaYN("Coba lagi? (y/n, n = batal bayar): ")) {
                    return false;
                }
            }
        } else {
            namaMetode = "Debit/QRIS";
            uangDibayar = totalBayar;
            System.out.println("Memproses pembayaran non-tunai...");
            tunggu(1500);
            System.out.println("Pembayaran berhasil!");
        }

        // update stok & laporan
        for (int i = 0; i < jenis.size(); i++) {
            int idx = jenis.get(i);
            stokBBM[idx] -= liter.get(i);
            terjualLiter[idx] += liter.get(i);
            terjualRupiah[idx] += Math.round(hargaBBM[idx] * liter.get(i));
        }
        pendapatanHarian += totalBayar;
        totalDiskonDiberikan += totalDiskon;
        jumlahTransaksi++;

        // cetak struk
        String struk = buatStruk(jenis, liter, subtotal, diskonMember, diskonVolume,
                totalBayar, uangDibayar, kembalian, namaMetode, member);
        System.out.println(struk);
        riwayat.add(struk);
        nomorTransaksi++;

        System.out.println("Terima kasih telah membeli di POM Mini kami!");
        if (bacaYN("Beli lagi? (y/n): ")) {
            transaksi(); // transaksi baru
        }
        return true;
    }

    static String buatStruk(ArrayList<Integer> jenis, ArrayList<Double> liter, double subtotal,
                            double diskonMember, double diskonVolume, long totalBayar,
                            long uangDibayar, long kembalian, String metode, boolean member) {
        StringBuilder sb = new StringBuilder();
        String garis = "==========================================";
        sb.append("\n").append(garis).append("\n");
        sb.append("              STRUK POM MINI              \n");
        sb.append(garis).append("\n");
        sb.append(String.format("No. Transaksi : TRX-%04d%n", nomorTransaksi));
        sb.append("Waktu         : ").append(LocalDateTime.now().format(FORMAT_WAKTU)).append("\n");
        sb.append("Member        : ").append(member ? "Ya" : "Tidak").append("\n");
        sb.append("------------------------------------------\n");
        for (int i = 0; i < jenis.size(); i++) {
            int idx = jenis.get(i);
            sb.append(String.format("%-15s %.2f L x %s%n", namaBBM[idx], liter.get(i), rupiah(hargaBBM[idx])));
            sb.append(String.format("%42s%n", rupiah(Math.round(hargaBBM[idx] * liter.get(i)))));
        }
        sb.append("------------------------------------------\n");
        sb.append(String.format("%-24s %17s%n", "Subtotal", rupiah(subtotal)));
        if (diskonMember > 0) sb.append(String.format("%-24s %17s%n", "Diskon Member", "-" + rupiah(diskonMember)));
        if (diskonVolume > 0) sb.append(String.format("%-24s %17s%n", "Diskon Volume", "-" + rupiah(diskonVolume)));
        sb.append(String.format("%-24s %17s%n", "TOTAL", rupiah(totalBayar)));
        sb.append(String.format("%-24s %17s%n", "Metode (" + metode + ")", ""));
        sb.append(String.format("%-24s %17s%n", "Uang dibayarkan", rupiah(uangDibayar)));
        sb.append(String.format("%-24s %17s%n", "Kembalian", rupiah(kembalian)));
        sb.append(garis);
        return sb.toString();
    }

    // ===================== TAMPILAN =====================
    static void tampilkanDaftar() {
        System.out.println("\n============ DAFTAR HARGA & STOK ============");
        System.out.printf("%-3s %-15s %14s %12s%n", "No", "BBM", "Harga/Liter", "Stok (L)");
        for (int i = 0; i < namaBBM.length; i++) {
            System.out.printf("%-3d %-15s %14s %12.1f%n", i + 1, namaBBM[i], rupiah(hargaBBM[i]), stokBBM[i]);
        }
        System.out.println("=============================================");
        System.out.println("Promo: Member diskon 3% | Beli >= 20 L diskon 2%");
    }

    static void tampilkanRiwayat() {
        if (riwayat.isEmpty()) {
            System.out.println("\nBelum ada transaksi.");
            return;
        }
        System.out.println("\n========== RIWAYAT TRANSAKSI (" + riwayat.size() + ") ==========");
        for (String s : riwayat) {
            System.out.println(s);
        }
    }

    static void laporanPenjualan() {
        System.out.println("\n============== LAPORAN PENJUALAN ==============");
        System.out.println("Waktu cetak       : " + LocalDateTime.now().format(FORMAT_WAKTU));
        System.out.println("Jumlah transaksi  : " + jumlahTransaksi);
        System.out.printf("%-15s %12s %18s%n", "BBM", "Terjual (L)", "Pendapatan");
        double totalLiter = 0;
        for (int i = 0; i < namaBBM.length; i++) {
            System.out.printf("%-15s %12.2f %18s%n", namaBBM[i], terjualLiter[i], rupiah(terjualRupiah[i]));
            totalLiter += terjualLiter[i];
        }
        System.out.println("-----------------------------------------------");
        System.out.printf("%-15s %12.2f %18s%n", "TOTAL", totalLiter, rupiah(pendapatanHarian));
        System.out.println("Total diskon diberikan : " + rupiah(totalDiskonDiberikan));
        if (jumlahTransaksi > 0) {
            System.out.println("Rata-rata per transaksi: " + rupiah(Math.round(pendapatanHarian / jumlahTransaksi)));
        }
        System.out.println("===============================================");
    }

    // ===================== ADMIN =====================
    static void menuAdmin() {
        int percobaan = 0;
        boolean masuk = false;
        while (percobaan < 3 && !masuk) {
            System.out.print("Masukkan PIN admin: ");
            String pin = input.nextLine().trim();
            if (pin.equals(PIN_ADMIN)) {
                masuk = true;
            } else {
                percobaan++;
                System.out.println("PIN salah! (" + percobaan + "/3)");
            }
        }
        if (!masuk) {
            System.out.println("Akses admin ditolak.");
            return;
        }

        boolean admin = true;
        while (admin) {
            System.out.println("\n------------ MENU ADMIN ------------");
            System.out.println("1. Ubah harga BBM");
            System.out.println("2. Tambah stok BBM");
            System.out.println("3. Lihat harga & stok");
            System.out.println("4. Reset laporan penjualan");
            System.out.println("0. Kembali");
            int pilih = bacaInt("Pilih: ", 0, 4);
            switch (pilih) {
                case 1:
                    tampilkanDaftar();
                    int a = bacaInt("Pilih BBM (1-" + namaBBM.length + "): ", 1, namaBBM.length) - 1;
                    hargaBBM[a] = bacaDouble("Harga baru per liter: ", 1, 1000000);
                    System.out.println("Harga " + namaBBM[a] + " sekarang " + rupiah(hargaBBM[a]));
                    break;
                case 2:
                    tampilkanDaftar();
                    int b = bacaInt("Pilih BBM (1-" + namaBBM.length + "): ", 1, namaBBM.length) - 1;
                    stokBBM[b] += bacaDouble("Jumlah liter yang ditambah: ", 1, 100000);
                    System.out.printf("Stok %s sekarang %.1f L%n", namaBBM[b], stokBBM[b]);
                    break;
                case 3:
                    tampilkanDaftar();
                    break;
                case 4:
                    if (bacaYN("Yakin reset laporan & riwayat? (y/n): ")) {
                        terjualLiter = new double[namaBBM.length];
                        terjualRupiah = new double[namaBBM.length];
                        riwayat.clear();
                        pendapatanHarian = 0;
                        totalDiskonDiberikan = 0;
                        jumlahTransaksi = 0;
                        System.out.println("Laporan berhasil direset.");
                    }
                    break;
                case 0:
                    admin = false;
                    break;
            }
        }
    }

    // ===================== UTILITAS INPUT =====================
    static int bacaInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String s = input.nextLine().trim();
            try {
                int n = Integer.parseInt(s);
                if (n >= min && n <= max) return n;
                System.out.println("Input harus antara " + min + " dan " + max + "!");
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, masukkan angka!");
            }
        }
    }

    static long bacaLong(String prompt, long min) {
        while (true) {
            System.out.print(prompt);
            String s = input.nextLine().trim();
            try {
                long n = Long.parseLong(s);
                if (n >= min) return n;
                System.out.println("Input minimal " + min + "!");
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, masukkan angka bulat!");
            }
        }
    }

    static double bacaDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String s = input.nextLine().trim().replace(',', '.');
            try {
                double n = Double.parseDouble(s);
                if (n >= min && n <= max) return n;
                System.out.println("Input harus antara " + min + " dan " + max + "!");
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, masukkan angka!");
            }
        }
    }

    static boolean bacaYN(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = input.nextLine().trim();
            if (s.equalsIgnoreCase("y")) return true;
            if (s.equalsIgnoreCase("n")) return false;
            System.out.println("Input tidak valid, ketik y atau n!");
        }
    }

    static String rupiah(double nilai) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("id", "ID"));
        nf.setMaximumFractionDigits(0);
        return "Rp" + nf.format(Math.round(nilai));
    }

    static void tunggu(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
