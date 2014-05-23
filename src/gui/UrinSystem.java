package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;


import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import proj.Classify;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JTree;

public class UrinSystem {

	private JFrame frame;
	static Classify classify;
	private static JFileChooser fileChooser = new JFileChooser();
	static BufferedReader trainReader = null, testReader = null;
	/**
	 * @wbp.nonvisual location=81,-1
	 */
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fileChooser.setMultiSelectionEnabled(true);
					UrinSystem window = new UrinSystem();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
				} catch (Exception e) {
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public UrinSystem() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			trainReader = new BufferedReader(new FileReader("train.data"));
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(frame, "train.data not found, select another train file");
		}
		try {
			testReader = new BufferedReader(new FileReader("test.data"));
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(frame, "test.data not found, select another test file");
		}
		try {
			classify = new Classify();
			classify.changeReaders(trainReader, testReader);
		} catch (Exception e1) {
		}
		try {
			classify.train();
		} catch (Exception e1) {
		}
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JLabel lblTemperaturecc = new JLabel("Temperature(35C-42C)");
		lblTemperaturecc.setBounds(10, 11, 150, 14);
		frame.getContentPane().add(lblTemperaturecc);
		
		textField = new JTextField();
		textField.setBounds(150, 8, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		final JCheckBox lblLumbar = new JCheckBox("Lumbar Pain?");
		lblLumbar.setBounds(6, 58, 104, 14);
		frame.getContentPane().add(lblLumbar);
		
		final JCheckBox lblUrinePushingyesno = new JCheckBox("Urine Pushing?");
		lblUrinePushingyesno.setBounds(6, 82, 115, 14);
		frame.getContentPane().add(lblUrinePushingyesno);
		
		final JCheckBox lblMicturitionPainsyesno = new JCheckBox("Micturition Pains?");
		lblMicturitionPainsyesno.setBounds(6, 107, 123, 14);
		frame.getContentPane().add(lblMicturitionPainsyesno);
		
		final JCheckBox lblBurningOfUrethra = new JCheckBox("Burning of urethra?");
		lblBurningOfUrethra.setBounds(6, 129, 139, 14);
		frame.getContentPane().add(lblBurningOfUrethra);
		
		final JCheckBox lblNausea = new JCheckBox("Nausea?");
		lblNausea.setBounds(6, 32, 97, 23);
		frame.getContentPane().add(lblNausea);
		
		
		final JLabel lblNewLabel = new JLabel("INFLAMMATION OF URINARY BLADDER:");
		lblNewLabel.setBounds(10, 184, 250, 28);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("NEPHRITIS OF RENAL PELVIS ORIGIN");
		lblNewLabel_1.setBounds(10, 223, 250, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		final JLabel lblInflammation = new JLabel("");
		lblInflammation.setBounds(250, 191, 46, 14);
		frame.getContentPane().add(lblInflammation);
		
		final JLabel lblNephritis = new JLabel("");
		lblNephritis.setBounds(250, 223, 46, 14);
		frame.getContentPane().add(lblNephritis);
		
		JButton btnChangeTestFile = new JButton("Change Test");
		btnChangeTestFile.setBounds(10, 150, 109, 23);
		frame.getContentPane().add(btnChangeTestFile);
		
		JButton btnChangeTrainFile = new JButton("Change Train");
		btnChangeTrainFile.setBounds(130, 150, 109, 23);
		frame.getContentPane().add(btnChangeTrainFile);
		
		
		btnChangeTestFile.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                    String command = event.getActionCommand();
                    if (command.equals("Change Test")) {
                            fileChooser.showDialog(frame,
                                            "Choose Test File");
                            File f = fileChooser.getSelectedFile();
                            try {
								BufferedReader newReaderFile = new BufferedReader(new FileReader(f));
								testReader = newReaderFile;
								classify.changeReaders(null, newReaderFile);
								classify.test();
							} catch (FileNotFoundException e) {
								JOptionPane.showMessageDialog(frame, "File not found!");
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
                       
                    }
            }
    });
		
		btnChangeTrainFile.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                    String command = event.getActionCommand();
                    if (command.equals("Change Train")) {
                            fileChooser.showDialog(frame,
                                            "Choose Train File");
                            File f = fileChooser.getSelectedFile();
                            
                            try {
								BufferedReader newReaderFile = new BufferedReader(new FileReader(f));
								trainReader = newReaderFile;
								classify.changeReaders(newReaderFile, null);
								classify.train();
							} catch (FileNotFoundException e) {
								JOptionPane.showMessageDialog(frame, "File not found!");
							} catch (Exception e) {
							}
                    }
            }
    });
		
		JButton btnTest = new JButton("TEST");
		
		btnTest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				float temperature = 0;
				try{
				 temperature = Float.parseFloat(textField.getText());
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(frame, "Please use '.' as separator");
					return;
				}
				if(temperature < 35.0 || temperature > 42.0)
				{
					JOptionPane.showMessageDialog(frame, "Temperature must be between 35ºC and 42ºC");
					return;
				}
				String nausea, lumbarPain, urinePushing, micturitionPains, burning;
				
				if(lblNausea.isSelected()) nausea = "yes";
				else nausea = "no";
				if(lblLumbar.isSelected()) lumbarPain = "yes";
				else lumbarPain = "no";
				if(lblUrinePushingyesno.isSelected()) urinePushing = "yes";
				else urinePushing = "no";
				if(lblMicturitionPainsyesno.isSelected()) micturitionPains = "yes";
				else micturitionPains = "no";
				if(lblBurningOfUrethra.isSelected()) burning = "yes";
				else burning = "no";
				
				try {
					String result = classify.runClassifier(temperature, nausea, lumbarPain, urinePushing, micturitionPains, burning);
					if(result.charAt(0) == 'y') lblInflammation.setText("YES"); 
					else lblInflammation.setText("NO");
					if(result.charAt(1) == 'y') lblNephritis.setText("YES");
					else lblNephritis.setText("NO");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		});
		btnTest.setBounds(150, 78, 89, 23);
		frame.getContentPane().add(btnTest);
		

		

		
		
		
		
		
	
	}
}
