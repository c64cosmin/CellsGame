import java.util.Random;

import glm.Vec2;
import ogl.Shape;

public class Cell {
	public static Shape quads;
	public Vec2 position;
	private float radius;
	private float angle;
	public Cell(Vec2 p, float r){
		angle = new Random().nextFloat()*3.0f;
		position = p;
		radius = r;
	}
	public void draw(){
		addCell(quads, position, radius);
	}
	
	private void addCell(Shape q, Vec2 p, float s) {
		s+=0.01;
		q.add(p.add(new Vec2(-s,-s)));
		q.add(p.add(new Vec2( s,-s)));
		q.add(p.add(new Vec2(-s, s)));
		
		q.add(p.add(new Vec2(-s, s)));
		q.add(p.add(new Vec2( s,-s)));
		q.add(p.add(new Vec2( s, s)));
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
}
