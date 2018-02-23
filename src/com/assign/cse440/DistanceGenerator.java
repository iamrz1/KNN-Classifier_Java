package com.assign.cse440;

import java.util.ArrayList;

public class DistanceGenerator {
	
	private ArrayList<ArrayList<Double>> trainArrayList = new ArrayList<>();
	private ArrayList<ArrayList<Double>> testArrayList = new ArrayList<>();
	private Double accuracy =0.0;
	private int count=0;
	
	
	private int k;
	private int numberOfAttributes, numberOfTestData, numberOfTrainData, classLabelIndex;

	public DistanceGenerator(ArrayList<ArrayList<Double>> trainingArray,ArrayList<ArrayList<Double>> testArray,int k) {
		// TODO Auto-generated constructor stub
		this.trainArrayList=trainingArray;
		this.testArrayList = testArray;
		this.k = k;
		this.numberOfTrainData = trainingArray.get(0).size();
		this.numberOfTestData = testArray.get(0).size();
		this.numberOfAttributes = trainingArray.size()-1;
		this.classLabelIndex = numberOfAttributes;
		//System.out.println(" trainingArray = "+trainingArray.size() +" testArray = "+testArray.size());
		run();
		
		
	}
	
	
	private void run(){
		//for each test Data row
		for (int x=0; x<numberOfTestData;x++){
			Double distance= -1.0;
			ArrayList<Integer> trainIndexes = new ArrayList<>();
			ArrayList<Double> distanceArray = new ArrayList<>();
			ArrayList<DistanceProcessor> DistanceClassArray = new ArrayList<>();
			//go through each row of trainData
			for (int y=0;y< numberOfTrainData; y++){
				//System.out.println(" x = "+x +" y = "+y);
				//summize distance for each column
				distance =0.0;
				for (int z=0;z<numberOfAttributes;z++){
					Double testValue = testArrayList.get(z).get(x);
					Double trainValue = trainArrayList.get(z).get(y);
					distance += Math.pow(testValue - trainValue, 2);
					//System.out.println(" z = "+z);
					//System.out.println(" testValue = "+testValue +" trainValue = "+trainValue);
					
					
				}
				distance = Math.sqrt( distance );
				//System.out.println(" test index = "+x +" trainindexes = "+y +" distance = "+ distance);
				trainIndexes.add(y);
				distanceArray.add(distance);

			}
			DistanceProcessor d = new DistanceProcessor (x,trainIndexes,distanceArray,k,trainArrayList,testArrayList);
			//
			
			if (d.getAccuracy()>0){
				accuracy= accuracy+d.getAccuracy();
				count++;
			}
			
			
		}
		
		System.out.println("Number of Correct guess = " + count);
		System.out.println("Accuracy w/o tiebreakers = "+(double)count/(double)numberOfTestData*(double)100+" %");
		System.out.println("Accuracy w/ tiebreakers = "+accuracy/(double)numberOfTestData*(double)100+" %");
		System.out.println("Number of Correct guess = " + count);
		System.out.printf("Classification accuracy w/o tiebreakers = = %6.4f\n",((double)count/(double)numberOfTestData*(double)100) );
		//System.out.println("Accuracy w/ tiebreakers = "+accuracy/(double)numberOfTestData*(double)100+" %");
		System.out.printf("classification accuracy w/ tiebreakers = %6.4f\n", (accuracy/(double)numberOfTestData*(double)100) );
		
	}

}
