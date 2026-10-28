import java.util.Scanner;

public class CekSuhuRuangan {

    static void cekSuhu(double suhu) {
        if (suhu < 20) {
            System.out.println("Kondisi ruangan: Dingin");
        } else if (suhu <= 25) {
            System.out.println("Kondisi ruangan: Normal");
        } else {
            System.out.println("Kondisi ruangan: Panas");
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Masukkan suhu ruangan (°C): ");
        double suhu = input.nextDouble();

        cekSuhu(suhu);

        input.close();
    }
}