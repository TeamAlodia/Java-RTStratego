import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Species {
  private int id;
  private String name = "HAS NOT BEEN SET";
  private int base_health = 10;
  private int base_defense = 1;
  private int base_power = 1;

  // Constructors
  public Species(){}
  public Species(String _name){
    name = _name;
  }
  public Species(String _name, int _base_health, int _base_defense, int _base_power){
    name = _name;
    base_health = _base_health;
    base_defense = _base_defense;
    base_power = _base_power;
  }


  // Getter/Setter functions
  public int getId(){
    return id;
  }
  public String getName(){
    return name;
  }
  public int getBase_Health(){
    return base_health;
  }
  public int getBase_Power(){
    return base_power;
  }
  public int getBase_Defense(){
    return base_defense;
  }

  // Database functions


  public static Species find(int _id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM species WHERE id=:id";
      return con.createQuery(sql)
        .addParameter("id", _id)
        .executeAndFetchFirst(Species.class);
    }
  }
  public static Species findByName(String _name){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM species WHERE name=:name";
      return con.createQuery(sql)
        .addParameter("name", _name)
        .executeAndFetchFirst(Species.class);
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO species (name, base_health, base_defense, base_power) VALUES (:name, :base_health, :base_defense, :base_power)";

      id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("base_health", base_health)
        .addParameter("base_defense", base_defense)
        .addParameter("base_power", base_power)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Species> all(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM species";

      return con.createQuery(sql)
        .executeAndFetch(Species.class);
    }
  }

  // Overrides
  @Override
  public boolean equals(Object otherSpecies){
    if (!(otherSpecies instanceof Species))
      return false;
    Species newSpecies = (Species) otherSpecies;
    return id == newSpecies.getId();
  }

}
