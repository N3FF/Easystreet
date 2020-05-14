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
 * Contains the functionalities of a truck.
 * 
 * @author Justin Neff
 * @version 27 January
 *
 */
public class Truck extends AbstractVehicle {

    /**
     * Duration the vehicle should remain "Dead" for.
     */
    private static final int DEATH_TIME = 0;
    /**
     * A list of valid terrain.
     */
    private static final List<Terrain> VALID_TERRAIN =
                    Arrays.asList(Terrain.LIGHT, Terrain.CROSSWALK, Terrain.STREET);
    /**
     * A List of directions used to initialize an array to handle valid
     * directions that the truck can travel in.
     */
    private final List<Direction> myDirections;

    /**
     * Constructs a truck taking in it's Starting X Position, Y Position, and
     * Direction.
     * 
     * @param theVehicleX Starting X Position
     * @param theVehicleY Starting Y Position
     * @param theDirection Starting Direction
     */
    public Truck(final int theVehicleX, final int theVehicleY, final Direction theDirection) {
        super(theVehicleX, theVehicleY, theDirection, DEATH_TIME);
        myDirections = Arrays.asList(Direction.EAST, Direction.NORTH, Direction.SOUTH,
                                     Direction.WEST);
    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean passable = true;
        if (theTerrain == Terrain.CROSSWALK) {
            passable = !(theLight == Light.RED);
        }
        return passable;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final List<Direction> directions = new ArrayList<Direction>(myDirections);
        Direction newDir = getDirection().reverse();
        for (final Entry<Direction, Terrain> entry : theNeighbors.entrySet()) {
            if (!VALID_TERRAIN.contains(entry.getValue())
                || entry.getKey().equals(getDirection().reverse())) {
                directions.remove(entry.getKey());
            }
        }
        if (!directions.isEmpty()) {
            newDir = directions.get(random(directions.size()));
        }
        return newDir;
    }
}
