public class TugasUangTabungan {
    public static void main(String[] args) {
        String nama = "Caca";
        int uangjajan = 100000;
        int tabunganperhari = 20000;
        int jumlahhari = 30;
        int jumlahteman = 3;

        int jumlahtabungan = tabunganperhari*jumlahhari;
        int bagianperteman = jumlahtabungan / jumlahteman;
        int sisatabungan = jumlahtabungan % jumlahteman;

        System.out.println("Nama :" + nama);
        System.out.println("Uang jajan per hari :Rp" + uangjajan);
        System.out.println("Ditabung per hari :Rp" + tabunganperhari);
        System.out.println("Total menabung :Rp" + jumlahtabungan);
        System.out.println("Dibagi ke :" + jumlahteman + "teman");
        System.out.println("Bagian per teman :Rp" + bagianperteman);
        System.out.println("Sisa tabungan Caca :Rp" + sisatabungan);
    }
}