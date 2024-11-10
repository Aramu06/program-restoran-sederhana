import java.util.Scanner;
import java.text.DecimalFormat;

class Menu{
    String name;
    double price;
    String category;

    public Menu(String name, double price, String category){
        this.name = name;
        this.price = price;
        this.category = category;
    }
}

public class Main{
    private static final Menu[] menuList = {
        new Menu("Nasi Goreng Biasa", 15000, "Makanan"),
        new Menu("Nasi Goreng Seafood", 30000, "Makanan"),
        new Menu("Nasi Goreng Ayam", 20000, "Makanan"),
        new Menu("Nasi Goreng Sapi", 40000, "Makanan"),
        new Menu("Es Teh", 5000, "Minuman"),
        new Menu("Air mineral", 3000, "Minuman"),
        new Menu("Jus Mangga", 10000, "Minuman"),
        new Menu("Jus jeruk", 8000, "Minuman"),
    };

    private static final DecimalFormat format = new DecimalFormat("#,##0.00");

    public static void tampilkanMenu(){
        System.out.println("===> Daftar Menu <===");
        System.out.println("Makanan: ");
        for(Menu menu : menuList){
            if(menu.category.equals("Makanan")){
                System.out.println(menu.name + " -- Rp " + format.format(menu.price));
            }
        }
        System.out.println("\nMinuman: ");
        for(Menu menu : menuList){
            if(menu.category.equals("Minuman")){
                System.out.println(menu.name + " -- Rp " + format.format(menu.price));
            }
        }
    }

    public static double menghitungTotalBiaya(String[] orderedItems, int[] quantities){
        double totalCost = 0;
        for(int i = 0; i < orderedItems.length; i++){
            for(Menu menu : menuList){
                if(menu.name.equalsIgnoreCase(orderedItems[i])){
                    totalCost += menu.price * quantities[i];
                }
            }
        }
        return totalCost;
    }

    public static double applyDiscount(double totalCost){
        if(totalCost > 100000){
            return totalCost * 0.9; //diskon 10 persen
        }
        return totalCost;
    }

    public static void strukPesanan(String[] orderedItems, int[] quantities, double totalCost){
        double tax = 0.1 * applyDiscount(totalCost);
        double serviceCharge = 20000;
        double totalBill = applyDiscount(totalCost) + tax + serviceCharge;

        System.out.println("\n===> Struk Pesanan <===");
        for(int i = 0; i < orderedItems.length; i++){
            for(Menu menu : menuList){
                if(menu.name.equalsIgnoreCase(orderedItems[i])){
                    double itemCost = menu.price * quantities[i];
                    System.out.println(quantities[i] + "x " + menu.name + " -- Rp " + format.format(menu.price) + " = Rp " + format.format(itemCost));
                }
            }
        }

        System.out.println("\nTotal Biaya Pemesanan: Rp " + format.format(totalCost));

        //penawaran beli 1 gratis 1 untuk minuman

        if(totalCost >= 50000){
            System.out.println("Anda mendapatkan penawaran beli satu gratis satu untuk minuman!");
        }

        double discountedTotal = applyDiscount(totalCost);

        System.out.println("Diskon 10%: Rp " + format.format(totalCost - discountedTotal));
        System.out.println("total Biaya Setelah Diskon: Rp " + format.format(discountedTotal));
        System.out.println("PPN 10%: Rp " + format.format(tax));
        System.out.println("Biaya Pelayanan : Rp " + format.format(serviceCharge));
        System.out.println("Total Tagihan: Rp " + format.format(totalBill));
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)){
            tampilkanMenu();

            System.out.println("\n Masukkan pesanan anda! (maksimal 4 item)");

            String[] orderedItems = new String[4];
            int[] quantities = new int[4];

            int itemCount = 0;

            while(true){
                System.out.println("Pesanan " + (itemCount + 1) + ": ");
                String itemName = scanner.nextLine();

                if(itemName.isEmpty()){
                    break;
                }

                boolean itemFound = false;

                for(Menu menu : menuList){
                    if(menu.name.equalsIgnoreCase(itemName)){
                        itemFound = true;
                        orderedItems[itemCount] = menu.name;

                        System.out.print("jumlah: ");
                        quantities[itemCount] = scanner.nextInt();
                        scanner.nextLine(); // clear the newline character
                        itemCount++;
                        break;
                    }
                }

                if(!itemFound){
                    System.out.println("Menu tidak tersedia!");
                }

                if(itemCount >= 4){
                    System.out.println("Anda sudah mencapai maksimal pemesanan!");
                    break;
                }
            }

            double totalCost = menghitungTotalBiaya(orderedItems, quantities);

            strukPesanan(orderedItems, quantities, totalCost);

        }
    }
}
