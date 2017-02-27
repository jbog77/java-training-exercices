package io.robusta.birthday.implementations;

import io.robusta.birthday.interfaces.IGenerationThreshold;
import io.robusta.birthday.interfaces.IPeopleCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas Zozol on 04/10/2016.
 */
public class GenerationThreshold implements IGenerationThreshold {

	public GenerationThreshold() {
	}

	@Override
	public int getSmallNumber() {
		return 0;
	}

	@Override
	public int getBigNumber() {
		return 0;
	}

	@Override
	public int findSmallestNumberOfPeopleRequiredToHave50() {
		int n = 0;
		for (int i = 1;i<100;i++){
			n++;
			System.out.println(calculateProbabilityOfSame(i));
			if (calculateProbabilityOfSame(i)>0.5){
				i=100;
			}
		}
		System.out.println(n);
		return n;
		
		
/*		System.out.println(calculateProbabilityOfSame(n));
		while(t){
			System.out.println(calculateProbabilityOfSame(n));
			if (calculateProbabilityOfSame(n)>0.5){
				t = true;
			}
			n++;
		}
		return n;*/
	}

	@Override
	public float calculateProbabilityOfSame(int size) {
		Generation gen = new Generation(10000,size);
		int n = gen.getNumberOfCollectionsThatHasTwoPeopleWithSameBirthday();
		return (float)n/10000;
	}
}
