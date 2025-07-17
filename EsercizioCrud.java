package com.example;

import java.sql.*; // import delle classi necessarie per la connessione al DB 
import java.util.Scanner; // import della classe Scanner
        
    public class EsercizioCrud {
        static String URL = "jdbc:mysql://localhost:3306/";
        static String USER = null;
        static String PASSWORD = null;

    public static void main(String[] args) {

        try(Scanner scannerStr = new Scanner(System.in); Scanner scannerInt = new Scanner(System.in) ){
            menù(scannerStr, scannerInt);
        }
        
        

    }

    //metodo per prendere in input i dati per connettersi ad un DB
    public static void insertConnectionData() {
        Scanner scannerStr = new Scanner(System.in); // creo uno scanner per le stringhe in input
        System.out.println("Inserisci il nome del db"); // chiedo il nome del db
        String dbname = scannerStr.nextLine(); //prendo in inpur il nome del db dallo scanner
        URL = URL.concat(dbname); // concateno il nome del db alla prima parte fissa dell'attributo statico URL
        System.out.println("Inserisci il nome utente"); //chiedo di inserire il nome utente
        USER = scannerStr.nextLine(); //inserisco nell'attributo statico USER il valore in input 
        System.out.println("Inserisci la password"); //chiedo di inserire la password
        PASSWORD = scannerStr.nextLine();  //inserisco nell'attributo statico PASSWORD il valore in input 
        

    }



    // CREATE
    public static void insertUtente(String nome, String email) {
        String sql = "INSERT INTO utenti (nome, email) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.executeUpdate();
            System.out.println("Utente inserito.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ
    public static void readUtenti() {
        String sql = "SELECT * FROM utenti";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                                   ", Nome: " + rs.getString("nome") +
                                   ", Email: " + rs.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public static void updateUtente(int id, String nuovoNome) {
        String sql = "UPDATE utenti SET nome = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuovoNome);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Utente aggiornato.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public static void deleteUtente(int id) {
        String sql = "DELETE FROM utenti WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Utente eliminato.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void menù(Scanner myScannerStr, Scanner myScannerInt) {
        int scelta;
        do {
            System.out.println("===MENù===");
            System.out.println("1 - Connettiti al Data Base");
            System.out.println("2 - Esci");

            scelta = myScannerInt.nextInt();

            switch (scelta) {
                case 1:                
                    insertConnectionData();
                    int sceltaMenù;
                    do {
                        System.out.println("===MENù===");
                        System.out.println("1 - Inserisci utente");
                        System.out.println("2 - Aggiorna utente");
                        System.out.println("3 - Cancella utente");
                        System.out.println("4 - Visualizza tutti gli utenti");
                        System.out.println("5 - Esci");

                        sceltaMenù = myScannerInt.nextInt();

                        switch (sceltaMenù) {
                            case 1:

                                System.out.println("Inserisci Nome utente:");
                                String nome = myScannerStr.nextLine();
                                System.out.println("Inserisci Email utente:");
                                String email = myScannerStr.nextLine();
                                insertUtente(nome, email);
                                break;
                            case 2:
                                System.out.println("Inserisci ID utente da modificare:");
                                int id = myScannerInt.nextInt();
                                System.out.println("Inserisci Nome utente:");
                                String nuovoNome = myScannerStr.nextLine();
                                updateUtente(id, nuovoNome);
                                break;
                            case 3:
                            System.out.println("Inserisci ID utente da eliminare:");
                                int deleteID = myScannerInt.nextInt();

                                deleteUtente(deleteID);
                                break;
                            case 4:
                                readUtenti();
                                break;
                            default:
                                if (scelta == 5) {
                                    System.out.println("Programma terminato!");
                                } else {
                                    System.out.println("Errore: Scelta non valida.. selezione un opzione valida...");
                                }
                                break;
                        }

                    } while (sceltaMenù != 5);

                    break;

                default: 
                    if (scelta == 2) {
                        System.out.println("Programma terminato!");
                    } else {
                        System.out.println("Errore: Scelta non valida.. selezione un opzione valida...");
                    }
                    break;
            }
        } while (scelta != 2);
    }

}