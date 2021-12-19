import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.LatexEditorController;
import model.Document;
import model.DocumentManager;
import model.VersionsManager;
import view.MainWindow;

class UserStory7Test {
	private DocumentManager documentManager;
	private VersionsManager versionsManager;
	private LatexEditorController latexEditorController;
	private MainWindow mainWindow;
	private final String[] TEXT_EDITIONS = {"1 time edited text", "2 times edited text"};
	private final String[] VERSIONS_MANAGEMENT_TYPES = {"volatile", "stable"};
	
	@BeforeEach
	void setup() {
		documentManager = DocumentManager.getInstance();
		versionsManager = VersionsManager.getInstance();
		latexEditorController = new LatexEditorController();
		documentManager.setCurrentDocument(new Document("emptyTemplate"));
		latexEditorController.enact("create");
		mainWindow = new MainWindow(latexEditorController);
	}

	@Test
	void testRollbackToPreviousVersion() {
		for (String strategyType : VERSIONS_MANAGEMENT_TYPES) {
			editDocument2TimesAndRollback(strategyType);
			assertEquals("1 time edited text", documentManager.getCurrentDocument().getContents().trim());
		}
	}
	
	private void editDocument2TimesAndRollback(String strategyType) {
		versionsManager.setStrategyType(strategyType);
		latexEditorController.enact("enableVersionsManagement");
		editDocument2Times();
		latexEditorController.enact("rollbackToPreviousVersion");
	}
	
	private void editDocument2Times() {
		for (String text : TEXT_EDITIONS) {
			mainWindow.setCurrentMainWindowText(text);
			latexEditorController.enact("edit");
		}
	}
}
