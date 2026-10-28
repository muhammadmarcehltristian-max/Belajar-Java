import java.util.Scanner;

public class LayananLundry {
    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("\n==== Daftar Laundry ====");
        System.out.println("1. Reguler");
        System.out.println("2. Express");
        System.out.println("3. Super express");
        System.out.println("\n=======================");

        System.out.println("Masukkan nomor laundry:");
        int nomorlaundry = input.nextInt();

        System.out.println("Masukkan berat laundry: ");
        double beratLaundry = input.nextDouble();

        String Tipelaundry;
        double harga;
        double Diskon;
        double totalharga;
        double subtotal;

        switch (nomorlaundry) {
        case 1:
            Tipelaundry = "Regular";
            harga = 7000.0;
            break;
        case 2:
            Tipelaundry = "Express";
            harga = 10000.0;
            break;
        case 3:
            Tipelaundry = "Super express";
            harga = 15000.0;
            break;
        default:
        Tipelaundry = null;
        harga = 0;
        break;
    }
    
            if(beratLaundry > 5){
                Diskon = 5000.0;
            }else {
                Diskon = 0.0;
            }

        if(Tipelaundry != null){
            System.out.println("Tipe     :" + Tipelaundry);
            subtotal = harga * beratLaundry;
            totalharga =  subtotal - Diskon;
            System.out.println("Total harga:" + subtotal);
            System.out.println("Harga akhir   :" + totalharga);
        }else{
            System.out.println("Tipe laundry tidak valid, coba lagi");
        }
        input.close();
      }
    }