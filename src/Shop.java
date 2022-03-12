import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Shop {
    private Game game;
    private Player player;
    private map map;
    private interactPlane iP;
    private int x, y, index;
    private StackPane stack, shopView, buyPane;
    protected ImageView shop;
    private ImageView shopBig, weaponIcon, weaponIcon1, weaponIcon2, hpPotIcon, mpPotIcon, critPotIcon, buyIcon;
    private Rectangle shopSlot, playerSlot, weaponInfo, potionInfo, playerWeapon, playerPotion, buyWindow, calcBox;
    protected MenuBar shopWeapon, shopPotion, weapon1, weapon2, weapon3, potion1, potion2, potion3;
    private MenuItem sellW1, sellW2, sellW3, sellP1, sellP2, sellP3;
    private Text playerWT, playerPT, shopWeaponT, shopPotionT, buyT, buyN, buyC, buyCalc, quantityP1T, quantityP2T, quantityP3T;
    private ArrayList <MenuItem> weapon = new ArrayList <>();
    private ArrayList <MenuItem> potion = new ArrayList <>();
    protected String[] weaponName,potionName, potionEffect, potionType;
    protected int[] weaponMinDmg, weaponMaxDmg, weaponCrit, weaponCost, potionCost, potionCast;
    private Button buyW, buyP, confirm, cancel, exitBuy, exitShop;
    private String selectedWeapon;
    private EventHandler buy, sell;
    protected int quantityP1=0, quantityP2=0, quantityP3=0;
    
    public Shop(Game game, Player player, map map, interactPlane iP){
        this.game = game;
        this.player = player;
        this.map = map;
        this.iP = iP;
        
        setupShop();
        buy();
        Handler();
        
        sellW1.setOnAction(sell);
        sellW2.setOnAction(sell);
        sellW3.setOnAction(sell);
        sellP1.setOnAction(sell);
        sellP2.setOnAction(sell);
        sellP3.setOnAction(sell);
        
    }
    
    public void setupShop(){
        stack = new StackPane();
        shopView = new StackPane();
        buyPane = new StackPane();
        
        Random rand = new Random();
        shop = new ImageView ("res/shopSmall.png");
        shopBig = new ImageView ("res/ShopLarge.png");
        
        shopSlot = new Rectangle(375, 500, Color.ANTIQUEWHITE);
        shopSlot.setOpacity(0.5);
        shopSlot.setArcWidth(10);
        shopSlot.setArcHeight(10);
        
        playerSlot = new Rectangle(250, 500, Color.BISQUE);
        playerSlot.setOpacity(0.5);
        playerSlot.setArcHeight(10);
        playerSlot.setArcWidth(10);
        
        weaponInfo = new Rectangle(355, 150, Color.BURLYWOOD);
        weaponInfo.setOpacity(0.7);
        weaponInfo.setArcWidth(10);
        weaponInfo.setArcHeight(10);
        weaponInfo.setEffect(new DropShadow(10, 3, 10, Color.BLACK));
        
        shopWeaponT = new Text();
        shopWeaponT.setWrappingWidth(325);
        
        potionInfo = new Rectangle(355, 150, Color.BURLYWOOD);
        potionInfo.setOpacity(0.7);
        potionInfo.setArcWidth(10);
        potionInfo.setArcHeight(10);
        potionInfo.setEffect(new DropShadow(10, 3, 10, Color.BLACK));
        
        shopPotionT = new Text();
        shopPotionT.setWrappingWidth(325);
        
        playerWeapon = new Rectangle(230, 50, Color.BURLYWOOD);
        playerWeapon.setOpacity(0.7);
        playerWeapon.setArcWidth(10);
        playerWeapon.setArcHeight(10);
        playerWeapon.setEffect(new DropShadow(10, 3, 10, Color.BLACK));
        
        playerWT = new Text("Weapon");
        playerWT.setFont(Font.font("TimeNewRomans", FontWeight.BOLD, 20));
        
        playerPotion = new Rectangle(230, 50, Color.BURLYWOOD);
        playerPotion.setOpacity(0.7);
        playerPotion.setArcWidth(10);
        playerPotion.setArcHeight(10);
        playerPotion.setEffect(new DropShadow(10, 3, 10, Color.BLACK));
        
        playerPT = new Text("Potion");
        playerPT.setFont(Font.font("TimeNewRomans", FontWeight.BOLD, 20));
        
        //x = rand.nextInt(650);
        //y = rand.nextInt(500);
        
        shop.setTranslateX(300);
        shop.setTranslateY(450);
        
        shopSlot.setTranslateX(25);
        shopSlot.setTranslateY(-25);
        
        weaponInfo.setTranslateX(35);
        weaponInfo.setTranslateY(-300);
        
        shopWeaponT.setTranslateX(50);
        shopWeaponT.setTranslateY(-355);
        
        potionInfo.setTranslateX(35);
        potionInfo.setTranslateY(-50);
        
        shopPotionT.setTranslateX(50);
        shopPotionT.setTranslateY(-100);
        
        playerSlot.setTranslateX(425);
        playerSlot.setTranslateY(-25);
        
        playerWeapon.setTranslateX(435);
        playerWeapon.setTranslateY(-465);
        
        playerWT.setTranslateX(515);
        playerWT.setTranslateY(-477);
        
        playerPotion.setTranslateX(435);
        playerPotion.setTranslateY(-225);
        
        playerPT.setTranslateX(525);
        playerPT.setTranslateY(-232);
        
        
        
        inputInfo();
        Menu();
        
        shopView.setBackground(new Background(new BackgroundImage(new Image("res/shopBackground.png"), 
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        
        shopView.getChildren().addAll(playerSlot, shopSlot, shopBig, weaponInfo, potionInfo, playerWeapon, playerPotion, playerWT, playerPT,
                shopWeapon, shopPotion, weapon1, weapon2, weapon3, potion1, potion2, potion3, shopWeaponT, shopPotionT, buyP, buyW, quantityP1T, quantityP2T, quantityP3T);
        shopView.setAlignment(Pos.BOTTOM_LEFT);
        stack.getChildren().add(shop);
        map.gamePane.getChildren().add(stack);
    }
    
    public void Menu(){
        shopWeapon = new MenuBar(new Menu("WEAPON"));
        shopWeapon.setStyle("-fx-font-size: 20;"+"-fx-font-weight: bold");
        shopWeapon.setPadding(new Insets(7,0,0,120));
        shopWeapon.setPrefSize(355, 50);
        shopWeapon.setMaxWidth(355);
        index=0;
        for(String weapon:weaponName){
            this.weapon.add(new MenuItem(weapon));
            shopWeapon.getMenus().get(0).getItems().add(this.weapon.get(index));
            index++;
        }
        shopWeapon.setEffect(new DropShadow(10, 3, 10, Color.BLACK));
        
        shopPotion = new MenuBar(new Menu("POTION"));
        shopPotion.setPrefSize(355, 50);
        shopPotion.setMaxWidth(355);
        index=0;
        shopPotion.setStyle("-fx-font-size: 20;"+"-fx-font-weight: bold");
        shopPotion.setPadding(new Insets(7,0,0,130));
        for(String potion:potionName){
            this.potion.add(new MenuItem(potion));
            shopPotion.getMenus().get(0).getItems().add(this.potion.get(index));
            index++;
        }
        shopPotion.setEffect(new DropShadow(10, 3, 10, Color.BLACK));
        
        
        sellW1 = new MenuItem("Sell");
        sellW2 = new MenuItem("Sell");
        sellW3 = new MenuItem("Sell");
        sellP1 = new MenuItem("Sell");
        sellP2 = new MenuItem("Sell");
        sellP3 = new MenuItem("Sell");
        
        weaponIcon = new ImageView ("res/Wooden Sword Icon.png");
        weaponIcon1 = new ImageView ();
        weaponIcon2 = new ImageView ();
        hpPotIcon = new ImageView ();
        mpPotIcon = new ImageView ();
        critPotIcon = new ImageView ();
        
        weapon1 = new MenuBar(new Menu());
        weapon1.setPrefSize(75, 75);
        weapon1.setMaxSize(75, 75);
        weapon1.getMenus().get(0).getItems().addAll(sellW1);
        weapon1.getMenus().get(0).setGraphic(weaponIcon);
        weapon1.setId("Wooden Sword");
        weapon1.setPadding(new Insets(2,0,0,0));
        weapon1.setEffect(new DropShadow(10, 3, 10, Color.BLACK));
        
        weapon2 = new MenuBar(new Menu());
        weapon2.setPrefSize(75, 75);
        weapon2.setMaxSize(75, 75);
        weapon2.getMenus().get(0).getItems().addAll(sellW2);
        weapon2.setId(null);
        weapon2.getMenus().get(0).setDisable(true);
        weapon2.setPadding(new Insets(2,0,0,0));
        weapon2.setEffect(new DropShadow(10, 3, 10, Color.BLACK));
        
        weapon3 = new MenuBar(new Menu());
        weapon3.setPrefSize(75, 75);
        weapon3.setMaxWidth(75);
        weapon3.getMenus().get(0).getItems().addAll(sellW3);
        weapon3.setId(null);
        weapon3.getMenus().get(0).setDisable(true);
        weapon3.setPadding(new Insets(2,0,0,0));
        weapon3.setEffect(new DropShadow(10, 3, 10, Color.BLACK));
        
        potion1 = new MenuBar(new Menu());
        potion1.setPrefSize(75, 75);
        potion1.setMaxWidth(75);
        potion1.getMenus().get(0).getItems().add(sellP1);
        potion1.getMenus().get(0).setDisable(true);
        potion1.setPadding(new Insets(2,0,0,0));
        potion1.setEffect(new DropShadow(10, 3, 10, Color.BLACK));
        
        quantityP1T = new Text();
        quantityP1T.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        quantityP1T.setFill(Color.FIREBRICK);
        quantityP1T.setTranslateX(515);
        quantityP1T.setTranslateY(-120);
        
        quantityP2T = new Text();
        quantityP2T.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        quantityP2T.setFill(Color.FIREBRICK);
        quantityP2T.setTranslateX(610);
        quantityP2T.setTranslateY(-120);
        
        quantityP3T = new Text();
        quantityP3T.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        quantityP3T.setFill(Color.FIREBRICK);
        quantityP3T.setTranslateX(560);
        quantityP3T.setTranslateY(-35);
        
        potion2 = new MenuBar(new Menu());
        potion2.setPrefSize(75, 75);
        potion2.setMaxWidth(75);
        potion2.getMenus().get(0).getItems().add(sellP2);
        potion2.getMenus().get(0).setDisable(true);
        potion2.setPadding(new Insets(2,0,0,0));
        potion2.setEffect(new DropShadow(10, 3, 10, Color.BLACK));
        
        potion3 = new MenuBar(new Menu());
        potion3.setPrefSize(75, 75);
        potion3.setMaxWidth(75);
        potion3.getMenus().get(0).getItems().add(sellP3);
        potion3.getMenus().get(0).setDisable(true);
        potion3.setPadding(new Insets(2,0,0,0));
        potion3.setEffect(new DropShadow(10, 3, 10, Color.BLACK));
        
        buyW = new Button("Buy");
        buyW.setPrefSize(50, 20);
        buyW.setEffect(new DropShadow(10, 3, 3, Color.BLACK));
        
        buyP = new Button("Buy");
        buyP.setPrefSize(50, 20);
        buyP.setEffect(new DropShadow(10, 3, 3, Color.BLACK));
        
        shopWeapon.setTranslateX(35);
        shopWeapon.setTranslateY(-465);
        
        shopPotion.setTranslateX(35);
        shopPotion.setTranslateY(-225);
        
        weapon1.setTranslateX(465);
        weapon1.setTranslateY(-375);
        
        weapon2.setTranslateX(560);
        weapon2.setTranslateY(-375);
        
        weapon3.setTranslateX(510);
        weapon3.setTranslateY(-290);
        
        potion1.setTranslateX(465);
        potion1.setTranslateY(-125);
        
        potion2.setTranslateX(560);
        potion2.setTranslateY(-125);
        
        potion3.setTranslateX(510);
        potion3.setTranslateY(-40);
        
        buyW.setTranslateX(330);
        buyW.setTranslateY(-305);
        
        buyP.setTranslateX(330);
        buyP.setTranslateY(-55);
        
        
    }
    
    public void inputInfo(){
        weaponName = new String [10];
        weaponMinDmg = new int [10];
        weaponMaxDmg = new int [10];
        weaponCrit = new int [10];
        weaponCost = new int [10];
        try {
            Scanner sc = new Scanner (new FileInputStream ("src/res/Weapon.txt"));
            while(sc.hasNext()){
                for(int i=0; i<10; i++){
                    weaponName[i] = sc.nextLine();
                    weaponMinDmg[i] = Integer.parseInt(sc.nextLine());
                    weaponMaxDmg[i] = Integer.parseInt(sc.nextLine());
                    weaponCrit[i] = Integer.parseInt(sc.nextLine());
                    weaponCost[i] = Integer.parseInt(sc.nextLine());
                }
            }
            
            sc.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
        potionName = new String [5];
        potionType = new String [5];
        potionEffect = new String [5];
        potionCast = new int [5];
        potionCost = new int [5];
        try {
            Scanner sc = new Scanner (new FileInputStream ("src/res/Potion.txt"));
            while(sc.hasNext()){
                for(int i=0; i<5; i++){
                    potionName[i] = sc.nextLine();
                    potionType[i] = sc.nextLine();
                    potionEffect[i] = sc.nextLine();
                    potionCast[i]=Integer.parseInt(sc.nextLine());
                    potionCost[i] = Integer.parseInt(sc.nextLine());
                }
            }
            
            sc.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void buy(){
        buyWindow = new Rectangle (600, 400, Color.ALICEBLUE);
        buyWindow.setOpacity(0.8);
        buyWindow.setArcWidth(5);
        buyWindow.setArcHeight(5);
        buyWindow.setEffect(new DropShadow(10, 10, 10, Color.BLACK));
        
        buyT = new Text();
        buyT.setFont(Font.font("TimeNewRomans", FontWeight.BOLD, 40));
        buyT.setEffect(new DropShadow(10, 0, 5, Color.BLACK));
        buyT.setFill(new LinearGradient(0,0,0,1,true,CycleMethod.NO_CYCLE, new Stop(0,Color.BURLYWOOD), new Stop(1, Color.GOLD)));
        
        buyN = new Text();
        buyN.setFont(Font.font("TimeNewRomans", FontWeight.BOLD, 25));
        
        buyIcon = new ImageView();
        buyIcon.setEffect(new InnerShadow(5, 3, 3, Color.BLACK));
        
        buyC = new Text();
        buyC.setFont(Font.font("TimeNewRomans", FontWeight.SEMI_BOLD, 20));
        
        buyCalc = new Text();
        buyCalc.setTextAlignment(TextAlignment.LEFT);
        buyCalc.setFont(Font.font("TimeNewRomans", FontWeight.BOLD, 15));
        
        confirm = new Button("Confirm");
        confirm.setPrefSize(75, 20);
        confirm.setEffect(new DropShadow(5, 3, 3, Color.BLACK));
        
        cancel = new Button("Cancel");
        cancel.setPrefSize(75, 20);
        cancel.setEffect(new DropShadow(5, 3, 3, Color.BLACK));
        
        calcBox = new Rectangle(225, 280, Color.CORNSILK);
        calcBox.setEffect(new InnerShadow(5, 3, 3, Color.BLACK));
        
        buyT.setTranslateX(0);
        buyT.setTranslateY(-200);
        
        buyN.setTranslateX(-180);
        buyN.setTranslateY(-140);
        
        buyIcon.setTranslateX(-130);
        buyIcon.setTranslateY(0);
        
        buyC.setTranslateX(-200);
        buyC.setTranslateY(130);
        
        calcBox.setTranslateX(165);
        calcBox.setTranslateY(0);
        
        buyCalc.setTranslateX(165);
        buyCalc.setTranslateY(0);
        
        confirm.setTranslateX(170);
        confirm.setTranslateY(175);
        
        cancel.setTranslateX(250);
        cancel.setTranslateY(175);
        
        buyPane.getChildren().addAll(buyWindow, buyT, buyN, buyIcon, buyC, 
                calcBox, buyCalc, confirm, cancel);
    }
    
    public void Handler(){
        for(MenuItem item:shopWeapon.getMenus().get(0).getItems()){
            
            item.setOnAction(new EventHandler <ActionEvent>(){
            
            public void handle(ActionEvent event) {
                for(int i=0; i<weaponName.length; i++){
                if(item.getText().equals(weaponName[i]))
                    index = i;
                }
                selectedWeapon = weaponName[index];
                shopWeaponT.setText("Name: "+weaponName[index]+
                        "\nDamage: "+weaponMinDmg[index]+" ~ "+weaponMaxDmg[index]+
                        "\nCritical Chance: "+weaponCrit[index]+"%"+
                        "\nCost: "+weaponCost[index]+" $");
                
                buyW.setOnAction(buy);
            }
            
            
        });
        }
        
        for(MenuItem item:shopPotion.getMenus().get(0).getItems()){
            
            item.setOnAction(new EventHandler <ActionEvent>(){
            
            public void handle(ActionEvent event) {
                for(int i=0; i<potionName.length; i++){
                if(item.getText().equals(potionName[i]))
                    index = i;
                }
                shopPotionT.setText("Name: "+potionName[index]+
                        "\nEffect: \n"+potionEffect[index]+
                        "\nCost: "+potionCost[index]+" $");
                
                buyP.setOnAction(buy);
            }
            
            
        });
        }
        
        buy = new EventHandler <ActionEvent>(){
            
            public void handle(ActionEvent event) {
                if(event.getSource()==buyW){
                Image image = new Image("res/"+weaponName[index]+".png");
                buyIcon.setImage(image);
                buyT.setText("Weapon Shop");
                buyN.setText(weaponName[index]);
                buyC.setText("Cost: "+weaponCost[index]+" $");
                buyCalc.setText("Purchasement\n\n\n"
                        + "Before Purchase:\t\t"+player.credit+
                        "\n\nCost:\t\t\t\t"+weaponCost[index]+
                        "\n\n-------------------------------\n\n"+
                        "\nAfter Purchase:\t\t"+(player.credit-weaponCost[index]));
                shopView.getChildren().add(buyPane);
                confirm.setId("Weapon");
                iP.removeChild();
                iP.addChild(new Text("Are you confirm to buy this weapon?\n"
                        + "If yes, press confirm.\n"
                        + "If no, press cancel."));
            }
            else{
                Image image = new Image("res/"+potionName[index]+".png");
                buyIcon.setImage(image);
                buyT.setText("Potion Shop");
                buyN.setText(potionName[index]);
                buyC.setText("Cost: "+potionCost[index]+" $");
                buyCalc.setText("Purchasement\n\n\n"
                        + "Before Purchase:\t\t"+player.credit+
                        "\n\nCost:\t\t\t\t"+potionCost[index]+
                        "\n\n-------------------------------\n\n"+
                        "\nAfter Purchase:\t\t"+(player.credit-potionCost[index]));
                shopView.getChildren().add(buyPane);
                confirm.setId("Potion");
                iP.removeChild();
                iP.addChild(new Text("Are you confirm to buy this potion?\n"
                        + "If yes, press confirm.\n"
                        + "If no, press cancel."));
        }
        
        }};
        
        cancel.setOnAction(new EventHandler <ActionEvent>(){
            
            public void handle(ActionEvent event) {
                iP.removeChild();
                shopView.getChildren().remove(buyPane);
            }
        
        });
        
        confirm.setOnAction(new EventHandler <ActionEvent>(){
            
            public void handle(ActionEvent event) {
                if(confirm.getId().equals("Weapon")){
                    if(weapon1.getMenus().get(0).isDisable()==true||weapon2.getMenus().get(0).isDisable()==true||weapon3.getMenus().get(0).isDisable()==true)
                        if(player.credit>=weaponCost[index]){
                            if(weapon1.getMenus().get(0).isDisable()==true){
                                weapon1.getMenus().get(0).setGraphic(new ImageView("res/"+weaponName[index]+" Icon.png"));
                                weapon1.getMenus().get(0).getGraphic().setVisible(true);
                                weapon1.getMenus().get(0).setDisable(false);
                                weapon1.setId(weaponName[index]);
                            }
                            else if(weapon2.getMenus().get(0).isDisable()==true){
                                weapon2.getMenus().get(0).setGraphic(new ImageView("res/"+weaponName[index]+" Icon.png"));
                                weapon2.getMenus().get(0).getGraphic().setVisible(true);
                                weapon2.getMenus().get(0).setDisable(false);
                                weapon2.setId(weaponName[index]);
                            }
                            else{
                                weapon3.getMenus().get(0).setGraphic(new ImageView("res/"+weaponName[index]+" Icon.png"));
                                weapon3.getMenus().get(0).getGraphic().setVisible(true);
                                weapon3.getMenus().get(0).setDisable(false);
                                weapon3.setId(weaponName[index]);
                            }
                            player.creditSystem(-1*weaponCost[index]);
                            shopView.getChildren().remove(buyPane);
                            iP.removeChild();
                            iP.addChild(new Text("You bought the "+weaponName[index]+"!\n"
                                    + "Thank you for your purchasement.\n"
                                    + "Welcome again!"));
                        }
                        else{
                            iP.removeChild();
                            iP.addChild(new Text("You have not enough credit to buy this stuff!\n"
                                    + "Please confirm you have enough money before you make purchasement!"));
                        }
                    else{
                        iP.removeChild();
                        iP.addChild(new Text("Your weapon slots are already full!\n"
                                + "Please make sure you have enough slots to buy this stuff!"));
                    }
                }
                else{
                    if(potion1.getMenus().get(0).isDisable()==true||potion2.getMenus().get(0).isDisable()==true||potion3.getMenus().get(0).isDisable()==true
                            ||potionName[index].equals(potion1.getId())||potionName[index].equals(potion2.getId())||potionName[index].equals(potion3.getId()))
                        if(player.credit>=potionCost[index]){
                            if(potionName[index].equals(potion1.getId())){
                                quantityP1++;
                                quantityP1T.setText("x"+quantityP1);
                            }
                            else if(potionName[index].equals(potion2.getId())){
                                quantityP2++;
                                quantityP2T.setText("x"+quantityP2);
                            }
                            else if(potionName[index].equals(potion3.getId())){
                                quantityP3++;
                                quantityP3T.setText("x"+quantityP3);
                            }
                            else if(potion1.getMenus().get(0).isDisable()==true){
                                potion1.getMenus().get(0).setGraphic(new ImageView("res/"+potionName[index]+" Icon.png"));
                                potion1.getMenus().get(0).getGraphic().setVisible(true);
                                potion1.getMenus().get(0).setDisable(false);
                                potion1.setId(potionName[index]);
                                quantityP1 = 1;
                                quantityP1T.setText("x"+quantityP1);
                            }
                            else if(potion2.getMenus().get(0).isDisable()==true){
                                potion2.getMenus().get(0).setGraphic(new ImageView("res/"+potionName[index]+" Icon.png"));
                                potion2.getMenus().get(0).getGraphic().setVisible(true);
                                potion2.getMenus().get(0).setDisable(false);
                                potion2.setId(potionName[index]);
                                quantityP2 = 1;
                                quantityP2T.setText("x"+quantityP2);
                            }
                            else{
                                potion3.getMenus().get(0).setGraphic(new ImageView("res/"+potionName[index]+" Icon.png"));
                                potion3.getMenus().get(0).getGraphic().setVisible(true);
                                potion3.getMenus().get(0).setDisable(false);
                                potion3.setId(potionName[index]);
                                quantityP3 = 1;
                                quantityP3T.setText("x"+quantityP3);
                            }
                            player.creditSystem(-1*potionCost[index]);
                            shopView.getChildren().remove(buyPane);
                            iP.removeChild();
                            iP.addChild(new Text("You bought the "+potionName[index]+"!\n"
                                    + "Thank you for your purchasement.\n"
                                    + "Welcome again!"));
                        }
                        else{
                            iP.removeChild();
                            iP.addChild(new Text("You have not enough credit to buy this stuff!\n"
                                    + "Please confirm you have enough money before you make purchasement!"));
                        }
                else{
                    iP.removeChild();
                    iP.addChild(new Text("Your potion slots are already full!\n"
                            + "Please make sure you have enough slots to buy this stuff!"));
                }
                }
            }
        
        });
        
        sell = new EventHandler <ActionEvent>(){
            
            public void handle(ActionEvent event) {
                int j;
                if(event.getSource()==sellW1){
                    weapon1.getMenus().get(0).getGraphic().setVisible(false);
                    weapon1.getMenus().get(0).setDisable(true);
                    for(j=0; j<weaponName.length; j++)
                        if(weapon1.getId().equals(weaponName[j]))
                            break;
                    player.creditSystem(weaponCost[j]);
                    iP.removeChild();
                    iP.addChild(new Text("You sold your "+weaponName[j]+"!\n"
                            + "You get "+weaponCost[j]+" $ as rewards."));
                    
                    weapon1.setId(null);
                }
                else if(event.getSource()==sellW2){
                    weapon2.getMenus().get(0).getGraphic().setVisible(false);
                    weapon2.getMenus().get(0).setDisable(true);
                    for(j=0; j<weaponName.length; j++)
                        if(weapon2.getId().equals(weaponName[j]))
                            break;
                    player.creditSystem(weaponCost[j]);
                    iP.removeChild();
                    iP.addChild(new Text("You sold your "+weaponName[j]+"!\n"
                            + "You get "+weaponCost[j]+" $ as rewards."));
                    
                    weapon2.setId(null);
                }
                else if(event.getSource()==sellW3){
                    weapon3.getMenus().get(0).getGraphic().setVisible(false);
                    weapon3.getMenus().get(0).setDisable(true);
                    for(j=0; j<weaponName.length; j++)
                        if(weapon3.getId().equals(weaponName[j]))
                            break;
                    player.creditSystem(weaponCost[j]);
                    iP.removeChild();
                    iP.addChild(new Text("You sold your "+weaponName[j]+"!\n"
                            + "You get "+weaponCost[j]+" $ as rewards."));
                    weapon3.setId(null);
                }
                else if(event.getSource()==sellP1){
                    potion1.getMenus().get(0).getGraphic().setVisible(false);
                    potion1.getMenus().get(0).setDisable(true);
                    for(j=0; j<potionName.length; j++)
                        if(potion1.getId().equals(potionName[j]))
                            break;
                    player.creditSystem(potionCost[j]*quantityP1);
                    iP.removeChild();
                    iP.addChild(new Text("You sold your "+potionName[j]+"!\n"
                            + "You get "+potionCost[j]*quantityP1+" $ as rewards."));
                    
                    potion1.setId(null);
                    quantityP1T.setText(null);
                }
                else if(event.getSource()==sellP2){
                    potion2.getMenus().get(0).getGraphic().setVisible(false);
                    potion2.getMenus().get(0).setDisable(true);
                    for(j=0; j<potionName.length; j++)
                        if(potion2.getId().equals(potionName[j]))
                            break;
                    player.creditSystem(potionCost[j]*quantityP2);
                    iP.removeChild();
                    iP.addChild(new Text("You sold your "+potionName[j]+"!\n"
                            + "You get "+potionCost[j]*quantityP2+" $ as rewards."));
                    
                    potion2.setId(null);
                    quantityP2T.setText(null);
                }
                else if(event.getSource()==sellP3){
                    potion3.getMenus().get(0).getGraphic().setVisible(false);
                    potion3.getMenus().get(0).setDisable(true);
                    for(j=0; j<potionName.length; j++)
                        if(potion3.getId().equals(potionName[j]))
                            break;
                    player.creditSystem(potionCost[j]*quantityP3);
                    iP.removeChild();
                    iP.addChild(new Text("You sold your "+potionName[j]+"!\n"
                            + "You get "+potionCost[j]*quantityP3+" $ as rewards."));
                    
                    potion3.setId(null);
                    quantityP3T.setText(null);
                }
            }
        
        };
        
    }
    
    
    public void enterShop(){
        iP.removeChild();
        iP.addChild(new Text("Welcome to my shop!\n"
                + "What are you looking for?"));
        game.root.setCenter(shopView);
    }
    
    public void leaveShop(){
        shopWeaponT.setText("");
        shopPotionT.setText("");
        buyW.setOnAction(null);
        buyP.setOnAction(null);
        iP.removeChild();
        shopView.getChildren().remove(buyPane);
        game.root.setCenter(map.stack);
    }
    
    public double getShopX(){
        return shop.getTranslateX();
    }
    
    public double getShopY(){
        return shop.getTranslateY();
    }
    
}
