public class DataSiswa {
    public static void main(String[] args) {
        // Tipe data primitif
        String nama = "Budi Santoso";
        int umur = 17;
        double tinggiBadan = 165.5;
        char kelas = 'B';
        if (umur >= 17) {
            System.out.println(nama + " sudah cukup umur untuk mengikuti ujian.");
        } else {
            System.out.println(nama + " belum cukup umur untuk mengikuti ujian.");
        }
        long nomorInduk = 202310012345L;
        float beratBadan = 55.5f;

        // Tipe data referensi (array)
        int[] nilaiUjian = {85, 90, 78, 92, 88};

        // Operasi sederhana antar tipe data
        int totalNilai = 0;
        for (int i = 0; i < nilaiUjian.length; i++) {
            totalNilai += nilaiUjian[i];
        }
        double rataRata = (double) totalNilai / nilaiUjian.length;

        // Cek kelulusan berdasarkan rata-rata nilai
        boolean sudahLulus;
        if (rataRata >= 75) {
            sudahLulus = true;
        } else {
            sudahLulus = false;
        }

        // Output
        System.out.println("=== Data Siswa ===");
        System.out.println("Nama         : " + nama);
        System.out.println("Umur         : " + umur + " tahun");
        System.out.println("Tinggi Badan : " + tinggiBadan + " cm");
        System.out.println("Berat Badan  : " + beratBadan + " kg");
        System.out.println("Kelas        : " + kelas);
        System.out.println("No. Induk    : " + nomorInduk);
        System.out.println("Sudah Lulus  : " + sudahLulus);
        System.out.println("Rata-rata    : " + rataRata);
    }
}