//template taken from https://gist.github.com/jenndotcodes/b15b610aaf5c6ffc9282

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;



public class StockSale {
    StockSaleFrame stockSaleFrame = null;

    public StockSale(CustomersDashboard cDashboard) throws SQLException {
        stockSaleFrame = new StockSaleFrame(cDashboard);
        stockSaleFrame.setTitle("Transaction");
        stockSaleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        stockSaleFrame.setVisible(true);
    }
}
class StockSaleFrame extends JFrame
{
    public StockSaleFrame(CustomersDashboard cDashboard) throws SQLException {
        StockSalePanel stockSalePanel = new StockSalePanel(StockSaleFrame.this, cDashboard);
        add(stockSalePanel);
        setSize(500, 500);
        pack();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println(CurrentUsers.getUsers());
                cDashboard.setVisible(true);
                cDashboard.getCustomer().updateDBCustomer();
                dispose(); // Call closeWithUpdate when window is closing
            }
        });
    }

}

class StockSalePanel extends JPanel implements ActionListener {
    List<String> stocksfinal = new ArrayList<>();

    private String[] colors = { "Black", "White", "Red", "Pink", "Green",
            "Yellow" };
    private List<String> quantities;
    private JLabel imageLabel;
    private JComboBox sizeCombo;
    private JLabel colorCombo;
    private JLabel quantityLabel;
    private JLabel labelTitle;
    private JComboBox quantityCombo;
    private JButton calcButton;
    private JLabel resultLabel;
    private JLabel stockLabel;
    private JButton sellButton;

    private JButton exitButton;

    private JLabel accountBalance;

    private double balance;


    private double currentCost;

    private Object stockSelected;

