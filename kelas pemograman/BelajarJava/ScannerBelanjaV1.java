import java.util.Scanner;

public class ScannerBelanjaV1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the shopping program!");
        System.out.println("Please enter your shopping data.");

        System.out.println("\n===== SHOPPING RECEIPT =====");

        double totalsebelumdiscount = 0;
        double totalsetelahdiscount = 0;

        System.out.println("How many items do you want to buy? ");
        int totalitems = scanner.nextInt();scanner.nextLine();
        String[] namaBarang = new String[totalitems];
        int[] jumlahItem = new int[totalitems];
        for (int i = 1; i <= totalitems; i++) {
            System.out.println("\n===== ITEM " + i + " =====");

            System.out.print("Input name of item: ");
             namaBarang[i -1] = scanner.nextLine();

            System.out.print("Input quantity purchased: ");
             jumlahItem[i -1] = scanner.nextInt();

            System.out.print("Input item category (K: Needs, M: Food, P: Clothing): ");
            char kategori = scanner.next().charAt(0);

            if (kategori == 'K') {
                System.out.println("Category: Needs");
            } else if (kategori == 'M') {
                System.out.println("Category: Food");
            } else if (kategori == 'P') {
                System.out.println("Category: Clothing");
            } else {
                System.out.println("Invalid category.");
            }
            if (kategori == 'K') {
                System.out.println("Discount 10% for need items.");
            } else if (kategori == 'M') {
                System.out.println("Discount 5% for food items.");
            } else if (kategori == 'P') {
                System.out.println("Discount 15% for clothing items.");
            }
            System.out.println("Discount received: " + (kategori == 'K' ? "10%" : kategori == 'M' ? "5%" : kategori == 'P' ? "15%" : "No discount"));
            System.out.print("Item price: ");
            double hargaBarang = scanner.nextDouble();
            System.out.println("Subtotal:" + hargaBarang * jumlahItem[i - 1]);
            double subtotal = hargaBarang * jumlahItem[i- 1];
            double totalHarga = subtotal - (kategori == 'K' ? subtotal * 0.1 : kategori == 'M' ? subtotal * 0.05 : kategori == 'P' ? subtotal * 0.15 : 0);
            System.out.println("Item price after discount: " + totalHarga);
            totalsebelumdiscount += subtotal;
            totalsetelahdiscount += totalHarga;

            scanner.nextLine(); // Consume the newline character
        }
        System.out.println("Input amount paid:");
        double amountpaid = scanner.nextDouble();
        System.out.println("\n ============================");

        System.out.println("Total all items purchased: " + totalitems);
        System.out.println("Total price before discount:" + totalsebelumdiscount);
        System.out.println("Total price after discount: " + totalsetelahdiscount);
        System.out.println("You saved:" + (totalsebelumdiscount - totalsetelahdiscount));
        System.out.println("Amount paid:" + amountpaid);
        System.out.println("Change:" + (amountpaid - totalsetelahdiscount));
        System.out.println("\n===== SHOPPING SUMMARY =====");
        System.out.println("\n===== THANK YOU FOR COMING =====");
        scanner.close();
    }
}