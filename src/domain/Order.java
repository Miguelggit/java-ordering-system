package domain;

import java.util.List;

public class Order {
    private Long id;
    private Customer customer;
    private List<Product> product;

    public Order(Long id, Customer customer, List<Product> product) {
        this.id = id;
        this.customer = customer;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProduct() {
        return product;
    }
}
