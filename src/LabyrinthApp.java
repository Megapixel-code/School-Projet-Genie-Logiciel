import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.Group;
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
            PerfectMaze maze = new PerfectMaze(50,30,5); // Exemple size faudrait rajouter une interface pour choisir la taille
            while (!(maze.bfs_next_step())) {
            }
            generateMaze(labyrinthArea, maze);
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


        Scene scene = new Scene(root, 1200, 700);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Code Thomas ---------------------

    public int set_cell_size(Maze maze){
        int X = 1100 / maze.get_size()[0];
        int Y = 500 / maze.get_size()[1];
        if (X < Y){
            return X;
        }
        else{
            return Y;
        }
    }


    public void generateMaze(Pane pane, Maze maze) {
        // Code to generate Maze 
        Group MazeGroup = new Group();
        pane.getChildren().clear();
        // CELL_SIZE doit être défini en fonction de la taille du labyrinthe
        int CELL_SIZE = 30;
        CELL_SIZE = this.set_cell_size(maze);
        Line top =new Line(0,0,maze.get_size()[0]*CELL_SIZE,0);
        Line left =new Line(0,0,0,maze.get_size()[1]*CELL_SIZE);
        MazeGroup.getChildren().add(top);
        MazeGroup.getChildren().add(left);
        for(int x=0; x<maze.get_size()[0]; x++){
            for(int y=0; y<maze.get_size()[1]; y++){
                if(maze.in_edge_list(maze.get_node(x, y), maze.get_node(x+1, y))==1){
                    Line line = new Line((x+1)*CELL_SIZE, (y+0)*CELL_SIZE, (x+1)*CELL_SIZE, (y+1)*CELL_SIZE);
                    //line.setStyle("-fx-stroke: red;");
                    MazeGroup.getChildren().add(line);
                }
                if(maze.in_edge_list(maze.get_node(x, y), maze.get_node(x, y+1))==1){
                    Line line = new Line((x+0)*CELL_SIZE, (y+1)*CELL_SIZE, (x+1)*CELL_SIZE, (y+1)*CELL_SIZE);
                    //line.setStyle("-fx-stroke: red;");
                    MazeGroup.getChildren().add(line);
                }
            }
        }
        double mazeWidth = maze.get_size()[0] * CELL_SIZE;
        double mazeHeight = maze.get_size()[1] * CELL_SIZE;

        MazeGroup.layoutXProperty().bind(pane.widthProperty().subtract(mazeWidth).divide(2));
        MazeGroup.layoutYProperty().bind(pane.heightProperty().subtract(mazeHeight).divide(2));

        pane.getChildren().add(MazeGroup);
    } 


    // Code Thomas ---------------------

    public static void main(String[] args) {
        launch(args); 
    }
}
 