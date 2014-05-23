package proj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
public class Classify {

	static Classifier tree;
	static Instances test,train;
	
	public Classify()
	{
		tree = new J48();
	}
	public void test() throws Exception 
	{
		test.setClassIndex(train.numAttributes() - 1);
		
		
		for(int i = 0; i < test.numInstances();i++)
		{
			double predicted = tree.classifyInstance(test.instance(i));
			System.out.print("Temperature: " + test.instance(i).value(0));
			System.out.print(", actual: " + test.classAttribute().value((int) test.instance(i).classValue()));
			System.out.println(", predicted: " + test.classAttribute().value((int) predicted));
		}
	}
	
	public void train() throws Exception
	{
		train.setClassIndex(train.numAttributes() - 1);
		tree.buildClassifier(train);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String runClassifier(float temperature, String nauseaAns, String lumbarPainAns, String urinePushingAns, String micturitionPainsAns, String burningAns) throws Exception {


		ArrayList values = new ArrayList(2);
		ArrayList fourClassifierVec = new ArrayList(4);
		values.add("yes");
		values.add("no");
		fourClassifierVec.add("yy");
		fourClassifierVec.add("nn");
		fourClassifierVec.add("yn");
		fourClassifierVec.add("ny");

		Attribute tempAttr = new Attribute("temperature");
		Attribute nausea = new Attribute("nausea", values);
		Attribute lumbarPain = new Attribute("lumbarPain", values);
		Attribute urinePushing = new Attribute("urinePushing", values);
		Attribute micturitionPains = new Attribute("micturitionPains", values);
		Attribute burningOrItch = new Attribute("burningOrItch", values);
		Attribute dualDisease = new Attribute("inflammationOfBladderOrNephritisOfRenalPelvis", fourClassifierVec);

		ArrayList<Attribute> attrs = new ArrayList<Attribute>();
		attrs.add(tempAttr);
		attrs.add(nausea);
		attrs.add(lumbarPain);
		attrs.add(urinePushing);
		attrs.add(micturitionPains);
		attrs.add(burningOrItch);
		attrs.add(dualDisease);
		
		

		//double acc = accuracyCalc(predictions);
		Instances newCases = new Instances("newCase", attrs, 1);

			

			Instance newCaseInstance = new DenseInstance(7);
			newCaseInstance.setValue(tempAttr, temperature);
			newCaseInstance.setValue(nausea, nauseaAns);
			newCaseInstance.setValue(lumbarPain, lumbarPainAns);
			newCaseInstance.setValue(urinePushing, urinePushingAns);
			newCaseInstance.setValue(micturitionPains, micturitionPainsAns);
			newCaseInstance.setValue(burningOrItch, burningAns);

			newCases.add(newCaseInstance);
			newCases.setClassIndex(newCases.numAttributes() - 1);

			Instances labeled = new Instances(newCases);

			double label = tree.classifyInstance(newCases.instance(0));
			labeled.instance(0).setClassValue(label);
			
			return newCases.classAttribute().value((int) label);

		
	}
	
	public void changeReaders(BufferedReader trainReader, BufferedReader testReader) throws IOException
	{
		if(trainReader == null) test = new Instances(testReader); 
		else train = new Instances(trainReader);
	}

}
