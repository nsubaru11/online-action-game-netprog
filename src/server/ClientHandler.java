package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;
import java.util.logging.Logger;

class ClientHandler extends Thread {
	private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());

	private final Socket socket;
	private final PrintWriter out;
	private final BufferedReader in;

	private Consumer<String> messageListener;
	private boolean isConnected;

	public ClientHandler(Socket socket) {
		this.socket = socket;
		this.isConnected = true;

		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void setMessageListener(Consumer<String> messageListener) {
		this.messageListener = messageListener;
	}

	public void run() {
		try {
			// メッセージ受信ループ
			while (isConnected) {
				String line = in.readLine();
				if (line == null) break;
				if (messageListener != null) {
					logger.info(() -> "受信: " + line);
					messageListener.accept(line);
				}
			}
		} catch (IOException e) {
			logger.log(java.util.logging.Level.WARNING, "接続エラー", e);
		} finally {
			isConnected = false;
		}
	}

	public void sendMessage(String message) {
		out.println(message);
		out.flush();
	}

	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			logger.log(java.util.logging.Level.WARNING, "プレイヤーソケットクローズに失敗", e);
		}
	}
}
