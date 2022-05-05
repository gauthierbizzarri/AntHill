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

import java.io.FileNotFoundException;

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
        stage.setTitle("AntWar");

        Group root = new Group();

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("ORDER CHANGED ");
                map.anthills.get(0).set_order(0);
            }
        };

        Scene scene = new Scene(root);
        stage.setScene(scene);
        Canvas canvas = new Canvas( 25*30 + 25*30/6, 20*30  );
        Button b = new Button("button");
        b.setOnAction(event);
        root.getChildren().add( canvas );
        root.getChildren().add(b);

        map = new Map();
        map.create_map();
        map.add_anthill();



        new AnimationTimer()
        {
            @Override
            public void handle(long currentNanoTime)
            {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                boolean generated = false;

                try {
                    map.draw_graphic(gc);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) { }
            }
        }.start();

        stage.show();

    }
}

