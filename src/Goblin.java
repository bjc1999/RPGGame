import java.util.Random;
import javafx.animation.PathTransition;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Goblin extends Enemy{
    private Game game;
    private map map;
    private StackPane stack;
    protected ImageView goblin;
    private PathTransition path;
    
    public Goblin(Game game, map map){
        this.game = game;
        this.map = map;
        setting();
    }

    
    public void setting() {
        stack = new StackPane();
        stack.setAlignment(Pos.TOP_LEFT);
        goblin = new ImageView ("res/Goblin.png");
        setID();
        spawning();
        map.stack.getChildren().addAll(stack);
    }

    
    public void update() {
        
    }

    
    public void movement() {
        path = new PathTransition();
        path.setNode(goblin);
        path.setDuration(Duration.seconds(2));
        path.setPath(new Rectangle(goblin.getTranslateX(), goblin.getTranslateY(), 150, 50));
        path.setCycleCount(PathTransition.INDEFINITE);
        path.play();
    }

    
    public String getID() {
        return goblin.getId();
    }

    
    public void setID() {
        goblin.setId("Goblin");
    }

    public ImageView getNode() {
        return goblin;
    }

    
    public void spawning() {
        Random rand = new Random();
        int x = rand.nextInt(500);
        int y = rand.nextInt(200)+200;
        goblin.setTranslateX(x);
        goblin.setTranslateY(y);
        movement();
        
        stack.getChildren().add(goblin);
    }
    
    public void stopPath() {
        path.pause();
    }

    
    public void resumePath() {
        path.play();
    }
}
