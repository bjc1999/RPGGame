import java.util.Random;
import javafx.animation.PathTransition;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Centaur extends Enemy{
    private Game game;
    private map map;
    private StackPane stack;
    protected ImageView centaur;
    private PathTransition path;
    
    public Centaur(Game game, map map){
        this.game = game;
        this.map = map;
        setting();
    }

    
    public void setting() {
        stack = new StackPane();
        stack.setAlignment(Pos.TOP_LEFT);
        centaur = new ImageView ("res/Centaur.png");
        setID();
        spawning();
        map.stack.getChildren().add(stack);
    }

    
    public void update() {
        
    }

    
    public void movement() {
        path = new PathTransition();
        path.setNode(centaur);
        path.setDuration(Duration.seconds(2));
        path.setPath(new Rectangle(centaur.getTranslateX()+25, centaur.getTranslateY()+25, 150, 50));
        path.setCycleCount(PathTransition.INDEFINITE);
        path.play();
    }

    
    public String getID() {
        return centaur.getId();
    }

    
    public void setID() {
        centaur.setId("Centaur");
    }

    public ImageView getNode() {
        return centaur;
    }

    
    public void spawning() {
        Random rand = new Random();
        int x = rand.nextInt(500);
        int y = rand.nextInt(250);
        centaur.setTranslateX(x);
        centaur.setTranslateY(y);
        movement();
        
        stack.getChildren().add(centaur);
    }
    
    public void stopPath() {
        path.pause();
    }

    
    public void resumePath() {
        path.play();
    }
    
}
