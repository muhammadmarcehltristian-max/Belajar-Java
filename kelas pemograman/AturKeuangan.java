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

    // Jenis transaksi tabungan
    enum JenisTabungan {
        SETOR, // uang dari pemasukan dipindah ke tabungan (terkunci)
        TARIK  // uang tabungan dikembalikan ke uang bebas (setelah buka kunci)
    }

    // Kode konfirmasi yang harus diketik user untuk membuka kunci tabungan
    private static final String KODE_BUKA_KUNCI = "BUKA";

    // Mengubah tanggal menjadi nama hari bahasa Indonesia (real-time dari sistem)
    static String namaHari(LocalDate tanggal) {
        switch (tanggal.getDayOfWeek()) {
            case MONDAY:    return "Senin";
            case TUESDAY:   return "Selasa";
            case WEDNESDAY: return "Rabu";
            case THURSDAY:  return "Kamis";
            case FRIDAY:    return "Jumat";
            case SATURDAY:  return "Sabtu";
            default:        return "Minggu";
        }
    }

    // Class untuk merepresentasikan satu data kebutuhan
    static class Kebutuhan {
        Kategori kategori;
        String nama;       // nama makanan/minuman/barang, misal "Nasi Goreng", "Token Listrik"
        double harga;
        String hari;       // OTOMATIS dari sistem (sesuai hari real-time)
        LocalDate tanggal; // OTOMATIS dari sistem, tidak diinput user

        Kebutuhan(Kategori kategori, String nama, double harga, String hari, LocalDate tanggal) {
            this.kategori = kategori;
            this.nama = nama;
            this.harga = harga;
            this.hari = hari;
            this.tanggal = tanggal;
        }

        String toCsv() {
            String namaAman = nama.replace(";", ",");
            return kategori + ";" + namaAman + ";" + harga + ";" + hari + ";" + tanggal;
        }

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

        String toCsv() {
            String namaAman = nama.replace(";", ",");
            return namaAman + ";" + jumlah + ";" + tanggal;
        }

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
            // hari ditampilkan otomatis dari tanggal, jadi format file lama tetap kompatibel
            return String.format("%-15s | Rp%,.0f | %-10s | %s",
                    nama, jumlah, namaHari(tanggal), tanggal.format(fmt));
        }
    }

    // Class untuk satu transaksi tabungan (setor / tarik)
    static class Tabungan {
        JenisTabungan jenis;
        String keterangan; // tujuan menabung / alasan menarik
        double jumlah;
        String hari;       // OTOMATIS
        LocalDate tanggal; // OTOMATIS

        Tabungan(JenisTabungan jenis, String keterangan, double jumlah, String hari, LocalDate tanggal) {
            this.jenis = jenis;
            this.keterangan = keterangan;
            this.jumlah = jumlah;
            this.hari = hari;
            this.tanggal = tanggal;
        }

        String toCsv() {
            String ketAman = keterangan.replace(";", ",");
            return jenis + ";" + ketAman + ";" + jumlah + ";" + hari + ";" + tanggal;
        }

        static Tabungan fromCsv(String baris) {
            String[] bagian = baris.split(";");
            JenisTabungan j = JenisTabungan.valueOf(bagian[0]);
            String ket = bagian[1];
            double jml = Double.parseDouble(bagian[2]);
            String hari = bagian[3];
            LocalDate tgl = LocalDate.parse(bagian[4]);
            return new Tabungan(j, ket, jml, hari, tgl);
        }

        @Override
        public String toString() {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return String.format("%-6s | %-20s | Rp%,.0f | %-10s | %s",
                    jenis, keterangan, jumlah, hari, tanggal.format(fmt));
        }
    }

    // Nama file penyimpanan data (dibuat di folder tempat program dijalankan)
    private static final String FILE_DATA = "data_kebutuhan.csv";
    private static final String FILE_SALDO = "data_pemasukan.csv";
    private static final String FILE_TABUNGAN = "data_tabungan.csv";

    private static List<Kebutuhan> daftarKebutuhan = new ArrayList<>();
    private static List<Pemasukan> daftarPemasukan = new ArrayList<>();
    private static List<Tabungan> daftarTabungan = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        muatData();
        muatDataSaldo();
        muatDataTabungan();

        boolean lanjut = true;
        while (lanjut) {
            tampilkanMenu();
            int pilihan = bacaPilihan();

            switch (pilihan) {
                case 1:  tambahKebutuhan(); break;
                case 2:  tampilkanSemuaKebutuhan(); break;
                case 3:  lihatRingkasanMingguan(); break;
                case 4:  hitungTotalPerKategori(); break;
                case 5:  hitungTotalKeseluruhan(); break;
                case 6:  tambahPemasukan(); break;
                case 7:  ringkasanSaldo(); break;
                case 8:  setorTabungan(); break;
                case 9:  rincianTabungan(); break;
                case 10: bukaKunciTabungan(); break;
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
        LocalDate hariIni = LocalDate.now();
        System.out.println("\n=== APLIKASI ATUR KEUANGAN ===");
        System.out.println("Hari ini: " + namaHari(hariIni) + ", "
                + hariIni.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        System.out.println("1.  Tambah Kebutuhan");
        System.out.println("2.  Lihat Semua Kebutuhan");
        System.out.println("3.  Ringkasan Mingguan");
        System.out.println("4.  Total Per Kategori");
        System.out.println("5.  Total Keseluruhan Pengeluaran");
        System.out.println("6.  Tambah Uang Masuk (Saldo)");
        System.out.println("7.  Ringkasan Saldo (Uang Masuk, Pengeluaran, Tabungan, Sisa Uang)");
        System.out.println("8.  Tabung Uang (pisahkan dari pemasukan, terkunci)");
        System.out.println("9.  Rincian Tabungan");
        System.out.println("10. Ambil Tabungan (buka kunci)");
        System.out.println("0.  Keluar");
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
        if (harga <= 0) {
            System.out.println("Harga harus lebih dari 0.");
            return;
        }

        // Uang tabungan terkunci: pengeluaran tidak boleh memakai uang tabungan
        double saldoTabungan = hitungSaldoTabungan();
        if (saldoTabungan > 0 && harga > hitungSisaUangBebas()) {
            System.out.printf("Ditolak! Uang bebas hanya Rp%,.0f, sedangkan uang tabungan (Rp%,.0f) terkunci.%n",
                    hitungSisaUangBebas(), saldoTabungan);
            System.out.println("Gunakan menu 10 jika memang ingin membuka tabungan.");
            return;
        }

        // Hari & tanggal OTOMATIS sesuai real-time sistem
        LocalDate hariIni = LocalDate.now();
        String hari = namaHari(hariIni);

        Kebutuhan baru = new Kebutuhan(kategoriTerpilih, nama, harga, hari, hariIni);
        daftarKebutuhan.add(baru);
        simpanSatuData(baru);

        System.out.println("Tersimpan! (hari & tanggal otomatis: " + baru.hari + ", " + baru.tanggal + ")");
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
        if (jumlah <= 0) {
            System.out.println("Jumlah harus lebih dari 0.");
            return;
        }

        Pemasukan baru = new Pemasukan(nama, jumlah, LocalDate.now());
        daftarPemasukan.add(baru);
        simpanSatuPemasukan(baru);

        System.out.println("Uang masuk tersimpan! (hari & tanggal otomatis: "
                + namaHari(baru.tanggal) + ", " + baru.tanggal + ")");
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

    // Saldo tabungan = total setor - total tarik
    private static double hitungSaldoTabungan() {
        double total = 0;
        for (Tabungan t : daftarTabungan) {
            if (t.jenis == JenisTabungan.SETOR) {
                total += t.jumlah;
            } else {
                total -= t.jumlah;
            }
        }
        return total;
    }

    // Uang yang BOLEH dipakai = pemasukan - pengeluaran - tabungan (yang terkunci)
    private static double hitungSisaUangBebas() {
        return hitungTotalPemasukan() - hitungTotalPengeluaran() - hitungSaldoTabungan();
    }

    private static void ringkasanSaldo() {
        double totalMasuk = hitungTotalPemasukan();
        double totalKeluar = hitungTotalPengeluaran();
        double tabungan = hitungSaldoTabungan();
        double sisaBebas = hitungSisaUangBebas();

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
        System.out.printf("Total Uang Masuk              : Rp%,.0f%n", totalMasuk);
        System.out.printf("Total Pengeluaran             : Rp%,.0f%n", totalKeluar);
        System.out.printf("Uang Tabungan (terkunci)      : Rp%,.0f%n", tabungan);
        System.out.printf("Sisa Uang Bebas (bisa dipakai): Rp%,.0f%n", sisaBebas);

        if (sisaBebas < 0) {
            System.out.println("Perhatian: pengeluaran sudah melebihi uang bebas!");
        }
    }

    // ==========================================================
    // BAGIAN TABUNGAN (uang dipisahkan & terkunci)
    // ==========================================================

    private static void setorTabungan() {
        double sisaBebas = hitungSisaUangBebas();
        System.out.printf("%nUang bebas saat ini: Rp%,.0f%n", sisaBebas);
        if (sisaBebas <= 0) {
            System.out.println("Tidak ada uang bebas yang bisa ditabung.");
            return;
        }

        System.out.print("Masukkan tujuan menabung (contoh: Dana Darurat, Beli Laptop): ");
        String ket = scanner.nextLine().trim();
        if (ket.isEmpty()) {
            ket = "-";
        }

        System.out.print("Masukkan jumlah yang ingin ditabung: ");
        double jumlah;
        try {
            jumlah = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Jumlah tidak valid.");
            return;
        }
        if (jumlah <= 0) {
            System.out.println("Jumlah harus lebih dari 0.");
            return;
        }
        if (jumlah > sisaBebas) {
            System.out.printf("Jumlah melebihi uang bebas (Rp%,.0f).%n", sisaBebas);
            return;
        }

        LocalDate hariIni = LocalDate.now();
        Tabungan t = new Tabungan(JenisTabungan.SETOR, ket, jumlah, namaHari(hariIni), hariIni);
        daftarTabungan.add(t);
        simpanSatuTabungan(t);

        System.out.printf("Berhasil menabung Rp%,.0f. Uang ini terkunci dan tidak ikut dihitung sebagai uang bebas.%n", jumlah);
        System.out.printf("Total tabungan sekarang: Rp%,.0f%n", hitungSaldoTabungan());
    }

    private static void rincianTabungan() {
        if (daftarTabungan.isEmpty()) {
            System.out.println("\nBelum ada data tabungan.");
            return;
        }

        double totalSetor = 0;
        double totalTarik = 0;

        System.out.println("\n=== RINCIAN TABUNGAN ===");
        System.out.printf("%-3s %-6s | %-20s | %-10s | %-10s | %s%n",
                "No", "Jenis", "Keterangan", "Jumlah", "Hari", "Tanggal");
        for (int i = 0; i < daftarTabungan.size(); i++) {
            Tabungan t = daftarTabungan.get(i);
            System.out.println((i + 1) + ". " + t);
            if (t.jenis == JenisTabungan.SETOR) {
                totalSetor += t.jumlah;
            } else {
                totalTarik += t.jumlah;
            }
        }

        // Rincian total per tujuan menabung (setor dikurangi tarik)
        Map<String, Double> perTujuan = new TreeMap<>();
        for (Tabungan t : daftarTabungan) {
            double nilai = (t.jenis == JenisTabungan.SETOR) ? t.jumlah : -t.jumlah;
            perTujuan.merge(t.keterangan, nilai, Double::sum);
        }

        System.out.println("\nRincian per tujuan:");
        for (Map.Entry<String, Double> e : perTujuan.entrySet()) {
            System.out.printf("- %-20s : Rp%,.0f%n", e.getKey(), e.getValue());
        }

        System.out.println();
        System.out.printf("Total Setor                : Rp%,.0f%n", totalSetor);
        System.out.printf("Total Tarik                : Rp%,.0f%n", totalTarik);
        System.out.printf("Saldo Tabungan (terkunci)  : Rp%,.0f%n", totalSetor - totalTarik);
    }

    // Tabungan hanya bisa diambil jika user mengetik kode konfirmasi
    private static void bukaKunciTabungan() {
        double saldo = hitungSaldoTabungan();
        System.out.printf("%nSaldo tabungan terkunci: Rp%,.0f%n", saldo);
        if (saldo <= 0) {
            System.out.println("Tidak ada uang di tabungan.");
            return;
        }

        System.out.println("Tabungan dikunci supaya tidak terpakai sembarangan.");
        System.out.print("Ketik \"" + KODE_BUKA_KUNCI + "\" untuk membuka kunci (selain itu = batal): ");
        String kode = scanner.nextLine().trim();
        if (!kode.equals(KODE_BUKA_KUNCI)) {
            System.out.println("Dibatalkan. Tabungan tetap terkunci.");
            return;
        }

        System.out.print("Masukkan alasan pengambilan: ");
        String alasan = scanner.nextLine().trim();
        if (alasan.isEmpty()) {
            alasan = "-";
        }

        System.out.print("Masukkan jumlah yang diambil: ");
        double jumlah;
        try {
            jumlah = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Jumlah tidak valid.");
            return;
        }
        if (jumlah <= 0) {
            System.out.println("Jumlah harus lebih dari 0.");
            return;
        }
        if (jumlah > saldo) {
            System.out.printf("Jumlah melebihi saldo tabungan (Rp%,.0f).%n", saldo);
            return;
        }

        LocalDate hariIni = LocalDate.now();
        Tabungan t = new Tabungan(JenisTabungan.TARIK, alasan, jumlah, namaHari(hariIni), hariIni);
        daftarTabungan.add(t);
        simpanSatuTabungan(t);

        System.out.printf("Berhasil mengambil Rp%,.0f dari tabungan. Uang kembali menjadi uang bebas.%n", jumlah);
        System.out.printf("Sisa tabungan: Rp%,.0f%n", hitungSaldoTabungan());
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
                System.out.printf("%d. [>] %s (%s - %s) total = Rp%,.0f%n",
                        i + 1, label, mulai.format(fmtPendek), akhir.format(fmtPendek), totalMinggu);
            } else {
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
    // ==========================================================

    private static void muatData() {
        File file = new File(FILE_DATA);
        if (!file.exists()) {
            return;
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
            return;
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

    private static void muatDataTabungan() {
        File file = new File(FILE_TABUNGAN);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                if (baris.trim().isEmpty()) continue;
                try {
                    daftarTabungan.add(Tabungan.fromCsv(baris));
                } catch (Exception e) {
                    System.out.println("Baris data tabungan rusak dilewati: " + baris);
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca data tabungan lama: " + e.getMessage());
        }
    }

    private static void simpanSatuTabungan(Tabungan t) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_TABUNGAN, true))) {
            writer.write(t.toCsv());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data tabungan: " + e.getMessage());
        }
    }
}
