package review.fixed;

import java.util.ArrayList;
import java.util.List;

public class BadShopRefactored {
    public interface PaymentMethod {
        boolean pay(long amount);
        String printInvoice();
    }

    public interface Shippable {
        long calculateCost(int weightGrams);
    }

    public static class GiftVoucher implements PaymentMethod {
        private double balance = 100000.0;

        @Override
        public boolean pay(long amount) {
            if (amount <= 0 || amount > balance) {
                return false;
            }
            balance -= amount;
            return true;
        }

        @Override
        public String printInvoice() {
            return "Voucher, remaining balance " + balance;
        }

        public double getBalance() {
            return balance;
        }
    }

    public static abstract class ShippingService implements Shippable {
        private final String name;

        public ShippingService(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        protected abstract long ratePerKg();

        @Override
        public final long calculateCost(int weightGrams) {
            if (weightGrams <= 0) return 0;
            long kilograms = (weightGrams + 999L) / 1000;
            return kilograms * ratePerKg();
        }
    }

    public static class ExpressShipping extends ShippingService {
        public ExpressShipping() {
            super("Ekspres");
        }

        @Override
        protected long ratePerKg() {
            return 18000;
        }
    }

    public static class Cart {
        private final List<Item> items = new ArrayList<>();
        public static class Item {
            private final String name;
            private final long price;
            private final int quantity;

            public Item(String name, long price, int quantity) {
                this.name = name;
                this.price = price;
                this.quantity = quantity;
            }

            public long subtotal() {
                return price * quantity;
            }

            public String getName() {
                return name;
            }
        }

        public void add(Item item) {
            items.add(item);
        }

        public long total() {
            long total = 0;
            for (Item item : items) {
                total += item.subtotal();
            }
            return total;
        }
    }

    public static final class Address {
        private final String street;
        private final String city;

        public Address(String street, String city) {
            this.street = street;
            this.city = city;
        }

        public String getStreet() { return street; }
        public String getCity() { return city; }

        @Override
        public String toString() {
            return street + ", " + city;
        }
    }

    public static class SimpleOrder {
        private final String id;
        private final Address address;

        public SimpleOrder(String id, Address address) {
            this.id = id;
            this.address = address;
        }

        public Address getAddress() {
            return address;
        }

        @Override
        public String toString() {
            return id + " -> " + address;
        }
    }

    public interface Discount {
        long apply(long price);
    }

    public static class FlashSaleDiscount implements Discount {
        private final int percent;

        public FlashSaleDiscount(int percent) {
            this.percent = percent;
        }

        @Override
        public long apply(long price) {
            return price - (price * percent / 100);
        }
    }

    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.add(new Cart.Item("Kopi Gayo", 85_000, 2));
        System.out.println("Total cart: " + cart.total());

        ShippingService express = new ExpressShipping();
        System.out.println("Ongkir " + express.getName() + " 2 kg: " + express.calculateCost(2000));

        Address home1 = new Address("Jl. Sudirman 10", "Padang");
        Address home2 = new Address("Jl. Sudirman 10", "Bukittinggi");
        SimpleOrder first = new SimpleOrder("ORD-1", home1);
        SimpleOrder second = new SimpleOrder("ORD-2", home2);
        System.out.println(first);
        System.out.println(second);

        Discount flashSale = new FlashSaleDiscount(30);
        System.out.println("Harga flash sale: " + flashSale.apply(100_000));

        GiftVoucher voucher = new GiftVoucher();
        boolean paid = voucher.pay(30_000);
        System.out.println(paid + " | sisa Rp" + (long)voucher.getBalance());
    }

}
