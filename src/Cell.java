import java.util.ArrayList;
import java.util.Random;

import glm.Vec2;

public class Cell extends Base implements Runnable {
	
	private double switchPath;

	public Cell(Vec2 p) {
		Random rnd = new Random();
		position = new Vec2(p.v[0], p.v[1]);
		this.angle = (float) (rnd.nextFloat()*Math.PI*2.0);
		this.health = 60;
		alive = true;
		this.r = 1.0;
		this.g = 1.0;
		this.b = 1.0;
		this.a = 1.0;
		this.radius=0.0f;
		cellType = CellType.CELL;
		outsideMessages = new ArrayList<Message>();
		inputMessages = new ArrayList<Message>();
		switchPath = 1.0;
		initClone();
	}
	
	public void update(){
		Base target = null;
		double minDistance = 1000;
		for(int i=0;i<outsideMessages.size();i++)
		if(outsideMessages.get(i).cell.cellType==CellType.FOOD){
			double dist = Base.distance(this, outsideMessages.get(i).cell);
			if(minDistance>dist){
				minDistance = dist;
				target = outsideMessages.get(i).cell;
			}
		}
		if(health<0){
			this.alive=false;
			outputMessage = Message.Die();
		}
		Random rnd = new Random();
		health -= rnd.nextFloat()*0.01;
		float radiusTarget = 0.1f - ((100.0f-this.health)/100.0f)*0.06f; 
		this.radius += (radiusTarget-radius)*0.01;
		float speed=0.001f;
		if(target!=null){
			if(Base.toAngle(this, target)>30)
				angle += rnd.nextFloat()*0.01;
			if(Base.toAngle(this, target)<30)
				angle -= rnd.nextFloat()*0.01;
			if(health<100)
				outputMessage = Message.Eat(target);
		}
		else{
			if(rnd.nextInt(800)==0)switchPath = -switchPath;
			angle += rnd.nextFloat()*0.01*switchPath;
		}
		if(health>80){
			outputMessage = Message.Split();
		}
		position.v[0] += Math.cos((double)angle)*speed;
		position.v[1] += Math.sin((double)angle)*speed;
	}
}
