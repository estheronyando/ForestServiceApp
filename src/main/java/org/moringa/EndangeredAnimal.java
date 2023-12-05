package org.moringa;

import org.sql2o.Connection;

public class EndangeredAnimal extends Animal implements DbManagement {

    public static final String ILL_HEALTH = "Ill";
    public static final String OKAY_HEALTH = "Okay";
    public static final String HEALTHY_HEALTH = "Healthy";

    public static final String NEWBORN_AGE = "Newborn";
    public static final String YOUNG_AGE = "Young";
    public static final String ADULT_AGE = "Adult";


    public EndangeredAnimal(String name, String type, String health, String age) {
        super(name, type);
        this.age = age;
        this.health = health;
        this.type = type;

    }
    @Override
    public void save() {
        if(this.name.equals("") ||this.type.equals("")||this.health.equals("")||this.age.equals("")){
            throw new IllegalArgumentException("Fill in all fields");
        }
        try(Connection con = DB.sql2o.open()){
            String sql ="INSERT INTO animals (name,type,health,age) VALUES(:name,:type,:health,:age)";
            this.id = (int) con.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .addParameter("type",this.type)
                    .addParameter("health",this.health)
                    .addParameter("age",this.age)
                    .executeUpdate()
                    .getKey();
        }

    }
    public static EndangeredAnimal find(int id){
        String sql = "SELECT * FROM animals WHERE id:id";
        try(Connection con = DB.sql2o.open()){
            EndangeredAnimal enAnimal = con.createQuery(sql)
                    .addParameter("id",id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(EndangeredAnimal.class);
            return enAnimal;

        }
    }

}