package android.sead_systems.seads.main_menu;

public enum EnumNavBarNames {
    DEVICES(0, "Devices"),
    ROOMS(1, "Rooms"),
    OVERVIEW(2, "Overview");

    private final int index;
    private final String title;

    EnumNavBarNames(int index, String title) {
        this.index = index;
        this.title = title;
    }

    public int getIndex() {
        return this.index;
    }

    public String getTitle() {
        return this.title;
    }
}
