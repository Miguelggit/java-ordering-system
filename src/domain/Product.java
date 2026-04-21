package domain;

import exception.InvalidPriceException;

import java.util.Objects;

public class Product {
    private Long id;
    private String name;
    private Double price;
    private int stock;
    public Product(Long id, String name, Double price, int quantity) {
        if(price <= 0){
            throw new InvalidPriceException("Product price must be greater than zero");
        }
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = quantity;
    }

    public int getStock() {
        return stock;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Price: R$ %.2f | Stock: %d", getId(), getName(), getPrice(), getStock());
    }
}
