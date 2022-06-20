package pl.training.processor.adv.examples;

import java.util.EnumMap;
import java.util.EnumSet;

import static pl.training.processor.adv.examples.ConsoleLogger.getLogger;

public class EnumsExamples {

    public static void main(String[] args) {
        var summer = Season.SUMMER;
        System.out.println(Season.WINTER == Season.WINTER);
        System.out.println("Enum name: %s".formatted(summer.name()));
        System.out.println("Ordinal name: %s".formatted(summer.ordinal()));
        System.out.println("Instance: " + Season.valueOf("FALL"));

        for (Season season: Season.values()) {
            System.out.println(season.ordinal() + ":" + season.name());
        }

        switch (summer) {
            case WINTER -> System.out.println("It's cold");
            case SUMMER -> System.out.println("It's hot");
            default -> System.out.println("It's ok");
        }

        var result = switch (summer) {
            case WINTER -> "It's cold";
            case SUMMER -> "It's hot";
            default -> "It's ok";
        };

        System.out.println("Result: %s".formatted(result));

        //--------------------------------------------------------

        var earth = Planet.EARTH;
        System.out.println("Earth mass: %s".formatted(earth.getMass()));
        System.out.println("G constant: %s".formatted(Planet.G));
        System.out.println("Earth gravity: %s".formatted(earth.gravity()));

        //--------------------------------------------------------

        var planets = EnumSet.of(Planet.SATURN, Planet.EARTH, Planet.MARS);
        var bigPlanetsCount = planets.stream()
                .filter(planet -> planet.getRadius() > 3)
                .count();
        var planetsDescriptions = new EnumMap<Planet, String>(Planet.class);
        planetsDescriptions.put(Planet.MARS, "Very hot planet");
        planetsDescriptions.put(Planet.EARTH,"Home");
        planetsDescriptions.put(Planet.SATURN, "Cool planet");

        //--------------------------------------------------------

        // Singleton
        getLogger().log("Test log entry");
        // State / Strategy
        Planet.EARTH.gravity();
        Planet.MARS.gravity();
    }

}
