package proj;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import weka.attributeSelection.GainRatioAttributeEval;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class Classify {

	static Classifier tree;
	static Instances test, train;
	static Evaluation eval;
	static GainRatioAttributeEval infoGainEval;
	double max = -1.0;

	public Classify() {
		tree = new J48();
		infoGainEval = new GainRatioAttributeEval();
	}

	public void test() throws Exception {
		test.setClassIndex(train.numAttributes() - 1);

		eval = new Evaluation(train);
		eval.evaluateModel(tree, test);

	}

	public void train() throws Exception {
		train.setClassIndex(train.numAttributes() - 1);
		tree.buildClassifier(train);
		infoGainEval.buildEvaluator(train);
		System.out.println("ATTRIBUTES = " + train.numAttributes());
		for (int i = 0; i < train.numAttributes(); i++) {
			Attribute attr = train.attribute(i);
			double tmp = infoGainEval.evaluateAttribute(i);
			System.out.println("VAL = " + tmp + " = " + attr.name());
			if (tmp > max && tmp < 1.0)
				max = tmp;

		}
		System.out.println("MAX =  " + max);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String runClassifier(float temperature, String nauseaAns,
			String lumbarPainAns, String urinePushingAns,
			String micturitionPainsAns, String burningAns) throws Exception {

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
		Attribute dualDisease = new Attribute(
				"inflammationOfBladderOrNephritisOfRenalPelvis",
				fourClassifierVec);

		ArrayList<Attribute> attrs = new ArrayList<Attribute>();
		attrs.add(tempAttr);
		attrs.add(nausea);
		attrs.add(lumbarPain);
		attrs.add(urinePushing);
		attrs.add(micturitionPains);
		attrs.add(burningOrItch);
		attrs.add(dualDisease);

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

	public void changeReaders(BufferedReader trainReader,
			BufferedReader testReader) throws IOException {
		if (trainReader != null && testReader != null) {
			train = new Instances(trainReader);
			test = new Instances(testReader);
		} else {
			if (trainReader == null)
				test = new Instances(testReader);

			else
				train = new Instances(trainReader);
		}
	}

	public Classifier getClassifier() {
		return tree;
	}

	public Evaluation getEval() {
		return eval;
	}

	public double getMaxGain() {
		return max;
	}

	public void resetMaxGain() {
		max = -1.0;
	}
}
