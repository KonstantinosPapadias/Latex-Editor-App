package controller;

import java.util.HashMap;
import controller.commands.Command;
import controller.commands.CommandFactory;

public class LatexEditorController{
	private HashMap<String, Command> commands;
	private final String[] COMMAND_NAMES = {
			"addLatex",
			"changeVersionsStrategy",
			"create",
			"disableVersionsManagement",
			"edit",
			"enableVersionsManagement",
			"load",
			"rollbackToPreviousVersion",
			"save",
			"saveAsHTML",
			"loadFromHTML"
	};
	
	public LatexEditorController() {
		CommandFactory commandFactory = new CommandFactory();
		commands = new HashMap<String, Command>(); 
		for (String commandName : COMMAND_NAMES)
			commands.put(commandName, commandFactory.createCommand(commandName));
	}
	
	public void enact(String command) {
		commands.get(command).execute();
	}
}
