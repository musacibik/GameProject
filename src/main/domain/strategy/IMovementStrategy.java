package main.domain.strategy;

import main.domain.Paintable;

public interface IMovementStrategy {
	void moveAnimation(Paintable context);
	String toString();
}
