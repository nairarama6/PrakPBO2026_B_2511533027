package MiniBank.pekan3;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.util.Locale;

public class Rekening {
    // Atribut privat (Enkapsulasi)
    private String nomorRekening;
    private String namaPemilik;
    private double saldo;
    private String pin; // Data sensitif

    NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    private ArrayList<Transaksi> riwayatTransaksi;

    // Constructor menerima input PIN dari user
    public Rekening(String nomor, String nama, double saldoAwal, String pinInput) {
        this.nomorRekening = nomor;
        this.namaPemilik = nama;
        this.saldo = saldoAwal;
        
        // Validasi PIN 6 digit
        if (pinInput != null && pinInput.length() == 6) {
            this.pin = pinInput;
        } else {
            System.out.println("Peringatan: PIN harus 6 digit angka! Menggunakan PIN default 123456.");
            this.pin = "123456";
        }
        
        this.riwayatTransaksi = new ArrayList<>();
        
        // Pesan konfirmasi pembukaan rekening dan pendaftaran PIN
        System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + saldo + " dan PIN telah terdaftar.");
    }

    // Getter
    public String getNomorRekening() { return nomorRekening; }
    public String getNamaPemilik() { return namaPemilik; }

    // Method Otentikasi PIN
    public boolean otentikasi(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void setorTunai(double nominal) {
        if (nominal > 0) {
            saldo += nominal;
            String idTrx = "TRX-S" + System.currentTimeMillis();
            Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
            riwayatTransaksi.add(trxBaru);
            
            System.out.println("Setor tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
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

    public void tarikTunai(double nominal) {
        if (nominal < 10000) {
            System.out.println("Transaksi Gagal: Minimal nominal penarikan Rp10000");
        } else if (nominal > saldo) {
            System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp" + saldo);
        } else {
            saldo -= nominal;
            String idTrx = "TRX-T-" + System.currentTimeMillis(); 
            Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal); 
            riwayatTransaksi.add(trxBaru);
            
            System.out.println("Penarikan berhasil.");
            System.out.println("Jumlah penarikan: Rp" + nominal);
            System.out.println("Saldo sekarang: Rp" + saldo);
        }
    }

    public void cetakTotalTransaksi() {
        double totalSetor = 0;
        double totalTarik = 0;

        for (Transaksi trx : riwayatTransaksi) {
            if (trx.getJenis().equals("Kredit")) {
                totalSetor += trx.getNominal();
            } else if (trx.getJenis().equals("Debit")) {
                totalTarik += trx.getNominal();
            }
        }

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