package org.moringa;

import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Sighting implements DbManagement {
    private int id;
    private String animal_name;
    private String location_id;
    private int ranger_id;

    private String sight_time;
    Date now = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");


    public Sighting(String animal_name, String location_id, int ranger_id) {
        this.animal_name = animal_name;
        this.location_id = location_id;
        this.ranger_id = ranger_id;
        this.sight_time = dateFormat.format(new Timestamp(now.getTime()));
    }
    public int getId() {
        return id;
    }

    public String getAnimalName() {
        return animal_name;
    }



    public String getLocation() {
        return location_id;
    }

    public int getRangerId() {
        return ranger_id;
    }
    public String getSightTime() {
        return sight_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sighting)) return false;
        Sighting sighting = (Sighting) o;
        return Objects.equals(animal_name, sighting.animal_name) && Objects.equals(location_id, sighting.location_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animal_name, location_id);
    }
    @Override
    public void save() {
        if(this.animal_name.equals("")||this.location_id.equals(" ")||this.ranger_id == -1){
            throw new IllegalArgumentException("All fields must be filled");
        }
        try(Connection con =DB.sql2o.open()) {
//            String sql = "INSERT INTO sightings(animal_id,location_id,ranger_id,time) VALUES(:animal_id,:location_id,:ranger_id,:sightTime)";
            String sql= "INSERT INTO sightings (animal_name,location_id,ranger_id,sight_time) VALUES (:animal_name,:location_id," +
                    ":ranger_id,:sight_time)";

            this.id = (int) con.createQuery(sql, true)
                    .addParameter("animal_name", this.animal_name)
                    .addParameter("location_id", this.location_id)
                    .addParameter("ranger_id", this.ranger_id)
                    .addParameter("sight_time", this.sight_time)
                    .executeUpdate()
                    .getKey();
        }
    }
    public static List<Sighting> all(){
        try(Connection con = DB.sql2o.open()){
            String sql =" SELECT * FROM sightings";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Sighting.class);
        }
    }
    public static List<Sighting> allDescOrder(){
        try(Connection con = DB.sql2o.open()){
            String sql =" SELECT * FROM sightings ORDER BY sight_time DESC";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Sighting.class);
        }
    }


    @Override
    public void update() {
        if(this.animal_name.equals("")||this.location_id.equals("")||this.ranger_id== -1){
            throw new IllegalArgumentException("Fill in all fields");

        }
        try(Connection con =DB.sql2o.open()){
            String sql = "UPDATE sightings SET animal_name:animal_name,location_id:location_id,ranger_id:ranger_id";
            this.id =(int)con.createQuery(sql,true)
                    .addParameter("animal_name",this.animal_name)
                    .addParameter("location_id",this.location_id)
                    .addParameter("ranger_id",this.ranger_id)
                    .executeUpdate()
                    .getKey();

        }
    }

    public  static Sighting find(int id){
        try(Connection con = DB.sql2o.open()){
            String sql = "SELECT * FROM sightings WHERE id=:id";
            Sighting sighting = con.createQuery(sql)
                    .addParameter("id",id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Sighting.class);
            return sighting;
        }
    }

    public  static List<Sighting> findRangerSighting(int rangerId){
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM sightings WHERE ranger_id=:rangerId";
            return con.createQuery(sql)
                    .addParameter("rangerId",rangerId)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Sighting.class);

        }
    }


    public void deleteById(int sighting_id){
        try (Connection con=DB.sql2o.open()){
            String sql="DELETE FROM sightings WHERE id=:id";
            con.createQuery(sql)
                    .addParameter("id",this.id)
                    .executeUpdate();


        }

    }

    public static void deleteAll() {
        try (Connection con=DB.sql2o.open()){
            String sql = "DELETE FROM sightings";
            con.createQuery(sql)
                    .executeUpdate();
        }  catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

}