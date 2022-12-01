package SearchingAlgorithmUseCase;

public class UserSearchRestaurantsController {
    private UserSearchRestaurantsInputBoundary restaurantName;

    public UserSearchRestaurantsController(UserSearchRestaurantsInputBoundary restaurantName) {
        this.restaurantName = restaurantName;
    }

    public UserSearchRestaurantsResponseModel search(UserSearchRestaurantsRequestModel requestModel) {
        return restaurantName.search(requestModel);
    }
}
