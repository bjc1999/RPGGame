import java.util.Random;
import javafx.animation.PathTransition;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Dragon extends Enemy{
    private Game game;
    private map map;
    private StackPane stack;
    protected ImageView dragon;
    private PathTransition path;
    
    
    public Dragon(Game game, map map){
        this.game = game;
        this.map = map;
        setting();
        
        
    }
    
    public void setting(){
        stack = new StackPane();
        stack.setAlignment(Pos.TOP_LEFT);
        dragon = new ImageView ("res/Dragon.png");
        setID();
        spawning();
        map.stack.getChildren().add(stack);
    }
    
    public void update(){
        //System.out.println(dragon.getTranslateX());
        //System.out.println(dragon.getTranslateY());
    }
    
    public void movement(){
        path = new PathTransition();
        path.setNode(dragon);
        path.setDuration(Duration.seconds(5));
        path.setPath(new Rectangle(dragon.getTranslateX(), dragon.getTranslateY(), 150, 50));
        path.setCycleCount(PathTransition.INDEFINITE);
        path.play();
    }
    
    public void setID(){
        dragon.setId("Dragon");
    }
    
    public String getID(){
        return dragon.getId();
    }
    
    public ImageView getNode(){
        return dragon;
    }
    
    public void spawning(){
        Random rand = new Random();
        int x = rand.nextInt(500);
        int y = rand.nextInt(100);
        dragon.setTranslateX(x);
        dragon.setTranslateY(y);
        movement();
        
        stack.getChildren().add(dragon);
    }

    
    public void stopPath() {
        path.pause();
    }

    
    public void resumePath() {
        path.play();
    }
    
}
