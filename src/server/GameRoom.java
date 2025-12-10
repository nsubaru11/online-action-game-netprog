package server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class GameRoom {
	private static final Logger logger = Logger.getLogger(GameRoom.class.getName());
	private static final int MAX_PLAYERS = 4;
	private static int roomIdCounter = 0;
	private final int roomId;
	private final List<ClientHandler> players;
	private boolean isStarted;
	private boolean isClosed;

	public GameRoom() {
		this.roomId = roomIdCounter++;
		this.players = new ArrayList<>(MAX_PLAYERS);
		this.isStarted = false;
		this.isClosed = false;
	}

	public boolean addPlayer(ClientHandler player) {
		if (players.size() >= MAX_PLAYERS) return false;
		logger.info(() -> "ルーム " + roomId + " にプレイヤー " + player.getPlayerName() + " を追加しました");
		player.setMessageListener(msg -> this.handlePlayerAction(player, msg));
		players.add(player);
		return true;
	}

	// ここでゲームロジックを処理
	private void handlePlayerAction(ClientHandler player, String message) {
		// 例: "MOVE_RIGHT" というコマンドを解析して座標を更新
	}

	public void exitRoom(ClientHandler player) {
		players.remove(player);
		if (players.isEmpty()) isClosed = true;
	}

	private void startGame() {
		for (ClientHandler player : players) {
			if (!player.isReady()) return;
		}
		isStarted = true;
		logger.info(() -> "ルーム " + roomId + " でゲーム開始");
	}

	public void handleResign(ClientHandler resigner) {
		logger.info(() -> "ルーム " + roomId + ": Player resigned");

		closeRoom();
	}

	public void handleDisconnect(ClientHandler player) {
		logger.info(() -> "ルーム " + roomId + " でプレイヤー切断");
	}

	private boolean isGameOver() {
		return false;
	}

	private void endGame() {
		notifyResult();
		closeRoom();
	}

	private void notifyResult() {

	}

	private void closeRoom() {
		for (ClientHandler player : players) player.close();
	}

	private void broadcastMessage(String message) {
		for (ClientHandler player : players) player.sendMessage(message);
	}

	public int getRoomId() {
		return roomId;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ルーム ").append(roomId).append(":\n");
		if (isStarted) sb.append("ゲーム開始中\n");
		else sb.append("マッチング中\n");
		sb.append("プレイヤー:\n");
		for (ClientHandler player : players) sb.append("  ").append(player.getPlayerName()).append("\n");
		return sb.toString();
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof GameRoom)) return false;
		return ((GameRoom) obj).roomId == roomId;
	}

	public int hashCode() {
		return roomId;
	}
}
