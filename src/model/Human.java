/*
 * TCSS 305 - Winter 2018 
 * Assignment 3 - Easystreet
 */

package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Contains the functionalities of a human.
 * 
 * @author Justin Neff
 * @version 27 January
 *
 */
public class Human extends AbstractVehicle {
    /**
     * Duration the vehicle should remain "Dead" for.
     */
    private static final int DEATH_TIME = 50;
    /**
     * A list of valid terrain for a human.
     */
    private static final List<Terrain> VALID_TERRAIN =
                    Arrays.asList(Terrain.CROSSWALK, Terrain.GRASS);

    /**
     * Constructs a human taking in it's Starting X Position, Y Position, and
     * Direction.
     * 
     * @param theVehicleX Starting X Position
     * @param theVehicleY Starting Y Position
     * @param theDirection Starting Direction
     */
    public Human(final int theVehicleX, final int theVehicleY, final Direction theDirection) {
        super(theVehicleX, theVehicleY, theDirection, DEATH_TIME);
    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean passable = VALID_TERRAIN.contains(theTerrain);
        if (theTerrain == Terrain.CROSSWALK) {
            passable = theLight != Light.GREEN;
        }
        return passable;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final List<Direction> directions = new ArrayList<>();
        Direction newDirection = null;
        for (final Entry<Direction, Terrain> entry : theNeighbors.entrySet()) {
            if (entry.getValue() == Terrain.CROSSWALK
                && entry.getKey() != getDirection().reverse()) {
                newDirection = entry.getKey();
            } else if (VALID_TERRAIN.contains(entry.getValue())) {
                directions.add(entry.getKey());
            }
        }
        if (newDirection == null) {
            if (directions.size() == 1) {
                newDirection = getDirection().reverse();
            } else {
                directions.remove(getDirection().reverse());
                newDirection = directions.get(random(directions.size()));
            }
        }
        return newDirection;
    }
}
