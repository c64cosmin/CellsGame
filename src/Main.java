import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
	public static int maxN = 100;
	private Texture cellTexture;
	private Shape renderDuty;
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
		Pool.get();
		Mouse.mouse.set(Mat4.identity(), Mat4.identity());
		return 0;
	}

	public void cleanup(GL4 gl) {
	}

	public void draw(GL4 gl) {
		renderDuty.begin();
		ArrayList<Base> cells = Pool.get().getCells();
		for(int i=0;i<cells.size();i++){
			cells.get(i).draw(renderDuty);
		}
		for(int i=0;i<maxN;i++){
			if(i<cells.size()){
				this.testShader.setUniformVec2(gl, "points[" + i + "]", cells.get(i).position);
				this.testShader.setUniformFloat(gl, "radius[" + i + "]", cells.get(i).radius);
				this.testShader.setUniformVec4(gl, "colors[" + i + "]", new Vec4(cells.get(i).r, cells.get(i).g, cells.get(i).b, cells.get(i).a));
			}
			else{
				this.testShader.setUniformVec2(gl, "points[" + i + "]", new Vec2(-10,-10));
			}
		}
		
		this.testShader.setUniformFloat(gl, "time", time);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClear(GL4.GL_COLOR_BUFFER_BIT);
		renderDuty.draw(gl, testShader, DrawMode.triangles);
	}

	public int update(GL4 gl) {	
		return 0;
	}
}
