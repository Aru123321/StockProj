import org.w3c.dom.ls.LSOutput;

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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class BuyTest
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {

            public void run()
            {
                TShirtOrderFrame tsf = null;
                try {
                    tsf = new TShirtOrderFrame();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                tsf.setTitle("Transaction");
                tsf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                tsf.setVisible(true);

            }



        });
    }
}

class TShirtOrderFrame extends JFrame
{
    public TShirtOrderFrame() throws SQLException {
        TShirtOrderPanel tsp = new TShirtOrderPanel();
        add(tsp);
        pack();
    }
}

class TShirtOrderPanel extends JPanel implements ActionListener {
    // Arrays used for the values in the combo box
    List<String> stocksfinal = new ArrayList<>();

    private String[] colors = { "Black", "White", "Red", "Pink", "Green",
            "Yellow" };
    private String[] quantities = { "1", "2", "3","4","5","6","7","8", "9","10"};
    // Save the IWriteCodeStamp.jpg in the root
    // folder of your project
    private ImageIcon shirtImage = new ImageIcon("IWriteCodeStamp.jpg");
    // Declare all the controls that will be on the order form.
    private JLabel imageLabel;
    private JRadioButton mensRadioButton;
    private JRadioButton womensRadioButton;
    private JComboBox sizeCombo;
    private JLabel colorCombo;
    private JLabel quantityLabel;
    private JLabel labelTitle;
    private JComboBox quantityCombo;
    private JButton calcButton;
    private JLabel resultLabel;
    private JLabel stockLabel;

    public TShirtOrderPanel() throws SQLException {
        Database.getInstance();

        ResultSet stocks = Queries.fetchAllStocks();
        while (stocks.next()){
            String ticker = stocks.getString("Ticker");
            stocksfinal.add(ticker);
         }

        String[] stocksNew = stocksfinal.toArray(new String[0]);

        // Instantiate all the controls
        imageLabel = new JLabel(shirtImage);
        sizeCombo = new JComboBox(stocksNew);
        colorCombo = new JLabel();
        quantityLabel = new JLabel("Quantity");
        labelTitle = new JLabel("Stock Purchase");
        quantityCombo = new JComboBox(quantities);
        stockLabel = new JLabel("Stock:");
        calcButton = new JButton("Total");

        resultLabel = new JLabel("");

        // Pretty up the labels
        labelTitle.setFont(new Font("Impact", Font.BOLD, 28));
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setFont(new Font("Impact", Font.PLAIN, 16));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the radio buttons to a button group so only one
        // can be selected at a timemensRadioButton.setSelected(true);
        ButtonGroup dept = new ButtonGroup();
        dept.add(mensRadioButton);
        dept.add(womensRadioButton);

        // Arrange the controls onto the screen
        this.setLayout(new BorderLayout());
        this.add(labelTitle, BorderLayout.NORTH);
        this.add(imageLabel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        this.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JPanel deptPanel = new JPanel();

        JPanel comboPanel = new JPanel();
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
        add(resultLabel, BorderLayout.SOUTH);

        // Make button responsive to clicks
        calcButton.addActionListener(this);

        sizeCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected stock from sizeCombo
                String selectedStock = sizeCombo.getSelectedItem().toString();
                System.out.println(selectedStock);

                // Here, you'd need to fetch the price of the selected stock
                // You can use your Database/Queries to fetch the price based on the selected stock
                // For demonstration purposes, let's assume a fixed price based on the selected stock
                double price = Queries.fetchPriceByTicker(selectedStock); // Replace this with your logic

                int quantity = Integer.parseInt(quantityCombo.getSelectedItem()
                        .toString());
                price *= quantity;

                // Display the price in the colorCombo JLabel
                colorCombo.setText("Price: $" + price); // Display the price as a label text
            }
        });

        quantityCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected stock from sizeCombo
                String selectedStock = sizeCombo.getSelectedItem().toString();
                System.out.println(selectedStock);

                // Here, you'd need to fetch the price of the selected stock
                // You can use your Database/Queries to fetch the price based on the selected stock
                // For demonstration purposes, let's assume a fixed price based on the selected stock
                double price = Queries.fetchPriceByTicker(selectedStock); // Replace this with your logic

                int quantity = Integer.parseInt(quantityCombo.getSelectedItem()
                        .toString());
                price *= quantity;

                // Display the price in the colorCombo JLabel
                colorCombo.setText("Price: $" + price); // Display the price as a label text
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


        // Use decision statements to calculate the total for the
        // Women's Small: 12.00
        // Women's Medium: 13.00
        // Women's Large: 14.00
        // Women's XLarge: 15.00
        // For men's sizes add 1.00

        // For baseball sleeves add 2.00
        // For the color red add 1.00
        // If 5 shirts are ordered a 5% discount is applied
        // if 10 shirts are ordered a 10% discount is applied
        // If 20 shirts are ordered a 15% discount is applied
        // If 100 shirts are ordered a 25% discount is applied

        // Create a string that describes the details of the order including
        // size, color, quantity, department, if baseball sleeves are included
        // and order the total.

        String orderDetails = "5 Men's Shirt(s) in Black with baseball sleeves. \n Total: 2,000$";
        // Create a string with all theorder details
        resultLabel.setText(orderDetails);

    }



}