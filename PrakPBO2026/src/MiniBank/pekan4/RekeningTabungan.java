package MiniBank.pekan4;

public class RekeningTabungan extends Rekening {
	
	// Atribut spesifik yan hanya dimiliki oleh Tabungan
	private double sukuBunga;
	
	// Constructor Subsclass
	public RekeningTabungan(String nomor, String nama, double saldoAwal, String pinAwal, double sukuBunga) {
		// Super() memanggil constructor kelas induk (Rekening). WAJIB berada di baris pertama
		super(nomor, nama, saldoAwal, pinAwal);
		this.sukuBunga = sukuBunga;
	}
	
	public void tambahBungaAkhirBulan() {
		// Mengihitung bunga
		// Mengapa bisa mengakses saldo secara langsung dari class RekeningTabungan?
		double nominalBunga = saldo * (sukuBunga / 100);
		saldo += nominalBunga;
		
		// Mencari riwayat transaksi
		String idTrx = "TRX-B-" +  System.currentTimeMillis();
		riwayatTransaksi.add(new Transaksi(idTrx, "Bunga", nominalBunga));
		
		 System.out.println("Bunga " + sukuBunga + "% berhasil ditambahkan: Rp" + nominalBunga);
	}

}
