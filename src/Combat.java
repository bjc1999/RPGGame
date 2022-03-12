import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Combat {
    private StackPane backgroundL, backgroundR, content, interact;
    private Game game;
    private map map;
    private playerInfo player;
    private int y=50, i=0, check, enemyCurrentHP, dmg, battleRound=1, canRun, potionCheck, oriCrit;
    private double critChance;
    private ArrayList <Rectangle> place = new ArrayList <>();
    private Rectangle mapPic=null, enemyInfo, playerInfo, playerPlane, HP1, HP2, enemyHPBar, playerHPBar, rainOverlay;
    private Text enemyName, playerName, enemyLvl, playerLvl, enemyHP, playerHP;
    private ImageView enemyPic, rain, fullmap;
    private String [] enemyNameI, enemyLvlI;
    private String terrain;
    private int [] enemyHPI, enemyMinDmg, enemyMaxDmg, enemyXP, enemyCash;
    private Button attack, skill, run;
    private MenuBar bag;
    private Menu potion;
    private MenuItem potion1, potion2, potion3;
    private Random rand = new Random();
    private Player play;
    private interactPlane iP;
    private EventHandler <ActionEvent> attacked, ran, usePotion;
    private Shop shop;
    private boolean endRound = true, runAway = false;
    private NPC npc;
    protected boolean bossDie = false;
    
    public Combat(Game game, map map, playerInfo player, Player play, interactPlane iP, Shop shop, NPC npc){
        this.game = game;
        this.map = map;
        this.player = player;
        this.play = play;
        this.iP = iP;
        this.shop = shop;
        this.npc = npc;
        setting();
        enemyInput();
        
    }
    
    public void setting(){
        backgroundL = new StackPane();
        backgroundR = new StackPane();
        content = new StackPane();
        
        backgroundL.setPrefSize(650, 500);
        backgroundR.setPrefSize(650, 500);
        content.setPrefSize(650, 500);
        
        backgroundL.setAlignment(Pos.TOP_LEFT);
        backgroundR.setAlignment(Pos.TOP_RIGHT);
        content.setAlignment(Pos.TOP_LEFT);
        
        rain = new ImageView("res/rain.gif");
        
        backgroundL.getChildren().addAll(backgroundR, content);
        
    }
    
    public void battleSetUp(){
        for(i=1; i<12; i++){
            mapPic = new Rectangle(50, 50);
            
            if(game.ID.equals("Shemhazain")){
                bossBattle();
                terrain = "boss";
            }else{
                switch(play.getVel()){
                    case 5:
                        grassBattle();
                        terrain = "grass";
                        break;
                    case 3:
                        desertBattle();
                        terrain = "desert";
                        break;
                    case 1:
                        waterBattle();
                        terrain = "water";
                        break;
                }
            }
            
            place.add(mapPic);
        
        }
        for(i=0; i<11; i++){
            if(i%2==0){
                backgroundL.getChildren().add(place.get(i));
                place.get(i).setTranslateY(i*50);
                place.get(i).setTranslateX(-5);
            }
            else{
                backgroundR.getChildren().add(place.get(i));
                place.get(i).setTranslateY(i*50);
                place.get(i).setTranslateX(-10);
            }
            
        }
        
        
        
        if(npc.status2){
            rainOverlay = new Rectangle(700, 550);
            rainOverlay.setFill(Color.GREY);
            rainOverlay.setOpacity(0.75);
            rainOverlay.setTranslateX(0);
            rainOverlay.setTranslateY(0);
            
            content.getChildren().addAll(rainOverlay, rain);
            
            if(game.ID.equals("Shemhazain"))
                content.getChildren().remove(rainOverlay);
        }
        
        backgroundR.toFront();
        content.toFront();
        
        
        game.setup = 1;
    }
    
    public void grassBattle(){
       if(i%2==0)
            mapPic.setFill(new ImagePattern(new Image("res/grassBattle"+i+".png")));
        else
            mapPic.setFill(new ImagePattern(new Image("res/grassBattle"+i+".png")));
        
    }
    
    public void desertBattle(){
        if(i%2==0)
            mapPic.setFill(new ImagePattern(new Image("res/desertBattle"+i+".png")));
        else
            mapPic.setFill(new ImagePattern(new Image("res/desertBattle"+i+".png")));
        
    }
    
    public void waterBattle(){
        if(i%2==0)
            mapPic.setFill(new ImagePattern(new Image("res/waterBattle"+i+".png")));
        else
            mapPic.setFill(new ImagePattern(new Image("res/waterBattle"+i+".png")));
        
    }
    
    public void bossBattle(){
        if(i%2==0)
            mapPic.setFill(new ImagePattern(new Image("res/bossBattle"+i+".png")));
        else
            mapPic.setFill(new ImagePattern(new Image("res/bossBattle"+i+".png")));
    }
    
    public void switching(){
        y+=10;
        for(Rectangle point: place)
            point.setWidth(y);
        if(y>=700){
            game.setup = 2;
            y=50;
            fullmap = new ImageView("res/"+terrain+"Battle.png");
            content.getChildren().add(fullmap);
            backgroundR.getChildren().clear();
            backgroundL.getChildren().remove(0, 6);
            if(npc.status2&&!game.ID.equals("Shemhazain")){
                rainOverlay.toFront();
                rain.toFront();
            }
            else if(npc.status2&&game.ID.equals("Shemhazain")){
                rain.toFront();
            }
        }
    }
    
    
    public void createContent(){
        //set background
        enemyInfo = new Rectangle(250, 55);
        enemyInfo.setFill(Color.LIGHTGRAY);
        enemyInfo.setOpacity(0.5);
        enemyInfo.setEffect(new DropShadow(10, 3, 10, Color.BLACK));
        HP1 = new Rectangle(202, 27);
        HP1.setFill(Color.TRANSPARENT);
        HP1.setStroke(Color.WHITE);
        HP1.setStrokeWidth(2);
        enemyHPBar = new Rectangle(200, 25);
        enemyHPBar.setFill(Color.SPRINGGREEN);
        enemyHPBar.setFill(new LinearGradient(0,0,1,0,true,CycleMethod.NO_CYCLE, new Stop(0,Color.GREEN), new Stop(1, Color.CHARTREUSE)));
        enemyName = new Text ("");
        enemyLvl = new Text ("");
        
        playerInfo = new Rectangle(250, 55);
        playerInfo.setFill(Color.ALICEBLUE);
        playerInfo.setOpacity(0.5);
        playerInfo.setEffect(new DropShadow(10, 3, 10, Color.BLACK));
        HP2 = new Rectangle(202, 27);
        HP2.setFill(Color.TRANSPARENT);
        HP2.setStroke(Color.WHITE);
        HP2.setStrokeWidth(2);
        playerHPBar = new Rectangle((double)player.hp/player.maxhp*200, 25);
        if(player.hp<0.2*player.maxhp)
            playerHPBar.setFill(new LinearGradient(0,0,0,1,true,CycleMethod.NO_CYCLE, new Stop(0,Color.CRIMSON), new Stop(1, Color.RED)));
        else
            playerHPBar.setFill(new LinearGradient(0,0,1,0,true,CycleMethod.NO_CYCLE, new Stop(0,Color.GREEN), new Stop(1, Color.CHARTREUSE)));
        playerName = new Text ("");
        playerLvl = new Text ("");
        
        
        playerPlane = new Rectangle(265, 50);
        playerPlane.setFill(Color.AZURE);
        playerPlane.setOpacity(0.7);
        playerPlane.setEffect(new DropShadow(10, 3, 10, Color.BLACK));
        
        enemyInfo.setTranslateX(420);
        enemyInfo.setTranslateY(30);
        
        HP1.setTranslateX(445);
        HP1.setTranslateY(50);
        enemyHPBar.setTranslateX(447);
        enemyHPBar.setTranslateY(52);
        
        playerInfo.setTranslateX(50);
        playerInfo.setTranslateY(480);
        
        HP2.setTranslateX(75);
        HP2.setTranslateY(500);
        playerHPBar.setTranslateX(77);
        playerHPBar.setTranslateY(502);
        
        playerPlane.setTranslateX(420);
        playerPlane.setTranslateY(490);
        
        //insert enemy pic
        enemyPic = new ImageView ("res/"+game.ID+"(combat).png");
        enemyPic.setTranslateX(400);
        enemyPic.setTranslateY(90);
        
        
        
        //insert enemy & player Info
        for(int i=0; i<8; i++){
            if(enemyNameI[i].equals(game.ID)){
                check = i;        
                break;
            }
        }
        
        enemyName = new Text(enemyNameI[check]);
        enemyName.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        enemyName.setFill(Color.WHITE);
        enemyName.setStroke(Color.BLACK);
        enemyLvl = new Text("Lv: "+enemyLvlI[check]);
        enemyLvl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        enemyLvl.setFill(Color.WHITE);
        enemyLvl.setStroke(Color.BLACK);
        enemyCurrentHP = enemyHPI[check];
        enemyHP = new Text(enemyCurrentHP+"/"+enemyHPI[check]);//if possible change to right allignment
        enemyHP.setFill(Color.WHITE);
        enemyHP.setStroke(Color.WHITE);
        
        
        playerName = new Text("Player");
        playerName.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        playerName.setFill(Color.WHITE);
        playerName.setStroke(Color.BLACK);
        playerLvl = new Text("Lv: "+ player.level);
        playerLvl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        playerLvl.setFill(Color.WHITE);
        playerLvl.setStroke(Color.BLACK);
        playerHP = new Text(player.hp+"/"+player.maxhp);//if possible change to right allignment
        playerHP.setFill(Color.WHITE);
        playerHP.setStroke(Color.WHITE);
        
        enemyName.setTranslateX(445);
        enemyName.setTranslateY(16);
        
        enemyLvl.setTranslateX(590);
        enemyLvl.setTranslateY(16);
        
        enemyHP.setTranslateX(452);
        enemyHP.setTranslateY(53);
        
        playerName.setTranslateX(75);
        playerName.setTranslateY(466);
        
        playerLvl.setTranslateX(200);
        playerLvl.setTranslateY(466);
        
        playerHP.setTranslateX(82);
        playerHP.setTranslateY(503);
        
        //insert button interface
        attack = new Button("Attack");
        attack.setPrefSize(70, 25);
        attack.setEffect(new DropShadow(10, 3, 3, Color.BLACK));
        /*
        skill = new Button("Skill");
        skill.setPrefSize(120, 50);
        */
        run = new Button("Run");
        run.setPrefSize(70, 25);
        run.setEffect(new DropShadow(10, 3, 3, Color.BLACK));
        
        bag = new MenuBar();
        bag.setPrefSize(70, 25);
        bag.setMaxSize(70, 25);
        bag.setPadding(new Insets(0, 0, 0, 0));
        bag.setEffect(new DropShadow(10, 3, 3, Color.BLACK));
        potion = new Menu(" Potion");
        potion1 = new MenuItem();
        potion2 = new MenuItem();
        potion3 = new MenuItem();
        potion.getItems().addAll(potion1,potion2,potion3);
        bag.getMenus().add(potion);
        bag.setDisable(true);
        
        attack.setTranslateX(430);
        attack.setTranslateY(500);
        
        run.setTranslateX(600);
        run.setTranslateY(500);
        
        bag.setTranslateX(515);
        bag.setTranslateY(500);
        
        oriCrit = player.crit;
        
        handler();
        
        content.getChildren().add(enemyPic);
        rain.toFront();
        content.getChildren().addAll(enemyInfo, playerInfo, HP1, HP2, enemyHPBar, playerHPBar, enemyName, 
                enemyLvl, enemyHP, playerName, playerHP, playerLvl, playerPlane,  attack, run, bag);
        
        if(game.ID.equals("Shemhazain")){
            bossSetUp();
        }
        
        iP.removeChild();
        iP.addChild(new Text(game.ID+" appeared on the "+terrain+" !\n"
                + "Battle start!\t[ENTER]"));
        
        
        game.setup = 3;
        
        
    }
    
    public void bossSetUp(){
        enemyPic.setTranslateX(250);
        
    }
    
    public void update(){
        if(((enemyCurrentHP==0||player.hp==0)||runAway)&&game.askInput){
            iP.removeChild();
            game.setup = 4;
            battleRound=1;
            game.askInput = false;
            endRound = true;
            runAway = false;
        }
        if(game.askInput&&!(enemyCurrentHP==0 ||player.hp==0)&&endRound){
            removeHandler();
            potionUpdate();
            iP.removeChild();
            iP.addChild(new Text("Round "+battleRound+"\n"
            +"What you want to do?"));
            addHandler();
            game.askInput = false;
            
        }
        if(game.askInput&&!(enemyCurrentHP==0 ||player.hp==0)&&!endRound){
            enemyAttk();
            endRound = true;
            game.askInput = false;
        }
        
        
        
    }
    
    public void potionUpdate(){
        if(shop.quantityP1!=0)
            potion1.setText(shop.potion1.getId()+" x"+shop.quantityP1);
        else
            potion1.setText(null);
        
        if(shop.quantityP2!=0)
            potion2.setText(shop.potion2.getId()+" x"+shop.quantityP2);
        else
            potion2.setText(null);
        
        if(shop.quantityP3!=0)
            potion3.setText(shop.potion3.getId()+" x"+shop.quantityP3);
        else
            potion3.setText(null);
    }
    
    
    public void addHandler(){
        attack.setOnAction(attacked);
        run.setOnAction(ran);
        bag.setDisable(false);
        for(MenuItem item:potion.getItems())
            if(item.getText()==null)
                item.setOnAction(null);
    }
    
    public void removeHandler(){
        attack.setOnAction(null);
        run.setOnAction(null);
        bag.setDisable(true);
    }
    
    
    public void handler (){
        
        attacked = new EventHandler <ActionEvent> (){
            
            public void handle(ActionEvent event) {
                iP.removeChild();
                dmg = rand.nextInt(player.maxDmg-player.minDmg)+player.minDmg;
                critChance = rand.nextDouble()*100;
                if(critChance<=player.crit){
                    dmg*=2;
                    iP.addChild(new Text("You attacked and dealt "+dmg+" damage to "+game.ID+" with critical hit!"));
                }
                else
                    iP.addChild(new Text("You attacked and dealt "+dmg+" damage to "+game.ID+" ."));
                enemyCurrentHP -= dmg;
                
                if(enemyCurrentHP>0){
                    if(enemyCurrentHP<0.2*enemyHPI[check])
                        enemyHPBar.setFill(new LinearGradient(0,0,0,1,true,CycleMethod.NO_CYCLE, new Stop(0,Color.CRIMSON), new Stop(1, Color.RED)));
                    
                    enemyHPBar.setWidth(enemyHPBar.getWidth()-dmg*((double)200/enemyHPI[check]));
                    enemyHP.setText(enemyCurrentHP+"/"+enemyHPI[check]);
                    
                    
                }
                else{
                    enemyHPBar.setWidth(0);
                    enemyCurrentHP = 0;
                    enemyHP.setText(enemyCurrentHP+"/"+enemyHPI[check]);
                    player.lvlSystem(enemyXP[check]);
                    play.creditSystem(enemyCash[check]);
                    iP.addChild(new Text("\nYou beat the "+game.ID+"!\n"
                        + "You earn "+enemyXP[check]+" XP and "+enemyCash[check]+" credits.\t[ENTER]"));
                    
                    
                    if(npc.status1){
                        switch(game.ID){
                            case "Skeleton":
                                npc.skeletonCount++;
                                break;
                            case "Goblin":
                                npc.goblinCount++;
                                break;
                            case "Centaur":
                                npc.centaurCount++;
                                break;
                            case "Assasin":
                                npc.assassinCount++;
                                break;
                            case "Dragon":
                                npc.dragonCount++;
                                break;
                        }
                    }
                    
                    if(npc.status2){
                        switch(game.ID){
                            case "Zombie":
                                npc.vikingZombieCount++;
                                break;
                            case "Samurai":
                                npc.japanSamuraiCount++;
                                break;
                            case "Shemhazain":
                                npc.shemhazainCount++;
                                bossDie = true;
                                break;
                        }
                        
                    }
                }
                
                
                endRound = false;
                removeHandler();
                game.askInput = false;
                
            }
        
        };
        
        for(MenuItem item: potion.getItems()){
            item.setOnAction(new EventHandler <ActionEvent> (){
                public void handle(ActionEvent event) {
                    int i;
                    iP.removeChild();
                    for(i=0; i<shop.potionName.length; i++)
                        if(item.getText().contains(shop.potionName[i])){
                            for(potionCheck=0; potionCheck<potion.getItems().size(); potionCheck++)
                                if(item.getText().equals(potion.getItems().get(potionCheck).getText())) break;
                            break;
                        }
                    switch(shop.potionType[i]){
                        case "HP":
                            if(shop.potionCast[i]==1000)
                                iP.addChild(new Text("You use "+shop.potionName[i]+" and recover to full HP.\t[ENTER]"));
                            else
                                iP.addChild(new Text("You use "+shop.potionName[i]+" and recover "+shop.potionCast[i]+" points HP.\t[ENTER]"));
                            player.hp+=shop.potionCast[i];
                            if(player.hp>player.maxhp) player.hp = player.maxhp;
                            if(player.hp>0.2*player.maxhp)
                                playerHPBar.setFill(new LinearGradient(0,0,0,1,true,CycleMethod.NO_CYCLE, new Stop(0,Color.GREEN), new Stop(1, Color.CHARTREUSE)));
                            playerHPBar.setWidth((double)player.hp/player.maxhp*200);
                            playerHP.setText(player.hp+"/"+player.maxhp);
                            switch(potionCheck){
                                case 0:
                                    shop.quantityP1--;
                                    break;
                                case 1:
                                    shop.quantityP2--;
                                    break;
                                case 2:
                                    shop.quantityP3--;
                                    break;
                            }
                            break;
                        case "Crit":
                            iP.addChild(new Text("You use "+shop.potionName[i]+" and increase critical chance by double.\t[ENTER]"));
                            player.crit*=shop.potionCast[i];
                            switch(potionCheck){
                                case 0:
                                    shop.quantityP1--;
                                    break;
                                case 1:
                                    shop.quantityP2--;
                                    break;
                                case 2:
                                    shop.quantityP3--;
                                    break;
                            }
                            break;
                    }
                
                endRound = false;
                removeHandler();
                game.askInput = false;
                
                }
            });
        
        }
        
        ran =  new EventHandler <ActionEvent> (){
            
            public void handle(ActionEvent event) {
                canRun = rand.nextInt(30)+1;
                if(canRun>Integer.parseInt(enemyLvlI[check])-player.level){
                    runAway = true;
                    iP.removeChild();
                    iP.addChild(new Text("You ran away from "+game.ID+" .\t[ENTER]"));
                }
                else{
                    iP.removeChild();
                    iP.addChild(new Text("You can't ran away from "+game.ID+" !\t[ENTER]"));
                    endRound = false;
                }
                removeHandler();
                game.askInput = false;
            }
        
        };
        
    }
    
    public void enemyAttk(){
        iP.removeChild();
        dmg = rand.nextInt(enemyMaxDmg[check]-enemyMinDmg[check])+enemyMinDmg[check];
        if(dmg-player.armor>=0)
            dmg-=player.armor;
        else
            dmg=0;
        player.hp-=dmg;
        
        
        if(player.hp>0){
            if(player.hp<0.2*player.maxhp)
                playerHPBar.setFill(new LinearGradient(0,0,0,1,true,CycleMethod.NO_CYCLE, new Stop(0,Color.CRIMSON), new Stop(1, Color.RED)));
            iP.addChild(new Text(game.ID+" dealt "+(dmg-player.armor)+" damage to you.\t[ENTER]"));
            playerHPBar.setWidth(playerHPBar.getWidth()-dmg*(200/player.maxhp));
            playerHP.setText(player.hp+"/"+player.maxhp);
        }
        else{
            playerHPBar.setWidth(0);
            playerHP.setText("0/"+player.maxhp);
            player.setHP(0);
            iP.addChild(new Text("You beated by "+game.ID+" with "+(dmg-player.armor)+" damage!\n"
                + "You lost "+(player.XP/2)+" XP and "+(int)(play.credit*0.2)+" credits.\t[ENTER]"));
            player.lvlSystem(-1*(player.XP/2));
            play.creditSystem(-1*(int)(play.credit*0.2));  
            play.setPlayerX(350);
            play.setPlayerY(500);
            
        }
        
        battleRound++;
    }
    
    
   
    
    public void trigered(){
        game.root.setCenter(backgroundL);
    }
    
    public void endBattle(){
        game.combatTrigered = false;
        game.setup=0;
        if(player.hp==0) player.setHP(1);
        else player.setHP(player.hp);
        if(oriCrit!=player.crit) player.crit = oriCrit;
        place.clear();
        content.getChildren().clear();
        game.root.setCenter(map.stack);
        
    }
    
    public void enemyInput(){
        enemyNameI = new String [8];
        enemyLvlI = new String [8];
        enemyHPI = new int [8];
        enemyMinDmg = new int [8];
        enemyMaxDmg = new int [8];
        enemyXP = new int [8];
        enemyCash = new int [8];
        try {
            Scanner sc = new Scanner (new FileInputStream ("src/res/Enemy.txt"));
            
            while(sc.hasNext()){
                for (int i=0; i<8; i++){
                    enemyNameI[i]= sc.nextLine();
                    enemyLvlI[i] = sc.nextLine();
                    enemyHPI[i] = Integer.parseInt(sc.nextLine());
                    enemyMinDmg[i] = Integer.parseInt(sc.nextLine());
                    enemyMaxDmg[i] = Integer.parseInt(sc.nextLine());
                    enemyXP[i] = Integer.parseInt(sc.nextLine());
                    enemyCash[i] = Integer.parseInt(sc.nextLine());
                    
                }
            }
            
            
            
            sc.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Combat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
