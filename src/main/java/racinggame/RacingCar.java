package racinggame;

public class RacingCar {
	private final CarName name;

	public RacingCar(String name) {
		this.name = new CarName(name);
	}

	public String getName() {
		return name.toString();
	}
}
