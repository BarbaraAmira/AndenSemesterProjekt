package GUI;

import java.awt.BorderLayout; 
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.CageCtr;
import db.DataAccessException;
import model.Cage;
import model.FoodProduct;
import model.NonFoodProduct;

public class CageViewFrame extends JFrame {
    private Cage cageId;
    private JTextArea productArea;
    private CageCtr cageCtr;

    public CageViewFrame(Cage cage) throws DataAccessException {
        this.cageId = cage; 
        this.cageCtr = new CageCtr();
        

        setTitle("Cage " + cageId + " Contents");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Top label
        JLabel label = new JLabel("Viewing Cage ID: " + cageId, SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // --- Product list
        productArea = new JTextArea();
        productArea.setEditable(false);
        add(new JScrollPane(productArea), BorderLayout.CENTER);
        
        loadProducts();
        
        JPanel buttonPanel = new JPanel();

        JButton addFoodBtn = new JButton("Add Food Product");
        addFoodBtn.addActionListener(e -> openAddFoodProductDialog());

        JButton addNonFoodBtn = new JButton("Add Non-Food Product");
        addNonFoodBtn.addActionListener(e -> openAddNonFoodProductDialog());

        buttonPanel.add(addFoodBtn);
        buttonPanel.add(addNonFoodBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    
    //Metode til at kunne liste alle produkter i listen
    private void loadProducts() {
        try {
            productArea.setText("");

            for (FoodProduct fp : cageId.getFoodProducts()) {
                productArea.append("Food: " + fp.getName() + ", Quantity: " + fp.getQuantity() + ", Arrival Date: " + fp.getArrivalDate() + "\n");
            }

            for (NonFoodProduct nfp : cageId.getNonFoodProducts()) {
                productArea.append("Non-Food: " + nfp.getName() + ", Quantity: " + nfp.getQuantity() + ", Category: " + nfp.getCategory() + "\n");
            }

        } catch (Exception e) {
            productArea.setText("Error loading products: " + e.getMessage());
        }
    }
    /*Metode der åbner en dialog boks hvor man kan tilføje et produkt. Der er 4 tekst bokse
    	Den første er for at søge et id
    	Den næste er navne feltet som bliver automatisk fyldt ud, ud fra id'et
    	Den trejde er mængde som brugeren inputter selv
    	Den sidste er Arrival date som brugeren igen inputter selv
    
    */
    private void openAddFoodProductDialog() {
        JTextField productIdField = new JTextField();
        JTextField nameField = new JTextField();
        nameField.setEditable(false);

        JTextField quantityField = new JTextField();
        JTextField dateField = new JTextField();

        productIdField.addActionListener(e -> {
            try {
                int pid = Integer.parseInt(productIdField.getText());
                String name = cageCtr.getProductNameById(pid);
                nameField.setText(name);
            } catch (Exception ex) {
                nameField.setText("Invalid ID");
            }
        });

        Object[] fields = {
            "Product ID:", productIdField,
            "Name:", nameField,
            "Quantity:", quantityField,
            "Arrival Date yyyymmdd:", dateField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Add Food Product", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
            	int productId = Integer.parseInt(productIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                int arrivalDate = Integer.parseInt(dateField.getText());

                FoodProduct fp = new FoodProduct(name, productId, quantity, arrivalDate);
                fp.setName(name);
                fp.setProductId(productId);
                fp.setQuantity(quantity);
                fp.setArrivalDate(arrivalDate);

                cageCtr.addFoodProduct(cageId, fp);
                loadProducts();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
    
    // Samme metode som før bare med NonFood, arrivalDate er erstattet med category
    private void openAddNonFoodProductDialog() {
        JTextField productIdField = new JTextField();
        JTextField nameField = new JTextField();
        nameField.setEditable(false);

        JTextField quantityField = new JTextField();
        JTextField categoryField = new JTextField();

        productIdField.addActionListener(e -> {
            try {
                int pid = Integer.parseInt(productIdField.getText());
                String name = cageCtr.getProductNameById(pid);
                nameField.setText(name);
            } catch (Exception ex) {
                nameField.setText("Invalid ID");
            }
        });

        Object[] fields = {
            "Product ID:", productIdField,
            "Name:", nameField,
            "Quantity:", quantityField,
            "Category:", categoryField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Add Non-Food Product", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
            	String name = nameField.getText();
            	int productId = Integer.parseInt(productIdField.getText());      
                int quantity = Integer.parseInt(quantityField.getText());
                String category = categoryField.getText();

                NonFoodProduct nfp = new NonFoodProduct(name, productId , quantity, category);
                nfp.setName(name);
                nfp.setProductId(productId);
                nfp.setQuantity(quantity);
                nfp.setCategory(category);

                cageCtr.addNonFoodProduct(cageId, nfp);
                loadProducts();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
    
}