package change_user_info_use_case;

import change_password_use_case.*;
import org.bson.types.ObjectId;

public class TempTestChangeUserInfoScreen {
    public static void main(String[] args) {
        ChangeUserInfoPresenter changeUserInfoPresenter = new ChangeUserInfoProcessor(null);
        ChangeUserInfoInputBoundary changeUserInfoInputBoundary = new ChangeUserInfoInteractor(changeUserInfoPresenter);
        ChangeUserInfoController changeUserInfoController = new ChangeUserInfoController(changeUserInfoInputBoundary, new ObjectId("63335e7abb6cd6599ed6f64b"));

        ChangeUserInfoScreenInterface changeUserInfoScreen = new ChangeUserInfoScreen(changeUserInfoController);

        changeUserInfoPresenter.setScreen(changeUserInfoScreen);

        changeUserInfoScreen.getFrame().setVisible(true);
    }
}