import java.util.ArrayList;
import java.util.Random;

import glm.Vec2;

public class Female extends Base implements Runnable {
	
	private double switchPath;
	private Male mate;

	public Female(Vec2 p) {
		Random rnd = new Random();
		position = new Vec2(p.v[0], p.v[1]);
		this.angle = (float) (rnd.nextFloat()*Math.PI*2.0);
		this.health = 60;
		alive = true;
		this.r = 1.0;
		this.g = 0.7;
		this.b = 0.7;
		this.a = 1.0;
		this.radius=0.0f;
		cellType = CellType.FEMALE;
		switchPath = 1.0;
		mate = null;
		this.speed = 0.001 + rnd.nextDouble()*0.0005;
	}
	
	public void update(){
		if(health<0){
			this.alive=false;
		}
		if(alive){
			Base target = null;
			double minDistance = 1000;
			ArrayList<Base> cells = Pool.get().getCells();
			for(int i=0;i<cells.size();i++){
				double distance = Base.distance(this, cells.get(i));
				if(distance>0.001 && distance<minDistance)
				if(cells.get(i).isAlive())
				if(cells.get(i).getType() == Base.CellType.FOOD){
					target = cells.get(i);
					minDistance = distance;
				}
				if(health > 40)
				if(distance>0.001 && distance<minDistance)
				if(cells.get(i).isAlive())
				if(cells.get(i).getType() == Base.CellType.MALE){
					target = cells.get(i);
					minDistance = distance;
				}
			}
			Random rnd = new Random();
			health -= rnd.nextFloat()*0.01;
			float radiusTarget = 0.1f - ((100.0f-this.health)/100.0f)*0.06f; 
			this.radius += (radiusTarget-radius)*0.01;
			if(target!=null){
				if(Base.toAngle(this, target)>30)
					angle += rnd.nextFloat()*0.08;
				if(Base.toAngle(this, target)<30)
					angle -= rnd.nextFloat()*0.08;
				if(target.getType() == Base.CellType.FOOD){
					((Food) target).canEat(this);
				}
			}
			else{
				if(rnd.nextInt(800)==0)switchPath = -switchPath;
				angle += rnd.nextFloat()*0.01*switchPath;
			}
			if(this.health<=0.0){
				Pool.get().addNewCell(new Food(new Vec2(this.position.v[0], this.position.v[1])));
				this.alive=false;
			}
			if(health > 120){
				health -= 60;
				Pool.get().addNewCell(new Egg(new Vec2(this.position.v[0], this.position.v[1])));
			}
			if(this.mate != null){
				this.health -= 20;
				mate.mated();
				Pool.get().addNewCell(new Egg(new Vec2(this.position.v[0], this.position.v[1])));
				this.mate = null;
			}
			position.v[0] += Math.cos((double)angle)*speed;
			position.v[1] += Math.sin((double)angle)*speed;
		}
	}

	public synchronized boolean canMate(Male male) {
		if(this.mate == null)
		if(this.alive)
		if(this.health>40){
			mate = male;
			return true;
		}
		return false;
	}
}
