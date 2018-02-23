package com.assign.cse440;

import java.util.*;

public class classify {

	public classify() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args){
		
		String training_file = args[0];
		String test_file= args[1];
		String Algorithm = args[2];
		int k = 1;
		String x= args[3];
		Double y = Double.parseDouble(x);
		k = y.intValue();
		
		InputDataProcessor train = new InputDataProcessor(training_file);
		
		ArrayList<ArrayList<Double>> colArray = train.getColArray();
 
		NormalizeAttributes nrm = new NormalizeAttributes(colArray);

		ArrayList<ArrayList<Double>> normalizedTariningData = new ArrayList<>();
		normalizedTariningData.addAll(nrm.getNormalizedColArray());
		/*for (int i = 0; i<normalizedTariningData.size();i++){
			for (int j=0; j<normalizedTariningData.get(0).size();j++){
				System.out.println("Value = "+colArray.get(i).get(j));
				System.out.println("new Value = "+normalizedTariningData.get(i).get(j));
				
			}
			System.out.println("");
		}
		
		*/
		
		
		
		
		InputDataProcessor test = new InputDataProcessor(test_file);
		
		colArray = new ArrayList<>();
		colArray.clear();
		colArray.addAll(test.getColArray());
 
		NormalizeAttributes testAtr = new NormalizeAttributes(colArray);
		ArrayList<ArrayList<Double>> normalizedTestData = new ArrayList<>();
		normalizedTestData.addAll(testAtr.getNormalizedColArray());
		/*for (int i = 0; i<normalizedTestData.size();i++){
			for (int j=0; j<normalizedTestData.get(0).size();j++){
				//System.out.println("train Value = "+colArray.get(i).get(j));
				//System.out.println("new test Value = "+normalizedTestData.get(i).get(j));
				
			}
			System.out.println("");
		}
		
		*/
		
		DistanceGenerator dg = new DistanceGenerator(normalizedTariningData,normalizedTestData,k);
		
		
		
		
		
		
	}

}
