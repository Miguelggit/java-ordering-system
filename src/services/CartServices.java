package services;

import domain.*;
import exception.DuplicateOrderException;
import exception.EmptyCartException;
import exception.StockProductException;

import java.util.*;

public class CartServices {
    public void addToCart(Cart cart, Order order){
        List<Order> orderList = cart.getCart().get(order.getCustomer());
        if(orderList == null){
            orderList = new ArrayList<>();
            cart.getCart().put(order.getCustomer(), orderList);
        }
        if(orderList.contains(order)){
            throw new DuplicateOrderException("Order with id " + order.getId() + " already exists in cart.");
        }
        for(Item i: order.getItems()){
            if(i.getQuantity() > i.getProduct().getStock()){
                throw new StockProductException("The stock of product is above for quantity request!");
            }
            i.getProduct().setStock(i.getProduct().getStock() - i.getQuantity());
        }
        orderList.add(order);
    }
    public Map<Customer, Double> calculateTotalPerCustomer(Cart cart){
        Map<Customer, Double> listCalc = new HashMap<>();
        if(cart.getCart().isEmpty()){
            throw new EmptyCartException("Cart is empty. No data available for this operation.");
        }
        cart.getCart().forEach((customer, orders) -> {
            double[] total = {0.0};
            orders.forEach(order -> {
                for(Item i: order.getItems()){
                    total[0] += i.getProduct().getPrice() * i.getQuantity();
                }
            });
            listCalc.put(customer, total[0]);
        });
        return listCalc;
    }
    public Optional<Customer> customerWhoSpentTheMost(Cart cart){
        Customer topCustomer = null;
        Double maxValue = 0.0;
        for(Map.Entry<Customer, Double> entry : calculateTotalPerCustomer(cart).entrySet()){
            if(entry.getValue() > maxValue){
                maxValue = entry.getValue();
                topCustomer = entry.getKey();
            }
        }
        return Optional.ofNullable(topCustomer);
    }
    public Optional<Product> productBestSelling(Cart cart) {
        Map<Product, Integer> productQuantities = new HashMap<>();
        if(cart.getCart().isEmpty()){
            throw new EmptyCartException("Cart is empty. No data available for this operation.");
        }
        for (Map.Entry<Customer, List<Order>> entry : cart.getCart().entrySet()) {
            entry.getValue().forEach(order -> {
                for (Item i : order.getItems()) {
                    Product product= i.getProduct();
                    int currentQuantity= productQuantities.getOrDefault(product, 0);
                    productQuantities.put(product, currentQuantity + i.getQuantity());
                }
            });
        }
        int maxQuantity = 0;
        Product bestSelling = null;
        for (Map.Entry<Product, Integer> entry : productQuantities.entrySet()) {
            if (entry.getValue() > maxQuantity) {
                maxQuantity = entry.getValue();
                bestSelling = entry.getKey();
            }
        }
        return Optional.ofNullable(bestSelling);
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
        if(cart.getCart().isEmpty()){
            throw new EmptyCartException("Cart is empty. No data available for this operation.");
        }
        for(Map.Entry<Customer, List<Order>> input: cart.getCart().entrySet()){ //* Entro no meu cart
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
