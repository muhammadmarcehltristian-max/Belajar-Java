public class LatihanMethod {

    public static void main(String[] args) {
        double sisi = 9;
        double panjang = 8;
        double lebar = 5;
        double luas = hitungLuasPersegiPanjang(panjang, lebar);
        System.out.println("Luas persegi panjang: " + luas);
        double Keliling = hitungkelilingpersegipanjang(panjang, lebar);
        System.out.println("Keliling persegi panjang:" + Keliling);
        double Luas = hitungluaspersegi(sisi);
        System.out.println("Luas persegi:" + Luas);
        double keliling = hitungkelilingpersegi(sisi);
        System.out.println("Keliling persegi:" + keliling);


        cetakSalam("Marchel", 18, 72.0);

        double rataRata = hitungRataRata(80, 90, 70);
        System.out.println("Rata-rata: " + rataRata);
    }

    static double hitungLuasPersegiPanjang(double panjang, double lebar) {
        return panjang * lebar;
    }

    static double hitungkelilingpersegipanjang(double panjang, double lebar) {
        return 2 * (panjang + lebar);
    }

    static double hitungluaspersegi(double sisi) {
        return sisi * sisi;
    }

    static double hitungkelilingpersegi(double sisi) {
        return 4 * sisi;
    }

    static void cetakSalam(String nama, int umur, double beratBadan) {
        System.out.println("Halo, nama saya " + nama + ", umur " + umur
                + " tahun, dengan berat badan " + beratBadan + " kg.");
    }

    static double hitungRataRata(double a, double b, double c) {
        return (a + b + c) / 3;
    }
}