package menu_editing_use_case;
import javax.swing.*;

//Interface Adapters Layer;


public interface MenuEditingPresenter {


    MenuEditingResponseModel prepareSuccessView(MenuEditingResponseModel responseModel);

    MenuEditingResponseModel prepareFailView(String str);

    JPanel getMenuPanel();
}