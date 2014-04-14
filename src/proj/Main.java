package proj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Main {
	
	static ArrayList<Data> data;
	
	public static void main(String[] args) throws Exception {
		
		data = new ArrayList<Data>();
		
		File file = new File("teste.data");
		Scanner s = new Scanner(file);
		String line;

		while (s.hasNextLine()) {
			ArrayList<Boolean> values = new ArrayList<Boolean>();
			line = s.nextLine();
			String[] tokens = line.split("\\s");
			for (int i = 1; i < tokens.length - 1; i++) {
				if (tokens[i].equals("yes")) {
					values.add(true);
				} else if (tokens[i].equals("no")){
					values.add(false);
				}
				else {
					System.out.println("Unknown");
				}
			}
			String number = tokens[0].replace(',', '.');
			Data d = new Data(Float.parseFloat(number), values);
			data.add(d);
		}
		s.close();

		/*
		 * try { while ((line = reader.readLine()) != null) {
		 * System.out.println(line); ArrayList<String> temp = new
		 * ArrayList<String>(); String[] tokens = line.split("\\s");
		 * temperatures.add(Integer.parseInt(tokens[0])); for (int i = 1; i <
		 * tokens.length - 1; i++) { temp.add(tokens[i]); } values.add(temp); }
		 * } catch (IOException e) { e.printStackTrace(); }
		 */

		System.out
				.println("Attributes:\na1 - Patient temperature\na2 - Occurrence of nausea\n"
						+ "a3 - Lumbar pain\na4 - Urine pushing\na5 - Micturition pains\n"
						+ "a6 - Burning of urethra, itch, swelling of urethra outlet\n"
						+ "d1 - decision: Inflammation of urinary bladder\n"
						+ "d2 - decision: Nephritis of renal pelvis origin");

		FastVector a2 = new FastVector(2);
		a2.addElement("yes");
		a2.addElement("no");
		FastVector a3 = new FastVector(2);
		a3.addElement("yes");
		a3.addElement("no");
		FastVector a4 = new FastVector(2);
		a4.addElement("yes");
		a4.addElement("no");
		FastVector a5 = new FastVector(2);
		a5.addElement("yes");
		a5.addElement("no");
		FastVector a6 = new FastVector(2);
		a6.addElement("yes");
		a6.addElement("no");
		FastVector d1 = new FastVector(2);
		d1.addElement("yes");
		d1.addElement("no");
		FastVector d2 = new FastVector(2);
		d2.addElement("yes");
		d2.addElement("no");
	}
}
