import java.util.Scanner;

public class BelajarScanner {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Masukkan nama pemain:");
        String nama = input.nextLine();
        
        System.out.println("Masukkan umur pemain:");
        int umur = input.nextInt();
        input.nextLine();

        System.out.println("Masukkan posisi pemain:");
        String posisi = input.nextLine();

        System.out.println("Masukkan negara pemain:");
        String negara = input.nextLine();

        System.out.println("Masukkan tim pemain:");
        String tim = input.nextLine();


        System.out.println("Masukkan match pemain:");
        int match = input.nextInt();


        System.out.println("Masukkan goal pemain:");
        int goal = input.nextInt();

        System.out.println("Masukkan assist pemain:");
        int assist = input.nextInt();

        int totalGoalAssist = goal + assist;
        System.out.println("Total G/A: " + totalGoalAssist);

        if (umur <= 18 && totalGoalAssist >= 35 && match <= 30) {
            System.out.println("Pemain muda berbakat");
        } else if (umur >= 18 && umur <= 30 && totalGoalAssist >= 120 && match <= 100) {
            System.out.println("Pemain bintang");
        } else if (umur >= 30 && totalGoalAssist >= 500 && match <= 450) {
            System.out.println("Pemain legenda");
        } else {
            System.out.println("Pemain biasa");
        }

        System.out.println();
        System.out.println("===== DATA PEMAIN =====");
        System.out.println("Nama Pemain:" + "" + nama);
        System.out.println("Negara Pemain:" + "" + negara);
        System.out.println("Umur Pemain:" + "" + umur);
        System.out.println("Posisi Pemain:" + "" + posisi);
        System.out.println("Tim Pemain:" + "" + tim);
        System.out.println("Total Goal Pemain:" + "" + goal);
        System.out.println("Total Assist Pemain:" + "" + assist);
        System.out.println("Total Match Pemain:" + "" + match);

        input.close();
    }
}