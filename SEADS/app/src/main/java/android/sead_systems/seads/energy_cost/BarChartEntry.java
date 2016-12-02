package android.sead_systems.seads.energy_cost;

/**
 * Container class for a single bar chart entry used by {@link EnergyCostActivity}
 * @author Chris Persons
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

