import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class DB
{
    private static Properties config = new Properties();
    private static String hostname = config.getProperty("Host");
    private static String dbName = config.getProperty("dbname");
    private String dBPort = config.getProperty("dbport");
    private static String userName = config.getProperty("username");
    private static String password = config.getProperty("password");
    static Connection con;

    static {
        try {
            config.load(new FileInputStream("/resource/config.properties"));
            System.out.println(hostname);
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Datasheet> _Lastdataselect = new ArrayList<Datasheet>();

    public static void InsertStatement(String symbol,String date, double number,double zwoh)
    {
        String insertInTable =  "REPLACE INTO "+symbol+" VALUES('"+symbol+"','"+date+"', "+number+","+zwoh+");";
        try
        {
            Statement stm = con.createStatement();
            stm.executeQuery(insertInTable);
        }
        catch (SQLException e)
        {
            System.out.println("Die Tabelle konnte die Values nicht aufnehmen");
            e.printStackTrace();
        }
    }
    public static void Updatezwoah(String symbol,String date,double zwoh )
    {
        String insertInTable =  "UPDATE "+symbol+" SET ZWOH="+zwoh+" WHERE DATE = ?;";
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            Statement stm = con.createStatement();
            PreparedStatement preparedStmt = con.prepareStatement(insertInTable);
            preparedStmt.setDate(1, java.sql.Date.valueOf("2021-01-26"));
            preparedStmt.executeQuery();
        }
        catch (SQLException e)
        {
            System.out.println("Die Tabelle konnte die Values nicht aufnehmen");
            e.printStackTrace();
        }
    }

    public static void CreateTable(String symbol)
    {
        String createTable = "CREATE TABLE IF NOT EXISTS "+symbol+"(SYMBOL CHAR(10) ,DATE DATE PRIMARY KEY, CLOSE DOUBLE,ZWOH DOUBLE );";

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
            while(selection.next())
            {
                String date = selection.getString("Date");
                Double val = selection.getDouble("Close");
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
    public static void SelectAVGStatement(String symbol){
        String selectAVGCMD = "SELECT AVG(close) FROM " + symbol + " WHERE DATE BETWEEN DATE_SUB(?, INTERVAL 200 DAY) AND ?;";
        Connection con = null;
        Statement stmt = null;
        Date avgDate = new Date();
        try
        {
            Class.forName("org.mariadb.jdbc.Driver");
            con  = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);


            stmt = con.createStatement();
            PreparedStatement preparedStmt = con.prepareStatement(selectAVGCMD);
            preparedStmt.setDate(1, java.sql.Date.valueOf("2021-01-26"));
            preparedStmt.setDate(2, java.sql.Date.valueOf("2021-01-26"));
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next())
            {
                DB.Updatezwoah(symbol,"2021-01-26",rs.getDouble("AVG(close)"));
            }

        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }finally {
            try {
                if (stmt != null) {
                    con.close();
                }
            } catch (SQLException e)
            {
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
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