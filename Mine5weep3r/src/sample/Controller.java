package sample;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.RED;

public class Controller implements Initializable {

    public TextField Xt;
    public TextField Yt;
    public TextField Percentt;

    public Slider X;
    public Slider Y;
    public Slider perc;

    public BorderPane gamePage;
    public GridPane game;
    public HBox header;
    public ImageView clock;
    public Label time;
    public Label status;
    public ImageView bomb;
    public Label bombCount;
    public Button newGameB;
    public Button changeSizeB;
    public Button COMMENCEHACK;

    public ImageView[][] blocks;

    public Boolean[][] isBomb;
    public Boolean[][] isFlagged;
    public Boolean[][] isCleared;

    public AnimationTimer stopWatch;

    public IntegerProperty bombCountNum = new SimpleIntegerProperty();

    public double empty;
    public double cleared;
    public int botCleared;

    public static int xSize;
    public static int ySize;
    public int percent;

    public Boolean playing;

    public ArrayList<String> perms;
    public ArrayList<Double> odds;
    public ArrayList<String[]> results;

    public void Game() throws IOException {
        playing=true;
        empty=0;
        cleared=0;
        botCleared=0;

        bombCountNum.setValue(0);

        gamePage=new BorderPane();

        xSize = (int) X.getValue();
        ySize = (int) Y.getValue();
        percent = (int) perc.getValue();

        game = new GridPane();

        blocks = new ImageView[xSize][ySize];

        for(int x=0;x<xSize;x++){
            for(int y=0;y<ySize;y++){
                blocks[x][y] = new ImageView(this.getClass().getResource("blocks/facingDown.png").toExternalForm());
                game.setConstraints(blocks[x][y],x,y);
                game.getChildren().add(blocks[x][y]);
            }
        }

        gamePage.setCenter(game);

        header = new HBox();
        header.setMinHeight(25.0);
        header.setFillHeight(true);
        header.setSpacing(20);

        time = new Label();

        bombCount = new Label(String.format("%s",bombCountNum.getValue().intValue()));


        bombCountNum.addListener((v, oldValue, newValue) ->{
            bombCount.setText(String.format("%s",bombCountNum.getValue().intValue()));
        });

        newGameB = new Button("New Game");

        newGameB.setOnAction(event -> {
            newGame();
        });

        changeSizeB = new Button("Change Conditions");

        changeSizeB.setOnAction(event -> {
            changeSize();
        });

        COMMENCEHACK = new Button("COMMENCEHACK");

        COMMENCEHACK.setOnAction(event -> {
            Bot();
        });

        header.getChildren().addAll(time,newGameB,changeSizeB,bombCount,COMMENCEHACK);

        gamePage.setTop(header);

        Main.stage.setTitle("Minesweeper");
        Main.stage.setScene(new Scene(gamePage,20*xSize,20*ySize+25));
        Main.stage.show();

        isBomb = new Boolean[xSize][ySize];
        isFlagged = new Boolean[xSize][ySize];
        isCleared = new Boolean[xSize][ySize];

        int[] current = new int[2];

        game.setOnMousePressed(event -> {
            int xPos=(int) Math.floor(event.getX() / 20);
            int yPos=(int) Math.floor(event.getY() / 20);
            if(cleared==0){
                for(int x=0;x<xSize;x++){
                    for(int y=0;y<ySize;y++){

                        isFlagged[x][y] = false;
                        isCleared[x][y] = false;

                        if(percent>=Math.random()*100){
                            isBomb[x][y] = true;
                            bombCountNum.setValue(bombCountNum.getValue()+1);
                        }else{
                            isBomb[x][y] = false;
                            empty+=1;
                        }

                    }
                }
                for(int i=-1;i<=1;i++){
                    for(int o=-1;o<=1;o++){
                        if(0<=(xPos+i)&&(xPos+i)<xSize&&(yPos+o)<ySize&&0<=(yPos+o)&&isBomb[xPos+i][yPos+o]){
                            isBomb[xPos+i][yPos+o] = false;
                            bombCountNum.setValue(bombCountNum.getValue()-1);
                        }
                    }
                }
                timer();
            }
            if(!isCleared[xPos][yPos]&&!isFlagged[xPos][yPos]){
                if(event.getButton()==MouseButton.PRIMARY) {
                    blocks[xPos][yPos].setImage(new Image(this.getClass().getResource("blocks/0.png").toExternalForm()));
                    current[0] = xPos;
                    current[1] = yPos;
                }
            }
        });

        game.setOnMouseDragged(event -> {
            int xPos=(int) Math.floor(event.getX() / 20);
            int yPos=(int) Math.floor(event.getY() / 20);
            if(event.getButton()==MouseButton.PRIMARY) {
                if(!isCleared[current[0]][current[1]])
                    blocks[current[0]][current[1]].setImage(new Image(this.getClass().getResource("blocks/facingDown.png").toExternalForm()));
                if(!isCleared[xPos][yPos]&&!isFlagged[xPos][yPos]){
                    blocks[xPos][yPos].setImage(new Image(this.getClass().getResource("blocks/0.png").toExternalForm()));
                    current[0] = xPos;
                    current[1] = yPos;
                }
            }
        });

        game.setOnMouseReleased(event -> {
            try {
                click((int) Math.floor(event.getX() / 20), (int) Math.floor(event.getY() / 20), event.getButton().toString(),"NOT");
            }catch (ArrayIndexOutOfBoundsException e){
            }
        });
    }

