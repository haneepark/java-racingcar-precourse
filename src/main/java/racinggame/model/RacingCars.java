package racinggame.model;

import java.util.ArrayList;
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

	public RacingCars getWinningCars() {
		CarPosition winningPosition = findWinningPosition();
		return getCarsAt(winningPosition);
	}

	public CarPosition findWinningPosition() {
		RacingCar winningCar = cars.get(0);
		for (RacingCar car : cars) {
			winningCar = RacingCar.ahead(winningCar, car);
		}
		return winningCar.getPosition();
	}

	public RacingCars getCarsAt(CarPosition position) {
		List<RacingCar> carsAtPosition = new ArrayList<>();
		for (RacingCar car : this.cars) {
			if (car.isAt(position)) {
				carsAtPosition.add(car);
			}
		}
		return new RacingCars(carsAtPosition);
	}

	public List<String> getAllCarNames() {
		List<String> result = new ArrayList<>();

		for (RacingCar car : cars) {
			result.add(car.getName());
		}

		return result;
	}
}
