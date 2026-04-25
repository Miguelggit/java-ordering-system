package domain;
import exception.DuplicateOrderException;
import exception.EmptyCartException;
import exception.StockProductException;
import java.util.*;

public class Cart{
    private Map<Customer, List<Order>> cart = new HashMap<>();

    public Map<Customer, List<Order>> getCart() {
        return cart;
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
