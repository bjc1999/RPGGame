import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class playerInfo {
    protected VBox charWin;
    private Game game;
    //private Combat combat;
    private MenuItem weapon1, weapon2, weapon3;
    private Text weaponAtt1, weaponAtt2, weaponAtt3, weaponAtt5;
    private Text hpT, levelT, creditT, maxhpT, damageT, armorT, critical;
    private MenuBar bar, weaponbar, attributebar;
    private int lvltemp;
    protected int hp=200, armor=10, level=16, maxhp=200, minDmg=0, maxDmg = 10, XP=0, maxXP=2, crit=0;
    private Shop shop;
    private Player player;
    
    public playerInfo(Game game, Player player, Shop shop){
        this.game = game;
        this.player = player;
        this.shop = shop;
        setting();
        render();
    }
    
    public VBox setting(){
        charWin = new VBox();
        charWin.setSpacing(10);
        charWin.setPrefWidth(140);
        charWin.setMaxWidth(140);
        charWin.setMinWidth(140);
        charWin.setStyle("-fx-background-color: floralwhite;");
        
        hpT = new Text("  H/P: "+hp);
        levelT = new Text("  Lvl: "+level+"\t"+ XP+" / "+maxXP);
        creditT = new Text("  Credit: "+player.credit+" $");
        
        MenuBar bar = new MenuBar();
        Menu charInfo = new Menu("    Character    ");
        
        bar.getMenus().add(charInfo);
        
        MenuBar attributebar = new MenuBar();
        Menu attribute = new Menu("     Attribute    ");
        
        maxhpT = new Text("  Max H/P: "+maxhp);
        damageT = new Text("  Damage: "+minDmg+" ~ "+maxDmg);
        armorT = new Text("  Armor: "+armor);
        critical = new Text("  Critical: "+crit+" %");
        
        attributebar.getMenus().add(attribute);
        
        
        MenuBar weaponbar = new MenuBar();
        Menu weapon = new Menu("     Weapon     ");
        
        weapon1 = new MenuItem (shop.weapon1.getId());
        weapon2 = new MenuItem (shop.weapon2.getId());
        weapon3 = new MenuItem (shop.weapon3.getId());
        
        weapon.getItems().addAll(weapon1 ,weapon2, weapon3);
        
        weaponbar.getMenus().add(weapon);
        
        charWin.getChildren().addAll(bar, hpT, levelT, creditT, attributebar, maxhpT, damageT, armorT, critical, weaponbar);
        return charWin;
    }
    
    public void update(){
        
        String newDmg = "  Damage: "+minDmg+" ~ "+maxDmg;
        damageT.setText(newDmg);
        hpT.setText("  H/P: "+hp);
        maxhpT.setText("  Max H/P: "+maxhp);
        levelT.setText("  Lvl: "+level+"\t"+ XP+" / "+maxXP);
        creditT.setText("  Credit: "+player.credit+" $");
        critical.setText("  Critical: "+crit+" %");
        
        weapon1.setText(shop.weapon1.getId());
        weapon2.setText(shop.weapon2.getId());
        weapon3.setText(shop.weapon3.getId());
    }
    
    public void setHP(int hp){
        this.hp = hp;
    }
    
    public void lvlSystem(int xp){
        lvltemp = XP + xp;
        if(lvltemp<=maxXP)
            XP = lvltemp;
        while(maxXP<=lvltemp){
            level++;
            XP = lvltemp - maxXP;
            lvltemp = XP;
            maxXP+=4;
            maxhp+=10;
            hp=maxhp;
            armor+=1;
            maxDmg+=10;
            
        }
    }
    
    
    
    
    public void render(){
        
        weapon1.setOnAction(new EventHandler <ActionEvent>(){
            
            public void handle(ActionEvent event) {
                int check=0;
                int i=0;
                if(weapon1.getText()!=null){
                for(i=0; i<10; i++){
                    if(weapon1.getText().equals(shop.weaponName[i])){
                        check = i;
                        break;
                    }
                }
                charWin.getChildren().removeAll(weaponAtt1, weaponAtt2, weaponAtt3, weaponAtt5);
                
                //reset damage
                minDmg=0;
                maxDmg=10;
                minDmg+=shop.weaponMinDmg[check];
                maxDmg+=shop.weaponMaxDmg[check];
                crit = shop.weaponCrit[check];
                
                
                weaponAtt1 = new Text(" "+shop.weaponName[check]);
                weaponAtt1.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                weaponAtt2 = new Text("  Dmg(Min): "+shop.weaponMinDmg[check]);
                weaponAtt3 = new Text("  Dmg(Max): "+shop.weaponMaxDmg[check]);
                weaponAtt5 = new Text("  Crit: "+shop.weaponCrit[check]+" %");
                
                
                charWin.getChildren().addAll(weaponAtt1, weaponAtt2, weaponAtt3, weaponAtt5);
                    
            }
            else{
                charWin.getChildren().removeAll(weaponAtt1, weaponAtt2, weaponAtt3, weaponAtt5);
                
                minDmg=0;
                maxDmg=10;
                crit = 0;
            }
            }
            
            
        });
        
        weapon2.setOnAction(new EventHandler <ActionEvent>(){
            
            public void handle(ActionEvent event) {
                int check=0;
                int i=0;
                if(weapon2.getText()!=null){
                for(i=0; i<10; i++){
                    if(weapon2.getText().equals(shop.weaponName[i])){
                        check = i;
                        break;
                    }
                }
                charWin.getChildren().removeAll(weaponAtt1, weaponAtt2, weaponAtt3, weaponAtt5);
                
                //reset damage
                minDmg=0;
                maxDmg=10;
                minDmg+=shop.weaponMinDmg[check];
                maxDmg+=shop.weaponMaxDmg[check];
                crit = shop.weaponCrit[check];

                
                weaponAtt1 = new Text(" "+shop.weaponName[check]);
                weaponAtt1.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                weaponAtt2 = new Text("  Dmg(Min): "+shop.weaponMinDmg[check]);
                weaponAtt3 = new Text("  Dmg(Max): "+shop.weaponMaxDmg[check]);
                weaponAtt5 = new Text("  Crit: "+shop.weaponCrit[check]+" %");
                
                charWin.getChildren().addAll(weaponAtt1, weaponAtt2, weaponAtt3, weaponAtt5);
                
                    
            }
            
            else{
                charWin.getChildren().removeAll(weaponAtt1, weaponAtt2, weaponAtt3, weaponAtt5);
                minDmg=0;
                maxDmg=10;
                crit = 0;
            }
            }
            
            
        });
        
        
        weapon3.setOnAction(new EventHandler <ActionEvent>(){
            
            public void handle(ActionEvent event) {
                int check=0;
                int i=0;
                if(weapon3.getText()!=null){
                for(i=0; i<10; i++){
                    if(weapon3.getText().equals(shop.weaponName[i])){
                        check = i;
                        break;
                    }
                }
                charWin.getChildren().removeAll(weaponAtt1, weaponAtt2, weaponAtt3, weaponAtt5);
                
                //reset damage
                minDmg=0;
                maxDmg=10;
                minDmg+=shop.weaponMinDmg[check];
                maxDmg+=shop.weaponMaxDmg[check];
                crit = shop.weaponCrit[check];

                
                weaponAtt1 = new Text(" "+shop.weaponName[check]);
                weaponAtt1.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                weaponAtt2 = new Text("  Dmg(Min): "+shop.weaponMinDmg[check]);
                weaponAtt3 = new Text("  Dmg(Max): "+shop.weaponMaxDmg[check]);
                weaponAtt5 = new Text("  Crit: "+shop.weaponCrit[check]+" %");
                
                charWin.getChildren().addAll(weaponAtt1, weaponAtt2, weaponAtt3, weaponAtt5);
                
                    
            }
            
            else{
                charWin.getChildren().removeAll(weaponAtt1, weaponAtt2, weaponAtt3, weaponAtt5);
                minDmg=0;
                maxDmg=10;
                crit = 0;
            }
            }
            
            
        });
    }
}
