package server;

import model.GameCharacter;

public class Player {
	private String id;
	private String name;
	private boolean isReady; // ゲームロジックとしての状態
	private double x, y;      // アクションゲーム用の座標
	GameCharacter character;

	public Player(String name) {
		this.name = name;
	}

	public boolean isReady() {
		return isReady;
	}

	public void setX(double x) {

	}

	public String getPlayerName() {
		return name;
	}


	public void setReady(boolean ready) {
		this.isReady = ready;
	}
}
