import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AturKeuangan {

    // Kategori kebutuhan yang tersedia
    enum Kategori {
        MAKAN,
        LISTRIK,
        KULIAH,
        BENSIN,
        LAINNYA
    }

    // Class untuk merepresentasikan satu data kebutuhan
    static class Kebutuhan {
        Kategori kategori;
        String nama;       // nama makanan/minuman/barang, misal "Nasi Goreng", "Token Listrik"
        double harga;
        String hari;       // diinput user (contoh: "Senin")
        LocalDate tanggal; // OTOMATIS dari sistem, tidak diinput user

        Kebutuhan(Kategori kategori, String nama, double harga, String hari, LocalDate tanggal) {
            this.kategori = kategori;
            this.nama = nama;
            this.harga = harga;
            this.hari = hari;
            this.tanggal = tanggal;
        }

        // Format untuk disimpan ke file (dipisah ";")
        // Nama diganti karakter ";" (jika ada) supaya tidak merusak format CSV
        String toCsv() {
            String namaAman = nama.replace(";", ",");
            return kategori + ";" + namaAman + ";" + harga + ";" + hari + ";" + tanggal;
        }

        // Membaca kembali 1 baris file menjadi objek Kebutuhan
        static Kebutuhan fromCsv(String baris) {
            String[] bagian = baris.split(";");
            Kategori k = Kategori.valueOf(bagian[0]);
            String nama = bagian[1];
            double h = Double.parseDouble(bagian[2]);
            String hari = bagian[3];
            LocalDate tgl = LocalDate.parse(bagian[4]);
            return new Kebutuhan(k, nama, h, hari, tgl);
        }

        @Override
        public String toString() {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return String.format("%-8s | %-15s | Rp%,.0f | %-10s | %s",
                    kategori, nama, harga, hari, tanggal.format(fmt));
        }
    }

    // Class untuk merepresentasikan satu data pemasukan (uang masuk)
    static class Pemasukan {
        String nama;       // sumber uang masuk, misal "Gaji", "Transfer Ortu", "Bonus"
        double jumlah;
        LocalDate tanggal; // OTOMATIS dari sistem, tidak diinput user

        Pemasukan(String nama, double jumlah, LocalDate tanggal) {
            this.nama = nama;
            this.jumlah = jumlah;
            this.tanggal = tanggal;
        }

        // Format untuk disimpan ke file (dipisah ";")
        String toCsv() {
            String namaAman = nama.replace(";", ",");
            return namaAman + ";" + jumlah + ";" + tanggal;
        }

        // Membaca kembali 1 baris file menjadi objek Pemasukan
        static Pemasukan fromCsv(String baris) {
            String[] bagian = baris.split(";");
            String nama = bagian[0];
            double j = Double.parseDouble(bagian[1]);
            LocalDate tgl = LocalDate.parse(bagian[2]);
            return new Pemasukan(nama, j, tgl);
        }

        @Override
        public String toString() {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return String.format("%-15s | Rp%,.0f | %s", nama, jumlah, tanggal.format(fmt));
        }
    }

    // Nama file penyimpanan data (dibuat di folder tempat program dijalankan)
    private static final String FILE_DATA = "data_kebutuhan.csv";
    private static final String FILE_SALDO = "data_pemasukan.csv";

    private static List<Kebutuhan> daftarKebutuhan = new ArrayList<>();
    private static List<Pemasukan> daftarPemasukan = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        muatData();       // load data kebutuhan lama saat program dibuka
        muatDataSaldo();  // load data pemasukan lama saat program dibuka

        boolean lanjut = true;
        while (lanjut) {
            tampilkanMenu();
            int pilihan = bacaPilihan();

            switch (pilihan) {
                case 1:
                    tambahKebutuhan();
                    break;
                case 2:
                    tampilkanSemuaKebutuhan();
                    break;
                case 3:
                    lihatRingkasanMingguan();
                    break;
                case 4:
                    hitungTotalPerKategori();
                    break;
                case 5:
                    hitungTotalKeseluruhan();
                    break;
                case 6:
                    tambahPemasukan();
                    break;
                case 7:
                    ringkasanSaldo();
                    break;
                case 0:
                    lanjut = false;
                    System.out.println("Data sudah tersimpan otomatis. Sampai jumpa!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }

        scanner.close();
    }

    private static void tampilkanMenu() {
        System.out.println("\n=== APLIKASI ATUR KEUANGAN ===");
        System.out.println("1. Tambah Kebutuhan");
        System.out.println("2. Lihat Semua Kebutuhan");
        System.out.println("3. Ringkasan Mingguan");
        System.out.println("4. Total Per Kategori");
        System.out.println("5. Total Keseluruhan Pengeluaran");
        System.out.println("6. Tambah Uang Masuk (Saldo)");
        System.out.println("7. Ringkasan Saldo (Uang Masuk, Pengeluaran, Sisa Uang)");
        System.out.println("0. Keluar");
        System.out.print("Pilih menu: ");
    }

    private static int bacaPilihan() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void tambahKebutuhan() {
        System.out.println("\nPilih kategori kebutuhan:");
        Kategori[] semuaKategori = Kategori.values();
        for (int i = 0; i < semuaKategori.length; i++) {
            System.out.println((i + 1) + ". " + semuaKategori[i]);
        }
        System.out.print("Masukkan nomor kategori: ");

        int pilihanKategori;
        try {
            pilihanKategori = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid.");
            return;
        }

        if (pilihanKategori < 1 || pilihanKategori > semuaKategori.length) {
            System.out.println("Kategori tidak ditemukan.");
            return;
        }

        Kategori kategoriTerpilih = semuaKategori[pilihanKategori - 1];

        // Nama makanan/minuman/barang, biar catatan lebih terperinci
        System.out.print("Masukkan nama (contoh: Nasi Goreng, Token Listrik, Bensin Pertalite): ");
        String nama = scanner.nextLine().trim();
        if (nama.isEmpty()) {
            nama = "-";
        }

        System.out.print("Masukkan harga: ");
        double harga;
        try {
            harga = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Harga tidak valid.");
            return;
        }

        System.out.print("Masukkan hari (contoh: Senin): ");
        String hari = scanner.nextLine().trim();

        Kebutuhan baru = new Kebutuhan(kategoriTerpilih, nama, harga, hari, LocalDate.now());
        daftarKebutuhan.add(baru);
        simpanSatuData(baru); // langsung ditulis ke file, tidak menunggu program ditutup

        System.out.println("Tersimpan! (tanggal otomatis: " + baru.tanggal + ")");
    }

    // ==========================================================
    // BAGIAN PEMASUKAN (UANG MASUK / SALDO)
    // ==========================================================

    private static void tambahPemasukan() {
        System.out.print("\nMasukkan sumber uang masuk (contoh: Gaji, Transfer Ortu, Bonus): ");
        String nama = scanner.nextLine().trim();
        if (nama.isEmpty()) {
            nama = "-";
        }

        System.out.print("Masukkan jumlah uang masuk: ");
        double jumlah;
        try {
            jumlah = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Jumlah tidak valid.");
            return;
        }

        Pemasukan baru = new Pemasukan(nama, jumlah, LocalDate.now());
        daftarPemasukan.add(baru);
        simpanSatuPemasukan(baru); // langsung ditulis ke file

        System.out.println("Uang masuk tersimpan! (tanggal otomatis: " + baru.tanggal + ")");
    }

    private static double hitungTotalPemasukan() {
        double total = 0;
        for (Pemasukan p : daftarPemasukan) {
            total += p.jumlah;
        }
        return total;
    }

    private static double hitungTotalPengeluaran() {
        double total = 0;
        for (Kebutuhan keb : daftarKebutuhan) {
            total += keb.harga;
        }
        return total;
    }

    private static void ringkasanSaldo() {
        double totalMasuk = hitungTotalPemasukan();
        double totalKeluar = hitungTotalPengeluaran();
        double sisa = totalMasuk - totalKeluar;

        System.out.println("\n=== RINGKASAN SALDO ===");
        if (daftarPemasukan.isEmpty()) {
            System.out.println("Belum ada data uang masuk.");
        } else {
            System.out.println("Riwayat Uang Masuk:");
            for (int i = 0; i < daftarPemasukan.size(); i++) {
                System.out.println((i + 1) + ". " + daftarPemasukan.get(i));
            }
        }

        System.out.println();
        System.out.printf("Total Uang Masuk      : Rp%,.0f%n", totalMasuk);
        System.out.printf("Total Pengeluaran      : Rp%,.0f%n", totalKeluar);
        System.out.printf("Sisa Uang              : Rp%,.0f%n", sisa);

        if (sisa < 0) {
            System.out.println("Perhatian: pengeluaran sudah melebihi uang masuk!");
        }
    }

    private static void tampilkanSemuaKebutuhan() {
        if (daftarKebutuhan.isEmpty()) {
            System.out.println("\nBelum ada data kebutuhan.");
            return;
        }

        System.out.println("\n=== DAFTAR KEBUTUHAN (semua, tidak dikelompokkan) ===");
        for (int i = 0; i < daftarKebutuhan.size(); i++) {
            System.out.println((i + 1) + ". " + daftarKebutuhan.get(i));
        }
    }

    private static void hitungTotalPerKategori() {
        if (daftarKebutuhan.isEmpty()) {
            System.out.println("\nBelum ada data kebutuhan.");
            return;
        }

        Map<Kategori, Double> total = new EnumMap<>(Kategori.class);
        for (Kategori k : Kategori.values()) {
            total.put(k, 0.0);
        }
        for (Kebutuhan keb : daftarKebutuhan) {
            total.put(keb.kategori, total.get(keb.kategori) + keb.harga);
        }

        System.out.println("\n=== TOTAL PER KATEGORI ===");
        for (Map.Entry<Kategori, Double> entri : total.entrySet()) {
            System.out.printf("%-8s : Rp%,.0f%n", entri.getKey(), entri.getValue());
        }
    }

    private static void hitungTotalKeseluruhan() {
        double total = hitungTotalPengeluaran();
        System.out.printf("%nTotal keseluruhan pengeluaran: Rp%,.0f%n", total);
    }

    // ==========================================================
    // BAGIAN RINGKASAN MINGGUAN
    // Minggu dihitung Senin s.d. Minggu. Minggu yang sudah lewat
    // ditampilkan terpangkas ("Minggu N total = ..."), sedangkan
    // minggu yang masih berjalan ditampilkan detailnya langsung.
    // Ketik nomor minggu untuk membuka/menutup detailnya
    // (pengganti tombol panah, karena console Java standar tidak
    // bisa mendeteksi tombol panah tanpa library tambahan seperti JLine).
    // ==========================================================

    private static LocalDate awalMinggu(LocalDate tanggal) {
        return tanggal.with(DayOfWeek.MONDAY);
    }

    private static LocalDate akhirMinggu(LocalDate tanggal) {
        return tanggal.with(DayOfWeek.SUNDAY);
    }

    private static void lihatRingkasanMingguan() {
        if (daftarKebutuhan.isEmpty()) {
            System.out.println("\nBelum ada data kebutuhan.");
            return;
        }

        // Kelompokkan semua data berdasarkan minggu (kunci = tanggal Senin di minggu itu)
        TreeMap<LocalDate, List<Kebutuhan>> perMinggu = new TreeMap<>();
        for (Kebutuhan keb : daftarKebutuhan) {
            LocalDate kunciMinggu = awalMinggu(keb.tanggal);
            perMinggu.computeIfAbsent(kunciMinggu, x -> new ArrayList<>()).add(keb);
        }

        List<LocalDate> kunciTerurut = new ArrayList<>(perMinggu.keySet());
        LocalDate hariIni = LocalDate.now();
        DateTimeFormatter fmtPendek = DateTimeFormatter.ofPattern("dd/MM");

        System.out.println("\n=== RINGKASAN MINGGUAN ===");
        for (int i = 0; i < kunciTerurut.size(); i++) {
            LocalDate mulai = kunciTerurut.get(i);
            LocalDate akhir = akhirMinggu(mulai);
            List<Kebutuhan> isiMinggu = perMinggu.get(mulai);

            double totalMinggu = 0;
            for (Kebutuhan keb : isiMinggu) {
                totalMinggu += keb.harga;
            }

            boolean sudahSelesai = akhir.isBefore(hariIni);
            String label = "Minggu " + (i + 1);

            if (sudahSelesai) {
                // Minggu yang sudah lewat -> tampil terpangkas
                System.out.printf("%d. [>] %s (%s - %s) total = Rp%,.0f%n",
                        i + 1, label, mulai.format(fmtPendek), akhir.format(fmtPendek), totalMinggu);
            } else {
                // Minggu yang masih berjalan -> tampil terbuka + detailnya
                System.out.printf("%d. [v] %s (%s - %s) [BERJALAN] total sementara = Rp%,.0f%n",
                        i + 1, label, mulai.format(fmtPendek), akhir.format(fmtPendek), totalMinggu);
                for (Kebutuhan keb : isiMinggu) {
                    System.out.println("       - " + keb);
                }
            }
        }

        System.out.print("\nKetik nomor minggu untuk lihat detail (0 = kembali ke menu): ");
        int pilih;
        try {
            pilih = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return;
        }

        if (pilih < 1 || pilih > kunciTerurut.size()) {
            return;
        }

        LocalDate mulaiTerpilih = kunciTerurut.get(pilih - 1);
        LocalDate akhirTerpilih = akhirMinggu(mulaiTerpilih);
        List<Kebutuhan> isi = perMinggu.get(mulaiTerpilih);
        DateTimeFormatter fmtPanjang = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("\n=== DETAIL Minggu " + pilih + " ("
                + mulaiTerpilih.format(fmtPanjang) + " - " + akhirTerpilih.format(fmtPanjang) + ") ===");
        double total = 0;
        for (Kebutuhan keb : isi) {
            System.out.println("- " + keb);
            total += keb.harga;
        }
        System.out.printf("Total minggu ini: Rp%,.0f%n", total);
    }

    // ==========================================================
    // BAGIAN PENYIMPANAN FILE
    // Supaya data tidak hilang tiap program ditutup, dan program
    // bisa dipakai terus-menerus setiap hari.
    // ==========================================================

    private static void muatData() {
        File file = new File(FILE_DATA);
        if (!file.exists()) {
            return; // pertama kali dijalankan, belum ada data
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                if (baris.trim().isEmpty()) continue;
                try {
                    daftarKebutuhan.add(Kebutuhan.fromCsv(baris));
                } catch (Exception e) {
                    System.out.println("Baris data rusak dilewati: " + baris);
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca data lama: " + e.getMessage());
        }
    }

    private static void simpanSatuData(Kebutuhan keb) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_DATA, true))) {
            writer.write(keb.toCsv());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data: " + e.getMessage());
        }
    }

    private static void muatDataSaldo() {
        File file = new File(FILE_SALDO);
        if (!file.exists()) {
            return; // pertama kali dijalankan, belum ada data
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                if (baris.trim().isEmpty()) continue;
                try {
                    daftarPemasukan.add(Pemasukan.fromCsv(baris));
                } catch (Exception e) {
                    System.out.println("Baris data pemasukan rusak dilewati: " + baris);
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca data pemasukan lama: " + e.getMessage());
        }
    }

    private static void simpanSatuPemasukan(Pemasukan p) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_SALDO, true))) {
            writer.write(p.toCsv());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data pemasukan: " + e.getMessage());
        }
    }
}