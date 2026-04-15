package domain;

import exception.StockProductException;

import java.util.List;
import java.util.Objects;

public class Order {
    private Long id;
    private Customer customer;
    private List<Item> Itens;

    public Order(Long id, Customer customer, List<Item> product) {
        this.id = id;
        this.customer = customer;
        this.Itens = product;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Item> getProduct() {
        return Itens;
    }

    public double calculateTovalValue(){
        double total = 0.0;
        for(Item i : getProduct()){
            if(i.getQuantity() > i.getProduct().getStock()){
                throw new StockProductException("The stock of product is above for quantity request!");
            }
            i.getProduct().setStock(i.getProduct().getStock() - i.getQuantity());
            total += i.getProduct().getPrice() * i.getQuantity();
        }
        return total;
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
}
