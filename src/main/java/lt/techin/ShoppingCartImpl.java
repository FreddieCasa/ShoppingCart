package lt.techin;

import lt.techin.shoppingcart.ShoppingCart;
import lt.techin.shoppingcart.ShoppingCartModificationException;
import lt.techin.shoppingcart.ShoppingItem;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class ShoppingCartImpl implements ShoppingCart {
    private Collection<ShoppingItem> shoppingItems;
    private int discountRate = 0;
    private int taxRate = 0;


    public ShoppingCartImpl(ShoppingCart shoppingCart) {

        this.shoppingItems = shoppingCart.getShoppingCartItems();

    }

    public ShoppingCartImpl(ShoppingCart shoppingCart, int discountRate, int taxRate) {
        this.shoppingItems = shoppingCart.getShoppingCartItems();
        this.discountRate = discountRate;
        this.taxRate = taxRate;

    }


    public void addShoppingItem(ShoppingItem shoppingItem) throws ShoppingCartModificationException {
        if (this.shoppingItems == Collections.unmodifiableCollection(this.shoppingItems)) {
            throw new ShoppingCartModificationException();
        } else {
            shoppingItems.add(shoppingItem);
        }

    }

    public void removeShoppingItem(ShoppingItem shoppingItem) {

        if (this.shoppingItems == Collections.unmodifiableCollection(this.shoppingItems)) {
            throw new ShoppingCartModificationException();
        } else {
            this.shoppingItems.remove(shoppingItem);
        }

    }

    public void clearShoppingCart() {
        if (this.shoppingItems == Collections.unmodifiableCollection(this.shoppingItems)) {
            throw new ShoppingCartModificationException();
        } else {
            this.shoppingItems.clear();
        }

    }

    public Collection<ShoppingItem> getShoppingCartItems() {
        return Collections.unmodifiableCollection(this.shoppingItems);
    }

    public double calculateTotalPrice() {

        if (discountRate > 0 && taxRate > 0) {

            this.shoppingItems = this.shoppingItems.stream()
                    .map(item -> new ShoppingItem(item.getName(), (item.getPrice()) * (discountRate / 100.0)))
                    .collect(Collectors.toList());

            this.shoppingItems = this.shoppingItems.stream()
                    .map(item -> new ShoppingItem(item.getName(), (item.getPrice() + (item.getPrice()) * (taxRate / 100.0))))
                    .collect(Collectors.toList());

            return this.shoppingItems.stream().mapToDouble(ShoppingItem::getPrice).sum();

        } else if (discountRate > 0) {
            this.shoppingItems = this.shoppingItems.stream()
                    .map(item -> new ShoppingItem(item.getName(), (item.getPrice()) * (discountRate / 100.0)))
                    .collect(Collectors.toList());
            return this.shoppingItems.stream().mapToDouble(ShoppingItem::getPrice).sum();

        } else if (taxRate > 0) {

            this.shoppingItems = this.shoppingItems.stream()
                    .map(item -> new ShoppingItem(item.getName(), (item.getPrice() + (item.getPrice()) * (taxRate / 100.0))))
                    .collect(Collectors.toList());

            return this.shoppingItems.stream().mapToDouble(ShoppingItem::getPrice).sum();

        } else {
            return this.shoppingItems.stream().mapToDouble(ShoppingItem::getPrice).sum();
        }

    }


}

