package Tests;

import Delta.DeltaCounter;
import org.junit.Assert;
import org.junit.Test;

public class DeltaCounterTest {

    private DeltaCounter deltaCounter;

    @Test
    public void minusDeltaTest() throws Exception {
        deltaCounter = new DeltaCounter(1.0, 4.0, 32.0);
        deltaCounter.count();

        Assert.assertEquals(deltaCounter.getDeltaState(), 0);
    }

    @Test
    public void zeroDeltaTest() throws Exception {
        deltaCounter = new DeltaCounter(1.0, 4.0, 4.0);
        deltaCounter.count();

        Assert.assertEquals(deltaCounter.getDeltaState(), 1);
        Assert.assertEquals(deltaCounter.getX1(), -2.0, 0.0001);
    }

    @Test
    public void doubleDeltaTest() throws Exception {
        deltaCounter = new DeltaCounter(1.0, 0.0, -4.0);
        deltaCounter.count();

        Assert.assertEquals(deltaCounter.getDeltaState(), 2);
        Assert.assertEquals(deltaCounter.getX1(), 2.0, 0.0001);
        Assert.assertEquals(deltaCounter.getX2(), -2.0, 0.0001);
    }

}