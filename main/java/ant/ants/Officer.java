package ant.ants;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Flow;
import java.util.stream.IntStream;


public class Officer  extends Ant  implements Flow.Subscriber<Integer>{
    
    
    protected Anthill queen;

    private Flow.Subscription subscription;
    private final String subscriberName;
    public Officer(Anthill queen, int id)
    {
    	// This officer belongs to a queen(anthill)
        this.queen = queen;
        // THis officer is set at queen position
        this.x = this.queen.x;
        this.y = this.queen.y;
        // Officer id 
        this.id = id;

		this.thread = new Thread(this);
        this.color = this.queen.color ;
        this.subscriberName = String.valueOf(this.queen.id);
        // 
       
        
    }

    public void move() {


        // Remove old worker position from the map
        Case tile_old_officer;
        tile_old_officer = this.queen.map.get_tile_with_coord(this.x,this.y);
        tile_old_officer.unset_officer();
        tile_old_officer.set_color("");
        // Choosing a random value in Array
        int random_x_dir = ThreadLocalRandom.current().nextInt(-1, 1 + 1);
        int random_y_dir = ThreadLocalRandom.current().nextInt(-1, 1 + 1);
        // Update the ant position
        this.x = this.x + random_x_dir;
        this.y = this.y +random_y_dir;
        // If x or y overflow a border of the map . The ant will be on the opposite position (It works as a spherical

        // Upper bound is 25-1 = 24
        // If the ant overflow the right corner of the grid it will be replaced to the 1st , on the left border
        if (this.x>24){
            this.x = 0;
        }
        // Upper bound is 21-1 = 20
        // If the ant overflow the bottom corner of the grid it will be replaced to the 1st , on the top border
        if (this.y>19)
        {
            this.y = 0;

        }
        if (this.x<0)
        {
            this.x = 24;
        }
        if (this.y<0)
        {
            this.y = 19;
        }
        // Update worker position on the map
        Case tile_officer;
        tile_officer = this.queen.map.get_tile_with_coord(this.x,this.y);
        tile_officer.set_officer();
        // Update the color
        tile_officer.set_color(this.color);
    }
      
    public void run(){
        // System.out.println("Officer" + this.id+" de anthill "+this.queen.id+" called"+"\n");
        this.move();
        ;

    }


    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);

    }

    @Override
    // order 0 : go home , order 1 : collect resources
    public void onNext(final Integer order) {
        if (order == 0) {
            System.out.println("WE LL  gather resources");
        }
        if (order == 1) {
            System.out.println("I ll go home ");
        }
        try {
            this.thread.sleep(150);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onError(Throwable throwable) {

        System.out.println("ERROR OCCURED"+throwable);
    }

    @Override
    public void onComplete() {
        System.out.println("ORDER PASSED");

    }
    public String getSubscriberName() {
        return subscriberName;
    }
}

/*

				publisher.subscribe(worker);
				int order = 0;
				int MAX_SECONDS_TO_KEEP_IT_WHEN_NO_SPACE = 2;
				final int lag = publisher.offer(
						order,
						MAX_SECONDS_TO_KEEP_IT_WHEN_NO_SPACE,
						TimeUnit.SECONDS,
						(subscriber, msg) -> {
							subscriber.onError(
									new RuntimeException("Hey " + ((Worker) subscriber)
											.getSubscriberName() + "! You are too slow getting orders" +
											" and we don't have more space for them! " +
											"I'll drop your order: " + msg));
							return false; // don't retry, we don't believe in second opportunities
						});
 */