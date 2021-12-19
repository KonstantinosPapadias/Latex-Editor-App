package controller.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import model.Document;
import model.DocumentManager;
import view.MainWindow;

public class LoadCommand implements Command {
	
	public LoadCommand() {
		
	}

	@Override
	public void execute() {
		DocumentManager documentManager = DocumentManager.getInstance();
		String type;
		String fileContents = readFile();
		documentManager.setCurrentDocument(new Document());
		documentManager.getCurrentDocument().setContents(fileContents);
		type = chooseCurrentDocumentType(fileContents);
		documentManager.getCurrentDocument().setType(type);
	}
	
	private String readFile() {
		MainWindow mainWindow = MainWindow.getInstance();
		String fileContents = "";
		try {
			Scanner scanner = new Scanner(new FileInputStream(mainWindow.getFilename()));
			while (scanner.hasNextLine()) {
				fileContents = fileContents + scanner.nextLine() + "\n";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileContents;
	}
	
	private String chooseCurrentDocumentType(String fileContents) {
		String type = "emptyTemplate";
		fileContents = fileContents.trim();
		if (fileContents.startsWith("\\documentclass[11pt,twocolumn,a4paper]{article}")) {
			type = "articleTemplate";
		}
		else if (fileContents.startsWith("\\documentclass[11pt,a4paper]{book}")) {
			type = "bookTemplate";
		}
		else if (fileContents.startsWith("\\documentclass[11pt,a4paper]{report}")) {
			type = "reportTemplate";
		}
		else if (fileContents.startsWith("\\documentclass{letter}")) {
			type = "letterTemplate";
		}
		return type;
	}
}
