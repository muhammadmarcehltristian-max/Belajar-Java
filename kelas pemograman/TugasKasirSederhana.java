public class TugasKasirSederhana {
    public static void main(String[] args) {
        String Namabarang = "Buku Tulis";
        int Kodebarang = 505;
        Double Hargabarang = 7000.0;
        boolean Sedangdiskon = true;
        char Kategori = 'P';
        double diskon = 0.1; // 10% diskon
        if (Sedangdiskon) {
            Hargabarang = Hargabarang - (Hargabarang * diskon);
        }
        System.out.println("Nama Barang: " + Namabarang);
        System.out.println("Kode Barang: " + Kodebarang);
        System.out.println("Harga Barang: " + Hargabarang);
        System.out.println("Sedang Diskon: " + Sedangdiskon);
        System.out.println("Kategori: " + Kategori);
    }
}