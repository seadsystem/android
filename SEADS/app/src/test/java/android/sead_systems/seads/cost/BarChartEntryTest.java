package android.sead_systems.seads.cost;

/**
 * Created by christopherpersons on 11/22/16.
 */

import android.sead_systems.seads.BarChartEntry;
import junit.framework.Assert;

import org.junit.Test;

public class BarChartEntryTest {

    @Test
    public void testInitialize() {
        BarChartEntry barChartEntry = new BarChartEntry("", 0.0f, 0);
        Assert.assertNotNull(barChartEntry);
    }

    @Test
    public void testConstructor() {
        BarChartEntry barChartEntry = new BarChartEntry("1:00 PM", 12.8f, 0);
        Assert.assertNotNull(barChartEntry);
        Assert.assertEquals("1:00 PM", barChartEntry.getTime());
        Assert.assertEquals(12.8f, barChartEntry.getCost());
        Assert.assertEquals(0, barChartEntry.getPosition());
    }

}
