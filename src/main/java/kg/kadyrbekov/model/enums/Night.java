package kg.kadyrbekov.model.enums;

public enum Night {

    NIGHT(1);

    private final double night;

    Night(int count) {
        this.night = count;
    }

    public double getCount() {
        return night;
    }
}