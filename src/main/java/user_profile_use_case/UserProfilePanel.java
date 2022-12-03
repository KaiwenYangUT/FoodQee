package user_profile_use_case;

import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserProfilePanel extends JPanel implements UserProfilePanelInterface {
    private static final Color BG_DARK_GREEN =  new Color(38, 73, 65);
    private static final Color GREY_WHITE = new Color(214, 210, 205);
    private static final Border emptyBorder3 = BorderFactory.createEmptyBorder(200, 0, 20, 0);

    private JLabel nameLabel = new JLabel("Name");
    private JLabel emailLabel = new JLabel("Email");
    private UserProfileController userProfileController;

    public UserProfilePanel(ObjectId userId) {
        GridLayout gridLayout = new GridLayout(0,1);

        UserProfilePresenter userProfilePresenter = new UserProfileProcessor(this);
        UserProfileInputBoundary userProfileInteractor = new UserProfileInteractor(userProfilePresenter);
        userProfileController = new UserProfileController(userProfileInteractor, userId);

        updatePanelData();

        JPanel centrePanel = new JPanel(gridLayout);
        centrePanel.setOpaque(false);
        nameLabel.setFont(new Font("Serif", Font.BOLD|Font.ITALIC, 40));
        nameLabel.setForeground(GREY_WHITE);
        nameLabel.setBorder(emptyBorder3);
        centrePanel.add(nameLabel);
        emailLabel.setFont(new Font("Serif", Font.BOLD|Font.ITALIC, 40));
        emailLabel.setForeground(GREY_WHITE);
        emailLabel.setBorder(emptyBorder3);
        centrePanel.add(emailLabel);

        this.add(centrePanel, BorderLayout.CENTER);

        // Add button panel
        LayoutManager gridLayout1 = new GridLayout(0,2);
        JPanel buttonsPanel = new JPanel(gridLayout1);

        JButton changeInfoButton = new JButton("Change Info");
        changeInfoButton.setOpaque(false);
        changeInfoButton.setFont(new Font("Serif", Font.BOLD, 15));
        changeInfoButton.setForeground(BG_DARK_GREEN);
        changeInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        buttonsPanel.add(changeInfoButton);

        JButton changePasswordPanel = new JButton("Change Password");
        changePasswordPanel.setOpaque(false);
        changePasswordPanel.setFont(new Font("Serif", Font.BOLD, 15));
        changePasswordPanel.setForeground(BG_DARK_GREEN);
        changePasswordPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        buttonsPanel.add(changePasswordPanel);

        this.add(buttonsPanel, BorderLayout.SOUTH);
    }


    /**
     * @param newName
     */
    @Override
    public void updateNameLabel(String newName) {
        nameLabel.setText(newName);
    }

    public void updateEmailLabel(String newEmail) {
        emailLabel.setText(newEmail);
    }

    public void updatePanelData() {
        userProfileController.getUserProfile();
    }
}