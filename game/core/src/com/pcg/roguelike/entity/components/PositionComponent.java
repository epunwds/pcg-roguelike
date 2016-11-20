package com.pcg.roguelike.entity.components;
import com.badlogic.ashley.core.Component;

/**
 *
 * @author Cr0s
 */
public class PositionComponent implements Component {
	public float x, y;

	public PositionComponent (float x, float y) {
		this.x = x;
		this.y = y;
	}
}