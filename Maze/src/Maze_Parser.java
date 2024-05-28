import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class Maze_Parser {
    protected Scanner lineScanner = null;
    private final ArrayList<String> lines = new ArrayList<>();
    private boolean hasRead;
    protected int[] startPoint;
    protected int[] endPoint;
    protected int[][] maze;
    private boolean hasLoaded;
    private File inputFile;

    public Boolean isFileRead() {
        return this.hasRead;
    }

    public int[] getStartPoint() {
        if (hasLoaded()) {
            return this.startPoint;
        }
        return null;
    }

    public int[] getEndPoint() {
        if (hasLoaded()) {
            return this.endPoint;
        }
        return null;
    }

    public int[][] getPuzzle() {
        if (hasLoaded()) {
            return this.maze;
        }
        return null;
    }

    public void loadLines() throws IOException {
        if (this.hasRead) {
            lines.addAll(Files.readAllLines(inputFile.toPath(), Charset.defaultCharset()));
            this.hasLoaded = true;
        }
    }

    public void fileRead(String path) throws FileNotFoundException {
        File inputFile;
        inputFile = new File(path);

        if (inputFile.length() == 0) {
            throw new FileNotFoundException("File " + path + " does not exist");
        }

        this.inputFile = inputFile;
        this.hasRead = true;
    }

    public String getFileName() {
        if (hasLoaded) {
            return inputFile.getName();
        }
        return null;
    }

    public ArrayList<String> getLines() {
        if (this.hasRead) {
            return this.lines;
        }
        return null;
    }

    public File getFile() {
        if (this.hasRead) {
            return inputFile;
        }
        return null;
    }

    public void printLines() {
        if (this.hasRead) {
            for (String line: lines) {
                System.out.println(line);
            }
        }
    }

    //To Add a new maze In GUI
    public void fileRead() throws Exception {
        FileDialog fileDialog = new FileDialog((Frame)null, "Please select input file");
        fileDialog.setMode(FileDialog.LOAD);
        fileDialog.setDirectory(System.getProperty("user.dir"));
        fileDialog.setFile("*.txt");
        fileDialog.setVisible(true);
        String file = fileDialog.getFile();
        String fileType = file.substring(Math.max(0, file.length()-4));

        if (!fileType.equals(".txt")) {
            throw new Exception("File extension of the input file should be in .txt");
        }

        File inputFile = fileDialog.getFiles()[0];
        if (inputFile.length() == 0) throw new Exception("Hasn't selected any file");
        this.inputFile = fileDialog.getFiles()[0];
        this.hasRead = true;
    }

    public Boolean hasLoaded() {
        if (this.isFileRead()) {
            return this.hasLoaded;
        }
        return null;
    }

    public boolean loadValues() {
        ArrayList<String> lines = this.getLines();
        this.lineScanner = new Scanner(this.getLines().get(0));

        int floorSize = this.getLines().get(0).trim().length();
        this.maze = new int[floorSize][lines.size()];
        int lineCount = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int[] floor = new int[floorSize];
            this.lineScanner = new Scanner(line);
            int counter = 0;
            while (lineScanner.hasNext()) {
                if (counter < floorSize) {
                    String li = lineScanner.nextLine();
                    li = li.replace("0", "1");
                    li = li.replace(".", "0");

                    if(li.contains("S")) {
                        this.startPoint = new int[]{lineCount, li.indexOf("S")};
                        li = li.replace("S", "0");
                    }
                    if(li.contains("F")) {
                        this.endPoint = new int[]{lineCount, li.indexOf("F")};
                        li = li.replace("F", "0");
                    }
                    String[] string = li.split("");

                    for (int j = 0; j < string.length; j++) {
                        floor[j] = Integer.valueOf(string[j]);
                    }
                    lineCount++;
                }
                counter++;
            }
            lineScanner.close();
            lineScanner = null;
            maze[i] = floor;
        }

        return true;
    }
}

