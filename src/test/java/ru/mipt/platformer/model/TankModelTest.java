import static org.junit.jupiter.api.Assertions.*;
import static com.badlogic.gdx.math.MathUtils.isEqual;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.TankModel;

public class TankModelTest {

    private TankModel tank;
    private GridPoint2 initialCoordinates;

    @BeforeEach
    public void setUp() {
        initialCoordinates = new GridPoint2(5, 5);
        tank = new TankModel(initialCoordinates, 2f);
    }

    @Test
    public void testConstructorInitializesFields() {
        assertEquals(initialCoordinates, tank.getCoordinates());
        assertEquals(initialCoordinates, tank.getDestinationCoordinates());
    }

    @Test
    public void testSetDestinationCoordinates() {
        GridPoint2 dest1 = new GridPoint2(20, 20);
        tank.setDestinationCoordinates(dest1);
        assertFalse(dest1.equals(tank.getCoordinates()));
        assertTrue(dest1.equals(tank.getDestinationCoordinates()));

        GridPoint2 dest2 = new GridPoint2(10, 10);
        tank.setDestinationCoordinates(dest2);
        assertFalse(dest2.equals(tank.getCoordinates()));
        assertFalse(dest2.equals(tank.getDestinationCoordinates()));

        assertFalse(dest1.equals(tank.getCoordinates()));
        assertTrue(dest1.equals(tank.getDestinationCoordinates()));
        tank.continueProgress(2f);
        tank.commitAction();
        assertTrue(dest1.equals(tank.getCoordinates()));
    }

    @Test
    public void testMoveDestination() {
        GridPoint2 offset = new GridPoint2(2, 3);
        float newRotation = 45f;

        tank.moveDestination(offset, newRotation);
        GridPoint2 expectedDest = offset.add(tank.getCoordinates());
        tank.continueProgress(2f);
        tank.commitAction();
        assertTrue(isEqual(newRotation, tank.getRotation()));
        assertEquals(tank.getCoordinates(), expectedDest);
    }
}