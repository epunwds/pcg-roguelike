package com.pcg.roguelike.entity.components;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Cr0s
 */
public class BobComponent implements Component {
	public Rectangle bob;

	public BobComponent (Rectangle bob) {
            this.bob = bob;
	}
}