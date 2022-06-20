package pl.training.processor.adv.examples;

public enum Planet {

    EARTH(5.9, 6.3),
    MARS(3.2, 11.1) {

        @Override
        public double gravity() {
            return super.gravity() * 0.99;
        }

    },

    SATURN(1.0, 2.5);

    public static final double G = 6.6e-11;

    private final double mass;
    private final double radius;

    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    public double gravity() {
        return G * mass / (radius * radius);
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

}
