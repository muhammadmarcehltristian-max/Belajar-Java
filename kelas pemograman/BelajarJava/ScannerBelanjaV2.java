import java.util.Scanner;

public class ScannerBelanjaV2 {

    static double getpersenDiscount(char kategori) {
        if (kategori == 'K') return 0.10;
        else if (kategori == 'M') return 0.15;
        else if (kategori == 'P') return 0.20;
        else return 0.0;
    }
    
    static char inputkategoriValid(Scanner scanner) {
       char  kategori;
       while (true) {
        System.out.println("Items Category(K: Needs, M: Food, P: Clothes):");
        kategori = scanner.next().charAt(0);            

        if (kategori == 'K' || kategori == 'M' || kategori == 'P') {
            break;
        }else {
            System.out.println("Invalid category, please try again.");
        }
       }
       return kategori;
    }


    static int inputangkaPositif(Scanner scanner, String Item) {
     int angka;
      while (true) {
        System.out.println(Item);
        angka = scanner.nextInt();
        if (angka > 0){
            break;
        }else {
          System.out.println("The number entered must be greater than 0, please try again.");  
        }
      }  
      return angka;
    }

    static double inputhargaPositif(Scanner scanner, String Item) {
        double harga;
        while (true) {
            System.out.print(Item);
            harga = scanner.nextDouble();
            if (harga > 0) {
                break;
            } else {
                System.out.println("The number entered must be greater than 0, please try again.");
            }
        }
        return harga;
    }

    public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
       System.out.println("Welcome to the shopping program!");
       System.out.println("Please enter your shopping data.");

       System.out.println("\n ====== SHOPPING RECEIPT =====");

       double totalsebelumDiskon = 0;
       double totalsetelahDiskon = 0;

       System.out.println("How many items do you want to buy? ");
        int totalitems = scanner.nextInt();
        scanner.nextLine();

        String[] namaBarang = new String[totalitems];
        int[] jumlahItems = new int[totalitems];
        char[] kategoriBarang = new char[totalitems];
        double[] hargasetelahDiskon = new double[totalitems];

        for (int i = 1; i <= totalitems; i++){
        System.out.println("\n===== ITEM" + i + "===== ");

        System.out.println("Input name of item:");
        namaBarang[i -1] = scanner.nextLine();

            System.out.print("Input quantity purchased: ");
             jumlahItems[i -1] = scanner.nextInt();

            System.out.print("Input item category (K: Needs, M: Food, P: Clothing): ");
            char kategori = scanner.next().charAt(0);

            double persenDiskon = getpersenDiscount(kategori);
            System.out.println("Discount recived: " + (int) (persenDiskon * 100) + "%");

            double hargaBarang = inputhargaPositif(scanner, "Item price: ");

            double subtotal = hargaBarang * jumlahItems[i -1];
            System.out.printf("Subtotal: %.2f%n", subtotal);

            double totalHarga = subtotal - (subtotal * persenDiskon);
            System.out.printf("Price after discount: %.2f%n", totalHarga);

            hargasetelahDiskon[i-1] = totalHarga;

            totalsebelumDiskon += subtotal;
            totalsetelahDiskon += totalHarga;

            scanner.nextLine();
        }

        double uangDibayar;
        while (true) {
            System.out.println("\nAmount paid: ");
            uangDibayar = scanner.nextDouble();
        if (uangDibayar >= totalsetelahDiskon){
        break;
        }else {
            System.out.printf("Insufficient funds! Total amount due: %.2f. Please try again.%n", totalsetelahDiskon);
        }
    }

    System.out.println("\n=====================");
    System.out.println("Item: " + totalitems);
    System.out.printf("Total price before discount: %.2f%n", totalsebelumDiskon);
    System.out.printf("Total price after discount: %.2f%n", totalsetelahDiskon);
    System.out.printf("You saved: %.2f%n", totalsebelumDiskon - totalsetelahDiskon);
    System.out.printf("Amount paid: %.2f%n", uangDibayar);
    System.out.printf("Change: %.2f%n", uangDibayar - totalsetelahDiskon);

    System.out.println("\n===== SPENDING SUMMARY =====");
    for (int i = 0; i < totalitems; i++) {
        System.out.printf("%d. %s (%d items, kategori %c) - Rp%.2f%n",
            i +1, namaBarang[i], jumlahItems[i], kategoriBarang[i], hargasetelahDiskon[i]);
    }
    System.out.println("\n===== THANK YOU FOR COMING =====");
    scanner.close();
    }
}