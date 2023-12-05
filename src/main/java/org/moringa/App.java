package org.moringa;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        //home/index page
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //animals routing
        post("/animals/new", (request, response) -> {

            Map<String, Object> model = new HashMap<>();

            String name = request.queryParams("name");
            String type = "common";
            Animal newAnimal = new Animal(name, type);
            newAnimal.save();

            model.put("animals", newAnimal);


            return new ModelAndView(model, "animals-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("endangered/new", (request, response) -> {

            Map<String, Object> model = new HashMap<>();

            String name = request.queryParams("name");
            String type = "endangered";
            String health = request.queryParams("health");
            String age = request.queryParams("age");

            EndangeredAnimal newEndageredAnimal = new EndangeredAnimal(name, type, health, age);
            newEndageredAnimal.save();
            model.put("animals", newEndageredAnimal);

            return new ModelAndView(model, "endangered-form.hbs");
        }, new HandlebarsTemplateEngine());


    }
}