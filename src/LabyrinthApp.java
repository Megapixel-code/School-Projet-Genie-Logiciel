import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.Group;
import javafx.geometry.Pos;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Optional;
import java.util.Random;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * LabyrinthApp is the class to display the labyrinth using JavaFX and interact with the user.
 * It allows the user to generate and resolve labyrinths using different methods.
 * The labyrinth is displayed in a graphical interface and the user can interact with it using buttons and text fields.
 * The user can choose the size and seed of the labyrinth.
 * The Labyrinth can be modified by clicking on the walls and nodes.
 * The user can also save and load labyrinths from files.
 */
public class LabyrinthApp extends Application {
    private TextArea terminalArea;
    private String selectedMethod = null;
    private String selectedGLMethod = null;
    private String generatedLabyrinth = null;
    private Maze CurrentMaze = null;

    /**
     * Prints a message to the terminal area instead of the console
     * @param message
     */
    private void printToTerminal(String message) {
        terminalArea.appendText(message + "\n");
    }


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Labyrinth");

        BorderPane root = new BorderPane();


/**
 * Space for the labyrinth
 */
        Pane labyrinthArea = new Pane();
        labyrinthArea.getStyleClass().add("labyrinth-area");

/**
 * Space for the terminal
 */
        terminalArea = new TextArea();
        terminalArea.setEditable(false);
        terminalArea.setWrapText(true);
        terminalArea.setPrefWidth(300); // largeur fixe pour le terminal
        terminalArea.getStyleClass().add("terminal-area");

/**
 * Center box to hold the labyrinth and terminal
 * The labyrinth takes 75% of the width and the terminal takes 25%
 */
        HBox centerBox = new HBox();
        centerBox.getChildren().addAll(labyrinthArea, terminalArea);

        labyrinthArea.prefWidthProperty().bind(centerBox.widthProperty().multiply(0.75));
        labyrinthArea.prefHeightProperty().bind(centerBox.heightProperty());
        terminalArea.prefWidthProperty().bind(centerBox.widthProperty().multiply(0.25));
        terminalArea.prefHeightProperty().bind(centerBox.heightProperty());

        root.setCenter(centerBox);

        /**
         * Space for the buttons
         */
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getStyleClass().add("hbox");
        root.setBottom(buttonBox);


/**
 * TextFields for the size and seed of the labyrinth
 * The size and seed are stored in mazeWidth, mazeHeight and mazeSeed
 */
        TextField widthField = new TextField("15");
        widthField.setPromptText("Largeur");
        widthField.setMaxWidth(70);

        TextField heightField = new TextField("15");
        heightField.setPromptText("Hauteur");
        heightField.setMaxWidth(70);

        TextField seedField = new TextField();
        seedField.setPromptText("Seed");
        seedField.setMaxWidth(150);

        final int[] mazeWidth = {15};
        final int[] mazeHeight = {15};
        final int[] mazeSeed = {0};


/**
 * Button to validate the size and seed of the labyrinth
 * The size and seed are stored in mazeWidth, mazeHeight and mazeSeed
 */
        Button validateButton = new Button("Validate Size/Seed");

