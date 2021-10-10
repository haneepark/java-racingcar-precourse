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

	@Nested
	class getCarsAt {
		@Test
		void allCarsAtZero() {
			RacingCars carsAtPosition = cars.getCarsAt(new CarPosition(0));
			Map<String, Integer> result = carsAtPosition.getRacingStatusMap();

			assertThat(result.size()).isEqualTo(3);
			assertThat(result.get("finn")).isEqualTo(0);
			assertThat(result.get("jake")).isEqualTo(0);
			assertThat(result.get("bagel")).isEqualTo(0);
		}

		@Test
		void NoCarAt2() {
			RacingCars carsAtPosition = cars.getCarsAt(new CarPosition(2));
			Map<String, Integer> result = carsAtPosition.getRacingStatusMap();

			assertThat(result.size()).isEqualTo(0);
		}

		@Test
		void OneCarAt2() {
			try (final MockedStatic<MoveSignMaker> moveSignMakerMock = mockStatic(MoveSignMaker.class)) {
				moveSignMakerMock.when(MoveSignMaker::getSign)
					.thenReturn(
						MoveSign.NO_GO, MoveSign.GO, MoveSign.NO_GO,
						MoveSign.NO_GO, MoveSign.GO, MoveSign.NO_GO
					);

				cars.drive();
				cars.drive();
				RacingCars carsAtPosition = cars.getCarsAt(new CarPosition(2));
				Map<String, Integer> result = carsAtPosition.getRacingStatusMap();

				assertThat(result.size()).isEqualTo(1);
				assertThat(result.get("jake")).isEqualTo(2);
			}
		}
	}

	@Nested
	class getWinningCars {
		@Test
		void oneCarWithNoMove() {
			RacingCars cars = RacingCars.of(Collections.singletonList("finn"));

			RacingCars winningCars = cars.getWinningCars();
			List<String> result = winningCars.getAllCarNames();

			assertThat(result.size()).isEqualTo(1);
			assertThat(result.get(0)).isEqualTo("finn");
		}

		@Test
		void threeCarsWithNoMove() {
			RacingCars winningCars = cars.getWinningCars();
			List<String> result = winningCars.getAllCarNames();

			assertThat(result.size()).isEqualTo(3);
			assertThat(result.contains("finn")).isTrue();
			assertThat(result.contains("jake")).isTrue();
			assertThat(result.contains("bagel")).isTrue();
		}

		@Test
		void threeCarsWithOneMove() {
			try (final MockedStatic<MoveSignMaker> moveSignMakerMock = mockStatic(MoveSignMaker.class)) {
				moveSignMakerMock.when(MoveSignMaker::getSign)
					.thenReturn(
						MoveSign.NO_GO, MoveSign.GO, MoveSign.NO_GO
					);

				cars.drive();
				RacingCars winningCars = cars.getWinningCars();
				List<String> result = winningCars.getAllCarNames();

				assertThat(result.size()).isEqualTo(1);
				assertThat(result.get(0)).isEqualTo("jake");
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
				RacingCars winningCars = cars.getWinningCars();
				List<String> result = winningCars.getAllCarNames();

				assertThat(result.size()).isEqualTo(1);
				assertThat(result.get(0)).isEqualTo("jake");
			}
		}

		@Test
		void threeCarsTwoWinners() {
			try (final MockedStatic<MoveSignMaker> moveSignMakerMock = mockStatic(MoveSignMaker.class)) {
				moveSignMakerMock.when(MoveSignMaker::getSign)
					.thenReturn(
						MoveSign.GO, MoveSign.GO, MoveSign.GO,
						MoveSign.NO_GO, MoveSign.GO, MoveSign.GO,
						MoveSign.NO_GO, MoveSign.GO, MoveSign.GO
					);

				cars.drive();
				cars.drive();
				cars.drive();
				RacingCars winningCars = cars.getWinningCars();
				List<String> result = winningCars.getAllCarNames();

				assertThat(result.size()).isEqualTo(2);
				assertThat(result.contains("jake")).isTrue();
				assertThat(result.contains("bagel")).isTrue();
			}
		}
	}
}
