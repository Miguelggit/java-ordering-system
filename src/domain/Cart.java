package domain;
import exception.DuplicateOrderException;
import exception.EmptyCartException;
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
        if(orderList.contains(order)){
            throw new DuplicateOrderException("Order with id " + order.getId() + " already exists in cart.");
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
        if(cart.isEmpty()){
            throw new EmptyCartException("Cart is empty. No data available for this operation.");
        }
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
        for(Map.Entry<Customer, Double> entry : calculateTotalPerCustomer().entrySet()){
            if(entry.getValue() > maxValue){
                maxValue = entry.getValue();
                topCustomer = entry.getKey();
            }
        }
        return Optional.ofNullable(topCustomer);
    }
    public Optional<Product> productBestSelling() {
        Map<Product, Integer> productQuantities = new HashMap<>();
        if(cart.isEmpty()){
            throw new EmptyCartException("Cart is empty. No data available for this operation.");
        }
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

    public List<Customer> top3Customers(){
        List<Map.Entry<Customer, Double>> list = new ArrayList<>(calculateTotalPerCustomer().entrySet());
        List<Customer> topTiers = new ArrayList<>();
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        list.forEach( o1 -> {
            if(topTiers.size() < 3){
                topTiers.add(o1.getKey());
            }
        });
        return topTiers;
    }

    public Double avgValueByOrder(){
        Double total = 0.0;
        int quantity=0;
        if(cart.isEmpty()){
            throw new EmptyCartException("Cart is empty. No data available for this operation.");
        }
        for(Map.Entry<Customer, List<Order>> input: cart.entrySet()){ //* Entro no meu cart
            for( Order o: input.getValue()){ //* Para cada ordem que eu tenho eu vou somar todos os itens de acordo com a quantidade que foi pedida e alocar no total
                for(Item i : o.getProduct()){ //* Para cada item irei fazer a soma total de cada item que estiver na lista, resumindo se uma ordem tiver 2 itens eu irei fazer a soma total destes dois itens e logo em seguida irei adicionar uma quantity
                    total += i.getQuantity() * i.getProduct().getPrice(); //* soma dos itens
                }
                quantity++;
            }
        }
        return total / quantity;
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
