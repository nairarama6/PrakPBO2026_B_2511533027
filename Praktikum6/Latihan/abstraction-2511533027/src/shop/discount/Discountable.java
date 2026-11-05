package shop.discount;

public interface Discountable {
    /** Abstract method: setiap kelas konkret menentukan persentase diskonnya */
    int discountPercent();

    /** Default method: menghitung harga akhir setelah dipotong diskon */
    default long applyDiscount(long price) {
        return price - percentOf(price, safePercent());
    }

    /** Static method: utilitas pembantu untuk menghitung sekian persen dari suatu jumlah */
    static long percentOf(long amount, int percent) {
        return amount * percent / 100;
    }

    /** Private method: memvalidasi agar persentase diskon selalu berada di rentang 0 - 100 */
    private int safePercent() {
        int percent = discountPercent();
        if (percent < 0) {
            return 0;
        }
        return Math.min(percent, 100);
    }
}