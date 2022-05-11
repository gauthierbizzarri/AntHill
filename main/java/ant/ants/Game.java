package ant.ants;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;

public class Game extends Application{

    private Map map;
    public static void main(String[] args)
    {

        Game game = new Game();


        launch(Game.class, args);
    }

    @Override
    public void start(Stage stage) {
        stage.setResizable(false);
        stage.setTitle("Fourmiz");

        Group root = new Group();

        EventHandler<ActionEvent> event_home_rouge = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("ORDER CHANGED ");
                map.anthills.get(0).set_order(0);
            }
        };

        EventHandler<ActionEvent> event_home_vert = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("ORDER CHANGED ");
                map.anthills.get(1).set_order(0);
            }
        };

        EventHandler<ActionEvent> event_home_bleu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("ORDER CHANGED ");
                map.anthills.get(2).set_order(0);
            }
        };

        Scene scene = new Scene(root);
        stage.setScene(scene);
        Canvas canvas = new Canvas( 25*30 + 25*30/6, 20*30  );

        root.getChildren().add( canvas );

        // Buttons in order to change the order of the anthill

        FileInputStream btn_home_red = null;
        try {
            btn_home_red = new FileInputStream("/home/bizzarri/eclipse-workspace/ants/src/main/resources/ant/ants/home_icon.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image img_home_red = new Image(btn_home_red);
        ImageView img_home_red_view = new ImageView(img_home_red);
        img_home_red_view.setFitHeight(20);
        img_home_red_view.setPreserveRatio(true);


        FileInputStream btn_home_green = null;
        try {
            btn_home_green = new FileInputStream("/home/bizzarri/eclipse-workspace/ants/src/main/resources/ant/ants/home_icon.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image img_home_green = new Image(btn_home_green);
        ImageView img_home_green_view = new ImageView(img_home_green);
        img_home_green_view.setFitHeight(20);
        img_home_green_view.setPreserveRatio(true);


        FileInputStream btn_home_blue = null;
        try {
            btn_home_blue = new FileInputStream("/home/bizzarri/eclipse-workspace/ants/src/main/resources/ant/ants/home_icon.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image img_home_blue = new Image(btn_home_blue);
        ImageView img_home_blue_view = new ImageView(img_home_blue);
        img_home_blue_view.setFitHeight(20);
        img_home_blue_view.setPreserveRatio(true);


        // 1st Anthill

        Button b = new Button("1");
        b.setLayoutX(25 * 30+ 100);
        b.setLayoutY(60);
        b.setPrefSize(20, 20);
        b.setGraphic(img_home_red_view);
        b.setOnAction(event_home_rouge);
        root.getChildren().add(b);

        //  2 nd Anthill



        Button b1 = new Button("2");
        b1.setLayoutX(25 * 30+ 100);
        b1.setLayoutY(60+200);
        b1.setPrefSize(20, 20);
        b1.setGraphic(img_home_green_view);
        b1.setOnAction(event_home_vert);
        root.getChildren().add(b1);

        //  3 nd Anthill
        Button b2 = new Button("3");
        b2.setLayoutX(25 * 30+ 100);
        b2.setLayoutY(60+400);
        b2.setPrefSize(20, 20);
        b2.setGraphic(img_home_blue_view);
        b2.setOnAction(event_home_bleu);
        root.getChildren().add(b2);

        map = new Map();
        map.create_map();
        map.add_anthill();


        long start = System.nanoTime();
        long duration = Duration.ofMinutes(2).toNanos();
        new AnimationTimer()
        {
            @Override
            public void handle(long now) {
                if (now > duration + start) {
                    this.stop();
                } else {
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    // DRAW CLOCK

                    // gc.fillText(String.valueOf((duration+start)/(1*1000*1000*1000)),25*32,20 + 15*32);
                    boolean generated = false;

                    try {
                        map.draw_graphic(gc);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();

        stage.show();

    }
}

