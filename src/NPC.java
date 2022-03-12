import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class NPC {
    private Game game;
    private map map;
    private interactPlane iP;
    private StackPane stack, NPCView, task;
    protected ImageView NPC;
    private ImageView NPCLarge, taskBoardSmall, taskBoardLarge, taskNo1, taskNo2, taskPaper;
    private int x, y;
    protected int skeletonCount = 3, centaurCount = 3, dragonCount = 3, shemhazainCount = 0, assassinCount=3, goblinCount=3, japanSamuraiCount, vikingZombieCount;
    private Button butt;
    private Text taskText1, taskText2, accept1, accept2, finish, decline;
    private boolean finish1 = false;
    protected boolean status1 = false, status2 = false, finish2 = false;
    private Player player;

    public NPC(Game game, map map, interactPlane iP, Player player) {
        this.game = game;
        this.map = map;
        this.iP = iP;
        this.player = player;
        setNPC();
        Handler();

    }

    public void setNPC() {
        stack = new StackPane();
        NPCView = new StackPane();
        task = new StackPane();

        Random rand = new Random();
        //x = rand.nextInt(650);
        //y = rand.nextInt(500);
        NPC = new ImageView("res/blackSmithSmall.png");
        NPC.setTranslateX(400);
        NPC.setTranslateY(450);
        NPCLarge = new ImageView("res/blackSmithLarge.png");
        NPCLarge.setTranslateX(-20);
        NPCLarge.setTranslateY(-5);

        taskBoardSmall = new ImageView("res/taskBoardSmall.png");
        taskBoardSmall.setTranslateX(-260);
        taskBoardSmall.setTranslateY(-25);

        taskBoardLarge = new ImageView("res/taskBoardLarge.png");
        butt = new Button("X");
        butt.setFont(Font.font("Times New Romen", FontWeight.BOLD, 15));
        butt.setStyle("-fx-background-color:dimgray ; -fx-text-fill:darkred");
        butt.setPrefSize(30, 30);
        butt.setTranslateX(270);
        butt.setTranslateY(-225);
        taskNo1 = new ImageView("res/task1.png");
        taskNo1.setTranslateX(-160);
        taskNo1.setTranslateY(15);
        taskNo2 = new ImageView("res/task2.png");
        taskNo2.setTranslateX(100);
        taskNo2.setTranslateY(15);

        taskPaper = new ImageView("res/taskPaper.png");

        taskText1 = new Text();
        taskText1.setFont(Font.font("Georgia", FontWeight.SEMI_BOLD, 20));
        taskText1.setWrappingWidth(500);
        taskText1.setTranslateX(0);
        taskText1.setTranslateY(-30);

        taskText2 = new Text();
        taskText2.setFont(Font.font("Georgia", FontWeight.SEMI_BOLD, 20));
        taskText2.setWrappingWidth(500);
        taskText2.setTranslateX(0);
        taskText2.setTranslateY(-30);

        accept1 = new Text("ACCEPT");
        accept1.setTranslateX(160);
        accept1.setTranslateY(200);

        accept2 = new Text("ACCEPT");
        accept2.setTranslateX(160);
        accept2.setTranslateY(200);

        decline = new Text("DECLINE");
        decline.setTranslateX(220);
        decline.setTranslateY(200);

        finish = new Text("FINISH");
        finish.setTranslateX(160);
        finish.setTranslateY(200);

        NPCView.setBackground(new Background(new BackgroundImage(new Image("res/blacksmithBackground.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        NPCView.setAlignment(Pos.CENTER_RIGHT);
        NPCView.getChildren().addAll(NPCLarge, taskBoardSmall);
        stack.getChildren().add(NPC);
        map.gamePane.getChildren().add(stack);

    }

    public void update() {
        taskText1.setText("Task 1 \n\n"
                + "God of Chaos had reached here! He released demons to collect spirits for him ."
                + "He need spirits to gain power to destroy the world .They had vanish this town , "
                + "many of our people had die please help us to kill those demons !\n\n "
                + "You need to kill :\n"
                + " 3 x Skeletons (" + skeletonCount + "/3)\n"
                + " 3 x Goblins (" + goblinCount + "/3)\n"
                + " 3 x Centaurs (" + centaurCount + "/3)\n"
                + " 3 x Assassins (" + assassinCount + "/3)\n"
                + " 3 x Dragons (" + dragonCount + "/3)\n"
                + "\n"
                + "You will get :\n"
                + "Credits : 200$ ");
        taskText2.setText("Task 2 \n\n"
                + "Shemhazain , the God of Chaos is out !He had get enough power ! "
                + "Cannot let him left this town ! "
                + "He had bring along with his followers ! Be careful."
                + "Please stop him from destroying this world ! \n\n"
                + "You need to kill :\n"
                + "JapanSamurai (" + japanSamuraiCount + "/1)\n"
                + "VikingZombie (" + vikingZombieCount + "/1)\n"
                + "Shemhazain (BOSS)(" + shemhazainCount + "/1)\n"
                + "\n"
                + "You will get :\n"
                + "Credits : 500$ \n");
    }

    //Handler
    public void Handler() {
        taskBoardSmall.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                if (finish1 == false && finish2 == false) {
                    task.getChildren().addAll(taskBoardLarge, butt, taskNo1, taskNo2);
                    NPCView.getChildren().addAll(task);
                } else if (finish1 == true && finish2 == false) {
                    task.getChildren().addAll(taskBoardLarge, butt, taskNo2);
                    NPCView.getChildren().addAll(task);
                } else if (finish1 == false && finish2 == true) {
                    task.getChildren().addAll(taskBoardLarge, butt, taskNo1);
                    NPCView.getChildren().addAll(task);
                } else if (finish1 == true && finish2 == true) {
                    task.getChildren().addAll(taskBoardLarge, butt);
                    NPCView.getChildren().addAll(task);
                }

            }
        });

        butt.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                task.getChildren().clear();
                NPCView.getChildren().remove(task);
            }
        });

        taskNo1.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                if (status1 == false) {
                    task.getChildren().addAll(taskPaper, taskText1, accept1, decline);
                    iP.removeChild();
                    iP.addChild(new Text("Can you help me ?\nClick ACCEPT or DECLINE ."));
                    //NPCView.getChildren().add(task);
                } else {
                    task.getChildren().addAll(taskPaper, taskText1, finish, decline);
                    //NPCView.getChildren().add(task);
                }
            }
        });

        taskNo2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (status2 == false) {
                    task.getChildren().addAll(taskPaper, taskText2, accept2, decline);
                    iP.removeChild();
                    iP.addChild(new Text("Can you help me ?\nClick ACCEPT or DECLINE ."));
                    //NPCView.getChildren().add(task);
                } else {
                    task.getChildren().addAll(taskPaper, taskText2, finish, decline);
                    //NPCView.getChildren().add(task);
                }
            }
        });

        accept1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                status1 = true;
                task.getChildren().removeAll(taskPaper, taskText1, accept1, decline);
                iP.removeChild();
            }
        });

        accept2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (finish1 == true) {
                    status2 = true;
                    game.isSpawn = true;
                    task.getChildren().removeAll(taskPaper, taskText2, accept2, decline);
                    iP.removeChild();
                } else {
                    iP.removeChild();
                    iP.addChild(new Text("Please finish the first task first. Kill those demons!!"));
                }
            }
        });

        finish.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (status1 == true) {
                    if (skeletonCount >= 3 && centaurCount >= 3 && dragonCount >= 3 && goblinCount >= 3 && assassinCount >= 3) {
                        iP.removeChild();
                        iP.addChild(new Text("You did it ! \nWell done !!\nYou will get what you deserve."));
                        finish1 = true;
                        status1 = false;
                        skeletonCount = 0;
                        centaurCount = 0;
                        dragonCount = 0;
                        goblinCount = 0;
                        assassinCount = 0;
                        player.creditSystem(200);
                        task.getChildren().removeAll(taskPaper, taskText1, accept1, finish, decline, taskNo1);

                    } else {
                        iP.removeChild();
                        iP.addChild(new Text("Those demoms are still there!! Please kill them all before Shemhazain become stronger!!"));
                    }
                }
                if (status2 == true) {
                    if (shemhazainCount == 1 && vikingZombieCount==1 && japanSamuraiCount==1) {
                        iP.removeChild();
                        iP.addChild(new Text("You did it ! \nWell done !!\nYou saved the whole world !!"));
                        finish2 = true;
                        status2 = false;
                        player.creditSystem(500);
                        shemhazainCount = 0;
                        task.getChildren().removeAll(taskPaper, taskText2, accept2, finish, decline, taskNo2);
                    }else {
                        iP.removeChild();
                        iP.addChild(new Text("You did not kill Shemhazain !! You should hurry up ! Not much time left !"));
                    }
                }
            }

        });

        decline.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                task.getChildren().removeAll(taskPaper, taskText1, taskText2, accept1, accept2, finish, decline);
                iP.removeChild();
            }
        });

    }

    public void EnterNPC() {
        iP.removeChild();
        iP.addChild(new Text("Oh so you tried to solo a group dungeon and got slaughtered did you?\n Jolly good show!"));

        game.root.setCenter(NPCView);
    }

    public void LeaveNPC() {
        iP.removeChild();
        task.getChildren().clear();
        NPCView.getChildren().remove(task);
        game.root.setCenter(map.stack);
    }
}