package controller.commands;

import model.DocumentManager;
import view.MainWindow;

public class SaveCommand implements Command {
	
	public SaveCommand() {
		
	}
	
	@Override
	public void execute() {
		DocumentManager documentManager = DocumentManager.getInstance();
		String fileName = MainWindow.getInstance().getFilename();
		documentManager.getCurrentDocument().save(fileName);
	}
}
