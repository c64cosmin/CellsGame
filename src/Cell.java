import java.util.Random;

import glm.Vec2;
import ogl.Shape;

public class Cell implements Runnable{
	public Vec2 position;
	private float radius;
	private float angle;
	public Cell(Vec2 p, float r){
		angle = (float)(new Random().nextFloat()*Math.PI);
		position = p;
		radius = r;
	}
	
	private Cell(Vec2 p, float r, float a){
		position = p;
		radius = r;
		angle = a;
	}
	
	public Cell(Cell cell) {
		this(cell.position, cell.radius, cell.angle);
	}

	public Vec2 toPoint(){
		return position;
	}
	
	public void update(){
		Random rnd = new Random();
		float speed=0.001f;
		angle += rnd.nextFloat()*0.01;
		position.v[0] += Math.cos((double)angle)*speed;
		position.v[1] += Math.sin((double)angle)*speed;
	}
	
	public void run() {
		
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
}
