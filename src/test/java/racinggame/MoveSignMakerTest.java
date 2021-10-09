package racinggame;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import nextstep.utils.Randoms;

public class MoveSignMakerTest {
	@ParameterizedTest
	@ValueSource(ints = {4, 5, 6, 7, 8, 9})
	void go(int random) {
		try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
			mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
				.thenReturn(random);
			assertThat(MoveSignMaker.getSign()).isEqualTo(MoveSign.GO);
		}
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3})
	void noGo(int random) {
		try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
			mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
				.thenReturn(random);
			assertThat(MoveSignMaker.getSign()).isEqualTo(MoveSign.NO_GO);
		}
	}
}
