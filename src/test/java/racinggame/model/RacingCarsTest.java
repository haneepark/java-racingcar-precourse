package racinggame.model;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class RacingCarsTest {
	RacingCars cars;

	@BeforeEach
	void setUp() {
		List<String> names = Arrays.asList("finn", "jake", "bagel");
		cars = RacingCars.of(names);
	}

	@Test
	void defaultStatus() {
		Map<String, Integer> map = cars.getRacingStatusMap();

		assertThat(map.containsKey("finn")).isTrue();
		assertThat(map.containsKey("jake")).isTrue();
		assertThat(map.containsKey("bagel")).isTrue();
		assertThat(map.get("finn")).isEqualTo(0);
		assertThat(map.get("jake")).isEqualTo(0);
		assertThat(map.get("bagel")).isEqualTo(0);
	}

	@Test
	void createCarsWithInvalidName() {
		assertThatIllegalArgumentException().isThrownBy(() -> {
			List<String> names = Arrays.asList("finn", "jake", "bagel", "bubblegum");
			RacingCars.of(names);
		});
	}

	@Test
	void drive() {
		try (final MockedStatic<MoveSignMaker> moveSignMakerMock = mockStatic(MoveSignMaker.class)) {
			moveSignMakerMock.when(MoveSignMaker::getSign)
				.thenReturn(MoveSign.NO_GO, MoveSign.GO, MoveSign.NO_GO);

			cars.drive();
		 	Map<String, Integer> map = cars.getRacingStatusMap();

			assertThat(map.get("finn")).isEqualTo(0);
			assertThat(map.get("jake")).isEqualTo(1);
			assertThat(map.get("bagel")).isEqualTo(0);
		}
	}

	@Test
	void driveTwice() {
		try (final MockedStatic<MoveSignMaker> moveSignMakerMock = mockStatic(MoveSignMaker.class)) {
			moveSignMakerMock.when(MoveSignMaker::getSign)
				.thenReturn(
					MoveSign.NO_GO, MoveSign.GO, MoveSign.NO_GO,
					MoveSign.NO_GO, MoveSign.GO, MoveSign.NO_GO
				);

			cars.drive();
			cars.drive();
			Map<String, Integer> map = cars.getRacingStatusMap();

			assertThat(map.get("finn")).isEqualTo(0);
			assertThat(map.get("jake")).isEqualTo(2);
			assertThat(map.get("bagel")).isEqualTo(0);
		}
	}

	@Nested
	class WinningPosition {
		@Test
		void oneCarWithNoMove() {
			RacingCars cars = RacingCars.of(Collections.singletonList("finn"));

			CarPosition winningPosition = cars.findWinningPosition();

			assertThat(winningPosition).isEqualTo(new CarPosition(0));
		}

		@Test
		void threeCarsWithNoMove() {
			CarPosition winningPosition = cars.findWinningPosition();

			assertThat(winningPosition).isEqualTo(new CarPosition(0));
		}

		@Test
		void threeCarsWithOneMove() {
			try (final MockedStatic<MoveSignMaker> moveSignMakerMock = mockStatic(MoveSignMaker.class)) {
				moveSignMakerMock.when(MoveSignMaker::getSign)
					.thenReturn(
						MoveSign.NO_GO, MoveSign.GO, MoveSign.NO_GO
						);

				cars.drive();
				CarPosition winningPosition = cars.findWinningPosition();

				assertThat(winningPosition).isEqualTo(new CarPosition(1));
			}
		}

		@Test
		void threeCarsWinAlone() {
			try (final MockedStatic<MoveSignMaker> moveSignMakerMock = mockStatic(MoveSignMaker.class)) {
				moveSignMakerMock.when(MoveSignMaker::getSign)
					.thenReturn(
						MoveSign.NO_GO, MoveSign.GO, MoveSign.GO,
						MoveSign.NO_GO, MoveSign.GO, MoveSign.GO,
						MoveSign.NO_GO, MoveSign.GO, MoveSign.NO_GO
					);

				cars.drive();
				cars.drive();
				cars.drive();
				CarPosition winningPosition = cars.findWinningPosition();

				assertThat(winningPosition).isEqualTo(new CarPosition(3));
			}
		}

		@Test
		void threeCarsTwoWinners() {
			try (final MockedStatic<MoveSignMaker> moveSignMakerMock = mockStatic(MoveSignMaker.class)) {
				moveSignMakerMock.when(MoveSignMaker::getSign)
					.thenReturn(
						MoveSign.NO_GO, MoveSign.GO, MoveSign.GO,
						MoveSign.NO_GO, MoveSign.GO, MoveSign.GO
					);

				cars.drive();
				cars.drive();
				CarPosition winningPosition = cars.findWinningPosition();

				assertThat(winningPosition).isEqualTo(new CarPosition(2));
			}
		}
	}
}
