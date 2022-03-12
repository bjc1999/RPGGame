import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Player {

    protected StackPane stack;
    protected ImageView player;
    private map map;
    private Game game;
    protected int posX, posY, credit = 10000;
    private String pos = "";
    private boolean searching = true;
    private int vel = 5;

    public Player(Game game, map map) {
        this.game = game;
        this.map = map;
        stack = new StackPane();

        player = new ImageView("res/player1.png");
        player.setTranslateX(350);
        player.setTranslateY(500);
        

        stack.getChildren().addAll(player);

        this.map.gamePane.getChildren().add(stack);
    }

    public void movement() {

        switch (game.key) {
            case W:
                moveUp();
                break;
            case A:
                moveLeft();
                break;
            case S:
                moveDown();
                break;
            case D:
                moveRight();
                break;

        }

        if (player.getTranslateX() < 0) {
            player.setTranslateX(0);
        } else if (player.getTranslateX() > 650) {
            player.setTranslateX(650);
        } else if (player.getTranslateY() < 0) {
            player.setTranslateY(0);
        } else if (player.getTranslateY() > 500) {
            player.setTranslateY(500);
        }

    }

    public void update() {
        posX = (int) (player.getTranslateX() + 25) / 50;
        posY = (int) (player.getTranslateY() + 50) / 50;
        pos = "";
        pos += "" + posX + posY;
        for(int i=0; i<11; i++){
            for(int j=0; j<14; j++){
                if (pos.equals(""+j+i)) {
                    switch(map.terrain[i][j]){
                        case 0:
                            vel=5;
                            break;
                        case 1:
                            vel=3;
                            break;
                        default:
                            vel=1;
                            break;
                    }
                }
            }
        }

    }
    

    public void creditSystem(int credit) {
        this.credit += credit;
    }

    public int getVel() {
        return vel;
    }

    public double getPlayerX() {
        return player.getTranslateX();
    }

    public double getPlayerY() {
        return player.getTranslateY();
    }
    
    public void setPlayerX(int x){
        player.setTranslateX(x);
    }
    
    public void setPlayerY(int y){
        player.setTranslateY(y);
    }
    
    

    public void moveLeft() {
        player.setTranslateX(player.getTranslateX() - vel);
    }

    public void moveRight() {
        player.setTranslateX(player.getTranslateX() + vel);
    }

    public void moveUp() {
        player.setTranslateY(player.getTranslateY() - vel);
    }

    public void moveDown() {
        player.setTranslateY(player.getTranslateY() + vel);
    }

}
