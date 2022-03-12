import javafx.animation.PathTransition;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Shemhazain extends Enemy{
    private Game game;
    private map map;
    private StackPane stack;
    protected ImageView shemhazain, raining;
    private PathTransition path;
    
    public Shemhazain(Game game, map map){
        this.game = game;
        this.map = map;
        setting();
    }

    
    public void setting() {
        stack = new StackPane();
        stack.setAlignment(Pos.TOP_LEFT);
        shemhazain = new ImageView ("res/Shemhazain.png");
        raining = new ImageView ("res/rain.gif");
        setID();
        spawning();
        map.stack.getChildren().addAll(stack, raining);
    }

    
    public void update() {
    }

    
    public void movement() {
        path = new PathTransition();
        path.setNode(shemhazain);
        path.setDuration(Duration.seconds(2));
        path.setPath(new Rectangle(shemhazain.getTranslateX(), shemhazain.getTranslateY(), 1, 0));
        path.setCycleCount(PathTransition.INDEFINITE);
        path.play();
    }

    
    public String getID() {
        return shemhazain.getId();
    }

    
    public void setID() {
        shemhazain.setId("Shemhazain");
    }

    public ImageView getNode() {
        return shemhazain;
    }

    
    public void spawning() {
        shemhazain.setTranslateX(350);
        shemhazain.setTranslateY(20);
        movement();
        
        stack.getChildren().add(shemhazain);
    }
    
    public void stopPath() {
        path.pause();
    }

    
    public void resumePath() {
        path.play();
    }
}
