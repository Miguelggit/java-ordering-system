package services;

import domain.*;
import exception.DuplicateOrderException;
import exception.EmptyCartException;
import exception.StockProductException;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
        List<Map.Entry<Customer, Double>> list = new ArrayList<>(calculateTotalPerCustomer(cart).entrySet());
        List<Customer> topTiers = new ArrayList<>();
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        list.forEach( o1 -> {
            if(topTiers.size() < 3){
                topTiers.add(o1.getKey());
            }
        });
        return topTiers;
    }
    public Double avgValueByOrder(Cart cart){
        Double total = 0.0;
        int quantity=0;
        if(cart.getOrders().isEmpty()){
            throw new EmptyCartException("Cart is empty. No data available for this operation.");
        }
        for(Map.Entry<Customer, List<Order>> input: cart.getOrders().entrySet()){ //* Entro no meu cart
            for( Order o: input.getValue()){ //* Para cada ordem que eu tenho eu vou somar todos os itens de acordo com a quantidade que foi pedida e alocar no total
                for(Item i : o.getItems()){ //* Para cada item irei fazer a soma total de cada item que estiver na lista, resumindo se uma ordem tiver 2 itens eu irei fazer a soma total destes dois itens e logo em seguida irei adicionar uma quantity
                    total += i.getQuantity() * i.getProduct().getPrice(); //* soma dos itens
                }
                quantity++;
            }
        }
        return total / quantity;
    }
}
