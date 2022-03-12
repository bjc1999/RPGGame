import javafx.animation.PathTransition;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class JapanSamurai extends Enemy{
    private Game game;
    private map map;
    private StackPane stack;
    protected ImageView japanSamurai;
    private PathTransition path;
    
    public JapanSamurai(Game game, map map){
        this.game = game;
        this.map = map;
        setting();
    }

    
    public void setting() {
        stack = new StackPane();
        stack.setAlignment(Pos.TOP_LEFT);
        japanSamurai = new ImageView ("res/JapanSamurai.png");
        setID();
        spawning();
        map.stack.getChildren().addAll(stack);
    }

    
    public void update() {
        
    }

    
    public void movement() {
        path = new PathTransition();
        path.setNode(japanSamurai);
        path.setDuration(Duration.seconds(2));
        path.setPath(new Rectangle(japanSamurai.getTranslateX(), japanSamurai.getTranslateY(), 5, 5));
        path.setCycleCount(PathTransition.INDEFINITE);
        path.play();
    }

    
    public String getID() {
        return japanSamurai.getId();
    }

    
    public void setID() {
        japanSamurai.setId("Samurai");
    }

    public ImageView getNode() {
        return japanSamurai;
    }

    
    public void spawning() {
        japanSamurai.setTranslateX(400);
        japanSamurai.setTranslateY(70);
        movement();
        
        stack.getChildren().add(japanSamurai);
    }
    
    public void stopPath() {
        path.pause();
    }

    
    public void resumePath() {
        path.play();
    }
}
