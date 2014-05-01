package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JSeparator;

public class UrinSystem {

	private JFrame frame;
	/**
	 * @wbp.nonvisual location=81,-1
	 */
	private final JLabel label = DefaultComponentFactory.getInstance().createTitle("New JGoodies title");
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UrinSystem window = new UrinSystem();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UrinSystem() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTemperaturecc = new JLabel("Temperature(35C-42C)");
		lblTemperaturecc.setBounds(10, 11, 115, 14);
		frame.getContentPane().add(lblTemperaturecc);
		
		textField = new JTextField();
		textField.setBounds(150, 8, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JCheckBox lblNewLabel_1 = new JCheckBox("Lumbar Pain?");
		lblNewLabel_1.setBounds(6, 58, 104, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JCheckBox lblUrinePushingyesno = new JCheckBox("Urine Pushing?");
		lblUrinePushingyesno.setBounds(6, 82, 115, 14);
		frame.getContentPane().add(lblUrinePushingyesno);
		
		JCheckBox lblMicturitionPainsyesno = new JCheckBox("Micturition Pains?");
		lblMicturitionPainsyesno.setBounds(6, 107, 123, 14);
		frame.getContentPane().add(lblMicturitionPainsyesno);
		
		JCheckBox lblBurningOfUrethra = new JCheckBox("Burning of urethra?");
		lblBurningOfUrethra.setBounds(6, 129, 139, 14);
		frame.getContentPane().add(lblBurningOfUrethra);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Nausea?");
		chckbxNewCheckBox.setBounds(6, 32, 97, 23);
		frame.getContentPane().add(chckbxNewCheckBox);
		
		JButton btnTest = new JButton("TEST");
		btnTest.setBounds(10, 176, 89, 23);
		frame.getContentPane().add(btnTest);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(127, 176, 68, 28);
		frame.getContentPane().add(lblNewLabel);
	}
}
