#!/usr/bin/env bash

javac --module-path "./linux-javafx-sdk-17.0.15/lib" --add-modules javafx.controls,javafx.fxml -d bin src/*.java
if [ $? -ne 0 ]; then
  echo "Compilation failed"
  exit 1
fi

cp src/style.css bin/

java --module-path "./linux-javafx-sdk-17.0.15/lib" --add-modules javafx.controls,javafx.fxml -cp bin MainInteract
