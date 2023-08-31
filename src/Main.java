import at.dbp.klausur.DBPKlausurHelper;
import at.dbp.klausur.models.KlausurProjektaufgaben;
import at.dbp.klausur.models.KlausurProjekte;

import java.util.ArrayList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        System.out.printf("Hello JDBC");

        String url = "jdbc:sqlite:C:\\LVs\\DBP2023\\DBPKlausur2023Grabner.db";

        DBPKlausurHelper helper=new DBPKlausurHelper(url);

        /*
        helper.createTableKlausurProjekte();
        helper.createAdditionalTableAndFillWithSampleValues();
        helper.createTableKlausurProjektaufgaben();

        KlausurProjekte projekt =new KlausurProjekte();
        projekt.setBudget(900);
        projekt.setProjektbezeichnung("def");
        projekt.setLaufzeit(7);
        projekt.setProjekttyp("Software");
        helper.insertProjekt(projekt);

        System.out.printf("Projekt wurde mit der ID %d erzeugt",projekt.getProjektId());



        KlausurProjekte pSuche1 = helper.getProjektById(1);
        System.out.println("\nProjekt gefunden " + pSuche1);

        ArrayList<KlausurProjekte> listeProjekte =new ArrayList<KlausurProjekte>();

        listeProjekte = helper.getAlleProjekteSortedByProjekttypFilteredByMaxLaufzeit(50);
        System.out.println("\nListe Projekte gefunden " + listeProjekte);


        KlausurProjektaufgaben aufgabe=new KlausurProjektaufgaben();
        aufgabe.setAufgabenbezeichnung("Test");
        aufgabe.setAssignedEmployeeID(1);
        aufgabe.setProjektID(1);

        helper.insertProjektaufgabe(aufgabe);

        System.out.printf("\n die Aufgabe mit der ID %d wurde angelegt", aufgabe.getProjektAufgabeID());


        System.out.printf("Mit meisten Tasks" + helper.getProjektMitDenMeistenAufgaben());


        System.out.printf("Durchschnitt " + helper.getDurchschnittAufwandInStundenByProjektId(1));

        */


        helper.transferBudget(2,1,5000);

    }
}