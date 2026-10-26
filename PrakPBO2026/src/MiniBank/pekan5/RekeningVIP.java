package MiniBank.pekan5;

public class RekeningVIP extends Rekening {
	 private static final double BONUS = 100000;

	    public RekeningVIP(String nomor, String nama,
	                       double saldoAwal, String pin) {

	        super(nomor, nama, saldoAwal, pin, pin);

	        // Bonus pembukaan rekening VIP
	        saldo += BONUS;

	        System.out.println("Selamat! Anda mendapatkan bonus VIP sebesar Rp100000.");
	    }

}
