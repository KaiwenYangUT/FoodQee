package user_shopping_cart_use_case;

import org.bson.types.ObjectId;

import java.util.HashMap;

public interface ShoppingCartPresenter {
    public void displayShoppingCart(HashMap<String, HashMap<String, Object>> shoppingCartDisplay);
    void setScreen(ShoppingCartPanelInterface screen);
}
