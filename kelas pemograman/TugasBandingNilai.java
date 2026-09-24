public class TugasBandingNilai {
    public static void main(String[] args) {
        int nilaiL = 85;
        int nilaiLight = 75;

        boolean lLebihBesar = nilaiL > nilaiLight;
        boolean lSamaDenganLight = nilaiL == nilaiLight;

        System.out.println("=== OPERATOR RELASI ===");
        System.out.println("Nilai L: " + nilaiL + ", Nilai Light: " + nilaiLight);
        System.out.println("Apakah L > Light? " + lLebihBesar);
        System.out.println("Apakah L == Light? " + lSamaDenganLight);
    
        boolean keduaDiatas80 = (nilaiL >= 80) && (nilaiLight >= 80);
        boolean salahSatuDiatas80 = (nilaiL >= 80) || (nilaiLight >= 80);
        boolean lTidakSeratus = !(nilaiL == 100);

        System.out.println("\n=== OPERATOR LOGIKA ===");
        System.out.println("Apakah L >= 80 DAN Light >= 80? " + keduaDiatas80);
        System.out.println("Apakah L >= 80 ATAU Light >= 80? " + salahSatuDiatas80);
        System.out.println("Apakah benar L tidak mendapat nilai 100? " + lTidakSeratus);
    }
}