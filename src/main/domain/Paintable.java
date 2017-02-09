/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.domain;

import main.domain.strategy.IMovementStrategy;

/**
 *
 * @author JusticeLeague
 */
//Interface to List the printable objects
public abstract class Paintable {
	private IMovementStrategy movementStrategy;

	public IMovementStrategy getMovementStrategy() {
		return movementStrategy;
	}

	public void setMovementStrategy(IMovementStrategy movementStrategy) {
		this.movementStrategy = movementStrategy;
	}

}
