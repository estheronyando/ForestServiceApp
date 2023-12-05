package org.moringa;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import spark.ModelAndView;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    static int getAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String portEnv = processBuilder.environment().get("PORT");

        if (portEnv != null) {
            return Integer.parseInt(portEnv);
        } else {
            return 4567;
        }
    }

    public static void main(String[] args) {
        port(getAssignedPort());
        staticFileLocation("/public");

        Handlebars handlebars = new Handlebars();

        // Home/index page
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return render(handlebars, "index", model);
        });

        // Animals routing
        post("/animals/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            String type = "common";
            Animal newAnimal = new Animal(name, type);
            newAnimal.save();
            model.put("animals", newAnimal);
            return render(handlebars, "animals-form", model);
        });

        post("/endangered/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            String type = "endangered";
            String health = request.queryParams("health");
            String age = request.queryParams("age");
            EndangeredAnimal newEndangeredAnimal = new EndangeredAnimal(name, type, health, age);
            newEndangeredAnimal.save();
            model.put("animals", newEndangeredAnimal);
            return render(handlebars, "endangered-form", model);
        });

        // READ: Animal
        get("/animals/form", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("animals", Animal.all());
            return render(handlebars, "animals-form", model);
        });

        get("/animals/view", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("animals", Animal.all());
            return render(handlebars, "animals-view", model);
        });

        get("/endangered/form", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<String> age = Arrays.asList(
                    EndangeredAnimal.ADULT_AGE,
                    EndangeredAnimal.NEWBORN_AGE,
                    EndangeredAnimal.YOUNG_AGE
            );
            List<String> health = Arrays.asList(
                    EndangeredAnimal.ILL_HEALTH,
                    EndangeredAnimal.OKAY_HEALTH,
                    EndangeredAnimal.HEALTHY_HEALTH
            );
            model.put("health", health);
            model.put("age", age);
            return render(handlebars, "endangered-form", model);
        });

        get("/animals/:id/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int animalId = Integer.parseInt(request.params(":id"));
            Animal foundAnimal = Animal.find(animalId);
            foundAnimal.deleteById(animalId);
            model.put("animals", Animal.all());
            return render(handlebars, "animals-view", model);
        });

        get("/animals/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Animal.deleteAll();
            model.put("animals", Animal.all());
            return render(handlebars, "animals-view", model);
        });

        // Rangers routes
        post("/rangers/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            String badge_number = request.queryParams("badge_number");
            String phone_number = request.queryParams("phone_number");
            String email = request.queryParams("email");
            Ranger newRanger = new Ranger(name, badge_number, phone_number, email);
            newRanger.save();
            model.put("rangers", newRanger);
            return render(handlebars, "rangers-form", model);
        });

        // READ
        get("/rangers/form", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return render(handlebars, "rangers-form", model);
        });

        get("/rangers/view", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("rangers", Ranger.all());
            return render(handlebars, "rangers-view", model);
        });

        get("/rangers/sighting/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int rangerId = Integer.parseInt(request.params(":id"));
            model.put("rangerId", rangerId);
            model.put("sightings", Sighting.findRangerSighting(rangerId));
            return render(handlebars, "rangerSightingView", model);
        });

        // UPDATE & DELETE routes for rangers

        // Sighting views
        get("/sighting/:id/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int sightingId = Integer.parseInt(request.params(":id"));
            Sighting foundSighting = Sighting.find(sightingId);
            foundSighting.deleteById(sightingId);
            model.put("sightings", Sighting.all());
            return render(handlebars, "sighting-view", model);
        });

        get("/sightings/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Sighting.deleteAll();
            model.put("sightings", Sighting.all());
            return render(handlebars, "sightings-view", model);
        });

        post("/sighting/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String animal_name = request.queryParams("name");
            String location_id = request.queryParams("location");
            int ranger_id = Integer.parseInt(request.queryParams("ranger"));
            Sighting newSighting = new Sighting(animal_name, location_id, ranger_id);
            newSighting.save();
            model.put("sighting", newSighting);
            return render(handlebars, "sighting-form", model);
        });

        // READ routes for sightings

        get("/sighting/form", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("ranger", Ranger.all());
            model.put("animal", Animal.all());
            return render(handlebars, "sighting-form", model);
        });

        get("/sighting/view", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("sightings", Sighting.all());
            return render(handlebars, "sighting-view", model);
        });

        get("/sighting/view/desc", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("sightings", Sighting.allDescOrder());
            return render(handlebars, "sighting-view", model);
        });
    }

    private static String render(Handlebars handlebars, String templateName, Map<String, Object> model) {
        try {
            String templatePath="templates/" + templateName;
            Template template = handlebars.compile(templatePath);
            return template.apply(model);
        } catch (IOException e) {
            return "Error rendering template: " + e.getLocalizedMessage();
        }
    }
}