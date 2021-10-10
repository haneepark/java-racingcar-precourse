package racinggame.model;

import java.util.ArrayList;
import java.util.List;

public class RacingCars {
	List<RacingCar> cars;

	public RacingCars(List<String> names) {
		cars = new ArrayList<>();

		for (String name : names) {
			RacingCar car = new RacingCar(name);
			cars.add(car);
		}
	}
}
