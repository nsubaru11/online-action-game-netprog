package model;

public final class Protocol {
	private Protocol() {
	}

	// -------------------- サーバー -> クライアント --------------------
	public static String gameStart() {
		return CommandType.GAME_START.getId() + "";
	}

	public static String gameOver() {
		return CommandType.GAME_OVER.getId() + "";
	}

	public static String move(int i, int j) {
		return CommandType.MOVE.getId() + ":" + i + " " + j;
	}

	public static String damage(int hp) {
		return CommandType.DAMAGE.getId() + ":" + hp;
	}

	public static String dead() {
		return CommandType.DEAD.getId() + "";
	}

	public static String opponentResigned() {
		return CommandType.OPPONENT_RESIGNED.getId() + "";
	}

	public static String opponentDisconnected() {
		return CommandType.OPPONENT_DISCONNECTED.getId() + "";
	}

	public static String joinRoom() {
		return CommandType.JOIN_ROOM.getId() + "";
	}

	public static String joinOpponent(String opponentName) {
		return CommandType.JOIN_OPPONENT.getId() + ":" + opponentName;
	}

	public static String result(String result) {
		return CommandType.RESULT.getId() + ":" + result;
	}

	// -------------------- クライアント -> サーバー --------------------

	public static String connect(String playerName) {
		return CommandType.CONNECT.getId() + ":" + playerName;
	}

	public static String moveLeft() {
		return CommandType.MOVE_LEFT.getId() + "";
	}

	public static String moveRight() {
		return CommandType.MOVE_RIGHT.getId() + "";
	}

	public static String moveUp() {
		return CommandType.MOVE_UP.getId() + "";
	}

	public static String moveDown() {
		return CommandType.MOVE_DOWN.getId() + "";
	}

	public static String resign() {
		return CommandType.RESIGN.getId() + "";
	}

}
