package services;

import domain.Cart;
import domain.Customer;
import domain.Item;
import domain.Order;
import exception.DuplicateOrderException;
import exception.EmptyCartException;
import exception.StockProductException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
