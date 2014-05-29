package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import proj.Classify;
import weka.classifiers.trees.J48;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;

import javax.swing.JSeparator;

public class UrinSystem {

	private JFrame frame;
	private boolean trainFileExists = true;
	static Classify classify;
	private static JFileChooser fileChooser = new JFileChooser();
	static BufferedReader trainReader = null, testReader = null;
	javax.swing.JFrame treeFrame;
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

	public void viewTree() {
		if (trainFileExists) {
			treeFrame = new javax.swing.JFrame("C4.5 Decision Tree");
			treeFrame.setSize(500, 400);
			treeFrame.getContentPane().setLayout(new BorderLayout());
			TreeVisualizer tv = null;
			try {
				tv = new TreeVisualizer(null,
						((J48) classify.getClassifier()).graph(),
						new PlaceNode2());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			treeFrame.getContentPane().add(tv, BorderLayout.CENTER);
			treeFrame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {

					treeFrame.dispose();
				}
			});

			treeFrame.setVisible(true);
			tv.fitToScreen();
		}
	}

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public UrinSystem() throws Exception {
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

		final JLabel testedCases = new JLabel("");
		testedCases.setBounds(352, 11, 46, 14);
		frame.getContentPane().add(testedCases);

		JLabel lblCorrectlyClassified = new JLabel("Correctly Predicted(%)");
		lblCorrectlyClassified.setBounds(266, 55, 130, 14);
		frame.getContentPane().add(lblCorrectlyClassified);

		final JLabel correctlyClassified = new JLabel("");
		correctlyClassified.setBounds(395, 55, 50, 14);
		frame.getContentPane().add(correctlyClassified);

		JLabel lblCorrectlyPredicted = new JLabel("Correctly Predicted");
		lblCorrectlyPredicted.setBounds(266, 33, 113, 14);
		frame.getContentPane().add(lblCorrectlyPredicted);

		final JLabel correctlyPredicted = new JLabel("");
		correctlyPredicted.setBounds(382, 33, 46, 14);
		frame.getContentPane().add(correctlyPredicted);

		JLabel lblRootAttribute = new JLabel("Root Gain Ratio");
		lblRootAttribute.setBounds(266, 77, 86, 14);
		frame.getContentPane().add(lblRootAttribute);

		final JLabel rootLabel = new JLabel("");
		rootLabel.setBounds(360, 77, 60, 14);
		frame.getContentPane().add(rootLabel);
		
		JLabel lblRootInfoGain = new JLabel("Root Info Gain");
		lblRootInfoGain.setBounds(266, 99, 86, 14);
		frame.getContentPane().add(lblRootInfoGain);
		
		final JLabel rootInfo = new JLabel("");
		rootInfo.setBounds(360, 99, 60, 14);
		frame.getContentPane().add(rootInfo);
		
		JLabel lblTrainingTreeTime = new JLabel("Tree Training Time");
		lblTrainingTreeTime.setBounds(266, 121, 110, 14);
		frame.getContentPane().add(lblTrainingTreeTime);
		
		final JLabel time = new JLabel("");
		time.setBounds(382, 121, 50, 14);
		frame.getContentPane().add(time);

		try {
			trainReader = new BufferedReader(new FileReader("train.data"));

		} catch (FileNotFoundException e1) {
			trainFileExists = false;
			JOptionPane.showMessageDialog(frame,
					"train.data not found, select another train file");
		}
		try {
			testReader = new BufferedReader(new FileReader("test.data"));
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(frame,
					"test.data not found, select another test file");
		}
		try {
			classify = new Classify();
			classify.changeReaders(trainReader, testReader);
		} catch (Exception e1) {
		}
		try {
			classify.train();
			classify.test();
			testedCases.setText(Double.toString(classify.getEval().numInstances()));
			correctlyClassified.setText(Double.toString(classify.getEval().pctCorrect()) + "%");
			correctlyPredicted.setText(Double.toString(classify.getEval().correct()));
			double maxRatio = new BigDecimal(Double.toString(classify.getMaxRatio())).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			rootLabel.setText(Double.toString(maxRatio) + " bits");
			double maxGain = new BigDecimal(Double.toString(classify.getMaxGain())).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			rootInfo.setText(Double.toString(maxGain) + " bits");
			time.setText(Long.toString(classify.getTrainTime()) + " ms");
		} catch (Exception e1) {
		}
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

		final JCheckBox lblMicturitionPainsyesno = new JCheckBox(
				"Micturition Pains?");
		lblMicturitionPainsyesno.setBounds(6, 107, 123, 14);
		frame.getContentPane().add(lblMicturitionPainsyesno);

		final JCheckBox lblBurningOfUrethra = new JCheckBox(
				"Burning of urethra?");
		lblBurningOfUrethra.setBounds(6, 129, 139, 14);
		frame.getContentPane().add(lblBurningOfUrethra);

		final JCheckBox lblNausea = new JCheckBox("Nausea?");
		lblNausea.setBounds(6, 32, 97, 23);
		frame.getContentPane().add(lblNausea);

		final JLabel lblNewLabel = new JLabel(
				"INFLAMMATION OF URINARY BLADDER:");
		lblNewLabel.setBounds(10, 184, 250, 28);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("NEPHRITIS OF RENAL PELVIS ORIGIN:");
		lblNewLabel_1.setBounds(10, 223, 250, 14);
		frame.getContentPane().add(lblNewLabel_1);

		final JLabel lblInflammation = new JLabel("");
		lblInflammation.setBounds(230, 191, 46, 14);
		frame.getContentPane().add(lblInflammation);

		final JLabel lblNephritis = new JLabel("");
		lblNephritis.setBounds(230, 223, 46, 14);
		frame.getContentPane().add(lblNephritis);

		JButton btnChangeTestFile = new JButton("Change Test");
		btnChangeTestFile.setBounds(10, 150, 109, 23);
		frame.getContentPane().add(btnChangeTestFile);

		JButton btnChangeTrainFile = new JButton("Change Train");
		btnChangeTrainFile.setBounds(130, 150, 109, 23);
		frame.getContentPane().add(btnChangeTrainFile);

		JLabel lblTestedCases = new JLabel("Tested Cases");
		lblTestedCases.setBounds(266, 11, 86, 14);
		frame.getContentPane().add(lblTestedCases);
		
		

		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		separator.setBounds(260, 0, 100, 600);
		frame.getContentPane().add(separator);

		viewTree();
		btnChangeTestFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();
				if (command.equals("Change Test")) {
					fileChooser.showDialog(frame, "Choose Test File");
					File f = fileChooser.getSelectedFile();
					try {
						BufferedReader newReaderFile = new BufferedReader(
								new FileReader(f));
						testReader = newReaderFile;
						classify.changeReaders(null, newReaderFile);
						classify.test();
						testedCases.setText(Double.toString(classify.getEval().numInstances()));
						correctlyClassified.setText(Double.toString(classify.getEval().pctCorrect()) + "%");
						correctlyPredicted.setText(Double.toString(classify.getEval().correct()));
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(frame, "File not found!");
					} catch (Exception e) {
					}

				}
			}
		});

		btnChangeTrainFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();
				if (command.equals("Change Train")) {
					fileChooser.showDialog(frame, "Choose Train File");
					File f = fileChooser.getSelectedFile();

					try {
						BufferedReader newReaderFile = new BufferedReader(new FileReader(f));
						trainReader = newReaderFile;
						classify.changeReaders(newReaderFile, null);
						treeFrame.dispatchEvent(new WindowEvent(treeFrame,WindowEvent.WINDOW_CLOSING));
						classify.resetMaxRatio();
						classify.resetMaxGain();
						time.setText(Long.toString(classify.getTrainTime()) + " ms");
						classify.train();
						classify.test();
						double maxRatio = new BigDecimal(Double.toString(classify.getMaxRatio())).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
						rootLabel.setText(Double.toString(maxRatio) + " bits");
						double maxGain = new BigDecimal(Double.toString(classify.getMaxGain())).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
						rootInfo.setText(Double.toString(maxGain) + " bits");
						trainFileExists = true;
						viewTree();
					} catch (FileNotFoundException e) {
						trainFileExists = false;
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
				try {
					temperature = Float.parseFloat(textField.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(frame,
							"Please use '.' as separator");
					return;
				}
				if (temperature < 35.0 || temperature > 42.0) {
					JOptionPane.showMessageDialog(frame,
							"Temperature must be between 35ºC and 42ºC");
					return;
				}
				String nausea, lumbarPain, urinePushing, micturitionPains, burning;

				if (lblNausea.isSelected())
					nausea = "yes";
				else
					nausea = "no";
				if (lblLumbar.isSelected())
					lumbarPain = "yes";
				else
					lumbarPain = "no";
				if (lblUrinePushingyesno.isSelected())
					urinePushing = "yes";
				else
					urinePushing = "no";
				if (lblMicturitionPainsyesno.isSelected())
					micturitionPains = "yes";
				else
					micturitionPains = "no";
				if (lblBurningOfUrethra.isSelected())
					burning = "yes";
				else
					burning = "no";

				try {
					String result = classify
							.runClassifier(temperature, nausea, lumbarPain,
									urinePushing, micturitionPains, burning);
					if (result.charAt(0) == 'y')
						lblInflammation.setText("YES");
					else
						lblInflammation.setText("NO");
					if (result.charAt(1) == 'y')
						lblNephritis.setText("YES");
					else
						lblNephritis.setText("NO");

				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, "Bad File Format");
				}

			}

		});
		btnTest.setBounds(150, 78, 89, 23);
		frame.getContentPane().add(btnTest);
		

		
	
		


	}
}
