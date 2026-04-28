package domain;
import java.util.*;

public class Cart{
    private Map<Customer, List<Order>> orders = new HashMap<>();
    public Map<Customer, List<Order>> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "Cart: "+ orders;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart1 = (Cart) o;
        return Objects.equals(orders, cart1.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orders);
    }
}
