package racinggame.model;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class RacingCarsTest {
	@Test
	void createCars() {
		List<String> names = Arrays.asList("finn", "jake", "bagel");
		RacingCars cars = new RacingCars(names);
		assertThat(cars).isInstanceOf(RacingCars.class);
	}

	@Test
	void createCarsWithInvalidName() {
		assertThatIllegalArgumentException().isThrownBy(() -> {
			List<String> names = Arrays.asList("finn", "jake", "bagel", "bubblegum");
			new RacingCars(names);
		});
	}
}
