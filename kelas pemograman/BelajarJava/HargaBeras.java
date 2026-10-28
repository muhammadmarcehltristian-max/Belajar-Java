import java.util.Scanner;

public class HargaBeras {

    static String formatRupiah(long angka) {
        return "Rp " + String.format("%,d", angka).replace(',', '.');
    }

    // Ide 5: baca angka dengan validasi, ulangi terus sampai input benar
    static int bacaAngka(Scanner input, String pesan) {
        System.out.print(pesan);
        while (!input.hasNextInt()) {
            System.out.println("Input harus berupa angka!");
            input.next();               // buang input yang salah
            System.out.print(pesan);
        }
        return input.nextInt();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Ide 4: data disimpan di array, bukan di switch
        String[] nama = {"A1", "PW", "Walet", "Ayam"};
        long[] harga = {790000, 750000, 750000, 760000};

        int nomorBeras;

        do {
            System.out.println("\n=====Daftar beras=====");
            for (int i = 0; i < nama.length; i++) {
                System.out.println((i + 1) + ". " + nama[i] + " - " + formatRupiah(harga[i]));
            }
            System.out.println("0. Keluar");
            System.out.println("======================");

            nomorBeras = bacaAngka(input, "Masukkan nomor beras: ");

            if (nomorBeras == 0) {
                System.out.println("Terima kasih telah memesan");

            } else if (nomorBeras >= 1 && nomorBeras <= nama.length) {
                String namaBeras = nama[nomorBeras - 1];
                long hargaBeras = harga[nomorBeras - 1];

                int jumlah = bacaAngka(input, "Masukkan jumlah karung: ");

                if (jumlah <= 0) {
                    System.out.println("Jumlah harus lebih dari 0.");
                } else {
                    long total = hargaBeras * jumlah;
                    long diskon = 0;
                    if (jumlah >= 20) {
                        diskon = total * 5 / 100;
                    }

                    long hargasebelumdiskon = total;
                    System.out.println("Harga sebelum diskon: " + formatRupiah(hargasebelumdiskon));
                    long hargasetelahdiskon = total - diskon;
                    System.out.println("Harga setelah diskon: " + formatRupiah(hargasetelahdiskon));
                    System.out.println("Maukkan uang yang dibayarkan: ");
                    long uangdibayar = input.nextLong();

                    while(uangdibayar < hargasetelahdiskon){
                        System.out.println("Uang yang dimasukkan kurang, mohon coba lagi!!");
                        System.out.println("Masukkan uang yang dibayarkan: ");
                        uangdibayar = input.nextLong();
                        input.nextLine();
                    }
                    long Uangkembalian = uangdibayar - hargasetelahdiskon;
                    
                    
                    System.out.println("\n----- Rincian -----");
                    System.out.println("Beras        : " + namaBeras);
                    System.out.println("Harga/karung : " + formatRupiah(hargaBeras));
                    System.out.println("Jumlah       : " + jumlah + " karung");
                    System.out.println("Harga sebelum diskon     : " + formatRupiah(total));
                    if(jumlah >= 20){
                    System.out.println("Harga setelah diskon: " + formatRupiah(hargasetelahdiskon));
                    }else{
                        System.out.println("Maaf anda tidak dapat diskon.");
                    }
                    System.out.println("Diskon       : " + formatRupiah(diskon));
                    System.out.println("Uang yang dibayar  : " + formatRupiah(uangdibayar));
                    System.out.println("Uang kembalian: " + formatRupiah(Uangkembalian));
                }

            } else {
                System.out.println("Nomor tidak valid, silakan coba lagi.");
            }

        } while (nomorBeras != 0);

        input.close();
    }
}