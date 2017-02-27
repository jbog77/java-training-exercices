package io.robusta.birthday.interfaces;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.robusta.birthday.implementations.GenerationThreshold;

/**
 * Created by Nicolas Zozol on 05/10/2016.
 */
public class GenerationThresholdTest {

	 GenerationThreshold generationThreshold = new GenerationThreshold();
	
	@Test
	public void findSmallestNumberOfPeopleRequiredToHave50() throws Exception {
		
		//Smaller is always 23
		assertTrue(generationThreshold.findSmallestNumberOfPeopleRequiredToHave50() == 23);
	}
	
	@Test
	public void calculateProbabilityOfSame() throws Exception {
		
		//One People : Always false
		assertTrue(generationThreshold.calculateProbabilityOfSame(1) == 0);
		
		
		//366 People : Always True
		assertTrue(generationThreshold.calculateProbabilityOfSame(366) == 1);
	}

}