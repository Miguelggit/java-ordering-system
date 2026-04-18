package domain;

import exception.StockProductException;

import java.util.*;

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
            total += i.getProduct().getPrice() * i.getQuantity();
        }
        return total;
    }

    public List<Item> filterByMinPrice(int value){
        List<Item> itensFilter = new ArrayList<>();
        for(Item i : getProduct()){
            if(i.getProduct().getPrice() <= value){
                itensFilter.add(i);
            }
        }
        return itensFilter;
    }

    public List<Item> sortedOrdersByValue(){
        List<Item> sortedList = new ArrayList<>(getProduct()); //* Adiciona os produtos para a nova lista
        sortedList.sort(Comparator.comparing(p -> p.getProduct().getPrice())); //* Ordena os produtos
        Collections.reverse(sortedList); //* Inverte a ordenação
        return sortedList;
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
                ", Itens=" + Itens +
                '}';
    }
}
