/*
 * TCSS 305 - Winter 2018 
 * Assignment 3 - Easystreet
 */

package model;

import java.util.Map;
import java.util.Random;

/**
 * Abstract Vehicle class for all the moving vehicles that are added to the GUI panel.
 * @author Justin Neff
 * @version 30 January
 */
public abstract class AbstractVehicle implements Vehicle {

    /**
     * Random Object.
     */
    private final Random myRandom = new Random();
    /**
     * Starting X position.
     */
    private final int myStartX;
    /**
     * Starting Y position.
     */
    private final int myStartY;
    /**
     * Initial direction.
     */
    private final Direction myStartDirection;
    /**
     * Current X Position.
     */
    private int myVehicleX;
    /**
     * Current Y Position.
     */
    private int myVehicleY;
    /**
     * Current Direction.
     */
    private Direction myDirection;
    /**
     * Is the vehicle currently dead/alive.
     */
    private boolean myIsAlive = true;
    /**
     * Duration the vehicle should be "Dead" for.
     */
    private final int myDeathDuration;
    /**
     * A timer to keep track of how long a vehicle has been "Dead" for.
     */
    private int myDeathTimer;

    /**
     * Abstract Vehicle constructor that sets the vehicles current X position, Y
     * position, starting direction, and "Death" duration.
     * 
     * @param theVehicleX Starting X position
     * @param theVehicleY Starting Y position
     * @param theDirection Starting direction
     * @param theDeathDuration Duration that vehicles should remain "Dead" for
     */
    protected AbstractVehicle(final int theVehicleX, final int theVehicleY,
                              final Direction theDirection, final int theDeathDuration) {
        myDirection = theDirection;
        myVehicleX = theVehicleX;
        myVehicleY = theVehicleY;

        myStartDirection = theDirection;
        myStartX = theVehicleX;
        myStartY = theVehicleY;

        myDeathDuration = theDeathDuration;

    }

    @Override
    public abstract boolean canPass(Terrain theTerrain, Light theLight);

    @Override
    public abstract Direction chooseDirection(Map<Direction, Terrain> theNeighbors);

    @Override
    public void collide(final Vehicle theOther) {
        if (myIsAlive) {
            myIsAlive = theOther.getDeathTime() >= myDeathDuration;
        }

    }

    @Override
    public int getDeathTime() {
        return myDeathDuration;
    }

    @Override
    public String getImageFileName() {
        final String objName = getClass().getSimpleName().toLowerCase();
        // Ternary operator is awesome. Spoke to you about it in class being for
        // this use.
        return myIsAlive ? objName + ".gif" : objName + "_dead.gif";
    }

    @Override
    public Direction getDirection() {
        return myDirection;
    }

    @Override
    public int getX() {
        return myVehicleX;
    }

    @Override
    public int getY() {
        return myVehicleY;
    }

    @Override
    public boolean isAlive() {
        return myIsAlive;
    }

    @Override
    public void poke() {
        if (!myIsAlive) {
            if (myDeathTimer >= 0) {
                myDeathTimer--;
            } else {
                myDeathTimer = myDeathDuration;
                myDirection = Direction.random();
                myIsAlive = true;
            }
        }
    }

    @Override
    public void reset() {
        myVehicleX = myStartX;
        myVehicleY = myStartY;
        myDirection = myStartDirection;

    }

    @Override
    public void setDirection(final Direction theDir) {
        myDirection = theDir;

    }

    @Override
    public void setX(final int theX) {
        myVehicleX = theX;

    }

    @Override
    public void setY(final int theY) {
        myVehicleY = theY;

    }

    /**
     * Generates a random integer between 0 and the given maximum value.
     * 
     * @param theMax Maximum random value
     * @return int - Random integer
     */
    public int random(final int theMax) {
        return myRandom.nextInt(theMax);
    }
}
