import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application{
    
    protected BorderPane root = new BorderPane();
    private Player player;
    protected final int width = 850;
    protected final int height = 660;
    private interactPlane iP;
    private map map;
    protected Scene scene;
    protected KeyCode key;
    private playerInfo pI;
    private Combat combat;
    private Dragon dragon;
    protected boolean combatTrigered = false, askInput = false, isSpawn = false; 
    protected int setup = 0, spawnRate=1;
    protected ArrayList <Enemy> enemylist = new ArrayList <>();
    private Enemy enemy;
    protected String ID=null;
    private int enemyIndex;
    private Shop shop;
    private NPC npc;
    private VikingZombie vz;
    private JapanSamurai js;
    private Shemhazain boss;
    
    public Game(){
        map = new map(this);
        iP = new interactPlane(this);
        player = new Player(this, map);
        npc = new NPC(this , map ,iP, player);
        shop = new Shop(this, player, map, iP);
        pI = new playerInfo (this, player, shop);
        combat = new Combat (this, map, pI, player, iP, shop, npc);
        
    }
    
    public void spawningSystem(){
        Random rand = new Random ();
        int possibility = rand.nextInt(100);
        int num = rand.nextInt(spawnRate);
        if(num==0){
            if(pI.level<=15){
                if (possibility%2==0){
                    enemylist.add(new Skeleton(this, map));
                }
                else if (possibility%5==0){
                    enemylist.add(new Centaur(this, map));
                }
                else{
                    enemylist.add(enemy = new Goblin(this, map));
                }
            }
            else{
                if (possibility%2==0){
                    enemylist.add(new Assasin(this, map));
                }
                else if (possibility%5==0){
                    enemylist.add(new Dragon(this, map));
                }
                else{
                    enemylist.add(enemy = new Centaur(this, map));
                }
            }
        }
         //System.out.println("num: "+num+"  spawn: "+possibility);   
    }
    
    private Parent createContent(){
        root.setPrefSize(width, height);
        root.setBottom(iP.background);
        root.setRight(pI.charWin);
        root.setCenter(map.stack);
        AnimationTimer timer = new AnimationTimer(){
           
            public void handle(long now) {
                render();
                update();
            }
            
        };
        
        timer.start();
        return root;
    }
    
    public void update(){
        npc.update();
        pI.update();
        player.update();
        if(combat.bossDie){
                map.stack.getChildren().remove(boss.raining);
            }
         
        if (combatTrigered){
            switch (setup) {
                case 0:
                    combat.battleSetUp();
                    break;
                case 1:
                    combat.switching();
                    break;
                case 2:
                    combat.createContent();
                    break;
                case 3:
                    combat.update();
                    break;
                default:
                    combat.endBattle();
                    if(combat.bossDie||(!ID.equals("Zombie")||!ID.equals("Shemhazain")||!ID.equals("Samurai"))){
                        enemylist.remove(enemyIndex);
                        map.stack.getChildren().remove(enemyIndex+1);
                        combat.bossDie = false;
                    }
                    for(Enemy enemy:enemylist)
                        enemy.resumePath();
                    break;
            }
            if(npc.status2)
                boss.raining.toFront();
        }
    }

    
    public void render (){
        
        if (enemylist.isEmpty()){
            spawnRate = 10;
            spawningSystem();
        }
        else if (enemylist.size()<=3&&!npc.status2){
            spawnRate = 500;
            spawningSystem();
        }
        else if (enemylist.size()<=6&&npc.status2){
            spawnRate = 500;
            spawningSystem();
        }
        
        if(npc.status2&&isSpawn){
            vz = new VikingZombie(this, map);
            enemylist.add(vz);
            js = new JapanSamurai(this, map);
            enemylist.add(js);
            boss = new Shemhazain(this, map);
            enemylist.add(boss);
            isSpawn = false;
        }
        
        
        
        for (int i=0; i<enemylist.size(); i++){
            if (player.player.getBoundsInParent().intersects(enemylist.get(i).getNode().getBoundsInParent())){
                enemyIndex = i;
                ID = enemylist.get(i).getID();
                combatTrigered = true;
                combat.trigered();
                for(Enemy enemy:enemylist)
                    enemy.stopPath();
                
            }
        }
        
        
        
        
        scene.setOnKeyPressed(e->{

            switch (e.getCode()){
                case Q:
                    if(player.player.getBoundsInParent().intersects(shop.shop.getBoundsInParent()))
                        shop.enterShop();
                    break;
                case E:
                    if(player.player.getBoundsInParent().intersects(shop.shop.getBoundsInParent()))
                        shop.leaveShop();
                    break;
                case Z :
                    if(player.player.getBoundsInParent().intersects(npc.NPC.getBoundsInParent()))
                        npc.EnterNPC();
                    break;
                case X :
                    if(player.player.getBoundsInParent().intersects(npc.NPC.getBoundsInParent()))
                        npc.LeaveNPC();
                    break;
                case ENTER:
                    askInput = true;
                    //iP.talk();
                    break;
                default:
                    key = e.getCode();
                    player.movement();
                    if(!combatTrigered){
                        if(player.player.getBoundsInParent().intersects(shop.shop.getBoundsInParent())) {
                            iP.removeChild();
                            iP.addChild(new Text("Press Q to enter shop.\nPress E to exit from shop."));
                        } else if(player.player.getBoundsInParent().intersects(npc.NPC.getBoundsInParent())) {
                            iP.removeChild();
                            iP.addChild(new Text("Press Z to interact with NPC.\nPress X to stop interacting."));
                        } else iP.removeChild();
                    }
                    break;
            }
            
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage primaryStage) throws Exception {
        // scene = new Scene(createContent()); 
        primaryStage.setMaxHeight(height);
        primaryStage.setMaxWidth(width);
        if (!gameStarted) {
            displayMM(primaryStage);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Swords Of Fortune");
            primaryStage.show();

        } else {
            scene = new Scene(createContent());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Swords Of Fortune");
            primaryStage.show();
        }

    }
    
    
    //MainMenu
    private String title = "Swords Of Fortune";
    private Text titleLab = new Text(title);  //show text
    private VBox totally = new VBox(10);
    private VBox buttonLayout = new VBox(40);
    private VBox titleLayout = new VBox(0);
    private Scene mmScene;
    private boolean gameStarted = false;

    public Stage displayMM(Stage stage) {

        titleLab.setFont(javafx.scene.text.Font.font("Times New Romen", 60));
        titleLab.setFill(Color.WHITE);
        titleLab.setEffect(new DropShadow(10, Color.BLACK));

        // New Game
        Button start = new Button();
        start.setText("Start");
        start.setFont(javafx.scene.text.Font.font("Times New Romen"));
        start.setEffect(new DropShadow(10, Color.BLACK));
        start.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), Insets.EMPTY)));
        start.setOnAction(e -> {
            gameStarted = true;
            try {
               start(stage); 
            } catch (Exception ex) {
            }
            
        });
        
        buttonLayout.getChildren().addAll(start);//, loadGame);
        titleLayout.getChildren().addAll(titleLab);
        buttonLayout.setAlignment(Pos.CENTER);
        titleLayout.setAlignment(Pos.CENTER);
        
        totally.getChildren().addAll(new VBox(), titleLayout, buttonLayout);
        totally.setSpacing(150.0);
        totally.setBackground(new Background(new BackgroundImage(new Image("res/mainMenu.jpg"), 
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        mmScene = new Scene(totally, 800, 600);
        
        stage.setScene(mmScene);
        
        return stage;
    }

    
   
}
