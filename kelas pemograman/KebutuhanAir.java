import java.util.Scanner;

public class KebutuhanAir {

    static double hitungAir(int jumlahTanaman, double kebutuhanAir) {
        return jumlahTanaman * kebutuhanAir;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Masukkan jumlah tanaman: ");
        int jumlahTanaman = input.nextInt();

        System.out.print("Masukkan kebutuhan air per tanaman (liter): ");
        double kebutuhanAir = input.nextDouble();

        double total = hitungAir(jumlahTanaman, kebutuhanAir);

        System.out.println("Total kebutuhan air: " + total + " liter");

        input.close();
    }
}