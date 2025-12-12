package server;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

class GameRoom extends Thread {
	private static final Logger logger = Logger.getLogger(GameRoom.class.getName());
	private static final int MAX_PLAYERS = 4;
	private static int roomIdCounter = 0;
	private final int roomId;
	private final Map<ClientHandler, Player> playerMap;
	private final Queue<Command> commandQueue = new ConcurrentLinkedQueue<>();
	private boolean isStarted;
	private boolean isClosed;

	public GameRoom() {
		this.roomId = roomIdCounter++;
		this.playerMap = new ConcurrentHashMap<>(MAX_PLAYERS);
		this.isStarted = false;
		this.isClosed = false;
	}

	@Override
	public void run() {
		while (!isClosed) {
			while (!commandQueue.isEmpty()) {
				Command cmd = commandQueue.poll();
				ClientHandler sender = cmd.getSender();
				String msg = cmd.getMessage();
				Player targetPlayer = playerMap.get(sender);
				if (targetPlayer != null) {
					handleCommand(targetPlayer, msg);
				}
			}
			broadcastState();
		}
	}

	public boolean join(final ClientHandler handler) {
		if (playerMap.size() >= MAX_PLAYERS) return false;
		logger.info(() -> "ルーム " + roomId + " にプレイヤー " + handler.getConnectionId() + " を追加しました");
		handler.setMessageListener(msg -> this.commandQueue.add(new Command(handler, msg)));
		handler.setDisconnectListener(() -> handleDisconnect(handler));
		Player newPlayer = new Player("NoName");
		playerMap.put(handler, newPlayer);
		return true;
	}

	// ここでゲームロジックを処理
	private void handleCommand(Player player, String message) {
		// 例: "MOVE_RIGHT" というコマンドを解析して座標を更新
	}

	public void exitRoom(ClientHandler handler) {
		playerMap.remove(handler);
		handler.close();
		if (playerMap.isEmpty()) isClosed = true;
	}

	private void startGame() {
		for (Player player : playerMap.values()) {
			if (!player.isReady()) return;
		}
		isStarted = true;
		logger.info(() -> "ルーム " + roomId + " でゲーム開始");
	}

	public void handleResign(Player resigner) {
		logger.info(() -> "ルーム " + roomId + ": Player resigned");

		closeRoom();
	}

	public void handleDisconnect(ClientHandler handler) {
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
		for (ClientHandler handler : playerMap.keySet()) {
			handler.close();
		}
	}

	private void broadcastState() {
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
		for (Player player : playerMap.values()) sb.append("  ").append(player.getPlayerName()).append("\n");
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
