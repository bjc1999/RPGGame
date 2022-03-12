import java.util.Random;
import javafx.animation.PathTransition;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Skeleton extends Enemy{
    private Game game;
    private map map;
    private StackPane stack;
    protected ImageView skeleton;
    private PathTransition path;
    
    public Skeleton (Game game, map map){
        this.game = game;
        this.map = map;
        setting();
    }

    
    public void setting() {
        stack = new StackPane();
        stack.setAlignment(Pos.TOP_LEFT);
        skeleton = new ImageView ("res/Skeleton.png");
        setID();
        spawning();
        map.stack.getChildren().add(stack);
    }

    
    public void update() {
        
    }

    
    public void movement() {
        path = new PathTransition();
        path.setNode(skeleton);
        path.setDuration(Duration.seconds(5));
        path.setPath(new Rectangle(skeleton.getTranslateX(), skeleton.getTranslateY(), 150, 0));
        path.setCycleCount(PathTransition.INDEFINITE);
        path.play();
    }

    
    public String getID() {
        return skeleton.getId();
    }

    
    public void setID() {
        skeleton.setId("Skeleton");
    }
    
    public ImageView getNode() {
        return skeleton;
    }

    
    public void spawning() {
        Random rand = new Random();
        int x = rand.nextInt(500);
        int y = rand.nextInt(150)+200;
        skeleton.setTranslateX(x);
        skeleton.setTranslateY(y);
        movement();
        
        stack.getChildren().add(skeleton);
    }
    
    
    public void stopPath() {
        path.pause();
    }

    
    public void resumePath() {
        path.play();
    }
    
}
