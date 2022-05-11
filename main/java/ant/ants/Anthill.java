package ant.ants;
import java.util.ArrayList;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Anthill extends Thread {
	int id;
	int x;
	int y;
	String color;

	int order ;
	protected ArrayList<Resource> resources;

	// Content is a list of all content in the tile , due to the fact that a tile may contain more than 1 ant
	public ArrayList <String> content;

	public Map map;
	// List of all workers on the map
	protected ArrayList<Worker> workers;
	// List of all Officers on the map
	protected ArrayList<Officer> officers;

	protected Thread thread;

	// public ArrayList <OrderQueen> queenorders;

	Anthill(int id, int x, int y, Map map, String color) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.map = map;
		this.color = color;
		this.order = 1;
		this.resources = new ArrayList<>();
		officers = new ArrayList<Officer>();

		// INIT OFFICERS
		for (int i = 0; i <1 ; i++) {

			Officer officer = new Officer(this, i);
			officers.add(officer);
			officer.start();

			// Adding the officer to the map , the officer will be displayed at the anthill position
			Case tile_officer;
			tile_officer = this.map.get_tile_with_coord(this.x, this.y);
			tile_officer.set_officer();


			// CREATE WORKERS WHO BELONGS TO THE OFFICER

			workers = new ArrayList<Worker>();

			for (int j = 0; j < 1; j++) {

				Worker worker = new Worker(this, j,officer);
				workers.add(worker);
				worker.start();
				// Updating position of the ant to the map
				Case tile_worker;
				tile_worker = this.map.get_tile_with_coord(this.x, this.y);
				tile_worker.set_worker();
			}

		}
		// START THREAD
		this.thread = new Thread(this);
		this.thread.start();

	}
	public void addRessouce(Resource r){
		this.resources.add(r);
	}

	public void run() {
		// While true to not stop drawing

		// IF NEW ORDER


		while (true) {

			final SubmissionPublisher<Integer> publisher =
					new SubmissionPublisher<>(ForkJoinPool.commonPool(), 20);


			for (Officer officer : officers) {

				// SENDING ORDERS TO ALL OFFICERS
				publisher.subscribe(officer);
				int order = this.order;
				int MAX_SECONDS_TO_KEEP_IT_WHEN_NO_SPACE = 2;
				final int lag = publisher.offer(
						order,
						MAX_SECONDS_TO_KEEP_IT_WHEN_NO_SPACE,
						TimeUnit.SECONDS,
						(subscriber, msg) -> {
							subscriber.onError(
									new RuntimeException("Hey " + ((Officer) subscriber)
											.getSubscriberName() + "! You are too slow getting orders" +
											" and we don't have more space for them! " +
											" : " + msg));
							return false; // don't retry, we don't believe in second opportunities
						});
				/*
				if ((officer.thread == null) || (!officer.thread.isAlive())) {
					officer.thread = new Thread(officer);
					officer.thread.start();

				*/

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}

			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}









	public void set_order(int order)
	{
		System.out.println("ORDER CHANGED ");
		this.order = order;
	}
}
