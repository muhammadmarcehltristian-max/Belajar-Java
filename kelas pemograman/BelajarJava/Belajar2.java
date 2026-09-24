
import java.util.Scanner;

public class Belajar2 {

    static double getpercentDiscount(char kategori) {
        if (kategori == 'A') return 0.50;
        else if (kategori == 'D') return 0.25;
        else if (kategori == 'M') return 0.20;
        else if (kategori == 'm') return 0.15;
        else return 0.0;
    }

    static char inputkategoriValid(Scanner scanner) {
        char kategori;
        while(true) {
        System.out.println("Masukkan kategori tiket (A: Anak, D: Dewasa):");
        kategori = scanner.next().charAt(0);

        System.out.println("Masukkan Kategori makanan/minuman (M: Makanan, m: Minuman):");
        kategori = scanner.next().charAt(kategori);

        if (kategori == 'A' || kategori == 'D' || kategori == 'M' || kategori == 'm'){
        break;
    }else {
        System.out.println("Kategori tidak valid, coba lagi!");
    }
        }
    return kategori;
}

    static int inputangkaPositif(Scanner scanner, String Tiket) {
        int angka;
        while (true) {
            System.out.println(Tiket);
            angka = scanner.nextInt();
        if(angka > 0) {
            break;
        }else {
            System.out.println("Angka yang dimasukkan tidak valid, coba lagi!");
        }
    }
        return angka;
    }

    static int inputangkapositif(Scanner scanner, String Lainnya) {
        int angka;
        while (true) {
            System.out.println(Lainnya);
            angka = scanner.nextInt();
        if (angka > 0) {
            break;
        }else {
            System.out.println("Angka tidak valid, coba lagi!");
        }
        }
        return angka;
    }

    static double inputhargaValid(Scanner scanner, String Tiket) {
        double harga;
        while (true) {
            System.out.println(Tiket);
            harga = scanner.nextDouble();
        if(harga > 0){
            break;
        }else {
            System.out.println("Uang yang dimaukkan tidak cukup, coba lagi!");
        }
        }
        return harga;
    }

    static double inputhargavalid(Scanner scanner, String Lainnya) {
        double harga;
        while (true) {
            System.out.println(Lainnya);
            harga = scanner.nextDouble();
        if(harga > 0){
            break;
        }else {
            System.out.println("Uang yang dimaukkan tidak cukup, coba lagi!");
        }
        }
        return harga;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("SELAMAT DATANG DI BIOSKOP SUKA MAJU!");
        System.out.println("SILAHKAN MASUKKAN DATA PEMBELIAN ANDA!");

        System.out.println("\n==== RECIPT ====");

        double totalhargasebelumdiskon;
        double totalhargasetelahdiskon;

        System.out.println("Berapa tiket yang akan dibeli?");
        int totaltiket = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Barang lainnya?");
        int totallainnya = scanner.nextInt();
        scanner.nextLine();

        String[] namaFilm = new String[totaltiket];
        String[] namaBarang = new String[totallainnya];
        int[] jumlahItems = new int[totaltiket];
        int[] jumlahitems = new int[totallainnya];
        char[] Kategori = new char[totaltiket];
        char[] kategori = new char[totallainnya];
        double[] hargasetelahDiskon = new double[totaltiket];
        double[] hargasetelahdiskon = new double[totallainnya];


        for(int i = 1; i <= totaltiket; i++) {
            System.out.println("\n==== Tiket" + i + "====");
            
            System.out.print("Nama Film:");
            namaFilm[i-1] = scanner.nextLine();

            jumlahItems[i-1] = inputangkaPositif(scanner, "Jumlah tiket:");

            char kategorI = inputkategoriValid(scanner);
            kategori[i-1] = kategorI;

            double persenDiskon = getpercentDiscount(kategorI);
            System.out.println("Besar diskon:" + (int) (persenDiskon * 100) + "%");

            double hargaTiket = inputhargaValid(scanner, "Harga tiket per orang");

            double subtotal = hargaTiket * jumlahItems[i-1];
            
         }
     }
}
