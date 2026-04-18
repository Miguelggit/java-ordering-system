import domain.*;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Customer c1 = new Customer(1L, "Miguel");
        Customer c2 = new Customer(2L, "Maria");
        Customer c3 = new Customer(3L, "Josué");

        Product p1 = new Product(1L, "Notebook", 3500D, 30);
        Product p2 = new Product(2L, "Mosue", 150D, 200);
        Product p3 = new Product(3L, "Teclado", 300D, 15);
        Product p4 = new Product(4L, "Teclado", 250D, 12);
        Product p5 = new Product(5L, "Headset", 199.99D, 15);

        Item i1 = new Item(1L, p1, 1);
        Item i11 = new Item(30L, p1, 2);
        Item i2 = new Item(2L, p2, 1);
        Item i12 = new Item(23L, p2, 2);
        Item i3 = new Item(3L, p3, 12);
        Item i44 = new Item(319L, p3, 2);
        Item i4 = new Item(4L, p4, 1);
        Item i5 = new Item(5L, p5, 1);

        List<Item> itens = new ArrayList<>(List.of(i3, i2, i5, i4));
        List<Item> itens2 = new ArrayList<>(List.of(i1, i5, i4));
        List<Item> itens3 = new ArrayList<>(List.of(i44, i12, i11));

        Order o1 = new Order(1L, c2, itens);
        Order o2 = new Order(2L, c1, itens2);
        Order o3 = new Order(3L, c3, itens3);

        Cart cart = new Cart();
        cart.addToCart(o1);
        cart.addToCart(o2);
        cart.addToCart(o3);
        cart.calculateTotalPerCustomer();
        System.out.println(cart.customerWhoSpentTheMost());
        cart.productBestSelling().ifPresent( s -> System.out.println("Produto mais vendido é " + s));
    }
}