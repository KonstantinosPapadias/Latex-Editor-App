package controller.commands;

import model.VersionsManager;

public class RollbackToPreviousVersionCommand implements Command {
	
	public RollbackToPreviousVersionCommand() {
		
	}

	@Override
	public void execute() {
		VersionsManager.getInstance().rollback();
	}
}
