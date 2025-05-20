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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Random;

import javafx.animation.PauseTransition;
import javafx.util.Duration;


public class LabyrinthApp extends Application {
    private String selectedMethod = null;
    private String selectedGLMethod = null;
    private String generatedLabyrinth = null;
    private Maze CurrentMaze = null;
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

        TextField widthField = new TextField("15");
        widthField.setPromptText("Largeur");
        widthField.setMaxWidth(70);

        TextField heightField = new TextField("15");
        heightField.setPromptText("Hauteur");
        heightField.setMaxWidth(70);

        Button validateSizeButton = new Button("Validate Size");

        final int[] mazeWidth = {15};
        final int[] mazeHeight = {15};


        validateSizeButton.setOnAction(e -> {
            try {
                mazeWidth[0] = Integer.parseInt(widthField.getText());
                mazeHeight[0] = Integer.parseInt(heightField.getText());
                System.out.println("Nouvelle taille : " + mazeWidth[0] + " x " + mazeHeight[0]);
            } catch (NumberFormatException ex) {
                System.out.println("Veuillez entrer des nombres valides.");
            }
        });

        // ############################################### btn generate Labyrinth ###############################################

        Button generateLabyrinth = new Button("Generate Labyrinth");
        generateLabyrinth.setOnAction(e -> {
            if (selectedGLMethod == null) {
                System.out.println("Please select a generation method first.");
            } else if (selectedGLMethod == "perfect maze") {
                generatedLabyrinth = "Perfect Labyrinth";
                System.out.println("Generating perfect labyrinth");
                int[] end = {mazeWidth[0]-1, mazeHeight[0]-1};
                int[] start = {0, 0};
                Random random = new Random();
                PerfectMaze maze = new PerfectMaze(mazeWidth[0], mazeHeight[0], random.nextInt(), start, end);
                GenerateComplete(labyrinthArea, maze);
                CurrentMaze = maze;
            } else if (selectedGLMethod == "Imperfect maze") {
                // Code to generate imperfect maze
                generatedLabyrinth = "Imperfect Labyrinth";
            } else if (selectedGLMethod == "Step-by-step perfect maze"){
                generatedLabyrinth = "Perfect Labyrinth";
                System.out.println("Generating perfect labyrinth step by step");
                int[] end = {mazeWidth[0]-1, mazeHeight[0]-1};
                int[] start = {0, 0};
                Random random = new Random();
                PerfectMaze maze = new PerfectMaze(mazeWidth[0], mazeHeight[0], random.nextInt(), start, end);

                GenerateStepByStep(labyrinthArea, maze);
                CurrentMaze = maze;
            } else {
                // Code to generate imperfect maze step by step
                generatedLabyrinth = "Imperfect Labyrinth";

            }
        });

        // ############################################### btn 2 ###############################################

        ComboBox<String> generationMethods = new ComboBox<>();
        generationMethods.getItems().addAll("perfect maze", "Step-by-step perfect maze", "Imperfect maze", "Step-by-step imperfect maze");
        generationMethods.setPromptText("Select Generation Method");
        generationMethods.setOnAction(e -> {
            selectedGLMethod = generationMethods.getValue();
            // mettre code pour résoudre le labyrinthe ici
            System.out.println("Selected method: " + selectedGLMethod);
        });

        // ############################################### btn 4 ###############################################

