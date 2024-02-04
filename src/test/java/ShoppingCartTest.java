import lt.techin.shoppingcart.ShoppingCart;
import lt.techin.shoppingcart.ShoppingCartModificationException;
import lt.techin.shoppingcart.ShoppingItem;
import lt.techin.shoppingcart.test.BaseShoppingCartTest;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;


public class ShoppingCartTest extends BaseShoppingCartTest {

    public ShoppingCartTest() {
    }

    @Override
    protected ShoppingCart getLockedShoppingCartWithDiscountAndTaxApplied(ShoppingCart shoppingCart, int discountRate, int taxRate) {

        ShoppingCartImpl shoppingCart2 = new ShoppingCartImpl();
        Iterator var2 = shoppingCart.getShoppingCartItems().iterator();

        while (var2.hasNext()) {
            ShoppingItem predefinedShoppingItem = (ShoppingItem) var2.next();
            shoppingCart2.addShoppingItem(predefinedShoppingItem);
        }

        shoppingCart2.setLockedStatus();

        return shoppingCart2;

    }

    @Override
    protected ShoppingCart getDiscountAppliedShoppingCart(ShoppingCart shoppingCart, int discountRate) {
        return new ShoppingCartImpl();
    }

    @Override
    protected ShoppingCart getTaxAppliedShoppingCart(ShoppingCart shoppingCart, int taxRate) {
        ShoppingCartImpl shoppingCart2 = new ShoppingCartImpl();
        Iterator var2 = shoppingCart.getShoppingCartItems().iterator();


        while (var2.hasNext()) {
            ShoppingItem predefinedShoppingItem = (ShoppingItem) var2.next();
            shoppingCart2.addShoppingItem(predefinedShoppingItem);
        }

        shoppingCart2.applyTax(taxRate);

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

        private boolean taxStatus = false;
        private boolean discountStatus = false;


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

        public void applyTax(int tax) {
//            this.taxStatus = true;




        }

        public void setDiscountStatus() {
//            this.discountStatus = true;
        }
    }
}
