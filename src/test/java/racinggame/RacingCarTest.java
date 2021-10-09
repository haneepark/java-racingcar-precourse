package racinggame;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RacingCarTest {
	@Test
	void createCar() {
		RacingCar car = new RacingCar("ruby");
		assertThat(car.getName()).isEqualTo("ruby");
	}
}
