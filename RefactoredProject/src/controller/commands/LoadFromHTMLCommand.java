package controller.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import model.Document;
import model.DocumentManager;
import view.MainWindow;

public class LoadFromHTMLCommand implements Command {
	private ArrayList<String> htmlFileLines;
	
	public LoadFromHTMLCommand() {
		
	}
	
	public void execute() {
		try {
			readHTMLFileandTransformToLatexDocument();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void readHTMLFileandTransformToLatexDocument() throws FileNotFoundException {
		String filename = MainWindow.getInstance().getFilename();
		String htmlContentsString = readFileAsString(filename).trim();
		String latexEquivalentString = transformHTMLStringToLatex(htmlContentsString);
		setCurrentDocumentEqualToLatexString(latexEquivalentString);
	}
	
	private String readFileAsString(String fileName) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(fileName));
		String fileData = "";
		while (scanner.hasNextLine())
			fileData += scanner.nextLine() + "\n";
		return fileData.trim();
	}
	
	private String transformHTMLStringToLatex(String htmlContentsString) {
		String latexString = "";
		String htmlBodyString = parseHTMLBody(htmlContentsString);
		htmlFileLines = new ArrayList<String>(Arrays.asList(htmlBodyString.split("<")));
		htmlFileLines.remove(0); // remove empty line in the start of the string
		while (!htmlFileLines.isEmpty()) {
			String transformedTag = transformNextTagToLatex();
			latexString += transformedTag + "\n";
		}
		return latexString.trim();
	}
	
	private void setCurrentDocumentEqualToLatexString(String latexString) {
		Document newDocument = new Document();
		newDocument.setContents(latexString);
		DocumentManager.getInstance().setCurrentDocument(newDocument);
	}
	
	private String parseHTMLBody(String htmlContentsString) {
		String htmlBodyString = htmlContentsString.split("body>")[1].trim();
		htmlBodyString = htmlBodyString.substring(0, htmlBodyString.length() - 3).trim();
		return htmlBodyString;
	}

	private String transformNextTagToLatex() {
		String transformedTag = "";
		String currentHtmlLine = getNextHTMLLineAndRemoveFromList();
		if (currentHtmlLine.startsWith("h1")) {
			transformedTag = currentHtmlLine.trim().replace("h1>", "\\section{") + "}\n";
		} else if (currentHtmlLine.startsWith("h2")) {
			transformedTag = currentHtmlLine.trim().replace("h2>", "\\subsection{") + "}\n";
		} else if (currentHtmlLine.startsWith("h3")) {
			transformedTag = currentHtmlLine.trim().replace("h3>", "\\subsubsection{") + "}\n";
		} else if (currentHtmlLine.startsWith("ul")) {
			transformedTag = transformListTag("itemize");
		} else if (currentHtmlLine.startsWith("ol")) {
			transformedTag = transformListTag("enumerate");
		} else if (currentHtmlLine.startsWith("table")) {
			transformedTag = transformTableTag();
		} else if (currentHtmlLine.startsWith("img")) {
			transformedTag = transformImageTag(currentHtmlLine);
		}
		return transformedTag;
	}
	
	private String getNextHTMLLineAndRemoveFromList() {
		String nextHTMLLine = htmlFileLines.get(0).trim();
		htmlFileLines.remove(0);
		if (htmlFileLines.isEmpty())
			return nextHTMLLine;
		if (htmlFileLines.get(0).startsWith("/")) {
			htmlFileLines.remove(0); // remove closing tag
		}
		return nextHTMLLine;
	}
	
	private String transformListTag(String type) {
		String transformedListTag = "\\begin{" + type + "}\n";
		String listItem = getNextHTMLLineAndRemoveFromList();
		while (!listItem.startsWith("/ul") && !listItem.startsWith("/ol")) {
			transformedListTag += listItem.trim().replace("li>", "\\item ") + "\n";
			listItem = getNextHTMLLineAndRemoveFromList();
		}
		transformedListTag += "\\end{" + type + "}\n";
		return transformedListTag;
	}
	
	private String transformTableTag() {
		String transformedTable;
		ArrayList<ArrayList<String>> tableRowCellsList = getAllTableCells();
		transformedTable = createTransformedTableString(tableRowCellsList);
		return transformedTable;
	}

	private String transformImageTag(String htmlLine) {
		String transformedImage;
		String[] htmlLineValues = htmlLine.split("['\"]");
		String src, alt, width, height;
		src = htmlLineValues[1];
		alt = htmlLineValues[3];
		width = htmlLineValues[5];
		height = htmlLineValues[7];
		transformedImage = "\\begin{figure}\n\\includegraphics[width=" + width + ",height=" + height + "]{" + src + "}\n";
		transformedImage += "\\caption{" + alt + "}\\label{}\n\\end{figure}\n";
		return transformedImage;
	}
	
	private ArrayList<ArrayList<String>> getAllTableCells() {
		ArrayList<ArrayList<String>> tableRowCellsList = new ArrayList<ArrayList<String>>();
		ArrayList<String> currentRowCells = getTableRowCells();
		while (!currentRowCells.isEmpty()) {
			tableRowCellsList.add(currentRowCells);
			currentRowCells = getTableRowCells();
		}
		htmlFileLines.remove(0); // remove /table
		return tableRowCellsList;
	}
	
	private ArrayList<String> getTableRowCells() {
		ArrayList<String> tableRowCells = new ArrayList<String>();
		if (!htmlFileLines.get(0).startsWith("tr"))
			return tableRowCells;
		htmlFileLines.remove(0); // remove tr>
		String currentRowCell = htmlFileLines.get(0);
		for (int i=0; i<2; i++)
			htmlFileLines.remove(0); // remove element and closing tag
		while (currentRowCell.startsWith("th")) {
			tableRowCells.add(currentRowCell.trim().replace("th>", ""));
			currentRowCell = htmlFileLines.get(0);
			if (currentRowCell.startsWith("/tr"))
				break;
			for (int i=0; i<2; i++)
				htmlFileLines.remove(0); // remove element and closing tag
		}
		htmlFileLines.remove(0); // remove /tr>
		return tableRowCells;
	}
	
	private String createTransformedTableString(ArrayList<ArrayList<String>> tableRowElementsList) {
		String transformedTable = "\\begin{table}\n\\caption{}\\label{}\n\\begin{tabular}{";
		for (int i=0; i<tableRowElementsList.get(0).size(); i++)
			transformedTable += "|c";
		transformedTable += "|}\n\\hline\n";
		for (ArrayList<String> row : tableRowElementsList) {
			String rowString = "";
			for (String rowElement : row) {
				rowString += rowElement + "&";
			}
			rowString = rowString.substring(0, rowString.length() - 1) + "\\\\\n";
			transformedTable += rowString;
		}
		transformedTable += "\\hline\n\\end{tabular}\n\\end{table}\n";
		return transformedTable;
	}

}
