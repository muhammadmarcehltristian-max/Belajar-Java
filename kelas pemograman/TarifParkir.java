import java.util.Scanner;

public class TarifParkir {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Masukkan nama pemilik kendaraan : ");
        String nama = input.nextLine();

        System.out.print("Masukkan lama parkir (jam)      : ");
        int lamaParkir = input.nextInt();

        int tarif;

        if (lamaParkir <= 0) {
            System.out.println("\nInput tidak valid");
        } else {
            if (lamaParkir <= 2) {
                tarif = 5000;
            } else if (lamaParkir <= 5) {
                tarif = 10000;
            } else {
                tarif = 15000;
            }

            System.out.println("\n=== STRUK PARKIR ===");
            System.out.println("Nama Pemilik   : " + nama);
            System.out.println("Lama Parkir    : " + lamaParkir + " jam");
            System.out.println("Tarif          : Rp" + tarif);
        }

        input.close();
    }
}