        Button resolveButton = new Button("Resolve Labyrinth");
        resolveButton.setOnAction(e -> {
            if (selectedMethod != null && generatedLabyrinth != null) {
                System.out.println("Labyrinth resolution using " + selectedMethod + " method...");
                // mettre résoudre le labyrinthe ici !! il faut
                //que le labyrinthe soit généré avant + faire attention à la méthgode de resolution
                if(selectedMethod.equals("DFS")) {
                    System.out.println("Resolving using DFS");
                    // Code to resolve labyrinth using DFS
                } else if (selectedMethod.equals("BFS")) {
                    System.out.println("Resolving using BFS");
                    // Code to resolve labyrinth using BFS
                    if(Solver.bfs(CurrentMaze)){
                        System.out.println("Path found");
                    }
                    else{
                        System.out.println("No path found");
                    }
                    generateWay(labyrinthArea, CurrentMaze);
                } else if (selectedMethod.equals("Dijkstra")) {
                    System.out.println("Resolving using Dijkstra");
                    // Code to resolve labyrinth using Dijkstra
                } else if (selectedMethod.equals("A*")) {
                    System.out.println("Resolving using A*");
                    // Code to resolve labyrinth using A*
                }
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

        // ############################################### btn sauvegarder ###############################################

        Button saveLaby = new Button("Save Labyrinth");
        saveLaby.setOnAction(e -> {
            System.out.println("Labyrinth saved");
            // Code to save the labyrinth
        });

        // ############################################### btn charger ###############################################
        TextField seedField = new TextField();
        seedField.setPromptText("Seed du labyrinthe");
        seedField.setMaxWidth(120);

        Button validateSeedButton = new Button("Valider");
        validateSeedButton.setOnAction(e -> {
            String seedText = seedField.getText();
            try {
                int seed = Integer.parseInt(seedText);
                System.out.println("Labyrinth loaded with seed: " + seed);
                // Code pour charger le labyrinthe avec la seed ici
                // Par exemple :
                // PerfectMaze maze = new PerfectMaze(mazeWidth[0], mazeHeight[0], seed, start, end);
                // generateMaze(labyrinthArea, maze);
            } catch (NumberFormatException ex) {
                System.out.println("Veuillez entrer une seed valide (nombre entier).");
            }
        });

        // ############################################### btn suivant / précédent ###############################################

        Button previousButton = new Button("⟵ Previous");

        Button generatePageButton = new Button("Generate Labyrinth ⟶");

        Button loadPageButton = new Button("⟵ Load Labyrinth");

        Button nextButton = new Button("Next ⟶");

        loadPageButton.setOnAction(e -> {
            System.out.println("Load labyrinth button clicked");
            buttonBox.getChildren().removeAll(loadPageButton, widthField, heightField, validateSizeButton, generationMethods, generateLabyrinth, nextButton);
            buttonBox.getChildren().addAll(seedField, validateSeedButton, generatePageButton);
        });

        generatePageButton.setOnAction(e -> {
            System.out.println("Generate labyrinth button clicked");
            buttonBox.getChildren().removeAll(seedField, validateSeedButton, generatePageButton);
            buttonBox.getChildren().addAll(loadPageButton, widthField, heightField, validateSizeButton, generationMethods, generateLabyrinth, nextButton);
        });

        previousButton.setOnAction(e -> {
            System.out.println("Previous button clicked");
            buttonBox.getChildren().removeAll(previousButton, resolveButton, resolutionMethods, saveLaby);
            buttonBox.getChildren().addAll(loadPageButton, widthField, heightField, validateSizeButton, generateLabyrinth, generationMethods, nextButton);
        });

        nextButton.setOnAction(e -> {
            System.out.println("Next button clicked");
            buttonBox.getChildren().removeAll(loadPageButton, widthField, heightField, validateSizeButton, generateLabyrinth, generationMethods, nextButton);
            buttonBox.getChildren().addAll(previousButton, resolveButton, resolutionMethods, saveLaby);
        });

        buttonBox.getChildren().addAll(loadPageButton, widthField, heightField, validateSizeButton, generateLabyrinth, generationMethods, nextButton);


        Scene scene = new Scene(root, 1300, 720);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
//####################################################################################################################################
//####################################################################################################################################
//####################################################################################################################################


    // Code Thomas ---------------------

    public void GenerateStepByStep(Pane labyrinthArea, PerfectMaze maze) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.05));
        pause.setOnFinished(event -> {
            boolean end = maze.bfs_next_step();
            generateMaze(labyrinthArea, maze);

            if (!end) {
                GenerateStepByStep(labyrinthArea, maze);
            }
        });
        pause.play();
    }

