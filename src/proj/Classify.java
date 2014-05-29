package proj;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import weka.attributeSelection.GainRatioAttributeEval;
import weka.attributeSelection.InfoGainAttributeEval;
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
	static GainRatioAttributeEval gainRatioEval;
	static InfoGainAttributeEval infoGainEval;
	double maxRatio = -1.0;
	double maxGain = -1.0;
	long mSecs = 0;

	public Classify() {
		tree = new J48();
		gainRatioEval = new GainRatioAttributeEval();
		infoGainEval = new InfoGainAttributeEval();
	}

	public void test() throws Exception {
		test.setClassIndex(train.numAttributes() - 1);

		eval = new Evaluation(train);
		eval.evaluateModel(tree, test);

	}

	public void train() throws Exception {
		train.setClassIndex(train.numAttributes() - 1);
		long startTime = System.nanoTime();  
		tree.buildClassifier(train);
		long estimatedTime = System.nanoTime() - startTime;
		mSecs = estimatedTime / 1000000;
		gainRatioEval.buildEvaluator(train);
		infoGainEval.buildEvaluator(train);
		for (int i = 0; i < train.numAttributes(); i++) {
			double tmpRatio = gainRatioEval.evaluateAttribute(i);
			double tmpGain = infoGainEval.evaluateAttribute(i);
			if (tmpRatio > maxRatio && tmpRatio < 1.0)
				maxRatio = tmpRatio;
			if(tmpGain > maxGain)
				maxGain = tmpGain;

		}
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

	public double getMaxRatio() {
		return maxRatio;
	}

	public void resetMaxRatio() {
		maxRatio = -1.0;
	}
	
	public long getTrainTime() {
		return mSecs;
	}
	
	public double getMaxGain() {
		return maxGain;
	}
	
	public void resetMaxGain() {
		maxGain = -1.0;
	}
}
