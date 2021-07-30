package Unit3;
//Adithya Ramanathan || Period 3 || Mr.Tully

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PickUpTrash extends Application {

    double X = 0;
    double Y = 0;
    int[][] arr = new int[20][20];
    boolean gameOver = false;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Pick Up Trash");

        Group group = new Group();
        Canvas canvas = new Canvas(400, 400);

        //Randomized trash
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                //50% chance of bag
                int chance = (int) (Math.random() * 2) + 1;
                arr[x][y] = chance;
            }
        }

        //Mouse listener
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // find rc
                //call pick up trash
                //call draw
                X = event.getX();
                Y = event.getY();
                int r = (int)(X - 48)/15;
                int c = (int)(Y - 48)/15;
                pickupTrash(c,r);
                drawStuff(canvas.getGraphicsContext2D());
            }
        });

        group.getChildren().add(canvas);
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        drawStuff(canvas.getGraphicsContext2D());

        primaryStage.show();
    }

    public void drawStuff(GraphicsContext gc) {
        //black outline squares
        gc.setStroke(Color.BLACK);
        int xcount = 0;
        int ycount = 0;
        for (int x = 50; xcount < 20; x += 15) {
            ycount = 0;
            xcount++;
            for (int y = 50; ycount < 20; y += 15) {
                ycount++;
                gc.strokeRect(x, y, 10, 10);
            }
        }
        //white covering squares
        gc.setFill(Color.WHITE);
        int xcount2 = 0;
        int ycount2 = 0;
        for (int x = 48; xcount2 < 20; x += 15) {
            ycount2 = 0;
            xcount2++;
            for (int y = 48; ycount2 < 20; y += 15) {
                ycount2++;
                gc.fillRect(x, y, 10, 10);
            }
        }
        //set trash
        gc.setFill(Color.BLACK);
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[0].length; y++) {
                if (arr[x][y] == 2) {
                    //draw trash
                    gc.fillRect(x*15+48, y*15+48, 10, 10);
                }
            }
        }
        int trashcounter = 0;
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr.length; y++) {
                if (arr[x][y] == 2) {
                    trashcounter++;
                }
            }
        }
        if(trashcounter == 0)
        {
            gameOver = true;
        }
        if(gameOver){
            gc.setFont(new Font("Times New Roman",25));
            gc.fillText("You Cleared All Trash",20,20);
        }
    }

    public void pickupTrash(int c, int r) {
        //base case
        if(r < 0 || c < 0 ||r >= arr.length || c >= arr[0].length || arr[r][c] == 1){

            return;  }

        else {
            arr[r][c] = 1;
            pickupTrash(c,r-1);
            pickupTrash(c,r+1);
            pickupTrash(c-1,r);
            pickupTrash(c+1,r);
        }
    }

}
