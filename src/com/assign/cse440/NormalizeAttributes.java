package com.assign.cse440;

import java.util.ArrayList;
import java.util.Collections;

public class NormalizeAttributes {

	private ArrayList<ArrayList<Double>> colArray;
	private ArrayList<ArrayList<Double>> normalizedColArray =  new ArrayList<>();
	public ArrayList<ArrayList<Double>> getNormalizedColArray() {
		return normalizedColArray;
	}



	public NormalizeAttributes(ArrayList <ArrayList<Double>> colArray) {
		this.colArray=colArray;
		// TODO Auto-generated constructor stub
		normalizeAttributes();
	}
	
	
	
	private void normalizeAttributes(){

		int numberOfAttr = colArray.size()-1;
		int sizeOfEachAttribute = colArray.get(0).size();

		
		for (int i = 0; i<numberOfAttr ;i++){
			Double max = Collections.max(colArray.get(i));
			Double min = Collections.min(colArray.get(i));
			//System.out.println("Column = " + i +" max = "+ max+" min = "+min);
			
			if (max.equals(min) ) {
				if (max.equals(0.0)) {
					max=1.0;
					
				}
				else {
					min=0.0;
				}

			}
			
			double difference = max-min;
			
			ArrayList <Double> tempArray = new ArrayList<>();
			tempArray.clear();

			for (int j=0; j<sizeOfEachAttribute;j++){

				Double value = colArray.get(i).get(j); 
				//System.out.println("Value b = "+value);
				value = ((value-min))/(difference);
				tempArray.add(value);
				//colArray.get(i).add(j, value);
				//System.out.println("Value a = "+value);
				
			}
			normalizedColArray.add(tempArray);
		}
		ArrayList <Double> tempArray = new ArrayList<>();
		tempArray.clear();
		for (int i=0; i<sizeOfEachAttribute;i++){

			Double value = colArray.get(numberOfAttr).get(i); 
			tempArray.add(value);
		}
		normalizedColArray.add(tempArray);
	}
}
