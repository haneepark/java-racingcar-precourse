package racinggame;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import nextstep.utils.Console;
import racinggame.model.RacingCars;

public class Controller {
	private final View view;

	private RacingCars cars;

	private Integer count;

	public Controller(View view) {
		this.view = view;
	}

	public void play() {
		while (Objects.isNull(cars)) {
			setCars();
		}

		while (Objects.isNull(count)) {
			setCount();
		}

		playGame();
		displayResult();
	}

	private void setCars() {
		try {
			view.displayEnterCarNameMessage();
			String names = Console.readLine();
			cars = RacingCars.of(Arrays.asList(names.split(",")));
		} catch (IllegalArgumentException e) {
			view.displayInputErrorMessage();
		}
	}

	private void setCount() {
		try {
			view.displayEnterCountMessage();
			String countString = Console.readLine();
			count = Integer.parseInt(countString);
		} catch (IllegalArgumentException e) {
			view.displayInputErrorMessage();
		}
	}

	private void playGame() {
		view.displayStartGameMessage();

		for (int i = 0; i < count; i++) {
			cars.drive();
			Map<String, Integer> status = cars.getRacingStatusMap();
			view.displayCurrentStatus(status);
		}
	}

	private void displayResult() {
		RacingCars winners = cars.getWinningCars();
		view.displayResult(winners.getAllCarNames());
	}
}
