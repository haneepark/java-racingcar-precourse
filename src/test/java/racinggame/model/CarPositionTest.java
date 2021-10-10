package racinggame.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CarPositionTest {
	@Test
	void defaultPosition() {
		CarPosition position = new CarPosition();
		assertThat(position.getValue()).isEqualTo(0);
	}

	@Test
	void moveForward() {
		CarPosition position = new CarPosition();
		position.moveForward();
		assertThat(position.getValue()).isEqualTo(1);
	}

	@Test
	void moveForwardTwice() {
		CarPosition position = new CarPosition();
		position.moveForward();
		position.moveForward();
		assertThat(position.getValue()).isEqualTo(2);
	}

	@Test
	void compareTo() {
		CarPosition ahead = new CarPosition(1);
		CarPosition behind = new CarPosition();

		assertThat(ahead.compareTo(behind)).isEqualTo(1);
	}
}
