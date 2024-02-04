import lt.techin.shoppingcart.ShoppingCart;
import lt.techin.shoppingcart.ShoppingCartModificationException;
import lt.techin.shoppingcart.ShoppingItem;
import lt.techin.shoppingcart.test.BaseShoppingCartTest;

import java.util.*;
import java.util.stream.Collectors;


public class ShoppingCartTest extends BaseShoppingCartTest {

    public ShoppingCartTest() {
    }

    @Override
    protected ShoppingCart getLockedShoppingCartWithDiscountAndTaxApplied(ShoppingCart shoppingCart, int discountRate, int taxRate) {

        return getLockedFromModificationShoppingCart(getDiscountAppliedShoppingCart(getTaxAppliedShoppingCart(shoppingCart, taxRate), discountRate));

    }

    @Override
    protected ShoppingCart getDiscountAppliedShoppingCart(ShoppingCart shoppingCart, int discountRate) {


        List<ShoppingItem> itemsWithTax = shoppingCart.getShoppingCartItems().stream()
                .map(item -> new ShoppingItem(item.getName(), (item.getPrice()) * (discountRate / 100.0)))
                .collect(Collectors.toList());


        ShoppingCartImpl shoppingCart2 = new ShoppingCartImpl();
        Iterator var2 = itemsWithTax.iterator();

        while (var2.hasNext()) {
            ShoppingItem predefinedShoppingItem = (ShoppingItem) var2.next();
            shoppingCart2.addShoppingItem(predefinedShoppingItem);
        }


        return shoppingCart2;

    }

    @Override
    protected ShoppingCart getTaxAppliedShoppingCart(ShoppingCart shoppingCart, int taxRate) {

        List<ShoppingItem> itemsWithTax = shoppingCart.getShoppingCartItems().stream()
                .map(item -> new ShoppingItem(item.getName(), (item.getPrice() + (item.getPrice()) * (taxRate / 100.0))))
                .collect(Collectors.toList());


        ShoppingCartImpl shoppingCart2 = new ShoppingCartImpl();
        Iterator var2 = itemsWithTax.iterator();

        while (var2.hasNext()) {
            ShoppingItem predefinedShoppingItem = (ShoppingItem) var2.next();
            shoppingCart2.addShoppingItem(predefinedShoppingItem);
        }


        return shoppingCart2;


    }


    @Override
    protected ShoppingCart getLockedFromModificationShoppingCart(ShoppingCart shoppingCart) {

        ShoppingCartImpl shoppingCart2 = new ShoppingCartImpl();
        Iterator var2 = shoppingCart.getShoppingCartItems().iterator();

        while (var2.hasNext()) {
            ShoppingItem predefinedShoppingItem = (ShoppingItem) var2.next();
            shoppingCart2.addShoppingItem(predefinedShoppingItem);
        }

        shoppingCart2.setLockedStatus();
        return shoppingCart2;
    }


    private static class ShoppingCartImpl implements ShoppingCart {
        private final Collection<ShoppingItem> shoppingItems = new HashSet<>();
        private boolean lockedStatus = false;


        private ShoppingCartImpl() {
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
}
