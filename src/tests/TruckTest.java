/*
 * TCSS 305 - Winter 2018 
 * Assignment 3 - Easystreet
 */

package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Atv;
import model.Bicycle;
import model.Car;
import model.Direction;
import model.Human;
import model.Light;
import model.Taxi;
import model.Terrain;
import model.Truck;
import model.Vehicle;
import org.junit.Test;

/**
 * Truck JUnit Test.
 * 
 * Truck's poke() is not fully covered because trucks can't die!
 * 
 * Some of truck's test methods were copied over as a template from the HumanTest 
 * written by Alan Fowler. 
 * 
 * @author Justin Neff
 * @author TCSS 305
 * @version 2 February
 *
 */
public class TruckTest {

    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;

    /**
     * Test method for {@link model.Truck#canPass(model.Terrain, model.Light)}.
     */
    @Test
    public final void testCanPass() {

        // Trucks can move on the Street, through Lights, and on the Crosswalk
        // so we need to test both of those conditions

        // Trucks should NOT choose to move to other terrain types
        // so we need to test that Trucks never move to other terrain types

        // Trucks should only reverse direction if no other option is available
        // so we need to be sure to test that requirement also

        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.LIGHT);
        validTerrain.add(Terrain.CROSSWALK);
        validTerrain.add(Terrain.STREET);

        final Truck truck = new Truck(0, 0, Direction.NORTH);
        // test each terrain type as a destination
        for (final Terrain destinationTerrain : Terrain.values()) {
            // try the test under each light condition
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.STREET) {
                    assertTrue("Trucks should be able to pass STREET" + ", with light "
                               + currentLightCondition,
                               truck.canPass(destinationTerrain, currentLightCondition));
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                    if (currentLightCondition == Light.RED) {
                        assertFalse("Trucks should NOT be able to pass " + destinationTerrain
                                    + ", with light " + currentLightCondition,
                                    truck.canPass(destinationTerrain, currentLightCondition));
                    } else { // light is yellow or red
                        assertTrue("Trucks should be able to pass " + destinationTerrain
                                   + ", with light " + currentLightCondition,
                                   truck.canPass(destinationTerrain, currentLightCondition));
                    }
                } else if (destinationTerrain == Terrain.LIGHT) {
                    assertTrue("Trucks should be able to pass LIGHT" + ", with light "
                               + currentLightCondition,
                               truck.canPass(destinationTerrain, currentLightCondition));
                }
            }
        }
    }

    /** Test method for Truck setters. */
    @Test
    public void testTruckSetters() {
        final Truck t = new Truck(10, 11, Direction.NORTH);
        t.setX(12);
        assertEquals("Truck setX failed!", 12, t.getX());
        t.setY(13);
        assertEquals("Truck setY failed!", 13, t.getY());
        t.setDirection(Direction.SOUTH);
        assertEquals("Truck setDirection failed!", Direction.SOUTH, t.getDirection());
    }

    /**
     * Test method for {@link model.Truck#Truck(int, int, model.Direction)}.
     */
    @Test
    public final void testTruck() {
        final Truck t = new Truck(10, 11, Direction.NORTH);

        assertEquals("Truck x coordinate not initialized correctly!", 10, t.getX());
        assertEquals("Truck y coordinate not initialized correctly!", 11, t.getY());
        assertEquals("Truck direction not initialized correctly!", Direction.NORTH,
                     t.getDirection());
        assertEquals("Truck death time not initialized correctly!", 0, t.getDeathTime());
        assertTrue("Truck isAlive() fails initially!", t.isAlive());
    }

    /**
     * Test method for {@link model.Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    public final void testChooseDirectionOnStreet() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.STREET);
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.STREET);

        boolean seenWest = false;
        boolean seenNorth = false;
        boolean seenEast = false;
        boolean seenSouth = false;

        final Truck truck = new Truck(0, 0, Direction.NORTH);

        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
            final Direction d = truck.chooseDirection(neighbors);

            if (d == Direction.WEST) {
                seenWest = true;
            } else if (d == Direction.NORTH) {
                seenNorth = true;
            } else if (d == Direction.EAST) {
                seenEast = true;
            } else if (d == Direction.SOUTH) { // this should NOT be chosen
                seenSouth = true;
            }
        }

        assertTrue("Truck chooseDirection() fails to select randomly "
                   + "among all possible valid choices!", seenWest && seenNorth && seenEast);

        assertFalse("Truck chooseDirection() reversed direction when not necessary!",
                    seenSouth);
    }

    /**
     * Test method for {@link Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionOnStreetMustReverse() {

        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.LIGHT && t != Terrain.CROSSWALK && t != Terrain.STREET) {

                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, Terrain.STREET);

                final Truck truck = new Truck(0, 0, Direction.NORTH);

                // the Truck must reverse and go SOUTH
                assertEquals("Truck chooseDirection() failed "
                             + "when reverse was the only valid choice!", Direction.SOUTH,
                             truck.chooseDirection(neighbors));
            }

        }
    }

    /**
     * Test method for {@link Truck#collide(java.util.Map)}.
     */
    @Test
    public void testCollide() {
        final List<Vehicle> list = new ArrayList<>();
        list.add(new Human(0, 0, Direction.NORTH));
        list.add(new Bicycle(0, 0, Direction.NORTH));
        list.add(new Atv(0, 0, Direction.NORTH));
        list.add(new Car(0, 0, Direction.NORTH));
        list.add(new Taxi(0, 0, Direction.NORTH));
        list.add(new Truck(0, 0, Direction.NORTH));
        final Truck t = new Truck(0, 0, Direction.NORTH);
        assertTrue(t.isAlive());
        for (final Vehicle v : list) {
            t.collide(v);
        }
        //Should always be alive since trucks never die.
        assertTrue(t.isAlive());
    }

    /**
     * Test method for {@link Truck#getImageFileName(java.util.Map)}.
     */
    @Test
    public void testGetImageFileName() {
        final Truck t = new Truck(0, 0, Direction.NORTH);
        assertEquals("truck.gif", t.getImageFileName());
    }

    /**
     * Test method for {@link Truck#reset(java.util.Map)}.
     */
    @Test
    public void testReset() {
        final Truck t = new Truck(0, 0, Direction.NORTH);
        t.setX(10);
        t.setY(10);
        assertEquals(10, t.getX());
        assertEquals(10, t.getY());
        t.reset();
        assertEquals(0, t.getX());
        assertEquals(0, t.getY());
        
        //toss in a poke to make sure poke() is working. Trucks don't die
        //so poke() should never get full coverage.
        t.poke();
    }
}
