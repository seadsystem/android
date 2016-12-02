package android.sead_systems.seads.energy_cost;

/**
 * Container class for a single bar chart entry used by {@link EnergyCostActivity}
 * @author Chris Persons
 *
 * @class BarChartEntry is a class that is used to hold data for each entry on the bar chart.
 *  time: String that is used to hold the specific hour of the day.
 *  cost: Float value of how many cents per kWh for the specified time.
 *  color: Int value that represents the color of the Bar Entry. Color is dfferent based off
 *      the cost.
 *  position: Int value that represents the position of the value as it is retrieved from the
 *      database. It is also used for sorting and applying the proper colors to the bars.
 */

class BarChartEntry {

    private String mTime = "";
    private float mCost = 0.0f;
    private int mColor = 0;
    private int mPosition = 0;

    public BarChartEntry (String time, float cost, int position) {
        mTime = time;
        mCost = cost;
        mPosition = position;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public String getTime() {
        return mTime;
    }

    public float getCost() {
        return mCost;
    }

    public int getColor() {
        return mColor;
    }

    public int getPosition() {
        return mPosition;
    }

    public String toString() {
        return "Time: " + mTime + "\n" + "Cost: " + mCost + "\n" + "Color: " + mColor + "\n"
                + "Position: " + mPosition + "\n";
    }

}

