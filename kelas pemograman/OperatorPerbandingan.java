import java.util.Scanner;

public class OperatorPerbandingan {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Masukkan angka pertama: ");
        int angka1 = input.nextInt();

        System.out.print("Masukkan angka kedua: ");
        int angka2 = input.nextInt();

        System.out.println();
        System.out.println("===== HASIL PERBANDINGAN =====");

        System.out.println("Angka pertama > angka kedua  : " + (angka1 > angka2));
        System.out.println("Angka pertama < angka kedua  : " + (angka1 < angka2));
        System.out.println("Angka pertama >= angka kedua : " + (angka1 >= angka2));
        System.out.println("Angka pertama <= angka kedua : " + (angka1 <= angka2));
        System.out.println("Angka pertama == angka kedua : " + (angka1 == angka2));
        System.out.println("Angka pertama != angka kedua : " + (angka1 != angka2));

        input.close();
    }
}