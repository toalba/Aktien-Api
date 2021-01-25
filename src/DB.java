import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DB
{
    static Connection con;
    private static String hostname = "localhost";
    private static String dbName = "aktien";
    private String dBPort = "3306";
    private static String userName = "root";
    private static String password = "wa22er!wasser";
    public static ArrayList<Datasheet> _Lastdataselect = new ArrayList<Datasheet>();
    /*public static void Connection()
    {
        String hostname = "localhost";
        String dbName = "Aktien";
        String dBPort = "3306";
        String userName = "root";
        String password = "SHW_Destroyer";
		 
        try
        {
            Class.forName("org.mariadb.jdbc.Driver");
        }
        catch(Exception e)
        {
            System.out.println("Treiber konnte nicht richtig geladen werden ... ");
            e.printStackTrace();
        }
        try
        {
               con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
               Statement statement = con.createStatement();
               //ResultSet rS=statement.executeQuery("Select * from ");

               con.close();
        }
        catch (SQLException e)
        {
               e.printStackTrace();
        }
    }*/

    public static void InsertStatement(String symbol,String date, double number)
    {

        String insertInTable =  "REPLACE INTO "+symbol+" VALUES('"+date+"', "+number+");";

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            Statement stm = con.createStatement();
            stm.execute(insertInTable);
        }
        catch (SQLException e)
        {
            System.out.println("Die Tabelle konnte die Values nicht aufnehmen");
            e.printStackTrace();
        }
    }

    public static void CreateTable(String symbol)
    {
        String createTable = "CREATE TABLE IF NOT EXISTS "+symbol+"(DATE CHAR(10) PRIMARY KEY, VALUE DOUBLE);";

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            Statement stm = con.createStatement();
            stm.execute(createTable);
        }
        catch (SQLException e)
        {
            System.out.println("Die Tabelle konnte nicht erzeugt werden");
            e.printStackTrace();
        }
    }
    public static void SelectStatement(String symbol){
        String selectCMD = "SELECT * FROM " + symbol+ ";";
        try
        {
            ArrayList<Datasheet> selectsheet = new ArrayList<>();
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            Statement stm = con.createStatement();
            ResultSet selection = stm.executeQuery(selectCMD);
            stm.execute(selectCMD);
            System.out.println("Datum      Wert");
            while(selection.next())
            {
                String date = selection.getString("Date");
                Double val = selection.getDouble("Value");
                selectsheet.add(new Datasheet(date,val));
            }
            _Lastdataselect=selectsheet;
        }
        catch (SQLException e)
        {
            System.out.println("Konnte keine Select Abfrage durchführen");
            e.printStackTrace();
        }
    }

    public static void CreateSTM()
    {
        String createDatabase = "CREATE DATABASE IF NOT EXISTS "+dbName;
        try {
                con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
                Statement stm = con.createStatement();
                stm.execute(createDatabase);
            }
        catch (SQLException e)
                 {
                    System.out.println("Die Datenbank "+dbName+" konnte nicht erstellt werden");
                    e.printStackTrace();
                 }
    }
    public static void UseSTM()
    {
        String useDatabase = "USE "+dbName;
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            Statement stm = con.createStatement();
            stm.execute(useDatabase);
        }
        catch (SQLException e)
        {
            System.out.println("Konnte die Datenbank "+dbName+" nicht festlegen");
            e.printStackTrace();
        }
    }
}