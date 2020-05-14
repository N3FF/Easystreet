/*
 * TCSS 305 - Winter 2018 
 * Assignment 3 - Easystreet
 */
package model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Contains the functionalities of a bicycle.
 * 
 * @author Justin Neff
 * @version 27 January
 *
 */
public class Bicycle extends AbstractVehicle {
    /**
     * Duration the vehicle should remain "Dead" for.
     */
    private static final int DEATH_TIME = 30;
    /**
     * A list of valid terrain for a bicycle.
     */
    private static final List<Terrain> VALID_TERRAIN = 
                Arrays.asList(Terrain.CROSSWALK, Terrain.TRAIL, Terrain.LIGHT, Terrain.STREET);
    /**
     * Constructs a bicycle taking in it's Starting X Position, Y Position, and
     * Direction.
     * 
     * @param theVehicleX Starting X Position
     * @param theVehicleY Starting Y Position
     * @param theDirection Starting Direction
     */
    public Bicycle(final int theVehicleX, final int theVehicleY,
                   final Direction theDirection) {
        super(theVehicleX, theVehicleY, theDirection, DEATH_TIME);

    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean passable = true;
        if (theTerrain == Terrain.CROSSWALK || theTerrain == Terrain.LIGHT) {
            passable = theLight == Light.GREEN;
        }
        return passable;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction newDir = getDirection().reverse();
        final List<Direction> dirPriority =
                        Arrays.asList(getDirection(), getDirection().left(),
                                      getDirection().right(), getDirection().reverse());
        for (final Entry<Direction, Terrain> entry : theNeighbors.entrySet()) {
            if (VALID_TERRAIN.contains(entry.getValue())) {
                if (entry.getValue().equals(Terrain.TRAIL)
                    && !entry.getKey().equals(getDirection().reverse())) {
                    newDir = entry.getKey();
                    break;
                } else if (dirPriority.indexOf(entry.getKey()) < dirPriority.indexOf(newDir)) {
                    newDir = entry.getKey();
                }
            }
        }
        return newDir;
    }
}
