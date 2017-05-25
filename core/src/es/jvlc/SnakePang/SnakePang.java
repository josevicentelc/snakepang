package es.jvlc.SnakePang;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class SnakePang extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	TextureRegion fondoMenu;

	private ControlMenu control;
	private Nivel nivel;

	@Override
	public void create () {
		control = new ControlMenu();
		batch = new SpriteBatch();
		img = new Texture("Sprites.png");
		fondoMenu = new TextureRegion(img, 138, 0, 843, 548);
		nivel = new Nivel(img);
	}

	@Override
	public void render (){
		batch.begin();
		//Si no hay un partido iniciado, muestro el menu
		if (nivel.isFinDePartido()){
			batch.draw(fondoMenu, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			Gdx.input.setInputProcessor(control);
			int a =control.botonPulsado();

			//si habia algun boton pulsado
			if (a != -1){
				if (a==1) nivel.jugar(2);
				if (a==2) nivel.jugar(1);
				if (a==3) nivel.jugar(0);
			}
		}
		else{
			//Caso de partido iniciado, muestro el campo
			nivel.draw(batch);
		}
		batch.end();
	}
}
