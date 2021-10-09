package racinggame;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import nextstep.utils.Randoms;

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
	void driveWithGoSign() {
		try (final MockedStatic<MoveSignMaker> moveSignMakerMock = mockStatic(MoveSignMaker.class)) {
			moveSignMakerMock.when(MoveSignMaker::getSign).thenReturn(MoveSign.GO);
			RacingCar car = new RacingCar("ruby");

			car.drive();

			assertThat(car.getPosition()).isEqualTo(1);
		}
	}

	@Test
	void driveWithNoGoSign() {
		try (final MockedStatic<MoveSignMaker> moveSignMakerMock = mockStatic(MoveSignMaker.class)) {
			moveSignMakerMock.when(MoveSignMaker::getSign).thenReturn(MoveSign.NO_GO);
			RacingCar car = new RacingCar("ruby");

			car.drive();

			assertThat(car.getPosition()).isEqualTo(0);
		}
	}
}
