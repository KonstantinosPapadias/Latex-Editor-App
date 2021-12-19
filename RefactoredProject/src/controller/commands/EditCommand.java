package controller.commands;

import model.DocumentManager;
import model.VersionsManager;
import view.MainWindow;

public class EditCommand implements Command {	
	
	public EditCommand() {
		
	}

	@Override
	public void execute() {
		DocumentManager documentManager = DocumentManager.getInstance();
		VersionsManager versionsManager = VersionsManager.getInstance();
		String currentMainWindowText = MainWindow.getInstance().getCurrentMainWindowText();
		if(versionsManager.isEnabled()) {
			createNewDocumentVersion(documentManager, versionsManager);
		}
		documentManager.getCurrentDocument().setContents(currentMainWindowText);
	}
	
	private void createNewDocumentVersion(DocumentManager documentManager, VersionsManager versionsManager) {
		versionsManager.getStrategy().putVersion(documentManager.getCurrentDocument());
		documentManager.getCurrentDocument().changeVersion();
	}
}