    public void GenerateComplete(Pane labyrinthArea, PerfectMaze maze) {
        while (!(maze.bfs_next_step())) {}
        generateMaze(labyrinthArea, maze);
    }

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
        BooleanProperty ChangeStartNode = new SimpleBooleanProperty(false);
        BooleanProperty ChangeEndNode = new SimpleBooleanProperty(false);
        for(int x=0; x<maze.get_size()[0]; x++){
            for(int y=0; y<maze.get_size()[1]; y++){
                // Draw Start and End Nodes
                Circle NodeCircle = new Circle((x + 0.5) * CELL_SIZE, (y + 0.5) * CELL_SIZE, CELL_SIZE / 3);
                MazeGroup.getChildren().add(NodeCircle);
                Node changeNode = maze.get_node(x, y);
                if(maze.getStartNode().get_coordinates()[0] == x && maze.getStartNode().get_coordinates()[1] == y){
                    NodeCircle.getStyleClass().add("start");
                    NodeCircle.setOnMouseClicked(event -> ChangeStartNode.set(StartNodeClicked(maze, ChangeEndNode, NodeCircle)));
                }
                else if(maze.getEndNode().get_coordinates()[0] == x && maze.getEndNode().get_coordinates()[1] == y){
                    NodeCircle.getStyleClass().add("end");
                    NodeCircle.setOnMouseClicked(event -> ChangeEndNode.set(EndNodeClicked(maze, ChangeStartNode, NodeCircle)));
                }
                else{
                    NodeCircle.getStyleClass().add("node");
                    NodeCircle.setOnMouseClicked(event -> NodeClicked(pane, maze, ChangeStartNode, ChangeEndNode, changeNode));
                }
                // Draw vertical lines
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
                // Draw horizontal lines
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

    public Boolean StartNodeClicked(Maze maze, BooleanProperty ChangeEndNode,Circle c) {
        if (ChangeEndNode.getValue()) {
            System.out.println("End node already selected.");
            return false;
        }
        c.getStyleClass().remove("start");
        c.getStyleClass().add("inChange");
        System.out.println("Start node ready to change.");
        return true;
    }
    public Boolean EndNodeClicked(Maze maze, BooleanProperty ChangeStartNode,Circle c) {
        if (ChangeStartNode.getValue()) {
            System.out.println("Start node already selected.");
            return false;
        }
        c.getStyleClass().remove("end");
        c.getStyleClass().add("inChange");
        System.out.println("End node ready to change.");
        return true;
    }
    public void NodeClicked(Pane pane, Maze maze, BooleanProperty ChangeStartNode,BooleanProperty ChangeEndNode,Node node) {
        if (ChangeStartNode.getValue()) {
            maze.set_StartNode(node.get_coordinates()[0], node.get_coordinates()[1]);
            System.out.println("Start node changed to: " + node.get_coordinates()[0] + ", " + node.get_coordinates()[1]);
            generateMaze(pane, maze);
        }
        if (ChangeEndNode.getValue()) {
            maze.set_EndNode(node.get_coordinates()[0], node.get_coordinates()[1]);
            System.out.println("End node changed to: " + node.get_coordinates()[0] + ", " + node.get_coordinates()[1]);
            generateMaze(pane, maze);
        }
        else {
            System.out.println("To change the path, select the Start or the End before");
        }
    }

    public void generateWay(Pane pane, Maze maze) {
        pane.getChildren().clear();
        Group WayGroup = new Group();
        int CELL_SIZE = set_cell_size(maze);
        boolean[][] visited = new boolean[maze.get_size()[0]][maze.get_size()[1]];
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                visited[i][j] = false;
            }
        }
        visited[maze.getStartNode().get_coordinates()[0]][maze.getStartNode().get_coordinates()[1]] = true;
        drawWay(pane, maze,maze.getStartNode(),CELL_SIZE, WayGroup, visited);
        WayGroup.layoutXProperty().bind(pane.widthProperty().subtract(maze.get_size()[0] * CELL_SIZE).divide(2));
        WayGroup.layoutYProperty().bind(pane.heightProperty().subtract(maze.get_size()[1] * CELL_SIZE).divide(2));
        generateMaze(pane, maze);
        pane.getChildren().add(WayGroup);
        System.out.println("Way generated");
    }

    public void drawWay(Pane pane, Maze maze, Node CurrentNode,int CELL_SIZE, Group WayGroup, boolean[][] visited) {
        if(CurrentNode == null){
            return;
        }
        int[][] directions = {
            {1, 0},  // droite
            {0, 1},  // bas
            {-1, 0}, // gauche
            {0, -1}  // haut
        };
        int[] coordinates = CurrentNode.get_coordinates();
        for (int[] d : directions) {
            int nx = coordinates[0] + d[0];
            int ny = coordinates[1] + d[1];
        
            if (maze.get_node(nx, ny) != null &&
                maze.in_edge_list(CurrentNode, maze.get_node(nx, ny))>=0 &&
                !visited[nx][ny] &&
                maze.get_node(nx, ny).getMark() != null) {
                Circle WayCircle = new Circle((coordinates[0] + d[0] + 0.5) * CELL_SIZE,
                                              (coordinates[1] + d[1] + 0.5) * CELL_SIZE,
                                              CELL_SIZE / 7);
                Circle MiddleWayCircle = new Circle((coordinates[0] + 0.5 + d[0] * 0.5) * CELL_SIZE,
                                                    (coordinates[1] + 0.5 + d[1] * 0.5) * CELL_SIZE,
                                                    CELL_SIZE / 7);
                WayGroup.getChildren().add(WayCircle);
                WayGroup.getChildren().add(MiddleWayCircle);
        
                if(maze.get_node(nx, ny) == maze.getEndNode()){
                    WayGroup.getChildren().remove(WayCircle);
                    MiddleWayCircle.getStyleClass().add("way");
                }
                else if (maze.get_node(nx, ny).isPath()) {
                    WayCircle.getStyleClass().add("way");
                    MiddleWayCircle.getStyleClass().add("way");
                } 
                else {
                    WayCircle.getStyleClass().add("visited");
                    MiddleWayCircle.getStyleClass().add("visited");
                }
                visited[nx][ny] = true;
                drawWay(pane, maze, maze.get_node(nx, ny), CELL_SIZE, WayGroup, visited);
            }
        }
    }

    // Code Thomas ---------------------

    public static void main(String[] args) {
        launch(args);
    }
}