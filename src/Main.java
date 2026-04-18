import domain.*;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Customer c1 = new Customer(1L, "Miguel");
        Customer c2 = new Customer(2L, "Maria");

        Product p1 = new Product(1L, "Notebook", 3500D, 30);
        Product p2 = new Product(2L, "Mosue", 150D, 200);
        Product p3 = new Product(3L, "Teclado", 300D, 15);
        Product p4 = new Product(4L, "Teclado", 250D, 12);
        Product p5 = new Product(5L, "Headset", 199.99D, 15);

        Item i1 = new Item(1L, p1, 1);
        Item i2 = new Item(2L, p2, 1);
        Item i3 = new Item(3L, p3, 1);
        Item i4 = new Item(4L, p4, 1);
        Item i5 = new Item(5L, p5, 1);

        List<Item> itens = new ArrayList<>(List.of(i1, i3, i2, i5, i4));
        List<Item> itens2 = new ArrayList<>(List.of(i5, i4));

        Order o1 = new Order(1L, c1, itens);
        Order o2 = new Order(2L, c2, itens2);

        Cart cart = new Cart();
        Cart cart2 = new Cart();

        System.out.println(o1.calculateTovalValue());
        System.out.println(p1.getStock());
        System.out.println(p2.getStock());
        System.out.println(p3.getStock());
        System.out.println(o1.filterByMinPrice(400));
        System.out.println(o1.sortedOrdersByValue());
        System.out.println(cart2.addToCart(o2));
        System.out.println("---------------------");
        System.out.println(cart.addToCart(o1));
        System.out.println(cart.calculateTotalPerCustomer());
        System.out.println("-----------------------");
        System.out.println(cart2.calculateTotalPerCustomer());
    }
}