    public StockSalePanel(StockSaleFrame stockSaleFrame, CustomersDashboard cDashboard) throws SQLException {
        Database.getInstance();

        ResultSet shares = Queries.fetchSharesByUsername(cDashboard.getCustomer().getUsername());
        while (shares.next()){
            String ticker = shares.getString("ticker");
            String num = String.valueOf(shares.getInt("numShares"));
            stocksfinal.add(ticker);
        }

        String[] stocksNew = stocksfinal.toArray(new String[0]);




        // Instantiate all the controls
        sizeCombo = new JComboBox(stocksNew);
        colorCombo = new JLabel();
        quantityLabel = new JLabel("Quantity");
        labelTitle = new JLabel("Stock Sale");
        stockLabel = new JLabel("Stock:");
        calcButton = new JButton("Total");
        sellButton = new JButton("Sell Stocks");
        exitButton = new  JButton("Exit");
        quantityCombo = new JComboBox();

        /*sellButton.addActionListener(new ActionListener() {
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

        accountBalance = new JLabel("Current balance: $" + String.valueOf(balance));

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
        comboPanel.add(colorCombo);

        quantityCombo.setVisible(false);

        JPanel quantPanel = new JPanel();
        quantPanel.add(stockLabel);
        quantPanel.add(sizeCombo);
        quantPanel.add(quantityLabel);
        quantPanel.add(quantityCombo);

        centerPanel.add(deptPanel);
        centerPanel.add(comboPanel);
        centerPanel.add(quantPanel);
        centerPanel.add(Box.createGlue());
        centerPanel.add(sellButton);
        centerPanel.add(exitButton);

        add(resultLabel, BorderLayout.SOUTH);
        quantityLabel.setVisible(false);
        sellButton.setVisible(false);

        // Make button responsive to clicks
        calcButton.addActionListener(this);

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cDashboard.setVisible(true);
                cDashboard.getCustomer().updateDBCustomer();

                stockSaleFrame.dispose();
            }
        });

        sizeCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (sizeCombo.getSelectedItem() != null && sizeCombo.getItemCount() > 0) {
                    String selectedStock = sizeCombo.getSelectedItem().toString();
                    stockSelected = sizeCombo.getSelectedItem();
                    System.out.println(selectedStock);

                    // Fetch the available shares for the selected stock
                    int availableShares; // Replace this with your logic to fetch available shares
                    try {
                        availableShares = Queries.fetchNumSharesByTicker(selectedStock);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    // Clear the existing items in quantityCombo
                    quantityCombo.removeAllItems();

                    // Add the available shares as items in quantityCombo
                    for (int i = 1; i <= availableShares; i++) {
                        quantityCombo.addItem(String.valueOf(i));
                    }

                    // Fetch the price of the selected stock
                    currentCost = Queries.fetchPriceByTicker(selectedStock);

                    int quantity = Integer.parseInt(quantityCombo.getSelectedItem().toString());

                    if (availableShares > 0) {
                        System.out.println("GOTEHEREEEEE");
                        quantityCombo.setVisible(true);
                        sellButton.setVisible(true);
                        resultLabel.setText("");
                    } else {
                        quantityCombo.setVisible(false);
                        quantityLabel.setVisible(false);
                        resultLabel.setText("No shares left");
                        sellButton.setVisible(false);
                    }
                    double averagePurchasePrice = 0;
                    try {
                        averagePurchasePrice = Queries.fetchAvgPriceByTicker(selectedStock, cDashboard.getCustomer().getUsername());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    double realizedProfit = (currentCost - averagePurchasePrice) * quantity;

                    currentCost *= quantity;

                    colorCombo.setText("Profit: $" + currentCost + " Realized Profit: $" + realizedProfit); // Display the price as a label text
                }
            }
        });

        quantityCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected stock from sizeCombo

                if (!(quantityCombo.getSelectedItem() == null)) {
                    sellButton.setVisible(true);
                    String selectedStock = sizeCombo.getSelectedItem().toString();
                    System.out.println(selectedStock);

                    currentCost = Queries.fetchPriceByTicker(selectedStock);

                    int quantity = Integer.parseInt(quantityCombo.getSelectedItem().toString());
                    double averagePurchasePrice;

                    try {
                        averagePurchasePrice = Queries.fetchAvgPriceByTicker(selectedStock,cDashboard.getCustomer().getUsername());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    double realizedProfit = (currentCost - averagePurchasePrice) * quantity;

                    currentCost *= quantity;

                    colorCombo.setText("Profit: $" + currentCost + " Realized Profit: $" + realizedProfit); // Display the price as a label text
                }
            }
        });

        sellButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // set ticker and amount of shares sold
                String selectedStock = sizeCombo.getSelectedItem().toString();
                boolean append = false;
                int quantity = Integer.parseInt(quantityCombo.getSelectedItem().toString());

                try {


                    // calculate/get avgPrice bought(compare to current stock price), balance,
                    double balance = cDashboard.getCustomer().getBalance();
                    int availableShares = Queries.fetchNumSharesByTicker(selectedStock);
                    double averagePurchasePrice = Queries.fetchAvgPriceByTicker(selectedStock,cDashboard.getCustomer().getUsername());
                    if (quantity == availableShares){
                        sellButton.setVisible(false);
                        stocksfinal.remove(selectedStock); // Remove the specific element
                        if (sizeCombo.getSelectedItem() != null && sizeCombo.getItemCount() != 0) {
                            sizeCombo.removeItem(stockSelected);
                            System.out.println("NOT GETTING HERE");
                            quantityCombo.setVisible(false);
                            quantityLabel.setVisible(false);
                            sellButton.setVisible(false);
                            if (sizeCombo.getItemCount() == 0) {
                                append = true;
                            }
                        }
                    }

                    currentCost = Queries.fetchPriceByTicker(selectedStock);

                    double realizedProfit = (currentCost - averagePurchasePrice) * quantity;

                    colorCombo.setText("Profit: $" + currentCost + " Realized Profit: $" + realizedProfit); // Display the price as a label text

                    double totalProfit = currentCost * quantity;

                    System.out.println("BEFORE SALE:" + cDashboard.getCustomer().getShares());

                    cDashboard.getCustomer().sellShare(selectedStock, quantity);
                    System.out.println(cDashboard.getCustomer().getShares());


                    cDashboard.getCustomer().setProfits(cDashboard.getCustomer().getProfits() + (realizedProfit));
                    cDashboard.getCustomer().setUnProfits(cDashboard.getCustomer().getUnProfits() - (realizedProfit));

                    if (cDashboard.getCustomer().getProfits()>= 10000 && (cDashboard.getCustomer().getOptions()).equalsIgnoreCase("false")) {
                        JOptionPane.showMessageDialog(null, "Congratulations! As you have made more than $10,000 in realized trading gains" +
                                ", you have qualified for a derivative trading account.");
                        cDashboard.getCustomer().setOptions("true");
                    }


                    // Update the quantityBox display
                    availableShares -= quantity;
                    quantityCombo.removeAllItems();
                    for (int i = 1; i <= availableShares; i++) {
                        quantityCombo.addItem(String.valueOf(i));
                    }

                    // Update the user's Account Balance and realized profits
                    balance += totalProfit;
                    accountBalance.setText("Current balance: $" + balance);
                    cDashboard.getCustomer().setBalance(balance);

                    cDashboard.getCustomer().updateDBCustomer();

                    // Display the result
                    resultLabel.setText(String.format("Sold %d shares for $%.2f per share. Realized profits: $%.2f", quantity, currentCost, realizedProfit));

                    if (append) {
                        resultLabel.setText(String.format("No shares left. Please exit.", quantity, totalProfit, realizedProfit));
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                sellButton.setVisible(false);
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