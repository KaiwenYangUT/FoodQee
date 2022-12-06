package restaurant_verify_use_case;

import org.bson.types.ObjectId;

import javax.swing.*;

public interface VerifyResScreenInterface {
    JFrame getFrame();
    void showLoginScreen();
    void showRestaurantHomePage(ObjectId restaurantId, String restaurantName);
    void close();
    void showMessage(String message);
}