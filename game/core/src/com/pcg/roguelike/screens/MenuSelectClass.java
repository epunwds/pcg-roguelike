package com.pcg.roguelike.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class MenuSelectClass implements Screen {

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private ImageButton buttonWizard, buttonWarrior;
    private TextButton buttonBack;
    private BitmapFont white, black;
    private Label heading1, heading2;


    @Override
    public void show() {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // creating fonts
        black = new BitmapFont(Gdx.files.internal("font/black.fnt"), false);

        /* Load textures */
        Texture tex = new Texture(Gdx.files.internal("player.png"));
        TextureRegion[][] playerTextures = TextureRegion.split(tex, 8, 8);        
        Sprite playerWizard = new Sprite(playerTextures[1][0]);
        playerWizard.setSize(128, 128);

        Sprite playerWarrior = new Sprite(playerTextures[5][0]);
        playerWarrior.setSize(128, 128);        
        
        // creating buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        ImageButton.ImageButtonStyle buttonWizardStyle = new ImageButton.ImageButtonStyle();
        buttonWizardStyle.imageUp = new SpriteDrawable(playerWizard);
        buttonWizardStyle.pressedOffsetX = 1;
        buttonWizardStyle.pressedOffsetY = -1;
        
        //button wizard
        buttonWizard = new ImageButton(buttonWizardStyle);
        buttonWizard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Play(0));
            }
        });
        
        ImageButton.ImageButtonStyle buttonWarriorStyle = new ImageButton.ImageButtonStyle();
        buttonWarriorStyle.imageUp = new SpriteDrawable(playerWarrior);
        buttonWarriorStyle.pressedOffsetX = 1;
        buttonWarriorStyle.pressedOffsetY = -1;
        
        //button warrior
        buttonWarrior = new ImageButton(buttonWarriorStyle);
        buttonWarrior.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Play(1));
            }
        });        

        // button exit
        buttonBack = new TextButton("Back", textButtonStyle);
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });

        // creating headings
        heading1 = new Label("New game", new Label.LabelStyle(black, Color.WHITE));
        heading1.setFontScale(3);
        
        heading2 = new Label("Select class", new Label.LabelStyle(black, Color.WHITE));
        heading2.setFontScale(2);
        
        // putting stuff together
        table.add(heading1).spaceBottom(5);
        table.row();
        
        table.add(heading2).spaceBottom(55);
        table.row();
        
        Table temp = new Table();
        
        temp.add(buttonWizard).spaceRight(50).spaceBottom(15);
        temp.add(buttonWarrior).spaceBottom(15);

        temp.row();

        temp.add(new Label("Wizard", new Label.LabelStyle(black, Color.WHITE))).spaceRight(50);
        temp.add(new Label("Warrior", new Label.LabelStyle(black, Color.WHITE)));
        
        table.add(temp).colspan(25).spaceBottom(25);
        
        table.row();
        table.add(buttonBack).spaceBottom(60);
        
        stage.addActor(table);
    }
    
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
