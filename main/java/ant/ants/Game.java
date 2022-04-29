package ant.ants;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Game extends Application{

    protected Map map;
    public static void main(String[] args)
    {
        //Map.setNbTile(5, 5);

        Game game = new Game();

        // Map.shared().consoleDraw();


        // Master.shared().startGame();

        launch(Game.class, args);
    }

    @Override
    public void start(Stage stage) {
        stage.setResizable(false);
        stage.setTitle("AntWar");

        Group root = new Group();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        Canvas canvas = new Canvas( 25*30, 20*30 );
        root.getChildren().add( canvas );

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

                map.draw_graphic(gc);
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) { }
            }
        }.start();

        stage.show();

    }
}

