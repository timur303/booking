package kg.kadyrbekov.model.enums;

public enum Night {

    NIGHT(1);

    private final double night;

    Night(int hours) {
        this.night = hours;
    }

    public double getHours() {
        return night;
    }
}