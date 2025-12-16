package server;


import model.CommandType;

class Command {
	private final ClientHandler sender;
	private final String commandBody;
	private final CommandType commandType;

	public Command(final ClientHandler sender, final String message) {
		this.sender = sender;
		int index = message.indexOf(":");
		if (index == -1) {
			this.commandType = CommandType.fromId(Integer.parseInt(message));
			this.commandBody = "";
		} else {
			this.commandType = CommandType.fromId(Integer.parseInt(message.substring(0, index)));
			this.commandBody = message.substring(index + 1);
		}
	}

	public CommandType getCommandType() {
		return commandType;
	}

	public ClientHandler getSender() {
		return sender;
	}

	public String getBody() {
		return commandBody;
	}
}
