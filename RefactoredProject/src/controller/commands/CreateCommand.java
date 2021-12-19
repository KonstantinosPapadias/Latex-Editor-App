package controller.commands;

import model.Document;
import model.DocumentManager;

public class CreateCommand implements Command {
	
	public CreateCommand() {
		
	}

	@Override
	public void execute() {
		DocumentManager documentManager = DocumentManager.getInstance();
		String newDocumentType = documentManager.getCurrentDocument().getType();
		Document newDocument = documentManager.createDocument(newDocumentType);
		documentManager.setCurrentDocument(newDocument);
	}
}
