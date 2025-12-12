package server;


class Command {
	private final ClientHandler sender;
	private final String message;

	public Command(final ClientHandler sender, final String message) {
		this.sender = sender;
		this.message = message;
	}

	public ClientHandler getSender() {
		return sender;
	}

	public String getMessage() {
		return message;
	}
}
