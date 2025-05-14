import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Pos;

public class LabyrinthApp extends Application { 
    private String selectedMethod = null;
    private String generatedLabyrinth = null;

    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Labyrinth");

        BorderPane root = new BorderPane();

        Pane labyrinthArea = new Pane();
        labyrinthArea.getStyleClass().add("labyrinth-area");
        root.setCenter(labyrinthArea);

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getStyleClass().add("hbox");
        root.setBottom(buttonBox);
       
        // ############################################### btn 1 ###############################################

        Button generatePerfecLabyrinth = new Button("Generate Perfect Labyrinth");
        generatePerfecLabyrinth.setOnAction(e -> {
            generatedLabyrinth = "Perfect Labyrinth"; 
            System.out.println("Generating perfect labyrinth");
        });

        // ############################################### btn 2 ###############################################

        Button generateImperfecLabyrinth = new Button("Generate Imperfect Labyrinth");
        generateImperfecLabyrinth.setOnAction(e -> {
            generatedLabyrinth = "Imperfect Labyrinth"; 
            System.out.println("Generating imperfect labyrinth");
        });

        // ############################################### btn 4 ###############################################

        Button resolveButton = new Button("Resolve Labyrinth");
        resolveButton.setOnAction(e -> {
            if (selectedMethod != null && generatedLabyrinth != null) {
                System.out.println("Labyrinth resolution using " + selectedMethod + " method...");
                // mettre résoudre le labyrinthe ici !! il faut 
                //que le labyrinthe soit généré avant + faire attention à la méthgode de resolution
            } else if (generatedLabyrinth != null) {
                System.out.println("Please select a resolution method first.");
            } else if (selectedMethod != null) {
                System.out.println("Please generate a labyrinth first.");
            } else {
                System.out.println("Please select a resolution method and generate a labyrinth first.");
            }
        });

        // ############################################### btn 3 ###############################################

        ComboBox<String> resolutionMethods = new ComboBox<>();
        resolutionMethods.getItems().addAll("DFS", "BFS", "Dijkstra", "A*");
        resolutionMethods.setPromptText("Select Resolution Method");
        resolutionMethods.setOnAction(e -> {
            selectedMethod = resolutionMethods.getValue();
            // mettre code pour résoudre le labyrinthe ici
            System.out.println("Selected method: " + selectedMethod);
        });
        
        
        buttonBox.getChildren().addAll(generatePerfecLabyrinth, generateImperfecLabyrinth, resolveButton, resolutionMethods);


        Scene scene = new Scene(root, 1800, 900);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); 
    }
}
 