
public class Belajar {
    public static void main(String[] args) {
        String nama = "Bruno";
        String negara = "Brazil";
        String hobi =  "Sepak Bola";
        String keterangan = "Pemain sepak bola asal Brazil yang terkenal dengan kemampuan dribbling dan mencetak gol yang luar biasa.";
        String posisi = "Left Winger";

        int umur = 28;
        double tinggi = 180.5;
        double berat = 75.6;
        int goal = 900;
        int assist = 286;
        int match = 900;
        int yellowCard = 68;
        int redCard = 3;
         int totalGoalAassist = goal + assist;

        if (match >= 800 && totalGoalAassist >= 1000) {
            System.out.println("Legenda Grade A");
        } else if (match >= 500 && totalGoalAassist >= 500) {
            System.out.println("Legenda Grade B");
        }
        else if (match >= 300 && totalGoalAassist >= 300) {
            System.out.println("Legenda Grade C");
        } else {
            System.out.println("Bukan Legenda");
        }

        System.out.println("Nama Pemain: " + nama);
        System.out.println("Negara: " + negara);
        System.out.println("Hobi: " + hobi);
        System.out.println("Keterangan: " + keterangan);
        System.out.println("Posisi: " + posisi);
        System.out.println("Umur: " + umur + " tahun");
        System.out.println("Tinggi: " + tinggi + " cm");
        System.out.println("Berat: " + berat + " kg");
        System.out.println("Total Goal: " + goal);
        System.out.println("Total Assist: " + assist);
        System.out.println("Total Match: " + match);
        System.out.println("Total Yellow Card: " + yellowCard);
        System.out.println("Total Red Card: " + redCard);
        System.out.println("Total G/A: " + totalGoalAassist);
    }
}