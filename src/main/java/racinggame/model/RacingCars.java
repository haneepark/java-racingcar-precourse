package racinggame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RacingCars {
	List<RacingCar> cars;

	public static RacingCars of(List<String> names) {
		List<RacingCar> cars = new ArrayList<>();
		for (String name : names) {
			cars.add(new RacingCar(name));
		}
		return new RacingCars(cars);
	}

	private RacingCars(List<RacingCar> cars) {
		this.cars = cars;
	}

	public void drive() {
		cars.forEach(RacingCar::drive);
	}

	public Map<String, Integer> getRacingStatusMap() {
		Map<String, Integer> map = new HashMap<>();
		for (RacingCar car : cars) {
			map.put(car.getName(), car.getPosition().getValue());
		}
		return map;
	}
}
