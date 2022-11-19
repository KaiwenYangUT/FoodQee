import LoginUseCase.UserLoginController;
import LoginUseCase.UserLoginInputBoundary;
import LoginUseCase.UserLoginInteractor;
import LoginUseCase.UserLoginPresenter;
import MenuEditingUseCase.UserLoginProcessor;
import Presenters.*;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        // Build the main program window
        JFrame application = new JFrame("Login Example");
        CardLayout cardLayout = new CardLayout();
        JPanel screens = new JPanel(cardLayout);
        application.add(screens);


        // Build the GUI, plugging in the parts
        UserLoginPresenter presenter = new UserLoginProcessor();
        UserLoginInputBoundary interactor = new UserLoginInteractor(presenter);
        UserLoginController userLoginController = new UserLoginController(interactor);

        LoginScreen loginScreen = new LoginScreen(userLoginController);

        screens.add(loginScreen.getContentPane(), "login");
        cardLayout.show(screens, "login");
        application.pack();
        application.setVisible(true);

        // Unused screens; we'll uncomment this later
//        WelcomeScreen welcomeScreen = new WelcomeScreen();
//        LoginScreen loginScreen = new LoginScreen();
//        LoggedInScreen loggedInScreen = new LoggedInScreen();
//        screens.add(welcomeScreen, "register");
//        screens.add(loginScreen, "login");
//        screens.add(loggedInScreen, "loggedIn");

    }

}