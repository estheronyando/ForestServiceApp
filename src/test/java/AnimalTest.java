
import org.junit.Rule;
import org.junit.Test;
import org.moringa.Animal;


import java.util.Arrays;

import static org.junit.Assert.*;



public class AnimalTest {
    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void animal_instantiatesCorrectly_true() {
        Animal testAnimal = new Animal("Hare", "common");

        assertEquals(true, testAnimal instanceof Animal);
    }

    @Test
    public void getName_AnimalInstantiatesWithName_Hare() {
        Animal testAnimal = new Animal("Hare", "common");

        assertEquals("Hare", testAnimal.getName());
    }

    @Test
    public void getType_AnimalInstantiatesWithType_Common() {
        Animal testAnimal = new Animal("Hare", "common");
        assertEquals("common", testAnimal.getType());
    }

    @Test
    public void equals_returnsTrueIfNameAndTypeAreSame_true() {
        Animal testAnimal = new Animal("Hare", "common");
        Animal anotherAnimal = new Animal("Hare", "common");
        assertTrue(testAnimal.equals(anotherAnimal));
    }
    @Test
    public void equals_returnsFalseIfNameAndTypeAreDifferent_false() {
        Animal testAnimal = new Animal("Hare", "engangered");
        Animal anotherAnimal = new Animal("Tiger", "common");
        assertFalse(testAnimal.equals(anotherAnimal));
    }
    @Test
    public void save_insertsObjectIntoDatabase() {
        Animal testAnimal = new Animal("Hare", "common");
        testAnimal.save();
        assertTrue(Animal.getAllCommon().get(0).equals(testAnimal));
    }
    @Test
    public void all_returnsAllInstancesOfAnimal_true() {
        Animal testAnimal = new Animal("Hare", "common");
        Animal anotherAnimal = new Animal("Tiger", "common");
        testAnimal.save();
        anotherAnimal.save();

        assertEquals(true, Animal.all().get(1).equals(anotherAnimal));
        assertEquals(true, Animal.all().get(0).equals(testAnimal));


    }
    @Test
    public void save_assignsIdToAnimal() {
        Animal testAnimal = new Animal("Hare", "common");
        testAnimal.save();
        assertEquals(testAnimal.getId(),Animal.all().get(0).getId());
    }
    @Test
    public void find_returnsAnimalWithSameId_testAnimal() {
        Animal testAnimal = new Animal("Hare", "common");
        Animal anotherAnimal = new Animal("Tiger", "common");
        testAnimal.save();
        anotherAnimal.save();
        assertEquals(Animal.find(testAnimal.getId()), testAnimal);
    }

    @Test
    public void getAnimals_retrievesAllAnimalsFromDatabase_AnimalsList() {
        Animal testAnimal = new Animal("Hare", "common");
        Animal anotherAnimal = new Animal("Tiger", "common");
        testAnimal.save();
        anotherAnimal.save();
        Object[] animals= new Object[] { testAnimal, anotherAnimal };
        assertTrue(testAnimal.all().containsAll(Arrays.asList(animals)));
    }
    @Test
    public void deleteById_deletesAnimal_true() {
        Animal testAnimal = new Animal("Hare", "common");
        Animal anotherAnimal = new Animal("Tiger", "common");
        testAnimal.save();
        anotherAnimal.save();
        anotherAnimal.deleteById(anotherAnimal.getId());

        assertEquals(1, Animal.all().size());
    }
    @Test
    public void deleteAll_deletesAllAnimal_true() {
        Animal testAnimal = new Animal("Hare", "common");
        Animal anotherAnimal = new Animal("Tiger", "common");
        testAnimal.save();
        anotherAnimal.save();
        Animal.deleteAll();

        assertEquals(0, Animal.all().size());
    }
}