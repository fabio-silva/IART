package proj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Main {

	static ArrayList<Data> data;

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

		return ev;
	}

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

	public static void main(String[] args) throws Exception {

		BufferedReader file = readFile("teste.data");
		Instances data = new Instances(file);
		data.setClassIndex(data.numAttributes() - 1);

		Instances[][] split = splits(data, 3);

		Instances[] training = split[0];
		Instances[] testing = split[1];

		FastVector predictions = new FastVector();
		Classifier classifier = new J48(); //C4.5
		for (int i = 0; i < training.length; i++) {
			Evaluation valid = classification(classifier, training[i],
					testing[i]);
			predictions.appendElements(valid.predictions());

			System.out.println(classifier.toString());
		}

		double acc = accuracyCalc(predictions);
		
		System.out.println("Accuracy of " + classifier.getClass().getSimpleName() + ": " + String.format("%.2f%%", acc) + "\n----------------");
	}
}
