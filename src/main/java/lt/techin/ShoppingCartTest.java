package lt.techin;


import lt.techin.shoppingcart.ShoppingCart;

import lt.techin.shoppingcart.ShoppingItem;
import lt.techin.shoppingcart.test.BaseShoppingCartTest;
import java.util.*;
import java.util.stream.Collectors;


public class ShoppingCartTest extends BaseShoppingCartTest {

    public ShoppingCartTest() {
    }

    @Override
    protected ShoppingCart getLockedShoppingCartWithDiscountAndTaxApplied(ShoppingCart shoppingCart, int discountRate, int taxRate) {

        return new ShoppingCartImpl(shoppingCart, discountRate, taxRate);

    }

    @Override
    protected ShoppingCart getDiscountAppliedShoppingCart(ShoppingCart shoppingCart, int discountRate) {

        return new ShoppingCartImpl(shoppingCart,discountRate,0);

    }

    @Override
    protected ShoppingCart getTaxAppliedShoppingCart(ShoppingCart shoppingCart, int taxRate) {

        return new ShoppingCartImpl(shoppingCart,0,taxRate);

    }

    @Override
    protected ShoppingCart getLockedFromModificationShoppingCart(ShoppingCart shoppingCart) {

        return new ShoppingCartImpl(shoppingCart);
    }


}