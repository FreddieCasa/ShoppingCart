package lt.techin;

import lt.techin.shoppingcart.ShoppingCart;
import lt.techin.shoppingcart.ShoppingCartModificationException;
import lt.techin.shoppingcart.ShoppingItem;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class ShoppingCartImpl implements ShoppingCart {
    private final Collection<ShoppingItem> shoppingItems = new HashSet<>();
    private boolean lockedStatus = false;

    public ShoppingCartImpl() {
    }

    public void addShoppingItem(ShoppingItem shoppingItem) throws ShoppingCartModificationException {
        if (this.lockedStatus) {
            throw new ShoppingCartModificationException();
        } else {
            shoppingItems.add(shoppingItem);
        }


    }

    public void removeShoppingItem(ShoppingItem shoppingItem) {

        if (this.lockedStatus) {
            throw new ShoppingCartModificationException();
        } else {
            this.shoppingItems.remove(shoppingItem);
        }

    }

    public void clearShoppingCart() {
        if (this.lockedStatus) {
            throw new ShoppingCartModificationException();
        } else {
            this.shoppingItems.clear();
        }

    }

    public Collection<ShoppingItem> getShoppingCartItems() {
        return Collections.unmodifiableCollection(this.shoppingItems);
    }

    public double calculateTotalPrice() {

        return this.shoppingItems.stream().mapToDouble(ShoppingItem::getPrice).sum();
    }

    public void setLockedStatus() {
        this.lockedStatus = true;
    }


}

