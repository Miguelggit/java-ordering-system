package domain;

import exception.EmptyOrderException;
import exception.NullCustomerException;

import java.util.*;

public class Order {
    private Long id;
    private Customer customer;
    private List<Item> Items = new ArrayList<>();

    public Order(Long id, Customer customer, List<Item> product) {
        if(customer == null){
            throw new NullCustomerException("Order must have a customer associated");
        }
        if(product == null || product.isEmpty()){
            throw new EmptyOrderException("Order must contain at least one item.");
        }
        this.id = id;
        this.customer = customer;
        this.Items = product;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Item> getItems() {
        return Items;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", Itens=" + Items +
                '}';
    }
}
