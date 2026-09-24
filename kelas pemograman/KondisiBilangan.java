import java.util.Scanner;

public class KondisiBilangan {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Masukkan sebuah bilangan bulat : ");
        int bilangan = input.nextInt();

        // Menentukan positif, negatif, atau nol
        if (bilangan > 0) {
            System.out.println("Bilangan " + bilangan + " adalah bilangan POSITIF");
        } else if (bilangan < 0) {
            System.out.println("Bilangan " + bilangan + " adalah bilangan NEGATIF");
        } else {
            System.out.println("Bilangan " + bilangan + " adalah NOL");
        }

        // Menentukan genap atau ganjil (hanya jika bukan nol)
        if (bilangan != 0) {
            if (bilangan % 2 == 0) {
                System.out.println("Bilangan " + bilangan + " adalah bilangan GENAP");
            } else {
                System.out.println("Bilangan " + bilangan + " adalah bilangan GANJIL");
            }
        }

        input.close();
    }
}