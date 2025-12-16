package server;

import model.GameCharacter;

public class Player {
	private String id;
	private String name;
	private boolean isReady; // ゲームロジックとしての状態
	GameCharacter character;

	public Player(String name) {
		this.name = name;
	}

	public boolean isReady() {
		return isReady;
	}

	public String getPlayerName() {
		return name;
	}

	public void setPlayerName(String name) {
		this.name = name;
	}

	public void setReady() {
		this.isReady = true;
	}

	public void setUnReady() {
		this.isReady = false;
	}
}
