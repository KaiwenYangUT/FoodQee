package verify_user_use_case;

import org.bson.types.ObjectId;

public class VerifyUserProcessor implements VerifyUserPresenter{
    /**
     * @param currentUser
     * @return
     */
    @Override
    public String verifiedSuccess(ObjectId currentUser) {
        // Go to user's homepage and pass the current user
        return null;
    }

    /**
     * @return
     */
    @Override
    public String verifiedFailed() {
        return null;
    }
}
