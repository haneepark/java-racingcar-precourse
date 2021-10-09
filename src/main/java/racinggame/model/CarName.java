package racinggame.model;

import java.util.Objects;

public class CarName {
	public static final int MAX_LENGTH = 5;

	private final String name;

	public CarName(String name) {
		validateName(name);
		this.name = name;
	}

	public String toString() {
		return name;
	}

	private void validateName(String name) {
		if (Objects.isNull(name)
			|| name.isEmpty()
			|| name.length() > MAX_LENGTH) {
			throw new IllegalArgumentException();
		}
	}
}
