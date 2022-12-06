package user_order_history_use_case;


import entities.Order;
import user_make_review_use_case.MakeReviewController;
import user_make_review_use_case.screens.MakeReviewScreen;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderHistoryPanel extends JPanel implements OrderHistoryPanelInterface{

    OrderHistoryController orderHistoryController;

    private static final Color BG_DARK_GREEN =  new Color(38, 73, 65);
    private static final Color GREY_WHITE = new Color(214, 210, 205);

    private static final Border emptyBorder = BorderFactory.createEmptyBorder(30, 30, 30, 30);
    private static final Border emptyBorder2 = BorderFactory.createEmptyBorder(0, 10, 0, 10);
    private static final Border blackline = BorderFactory.createLineBorder(Color.black);

    private JPanel orderDisplayPanel = new JPanel();

    public OrderHistoryPanel(OrderHistoryController controller){

        this.orderHistoryController = controller;


        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(BG_DARK_GREEN);
        String orderTitle = controller.getResponse().getName() + "'s " + "Order History";

        JLabel title = new JLabel(orderTitle);
        title.setFont(new Font("Serif", Font.BOLD|Font.ITALIC, 40));
        title.setForeground(GREY_WHITE);
        title.setBorder(emptyBorder);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setOpaque(false);
        refreshButton.setForeground(BG_DARK_GREEN);

        titlePanel.add(title);
        titlePanel.add(refreshButton);

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderHistoryController.getOrders();
            }
        });

        orderDisplayPanel.setBackground(GREY_WHITE);
        orderDisplayPanel.setBorder(blackline);
        GridLayout layout = new GridLayout(0 ,2);
        layout.setVgap(40);
        layout.setHgap(40);
        orderDisplayPanel.setLayout(layout);
        orderDisplayPanel.setBorder(emptyBorder);

        JScrollPane orderHistoryScroll = new JScrollPane(orderDisplayPanel);
        orderHistoryScroll.setBorder(emptyBorder2);
        orderHistoryScroll.setBackground(BG_DARK_GREEN);
        orderHistoryScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        title.setVerticalAlignment(JLabel.CENTER);

        JLabel orderHistory = new JLabel();
        orderHistory.setText("You do not have any order history");
        orderHistory.setFont(new Font("Serif", Font.BOLD|Font.ITALIC, 60));
        orderDisplayPanel.add(orderHistory);


        this.setLayout(new BorderLayout());
        this.add(orderHistoryScroll, BorderLayout.CENTER);
        this.add(titlePanel, BorderLayout.NORTH);
    }

    @Override
    public void setOrder(OrderHistoryResponseModel orderHistoryResponseModel) {
        orderDisplayPanel.removeAll();
        for(Order order : orderHistoryResponseModel.getOrders()){
            JPanel orderPanel = new JPanel();
            orderPanel.setLayout(new GridLayout(0, 1));
            resIdToNameConvertor convertor = new resIdToNameConvertor();
            JLabel resName = new JLabel("Restaurant Name: " + convertor.getResNameById(order.getRestaurantID()));
            JLabel orderTime = new JLabel("Order Time: " + order.getOrderDate());
            JLabel orderStatus = new JLabel("Order Status: " + order.getOrderStatus());
            JButton viewDetails = new JButton("View Details");
            JButton makeReview = new JButton("Make Review");

            viewDetails.setForeground(BG_DARK_GREEN);

            resName.setForeground(BG_DARK_GREEN);
            resName.setBorder(emptyBorder2);
            orderTime.setForeground(BG_DARK_GREEN);
            orderTime.setBorder(emptyBorder2);
            orderStatus.setForeground(BG_DARK_GREEN);
            orderStatus.setBorder(emptyBorder2);

            viewDetails.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    OrderHistoryDetailScreen detailScreen = new OrderHistoryDetailScreen(orderHistoryController, order);
                }
            });

            makeReview.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MakeReviewController makeReviewController = new MakeReviewController();
                    new MakeReviewScreen(makeReviewController, order);
                }
            });

            orderPanel.add(resName);
            orderPanel.add(orderTime);
            orderPanel.add(orderStatus);
            orderPanel.add(viewDetails);
            orderPanel.add(makeReview);


            orderDisplayPanel.add(orderPanel);
        }

        orderDisplayPanel.revalidate();
        orderDisplayPanel.repaint();
    }

    @Override
    public void updateOrder() {
        orderHistoryController.getOrders();
    }
}

