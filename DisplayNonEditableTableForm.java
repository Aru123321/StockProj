import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DisplayNonEditableTableForm extends JFrame {

    public DisplayNonEditableTableForm() {
        setTitle("Display Non-Editable Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel panel = new JPanel();

        // Creating an instance of SharedEditableTable to access getNonEditableTable()
        SharedEditableTable sharedEditableTable = new SharedEditableTable();

        // Retrieve the non-editable table
        JTable nonEditableTable = SharedEditableTable.getNonEditableTable();

        JScrollPane scrollPane = new JScrollPane(nonEditableTable);
        panel.add(scrollPane);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DisplayNonEditableTableForm::new);
    }
}
