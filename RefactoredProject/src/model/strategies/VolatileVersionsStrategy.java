package model.strategies;

import java.util.ArrayList;
import java.util.List;

import model.Document;

public class VolatileVersionsStrategy implements VersionsStrategy {
	private ArrayList<Document> history;
	
	public VolatileVersionsStrategy() {
		super();
		history = new ArrayList<Document>();
	}

	@Override
	public void putVersion(Document document) {
		Document doc = document.clone();
		history.add(doc);
	}

	@Override
	public Document getVersion() {
		if(history.size() == 0)
			return null;
		return history.get(history.size() - 1);
	}

	@Override
	public void setEntireHistory(List<Document> documents) {
		history.clear();
		history.addAll(documents);
	}

	@Override
	public List<Document> getEntireHistory() {
		return history;
	}

	@Override
	public void removeVersion() {
		history.remove(history.size() - 1);
	}
}
