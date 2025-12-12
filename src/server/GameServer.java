package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.logging.Logger;

public final class GameServer {
	private static final Logger logger = Logger.getLogger(GameServer.class.getName());
	private static final int DEFAULT_PORT = 10000;
	private final ServerSocket serverSocket;
	private final LinkedHashSet<GameRoom> gameRooms;
	private final LinkedHashSet<ClientHandler> waitingPlayers;
	private boolean isRunning;

	public GameServer(int port) {
		gameRooms = new LinkedHashSet<>();
		waitingPlayers = new LinkedHashSet<>();
		isRunning = true;

		try {
			serverSocket = new ServerSocket(port);
			logger.info(() -> "オセロサーバーがポート " + port + " で起動しました");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		ServerLoggingConfig.initialize();

		int port = DEFAULT_PORT;

		// 引数があれば、それをポート番号として使う
		if (args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				logger.warning(() -> "ポート番号が不正です。デフォルト(" + DEFAULT_PORT + ")を使用します。");
			}
		}

		GameServer server = new GameServer(port);
		server.start();
	}

	public void start() {
		while (isRunning) {
			try {
				Socket clientSocket = serverSocket.accept();
				logger.info("新しいクライアントが接続しました");

				ClientHandler handler = new ClientHandler(clientSocket);
				handler.start();
				handler.setDisconnectListener(() -> disconnectHandler(handler));

				addWaitingHandler(handler);
			} catch (IOException e) {
				logger.log(java.util.logging.Level.SEVERE, "クライアント接続処理で例外", e);
				isRunning = false;
			}
		}
	}

	public synchronized void addWaitingHandler(ClientHandler handler) {
		waitingPlayers.add(handler);
		logger.info(() -> "プレイヤーが待ち行列に追加されました: " + handler.getConnectionId());
		matchPlayers();
	}

	private synchronized void matchPlayers() {
		Iterator<ClientHandler> iterator = waitingPlayers.iterator();
		while (iterator.hasNext()) {
			ClientHandler handler = iterator.next();
			boolean assigned = false;
			for (GameRoom room : gameRooms) {
				if (room.join(handler)) {
					assigned = true;
					logger.info(() -> "プレイヤーがルーム " + room.getRoomId() + " に追加されました: " + handler.getConnectionId());
					logger.info(room::toString);
					break;
				}
			}
			if (!assigned) {
				GameRoom room = new GameRoom();
				room.join(handler);
				gameRooms.add(room);
				logger.info(() -> "プレイヤーがルーム " + room.getRoomId() + " に追加されました: " + handler.getConnectionId());
				logger.info(room::toString);
			}
			iterator.remove();
		}
	}

	private synchronized void disconnectHandler(ClientHandler handler) {
		if (handler == null || !isRunning) return;
		logger.info(() -> "プレイヤーが切断されました: " + handler.getConnectionId());
		waitingPlayers.remove(handler);
	}
}
