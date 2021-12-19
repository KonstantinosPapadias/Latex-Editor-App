package model;

import java.util.HashMap;
import java.util.Map;

public class DocumentManager {
	private HashMap<String, Document> templates;
	private static DocumentManager documentManagerInstance = null;
	private Document currentDocument;
	private static Map<String, String> TEMPLATE_CONTENTS = Map.of(
				"reportTemplate", 
				"\\documentclass[11pt,a4paper]{report}\n\n"+
				"\\begin{document}\n"+
				"\\title{Report Template: How to Structure a LaTeX Document}\n"+
				"\\author{Author1 \\and Author2 \\and ...}\n"+
				"\\date{\\today}\n"+
				"\\maketitle\n\n"+
				"\\begin{abstract}\n"+
				"Your abstract goes here...\n"+
				"...\n"+
				"\\end{abstract}\n\n"+
				"\\chapter{Introduction}\n"+
				"\\section{Section Title 1}\n"+
				"\\section{Section Title 2}\n"+
				"\\section{Section Title.....}\n\n"+
				"\\chapter{....}\n\n"+
				"\\chapter{Conclusion}\n\n\n"+
				"\\chapter*{References}\n\n"+
				"\\end{document}\n",
				
				"bookTemplate", 
				"\\documentclass[11pt,a4paper]{book}\n\n"+
				"\\begin{document}\n"+
				"\\title{Book: How to Structure a LaTeX Document}\n"+
				"\\author{Author1 \\and Author2 \\and ...}\n"+
				"\\date{\\today}\n\n"+
				"\\maketitle\n\n"+
				"\\frontmatter\n\n"+
				"\\chapter{Preface}\n"+
				"% ...\n\n"+
				"\\mainmatter\n"+
				"\\chapter{First chapter}\n"+
				"\\section{Section Title 1}\n"+
				"\\section{Section Title 2}\n\n"+
				"\\section{Section Title.....}\n\n"+
				"\\chapter{....}\n\n"+
				"\\chapter{Conclusion}\n\n"+
				"\\chapter*{References}\n\n\n"+
				"\\backmatter\n"+
				"\\chapter{Last note}\n\n"+
				"\\end{document}\n",
				
				"articleTemplate",
				"\\documentclass[11pt,twocolumn,a4paper]{article}\n\n"+
				"\\begin{document}\n"+
				"\\title{Article: How to Structure a LaTeX Document}\n"+
				"\\author{Author1 \\and Author2 \\and ...}\n"+
				"\\date{\\today}\n\n"+
				"\\maketitle\n\n"+
				"\\section{Section Title 1}\n\n"+
				"\\section{Section Title 2}\n\n"+
				"\\section{Section Title.....}\n\n"+
				"\\section{Conclusion}\n\n"+
				"\\section*{References}\n\n"+
				"\\end{document}\n",
				
				"letterTemplate",
				"\\documentclass{letter}\n"+
				"\\usepackage{hyperref}\n"+
				"\\signature{Sender's Name}\n"+
				"\\address{Sender's address...}\n"+
				"\\begin{document}\n\n"+
				"\\begin{letter}{Destination address....}\n"+
				"\\opening{Dear Sir or Madam:}\n\n"+
				"I am writing to you .......\n\n\n"+
				"\\closing{Yours Faithfully,}\n"+
				"\\ps\n\n"+
				"P.S. text .....\n\n"+
				"\\encl{Copyright permission form}\n\n"+
				"\\end{letter}\n"+
				"\\end{document}\n",
				
				"emptyTemplate",
				""
			);
	
	private DocumentManager() {
		templates = new HashMap<String, Document>();
		for (Map.Entry<String, String> template : TEMPLATE_CONTENTS.entrySet()) {
			String documentType = template.getKey();
			String documentContents = template.getValue();
			templates.put(documentType, createNewDocument(documentType, documentContents));
		}
	}
	
	public static DocumentManager getInstance() {
		if (documentManagerInstance == null)
			documentManagerInstance = new DocumentManager();
		return documentManagerInstance;
	}
	
	public Document createDocument(String type) {
		return templates.get(type).clone();
	}
	
	public Document getCurrentDocument() {
		return currentDocument;
	}
	
	public void setCurrentDocument(Document currentDocument) {
		this.currentDocument = currentDocument;
	}
	
	private Document createNewDocument(String documentType, String documentContents) {
		Document newDocument = new Document(documentType);
		newDocument.setContents(documentContents);
		return newDocument;
	}
}
