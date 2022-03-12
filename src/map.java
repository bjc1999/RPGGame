import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class map {
   
    
    protected GridPane gamePane;
    protected StackPane stack;
    private Game game;
    private ImageView grassPic, desertPic, waterPic, mapPic;
    private int row, column;
    protected ArrayList <String> grass = new ArrayList <String>();
    protected ArrayList <String> desert = new ArrayList <String>();
    protected ArrayList <String> water = new ArrayList <String>();
    protected int[][] terrain = new int[11][14];  //row x column
    private Random r = new Random();
    
    public map(Game game){
        this.game = game;
        terrainGen();
        displayMap();
        
    }
    
    public StackPane displayMap() {
        gamePane = new GridPane();
        stack = new StackPane();
        stack.setAlignment(Pos.TOP_LEFT);
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 14; column++) {
                gamePane.add(getTile(terrain[row][column]), column, row);
            }
        }
        
        stack.getChildren().add(gamePane);
        return stack;
    }
    
    public ImageView getTile(int index) {

//        Random r = new Random();
        ImageView grassPic = new ImageView("res/grass.png");
        ImageView desertPic = new ImageView("res/desert.png");
        ImageView waterPic = new ImageView("res/water.gif");

//        int index = r.nextInt(3);
        switch (index) {
            case 0:
                return grassPic;
            case 1:
                return desertPic;
            default:
                return waterPic;
        }
    }

    public void terrainGen() {
        int numDesert = r.nextInt(3) + 2;
        int numWater = r.nextInt(3) + 2;
        
        //// Prefilled with grass
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 14; column++) {
                terrain[row][column] = 0;
            }
        }
        
        readTerrain(1, "src/res/Desert.txt", numDesert);
        readTerrain(2, "src/res/Water.txt", numWater);
        readTerrain(1, "src/res/Desert.txt", (((int) r.nextInt(2)) + 1));
        
        // Safe Zone
        for (int row = 7; row < 11; row++) {
            for (int column = 5; column < 10; column++) {
                terrain[row][column] = 0;
            }
        }
    }
    
    public void readTerrain(int type, String fileName, int numTerrain) {
        File file = new File(fileName);
        int numOfLine = 0;

        //// For debigging START
        if (file.exists()) {
            System.out.println("Type " + type + " file loaded");
        }
//        if (desertFile.canRead()) {
//            System.out.println("Desert file can be read.");
//        }
        //// For debigging END

        Scanner scanner = null;
        
        //// To count num of lines in desert file START
        LineNumberReader lnrD = null;
        String l;
        try {
            lnrD = new LineNumberReader(new FileReader(fileName));
        } catch (FileNotFoundException ex) {
            System.out.println("lnrD error");
        }
        try {
            while ((l = lnrD.readLine()) != null) {
                numOfLine++;
                // System.out.println(numLineDesert + ": " + l);
                // System.out.println(numLineDesert);
            }
        } catch (IOException ex) {
            System.out.println("lnrD2 error");
        }
        //// To count num of lines in desert file END
        
        //System.out.println("numLineDesert: " + numLineDesert);
        
        //// To output a random amount of distinct desert terrain
        for (; numTerrain > 0; numTerrain--) {

            try {
                scanner = new Scanner(file);
            } catch (Exception e) {
                System.out.println("Error - scanner Error");
            }

            System.out.println("numTerrain: " + numTerrain);

            int randomRow = r.nextInt(16); // Which row to paste
            int randomColumn = r.nextInt(12); // Which column to paste
            
            // System.out.println("randomRow: " + randomRow);
            // System.out.println("randomColumn: " + randomColumn);
            
            //// Choose a random desert from file
            int choosenLine = r.nextInt(numOfLine) + 1;
            System.out.println("choosenLine: " + choosenLine);
            
            //// Skip until appropriate line
            for (; choosenLine > 1; choosenLine--) {
                String temppp = scanner.nextLine();
                // System.out.println(temppp + " discarded");
            }
            
            //// Scan row number (first number for each line)
            int tempNumRow = scanner.nextInt();
            System.out.println("Random row: " + tempNumRow);
            //// Scan column number (second number for each line)
            int tempNumColumn = scanner.nextInt();
            System.out.println("Random column: " + tempNumColumn);
            
            for (int i = 0; i < tempNumRow; i++) {
                for (int j = 0; j < tempNumColumn; j++) {
                    try {
                        if (scanner.nextInt() == 1) {
                            terrain[randomRow + i][randomColumn + j] = type;
                        }
                    } catch (Exception e) {
                    }
                }
            }
            
            
        }
    }
    
    
}