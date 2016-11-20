package com.pcg.roguelike.entity.components;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author Cr0s
 */
public class VisualComponent implements Component {
	public TextureRegion region;

	public VisualComponent (TextureRegion region) {
		this.region = region;
	}
}