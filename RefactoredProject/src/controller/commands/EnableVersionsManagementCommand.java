package controller.commands;

import model.VersionsManager;

public class EnableVersionsManagementCommand implements Command {
	
	public EnableVersionsManagementCommand() {
		
	}

	@Override
	public void execute() {
		VersionsManager.getInstance().enableStrategy();
	}
}
