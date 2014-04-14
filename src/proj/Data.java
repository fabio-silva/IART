package proj;

import java.util.ArrayList;

public class Data {
	
	float temperature;
	ArrayList<Boolean> values;
	
	public Data(float temp, ArrayList<Boolean> val) {
		temperature = temp;
		values = val;
	}

	public float getTemperature() {
		return temperature;
	}

	public ArrayList<Boolean> getValues() {
		return values;
	}
}