    public void click(int xPos, int yPos, String buttonType, String Bot){
        switch(buttonType){
            case "PRIMARY":
                if(cleared==0){
                    for(int x=0;x<xSize;x++){
                        for(int y=0;y<ySize;y++){

                            isFlagged[x][y] = false;
                            isCleared[x][y] = false;

                            if(percent>=Math.random()*100){
                                isBomb[x][y] = true;
                                bombCountNum.setValue(bombCountNum.getValue()+1);
                            }else{
                                isBomb[x][y] = false;
                                empty+=1;
                            }

                        }
                    }
                    for(int i=-1;i<=1;i++){
                        for(int o=-1;o<=1;o++){
                            if(0<=(xPos+i)&&(xPos+i)<xSize&&(yPos+o)<ySize&&0<=(yPos+o)&&isBomb[xPos+i][yPos+o]){
                                isBomb[xPos+i][yPos+o] = false;
                                bombCountNum.setValue(bombCountNum.getValue()-1);
                            }
                        }
                    }
                    timer();
                }
                if(isBomb[xPos][yPos]){
                    if(!isFlagged[xPos][yPos]&&!isCleared[xPos][yPos]) {
                        for(int i=0;i<xSize;i++){
                            for(int o=0;o<ySize;o++){
                                if(isBomb[i][o]){
                                    blocks[i][o].setImage(new Image(this.getClass().getResource("blocks/bomb.png").toExternalForm()));
                                }
                            }
                        }
                        InnerShadow shadow = new InnerShadow();
                        shadow.setColor(RED);
                        shadow.setChoke(0.4);
                        blocks[xPos][yPos].setEffect(shadow);
                        end();
                    }
                }else{
                    if(!isCleared[xPos][yPos]){
                        int near=0;
                        for(int i=-1;i<=1;i++){
                            for(int o=-1;o<=1;o++){
                                if(0<=(xPos+i)&&(xPos+i)<xSize&&(yPos+o)<ySize&&0<=(yPos+o)&&isBomb[xPos+i][yPos+o]&&(Math.abs(i)+Math.abs(o)!=0)){
                                    near+=1;
                                }
                            }
                        }
                        isCleared[xPos][yPos]=true;
                        blocks[xPos][yPos].setImage(new Image(this.getClass().getResource("blocks/"+near+".png").toExternalForm()));
                        if(Bot.equals("BOT")){
                            botCleared+=1;
                        }
                        cleared+=1;
                        if(near==0){
                            for(int i=-1;i<=1;i++){
                                for(int o=-1;o<=1;o++){
                                    if(0<=(xPos+i)&&(xPos+i)<xSize&&(yPos+o)<ySize&&0<=(yPos+o)&&(Math.abs(i)+Math.abs(o)!=0)){
                                        click(xPos+i,yPos+o,"PRIMARY",Bot);
                                    }
                                }
                            }
                        }
                        if(cleared==empty)
                            end();
                    }
                }
                break;
            case "SECONDARY":
                if(!isCleared[xPos][yPos]){
                    if(isFlagged[xPos][yPos]){
                        isFlagged[xPos][yPos]=false;
                        blocks[xPos][yPos].setImage(new Image(this.getClass().getResource("blocks/facingDown.png").toExternalForm()));
                    }else{
                        bombCountNum.setValue(bombCountNum.getValue()-1);
                        isFlagged[xPos][yPos]=true;
                        blocks[xPos][yPos].setImage(new Image(this.getClass().getResource("blocks/flagged.png").toExternalForm()));
                    }
                }
                break;
            default:

                break;
        }
    }

