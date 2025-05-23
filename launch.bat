"C:\Program Files\Java\jdk-21\bin\javac.exe" --module-path "..\School-Projet-Genie-Logiciel\javafx-sdk-17.0.15\lib" --add-modules javafx.controls,javafx.fxml -d bin src\*.java
if errorlevel 1 goto error
copy /Y src\style.css bin\

"C:\Program Files\Java\jdk-21\bin\java.exe" --module-path "..\School-Projet-Genie-Logiciel\javafx-sdk-17.0.15\lib" --add-modules javafx.controls,javafx.fxml -cp bin LabyrinthApp
goto end

:error
echo Compilation failed

:end
