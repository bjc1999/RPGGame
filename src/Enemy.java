import javafx.scene.image.ImageView;

public abstract class Enemy {
    
    public abstract void setting();
    
    public abstract void update();
    
    public abstract void movement();
    
    public abstract String getID();
    
    public abstract void setID();
    
    public abstract ImageView getNode();
    
    public abstract void spawning();
    
    public abstract void stopPath();
    
    public abstract void resumePath();
}

