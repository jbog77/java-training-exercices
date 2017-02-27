package io.robusta.birthday.implementations;

import io.robusta.birthday.interfaces.IGeneration;
import io.robusta.birthday.interfaces.IPeopleCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas Zozol on 04/10/2016.
 */
public class Generation implements IGeneration {

	List<PeopleCollection> collections;

	public Generation() {

	}

	public Generation(int n, int collectionSize) {
		this.collections = createAllRandom(n, collectionSize);
	}

	@Override
	public PeopleCollection createRandom(int size) {
		return new PeopleCollection(size);
	}

	@Override
	public List<PeopleCollection> createAllRandom(int n, int size) {
		List<PeopleCollection> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			list.add(createRandom(size));
		}
		return list;
	}

	@Override
	public List<PeopleCollection> getPeopleCollections() {
		return this.collections;
	}

	@Override
	public int getNumberOfCollectionsThatHasTwoPeopleWithSameBirthday() {
		int n = 0;
		for (PeopleCollection p : collections) {
			if (p.hasSame()) {
				n++;
			}
		}
		return n;
	}

	@Override
	public boolean isLessThan50() {
		int size = collections.size();
		int nbSame = getNumberOfCollectionsThatHasTwoPeopleWithSameBirthday();
		if ((float)(nbSame / size) < 0.5 ) {
			return true;
		}
			return false;
	}

}
