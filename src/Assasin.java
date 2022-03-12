import java.util.Random;
import javafx.animation.PathTransition;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Assasin extends Enemy{
    private Game game;
    private map map;
    private StackPane stack;
    protected ImageView assasin;
    private PathTransition path;
    
    public Assasin(Game game, map map){
        this.game = game;
        this.map = map;
        setting();
    }

    
    public void setting() {
        stack = new StackPane();
        stack.setAlignment(Pos.TOP_LEFT);
        assasin = new ImageView ("res/Assasin.png");
        setID();
        spawning();
        map.stack.getChildren().addAll(stack);
    }

    
    public void update() {
        
    }

    
    public void movement() {
        path = new PathTransition();
        path.setNode(assasin);
        path.setDuration(Duration.seconds(2));
        path.setPath(new Rectangle(assasin.getTranslateX(), assasin.getTranslateY(), 5, 5));
        path.setCycleCount(PathTransition.INDEFINITE);
        path.play();
    }

    
    public String getID() {
        return assasin.getId();
    }

    
    public void setID() {
        assasin.setId("Assasin");
    }

    public ImageView getNode() {
        return assasin;
    }

    
    public void spawning() {
        Random rand = new Random();
        int x = rand.nextInt(500);
        int y = rand.nextInt(400);
        assasin.setTranslateX(x);
        assasin.setTranslateY(y);
        movement();
        
        stack.getChildren().add(assasin);
    }
    
    public void stopPath() {
        path.pause();
    }

    
    public void resumePath() {
        path.play();
    }
}
