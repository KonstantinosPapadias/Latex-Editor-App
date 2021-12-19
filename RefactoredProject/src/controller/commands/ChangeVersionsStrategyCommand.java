package controller.commands;

import model.VersionsManager;

public class ChangeVersionsStrategyCommand implements Command {
	
	public ChangeVersionsStrategyCommand() {
		
	}

	@Override
	public void execute() {
		VersionsManager.getInstance().changeStrategy();
	}
}
