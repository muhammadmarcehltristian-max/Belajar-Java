import java.util.Scanner;

public class BencoolenFood {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== DAFTAR MENU BENCOOLEN FOOD ===");
        System.out.println("1. Nasi Goreng");
        System.out.println("2. Mie Ayam");
        System.out.println("3. Soto Ayam");
        System.out.println("4. Bakso");
        System.out.println("===================================");

        System.out.print("Masukkan nomor menu : ");
        int nomorMenu = input.nextInt();

        String namaMakanan;
        int harga;

        switch (nomorMenu) {
            case 1:
                namaMakanan = "Nasi Goreng";
                harga = 15000;
                break;
            case 2:
                namaMakanan = "Mie Ayam";
                harga = 12000;
                break;
            case 3:
                namaMakanan = "Soto Ayam";
                harga = 13000;
                break;
            case 4:
                namaMakanan = "Bakso";
                harga = 10000;
                break;
            default:
                namaMakanan = null;
                harga = 0;
                break;
        }

        System.out.println();
        if (namaMakanan != null) {
            System.out.println("Menu       : " + namaMakanan);
            System.out.println("Harga      : Rp" + harga);
        } else {
            System.out.println("Menu tidak tersedia");
        }

        input.close();
    }
}