package racinggame;

public class CarPosition {
	Integer position;

	public CarPosition() {
		position = 0;
	}

	public void moveForward() {
		position++;
	}

	public Integer getValue() {
		return position;
	}
}
