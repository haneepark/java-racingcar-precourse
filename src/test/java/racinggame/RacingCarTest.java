package racinggame;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RacingCarTest {
	@Test
	void createCar() {
		RacingCar car = new RacingCar("ruby");
		assertThat(car.getName()).isEqualTo("ruby");
		assertThat(car.getPosition()).isEqualTo(0);
	}

	@Test
	void createCarWithLongName() {
		assertThatIllegalArgumentException().isThrownBy(() -> {
			new RacingCar("tomato");
		});
	}

	@Test
	void createCarWithNullName() {
		assertThatIllegalArgumentException().isThrownBy(() -> {
			new RacingCar(null);
		});
	}

	@Test
	void createCarWithEmptyName() {
		assertThatIllegalArgumentException().isThrownBy(() -> {
			new RacingCar("");
		});
	}

	@Test
	void go() {
		RacingCar car = new RacingCar("ruby");

		car.go();

		assertThat(car.getPosition()).isEqualTo(1);
	}
}
