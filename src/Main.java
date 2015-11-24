import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import glm.Mat4;
import glm.Vec2;
import glm.Vec4;
import ogl.App;
import ogl.BadShaderException;
import ogl.DrawMode;
import ogl.Mouse;
import ogl.OpenGL;
import ogl.Shader;
import ogl.Shape;

public class Main extends App{
	private Shader testShader;
	private Shape screenQuad;
	private float time=0; 
	private int n = 100;
	private int maxN = 100;
	private Texture cellTexture;
	private Shape renderDuty;
	private Pool pool;
	public static void main(String[] args){
		new OpenGL("Cell game",(App)new Main());
	}

	public int startup(GL4 gl) {
		try {
			this.testShader = new Shader(gl, "test", new int[]{2}, new String[]{"xy"});
		} catch (BadShaderException e) {e.printStackTrace();}
		try {
			this.cellTexture = TextureIO.newTexture(new File("cellTexture.png"), false);
		} catch (GLException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		
		float ratio = (float)OpenGL.screenX/OpenGL.screenY;
		this.testShader.setUniformVec4(gl, "resolution", new Vec4(OpenGL.screenX, OpenGL.screenY, 0.0f, 0.0f));		
		this.testShader.setUniformTexture(gl, "tex0", 0, cellTexture);
		renderDuty = new Shape(gl);
		this.screenQuad = new Shape(gl);
		this.screenQuad.begin();
		this.screenQuad.add(new Vec2(-1.0f * ratio, -1.0f));
		this.screenQuad.add(new Vec2( 1.0f * ratio, -1.0f));
		this.screenQuad.add(new Vec2(-1.0f * ratio,  1.0f));
		this.screenQuad.add(new Vec2( 1.0f * ratio,  1.0f));
		pool = new Pool(n);
		(new Thread(pool)).start();
		Mouse.mouse.set(Mat4.identity(), Mat4.identity());
		return 0;
	}

	public void cleanup(GL4 gl) {
	}

	public void draw(GL4 gl) {
		renderDuty.begin();
		ArrayList<Base> cells = pool.getCells();
		for(int i=0;i<cells.size();i++){
			cells.get(i).draw(renderDuty);
		}
		for(int i=0;i<maxN;i++){
			if(i<cells.size()){
				this.testShader.setUniformVec2(gl, "points[" + i + "]", cells.get(i).position);
				this.testShader.setUniformFloat(gl, "radius[" + i + "]", cells.get(i).radius);
			}
			else{
				this.testShader.setUniformVec2(gl, "points[" + i + "]", new Vec2(-10,-10));
			}
		}
		
		this.testShader.setUniformFloat(gl, "time", time);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClear(gl.GL_COLOR_BUFFER_BIT);
		renderDuty.draw(gl, testShader, DrawMode.triangles);
	}

	public int update(GL4 gl) {
		
		return 0;
	}
}
