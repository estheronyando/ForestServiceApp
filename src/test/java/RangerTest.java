import org.junit.Rule;
import org.junit.Test;
import org.moringa.Ranger;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RangerTest {
    @Rule
    public DatabaseRule database = new DatabaseRule();


    @Test
    public void Ranger_instantiatesCorrectly_true() {
        Ranger testRanger = new Ranger("Dennis", "6574", "28394835", "dennis@gmail.com");

        assertEquals(true, testRanger instanceof Ranger);
    }

    @Test
    public void getName_RangerInstantiatesWithName_Dennis() {
        Ranger testRanger = new Ranger("Dennis", "6574","28394835","dennis@gmail.com");

        assertEquals("Dennis", testRanger.getName());
    }
    @Test
    public void getBadgeNumber_RangerInstantiatesWithBadgeNumber_True() {
        Ranger testRanger = new Ranger("Dennis", "6574","28394835","dennis@gmail.com");

        assertEquals("6574", testRanger.getBadgeNumber());
    }
    @Test
    public void getPhoneNumber_RangerInstantiatesWithPhoneNumber_True() {
        Ranger testRanger = new Ranger("Dennis", "6574","28394835","dennis@gmail.com");

        assertEquals("28394835", testRanger.getPhoneNumber());
    }
    @Test
    public void getEmail_RangerInstantiatesWithEmail_True() {
        Ranger testRanger = new Ranger("Dennis", "6574","28394835","dennis@gmail.com");

        assertEquals("dennis@gmail.com", testRanger.getEmail());
    }

    @Test
    public void equals_returnsTrueIfInstancesSame_true() {
        Ranger testRanger = new Ranger("Dennis", "6574","28394835","dennis@gmail.com");
        Ranger anotherRanger = new Ranger("Dennis", "6574","28394835","dennis@gmail.com");

        assertTrue(testRanger.equals(anotherRanger));
    }
    @Test
    public void equals_returnsFalseIfInstances_false() {
        Ranger testRanger = new Ranger("Dennis", "6574","28394835","dennis@gmail.com");
        Ranger anotherRanger = new Ranger("Makaila", "7896","0710617457","makaila@gmail.com");
        assertFalse(testRanger.equals(anotherRanger));
    }

    @Test
    public void save_insertsObjectIntoDatabase_Ranger() {
        Ranger testRanger = new Ranger("Dennis", "6574","28394835","dennis@gmail.com");
        testRanger.save();
        assertEquals("dennis@gmail.com",testRanger.getEmail());
    }
    @Test
    public void save_assignsIdToRanger() {
        Ranger testRanger = new Ranger("Dennis", "6574","28394835","dennis@gmail.com");
        testRanger.save();
        assertEquals(testRanger.getId(),Ranger.all().get(0).getId());
    }
    @Test
    public void all_returnsAllInstancesOfRangers_true() {
        Ranger testRanger = new Ranger("Dennis", "6574","28394835","dennis@gmail.com");
        Ranger anotherRanger = new Ranger("Makaila", "7896","0710617457","makaila@gmail.com");

        testRanger.save();
        anotherRanger.save();

        assertEquals(true, Ranger.all().get(1).equals(anotherRanger));
        assertEquals(true, Ranger.all().get(0).equals(testRanger));
    }
    @Test
    public void getAnimals_retrievesAllRangersFromDatabase_RangersList() {
        Ranger testRanger = new Ranger("Dennis", "6574","28394835","dennis@gmail.com");
        Ranger anotherRanger = new Ranger("Makaila", "7896","0710617457","makaila@gmail.com");
        testRanger.save();
        anotherRanger.save();
        Object[] rangers = new Object[] { testRanger, anotherRanger};
        assertTrue(testRanger.all().containsAll(Arrays.asList(rangers)));
    }
    @Test
    public void find_returnsRangerWithSameId_testRanger() {
        Ranger testRanger = new Ranger("Dennis", "6574","28394835","dennis@gmail.com");
        Ranger anotherRanger = new Ranger("Makaila", "7896","0710617457","makaila@gmail.com");
        testRanger.save();
        anotherRanger.save();
        assertEquals(Ranger.find(testRanger.getId()), testRanger);
    }

    @Test
    public void deleteById_deletesAnimal_true() {
        Ranger testRanger = new Ranger("Dennis", "6574","28394835","dennis@gmail.com");
        Ranger anotherRanger = new Ranger("Makaila", "7896","0710617457","makaila@gmail.com");
        testRanger.save();
        anotherRanger.save();
        anotherRanger.deleteById(anotherRanger.getId());

        assertEquals(1,Ranger.all().size());
    }
    @Test
    public void deleteAll_deletesAllAnimal_true() {
        Ranger testRanger = new Ranger("Dennis", "6574","28394835","dennis@gmail.com");
        Ranger anotherRanger = new Ranger("Makaila", "7896","0710617457","makaila@gmail.com");
        testRanger.save();
        anotherRanger.save();
        Ranger.deleteAll();

        assertEquals(0, Ranger.all().size());
    }
}