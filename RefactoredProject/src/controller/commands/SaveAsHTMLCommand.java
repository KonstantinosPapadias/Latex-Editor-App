package controller.commands;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import model.DocumentManager;
import view.MainWindow;

public class SaveAsHTMLCommand implements Command {
	private final String htmlHeader = "<!DOCTYPE html>\r\n" + 
								"<html lang=\"en\">\r\n" + 
								"<head>\r\n" + 
								"    <meta charset=\"UTF-8\">\r\n" + 
								"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n" + 
								"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
								"    <title>Document</title>\r\n" + 
								"</head>\r\n" + 
								"<body>\n";
	private final String htmlClosingTag = "</body>\r\n"
									+ "</html>";
	private ArrayList<String> currentDocumentLinesList;
	
	
	public SaveAsHTMLCommand() {
		
	}

	@Override
	public void execute() {
		try {
			transformCurrentDocumentToHTMLAndSave();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void transformCurrentDocumentToHTMLAndSave() throws IOException {
		String currentDocumentContents = DocumentManager.getInstance().getCurrentDocument().getContents();
		String transformedDocument = transformDocumentToHTMLString(currentDocumentContents);
		saveTransformedDocument(transformedDocument);
	}
	
	private String transformDocumentToHTMLString(String documentContents) {
		currentDocumentLinesList = getDocumentLinesList(documentContents);
		return htmlHeader + getHTMLBodyFromDocumentLines() + htmlClosingTag;
	}
	
	private ArrayList<String> getDocumentLinesList(String documentContents) {
		ArrayList<String> documentLinesList = new ArrayList<String>(Arrays.asList(documentContents.split("\\\\")));
		documentLinesList.remove(0); // remove the first line which is equal to ""
		return documentLinesList;
	}
	
	private String getHTMLBodyFromDocumentLines() {
		String transformedDocumentContents = "";
		while (!currentDocumentLinesList.isEmpty())
			transformedDocumentContents += transformNextElementToHTML();
		return transformedDocumentContents;
	}
	
	private String transformNextElementToHTML() {
		String latexElementFirstLine = getDocumentLineAndRemoveFromList();
		if (!latexElementFirstLine.startsWith("begin")) {
			return transformSimpleElementToHTML(latexElementFirstLine);
		} else if (latexElementFirstLine.startsWith("begin{itemize}")) {
			return transformListElementToHTML("ul");
		} else if (latexElementFirstLine.startsWith("begin{enumerate}")) {
			return transformListElementToHTML("ol");
		} else if (latexElementFirstLine.startsWith("begin{table}")) {
			return transformTableElementToHTML();
		} else if (latexElementFirstLine.startsWith("begin{figure}")) {
			return transformFigureElementToHTML();
		} else {
			return "";
		}
	}
	
	private String getDocumentLineAndRemoveFromList() {
		String nextDocumentLine = currentDocumentLinesList.get(0);
		currentDocumentLinesList.remove(0);
		currentDocumentLinesList.trimToSize();
		return nextDocumentLine;
	}
	
	private String transformSimpleElementToHTML(String latexElementLine) {
		if (latexElementLine.startsWith("chapter")) {
			return latexElementLine.replace("chapter{", "<h1>").replace("}", "</h1>").trim() + "\n";
		} else if (latexElementLine.startsWith("section")) {
			return latexElementLine.replace("section{", "<h1>").replace("}", "</h1>").trim() + "\n";
		} else if (latexElementLine.startsWith("subsection")) {
			return latexElementLine.replace("subsection{", "<h2>").replace("}", "</h2>").trim() + "\n";
		} else if (latexElementLine.startsWith("subsubsection")) {
			return latexElementLine.replace("subsubsection{", "<h3>").replace("}", "</h3>").trim() + "\n";
		} else {
			return "";
		}
	}
	
	private String transformListElementToHTML(String listType) {
		String listOpeningTag = "<" + listType + ">\n";
		String listClosingTag = "</" + listType + ">\n";
		return listOpeningTag + getListBodyFromDocumentLines() + listClosingTag;
	}
	
	private String getListBodyFromDocumentLines() {
		String listBody = "";
		String currentDocumentLine = getDocumentLineAndRemoveFromList();
		while (!currentDocumentLine.startsWith("end")) {
			listBody += parseListElementFromDocumentLine(currentDocumentLine);
			currentDocumentLine = getDocumentLineAndRemoveFromList();
		}
		return listBody;
	}
	
	private String parseListElementFromDocumentLine(String documentLine) {
		return documentLine.trim().replace("item ", "\t<li>") + "</li>\n";
	}
	
	private String transformTableElementToHTML() {
		String transformedElement = "<table>\n";
		for (int i=0; i<3; i++)
			getDocumentLineAndRemoveFromList(); // remove caption, label, begin{tabular}
		String currentDocumentLine = getDocumentLineAndRemoveFromList();
		String currentTableRow = parseTableRowFromDocumentLine(currentDocumentLine);
		transformedElement += currentTableRow;
		currentDocumentLine = getDocumentLineAndRemoveFromList();
		while (!currentDocumentLine.contains("hline")) {
			currentTableRow = parseTableRowFromDocumentLine(currentDocumentLine);
			transformedElement += currentTableRow;
			currentDocumentLine = getDocumentLineAndRemoveFromList();
		}
		for (int i=0; i<2; i++)
			getDocumentLineAndRemoveFromList(); // remove hline, tabular, table
		transformedElement += "</table>\n";
		return transformedElement;
	}
	
	private String parseTableRowFromDocumentLine(String documentLine) {
		if (!documentLine.contains("&"))
			return "";
		String tableRow = "\t<tr>\n";
		String[] rowElements = documentLine.replace("hline", "").trim().split("&");
		for (int i=0; i<rowElements.length; i++)
			rowElements[i] = "\t\t<th>" + rowElements[i] + "</th>\n";
		tableRow += String.join("", rowElements) + "\t</tr>\n";
		return tableRow;
	}
	
	private String transformFigureElementToHTML() {
		String transformedElement;
		String[] widthHeightSrcArray = parseWidthHeightSrcFromDocumentLine(getDocumentLineAndRemoveFromList());
		if (widthHeightSrcArray.length != 3)
			return "";
		String width = widthHeightSrcArray[0];
		String height = widthHeightSrcArray[1];
		String src = widthHeightSrcArray[2];
		String alt = getDocumentLineAndRemoveFromList().split("[{}]")[1];
		transformedElement = "<img src='" + src + "' alt='" + alt + "' width='" + width + "' height='" + height + "'>\n";
		return transformedElement;
	}
	
	private String[] parseWidthHeightSrcFromDocumentLine(String documentLine) {
		String[] parsedStringArray = documentLine.split("[=,\\]{}]");
		String[] widthHeightSrcArray = {parsedStringArray[1], parsedStringArray[3], parsedStringArray[5]};
		return widthHeightSrcArray;
	}
	
	private void saveTransformedDocument(String transformedDocument) throws IOException {
		String filename = MainWindow.getInstance().getFilename();
		FileWriter fileWriter = new FileWriter(filename);
		fileWriter.write(transformedDocument);
		fileWriter.close();
	}
}
