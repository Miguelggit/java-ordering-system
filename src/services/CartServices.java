package services;

import domain.*;
import exception.DuplicateOrderException;
import exception.EmptyCartException;
import exception.StockProductException;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CartServices {
    public void addToCart(Cart cart, Order order){
        List<Order> orderList = cart.getOrders().computeIfAbsent(order.getCustomer(), k -> new ArrayList<>());
        if(orderList.contains(order)){
            throw new DuplicateOrderException("Order with id " + order.getId() + " already exists in cart.");
        }
        for(Item i: order.getItems()){
            i.getProduct().setStock(i.getProduct().getStock() - i.getQuantity());
        }
        orderList.add(order);
    }
    public Map<Customer, Double> calculateTotalPerCustomer(Cart cart){
        if(cart.getOrders().isEmpty()){
            throw new EmptyCartException("Cart is empty. No data available for this operation.");
        }
        return cart.getOrders().entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> entry.getKey(),
                                entry -> entry.getValue().stream()
                                        .flatMap(o -> o.getItems().stream())
                                                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                                                .sum()
                                ));
    }
    public Optional<Customer> customerWhoSpentTheMost(Cart cart){
        return calculateTotalPerCustomer(cart).entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey);
    }
    public Optional<Product> productBestSelling(Cart cart) {
        if(cart.getOrders().isEmpty()){
            throw new EmptyCartException("Cart is empty. No data available for this operation.");
        }
        return cart.getOrders().values().stream()
                .flatMap(List::stream)
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy( Item::getProduct, Collectors.summingInt(Item::getQuantity)
                ))
                .entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey);
    }
    public List<Customer> top3Customers(Cart cart){
        return calculateTotalPerCustomer(cart).entrySet().stream()
                .sorted(Map.Entry.<Customer, Double>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
    }
    public Double avgValueByOrder(Cart cart){
        double totalValue = cart.getOrders().values().stream()
                .flatMap(List::stream)
                .flatMap(o -> o.getItems().stream())
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();

        long longStream = cart.getOrders().values().stream()
                .flatMap(List::stream)
                .map(o -> o.getItems().stream())
                .count();

        return totalValue / longStream;
    }
}
