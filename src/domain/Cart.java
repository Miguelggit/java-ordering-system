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

    public Optional<Product> productBestSelling() {
        Map<Product, Integer> productQuantities = new HashMap<>();
        for (Map.Entry<Customer, List<Order>> entry : cart.entrySet()) {
            entry.getValue().forEach(order -> {
                for (Item i : order.getProduct()) {
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
