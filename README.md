Hangman Game in JavaFx
Project in the Multimedia course of the NTUA ECE class of 2021-22.
 
Implementation design

The Hangman Game application implements all the prerequisites of the project.

The Java language was used both for the communication with the api of the website and
for the backend of the application. JavaFx was used for the graphical representation.

The implementation started by creating an interface with the api of the website
"openlibrary.org" and storing the useful data in a .txt file. Source for the code
was the corresponding example made in the "Movie Browser" course.
Then in parallel with the creation of the graphical interface (GUI) the
"controllers" and their corresponding fxml files.

The application contains :
- 7 class files (.java)
- 5 files (.fxml)
- Photo files (.jpg and .png)
- Text files (.txt)

Each .fxml file corresponds to a .java class file. which acts as the
fx:controller.
