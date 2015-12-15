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
		switchPath = 1.0;
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
			}
			Random rnd = new Random();
			health -= rnd.nextFloat()*0.01;
			float radiusTarget = 0.1f - ((100.0f-this.health)/100.0f)*0.06f; 
			this.radius += (radiusTarget-radius)*0.01;
			float speed=0.001f;
			if(target!=null){
				if(Base.toAngle(this, target)>30)
					angle += rnd.nextFloat()*0.03;
				if(Base.toAngle(this, target)<30)
					angle -= rnd.nextFloat()*0.03;
				if(target.getType() == Base.CellType.FOOD){
					((Food) target).canEat(this);
				}
			}
			else{
				if(rnd.nextInt(800)==0)switchPath = -switchPath;
				angle += rnd.nextFloat()*0.01*switchPath;
			}
			if(health>80){
				Pool.get().addNewCell(new Cell(new Vec2(this.position.v[0], this.position.v[1])));
				this.health-=25;
			}
			if(this.health<=0.0){
				Pool.get().addNewCell(new Food(new Vec2(this.position.v[0], this.position.v[1])));
				this.alive=false;
			}
			position.v[0] += Math.cos((double)angle)*speed;
			position.v[1] += Math.sin((double)angle)*speed;
		}
	}
}
