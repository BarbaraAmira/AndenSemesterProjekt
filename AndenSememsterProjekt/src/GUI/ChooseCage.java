package GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import controller.CageCtr;
import db.DataAccessException;
import model.Cage;

public class ChooseCage {
	private JFrame frame;
	private JTextField textFieldForId;
	JLabel infolbl;
	private CageCtr cageCtr;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseCage window = new ChooseCage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	**/
	
	 
	public ChooseCage() throws DataAccessException {
		try {
			cageCtr = new CageCtr();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws DataAccessException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JButton confirmBtn = new JButton("Confirm");
		frame.getContentPane().add(confirmBtn, BorderLayout.SOUTH);
		
		//Knap der vil aceptere et valid bur som input og dernæst åbne det næste frame. Det bur som bliver inputtet vil så være buret der vil blive interageret med
		confirmBtn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String enteredId = textFieldForId.getText();
		        if (isValidId(enteredId)) {
		            try {
		                int cageId = Integer.parseInt(enteredId);
		                CageCtr cageCtr = new CageCtr();
		                Cage cage = cageCtr.findCageById(cageId); 

		                if (cage != null) {
		                    CageViewFrame cageViewFrame = new CageViewFrame(cage);
		                    frame.setVisible(false);
		                    cageViewFrame.setVisible(true);
		                } else {
		                    infolbl.setText("Cage not found");
		                }
		            } catch (NumberFormatException ex) {
		                infolbl.setText("ID must be a number");
		            } catch (DataAccessException ex) {
		                infolbl.setText("Database error: " + ex.getMessage());
		            }
		        } else {
		            infolbl.setText("Invalid ID");
		        }
		    }
			
		    //Metode til at validere et cageId
			private boolean isValidId(String cageId) {
				try {
					List<String> validIds = cageCtr.getAllCageIds();
					return validIds.contains(cageId);
				} catch (DataAccessException e) {
					infolbl.setText("Database error: " + e.getMessage());
					return false;
				}
			}
		});
		
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel cagelbl = new JLabel("Insert cage id:");
		panel.add(cagelbl);
		
		textFieldForId = new JTextField();
		panel.add(textFieldForId);
		textFieldForId.setColumns(10);
		
	
		JLabel infolbl = new JLabel(" ");
		infolbl.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(infolbl, BorderLayout.CENTER);
	}

}
