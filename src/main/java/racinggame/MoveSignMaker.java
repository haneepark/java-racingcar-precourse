package racinggame;

import nextstep.utils.Randoms;

public class MoveSignMaker {
	public static final int MOVE_THRESHOLD = 4;

	public static MoveSign getSign() {
		int random = Randoms.pickNumberInRange(0, 9);

		if (random >= MOVE_THRESHOLD) {
			return MoveSign.GO;
		}

		return MoveSign.NO_GO;
	}
}
