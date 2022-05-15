package ant.ants;

import java.util.ArrayList;
import java.lang.Math;
import java.util.concurrent.Flow;
import java.util.concurrent.ThreadLocalRandom;

public class Worker extends Ant implements Flow.Subscriber<Integer> {

	protected Anthill queen;
	protected Officer officer;
	private Flow.Subscription subscription;
	protected ArrayList<Resource> ressouces;
	private final String subscriberName;

	 public Worker(Anthill queen , int id,Officer officer)
	    {
	        this.queen = queen;
	        this.x = this.queen.x;
	        this.y = this.queen.y;
	        this.id = id;
			this.stop = false;
			this.officer = officer;
			this.ressouces = new ArrayList<>();
			this.subscriberName = String.valueOf(this.officer.id);
	        this.thread = new Thread(this);
			this.color = this.queen.color;
			this.must_ho_home = false;
	    }
	 
	 public void move() {
		 synchronized (ThreadLocalRandom.current()) {

			 ////////////////////////////////////////////////////////////////////////////////////////////////////////
			 ///////////////// MOVE FUNCTION : A ANT WILL GO RANDOMLY x={-1,0,1} , y = {-1,0,1} /////////////////////
			 ///////////////////////////////////////////////////////////////////////////////////////////////////////

			 // WE REMOVE WORKER FROM OLD POSITION
			 Case tile_old_worker;
			 tile_old_worker = this.queen.map.get_tile_with_coord(this.x, this.y);
			 tile_old_worker.unset_worker();
			 tile_old_worker.set_color("");
			 // CHOOSING RANDOM VALUE
			 int random_x_dir = ThreadLocalRandom.current().nextInt(-1, 1 + 1);
			 int random_y_dir = ThreadLocalRandom.current().nextInt(-1, 1 + 1);
			 // UPDATE ANT POSITION
			 this.x = this.x + random_x_dir;
			 this.y = this.y + random_y_dir;
			 //////////////////////////////////////////////////////////
			 /////////////   BORDER HANDLER //////////////////////////
			 //////////////////////////////////////////////////////////
			 /////////       THE MAP IS MODELLED AS A ROUNDED WORLD WHEN YOU REACH A BORDER YOU WILL
			 ////////        IN FACT REACH THE OPPOSITE SIDE

			 if (this.x > 24) {
				 this.x = 0;
			 }
			 if (this.y > 19) {
				 this.y = 0;

			 }
			 if (this.x < 0) {
				 this.x = 24;
			 }
			 if (this.y < 0) {
				 this.y = 19;
			 }


			 // Update worker position on the map
			 Case tile_worker;
			 tile_worker = this.queen.map.get_tile_with_coord(this.x, this.y);
			 tile_worker.set_worker();
			 // Setting the color
			 tile_worker.set_color(this.color);

		 }
	 }


	public void go_back_home() {
		synchronized (ThreadLocalRandom.current()) {
			// Drawing a direct line (Pythagore) to the home
			int x_home = this.queen.x;
			int y_home = this.queen.y;
			int delta_x = x_home - this.x;
			int delta_y = y_home - this.y;
			double delta_x_sqr = delta_x * delta_x;
			double delta_y_sqr = delta_y * delta_y;
			double sqrt_delta = delta_x_sqr - delta_y_sqr;
			double length = Math.pow(sqrt_delta, 1 / 2);

			// Remove old worker position from the map
			Case tile_old_worker;
			tile_old_worker = this.queen.map.get_tile_with_coord(this.x, this.y);
			tile_old_worker.unset_worker();
			tile_old_worker.set_color("");


			this.x = (int) (this.x + delta_x / length);
			this.y = (int) (this.y + delta_y / length);

			// Update worker position on the map
			Case tile_worker;
			tile_worker = this.queen.map.get_tile_with_coord(this.x, this.y);
			tile_worker.set_worker();
			tile_worker.set_color(this.color);
		}
	}


	public void run() {
		while (true) {

			// Worker has to stop , the order of going home has passed and the worker is at his anthill
			if (this.stop == true) {
				break;
			}


			// If the worker has gathered resources or has received the order to go home , the worker go home
			if (this.must_ho_home==true || this.ressouces.size() >4) {
				this.go_back_home();
			}

			// If the worker has received the order to go home and his at home ( his anthill) the worker has to stop
			if (this.must_ho_home==true && this.x == this.queen.x && this.y == this.queen.y) {
				this.stop = true;
			}



			// If the worker has gathered resources or has received the order to go home , the worker go home
				//If the worker has not a full inventory (<5 resources it moves using move() method (ie the ant will move x,y randomly(-1,0,+1)

				if (this.stop == false && this.ressouces.size() < 5) {
					//System.out.println("Worker MOVE" + "\n");
					this.move();
				}

				////////////// RESOURCE HANDLER
			Case tile_worker;
			tile_worker = this.queen.map.get_tile_with_coord(this.x, this.y);
			// IF the worker has resources and is at his anthill , the worker will drop resources in the anthill
			if (this.x == this.queen.x && this.y == this.queen.y && this.ressouces.size() > 0) {
				if (this.ressouces.get(0) != null) {
					synchronized (this.ressouces.get(0)) {
						Resource myResource = this.ressouces.get(0);
						this.queen.resources.add(myResource);
						this.ressouces.remove(0);
					}
				}
			}
			// TAKE RESOURCE
			else {
				if (tile_worker.resources.size() > 0 && this.ressouces.size() < 5) {
					Resource current_resource = tile_worker.getFirstResource();
					tile_worker.removeResource(current_resource);
					this.ressouces.add(current_resource);
				}




				// 50 ms between each action
				try {
					thread.sleep(50);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}



			}
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
			System.out.println(" WORKER GO HOME "+ this.color);
			this.must_ho_home = true;
		}
		if (order == 1) {
			//System.out.println("...");
		}
		try {
			this.thread.sleep(50);
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
