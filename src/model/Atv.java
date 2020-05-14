/*
 * TCSS 305 - Winter 2018 
 * Assignment 3 - Easystreet
 */
package model;

import java.util.Map;

/**
 * Contains the functionalities of an ATV.
 * 
 * @author Justin Neff
 * @version 27 January
 *
 */
public class Atv extends AbstractVehicle {
    /**
     * Duration the vehicle should remain "Dead" for.
     */
    private static final int DEATH_TIME = 20;
    /**
     * Constructs an ATV taking in it's Starting X Position, Y Position, and
     * Direction.
     * 
     * @param theVehicleX Starting X Position
     * @param theVehicleY Starting Y Position
     * @param theDirection Starting Direction
     */
    public Atv(final int theVehicleX, final int theVehicleY, final Direction theDirection) {
        super(theVehicleX, theVehicleY, theDirection, DEATH_TIME);
    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return theTerrain != Terrain.WALL;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction newDirection = Direction.random();
        while (theNeighbors.get(newDirection).equals(Terrain.WALL)
               || newDirection.equals(getDirection().reverse())) {
            newDirection = Direction.random();
        }
        return newDirection;
    }
}
