/*
 * TCSS 305 - Winter 2018 
 * Assignment 3 - Easystreet
 */
package model;
/**
 * Contains the functionalities of a taxi.
 * 
 * @author Justin Neff
 * @version 27 January
 *
 */
public class Taxi extends Car {

    /**
     * Taxis must stop at crosswalks for this duration.
     */
    private static final int STOP_DURATION = 3;
    /**
     * The duration this taxi has been at a crosswalk.
     */
    private int myCrossTimer;
    /**
     * Constructs a taxi taking in it's Starting X Position, Y Position, and
     * Direction.
     * 
     * @param theVehicleX Starting X Position
     * @param theVehicleY Starting Y Position
     * @param theDirection Starting Direction
     */
    public Taxi(final int theVehicleX, final int theVehicleY, final Direction theDirection) {
        super(theVehicleX, theVehicleY, theDirection);
        myCrossTimer = STOP_DURATION;
    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean passable = true;
        if (theTerrain == Terrain.CROSSWALK) {
            if (theLight == Light.RED && myCrossTimer > 0) {
                myCrossTimer--;
            } else {
                myCrossTimer = STOP_DURATION;
            }
            passable = myCrossTimer == STOP_DURATION;
        } else if (theTerrain == Terrain.LIGHT) {
            passable = theLight != Light.RED;
        }
        return passable;
    }
}
