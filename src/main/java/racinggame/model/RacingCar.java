package racinggame.model;

import java.util.Objects;

public class RacingCar {
	private final CarPosition position;
	private final CarName name;

	public RacingCar(String name) {
		this.name = new CarName(name);
		this.position = new CarPosition();
	}

	public static RacingCar ahead(RacingCar car1, RacingCar car2) {
		return car1.isAheadOf(car2) ? car1 : car2;
	}

	public String getName() {
		return name.toString();
	}

	public void drive() {
		if (MoveSignMaker.getSign().equals(MoveSign.GO)) {
			moveForward();
		}
	}

	private void moveForward() {
		position.moveForward();
	}

	public CarPosition getPosition() {
		return position;
	}

	private boolean isAheadOf(RacingCar behind) {
		return this.position.compareTo(behind.position) > 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		RacingCar racingCar = (RacingCar)o;
		return Objects.equals(position, racingCar.position) && Objects.equals(name, racingCar.name);
	}
}
