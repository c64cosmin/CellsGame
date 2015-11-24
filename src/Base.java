import java.util.ArrayList;
import java.util.Random;

import glm.Vec2;
import ogl.Shape;

public class Base implements Runnable{
	public enum CellType {
		FOOD,
		CELL,
		FEMALE,
		MALE
	};
	public CellType cellType;
	public Vec2 position;
	public float radius;
	public float angle;
	public boolean alive;
	public float health;
	public double r;
	public double g;
	public double b;
	public double a;
	protected boolean hasInputMessages;
	protected ArrayList<Message> outsideMessages;
	protected ArrayList<Message> inputMessages;
	protected ArrayList<Message> outputMessages;
	public Base myClone;
	
	protected Base(){
		
	}
	
	protected Base(Vec2 p, CellType type, float radius, float angle, float health, double r, double g, double b, double a){
		position = new Vec2(p.v[0], p.v[1]);
		this.radius = radius;
		this.angle = angle;
		this.health = health;
		this.health = (new Random()).nextFloat()*100.0f;
		alive = true;
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		outsideMessages = new ArrayList<Message>();
		inputMessages = new ArrayList<Message>();
		outputMessages = new ArrayList<Message>();
		this.cellType = type;
		initClone();
	}
	
	protected void initClone(){
		myClone = new Base();
		myClone.position = new Vec2(position.v[0], position.v[1]);
		myClone.radius = this.radius;
		myClone.angle = this.angle;
		myClone.health = this.health;
		myClone.alive = this.alive;
		myClone.r = r;
		myClone.g = g;
		myClone.b = b;
		myClone.a = a;
		myClone.cellType = cellType;
		myClone.outputMessages = new ArrayList<Message>();
	}
	
	public Base(Vec2 p, float r, float health){
		this(p, CellType.FOOD, r, (float)(new Random().nextFloat()*Math.PI*2.0), health, new Random().nextFloat(),new Random().nextFloat(),new Random().nextFloat(),1.0);
	}	
	
	public Base clone(){
		Base r;
		r = new Base(position, cellType, radius, angle, health, this.r, this.g, this.b, this.a);
		r.alive = alive;
		return r;
	}
	
	public void update(){
		this.radius = 0.1f - ((100.0f-this.health)/100.0f)*0.06f;
		Random rnd = new Random();
		float speed=0.001f;
		angle += rnd.nextFloat()*0.01;
		position.v[0] += Math.cos((double)angle)*speed;
		position.v[1] += Math.sin((double)angle)*speed;
	}
	
	public void run() {
		int counter = 0;
		while(alive){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter++;
			if(counter%3 == 0){
				counter=0;
				synchronized(this){
					ArrayList<Message> oldMessages = myClone.outputMessages;
					myClone = this.clone();
					for(int i=0;i<outputMessages.size();i++){
						myClone.outputMessages.add(outputMessages.get(i).clone());
					}
					/*for(int i=0;i<oldMessages.size();i++){
						myClone.outputMessages.add(oldMessages.get(i).clone());
					}*/
					outputMessages.clear();
				}
				if(hasInputMessages){
					synchronized(this){
						outsideMessages.clear();
						for(int i=0;i<inputMessages.size();i++){
							outsideMessages.add(inputMessages.get(i).clone());
						}
					}
					hasInputMessages = false;
				}
			}
			update();
		}
		synchronized(this){
			myClone = this.clone();
		}
	}
	
	public void draw(Shape shape){
		float s = radius + 0.01f;
		shape.add(position.add(new Vec2(-s,-s)));
		shape.add(position.add(new Vec2( s,-s)));
		shape.add(position.add(new Vec2(-s, s)));
		
		shape.add(position.add(new Vec2(-s, s)));
		shape.add(position.add(new Vec2( s,-s)));
		shape.add(position.add(new Vec2( s, s)));
	}
	
	public void inputMessage(ArrayList<Message> msg){
		synchronized(this){
			inputMessages.clear();
			for(int i=0;i<msg.size();i++){
				inputMessages.add(msg.get(i).clone());
			}
			hasInputMessages = true;
		}
	}

	public synchronized Base getClone() {
		return myClone;
	}
	
	public static double distance(Base base1, Base base2) {
		return Math.sqrt(Math.pow(base1.position.v[0]-base2.position.v[0], 2) + Math.pow(base1.position.v[1]-base2.position.v[1], 2));
	}

	public static float angle(Cell cell, Base target){
	   float angle = (float) Math.toDegrees(Math.atan2(target.position.v[1] - cell.position.v[1], target.position.v[0] - cell.position.v[0]));
	    if(angle < 0){
	        angle += 360;
	    }
	    return angle;
	}
	public static float toAngle(Cell cell, Base target) {
		float angle = angle(cell,target);
		int d = (int) (Math.abs(angle - cell.angle*180/Math.PI) % 360);
		int r = d > 180 ? 360 - d : d;
		return r;
	}

	public boolean collide(Base base) {
		if(radius+base.radius>=Base.distance(this, base))
			return true;
		return false;
	}
}
