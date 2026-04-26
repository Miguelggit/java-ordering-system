package services;

import domain.Item;
import domain.Order;
import domain.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServices {
    public double calculateTotalValue(Order order){
        return order.getItems().stream()
                .mapToDouble( i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();
    }

    public List<Item> filterByMaxPrice(Order order,  double value){
        return order.getItems().stream()
                .filter(i -> i.getProduct().getPrice() <= value)
                .toList();
    }

    public List<Item> sortedOrdersByValue(Order order){
        return order.getItems().stream()
                .sorted(Comparator.comparing(i -> i.getProduct().getPrice(), Comparator.reverseOrder()))
                .toList();
    }
}
