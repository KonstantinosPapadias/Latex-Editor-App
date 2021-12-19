package controller.commands;

import model.VersionsManager;

public class DisableVersionsManagementCommand implements Command {
	
	public DisableVersionsManagementCommand() {
		
	}

	@Override
	public void execute() {
		VersionsManager.getInstance().disable();
	}
}
