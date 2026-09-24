import java.util.Scanner;

public class GradeMahasiswa {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Masukkan nilai ujian : ");
        int nilai = input.nextInt();

        if (nilai < 0 || nilai > 100) {
            System.out.println("Input tidak valid");
        } else if (nilai >= 85) {
            System.out.println("Nilai " + nilai + " -> Grade A");
        } else if (nilai >= 75) {
            System.out.println("Nilai " + nilai + " -> Grade B");
        } else if (nilai >= 65) {
            System.out.println("Nilai " + nilai + " -> Grade C");
        } else {
            System.out.println("Nilai " + nilai + " -> Tidak Lulus");
        }

        input.close();
    }
}