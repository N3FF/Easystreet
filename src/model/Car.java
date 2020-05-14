/*
 * TCSS 305 - Winter 2018 
 * Assignment 3 - Easystreet
 */
package model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 * Contains the functionalities of a car.
 * 
 * @author Justin Neff
 * @version 27 January
 *
 */
public class Car extends AbstractVehicle {
    /**
     * Duration the vehicle should remain "Dead" for.
     */
    private static final int DEATH_TIME = 10;
    /**
     * A list of valid terrain for a car.
     */
    private static final List<Terrain> VALID_TERRAIN =
                    Arrays.asList(Terrain.LIGHT, Terrain.CROSSWALK, Terrain.STREET);
    /**
     * Constructs a car taking in it's Starting X Position, Y Position, and
     * Direction.
     * 
     * @param theVehicleX Starting X Position
     * @param theVehicleY Starting Y Position
     * @param theDirection Starting Direction
     */
    public Car(final int theVehicleX, final int theVehicleY, final Direction theDirection) {
        super(theVehicleX, theVehicleY, theDirection, DEATH_TIME);
    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean passable = true;
        if (theTerrain == Terrain.CROSSWALK) {
            passable = theLight == Light.GREEN;
        } else if (theTerrain == Terrain.LIGHT) {
            passable = theLight != Light.RED;
        }
        return passable;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction newDirection;

        if (VALID_TERRAIN.contains(theNeighbors.get(getDirection()))) {
            newDirection = getDirection();
        } else if (VALID_TERRAIN.contains(theNeighbors.get(getDirection().left()))) {
            newDirection = getDirection().left();
        } else if (VALID_TERRAIN.contains(theNeighbors.get(getDirection().right()))) {
            newDirection = getDirection().right();
        } else {
            newDirection = getDirection().reverse();
        }

        return newDirection;
    }
}
