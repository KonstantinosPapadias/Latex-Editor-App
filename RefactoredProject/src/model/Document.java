package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Document {
	private String author;
	private String date;
	private String copyright;
	private String versionID = "0";
	private String contents;
	private String type;
	
	public Document(String author, String date, String copyright, String versionID, String contents, String type) {
		this.author = author;
		this.date = date;
		this.copyright = copyright;
		this.versionID = versionID;
		this.contents = contents;
		this.type = type;
	}
	
	public Document(String type) {
		contents = "";
		this.type = type;
	}
	
	
	public Document() {
		// TODO Auto-generated constructor stub
		this.contents = "";
	}


	public String getContents() {
		return contents;
	}


	public void setContents(String contents) {
		this.contents = contents;
	}


	public void save(String filename) {
		try {
			PrintWriter printWriter = new PrintWriter(new FileOutputStream(filename));
			
			printWriter.write(contents);
			printWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Document clone() {
		return new Document(author, date, copyright, versionID, contents, type);
	}

	public void changeVersion() {
		int n = Integer.parseInt(versionID);
		versionID = (n + 1) + "";
	}


	public String getVersionID() {
		// TODO Auto-generated method stub
		return versionID;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
}
