import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class Maze {
    private final static Scanner inputReader = new Scanner(System.in);
    private static Maze_Parser parsedInputFile;
    private static boolean shouldSkipLoad;
    private static File inputFile;
    private static String inputFileName;

    public static void main(String[] args) throws Exception {
        System.out.println("""
                          Welcome to Sliding Puzzles
                          ---------------""");

        while (true) {
            shouldSkipLoad = false;

            System.out.println("""
                                To Load a new Maze Enter: 1
                                To Quit please Enter: 0""");

            System.out.print("\nchoice: ");
            String loopChoice = inputReader.nextLine();

            switch (loopChoice) {
                case "0":
                    System.exit(0);
                    break;
                case "1":
                    loadNewMaze();
                    break;
                default:
                    System.out.println("Invalid choice. Please Enter valid input");
                    break;
            }
        }
    }

    private static void distanceCalculator() {
        Instant startTime = null;
        Instant endTime = null;
        Duration timeElapsed = null;
        while (true) {
            System.out.println("No of lines: " + parsedInputFile.getLines().size());
            System.out.println("\n");
            System.out.println("""
                                   To Print the path Enter: 1
                                   To Restart the game Enter: 9""");

            System.out.print("\nChoice: ");

            String loopChoice = inputReader.nextLine();

            int[][] n = parsedInputFile.getPuzzle();
            int[] s = parsedInputFile.getStartPoint();
            int[] t = parsedInputFile.getEndPoint();

            Algorithm solver = new Algorithm();

            switch (loopChoice) {
                case "1":
                    if (startTime == null) {
                        startTime = Instant.now();
                    }
                    System.out.println("\nFind the shortest distance\n");
                    System.out.println(solver.shortestDistance(n, s, t));

                    if (endTime == null) {
                        endTime = Instant.now();
                        timeElapsed = Duration.between(startTime, endTime);
                    }
                    System.out.print("\nTime elapsed: ");

                    if (timeElapsed.toMillis() > 1000) {
                        System.out.print(timeElapsed.toSeconds() + " seconds\n");
                        return;
                    }

                    System.out.print(timeElapsed.toMillis() + " milliseconds\n");
                    break;
                case "9":
                    System.out.println("\n");
                    inputFileName = null;
                    parsedInputFile = null;
                    shouldSkipLoad = true;
                    return;
                default:
                    System.out.println("Invalid choice\n");
                    break;
            }
        }
    }

    //To load a new maze
    private static void loadNewMaze() {
        while (true) {
            if (shouldSkipLoad)
                return;
            System.out.println("""
                                To select graph from file explorer enter: 1
                                Go back to main menu enter: 3""");

            System.out.print("\nchoice: ");
            String loopChoice = inputReader.nextLine();
            boolean graphLoadError = false;

            switch (loopChoice) {
                case "1":
                    System.out.println("""
                                        Choose a text input file
                                        Please check the taskbar for a new icon
                                        NOTE: place it in the 'Maze' folder""");
                    try {
                        Maze_Parser fileParser = new Maze_Parser();
                        fileParser.fileRead();
                        fileParser.loadLines();
                        fileParser.loadValues();
                        if (!fileParser.isFileRead()) {
                            throw new Exception("File did not loaded");
                        }
                        inputFileName = fileParser.getFileName();
                        inputFile = fileParser.getFile();
                        parsedInputFile = fileParser;
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println("\nThe input file could not be read. Please try again later.\n");
                        graphLoadError = true;
                    }
                    if (!graphLoadError) {
                        shouldSkipLoad = true;
                        distanceCalculator();
                    } else {
                        continue;
                    }
                    break;
            }
        }
    }
}
