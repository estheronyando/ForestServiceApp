package org.moringa;

import org.sql2o.Connection;

import java.util.List;
import java.util.Objects;

public class Ranger implements DbManagement {
    private int id;
    private String name;
    private String badge_number;
    private String phone_number;
    private String email;

    public Ranger(String name, String badge_number, String phone_number, String email) {
        this.name = name;
        this.badge_number = badge_number;
        this.phone_number = phone_number;
        this.email = email;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBadgeNumber() {
        return badge_number;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void save() {
        try (Connection con=DB.sql2o.open()){
            String sql="INSERT INTO rangers (name,badge_number,phone_number,email) VALUES (:name,:badge_number,:phone_number,:email)";
            if(name.equals("")||badge_number.equals("")||phone_number.equals("")||email.equals("")){
                throw new IllegalArgumentException("All fields must be filled");
            }
            this.id=(int) con.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .addParameter("badge_number",this.badge_number)
                    .addParameter("phone_number",this.phone_number)
                    .addParameter("email",this.email)
                    .executeUpdate()
                    .getKey();


        }

    }
    public static List<Ranger> all() {
        String sql = "SELECT * FROM rangers";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Ranger.class);
        }
    }


    @Override
    public void update(){
        if(this.name.equals("")||this.badge_number.equals("")||this.phone_number.equals("")||this.email.equals("")){
            throw new IllegalArgumentException("Fill in all fields");
        }
        try(Connection con = DB.sql2o.open()){
            String sql ="UPDATE rangers SET name:name,badge_number:badge_number,phone_number:phone_number,email:email";
            con.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .addParameter("badge_number",this.badge_number)
                    .addParameter("phone_number",this.phone_number)
                    .addParameter("ranger_email",this.email)
                    .executeUpdate();
        }
    }
    public  static Ranger find(int id){
        try(Connection con = DB.sql2o.open()){
            String sql = "SELECT * FROM rangers WHERE id=:id";
            Ranger ranger = con.createQuery(sql)
                    .addParameter("id",id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Ranger.class);
            return ranger;
        }
    }
    public void deleteById(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM rangers WHERE id=:id";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    public static void deleteAll() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE  FROM rangers *; ";
            con.createQuery(sql)
                    .executeUpdate();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ranger)) return false;
        Ranger ranger = (Ranger) o;
        return Objects.equals(getBadgeNumber(), ranger.getBadgeNumber()) && Objects.equals(getEmail(), ranger.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBadgeNumber(), getEmail());
    }
}
