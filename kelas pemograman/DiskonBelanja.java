    import java.util.Scanner;

public class DiskonBelanja {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Masukkan nama pelanggan : ");
        String nama = input.nextLine();

        System.out.print("Masukkan total belanja  : ");
        double totalBelanja = input.nextDouble();

        double persenDiskon;
        double besarDiskon;
        double totalBayar;

        if (totalBelanja >= 500000) {
            persenDiskon = 0.20;
        } else if (totalBelanja >= 250000) {
            persenDiskon = 0.10;
        } else {
            persenDiskon = 0.0;
        }

        besarDiskon = totalBelanja * persenDiskon;
        totalBayar = totalBelanja - besarDiskon;

        System.out.println("\n=== STRUK BELANJA ===");
        System.out.println("Nama Pelanggan   : " + nama);
        System.out.println("Total Belanja    : Rp" + totalBelanja);
        System.out.println("Diskon           : " + (int)(persenDiskon * 100) + "% (Rp" + besarDiskon + ")");
        System.out.println("Total Bayar      : Rp" + totalBayar);

        input.close();
    }
}