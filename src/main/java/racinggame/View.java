package racinggame;

import java.util.List;
import java.util.Map;

public class View {
	public void displayEnterCarNameMessage() {
		System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
	}

	public void displayEnterCountMessage() {
		System.out.println("시도할 횟수는 몇회인가요?");
	}

	public void displayCurrentStatus(Map<String, Integer> map) {
		for (String name : map.keySet()) {
			System.out.println(name + " : " + drawPosition(map.get(name)));
		}
		System.out.println();
	}

	private String drawPosition(int position) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < position; i++) {
			result.append("-");
		}
		return result.toString();
	}

	public void displayResult(List<String> names) {
		String joinedNames = String.join(",", names);
		System.out.println("최종 우승자는 " + joinedNames + " 입니다.");
	}

	public void displayStartGameMessage() {
		System.out.println();
		System.out.println("실행결과");
	}

	public void displayInputErrorMessage() {
		System.out.println("[ERROR] 입력 형식이 잘못되었습니다.");
	}
}
