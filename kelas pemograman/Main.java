public class Main {
    public static void main(String[] args) {
        int nilai1 = 90;
        int nilai2 = 75;

        int rataRata = (nilai1 + nilai2) / 2;

        boolean lebihBesar = nilai1 > nilai2;
        boolean samaDengan = nilai1 == nilai2;

        boolean syarat = rataRata >= 80 && nilai2 >=70;

        System.out.println("Rata-rata nilai = " + rataRata);
        System.out.println("Apakah nilai1 lebih besar dari nilai2? " + lebihBesar);
        System.out.println("Apakah nilai1 sama dengan nilai2? " + samaDengan);
        System.out.println("Apakah rata-rata nilai1 >= 80 DAN nilai2 >= 70? " + syarat);
    }
}