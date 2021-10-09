package racinggame;

public class RacingCar {
	private final CarPosition position;
	private final CarName name;

	public RacingCar(String name) {
		this.name = new CarName(name);
		this.position = new CarPosition();
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

	public Integer getPosition() {
		return position.getValue();
	}
}
