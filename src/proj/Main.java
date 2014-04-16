package proj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

@SuppressWarnings("deprecation")
public class Main {

	public static BufferedReader readFile(String file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found !");
		}

		return reader;
	}

	public static Evaluation classification(Classifier model,
			Instances training, Instances testing) throws Exception {
		Evaluation ev = new Evaluation(training);
		model.buildClassifier(training);
		ev.evaluateModel(model, testing);
		System.out.println(ev.toSummaryString("\nResults\n======\n", false));
		return ev;
	}

	@SuppressWarnings("rawtypes")
	public static double accuracyCalc(FastVector arr) {
		double correct = 0;

		for (int i = 0; i < arr.size(); i++) {
			NominalPrediction prediction = (NominalPrediction) arr.get(i);

			if (prediction.predicted() == prediction.actual())
				correct++;
		}

		return 100 * correct / arr.size();
	}

	public static Instances[][] splits(Instances data, int splitsNo) {
		Instances split[][] = new Instances[2][splitsNo];

		for (int i = 0; i < splitsNo; i++) {
			split[0][i] = data.trainCV(splitsNo, i);
			split[1][i] = data.testCV(splitsNo, i);
		}

		return split;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {

		Scanner scan = new Scanner(System.in);
		int choice = 0;
		ArrayList values = new ArrayList(3);
		values.add("yes");
		values.add("no");

		Attribute tempAttr = new Attribute("temperature");
		Attribute nausea = new Attribute("nausea", values);
		Attribute lumbarPain = new Attribute("lumbarPain", values);
		Attribute urinePushing = new Attribute("urinePushing", values);
		Attribute micturitionPains = new Attribute("micturitionPains", values);
		Attribute burningOrItch = new Attribute("burningOrItch", values);
		Attribute inflammationOfBladder = new Attribute(
				"inflammationOfBladder", values);
		Attribute nephritisOfRenalPelvis = new Attribute(
				"nephritisOfRenalPelvis", values);

		ArrayList<Attribute> attrs = new ArrayList<Attribute>();
		attrs.add(tempAttr);
		attrs.add(nausea);
		attrs.add(lumbarPain);
		attrs.add(urinePushing);
		attrs.add(micturitionPains);
		attrs.add(burningOrItch);
		attrs.add(inflammationOfBladder);
		attrs.add(nephritisOfRenalPelvis);
		Instances newCases = new Instances("newCase", attrs, 100);
		for (int j = 0;; j++) {
			do {
				System.out.println("Which disease do you want to test?");
				System.out.println("1.Nephritis of renal pelvis origin");
				System.out.println("2.Inflammation of urinary bladder");
				System.out.println("0.Exit");
				try {
					choice = scan.nextInt();
				} catch (java.util.InputMismatchException ex) {
					choice = -1;
					scan.next();
				}
			} while (choice < 0 || choice > 2);

			if (choice == 0)
				break;

			BufferedReader sourceTrain = new BufferedReader(new FileReader("train.data")); 
			BufferedReader sourceTest = new BufferedReader(new FileReader("test.data")); 
			Instances train = new Instances(sourceTrain); //Train set
			Instances test = new Instances(sourceTest); //Test set
			
			train.setClassIndex(train.numAttributes() - choice);
			test.setClassIndex(train.numAttributes() - choice);
			
			Classifier tree = new J48();
			tree.buildClassifier(train);
			Evaluation eval = new Evaluation(train);
			eval.evaluateModel(tree, test);
			
			System.out.println(eval.toSummaryString("\nResults\n======\n", false));
			

			//double acc = accuracyCalc(predictions);
			float temperature = 0;

			Instance newCaseInstance = new DenseInstance(8);

			do {
				System.out.print("Temperature of patient(35C - 42C): ");
				try {
					temperature = scan.nextFloat();
				} catch (java.util.InputMismatchException ex) {
					temperature = 0;
					scan.next();
				}
			} while (temperature < 35 || temperature > 42);
			newCaseInstance.setValue(tempAttr, temperature);

			String answer = null;
			do {
				System.out.println();
				System.out.print("Nausea(yes/no)? ");
				answer = scan.next();
			} while (!answer.equals("yes") && !answer.equals("no"));
			newCaseInstance.setValue(nausea, answer);

			do {
				System.out.println();
				System.out.print("Lumbar Pain(yes/no)? ");
				answer = scan.next();
			} while (!answer.equals("yes") && !answer.equals("no"));
			newCaseInstance.setValue(lumbarPain, answer);

			do {
				System.out.println();
				System.out.print("Urine Pushing(yes/no)? ");
				answer = scan.next();
			} while (!answer.equals("yes") && !answer.equals("no"));
			newCaseInstance.setValue(urinePushing, answer);

			do {
				System.out.println();
				System.out.print("Micturition Pains(yes/no)? ");
				answer = scan.next();
			} while (!answer.equals("yes") && !answer.equals("no"));
			newCaseInstance.setValue(micturitionPains, answer);

			do {
				System.out.println();
				System.out
						.print("Burning of urethra, itch, swelling of urethra outlet(yes/no)? ");
				answer = scan.next();
			} while (!answer.equals("yes") && !answer.equals("no"));
			newCaseInstance.setValue(burningOrItch, answer);

			if (choice == 1) {

				do {
					System.out.println();
					System.out
							.print("Inflammation of urinary bladder(yes/no)? ");
					answer = scan.next();
				} while (!answer.equals("yes") && !answer.equals("no"));
				newCaseInstance.setValue(inflammationOfBladder, answer);
			} else {
				do {
					System.out.println();
					System.out
							.print("Nephritis of renal pelvis origin(yes/no)? ");
					answer = scan.next();
				} while (!answer.equals("yes") && !answer.equals("no"));
				newCaseInstance.setValue(nephritisOfRenalPelvis, answer);
			}

			newCases.add(newCaseInstance);
			newCases.setClassIndex(newCases.numAttributes() - choice);

			Instances labeled = new Instances(newCases);

			double label = tree.classifyInstance(newCases.instance(j));
			labeled.instance(j).setClassValue(label);
			System.out.print("THE PATIENT HAS ");
			if (labeled.instance(j).classValue() == 1.0)
				System.out.print("NO ");
			System.out.print("DISEASE ");
			System.out.println();
			System.out.println();

		}
		scan.close();
	}
}
