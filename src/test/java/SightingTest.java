import org.junit.Rule;
import org.junit.Test;
import org.moringa.Animal;
import org.moringa.Ranger;
import org.moringa.Sighting;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SightingTest {
    @Rule
    public DatabaseRule database = new DatabaseRule();


    @Test
    public void sighting_instantiatesCorrectly_true() {
        Sighting testSighting = new Sighting("Tiger", "Zone 12", 4567);

        assertEquals(true, testSighting instanceof Sighting);
    }

    @Test
    public void getAnimalName_SightingInstantiatesWithName_Dennis() {
        Animal testAnimal = new Animal("Tiger", "common");
        Sighting testSighting= new Sighting(testAnimal.getName(), "Zone 12",4567);

        assertEquals("Tiger", testSighting.getAnimalName());
    }


    @Test
    public void getLocation_SightingInstantiatesWithLocation_True() {
        Sighting testSighting= new Sighting("Tiger", "Zone 12",4567);


        assertEquals("Zone 12", testSighting.getLocation());
    }
    @Test
    public void getRangerId_SightingInstantiatesWithRangerId_True() {
        Ranger testRanger = new Ranger("Makaila", "7896","0710617457","makaila@gmail.com");
        Sighting testSighting= new Sighting("Tiger", "Zone 12",testRanger.getId());


        assertEquals(testRanger.getId(), testSighting.getRangerId());
    }
    @Test
    public void equals_returnsTrueIfInstancesSame_true() {
        Sighting testSighting= new Sighting("Tiger", "Zone 12",4567);

        Sighting anotherSighting =new Sighting("Tiger", "Zone 12",4567);


        assertTrue(testSighting.equals(anotherSighting));
    }
    @Test
    public void equals_returnsFalseIfInstancesDifferent_true() {
        Sighting testSighting= new Sighting("Tiger", "Zone 12",4567);

        Sighting anotherSighting =new Sighting("Hare", "Zone 14",4587);


        assertFalse(testSighting.equals(anotherSighting));
    }

    @Test
    public void save_insertsObjectIntoDatabase_Sighting() {
        Sighting testSighting= new Sighting("Tiger", "Zone 12",4567);
        testSighting.save();
        assertEquals(4567,testSighting.getRangerId());
    }
    @Test
    public void save_assignsIdToSighting() {
        Sighting testSighting= new Sighting("Tiger", "Zone 12",4567);

        testSighting.save();
        assertEquals(testSighting.getId(),Sighting.all().get(0).getId());
    }
    @Test
    public void all_returnsAllInstancesOfSighting_true() {
        Sighting testSighting= new Sighting("Tiger", "Zone 12",4567);
        testSighting.save();

        Sighting anotherSighting =new Sighting("Hare", "Zone 14",4587);
        anotherSighting.save();

        assertEquals(testSighting, Sighting.all().get(0));
        assertEquals(true, Sighting.all().get(1).equals(anotherSighting));

    }
    @Test
    public void find_returnsSightingWithSameId_testSighting() {
        Sighting testSighting= new Sighting("Tiger", "Zone 12",4567);

        Sighting anotherSighting =new Sighting("Hare", "Zone 14",4587);
        testSighting.save();
        anotherSighting.save();
        assertEquals(Sighting.find(testSighting.getId()).getId(), testSighting.getId());
    }
    @Test
    public void findRangerSightings_returnsARangerSightings_testSighting() {
        Ranger testRanger = new Ranger("Makaila", "7896","0710617457","makaila@gmail.com");
        testRanger.save();
        Sighting testSighting= new Sighting("Tiger", "Zone 12",testRanger.getId());

        testSighting.save();

        assertEquals(testRanger.getId(),Sighting.findRangerSighting(testRanger.getId()).get(0).getRangerId());
    }
    @Test
    public void deleteById_deletesSighting_true() {
        Sighting testSighting= new Sighting("Tiger", "Zone 12",4567);

        Sighting anotherSighting =new Sighting("Hare", "Zone 14",4587);
        testSighting.save();
        anotherSighting.save();
        anotherSighting.deleteById(anotherSighting.getId());

        assertEquals(1,Sighting.all().size());
    }
    @Test
    public void getSightings_retrievesAllSightingsFromDatabase_SighingsList() {
        Sighting testSighting= new Sighting("Tiger", "Zone 12",4567);

        Sighting anotherSighting =new Sighting("Hare", "Zone 14",4587);
        testSighting.save();
        anotherSighting.save();
        Object[]  sightings= new Object[] {testSighting,anotherSighting};
        assertTrue(Sighting.all().containsAll(Arrays.asList(sightings)));
    }
    @Test
    public void deleteAll_deletesAllSightings_true() {
        Sighting testSighting= new Sighting("Tiger", "Zone 12",4567);

        Sighting anotherSighting =new Sighting("Hare", "Zone 14",4587);
        testSighting.save();
        anotherSighting.save();
        Sighting.deleteAll();

        assertEquals(0,Sighting.all().size());
    }

}