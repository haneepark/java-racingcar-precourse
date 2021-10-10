package racinggame.model;

import java.util.Objects;

public class CarPosition {
	Integer position;

	public CarPosition() {
		position = 0;
	}

	public CarPosition(Integer position) {
		this.position = position;
	}

	public void moveForward() {
		position++;
	}

	public Integer getValue() {
		return position;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CarPosition position1 = (CarPosition)o;
		return Objects.equals(position, position1.position);
	}

	public int compareTo(CarPosition behind) {
		return position.compareTo(behind.getValue());
	}
}
