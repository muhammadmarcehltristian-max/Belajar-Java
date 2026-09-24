import java.util.Scanner;

public class LatihanArray1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Masukkan jumlah mahasiswa:");
        int jumlahMahasiswa = scanner.nextInt();scanner.nextLine();
        String[] namaMahasiswa = new String[jumlahMahasiswa];
        System.out.println("Masukkan nilai mahasiswa:");
        int nilaiMahasiswa = scanner.nextInt();
        int[] nilai = new int[nilaiMahasiswa];
        
        System.out.println("Daftar Nilai Mahasiswa:");
        for (int i = 0; i < jumlahMahasiswa; i++) {
            System.out.println("namaMahasiswa:" + (i + 1) + " : " + nilai[i]);
                    namaMahasiswa[i] = scanner.nextLine();

                    System.out.print("Nilai: ");
                    nilai[i] = scanner.nextInt();
                          scanner.nextLine();
            System.out.println("Mahasiswa: " + namaMahasiswa[i] + ":" + nilai[i]);
        System.out.println("Hasil: " + namaMahasiswa[i] + "+" + nilai[i]);
        }
        scanner.close();
    }
}