        validateButton.setOnAction(e -> {
            try {
                mazeWidth[0] = Integer.parseInt(widthField.getText());
                mazeHeight[0] = Integer.parseInt(heightField.getText());
                printToTerminal("Nouvelle taille : " + mazeWidth[0] + " x " + mazeHeight[0]);
            } catch (NumberFormatException ex) {
                printToTerminal("Veuillez entrer des nombres valides.");
            }
            String seedText = seedField.getText();
            if (seedText == null || seedText.trim().isEmpty()) {
                mazeSeed[0] = new Random().nextInt();
                printToTerminal("Aucune seed fournie. Utilisation d'une seed aléatoire : " + mazeSeed[0]);
            } else {
                try {
                    mazeSeed[0] = Integer.parseInt(seedText);
                    printToTerminal("Labyrinth loaded with seed: " + mazeSeed[0]);
                } catch (NumberFormatException ex) {
                    printToTerminal("Veuillez entrer une seed valide (nombre entier).");
                }
            }

        });

/**
 * Button to generate the labyrinth
 * The labyrinth is generated using the selected method
 */
        Button generateLabyrinth = new Button("Generate Labyrinth");
        generateLabyrinth.setOnAction(e -> {
            if (selectedGLMethod == null) {
                printToTerminal("Please select a generation method first.");
            } else if (selectedGLMethod == "perfect maze") {
                generatedLabyrinth = "Perfect Labyrinth";
                printToTerminal("Generating perfect labyrinth");
                int[] end = {mazeWidth[0]-1, mazeHeight[0]-1};
                int[] start = {0, 0};
                PerfectMaze maze = new PerfectMaze(mazeWidth[0], mazeHeight[0], mazeSeed[0], start, end);
                GenerateComplete(labyrinthArea, maze);
                CurrentMaze = maze;
            } else if (selectedGLMethod == "Imperfect maze") {
                // Code to generate imperfect maze
                int[] end = {mazeWidth[0]-1, mazeHeight[0]-1};
                int[] start = {0, 0};
                ImperfectMaze maze = new ImperfectMaze(mazeWidth[0], mazeHeight[0], mazeSeed[0], start, end);
                generateMaze(labyrinthArea, maze);
                CurrentMaze = maze;
                generatedLabyrinth = "Imperfect Labyrinth";
            } else if (selectedGLMethod == "Step-by-step perfect maze"){
                generatedLabyrinth = "Perfect Labyrinth";
                printToTerminal("Generating perfect labyrinth step by step");
                int[] end = {mazeWidth[0]-1, mazeHeight[0]-1};
                int[] start = {0, 0};
                PerfectMaze maze = new PerfectMaze(mazeWidth[0], mazeHeight[0], mazeSeed[0], start, end);
                GenerateStepByStep(labyrinthArea, maze);
                CurrentMaze = maze;
            } else {
                // Code to generate imperfect maze step by step
                generatedLabyrinth = "Perfect Labyrinth";
                printToTerminal("Generating perfect labyrinth with Kruskal");
                int[] end = {mazeWidth[0]-1, mazeHeight[0]-1};
                int[] start = {0, 0};
                PerfectMaze maze = new PerfectMaze(mazeWidth[0], mazeHeight[0], mazeSeed[0], start, end);
                maze.generateKruskal();
                generateMaze(labyrinthArea, maze);
                CurrentMaze = maze;
            }
        });

/**
 * ComboBox for choosing the generation method
 */
        ComboBox<String> generationMethods = new ComboBox<>();
        generationMethods.getItems().addAll("perfect maze", "Step-by-step perfect maze", "Imperfect maze", "Kruskal Perfect maze");
        generationMethods.setPromptText("Select Generation Method");
        generationMethods.setOnAction(e -> {
            selectedGLMethod = generationMethods.getValue();
            // mettre code pour résoudre le labyrinthe ici
            printToTerminal("Selected method: " + selectedGLMethod);
        });

/**
 * ComboBox for choosing the speed
 */
        ComboBox<String> speedBox = new ComboBox<>();
        speedBox.getItems().addAll("Slow", "Normal", "Fast", "Very Fast");
        speedBox.setValue("Normal");
        speedBox.setVisible(false);
        speedBox.setManaged(false);


/**
 * Button to resolve the labyrinth
 * The labyrinth is resolved using the selected method
 */
        Button resolveButton = new Button("Resolve Labyrinth");
        resolveButton.setOnAction(e -> {
            if (selectedMethod != null && selectedMethod.contains("Step-by-step")) {
                speedBox.setVisible(true);
                speedBox.setManaged(true);
            } else {
                speedBox.setVisible(false);
                speedBox.setManaged(false);
            }
            final double speedy;
            if (speedBox.isVisible()) {
                switch (speedBox.getValue()) {
                    case "Slow":
                        speedy = 0.15;
                        break;
                    case "Normal":
                        speedy = 0.05;
                        break;
                    case "Fast":
                        speedy = 0.01;
                        break;
                    case "Very Fast":
                        speedy = 0.005;
                        break;
                    default:
                        speedy = 0.025;
                }
            } else {
                speedy = 0.025;
            }

            if (selectedMethod == null && generatedLabyrinth == null){
                printToTerminal("Please select a resolution method and generate a labyrinth first.");
            } else if (selectedMethod == null) {
                printToTerminal("Please select a resolution method first.");
            } else if (generatedLabyrinth  == null) {
                printToTerminal("Please generate a labyrinth first.");
            } else {
                printToTerminal("Labyrinth resolution using " + selectedMethod + " method...");
                if(selectedMethod.equals("DFS")) {
                    // Code to resolve labyrinth using DFS
                    Solver solver = new Solver();
                    if(solver.dfs(CurrentMaze)){
                        printToTerminal("Path found");
                    }
                    else{
                        printToTerminal("No path found");
                    }
                    generateWay(labyrinthArea, CurrentMaze);
                    printToTerminal("Time taken: " + solver.get_time_ms() + " milliseconds");
                } else if (selectedMethod.equals("BFS")) {
                    // Code to resolve labyrinth using BFS
                    Solver solver = new Solver();
                    if(solver.bfs(CurrentMaze)){
                        printToTerminal("Path found");
                    }
                    else{
                        printToTerminal("No path found");
                    }
                    generateWay(labyrinthArea, CurrentMaze);
                    printToTerminal("Time taken: " + solver.get_time_ms() + " milliseconds");
                } else if (selectedMethod.equals("A*")) {
                    // Code to resolve labyrinth using A*
                    Solver solver = new Solver();
                    if(solver.aStar(CurrentMaze)){
                        printToTerminal("Path found");
                    }
                    else{
                        printToTerminal("No path found");
                    }
                    generateWay(labyrinthArea, CurrentMaze);
                    printToTerminal("Time taken: " + solver.get_time_ms() + " milliseconds");
                } else if(selectedMethod.equals("DFS Step-by-step")) {
                    // Code to resolve labyrinth using DFS
                    SolverSbS solver = new SolverSbS(CurrentMaze, "dfs");
                    GenerateStepByStepWayFirstStep(CurrentMaze, solver, labyrinthArea, speedy, () -> {
                        GenerateStepByStepWayLastStep(CurrentMaze, solver, labyrinthArea, speedy);
                    });

                } else if (selectedMethod.equals("BFS Step-by-step")) {
                    // Code to resolve labyrinth using BFS
                    SolverSbS solver = new SolverSbS(CurrentMaze, "bfs");
                    GenerateStepByStepWayFirstStep(CurrentMaze, solver, labyrinthArea, speedy, () -> {
                        GenerateStepByStepWayLastStep(CurrentMaze, solver, labyrinthArea, speedy);
                    });
                } else if (selectedMethod.equals("A* Step-by-step")) {
                    // Code to resolve labyrinth using A*
                    SolverSbS solver = new SolverSbS(CurrentMaze, "astar");
                    GenerateStepByStepWayFirstStep(CurrentMaze, solver, labyrinthArea, speedy, () -> {
                        GenerateStepByStepWayLastStep(CurrentMaze, solver, labyrinthArea, speedy);
                    });
                } else if (selectedMethod.equals("Dijkstra")) {
                    // Code to resolve labyrinth using Dijkstra
                    Solver solver = new Solver();
                    if(solver.dijkstra(CurrentMaze)){
                        printToTerminal("Path found");
                    }
                    else{
                        printToTerminal("No path found");
                    }
                    generateWay(labyrinthArea, CurrentMaze);
                    printToTerminal("Time taken: " + solver.get_time_ms() + " milliseconds");
                } else if (selectedMethod.equals("Dijkstra Step-by-step")) {
                    // Code to resolve labyrinth using Dijkstra
                    SolverSbS solver = new SolverSbS(CurrentMaze, "dijkstra");
                    GenerateStepByStepWayFirstStep(CurrentMaze, solver, labyrinthArea, speedy, () -> {
                        GenerateStepByStepWayLastStep(CurrentMaze, solver, labyrinthArea, speedy);
                    });
                } else if (selectedMethod.equals("WallFollowerLeft")) {
                    // Code to resolve labyrinth following the left wall
                    Solver solver = new Solver();
                    if(solver.aStar(CurrentMaze)){
                        printToTerminal("Path found");
                    }
                    else{
                        printToTerminal("No path found");
                    }
                    generateWay(labyrinthArea, CurrentMaze);
                    printToTerminal("Time taken: " + solver.get_time_ms() + " milliseconds");
                } else if (selectedMethod.equals("WallFollowerRight")) {
                    // Code to resolve labyrinth following the right wall
                    Solver solver = new Solver();
                    if(solver.aStar(CurrentMaze)){
                        printToTerminal("Path found");
                    }
                    else{
                        printToTerminal("No path found");
                    }
                    generateWay(labyrinthArea, CurrentMaze);
                    printToTerminal("Time taken: " + solver.get_time_ms() + " milliseconds");
                }
            }});

/**
 * ComboBox for choosing the resolution method
 */
        ComboBox<String> resolutionMethods = new ComboBox<>();
        resolutionMethods.getItems().addAll("DFS", "DFS Step-by-step", "BFS", "BFS Step-by-step", "A*", "A* Step-by-step", "Dijkstra", "Dijkstra Step-by-step", "WallFollowerLeft", "WallFollowerRight");
        resolutionMethods.setPromptText("Select Resolution Method");
        resolutionMethods.setOnAction(e -> {
            selectedMethod = resolutionMethods.getValue();
            if (selectedMethod != null && selectedMethod.contains("Step-by-step")) {
                speedBox.setVisible(true);
                speedBox.setManaged(true);
            } else {
                speedBox.setVisible(false);
                speedBox.setManaged(false);
            }
            printToTerminal("Selected method: " + selectedMethod);
        });

/**
 * Button to save a labyrinth
 * The labyrinth is saved to a text file named by the user
 */
        Button saveLaby = new Button("Save Labyrinth");
        saveLaby.setOnAction(e -> {
            printToTerminal("Labyrinth saved");
            // Code to save the labyrinth
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Nom du labyrinthe");
            dialog.setHeaderText("Veuillez entrer un nom pour votre labyrinthe :");
            dialog.setContentText("Nom :");

            Optional<String> result = dialog.showAndWait();
            CurrentMaze.save_maze(result.get());
        });

/**
 * Button to load a labyrinth
 * The labyrinth is loaded from a text file choosen by the user
 */
        Button loadLaby = new Button("Load Labyrinth");
        loadLaby.setOnAction(e -> {
            printToTerminal("Labyrinth loaded");
            // Code to load the labyrinth
            String[] mazeList = Maze.get_backup_names();
            ChoiceDialog<String> dialog = new ChoiceDialog<>(mazeList[0], mazeList);
            dialog.setTitle("Choix du labyrinthe");
            dialog.setHeaderText("Sélectionnez un labyrinthe :");
            dialog.setContentText("Labyrinthe :");
            Optional<String> result = dialog.showAndWait();
            if(CurrentMaze == null){
                CurrentMaze = new PerfectMaze();
                generatedLabyrinth  = "Loaded Labyrinth";
            }
            CurrentMaze.restore_maze(result.get());
            generateMaze(labyrinthArea, CurrentMaze);
        });

/**
 * Buttons for navigation between generation and resolution
 */
        Button previousButton = new Button("⟵ Previous");

