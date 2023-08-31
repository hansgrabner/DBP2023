package at.dbp.klausur;


import at.dbp.klausur.models.KlausurProjektaufgaben;
import at.dbp.klausur.models.KlausurProjekte;

import java.sql.*;
import java.util.ArrayList;

public class DBPKlausurHelper {
    private String url = "jdbc:sqlite:C:\\LVs\\DBP2023\\DBPKlausur2023Grabner.db";

    //Aufgabe 2a
    public void createTableKlausurProjekte(){

        try (Connection conn = DriverManager.getConnection(url)) {

            String ddlCreateProjekte="CREATE TABLE KlausurProjekte\n" +
                    "(\n" +
                    "    ProjektID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    Projektbezeichnung varchar(50), " +
                    "    Projekttyp varchar(50),\n" +
                    "    Budget double(10,2),\n" +
                    "    Laufzeit int\n" +
                    ")";

            Statement ddlCreateProjekteStmt = conn.createStatement();
            ddlCreateProjekteStmt.execute(ddlCreateProjekte);
            System.out.println("Table KlausurProjekte succesfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connection;

    public DBPKlausurHelper(String url) {
        try {
            connection = DriverManager.getConnection(url);

            connection.createStatement().execute("PRAGMA foreign_keys = ON");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Aufgabe 2c
    public void createTableKlausurProjektaufgaben() {
        try (Connection conn = DriverManager.getConnection(url)) {

            String createProjekte = "CREATE TABLE IF NOT EXISTS Projektaufgaben(\n" +
                    " ProjektAufgabeID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    " ProjektID int, " +
                    " Aufgabenbezeichnung varchar(50), " +
                    " AufwandInStunden int, \n " +
                    " AssignedEmployeeID int, \n " +
                    " CONSTRAINT FK_ProjektID FOREIGN KEY (ProjektID) " +
                    " REFERENCES KlausurProjekte(ProjektID) " +
                    " CONSTRAINT FK_AssignedEmployeeID FOREIGN KEY (AssignedEmployeeID)" +
                    " REFERENCES Employees(EmployeeID)" +
                    ")";

            Statement createProjekteStmt = conn.createStatement();
            createProjekteStmt.execute(createProjekte);
            System.out.println("Table KlausurProjektaufgaben succesfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Aufgabe 3
    public KlausurProjekte insertProjekt(KlausurProjekte neuesProjekt) {
        String insertProjekt = "INSERT INTO KlausurProjekte (\n" +
                " Projektbezeichnung, Projekttyp, Budget,Laufzeit) \n" +
                " VALUES (?,?,?,?);";

        try {
            PreparedStatement pStmt = connection.prepareStatement(insertProjekt);
            pStmt.setString(1, neuesProjekt.getProjektbezeichnung());
            pStmt.setString(2, neuesProjekt.getProjekttyp());
            pStmt.setDouble(3, neuesProjekt.getBudget());
            pStmt.setInt(4, neuesProjekt.getLaufzeit());
            pStmt.executeUpdate();

            int id = getLastInsertId();
            neuesProjekt.setProjektId(id);

        } catch (SQLException ex) {
            System.out.printf("%s", ex.getStackTrace());
        }
        return neuesProjekt;
    }

    // needed for Aufgabe 3 -- check the auto ID of the last insertion, to set it to the new Projekt created
    private int getLastInsertId() throws SQLException {
        String readLastId = "SELECT last_insert_rowid() as rowid ";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(readLastId);
        rs.next();
        int id = rs.getInt("rowid");
        return id;
    }

    //Aufgabe 4a
    public KlausurProjekte getProjektById(int id) {
        String selectProjektByID = "SELECT ProjektID, Projektbezeichnung, Projekttyp, Budget, Laufzeit FROM KlausurProjekte WHERE ProjektID =?";

        KlausurProjekte kp1 = new KlausurProjekte();
        kp1.setProjektId(id);

        try {
            PreparedStatement pStmt = connection.prepareStatement(selectProjektByID);
            pStmt.setInt(1, id);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                kp1.setProjektbezeichnung(rs.getString("Projektbezeichnung"));
                kp1.setProjekttyp(rs.getString("Projekttyp"));
                kp1.setBudget(rs.getDouble("Budget"));
                kp1.setLaufzeit(rs.getInt("Laufzeit"));
            } else {
                System.out.println("Project with the ID " + kp1.getProjektId() + " was not found.");
            }


        } catch (SQLException ex) {
            System.out.printf("%s", ex.getStackTrace());
        }
        return kp1;
    }

    //Aufgabe 4b
    public ArrayList<KlausurProjekte> getAlleProjekteSortedByProjekttypFilteredByMaxLaufzeit(int laufzeit) {
        String selectProjects = "SELECT * FROM KlausurProjekte WHERE Laufzeit <= ? ORDER BY Projekttyp";

        ArrayList<KlausurProjekte> myProjects = new ArrayList<KlausurProjekte>();

        try {
            PreparedStatement pStmt = connection.prepareStatement(selectProjects);
            pStmt.setInt(1, laufzeit);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                KlausurProjekte projekt = new KlausurProjekte();
                projekt.setProjektId(rs.getInt("ProjektId"));
                projekt.setProjektbezeichnung(rs.getString("Projektbezeichnung"));
                projekt.setProjekttyp(rs.getString("Projekttyp"));
                projekt.setBudget(rs.getDouble("Budget"));
                projekt.setLaufzeit(rs.getInt("Laufzeit"));

                myProjects.add(projekt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myProjects;
    }


    //Aufgabe 5
    public KlausurProjektaufgaben insertProjektaufgabe(KlausurProjektaufgaben aufgabe) {
        String insertAufgabe = "INSERT INTO Projektaufgaben (" +
                " ProjektID, Aufgabenbezeichnung, AufwandInStunden, AssignedEmployeeID)" +
                " VALUES (?,?,?,?)";
        try {
            //Statement.RETURN_GENERATED_KEYS so that i can access the auto incremented ID after the insert
            PreparedStatement pStmt = connection.prepareStatement(insertAufgabe, Statement.RETURN_GENERATED_KEYS);
            pStmt.setInt(1, aufgabe.getProjektID());
            pStmt.setString(2, aufgabe.getAufgabenbezeichnung());
            pStmt.setInt(3, aufgabe.getAufwandInStunden());
            pStmt.setInt(4, aufgabe.getAssignedEmployeeID());

            int affectedRows = pStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Task could not be created, no rows affected.");
            }
            try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) {
                //if the generated ey exists (if the insert was successful), set it to the aufgabe
                if (generatedKeys.next()) {
                    aufgabe.setProjektAufgabeID(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Task could not be created, no ID was found");
                }
                System.out.println(aufgabe + " was added");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return aufgabe;
    }

    //Aufgabe 8
    public void createAdditionalTableAndFillWithSampleValues() {
        String createStmt = "CREATE TABLE IF NOT EXISTS Employees (\n" +
                " EmployeeID INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                " Name varchar(255), \n" +
                " Position varchar(100))";

        String sampleStmt = "INSERT INTO Employees(Name, Position)" +
                " VALUES (?,?)";

        try {
            connection = DriverManager.getConnection(url);


            PreparedStatement pStmt = connection.prepareStatement(createStmt);
            pStmt.executeUpdate(); //execute the create table statement

            pStmt = connection.prepareStatement(sampleStmt);
            insertEmployee(pStmt, "Sofia", "CEO");
            insertEmployee(pStmt, "Carla", "CFO");
            insertEmployee(pStmt, "Duarte", "Assistant");

        } catch (SQLException ex) {
            System.out.printf("%s", ex.getStackTrace());
        }
    }

    // needed for Aufgabe 8 -- extra method to insert Employees
    private static void insertEmployee(PreparedStatement preparedStatement, String name, String position) throws SQLException {
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, position);
        preparedStatement.executeUpdate();
    }

    public void transferBudget(int oldProjektId, int newProjektId, double budget) {
        String update1 = "UPDATE KlausurProjekte set Budget = Budget - ? WHERE ProjektID = ?";
        String update2 = "UPDATE KlausurProjekte set Budget = Budget + ? WHERE ProjektID = ?";

        try {
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false); //es gibt kein AutoCommit mehr, alle Transatktionen müssen mit commit abgeschlossen werden
            PreparedStatement pStmt1 = connection.prepareStatement(update1);
            pStmt1.setDouble(1, budget);
            pStmt1.setInt(2, oldProjektId);
            int affected1= pStmt1.executeUpdate(); //Bei Autocommit wird jede Ausführung sofort "festgeschrieben", kann nich rückgängig gemacht werden

            PreparedStatement pStmt2 = connection.prepareStatement(update2);
            pStmt2.setDouble(1, budget);
            pStmt2.setInt(2, newProjektId);

            int affected2= pStmt2.executeUpdate();

            System.out.printf("affected1 %d affected2 %d",affected1, affected2);
            //Wenn alles erfolgreich war, dann dauerhaft "festschreiben"
            if (affected1==1 && affected2==1) {
                connection.commit(); //Alle Änderungen sind in der DB, es gibt kein zurück
            } else {
                connection.rollback();
            }

        } catch (SQLException transactionFehler) {
            try {
                connection.rollback(); //alle Änderungen rückgängig machen
            } catch (SQLException rollbackFehler) {
                rollbackFehler.printStackTrace();
            }
            transactionFehler.printStackTrace();
        }
    }


    //Aufgabe 6a
    public KlausurProjekte getProjektMitDenMeistenAufgaben() {

        KlausurProjekte kp = new KlausurProjekte();
        String query = "SELECT p.ProjektID, k.Projektbezeichnung, k.Projekttyp, k.Budget, k.Laufzeit,  COUNT(p.ProjektAufgabeID) AS TaskCount " +
                "FROM Projektaufgaben p " +
                "JOIN KlausurProjekte k ON k.ProjektID = p.ProjektID " +
                "GROUP BY k.ProjektID, k.Projektbezeichnung " +
                "ORDER BY TaskCount DESC " +
                "LIMIT 1;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                kp.setProjektId(resultSet.getInt("ProjektID"));
                kp.setProjektbezeichnung(resultSet.getString("Projektbezeichnung"));
                kp.setProjekttyp(resultSet.getString("Projekttyp"));
                kp.setBudget(resultSet.getDouble("Budget"));
                kp.setLaufzeit(resultSet.getInt("Laufzeit"));
                kp.setTaskCount(resultSet.getInt("TaskCount"));

                return kp;
            } else {
                return null; // No projects found
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return kp;
    }

    //Aufgabe 6b
    public double getDurchschnittAufwandInStundenByProjektId(int projektId) {
        double avgAufwand = 0;
        try {
            String selectDurchschnitt = "SELECT AVG(AufwandInStunden) AS durchschnitt FROM ProjektAufgaben WHERE ProjektID = ?";
            PreparedStatement pStmt = connection.prepareStatement(selectDurchschnitt);
            pStmt.setInt(1, projektId);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                avgAufwand = rs.getDouble("durchschnitt");
            }
        } catch (SQLException e) {
            return 0;
        }
        return avgAufwand;
    }


}
