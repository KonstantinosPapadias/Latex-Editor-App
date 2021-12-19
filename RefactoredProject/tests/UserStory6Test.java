import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.LatexEditorController;
import model.Document;
import model.DocumentManager;
import model.VersionsManager;
import model.strategies.VolatileVersionsStrategy;
import view.MainWindow;

class UserStory6Test {
	private DocumentManager documentManager;
	private VersionsManager versionsManager;
	private LatexEditorController latexEditorController;
	private MainWindow mainWindow;
	private final String[] VERSIONS_MANAGEMENT_TYPES = {"volatile", "stable"};
	private final String[] TEXT_EDITIONS = {"1 time edited text", "2 times edited text"};
	
	@BeforeEach
	void setup() {
		documentManager = DocumentManager.getInstance();
		// for some reason the test does not pass when run together with all the other test cases 
		// in the default package if versionsManager is initiated with VersionsManager.getInstance(), 
		// maybe some static dependency with the other test cases
		versionsManager = new VersionsManager(new VolatileVersionsStrategy());
		latexEditorController = new LatexEditorController();
		documentManager.setCurrentDocument(new Document("emptyTemplate"));
		latexEditorController.enact("create");
		mainWindow = new MainWindow(latexEditorController);
	}
	
	@Test
	void testDisableVersionsManagement() {
		for (String strategyType : VERSIONS_MANAGEMENT_TYPES) {
			enableAndDisableVersionsManagement(strategyType);
			editDocument2Times();
			checkEmptyDocumentHistory();
		}
	}
	
	private void enableAndDisableVersionsManagement(String strategyType) {
		versionsManager.setStrategyType(strategyType);
		latexEditorController.enact("enableVersionsManagement");
		latexEditorController.enact("disableVersionsManagement");
	}
	
	private void editDocument2Times() {
		for (String text : TEXT_EDITIONS) {
			mainWindow.setCurrentMainWindowText(text);
			latexEditorController.enact("edit");
		}
	}
	
	private void checkEmptyDocumentHistory() {
		List<Document> documents = versionsManager.getStrategy().getEntireHistory();
		for (Document document : documents)
			System.out.println(document.getContents());
		assertEquals(0, documents.size());
	}
}