        Button nextButton = new Button("Next ⟶");


        previousButton.setOnAction(e -> {
            printToTerminal("Previous button clicked");
            buttonBox.getChildren().removeAll(previousButton, resolveButton, speedBox, resolutionMethods, loadLaby, saveLaby);
            buttonBox.getChildren().addAll(seedField, widthField, heightField, validateButton, generateLabyrinth, generationMethods, nextButton);
        });

        nextButton.setOnAction(e -> {
            printToTerminal("Next button clicked");
            buttonBox.getChildren().removeAll(seedField, widthField, heightField, validateButton, generateLabyrinth, generationMethods, nextButton);
            buttonBox.getChildren().addAll(previousButton, resolveButton, speedBox, resolutionMethods, loadLaby, saveLaby);
        });

        buttonBox.getChildren().addAll(seedField, widthField, heightField, validateButton, generateLabyrinth, generationMethods, nextButton);


        Scene scene = new Scene(root, 1300, 720);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();



    }
    /**
     * Generate the maze step by step
     * using the DFS algorithm
     * @param labyrinthArea
     * @param maze
     */
    public void GenerateStepByStep(Pane labyrinthArea, PerfectMaze maze) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.05));
        pause.setOnFinished(event -> {
            boolean end = maze.generate_dfs_next_step();
            generateMaze(labyrinthArea, maze);

            if (!end) {
                GenerateStepByStep(labyrinthArea, maze);
            }
        });
        pause.play();
    }
    /**
     * Generate the maze completely
     * using the DFS algorithm
     * @param labyrinthArea
     * @param maze
     */
    public void GenerateComplete(Pane labyrinthArea, PerfectMaze maze) {
        while (!(maze.generate_dfs_next_step())) {}
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
    /**
     * Generate the way step by step
     * First step is to find the path and mark all the nodes visited
     * @param maze
     * @param solver
     * @param labyrinthArea
     * @param speed
     * @param onFinish
     */
    public void GenerateStepByStepWayFirstStep(Maze maze, SolverSbS solver, Pane labyrinthArea, double speed, Runnable onFinish) {
        PauseTransition pause = new PauseTransition(Duration.seconds(speed));
        pause.setOnFinished(event -> {
            boolean end = solver.next_step();
            generateWay(labyrinthArea, CurrentMaze);
            if (!end) {
                GenerateStepByStepWayFirstStep(maze, solver, labyrinthArea, speed, onFinish);
            }
            else {
                onFinish.run();
            }
        });
        pause.play();
    }

    /**
     * Generate the way step by step
     * Last step is after finding the path
     * to mark the path beetween the start and the end nodes
     * @param maze
     * @param solver
     * @param labyrinthArea
     * @param speed
     */
    public void GenerateStepByStepWayLastStep(Maze maze, SolverSbS solver, Pane labyrinthArea, double speed) {
        PauseTransition pause = new PauseTransition(Duration.seconds(speed));
        pause.setOnFinished(event -> {
            boolean end = solver.find_path_step();
            generateWay(labyrinthArea, CurrentMaze);
            if (!end) {
                GenerateStepByStepWayLastStep(maze, solver, labyrinthArea, speed);
            }
        });
        pause.play();
    }

    /**
     * Generate a maze
     * This method is used to draw the maze in the pane
     * It draws the walls, the start and end nodes
     * @param pane
     * @param maze
     */
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
    /**
     * This method is used to add or remove walls in the maze
     * It is called when the user clicks on a wall
     * It checks if the wall is already present or not
     * If the wall is present, it removes it
     * If the wall is not present, it adds it
     * @param pane
     * @param maze
     * @param node1
     * @param node2
     */
    public void lineClicked(Pane pane, Maze maze, Node node1, Node node2) {
        if (node1 != null && node2 != null) {
            if (maze.in_edge_list(node1, node2) >= 0) {
                maze.remove_edge(maze.get_edge(node1, node2));
                maze.remove_adjacency(node1, node2);
                printToTerminal("Wall added");
            }
            else {
                Edge edge = new Edge(node1, node2);
                maze.add_edge(edge);
                maze.add_adjacency(node1, node2);
                printToTerminal("Wall removed");
            }
        }
        else {
            printToTerminal("Invalid coordinates for wall addition.");
        }
        generateMaze(pane, maze);
    }
    /**
     * This method is used to change the start and end nodes
     * It is called when the user clicks on the start node
     * It set the start node to the clicked node
     * It checks if the end node is already selected
     * @param maze
     * @param ChangeEndNode
     * @param c
     * @return
     */
    public Boolean StartNodeClicked(Maze maze, BooleanProperty ChangeEndNode,Circle c) {
        if (ChangeEndNode.getValue()) {
            printToTerminal("End node already selected.");
            return false;
        }
        c.getStyleClass().remove("start");
        c.getStyleClass().add("inChange");
        printToTerminal("Start node ready to change.");
        return true;
    }
    public Boolean EndNodeClicked(Maze maze, BooleanProperty ChangeStartNode,Circle c) {
        if (ChangeStartNode.getValue()) {
            printToTerminal("Start node already selected.");
            return false;
        }
        c.getStyleClass().remove("end");
        c.getStyleClass().add("inChange");
        printToTerminal("End node ready to change.");
        return true;
    }
    /**
     * This method is used to change the start and end nodes
     * It is called when the user clicks on a node after selecting the start or end node
     * It set the start or end node to the clicked node
     * @param pane
     * @param maze
     * @param ChangeStartNode
     * @param ChangeEndNode
     * @param node
     */
    public void NodeClicked(Pane pane, Maze maze, BooleanProperty ChangeStartNode,BooleanProperty ChangeEndNode,Node node) {
        if (ChangeStartNode.getValue()) {
            maze.set_StartNode(node.get_coordinates()[0], node.get_coordinates()[1]);
            printToTerminal("Start node changed to: " + node.get_coordinates()[0] + ", " + node.get_coordinates()[1]);
            generateMaze(pane, maze);
        }
        if (ChangeEndNode.getValue()) {
            maze.set_EndNode(node.get_coordinates()[0], node.get_coordinates()[1]);
            printToTerminal("End node changed to: " + node.get_coordinates()[0] + ", " + node.get_coordinates()[1]);
            generateMaze(pane, maze);
        }
        else {
            printToTerminal("To change the path, select the Start or the End before");
        }
    }

    /**
     * This method is used to generate the way
     * It is called when the user clicks on the resolve button
     * It generates the way after the selected resolution method had edit the maze
     * @param pane
     * @param maze
     */
    public void generateWay(Pane pane, Maze maze) {
        pane.getChildren().clear();
        Group WayGroup = new Group();
        int CELL_SIZE = set_cell_size(maze);
        drawWay(pane, maze,CELL_SIZE, WayGroup);
        WayGroup.layoutXProperty().bind(pane.widthProperty().subtract(maze.get_size()[0] * CELL_SIZE).divide(2));
        WayGroup.layoutYProperty().bind(pane.heightProperty().subtract(maze.get_size()[1] * CELL_SIZE).divide(2));
        generateMaze(pane, maze);
        pane.getChildren().add(WayGroup);
    }

    /**
     * This method is used to draw the way
     * It is called by generateWay
     * @param pane
     * @param maze
     * @param CELL_SIZE
     * @param WayGroup
     */
    public void drawWay(Pane pane, Maze maze,int CELL_SIZE, Group WayGroup) {
        int CIRCLE_SIZE = 1;
        if (CELL_SIZE/7 > 0){
            CIRCLE_SIZE = CELL_SIZE / 7;
        }
        for(int i = 0; i < maze.get_size()[0]; i++){
            for(int j = 0; j < maze.get_size()[1]; j++){

                int[] coordinates = maze.get_node(i,j).get_coordinates();
                if (maze.get_node(coordinates[0],coordinates[1]).getMark() != null) {
                    Circle WayCircle = new Circle((coordinates[0] + 0.5) * CELL_SIZE,
                            (coordinates[1] + 0.5) * CELL_SIZE,
                            CIRCLE_SIZE);
                    WayGroup.getChildren().add(WayCircle);
                    if(maze.get_node(coordinates[0],coordinates[1]) == maze.getEndNode()){
                        WayGroup.getChildren().remove(WayCircle);
                    }
                    if(maze.get_node(coordinates[0],coordinates[1]) == maze.getStartNode()){
                        WayGroup.getChildren().remove(WayCircle);
                    }
                    else if (maze.get_node(coordinates[0],coordinates[1]).isPath()) {
                        WayCircle.getStyleClass().add("way");
                    }
                    else {
                        WayCircle.getStyleClass().add("visited");
                    }
                }
                int[][] directions = {
                        {1, 0},
                        {0, 1},
                        {-1, 0},
                        {0, -1}
                };
                for (int[] d : directions) {
                    int nx = coordinates[0] + d[0];
                    int ny = coordinates[1] + d[1];

                    if (maze.get_node(nx, ny) != null &&
                            maze.in_edge_list(maze.get_node(coordinates[0],coordinates[1]), maze.get_node(nx, ny))>=0 &&
                            maze.get_node(nx, ny).getMark() != null &&
                            maze.get_node(coordinates[0],coordinates[1]).getMark() != null) {

                        Circle MiddleWayCircle = new Circle((coordinates[0] + 0.5 + d[0] * 0.5) * CELL_SIZE,
                                (coordinates[1] + 0.5 + d[1] * 0.5) * CELL_SIZE,
                                CIRCLE_SIZE);
                        WayGroup.getChildren().add(MiddleWayCircle);
                        if (maze.get_node(nx, ny).isPath() && maze.get_node(coordinates[0],coordinates[1]).isPath()) {
                            MiddleWayCircle.getStyleClass().add("way");
                        }
                        else {
                            MiddleWayCircle.getStyleClass().add("visited");
                        }
                    }
                }
            }
        }
    }
    /**
     * Main method to launch the application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}