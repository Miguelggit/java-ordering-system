package domain;
import exception.StockProductException;

import java.util.*;

public class Cart{
    private Map<Customer, List<Order>> cart = new HashMap<>();
    public List<Order> addToCart(Order order){
        List<Order> orderList = cart.get(order.getCustomer());
        if(orderList == null){
            orderList = new ArrayList<>();
            cart.put(order.getCustomer(), orderList);
        }
        for(Item i: order.getProduct()){
            if(i.getQuantity() > i.getProduct().getStock()){
                throw new StockProductException("The stock of product is above for quantity request!");
            }
            i.getProduct().setStock(i.getProduct().getStock() - i.getQuantity());
        }
        orderList.add(order);
        return orderList;
    }
    public Map<Customer, Double> calculateTotalPerCustomer(){
        Map<Customer, Double> listCalc = new HashMap<>();
        cart.forEach((customer, orders) -> {
            double[] total = {0.0};
            orders.forEach(order -> {
                for(Item i: order.getProduct()){
                    total[0] += i.getProduct().getPrice() * i.getQuantity();
                }
            });
                listCalc.put(customer, total[0]);
        });
        return listCalc;
    }
    public Optional<Customer> customerWhoSpentTheMost(){
        Customer topCustomer = null;
        Double maxValue = 0.0;
        Map<Customer, Double> dataOrders = calculateTotalPerCustomer();
        for(Map.Entry<Customer, Double> entry : dataOrders.entrySet()){
            if(entry.getValue() > maxValue){
                maxValue = entry.getValue();
                topCustomer = entry.getKey();
            }
        }
        return Optional.ofNullable(topCustomer);
    }

    @Override
    public String toString() {
        return "Cart: "+ cart;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart1 = (Cart) o;
        return Objects.equals(cart, cart1.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cart);
    }
}
