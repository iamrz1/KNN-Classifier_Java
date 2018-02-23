package com.assign.cse440;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DistanceProcessor {

	private int testIndex;
	private ArrayList <Integer> trainIndexes = new ArrayList <>();
	private ArrayList <Double> distanceArray = new ArrayList <>();
	private HashMap <Double,Integer> kDistanceArray = new HashMap <>();
	private HashMap <Double,Integer> kClassArray = new HashMap <>();
	private HashMap <Double,Double> kMapDistanceAndRow = new HashMap <>();
	private HashMap <Double,Integer> tiedClassArray = new HashMap <>();
	ArrayList<ArrayList<Double>> trainArrayList = new ArrayList<>();
	ArrayList<ArrayList<Double>> testArrayList = new ArrayList<>();
	private int tieSize=0;
	private Double chosenDistance;
	private Double Accuracy ;
	public Double getAccuracy() {
		return Accuracy;
	}



	int k=0;

	public DistanceProcessor( int testIndex, ArrayList <Integer> trainIndexes, ArrayList <Double> distanceArray,int k,ArrayList<ArrayList<Double>> trainArrayList,ArrayList<ArrayList<Double>> testArrayList) {
		this.distanceArray=distanceArray;
		this.testIndex= testIndex;
		this.trainIndexes=trainIndexes;
		this.k = k;
		this.trainArrayList=trainArrayList;
		this.testArrayList= testArrayList;
		// TODO Auto-generated constructor stub
		findKNN(k);
		generateClasLabels();
		
	}
	
	public HashMap<Double, Integer> getkDistanceArray() {
		return kDistanceArray;
	}

	public void findKNN(int k){
		Double maxDist = 0.0;
		int numberOfTrainExamples = trainIndexes.size();
		int mapSize=0;
		
		for (int i=0;i<numberOfTrainExamples;i++){
			Double distance = distanceArray.get(i);
			if (mapSize>=k){//I have a map of size k. Current mapsize can not exceed size k
				
				if ( distance < maxDist){
					//then add it to existing hashmap and remove current maxdist
					kDistanceArray.put(distance, i);
					kDistanceArray.remove(maxDist);
					//upadate maxdist
					maxDist = findMax();
				}
				else{
					continue;
				}
			}
			else if (mapSize<k){
				///then add it to existing hashmap
				mapSize++;
				//upadate maxdist
				kDistanceArray.put(distance, i);
				maxDist = findMax();
				
			}
		}
	}
	
	private Double findMax(){
		Double maxDist = Collections.max(kDistanceArray.keySet());
		
		//System.out.println("maxDist = "+maxDist);
		
		return maxDist;
	}
	
	
	private void generateClasLabels(){
		int indexOfClassLabel = testArrayList.size()-1;
		Set set = kDistanceArray.entrySet();

		Iterator it =  set.iterator();
		Double  maxValue=0.0,maxKey=0.0;
		ArrayList <Double> maxKeyArray= new ArrayList<>();
		int classLabelIndex = trainArrayList.size()-1;
		// Display elements
		while(it.hasNext()) {
			 @SuppressWarnings("rawtypes")
			Map.Entry me = (Map.Entry)it.next();
			Double distance = (Double) me.getKey();
			int index = (int) me.getValue();
			 
			 Double classLabel = trainArrayList.get(classLabelIndex).get(index);
			 kMapDistanceAndRow.put(distance, classLabel);
			// System.out.println(classLabel);
			 if (!kClassArray.containsKey(classLabel))
				 kClassArray.put(classLabel, 1);
			 else {
				 int occurance = kClassArray.get(classLabel);
				 occurance++;
				 kClassArray.replace(classLabel, occurance);
			 }
			 
		}
		int maxOccurance = Collections.max(kClassArray.values());
		//System.out.println(maxOccurance);
		ArrayList<Double> randomClaasLabels = new ArrayList<>();

		Set classArraySet = kClassArray.entrySet();
		Iterator classIt =  classArraySet.iterator();
		while(classIt.hasNext()){
			 @SuppressWarnings("rawtypes")
			 Map.Entry mapentry = (Map.Entry)classIt.next();
			 Double keyClassLabel = (Double) mapentry.getKey();
			 int valueOccurancce = (int) mapentry.getValue();
			 

			 if ((valueOccurancce==maxOccurance)){
				 tiedClassArray.put(keyClassLabel, valueOccurancce);
				 randomClaasLabels.add(keyClassLabel);
			 }
		}
		
		
		
		Collections.shuffle(randomClaasLabels);
		Double chosenClassLabel = randomClaasLabels.get(0);
		tieSize = maxOccurance*tiedClassArray.size();
		//System.out.println("Size of tie = "+ tieSize);
		chosenDistance = chosenDistance(chosenClassLabel);
		//System.out.println("chosenDistance = "+chosenDistance);
		int chosenRow = chosenRow(chosenDistance);
		//System.out.println("chosenRow = "+chosenRow);
		int objectID = testIndex;
		Double trueClass = testArrayList.get(indexOfClassLabel).get(objectID);
		//System.out.println("trueClass = "+trueClass);
		Accuracy = -1.0;
		if (chosenClassLabel.equals(trueClass)){
			Accuracy = 1.0/tieSize;
		}
		else Accuracy = 0.0;
		//System.out.println("Accuracy = "+Accuracy);
		
		System.out.printf("ID=%5d, predicted=%3d, true=%3d, nn=%5d, distance=%7.2f, accuracy= %4.2f\n", 
				objectID, chosenClassLabel.intValue(), trueClass.intValue(), chosenRow, chosenDistance, Accuracy);
		
	}
	
	private Double chosenDistance(Double chosenClassLabel){
		
		Set set = kMapDistanceAndRow.entrySet();

		Iterator it =  set.iterator();
		Double ChosenDistance=-1.0;
		ArrayList<Double> distanceArray = new ArrayList<>();
		int classLabelIndex = trainArrayList.size()-1;
		// Display elements
		
		while(it.hasNext()) {
			 @SuppressWarnings("rawtypes")
			 Map.Entry me = (Map.Entry)it.next();
			 Double distance = (Double) me.getKey();
			 Double classLabel = (Double) me.getValue();
			 if (classLabel.equals(chosenClassLabel)){
				 distanceArray.add(distance);
			 }

			 
		}
		Collections.shuffle(distanceArray);
		ChosenDistance = distanceArray.get(0);
		return ChosenDistance;
	}
	
	private int chosenRow(Double ChosenDistance){
		
		Set set = kDistanceArray.entrySet();

		Iterator it =  set.iterator();

		
		int chosenRow = -1;
		// Display elements
		while(it.hasNext()) {
			 @SuppressWarnings("rawtypes")
			 Map.Entry me = (Map.Entry)it.next();
			 Double distance = (Double) me.getKey();
			 int row = (int) me.getValue();
			 if (ChosenDistance.equals(distance)){
				 chosenRow=row;
			 }

			 
		}
		return chosenRow;
	}
	


	@Override
	public String toString() {
		return "DistanceProcessor [kDistanceArray=" + kDistanceArray + ", tiedClassArray=" + tiedClassArray + "]";
	}

	



	
	

}
