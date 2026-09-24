import java.util.Scanner;

public class AritmatikaScanner {

    public static void main(String[] args) {

        // Membuat Scanner
        Scanner input = new Scanner(System.in);

        // Input
        System.out.print("Masukkan angka pertama: ");
        int angka1 = input.nextInt();

        System.out.print("Masukkan angka kedua: ");
        int angka2 = input.nextInt();

        System.out.print("Masukkan angka ketiga: ");
        int angka3 = input.nextInt();

        System.out.print("Masukkan angka keempat: ");
        int angka4 = input.nextInt();

        // Operasi aritmatika
        int tambah = angka1 + angka2 + angka3 + angka4;
        int kurang = angka1 - angka2 - angka3 - angka4;
        int kali = angka1 * angka2 * angka3 * angka4;
        int bagi = angka1 / angka2;
        int modulus = angka1 % angka2;

        // Output
        System.out.println();
        System.out.println("===== HASIL PERHITUNGAN =====");
        System.out.println("Penjumlahan : " + tambah);
        System.out.println("Pengurangan : " + kurang);
        System.out.println("Perkalian   : " + kali);
        System.out.println("Pembagian   : " + bagi);
        System.out.println("Sisa bagi   : " + modulus);

        // Menutup Scanner
        input.close();
    }
}