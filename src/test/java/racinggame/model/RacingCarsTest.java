package racinggame.model;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class RacingCarsTest {
	RacingCars cars;

	@BeforeEach
	void setUp() {
		List<String> names = Arrays.asList("finn", "jake", "bagel");
		cars = new RacingCars(names);
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
			new RacingCars(names);
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
}
