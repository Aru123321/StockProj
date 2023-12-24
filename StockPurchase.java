//template taken from https://gist.github.com/jenndotcodes/b15b610aaf5c6ffc9282

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class StockPurchase {
    StockPurchaseFrame stockPurchaseFrame = null;

    public StockPurchase(CustomersDashboard cDashboard) throws SQLException {
        stockPurchaseFrame = new StockPurchaseFrame(cDashboard);
        stockPurchaseFrame.setTitle("Transaction");
        stockPurchaseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        stockPurchaseFrame.setVisible(true);
    }
}
class StockPurchaseFrame extends JFrame
{
    public StockPurchaseFrame(CustomersDashboard cDashboard) throws SQLException {
        StockPurchasePanel stockPurchasePanel = new StockPurchasePanel(StockPurchaseFrame.this, cDashboard);
        add(stockPurchasePanel);
        pack();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println(CurrentUsers.getUsers());
                cDashboard.setVisible(true);
                dispose(); // Call closeWithUpdate when window is closing
            }
        });
    }

}

class StockPurchasePanel extends JPanel implements ActionListener {
    List<String> stocksfinal = new ArrayList<>();

    private String[] colors = { "Black", "White", "Red", "Pink", "Green",
            "Yellow" };
    private String[] quantities = { "1", "2", "3","4","5","6","7","8", "9","10"};
    private JLabel imageLabel;
    private JComboBox sizeCombo;
    private JLabel colorCombo;
    private JLabel quantityLabel;
    private JLabel labelTitle;
    private JComboBox quantityCombo;
    private JButton calcButton;
    private JLabel resultLabel;
    private JLabel stockLabel;
    private JButton buyButton;

    private JButton exitButton;

    private JLabel accountBalance;

    private double balance;


    private double currentCost;

    public StockPurchasePanel(StockPurchaseFrame stockPurchaseFrame, CustomersDashboard cDashboard) throws SQLException {
        Database.getInstance();

        ResultSet stocks = Queries.fetchAllStocks();
        while (stocks.next()){
            String ticker = stocks.getString("Ticker");
            stocksfinal.add(ticker);
        }

        String[] stocksNew = stocksfinal.toArray(new String[0]);


        // Instantiate all the controls
        sizeCombo = new JComboBox(stocksNew);
        colorCombo = new JLabel();
        quantityLabel = new JLabel("Quantity");
        labelTitle = new JLabel("Stock Purchase");
        quantityCombo = new JComboBox(quantities);
        stockLabel = new JLabel("Stock:");
        calcButton = new JButton("Total");
        buyButton = new JButton("Buy Stocks");
        exitButton = new  JButton("Exit");

        /*buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    new StockPurchase(CustomerDashboard.this);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });*/
        balance = cDashboard.getCustomer().getBalance();

        accountBalance = new JLabel("Available balance: $" + String.valueOf(balance));

        resultLabel = new JLabel("");

        // Pretty up the labels
        labelTitle.setFont(new Font("Impact", Font.BOLD, 28));
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setFont(new Font("Impact", Font.PLAIN, 16));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);


        // Arrange the controls onto the screen
        this.setLayout(new BorderLayout());
        this.add(labelTitle, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        this.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JPanel deptPanel = new JPanel();

        JPanel comboPanel = new JPanel();
        comboPanel.add(accountBalance);
        comboPanel.add(stockLabel);
        comboPanel.add(sizeCombo);
        comboPanel.add(colorCombo);

        JPanel quantPanel = new JPanel();
        quantPanel.add(quantityLabel);
        quantPanel.add(quantityCombo);

        centerPanel.add(deptPanel);
        centerPanel.add(comboPanel);
        centerPanel.add(quantPanel);
        centerPanel.add(Box.createGlue());
        centerPanel.add(buyButton);
        centerPanel.add(exitButton);

        add(resultLabel, BorderLayout.SOUTH);
        buyButton.setVisible(false);

        // Make button responsive to clicks
        calcButton.addActionListener(this);

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cDashboard.setVisible(true);
                stockPurchaseFrame.dispose();
            }
        });

        sizeCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected stock from sizeCombo
                String selectedStock = sizeCombo.getSelectedItem().toString();
                System.out.println(selectedStock);

                // fetch the price of the selected stock
                currentCost  = Queries.fetchPriceByTicker(selectedStock); // Replace this with your logic

                int quantity = Integer.parseInt(quantityCombo.getSelectedItem()
                        .toString());
                currentCost *= quantity;

                if ( cDashboard.getCustomer().getBalance() >= currentCost) {
                    buyButton.setVisible(true);
                    resultLabel.setText("");
                }
                else{
                    resultLabel.setText("Not enough funds");
                    buyButton.setVisible(false);
                }

                // Display the price in the colorCombo JLabel
                colorCombo.setText("Price: $" + currentCost); // Display the price as a label text
            }
        });

        quantityCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected stock from sizeCombo
                String selectedStock = sizeCombo.getSelectedItem().toString();
                System.out.println(selectedStock);


                currentCost = Queries.fetchPriceByTicker(selectedStock); // Replace this with your logic

                int quantity = Integer.parseInt(quantityCombo.getSelectedItem()
                        .toString());
                currentCost *= quantity;

                if ( cDashboard.getCustomer().getBalance() >= currentCost) {
                    buyButton.setVisible(true);
                    resultLabel.setText("");
                }
                else{
                    resultLabel.setText("Not enough funds");
                    buyButton.setVisible(false);
                }

                // Display the price in the colorCombo JLabel
                colorCombo.setText("Price: $" + currentCost); // Display the price as a label text
            }
        });

        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedStock = sizeCombo.getSelectedItem().toString();

                // fetch the price of the selected stock
                double price = Queries.fetchPriceByTicker(selectedStock);

                Share newPurchase = new Share(selectedStock, Integer.parseInt(quantityCombo.getSelectedItem().toString()), price, cDashboard.getCustomer().getUsername()); // Replace this with your logic
                System.out.println( cDashboard.getCustomer().getShares());
                cDashboard.getCustomer().addShare(newPurchase);
                System.out.println( cDashboard.getCustomer().getShares());
                resultLabel.setText(String.format("Done! Bought %d shares for $%s at $%s per share.", Integer.parseInt(quantityCombo.getSelectedItem()
                        .toString()), String.valueOf(currentCost), price));


                System.out.println(balance);
                balance -= currentCost;
                accountBalance.setText("Available balance: $" + String.valueOf(balance));
                cDashboard.getCustomer().updateBalance(balance);


                int index = cDashboard.getCustomer().getShares().indexOf(newPurchase);
                Share dbShare = cDashboard.getCustomer().getShares().get(index);
                Queries.updateShare(cDashboard.getCustomer().getUsername(),selectedStock,dbShare.getNumber(),dbShare.getAveragePrice());

                Queries.updateUserBalance(cDashboard.getCustomer().getUsername(), balance);


                System.out.println(balance);
                if (balance <currentCost) {
                    buyButton.setVisible(false);
                    resultLabel.setText("Not enough funds");
                }

            }
        });

    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(500, 300);
    }

    public void actionPerformed(ActionEvent arg0)
    {
        String size = sizeCombo.getSelectedItem().toString();
        String color = colorCombo.toString();
        int quantity = Integer.parseInt(quantityCombo.getSelectedItem()
                .toString());

    }



}
