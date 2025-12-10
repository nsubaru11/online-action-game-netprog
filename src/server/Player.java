package server;

public class Player {
	private String id;
	private String name;
	private boolean isReady; // ゲームロジックとしての状態
	private float x, y;      // アクションゲーム用の座標

	public Player(String name) {
		this.name = name;
	}

	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean ready) {
		this.isReady = ready;
	}
}
