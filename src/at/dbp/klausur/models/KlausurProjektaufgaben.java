package at.dbp.klausur.models;

public class KlausurProjektaufgaben {

    //Variables:
    private int ProjektID;
    private String Aufgabenbezeichnung;
    private int AufwandInStunden;
    private int ProjektAufgabeID;

    private int assignedEmployeeID;


    //Getters and Setters:
    public int getProjektAufgabeID() {
        return ProjektAufgabeID;
    }

    public void setProjektAufgabeID(int projektAufgabeID) {
        ProjektAufgabeID = projektAufgabeID;
    }

    public int getAssignedEmployeeID() {
        return assignedEmployeeID;
    }

    public void setAssignedEmployeeID(int assignedEmployeeID) {
        this.assignedEmployeeID = assignedEmployeeID;
    }

    public int getProjektID() {
        return ProjektID;
    }

    public void setProjektID(int projektID) {
        ProjektID = projektID;
    }

    public String getAufgabenbezeichnung() {
        return Aufgabenbezeichnung;
    }

    public void setAufgabenbezeichnung(String aufgabenbezeichnung) {
        Aufgabenbezeichnung = aufgabenbezeichnung;
    }

    public int getAufwandInStunden() {
        return AufwandInStunden;
    }

    public void setAufwandInStunden(int aufwandInStunden) {
        AufwandInStunden = aufwandInStunden;
    }


    //toString:
    @Override
    public String toString() {
        return "KlausurProjektaufgaben{" +
                "ProjektID=" + ProjektID +
                ", Aufgabenbezeichnung='" + Aufgabenbezeichnung + '\'' +
                ", AufwandInStunden=" + AufwandInStunden +
                ", ProjektAufgabeID=" + ProjektAufgabeID +
                ", assignedEmployeeID=" + assignedEmployeeID +
                '}';
    }
}