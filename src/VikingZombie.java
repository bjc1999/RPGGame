import javafx.animation.PathTransition;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class VikingZombie extends Enemy{
    private Game game;
    private map map;
    private StackPane stack;
    protected ImageView vikingZombie;
    private PathTransition path;
    
    public VikingZombie(Game game, map map){
        this.game = game;
        this.map = map;
        setting();
    }

    
    public void setting() {
        stack = new StackPane();
        stack.setAlignment(Pos.TOP_LEFT);
        vikingZombie = new ImageView ("res/VikingZombie.png");
        setID();
        spawning();
        map.stack.getChildren().addAll(stack);
    }

    
    public void update() {
        
    }

    
    public void movement() {
        path = new PathTransition();
        path.setNode(vikingZombie);
        path.setDuration(Duration.seconds(2));
        path.setPath(new Rectangle(vikingZombie.getTranslateX(), vikingZombie.getTranslateY(), 5, 5));
        path.setCycleCount(PathTransition.INDEFINITE);
        path.play();
    }

    
    public String getID() {
        return vikingZombie.getId();
    }

    
    public void setID() {
        vikingZombie.setId("Zombie");
    }

    public ImageView getNode() {
        return vikingZombie;
    }

    
    public void spawning() {
        vikingZombie.setTranslateX(300);
        vikingZombie.setTranslateY(70);
        movement();
        
        stack.getChildren().add(vikingZombie);
    }
    
    public void stopPath() {
        path.pause();
    }

    
    public void resumePath() {
        path.play();
    }

}
