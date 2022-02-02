package com.trivia.triviamat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class QuestionController implements Initializable {
    private static final String[] arr = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "err", "pl", "VArrow", "HArrow", "up", "right", "down", "left", "null", "pass", "sound"};
    private static int[][] staticMap = new int[7][7];
    private static final int[][] screenMap = new int[3][3];
    private static int[][] newMap = new int[7][7];
    private static final int[][] tempMap = new int[7][7];
    private static final ArrayList<File> song = new ArrayList<File>();
    private static final File musicDir = new File("/music");
    private static final File imgDir = new File("/image");
    private static File[] musicFile;
    private static File[] imgFile;
    private static int curAns;
    private static int trueAns;

    private ImageView imgQuest ;
    private static boolean isAnsReq = false;
    private final Image[] simg = new Image[arr.length];
    private final int[] quesMap = genArr(25);
    @FXML
    private Text qID;
    @FXML
    private GridPane map;
    @FXML
    private GridPane screen;
    @FXML
    private RadioButton ans1;
    @FXML
    private RadioButton ans2;
    @FXML
    private RadioButton ans3;
    @FXML
    private RadioButton ans4;
    @FXML
    private MenuItem saveGame;
    @FXML
    private MenuItem loadGame;
    @FXML
    private MenuItem restart;
    @FXML
    private MenuItem exitGame;
    @FXML
    private MenuItem aboutGame;
    @FXML
    private MenuItem inforGame;
    @FXML
    private MenuItem cheat;
    @FXML
    private Text lifeRemain;
    @FXML
    private GridPane quesImage;
    @FXML
    private Text qText;
    private int prevPosX = 0, prevPosY = 0, curPosX = 0, curPosY = 0, nextPosX = 0, nextPosY = 0;
    private int cheatLimit = 3;
    private int totalCheat = 0;
    private int totalLife = 3;
    private int lifeRemainCount = totalLife ;

    private boolean isRunning;

    public QuestionController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lifeRemain.setText("Life Remaining : "+lifeRemainCount);
        musicFile = musicDir.listFiles();
        imgFile = imgDir.listFiles();


        if (musicFile != null) {
            System.out.println("Ah" + musicFile);
        }
        if (imgFile != null) {
            System.out.println("Kimochi!" + imgFile);
        }

        ToggleGroup group = new ToggleGroup();
        ans1.setToggleGroup(group);
        ans2.setToggleGroup(group);
        ans3.setToggleGroup(group);
        ans4.setToggleGroup(group);

        for (int i = 0; i < arr.length; i++) {
            Image img = new Image(arr[i] + ".png");
            simg[i] = img;
        }

        gameStart();
        saveGame.setOnAction(e -> {
            gameSave();
        });
        loadGame.setOnAction(e -> {
            gameLoad();
        });
        restart.setOnAction(e -> {
            Stage pri = new Stage();
            MessageBox.show(pri,
                    "Good luck this time :P",
                    "Restart",
                    MessageBox.ICON_INFORMATION);
            setCurPosX(0);
            setCurPosY(0);
            setNextPosX(0);
            setCurPosY(0);
            gameStart();
            drawMap(newMap);
        });
        exitGame.setOnAction(e -> {
            gameSave();
            javafx.application.Platform.exit();
        });
        aboutGame.setOnAction(e -> {
            gameAbout();
        });
        inforGame.setOnAction(e -> {
            gameInfo();
        });
        cheat.setOnAction(e -> {
            cheatGame();
        });
    }


    @FXML
    public void submit() {
        updateAnswer();
        ans1.setSelected(false);
        ans2.setSelected(false);
        ans3.setSelected(false);
        ans4.setSelected(false);
        if (!isAnsReq) return;
        if (curAns == trueAns) {
            newMap[curPosX][curPosY] = 1;
            isAnsReq = true;
            updatePlayerPos(curPosX, curPosY, curPosX, curPosY);
        } else {
            lifeRemainCount--;
            lifeRemain.setText("Life Remaining : "+ lifeRemainCount);
   /*         if (lifeRemainCount == 0) {
                Stage pri = new Stage();
                MessageBox.show(pri,
                        "You Loose!",
                        "Loose",
                        MessageBox.ICON_INFORMATION);
                setCurPosX(0);
                setCurPosY(0);
                setNextPosX(0);
                setCurPosY(0);
                gameStart();
                drawMap(newMap);
            } */
            isAnsReq = false;
            updateWay(curPosX, curPosY, 1);
            updatePlayerPos(curPosX, curPosY, prevPosX, prevPosY);
            newMap[prevPosX][prevPosY] = -2;
        }
        drawMap(newMap);
        updatePossibleWay(curPosX, curPosY);
        drawMap(newMap);
        if (lifeRemainCount == 0) {
            Stage pri = new Stage();
            MessageBox.show(pri,
                    "You Loose!",
                    "Loose",
                    MessageBox.ICON_INFORMATION);
            setCurPosX(0);
            setCurPosY(0);
            setNextPosX(0);
            setCurPosY(0);
            gameStart();
            drawMap(newMap);
        }
    }

    @FXML
    public void drawMap(int[][] a) {
        updatePossibleWay(curPosX, curPosY);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (a[i][j] == 3) {
                    ImageView imageView = new ImageView(simg[17]);
                    map.add(imageView, i, j);
                } else if (a[i][j] == -2) {
                    ImageView imageView = new ImageView(simg[16]);
                    map.add(imageView, i, j);
                } else if (a[i][j] == 2 || a[i][j] == 4) {
                    if (a[i][j] == 4) {
                        gameFinish();
                    }
                    ImageView imageView = new ImageView(simg[i / 2 + j * 2]);
                    int finalI = i;
                    int finalJ = j;
                    imageView.setOnMouseClicked(me -> {
                        setNextPosX(finalI);
                        setNextPosY(finalJ);
                        if (isAbleToMove(curPosX, curPosY, nextPosX, nextPosY)) {
                            updatePlayerPos(curPosX, curPosY, nextPosX, nextPosY);
                        }
                    });
                    map.add(imageView, i, j);
                } else if (a[i][j] == 1) {
                    ImageView imageView = new ImageView(simg[25]);
                    map.add(imageView, i, j);
                    int finalI = i;
                    int finalJ = j;
                    imageView.setOnMouseClicked(me -> {
                        setNextPosX(finalI);
                        setNextPosY(finalJ);
                        if (isAbleToMove(curPosX, curPosY, nextPosX, nextPosY)) {
                            updatePlayerPos(curPosX, curPosY, nextPosX, nextPosY);
                        }
                    });
                } else if (a[i][j] == 0) {
                    ImageView imageView;
                    if (i % 2 == 0) {
                        imageView = new ImageView(simg[18]);
                    } else {
                        imageView = new ImageView(simg[19]);
                    }
                    map.add(imageView, i, j);
                } else if (a[i][j] == -3) {
                    ImageView imageView;
                    imageView = new ImageView(simg[24]);
                    map.add(imageView, i, j);
                }
            }
        }
    }

    @FXML
    private boolean isAbleToMove(int curPosX, int curPosY, int nextPosX, int nextPosY) {
        return (Math.abs(curPosX - nextPosX) + Math.abs(curPosY - nextPosY) < 3) && (!isAnsReq) && (newMap[nextPosX][nextPosY] == 1 || newMap[nextPosX][nextPosY] == 2 || newMap[nextPosX][nextPosY] == 3 || newMap[nextPosX][nextPosY] == 4);
    }

    @FXML
    private void updatePossibleWay(int curPosX, int curPosY) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                screenMap[i][j] = 0;
            }
        }
        screenMap[1][1] = 2;
        if ((curPosX >= 1 && (curPosY >= 0) && (newMap[curPosX - 1][curPosY] == 0))) {
            screenMap[0][1] = 1;
        }
        if ((curPosX >= 0 && (curPosY >= 1) && (newMap[curPosX][curPosY - 1] == 0))) {
            screenMap[1][0] = 1;
        }
        if ((curPosX <= 5) && (curPosY <= 6) && (newMap[curPosX + 1][curPosY] == 0)) {
            screenMap[2][1] = 1;
        }
        if ((curPosX <= 6 && (curPosY <= 5) && (newMap[curPosX][curPosY + 1] == 0))) {
            screenMap[1][2] = 1;
        }
        updateScreenMap(screenMap);
    }

    @FXML
    private void updateScreenMap(int[][] screenMap) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (screenMap[i][j] == 0) {
                    ImageView imageView = new ImageView(simg[24]);
                    screen.add(imageView, i, j);
                } else if (screenMap[i][j] == 1) {
                    if (i == 0 && j == 1) {
                        ImageView imageView = new ImageView(simg[23]);
                        screen.add(imageView, i, j);
                    } else if (i == 1 && j == 0) {
                        ImageView imageView = new ImageView(simg[20]);
                        screen.add(imageView, i, j);
                    } else if (i == 1 && j == 2) {
                        ImageView imageView = new ImageView(simg[22]);
                        screen.add(imageView, i, j);
                    } else if (i == 2 && j == 1) {
                        ImageView imageView = new ImageView(simg[21]);
                        screen.add(imageView, i, j);
                    }
                } else {
                    ImageView imageView = new ImageView(simg[curPosX / 2 + 2 * curPosY]);
                    screen.add(imageView, 1, 1);
                }

            }
        }
    }

    @FXML
    private void updateWay(int curPosX, int curPosY, int i) {
        if (i == 1) {
            if (curPosX >= 1 && (curPosY >= 0)) {
                newMap[curPosX - 1][curPosY] = -3;
            }
            if (curPosX >= 0 && (curPosY >= 1)) {
                newMap[curPosX][curPosY - 1] = -3;
            }
            if (curPosX <= 5 && (curPosY <= 6)) {
                newMap[curPosX + 1][curPosY] = -3;
            }
            if (curPosX <= 6 && (curPosY <= 5)) {
                newMap[curPosX][curPosY + 1] = -3;
            }

        } else {
            if (curPosX >= 1 && (curPosY >= 0)) {
                newMap[curPosX - 1][curPosY] = 0;
            }
            if (curPosX >= 0 && (curPosY >= 1)) {
                newMap[curPosX][curPosY - 1] = 0;
            }
            if (curPosX <= 5 && (curPosY <= 6)) {
                newMap[curPosX + 1][curPosY] = 0;
            }
            if (curPosX <= 6 && (curPosY <= 5)) {
                newMap[curPosX][curPosY + 1] = 0;
            }

        }
        drawMap(newMap);
    }

    @FXML
    private void updatePlayerPos(int curPosX, int curPosY, int nextPosX, int nextPosY) {
        if (newMap[nextPosX][nextPosY] == 4) {
            Stage pri = new Stage();
            MessageBox.show(pri,
                    "You win!",
                    "Win",
                    MessageBox.ICON_INFORMATION);
            setCurPosX(0);
            setCurPosY(0);
            setNextPosX(0);
            setCurPosY(0);
            gameStart();
            drawMap(newMap);

        } else {
            if (newMap[nextPosX][nextPosY] == 2) {
                updateQuestion();
                isAnsReq = true;
            } else if (newMap[nextPosX][nextPosY] == 1) {
                quesImage.getChildren().remove(imgQuest);
                qText.setText("Move to the next room");
                ans1.setText("");
                ans2.setText("");
                ans3.setText("");
                ans4.setText("");
                isAnsReq = false;
            }

            newMap[curPosX][curPosY] = 1;
            newMap[nextPosX][nextPosY] = 3;
            setPrevPosX(curPosX);
            setPrevPosY(curPosY);
            setCurPosX(nextPosX);
            setCurPosY(nextPosY);

            drawMap(newMap);



        }
    }

    @FXML
    private void updateAnswer() {
        if (ans1.isSelected())
            curAns = 1;
        else if (ans2.isSelected())
            curAns = 2;
        else if (ans3.isSelected())
            curAns = 3;
        else if (ans4.isSelected())
            curAns = 4;
    }

    @FXML
    private void updateQuestion() {

        System.out.println(nextPosX+ " " + nextPosY + " " + quesMap[nextPosX * 2 + nextPosY / 2] );
        Question question = Database.getQuestion(quesMap[nextPosX * 2 + nextPosY / 2]);
        int[] AnsMap = genArr(4);
        String[] TextMap = new String[]{question.getqAns1(), question.getqAns2(), question.getqAns3(), question.getqAns4()};
        for (int i = 1; i <= 4; i++) {
            if (AnsMap[i - 1] == question.getqTrueAns()) {
                trueAns = i;
            }
        }
        String type = question.qContentType.toString();
        if (Objects.equals(question.getqContentType(), "img")) {
            Image quest = new Image(Game.class.getResource("/"+question.getqContentLink()).toExternalForm());
            imgQuest  = new ImageView(quest);
            imgQuest.setFitHeight(100);
            imgQuest.setFitWidth(200);
            quesImage.add(imgQuest,0,0);
        }
        qText.setText(question.getqContent());
        ans1.setText(TextMap[AnsMap[0] - 1]);
        ans2.setText(TextMap[AnsMap[1] - 1]);
        ans3.setText(TextMap[AnsMap[2] - 1]);
        ans4.setText(TextMap[AnsMap[3] - 1]);
    }

    @FXML
    public void keyMove(KeyEvent keyEvent) {
        String a = keyEvent.getCode().toString();
        if (a == "E") {
            submit();
        }
        if ((a == "W") && (screenMap[1][0] == 1) && (!isAnsReq)) {
            updatePlayerPos(curPosX, curPosY, curPosX, curPosY - 2);
        } else if ((a == "A") && (screenMap[0][1] == 1) && (!isAnsReq)) {
            updatePlayerPos(curPosX, curPosY, curPosX - 2, curPosY);
        } else if ((a == "S") && (screenMap[1][2] == 1) && (!isAnsReq)) {
            updatePlayerPos(curPosX, curPosY, curPosX, curPosY + 2);
        } else if ((a == "D") && (screenMap[2][1] == 1) && (!isAnsReq)) {
            updatePlayerPos(curPosX, curPosY, curPosX + 2, curPosY);
        }
        updateScreenMap(screenMap);
        drawMap(newMap);
    }

    @FXML
    void gameFinish() {

    }

    @FXML
    void gameStart() {
        lifeRemainCount = 3;
        lifeRemain.setText("Life Remaining :" + lifeRemainCount);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                screenMap[i][j] = 0;
            }
        }
        screenMap[1][1] = 2;

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (i % 2 == 1) {
                    if (j % 2 == 1) {
                        staticMap[i][j] = -3;
                    } else {
                        staticMap[i][j] = 0;
                    }
                } else {
                    if (j % 2 == 1) {
                        staticMap[i][j] = 0;
                    } else {
                        staticMap[i][j] = 2;
                    }
                }
            }
        }
        staticMap[0][0] = 3;
        staticMap[6][6] = 4;
        newMap = staticMap;
        staticMap = newMap;
        drawMap(staticMap);
        if (isFinishabe(newMap)){
            System.out.println("Hi!");
        }
    }

    @FXML
    void cheatGame() {
        if ((totalCheat < cheatLimit) && ((prevPosX != curPosX) || (prevPosY != curPosY))) {
            newMap[prevPosX][prevPosY] = 1;
            updateWay(prevPosX, prevPosY, 2);
            drawMap(newMap);
            totalCheat++;
        } else if (totalCheat >= cheatLimit) {
            Stage pri = new Stage();
            MessageBox.show(pri,
                    "Total cheat time reach limit",
                    "Cheating",
                    MessageBox.ICON_INFORMATION);
        }
    }

    @FXML
    void gameSave() {
        String gameData = "";
        //Data is a string to save to database which structure is
        //Playerpos|CurrentMapData|CurrentMapQues|MinimapData|
        gameData = gameData + prevPosX + "/" + prevPosY + "/" + curPosX + "/" + curPosY + "/" + nextPosX + "/" + nextPosY + "/";
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                gameData += newMap[i][j] + "/";
            }
        }
        for (int i = 0; i < 25; i++) {
            gameData += quesMap[i] + "/";
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameData += screenMap[i][j] + "/";
            }
        }
        if (isAnsReq)
            gameData += 1 + "/";
        else gameData += 0 + "/";
        gameData += cheatLimit + "/" + totalCheat + "/" + lifeRemainCount + "/" + totalLife;
        Database.setData(gameData);
        Stage pri = new Stage();
        MessageBox.show(pri,
                "Save Data successfully, please load data if you need",
                "Saving data",
                MessageBox.ICON_INFORMATION);
    }

    @FXML
    void gameLoad() {
        String data = Database.getData();
        String[] dataExc = data.split("/");
        setPrevPosX(Integer.parseInt(dataExc[0]));
        setPrevPosY(Integer.parseInt(dataExc[1]));
        setCurPosX(Integer.parseInt(dataExc[2]));
        setCurPosY(Integer.parseInt(dataExc[3]));
        setNextPosX(Integer.parseInt(dataExc[4]));
        setCurPosY(Integer.parseInt(dataExc[5]));
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                newMap[i][j] = Integer.parseInt(dataExc[6 + i * 7 + j]);
            }
        }
        for (int i = 0; i < 25; i++) {
            quesMap[i] = Integer.parseInt(dataExc[i + 54]);
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                screenMap[i][j] = Integer.parseInt(dataExc[80 + i * 3 + j]);
            }
        }
        isAnsReq = Integer.parseInt(dataExc[89]) > 0;
        cheatLimit = Integer.parseInt(dataExc[90]);
        totalCheat = Integer.parseInt(dataExc[91]);
        lifeRemainCount = Integer.parseInt(dataExc[92]);
        totalLife = Integer.parseInt(dataExc[93]);

        updateScreenMap(screenMap);
        drawMap(newMap);
        lifeRemain.setText("Life Remaining : " + lifeRemainCount);
        Stage pri = new Stage();
        MessageBox.show(pri,
                "Load Data successfully, please ignore this popup",
                "Loading game from data",
                MessageBox.ICON_INFORMATION);
    }

    @FXML
    void gameInfo() {
        Stage pri = new Stage();
        MessageBox.show(pri,
                "Get to the room P to finish the matrix",
                "Info",
                MessageBox.ICON_INFORMATION);
    }

    @FXML
    void gameAbout() {
        Stage pri = new Stage();
        MessageBox.show(pri,
                "Developed by Trong",
                "Notice",
                MessageBox.ICON_INFORMATION);
    }

    @FXML
    private boolean isFinishabe(int[][] map){
        int[][] xmap = new int[4][4];
        for (int i = 0;i<7;i++){
            for (int j = 0; j<7;j++){
                if ((i%2==0)&&(j%2==0)){
                    if(map[i][j]==1||map[i][j]==2)
                        xmap[i/2][j/2]=0;
                    else if (map[i][j]==3)
                        xmap[i/2][j/2]=1;
                    else if (map[i][j]==4)
                        xmap[i/2][j/2]=2;
                    else
                        xmap[i/2][j/2]=3;
                }
            }
        }
        return isPath(xmap);
    }
    @FXML
    private boolean isSafe(int i, int j, int matrix[][])
    {
        if (i >= 0 && i < 4 && j >= 0 && j < 4)
            return true;
        return false;
    }
    boolean isPath(int matrix[][], int i, int j, boolean visited[][]) {
        if (isSafe(i, j, matrix) && (matrix[i][j] != 0) && (!visited[i][j])){
            visited[i][j] = true;
            if (matrix[i][j] == 0)
                return true;
            boolean up = isPath(matrix, i - 1, j, visited);
            if (up)
                return true;
            boolean left = isPath(matrix, i, j - 1, visited);
            if (left)
                return true;
            boolean down = isPath(matrix, i + 1, j, visited);
            if (down)
                return true;
            boolean right = isPath(matrix, i, j + 1, visited);
            if (right)
                return true;
        }
        return false;
    }
    @FXML
    boolean isPath(int matrix[][])
    {
        boolean[][] visited = new boolean[4][4];
        boolean flag = false;
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (matrix[i][j] == 1 && !visited[i][j])
                    if (isPath(matrix, i, j, visited))
                    {
                        flag = true;
                        break;
                    }
            }
        }
        return flag;
    }

    @FXML
    private int[] genArr(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i + 1;
        }
        Random rnd = ThreadLocalRandom.current();
        for (int j = a.length - 1; j > 0; j--) {
            int index = rnd.nextInt(j);
            int b = a[index];
            a[index] = a[j];
            a[j] = b;
        }
        return a;
    }

    public void setPrevPosX(int prevPosX) {
        this.prevPosX = prevPosX;
    }

    public void setPrevPosY(int prevPosY) {
        this.prevPosY = prevPosY;
    }

    public void setCurPosX(int curPosX) {
        this.curPosX = curPosX;
    }

    public void setCurPosY(int curPosY) {
        this.curPosY = curPosY;
    }

    public void setNextPosX(int nextPosX) {
        this.nextPosX = nextPosX;
    }

    public void setNextPosY(int nextPosY) {
        this.nextPosY = nextPosY;
    }
}