javac --module-path "./School-Projet-Genie-Logiciel/javafx-sdk-17.0.15/lib" --add-modules javafx.controls,javafx.fxml -d bin src/*.java
if [ $? -ne 0 ]; then
  echo "Compilation failed"
  exit 1
fi

cp src/style.css bin/

java --module-path "./School-Projet-Genie-Logiciel/javafx-sdk-17.0.15/lib" --add-modules javafx.controls,javafx.fxml -cp bin LabyrinthApp
