package MiniBank.pekan2;

import java.util.ArrayList;
import java.text.NumberFormat;
import java.util.Locale;

public class Rekening {
    String nomorRekening;
    String namaPemilik;
    double saldo;
    
    NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    
    // Implementasi Asosiasi (1-to-many)
    ArrayList<Transaksi> riwayatTransaksi;

    public Rekening(String nomor, String nama, double saldoAwal) {
        nomorRekening = nomor;
        namaPemilik = nama;
        saldo = saldoAwal;
        
        // Wajib menginstalisasi ArrayList didalam constructor agar tidak NullPointerException
        this.riwayatTransaksi = new ArrayList<>();

        System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + saldo);
    }

    public void setorTunai(double nominal) {
        if (nominal > 0) {
            saldo += nominal;
            // Merekam riwayat (Pembuatan objek transaksi di dalam method)
            String idTrx = "TRX-S" + System.currentTimeMillis();
            Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
            riwayatTransaksi.add(trxBaru);
            
            System.out.println("Setor tunai Rp" + nominal+ " berhasil. Saldo saat ini: Rp" + saldo);
        } else {
            System.out.println("Gagal: Nominal setor harus lebih dari 0!");
        }
    }

    public void cekInformasi() {

        System.out.println("--- INFO REKENING ---");
        System.out.println("No. Rekening  : " + nomorRekening);
        System.out.println("Nama Pemilik  : " + namaPemilik);
        System.out.println("Saldo Akhir   : " + saldo);
    }

    // Method tarik tunai
    public void tarikTunai(double nominal) {
        if (nominal < 10000) {
            System.out.println(
                "Transaksi Gagal: Minimal nominal penarikan Rp10000");
        } else if (nominal > saldo) {
            System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp" + saldo);
        } else {
            saldo -= nominal;
            // Merekam riwayat penarikan
            String idTrx = "TRX-T-" + System.currentTimeMillis(); 
            Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal); 
            riwayatTransaksi.add(trxBaru);
            
            System.out.println("Penarikan berhasil.");
            System.out.println("Jumlah penarikan: Rp" + nominal);
            System.out.println("Saldo sekarang: Rp" + saldo);
        }
    }
    
    public void cekMutasi() {
    	if (riwayatTransaksi.isEmpty()) { 
    		System.out.println("Belum ada transaksi pada rekening ini"); 
    		} else { 
    			int mulai = Math.max(0, riwayatTransaksi.size() - 3); 
    			System.out.println("--- TRANSAKSI TERBARU ---"); 
    			for (int i = mulai; i < riwayatTransaksi.size(); i++) { 
    				riwayatTransaksi.get(i).cetakDetail(); 
    			} 
    		}
    }
    
    public void cetakTotalTransaksi() {

        double totalSetor = 0;
        double totalTarik = 0;

        for (Transaksi trx : riwayatTransaksi) {

            if (trx.jenis.equals("Kredit")) {
                totalSetor += trx.nominal;
            } else if (trx.jenis.equals("Debit")) {
                totalTarik += trx.nominal;
            }
        }

        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        System.out.println("--- TOTAL TRANSAKSI ---");
        System.out.println("Total Setor : " + rupiah.format(totalSetor));
        System.out.println("Total Tarik : " + rupiah.format(totalTarik));

        System.out.println("\n--- 3 TRANSAKSI TERBARU ---");

        int mulai = Math.max(0, riwayatTransaksi.size() - 3);

        for (int i = mulai; i < riwayatTransaksi.size(); i++) {
            riwayatTransaksi.get(i).cetakDetail();
        }
        
    }

}