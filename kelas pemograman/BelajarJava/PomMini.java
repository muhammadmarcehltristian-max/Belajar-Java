import java.util.Scanner;

public class PomMini {
    public static void main(String[] args) {
       Scanner input = new Scanner(System.in);
         System.out.println("\n==== SELAMAT DATANG DI POM MINI ====");
         System.out.println("1. Pertamax");
         System.out.println("2. Pertamax Turbo");
         System.out.println("3. Pertalite");
         
         System.out.println("\n=====================================");

    System.out.println("Masukkan Kategori: ");
    int Kategori = input.nextInt();
    System.out.println("Masukkan Berapa liter: ");
    double Liter = input.nextDouble();
       
      String Jenisbahanbakar;
      double harga;
      double total;

    switch (Kategori) {
        case 1:
            Jenisbahanbakar = "Pertamax";
            harga = 15000.0;
            break;
        case 2:
            Jenisbahanbakar = "Pertamax Turbo";
            harga = 20000.0;
            break;
        case 3:
            Jenisbahanbakar = "Pertalite";
            harga = 10000.0;
            break;
        default:
            Jenisbahanbakar = null;
            harga = 0;
            break;
    }
        if(Jenisbahanbakar != null){
            System.out.println("Kategori : " + Kategori);
            total = harga * Liter;
            System.out.println("Jumlah yang harus dibayar :" + total);
        }else {
            System.out.println("Tidak valid, mohon ulang lagi!");
        }
        input.close();
    }
}