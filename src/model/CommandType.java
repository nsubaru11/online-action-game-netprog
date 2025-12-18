package model;

import java.util.Arrays;

public enum CommandType {
	// -------------------- サーバー -> クライアント --------------------
	GAME_START(0),
	GAME_OVER(1),
	MOVE(2),
	DAMAGE(3),
	DEAD(4),
	OPPONENT_RESIGNED(5),
	OPPONENT_DISCONNECTED(6),
	JOIN_ROOM(7),
	JOIN_OPPONENT(8),
	RESULT(9),
	GAME_ROOM_CLOSED(10),
	SERVER_CLOSED(11),

	// -------------------- クライアント -> サーバー --------------------
	CONNECT(50),
	READY(51),
	UNREADY(52),
	MOVE_LEFT(53),
	MOVE_UP(54),
	MOVE_RIGHT(55),
	MOVE_DOWN(56),
	RESIGN(57),
	DISCONNECT(58),

	// -------------------- その他 --------------------
	ERROR(254),
	UNKNOWN(255);

	private final int id;

	private static final CommandType[] CACHE = new CommandType[256];

	static {
		Arrays.fill(CACHE, UNKNOWN);
		for (CommandType type : values()) {
			if (type.id >= 0 && type.id < CACHE.length) {
				CACHE[type.id] = type;
			}
		}
	}

	CommandType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static CommandType fromId(int id) {
		if (id < 0 || id >= CACHE.length) return UNKNOWN;
		return CACHE[id];
	}
}
