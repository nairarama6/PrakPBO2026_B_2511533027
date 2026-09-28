package MiniBank.pekan3;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Rekening> daftarRekening = new ArrayList<>();
        Rekening akunAktif = null;
        boolean isRunning = true;
        
        System.out.println("=== SISTEM PERBANKAN MINI ===");
        
        while (isRunning) {

            System.out.println("\nMenu Utama");
            System.out.println("1. Buka Rekening Baru");
            System.out.println("2. Setor Tunai");
            System.out.println("3. Tarik Tunai");
            System.out.println("4. Cek Informasi Rekening");
            System.out.println("5. Ganti Akun");
            System.out.println("6. Total Transaksi");
            System.out.println("7. Keluar");
            System.out.print("Pilih Menu: ");

            int pilihan = input.nextInt();
            input.nextLine(); // Clear buffer input pilihan

            switch (pilihan) {

                case 1:
                    System.out.print("Masukkan No Rekening: ");
                    String no = input.nextLine();

                    System.out.print("Masukkan Nama Pemilik: ");
                    String nama = input.nextLine();

                    System.out.print("Masukkan Saldo Awal: ");
                    double saldo = input.nextDouble();
                    input.nextLine(); // Clear buffer setelah input double

                    // Perintah memasukkan PIN saat Buka Rekening
                    System.out.print("Buat PIN Keamanan (6 digit): ");
                    String pinBaru = input.nextLine();

                    // Instansiasi objek baru dengan menyertakan PIN
                    Rekening rekeningBaru = new Rekening(no, nama, saldo, pinBaru);
                    daftarRekening.add(rekeningBaru);
                    akunAktif = rekeningBaru;
                    break;

                case 2:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum memiliki rekening!");
                    } else {
                        System.out.print("Masukkan nominal setor: ");
                        double setor = input.nextDouble();
                        input.nextLine(); // Clear buffer
                        akunAktif.setorTunai(setor);
                    }
                    break;

                case 3:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum memiliki rekening!");
                    } else {
                        // Meminta input PIN sebelum transaksi
                        System.out.print("Masukkan PIN Anda untuk verifikasi: ");
                        String pinInput = input.nextLine();

                        // Validasi otentikasi PIN
                        if (akunAktif.otentikasi(pinInput)) {
                            System.out.print("Masukkan nominal tarik tunai: ");
                            double tarik = input.nextDouble();
                            input.nextLine(); // Clear buffer
                            akunAktif.tarikTunai(tarik);
                        } else {
                            System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
                        }
                    }
                    break;

                case 4:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum membuka rekening!");
                    } else {
                        akunAktif.cekInformasi();
                    }
                    break;

                case 5:
                    if (daftarRekening.isEmpty()) {
                        System.out.println("Belum ada rekening yang tersedia.");
                    } else {
                        System.out.print("Masukkan nomor rekening: ");
                        String nomorCari = input.nextLine();
                        boolean ditemukan = false;
                        for (Rekening rekening : daftarRekening) {
                            if (rekening.getNomorRekening().equals(nomorCari)) {
                                akunAktif = rekening;
                                ditemukan = true;
                                System.out.println("Akun berhasil diganti ke " + rekening.getNamaPemilik());
                                break;
                            }
                        }
                        if (!ditemukan) {
                            System.out.println("Rekening tidak ditemukan.");
                        }
                    }
                    break;

                case 6:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum memiliki rekening!");
                    } else {
                        // Meminta input PIN sebelum mencetak total transaksi
                        System.out.print("Masukkan PIN Anda untuk verifikasi: ");
                        String pinInput = input.nextLine();

                        // Validasi otentikasi PIN
                        if (akunAktif.otentikasi(pinInput)) {
                            akunAktif.cetakTotalTransaksi();
                        } else {
                            System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
                        }
                    }
                    break;
                    
                case 7:
                    isRunning = false;
                    System.out.println("Sistem ditutup. Terima kasih!");
                    break;

                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
        input.close();
    }
}