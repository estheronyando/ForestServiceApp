package org.moringa;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        //ANIMAL
        get("animals/form", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("animals", Animal.all());

            return new ModelAndView(model, "animals-form.hbs");

        }, new HandlebarsTemplateEngine());

        get("/animals/view", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
//            model.put("animals",EndangeredAnimal.getAllEndangered());
            model.put("animals", Animal.all());
            return new ModelAndView(model, "animals-view.hbs");
        }, new HandlebarsTemplateEngine());

        get("/endangered/form", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            List<String> age = new ArrayList<String>();
            age.add(EndangeredAnimal.ADULT_AGE);
            age.add(EndangeredAnimal.NEWBORN_AGE);
            age.add(EndangeredAnimal.YOUNG_AGE);

            List<String> health = new ArrayList<String>();
            health.add(EndangeredAnimal.ILL_HEALTH);
            health.add(EndangeredAnimal.OKAY_HEALTH);
            health.add(EndangeredAnimal.HEALTHY_HEALTH);

            model.put("health", health);
            model.put("age", age);

            return new ModelAndView(model, "endangered-form.hbs");

        }, new HandlebarsTemplateEngine());


        get("/animals/:id/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int animalId = Integer.parseInt(request.params(":id"));
            Animal foundAnimal = Animal.find(animalId);

            foundAnimal.deleteById(animalId);
            model.put("animals", Animal.all());
            return new ModelAndView(model, "animals-view.hbs");
        }, new HandlebarsTemplateEngine());

        get("/animals/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Animal.deleteAll();
            model.put("animals", Animal.all());
            return new ModelAndView(model, "animals-view.hbs");

        }, new HandlebarsTemplateEngine());

        //rangers routes
        post("/rangers/new", (request, response) -> {

            Map<String, Object> model = new HashMap<>();

            String name = request.queryParams("name");
            String badge_number=request.queryParams("badge_number");
            String phone_number=request.queryParams("phone_number");
            String email=request.queryParams("email");


            Ranger newRanger = new Ranger(name,badge_number,phone_number,email);
            newRanger.save();

            model.put("rangers", newRanger);
            return new ModelAndView(model, "rangers-form.hbs");
        }, new HandlebarsTemplateEngine());


//        2.READ

        get("/rangers/form",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"rangers-form.hbs");
        },new HandlebarsTemplateEngine());

        get("/rangers/view",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("rangers",Ranger.all());
            return new ModelAndView(model,"rangers-view.hbs");
        },new HandlebarsTemplateEngine());



    }
}