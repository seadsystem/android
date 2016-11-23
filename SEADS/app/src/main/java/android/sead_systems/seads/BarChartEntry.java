package android.sead_systems.seads;

/**
 * Created by christopherpersons on 11/22/16.
 */
public class BarChartEntry {
        private String time = "";
        private float cost = 0.0f;
        private int color = 0;
        private int position = 0;

        public BarChartEntry (String time, float cost, int position) {
            this.time = time;
            this.cost = cost;
            this.position = position;
        }

        public void setColor(int color) { this.color = color; }

        public String getTime() {
            return time;
        }

        public float getCost() { return cost; }

        public int getColor() {
            return color;
        }

        public int getPosition() { return position; }

        public String toString() {
            return "Time: " + this.time + "\n" +
                    "Cost: " + this.cost + "\n" +
                    "Color: " + this.color + "\n" +
                    "Position: " + this.position + "\n";
        }
}

