import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

public class interactPlane{
    private Game game;
    private String [] tutor;
    protected StackPane background;
    protected StackPane stack;
    private int talkflow = 0;
    private Text tutorial;
    private playerInfo pI;
    private Combat combat;
    private Shop shop;
    private boolean skipTutor = false;
    private Player player;
    private Rectangle rect;
    
    
    public interactPlane(Game game){
        this.game = game;
        setting();
        Content();
    }
    
    public StackPane setting(){
        background = new StackPane();
        //background.setPadding(new Insets(5, 5, 5, 5));
        rect = new Rectangle(840, 74);
        rect.setFill(new LinearGradient(0,0,0,1,true,CycleMethod.NO_CYCLE, new Stop(1,Color.AQUAMARINE), new Stop(0, Color.ALICEBLUE)));
        rect.setStrokeType(StrokeType.INSIDE);
        rect.setStrokeWidth(2);
        rect.getStrokeDashArray().addAll(12.0, 5.0, 4.0, 5.0);
        rect.setStroke(Color.BLUE);
        
        
        
        background.setPrefHeight(74);
        background.getChildren().add(rect);
        return background;
    }
    
    public void Content(){
        stack = new StackPane();
        tutor = new String [5];
        
        
        
        tutorial = new Text();
        tutorial.setFill(Color.BLACK);
        
        stack.setPadding(new Insets(5, 0, 0, 20));
        stack.setAlignment(Pos.TOP_LEFT);
        
        background.getChildren().addAll(stack);
    }
    
    
    public void addChild (Text text){
        stack.getChildren().add(text);
    }
    
    public void removeChild (){
        stack.getChildren().clear();
    }



}
