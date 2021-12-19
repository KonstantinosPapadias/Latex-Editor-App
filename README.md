# Description
The goal of this project was to refactor the Latex editor app code and extend its features.

## Technologies used:
* Java
* Java Swing

## Code extensions
The original Latex editor app enabled the user to:
* Create a new Latex document, based on 4 different templates, or empty.
* Edit the document.
* Add Latex commands to the document.
* Activate an automatic version tracking mechanism, to save the history of document changes and rollback to an older version of the document, when editing it's contents.
* Save the document.
* Load a document.

I added the following code extensions:
* The user can now save a Latex document as HTML. The app will automatically transform the Latex document to HTML, so the user can look at it's contents in a browser.
* The user can now load an HTML document as Latex. The app will automatically transform the HTML file to a Latex document, so the user can edit it's contents.

## Refactoring
The original Latex editor app had the following problems:
1. The LatexEditorController class had a lot of duplicate code to populate the comands hashmap. So i used the Substitute Algorithm refactoring to remove the duplicate code.

2. The Command classes (controller.commands package) had private fields for the documentManager and versionsManager objects. I provided a global access point to these classes and the MainWindow class (i used a reference in a mainWindow object in the AddLatexCommand class after the refactoring), using the Singleton pattern, so as to remove the need for fields in the command classes.

3. The DocumentManager class had the same problem with the LatexEditorController duplicate code to populate a hashmap too. So i used the same refactoring (Substitute Algorithm refactoring) to remove the duplicate code.

4. The VersionsManager class had a lot of dead code (methods that weren't used anywhere), that i removed. The class, also, had a lot of chain methods that simply called corresponding methods of other classes. So i used the Remove Middle Man refactoring, to get rid of this problem.

5. There was a LatexEditorView class in the views package, that had business logic methods, related to saving and loading documents. So i used the Move Method and Move Field refactorings, to redistribute the responsibilities of the class to the right Command classes. The class was also removed, as it had no purpose anymore.

6. The MainWindow class contained a business logic method to edit the contents of a document with a Latex command. So i moved this method to the AddLatexCommand class, using the Move Method refactoring. This method had also a lot of duplicate code, that i removed, using the Substitute Algorithm refactoring.

# Instructions
The RefactoredProject folder contains the refactored Latex editor app as an Eclipse project. You can install the project in Eclipse and run the app from the src folder, that contains the source code of the app.

* To create a new document - **Create new Document -> Create**
* To edit a document use the editor and **File -> Save**
* To load a document - **File -> Load file**
* To save a document - **File -> Save file**
* To load an html file and transform it to a Latex document - **File -> Load from HTML**
* To transform a Latex document to HTML and save it - **File -> Save as HTML**
* To add a Latex command to a document - **Commands -> Add <command_name>**
* To enable the document version tracking mechanism when editing the document (Volatile to track the document changes in memory, Stable to track the changes in the disk) - **Strategy -> Enable -> Volatile/Stable**
* To disable the document version tracking mechanism - **Strategy -> Disable**
* To rollback to a previous version of the document, when editing it - **Strategy -> Rollback**

## Testing
The tests folder contains the JUnit test cases i created for the project, one test case for each user story of the final version of the app. Run the tests to ensure the app functionality is there (you should run the UserStory8Test before the UserStory9Test, so the output file of the 8th user story test will be the input file of 9th user story test).

## Architecture
The uml folder contains uml class diagrams for each package of the refactored Latex editor app.

