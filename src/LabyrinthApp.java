import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
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
       
        // ############################################### btn taille laby ###############################################

        TextField widthField = new TextField("50");
        widthField.setPromptText("Largeur");
        widthField.setMaxWidth(70);

        TextField heightField = new TextField("30");
        heightField.setPromptText("Hauteur");
        heightField.setMaxWidth(70);

        Button validateSizeButton = new Button("Valdate Size");

        final int[] mazeWidth = {50};

        final int[] mazeHeight = {30};

        validateSizeButton.setOnAction(e -> {
            try {
                mazeWidth[0] = Integer.parseInt(widthField.getText());
                mazeHeight[0] = Integer.parseInt(heightField.getText());
                System.out.println("Nouvelle taille : " + mazeWidth[0] + " x " + mazeHeight[0]);
            } catch (NumberFormatException ex) {
                System.out.println("Veuillez entrer des nombres valides.");
            }
        });

        // ############################################### btn 1 ###############################################

        Button generatePerfecLabyrinth = new Button("Generate Perfect Labyrinth");
        generatePerfecLabyrinth.setOnAction(e -> {
            generatedLabyrinth = "Perfect Labyrinth"; 
            System.out.println("Generating perfect labyrinth");
            Node startNode = new Node(0, 0);
            Node endNode = new Node(mazeWidth[0] - 1, mazeHeight[0] - 1);   // Faire un bouton pour choisir le début et la fin du labyrinthe
            PerfectMaze maze = new PerfectMaze(mazeWidth[0], mazeHeight[0], 5, startNode, endNode); // Exemple size faudrait rajouter une interface pour choisir la taille | Done !
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


        // ############################################### btn suivant / précédent ###############################################
        
        Button previousButton = new Button("⟵ Previous");

        Button nextButton = new Button("Next ⟶");

        previousButton.setOnAction(e -> {
            System.out.println("Previous button clicked");
            buttonBox.getChildren().removeAll(previousButton, resolveButton, resolutionMethods);
            buttonBox.getChildren().addAll(widthField, heightField, validateSizeButton, generatePerfecLabyrinth, generateImperfecLabyrinth, nextButton);
        });

        nextButton.setOnAction(e -> {
            System.out.println("Next button clicked");
            buttonBox.getChildren().removeAll(widthField, heightField, validateSizeButton, generatePerfecLabyrinth, generateImperfecLabyrinth, nextButton);
            buttonBox.getChildren().addAll(previousButton, resolveButton, resolutionMethods);
        });

        buttonBox.getChildren().addAll(widthField, heightField, validateSizeButton, generatePerfecLabyrinth, generateImperfecLabyrinth, nextButton);


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

    // ca il faut que je puisse l'appeler mais faut pas le mettre ici 

    public void generateMaze(Pane pane, Maze maze) { 
        // Code to generate Maze 
        Group MazeGroup = new Group();
        pane.getChildren().clear();
        // CELL_SIZE doit être défini en fonction de la taille du labyrinthe
        int CELL_SIZE = 30;
        CELL_SIZE = this.set_cell_size(maze);
        int LINE_SIZE = 1;
        if(CELL_SIZE/8 > 0){
            LINE_SIZE = CELL_SIZE / 5;
        }
        Line top =new Line(0,0,maze.get_size()[0]*CELL_SIZE,0);
        Line left =new Line(0,0,0,maze.get_size()[1]*CELL_SIZE);
        top.getStyleClass().add("wall");
        left.getStyleClass().add("wall");
        top.setStrokeWidth(LINE_SIZE);
        left.setStrokeWidth(LINE_SIZE);
        MazeGroup.getChildren().add(top);
        MazeGroup.getChildren().add(left);
        for(int x=0; x<maze.get_size()[0]; x++){
            for(int y=0; y<maze.get_size()[1]; y++){
                Line lineVertical = new Line((x+1)*CELL_SIZE, (y+0)*CELL_SIZE, (x+1)*CELL_SIZE, (y+1)*CELL_SIZE);
                lineVertical.setStrokeWidth(LINE_SIZE);
                Node node1 = maze.get_node(x, y);
                Node node2 = maze.get_node(x+1, y);
                lineVertical.setOnMouseClicked(event -> lineClicked(pane, maze, node1, node2));
                MazeGroup.getChildren().add(lineVertical);
                if(maze.in_edge_list(node1, node2)==-1){
                    lineVertical.getStyleClass().add("wall");
                }
                else{
                    lineVertical.getStyleClass().add("path");
                }
                Line lineHorizontal = new Line((x+0)*CELL_SIZE, (y+1)*CELL_SIZE, (x+1)*CELL_SIZE, (y+1)*CELL_SIZE);
                lineHorizontal.setStrokeWidth(LINE_SIZE);
                Node node3 = maze.get_node(x, y);
                Node node4 = maze.get_node(x, y+1);
                lineHorizontal.setOnMouseClicked(event -> lineClicked(pane, maze, node3, node4));
                MazeGroup.getChildren().add(lineHorizontal);
                if(maze.in_edge_list(node3, node4)==-1){
                    lineHorizontal.getStyleClass().add("wall");
                }
                else{
                    lineHorizontal.getStyleClass().add("path");
                }
            }
        }
        double mazeWidth = maze.get_size()[0] * CELL_SIZE;
        double mazeHeight = maze.get_size()[1] * CELL_SIZE;

        MazeGroup.layoutXProperty().bind(pane.widthProperty().subtract(mazeWidth).divide(2));
        MazeGroup.layoutYProperty().bind(pane.heightProperty().subtract(mazeHeight).divide(2));

        // Add Start and End Nodes
        int[] startNodeCoord = maze.get_start_node().get_coordinates();
        int[] endNodeCoord = maze.get_end_node().get_coordinates();
        Circle startNodeCircle = new Circle((startNodeCoord[0] + 0.5) * CELL_SIZE, (startNodeCoord[1] + 0.5) * CELL_SIZE, CELL_SIZE / 3);
        startNodeCircle.setStyle("-fx-fill: green;");
        Circle endNodeCircle = new Circle((endNodeCoord[0] + 0.5) * CELL_SIZE, (endNodeCoord[1] + 0.5) * CELL_SIZE, CELL_SIZE / 3);
        endNodeCircle.setStyle("-fx-fill: red;");
        MazeGroup.getChildren().addAll(startNodeCircle, endNodeCircle);

        pane.getChildren().add(MazeGroup);
    } 

    public void lineClicked(Pane pane, Maze maze, Node node1, Node node2) {
        if (node1 != null && node2 != null) {
            if (maze.in_edge_list(node1, node2) >= 0) {
                maze.remove_edge(maze.get_edge(node1, node2));
                System.out.println("Wall added");
            } 
            else {
                Edge edge = new Edge(node1, node2);
                maze.add_edge(edge);
                System.out.println("Wall removed");
            }
        }
        else {
            System.out.println("Invalid coordinates for wall addition.");
        }
        generateMaze(pane, maze);
    }
    // Code Thomas ---------------------

    public static void main(String[] args) {
        launch(args); 
    }
}
 