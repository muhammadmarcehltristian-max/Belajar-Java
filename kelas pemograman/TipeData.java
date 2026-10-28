class TipeData {
    public static void main(String[] args) {
        // 1. Contoh Tipe Data
        String nama = "jamal";
        int angka1 = 100;
        int angka2 = 100;
        int angka3 = 50;

        // 2. Contoh Aritmatika
        int hasilkurang = angka1 - angka2 / angka3;
        int hasilkali = angka1 * angka2 - angka3;
        int hasilbagi = angka1 / angka2 * angka3;
        int hasilmodulus = angka1 % angka2;
        int hasiltambah = angka1 + angka2 - angka3;

        // 3. Menampilkan Output
        System.out.println("Nama: " + nama);
        System.out.println("Hasil kurang: " + hasilkurang);
        System.out.println("Hasil kali: " + hasilkali);
        System.out.println("Hasil bagi: " + hasilbagi);
        System.out.println("Hasil modulus: " + hasilmodulus);
        System.out.println("Hasil tambah: " + hasiltambah);
    }
}