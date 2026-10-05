package MiniBank.pekan4;

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
            System.out.println("7. Simulasi Akhir Bulan (Khusus Tabungan)");
            System.out.println("8. Total Transaksi");
            System.out.println("9. Keluar");
            System.out.print("Pilih Menu: ");

            int pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {

                case 1:
                    System.out.print("Masukkan No Rekening: ");
                    String no = input.nextLine();

                    System.out.print("Masukkan Nama Pemilik: ");
                    String nama = input.nextLine();

                    System.out.print("Masukkan Saldo Awal: ");
                    double saldo = input.nextDouble();
                    input.nextLine();

                    System.out.print("Buat PIN Keamanan (6 digit): ");
                    String pinBaru = input.nextLine();

                    // Pilihan produk
                    System.out.println("\nPilih Produk:");
                    System.out.println("1. Tabungan Umum");
                    System.out.println("2. Giro Bisnis");
                    System.out.println("3. Rekening VIP");
                    System.out.print("Pilih Produk: ");

                    int pilihanProduk = input.nextInt();
                    input.nextLine();

                    Rekening rekeningBaru = null;

                    switch (pilihanProduk) {

                        case 1:
                            System.out.print("Masukkan Suku Bunga (%): ");
                            double sukuBunga = input.nextDouble();
                            input.nextLine();

                            rekeningBaru = new RekeningTabungan(
                                no, nama, saldo, pinBaru, sukuBunga
                            );

                            break;

                        case 2:
                            System.out.print("Masukkan Batas Overdraft: Rp");
                            double batasOverdraft = input.nextDouble();
                            input.nextLine();

                            rekeningBaru = new RekeningGiro(
                                no, nama, saldo, pinBaru, batasOverdraft
                            );

                            break;
                            
                        case 3:
                        	rekeningBaru = new RekeningVIP(no, nama, saldo, pinBaru);
                        	    break;

                        default:
                            System.out.println("Pilihan produk tidak valid.");
                            break;
                    }

                    // Menyimpan rekening ke daftar
                    if (rekeningBaru != null) {
                        daftarRekening.add(rekeningBaru);
                        akunAktif = rekeningBaru;

                        System.out.println("Rekening berhasil dibuat!");
                    }

                    break;

                case 2:
                    if (akunAktif == null) {
                        System.out.println(
                            "Error: Anda belum memiliki rekening!"
                        );
                    } else {
                        System.out.print("Masukkan nominal setor: ");
                        double setor = input.nextDouble();
                        input.nextLine();

                        akunAktif.setorTunai(setor);
                    }
                    break;

                case 3:
                    if (akunAktif == null) {
                        System.out.println(
                            "Error: Anda belum memiliki rekening!"
                        );
                    } else {
                        System.out.print(
                            "Masukkan PIN Anda untuk verifikasi: "
                        );
                        String pinInput = input.nextLine();

                        if (akunAktif.otentikasi(pinInput)) {
                            System.out.print(
                                "Masukkan nominal tarik tunai: "
                            );

                            double tarik = input.nextDouble();
                            input.nextLine();

                            akunAktif.tarikTunai(tarik);

                        } else {
                            System.out.println(
                                "Akses Ditolak: PIN yang Anda masukkan salah!"
                            );
                        }
                    }
                    break;

                case 4:
                    if (akunAktif == null) {
                        System.out.println(
                            "Error: Anda belum membuka rekening!"
                        );
                    } else {
                        akunAktif.cekInformasi();
                    }
                    break;

                case 5:
                    if (daftarRekening.isEmpty()) {
                        System.out.println(
                            "Belum ada rekening yang tersedia."
                        );
                    } else {
                        System.out.print(
                            "Masukkan nomor rekening: "
                        );

                        String nomorCari = input.nextLine();
                        boolean ditemukan = false;

                        for (Rekening rekening : daftarRekening) {

                            if (rekening.getNomorRekening()
                                    .equals(nomorCari)) {

                                akunAktif = rekening;
                                ditemukan = true;

                                System.out.println(
                                    "Akun berhasil diganti ke "
                                    + rekening.getNamaPemilik()
                                );

                                break;
                            }
                        }

                        if (!ditemukan) {
                            System.out.println(
                                "Rekening tidak ditemukan."
                            );
                        }
                    }
                    break;

                case 6:
                    if (akunAktif == null) {
                        System.out.println(
                            "Error: Anda belum memiliki rekening!"
                        );
                    } else {
                        System.out.print(
                            "Masukkan PIN Anda untuk verifikasi: "
                        );

                        String pinInput = input.nextLine();

                        if (akunAktif.otentikasi(pinInput)) {
                            akunAktif.cetakTotalTransaksi();
                        } else {
                            System.out.println(
                                "Akses Ditolak: PIN yang Anda masukkan salah!"
                            );
                        }
                    }
                    break;

                case 7:
                    if (akunAktif instanceof RekeningTabungan) {

                        RekeningTabungan tabungan =
                            (RekeningTabungan) akunAktif;

                        tabungan.tambahBungaAkhirBulan();

                    } else {
                        System.out.println(
                            "Gagal: Fitur bunga akhir bulan hanya berlaku "
                            + "untuk Rekening Tabungan."
                        );
                    }
                    break;

                case 8:
                    if (akunAktif == null) {
                        System.out.println(
                            "Error: Anda belum memiliki rekening!"
                        );
                    } else {
                        System.out.print(
                            "Masukkan PIN Anda untuk verifikasi: "
                        );

                        String pinInput = input.nextLine();

                        if (akunAktif.otentikasi(pinInput)) {
                            akunAktif.cetakTotalTransaksi();
                        } else {
                            System.out.println(
                                "Akses Ditolak: PIN yang Anda masukkan salah!"
                            );
                        }
                    }
                    break;

                case 9:
                    System.out.println(
                        "Terima kasih telah menggunakan MiniBank!"
                    );

                    isRunning = false;
                    break;

                default:
                    System.out.println(
                        "Pilihan menu tidak valid."
                    );
                    break;
            }
        }

        input.close();
    }
}