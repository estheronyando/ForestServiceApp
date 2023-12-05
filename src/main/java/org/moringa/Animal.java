package org.moringa;

import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.Objects;

public class Animal implements DbManagement {
    public int id;
    public String name;

    public String type;
    public String health;
    public String age;


    public Animal(String name, String type) {
        this.name = name;
        this.type = type;


    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getHealth() {
        return health;
    }

    public String getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return Objects.equals(getName(), animal.getName()) && Objects.equals(getType(), animal.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType());
    }


    @Override
    public void save() {
        if(this.name.equals(null)||this.type.equals(null)){
            throw new IllegalArgumentException("Fill in all fields");
        }
        try(Connection con = DB.sql2o.open()){
            String sql ="INSERT INTO animals (name,type) VALUES (:name,:type)";

            this.id=(int) con.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .addParameter("type",this.type)
                    .executeUpdate()
                    .getKey();
        }

    }

    public static List<Animal> all(){
        String sql = "SELECT * FROM animals ";
        try(Connection con = DB.sql2o.open()){
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Animal.class);
        }

    }
    public static List<Animal> getAllCommon(){
        String sql = "SELECT * FROM animals WHERE type= 'common'";
        try(Connection con = DB.sql2o.open()){
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Animal.class);
        }

    }



    @Override
    public void update() {
        if(this.name.equals(null)||this.type.equals(null)){
            throw new IllegalArgumentException("Kindly fill in all fields");
        }
        try(Connection con = DB.sql2o.open()){
            String sql ="UPDATE animals SET type:type health=:health,age=:age WHERE id:id";
            con.createQuery(sql,true)
                    .addParameter("type",this.type)
                    .addParameter("health","")
                    .addParameter("age","")
                    .addParameter("id",this.id)
                    .executeUpdate();
        }

    }

    public static Animal find(int id){
        String sql = "SELECT * FROM animals WHERE id=:id";
        try(Connection con = DB.sql2o.open()){
            Animal animal = con.createQuery(sql)
                    .addParameter("id",id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Animal.class);
            return animal;

        }
    }
    public void deleteById(int id) {
        try(Connection con = DB.sql2o.open()){
            String sql = "DELETE FROM animals where id=:id ";
            con.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }

    }
    public static void deleteAll(){
        try (Connection con=DB.sql2o.open()){
            String sql = "DELETE FROM animals";
            con.createQuery(sql)
                    .executeUpdate();
        }  catch (Sql2oException ex){
            System.out.println(ex);
        }

    }



}