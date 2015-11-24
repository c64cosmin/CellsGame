import java.util.ArrayList;
import java.util.Random;

import glm.Vec2;
import glm.Vec4;
import ogl.Shape;

public class Base implements Runnable{
	public Vec2 position;
	public float radius;
	public float angle;
	public boolean alive;
	public float health;
	public Vec4 color;
	private boolean hasInputMessages;
	private ArrayList<Message> outsideMessages;
	private ArrayList<Message> inputMessages;
	private ArrayList<Message> outputMessages;
	public Base myClone;
	
	private Base(){
		
	}
	
	private Base(Vec2 p, float r, float a, float health, Vec4 color){
		position = p;
		radius = r;
		angle = a;
		this.health = health;
		this.health = (new Random()).nextFloat()*100.0f;
		alive = true;
		this.color = color;
		outsideMessages = new ArrayList<Message>();
		inputMessages = new ArrayList<Message>();
		outputMessages = new ArrayList<Message>();
		myClone = new Base();
		myClone.position = this.position;
		myClone.radius = this.radius;
		myClone.angle = this.angle;
		myClone.health = this.health;
		myClone.alive = this.alive;
		myClone.color = this.color;
	}
	
	public Base(Vec2 p, float r, float health){
		this(p, r, (float)(new Random().nextFloat()*Math.PI*2.0), health, new Vec4(new Random().nextFloat(),new Random().nextFloat(),new Random().nextFloat(),1.0));
	}	
	
	public Base clone(){
		Base r;
		r = new Base(position, radius, angle, health, new Vec4(color.v[0],color.v[1],color.v[2],color.v[3]));
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
		ArrayList<Message> inputMessagesList;
		int worldSyncCounter = 0;
		int updateCounter = 0;
		while(alive){
			worldSyncCounter++;
			if(worldSyncCounter == 5000){
				if(hasInputMessages){
					synchronized(this){
						outsideMessages.clear();
						for(int i=0;i<inputMessages.size();i++){
							outsideMessages.add(inputMessages.get(i).clone());
						}
						myClone = this.clone();
					}
					hasInputMessages = false;
				}
				worldSyncCounter=0;
			}
			updateCounter++;
			if(updateCounter == 300000){
				updateCounter = 0;
				update();
			}
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
}