    public void end(){
        playing=false;
        stopWatch.stop();
        for(int i =0;i<xSize;i++){
            for(int o=0;o<ySize;o++){
                isCleared[i][o]=true;
            }
        }

    }

    public void newGame(){
        end();
        try {
            Game();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeSize(){
        Main.stage.close();
        Main.stage.setScene(Main.scene);
        Main.stage.setTitle("Set-up");
        Main.stage.show();
    }

    public void Start(){
        Main.stage.close();
        try {
            Game();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void timer(){
        long before=System.nanoTime();
        stopWatch = new AnimationTimer() {
            @Override
            public void handle(long now) {
                time.setText(String.format("%s", (now-before)/1000000000));
            }
        };
        stopWatch.start();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        X.setValue(30);
        Y.setValue(16);
        perc.setValue(20.625);
        Xt.setText("30");
        Yt.setText("16");
        Percentt.setText("20.62%");
        Xt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                X.setValue(Double.parseDouble(Xt.getText()));
            }
        });
        Yt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Y.setValue(Double.parseDouble(Yt.getText()));
            }
        });
        Percentt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                perc.setValue(Double.parseDouble(Percentt.getText().replace("%", "")));
            }
        });
        X.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Xt.setText(String.format("%.0f", X.getValue()));
            }
        });
        Y.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Yt.setText(String.format("%.0f", Y.getValue()));
            }
        });
        perc.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Percentt.setText(String.format("%.2f%s", perc.getValue(), "%"));
            }
        });

    }

    public void Bot(){
        long before = System.currentTimeMillis();
        do {
            click((int) (Math.random() * xSize), (int) (Math.random() * ySize), "PRIMARY","NOT");
            char[][] subject = new char[3][3];
            String name;

            do {
                results = new ArrayList<>();
                for (int x = 0; x < xSize; x++) {
                    for (int y = 0; y < ySize; y++) {
                        if (playing) {
                            if (isCleared[x][y]) {
                                name = "";
                                for (int i = -1; i <= 1; i++) {
                                    for (int o = -1; o <= 1; o++) {

                                        try {

                                            if (isCleared[x + i][y + o]) {
                                                subject[i + 1][o + 1] = blocks[x + i][y + o].getImage().getUrl().replace("file:/G:/My%20Drive/Mine5weep3r/out/production/MineSweeper/sample/blocks/", "").replace(".png", "").charAt(0);
                                                name += blocks[x + i][y + o].getImage().getUrl().replace("file:/G:/My%20Drive/Mine5weep3r/out/production/MineSweeper/sample/blocks/", "").replace(".png", "").charAt(0);
                                            } else if (isFlagged[x + i][y + o]) {
                                                subject[i + 1][o + 1] = 'F';
                                                name += 'F';
                                            } else {
                                                subject[i + 1][o + 1] = '_';
                                                name += '_';
                                            }

                                        } catch (IndexOutOfBoundsException e) {
                                            subject[i + 1][o + 1] = 'N';
                                            name += 'N';
                                        }

                                    }
                                }

                                File data = new File("G:\\My Drive\\Mine5weep3r\\src\\sample\\BotData\\" + name + ".txt");
                                if (!data.isFile()) {
                                    try {
                                        data.createNewFile();
                                        permutations(name);
                                        BufferedWriter writer = new BufferedWriter(new FileWriter(data, false));
                                        for (String poss : perms) {
                                            writer.write(poss + " 20.000000000000000 1\n");
                                        }
                                        writer.close();

                                    } catch (Exception e) {
                                    }

                                }
                                try {
                                    Boolean over = false;
                                    int total = 0;
                                    odds = new ArrayList<>();
                                    perms = new ArrayList<>();
                                    BufferedReader read = new BufferedReader(new FileReader(data));
                                    String[] line = read.readLine().split(" ");
                                    do {
                                        odds.add(Double.parseDouble(line[1]) / Double.parseDouble(line[2]));
                                        total += Double.parseDouble(line[1]) / Double.parseDouble(line[2]);
                                        perms.add(line[0]);
                                        try {
                                            line = read.readLine().split(" ");
                                        } catch (NullPointerException e) {
                                            over = true;
                                        }
                                    } while (!over);
                                    read.close();
                                    double rand = Math.random() * total;
                                    for (int i = 0; i < odds.size(); i++) {
                                        rand -= odds.get(i);
                                        if (rand <= 0) {
                                            for (int o = 0; o < 9; o++) {
                                                if (perms.get(i).charAt(o) != name.charAt(o)) {
                                                    switch (perms.get(i).charAt(o)) {
                                                        case 'C':
                                                            click((int) (x + (Math.floor(o / 3)) - 1), (y + (o % 3) - 1), "PRIMARY","BOT");
                                                            break;
                                                        case 'F':
                                                            click((int) (x + (Math.floor(o / 3)) - 1), (y + (o % 3) - 1), "SECONDARY","BOT");
                                                            break;
                                                    }
                                                }
                                            }
                                            results.add(new String[]{name, String.valueOf(i)});
                                            i = odds.size();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            } while (playing);
            for (String[] save : results) {
                try {
                    ArrayList<String> temp = new ArrayList<>();
                    BufferedReader read = new BufferedReader(new FileReader("G:\\My Drive\\Mine5weep3r\\src\\sample\\BotData\\" + save[0] + ".txt"));
                    Boolean over = false;
                    String line;
                    do {
                        try {
                            line = read.readLine();
                            if (line != null) {
                                temp.add(line);
                            } else {
                                over = true;
                            }
                            //System.out.println(line);
                        } catch (NullPointerException e) {
                            over = true;
                        }
                    } while (!over);
                    read.close();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("G:\\My Drive\\Mine5weep3r\\src\\sample\\BotData\\" + save[0] + ".txt", false));
                    for (int i = 0; i < Integer.parseInt(save[1]); i++) {
                        writer.write(temp.get(i) + "\n");
                    }
                    String[] update = temp.get(Integer.parseInt(save[1])).split(" ");
                    writer.write(String.format("%s %.15f %s\n", update[0], (Double.parseDouble(update[1]) + (botCleared/empty*100.0)), (Integer.parseInt(update[2]) + 1)));
                    for (int i = (Integer.parseInt(save[1]) + 1); i < temp.size(); i++) {
                        writer.write(temp.get(i) + "\n");
                    }
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        newGame();
        System.out.println((System.currentTimeMillis()-before)/1000.0/60.0);
        }while((System.currentTimeMillis()-before)<36000000);
            System.out.println("finished");
    }

    public void permutations(String original){
        ArrayList<Integer> opens = new ArrayList<>();
        for(int i=0;i<9;i++){
            if(original.charAt(i)=='_'){
                opens.add(i);
            }
        }
        perms = new ArrayList<>();
        change(original, opens, 0);
    }

    public void change(String last, ArrayList<Integer> availables, int toAdd){
        if(availables.isEmpty()){
            perms.add(last);
        }else {
            StringBuilder newer = new StringBuilder(last);
            int i = availables.get(availables.size()-1);
            availables.remove(availables.size()-1);
            newer.setCharAt(i,'_');
            change(newer.toString(), availables, i);
            i = availables.get(availables.size()-1);
            availables.remove(availables.size()-1);
            newer.setCharAt(i,'F');
            change(newer.toString(), availables, i);
            i = availables.get(availables.size()-1);
            availables.remove(availables.size()-1);
            newer.setCharAt(i,'C');
            change(newer.toString(), availables, i);
        }
            availables.add(toAdd);
    }

}
