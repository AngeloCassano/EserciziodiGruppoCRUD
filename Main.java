package com.example;

import java.sql.*;
import java.util.Scanner;

public class Main {
    //variabile statica per l'url
    static String URL = "jdbc:mysql://localhost:3306/";
    //variabile statica per l'user
    static String USER = null;
    //variabile statica per la password
    static String PASSWORD = null;
    public static void main(String[] args) {
        //dichiarazione di scanner per le stringhe
        Scanner myScannerStr = new Scanner(System.in);
        //dichiarazione di scanner per interi
        Scanner myScannerInt = new Scanner(System.in);
        //creo la tabella
        creoTabella();
        //richiamo la funzione menù
        menù(myScannerStr, myScannerInt);
        //chiusura degli scanner
        myScannerInt.close();
        myScannerStr.close();
    }

    //Funzione Create con passaggio di parametri nome e email
    public static void inserimentoUtente(String nome, String email) {
        //creo la query e la salvo nella variabile sql
        String sql = "INSERT INTO utenti (nome, email) VALUE (?, ?)";
        //try-catch per gestione delle possibili eccezioni, creo nelle parentesi del try la connesione e il preparedStatement
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql)){
            //valore 1 relativo al primo ?
            stmt.setString(1, nome);
            //valore 2 relativo al secondo ?
            stmt.setString(2, email);
            //invio il comando di update
            stmt.executeUpdate();
            //stampo utente creato con successo
            System.out.println("Utente cretao con successo!");
        //altrimenti gestisco il sql exception
        } catch (SQLException e) {
            //stampando l'errore
            e.printStackTrace();
        }
    }

    //Funzione Update con passaggio di parametri nome, email e id
    public static void modificaUtente(String nuovoNome, String nuovaEmail, int id) {
        //creo la query e la salvo nella variabile sql
        String sql = "UPDATE utenti SET nome = ? SET email = ? WHERE id = ?";
        //try-catch per gestione delle possibili eccezioni, creo nelle parentesi del try la connesione e il preparedStatement
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql)){
            //valore 1 relativo al primo ?
            stmt.setString(1, nuovoNome);
            //valore 2 relativo al secondo ?
            stmt.setString(2, nuovaEmail);
            //valore 3 relativo al terzo ?
            stmt.setInt(3, id);
            //invio il comando di update
            stmt.executeUpdate();
            //stampo utente modificato con successo
            System.out.println("Utente modificato con successo!");
        //altrimenti gestisco il sql exception
        } catch (SQLException e) {
            //stampando l'errore
            e.printStackTrace();
        }
    }

    //Funzione Delete
    public static void eliminaUtente(int id) {
        //creo la query e la salvo nella variabile sql
        String sql = "DELETE FROM utenti WHERE id = ?";
        //try-catch per gestione delle possibili eccezioni, creo nelle parentesi del try la connesione e il preparedStatement
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql)){
            //valore 1 relativo al primo ?
            stmt.setInt(1, id);
            //invio il comando di update
            stmt.executeUpdate();
            //stampo utente eliminato con successo
            System.out.println("Utente eliminato con successo!");
        //altrimenti gestisco il sql exception
        } catch (SQLException e) {
            //stampando l'errore
            e.printStackTrace();
        }
    }

    //funzione READ
    public static void letturaUtenti() {
        //creo la query e la salvo nella variabile sql
        String sql = "SELECT * FROM utenti";
        //try-catch per gestione delle possibili eccezioni, creo nelle parentesi del try la connesione e lo Statement
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sql)) {
            //con il ciclo while verifico la presenza di un elemento successivo ciclo e stampo tutti gli elementi
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nome: " + rs.getString("nome") + ", Email: " + rs.getString("email"));
            }
        //altrimenti gestisco il sql exception
        } catch (SQLException e) {
            //stampando l'errore
            e.printStackTrace();
        }
    }

    //funzione READ
    public static void creoTabella() {
        //creo la query e la salvo nella variabile sql
        String sql = "CREATE TABLE utenti (id INT PRIMARY KEY AUTO_INCREMENT, nome VARCHAR(100), email VARCHAR(100))";
        //try-catch per gestione delle possibili eccezioni, creo nelle parentesi del try la connesione e lo Statement
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabella 'utenti' creata con successo!");
        //altrimenti gestisco il sql exception
        } catch (SQLException e) {
            //stampando l'errore
            e.printStackTrace();
        }
    }

    //funzione di connessione al DB
    public static void connessione(Scanner myScannerStr) {
        //inserisco nome db
        System.out.println("Inserisci nome Data Base:");
        String nomeDB = myScannerStr.nextLine();
        URL = URL.concat(nomeDB);
        //inserisco user
        System.out.println("Inserisci User:");
        USER = myScannerStr.nextLine();
        //inserisco password
        System.out.println("Inserisci Password:");
        PASSWORD = myScannerStr.nextLine();
    }

    public static void menù(Scanner myScannerStr, Scanner myScannerInt) {
        //dichiaro la variabile
        int scelta;
        //do-while per gestire il menù
        do {
            //menù
            System.out.println("===MENù===");
            System.out.println("1 - Connettiti al Data Base");
            System.out.println("2 - Esci");
            //
            scelta = myScannerInt.nextInt();
            //switch per gestire la scelta
            switch (scelta) {
                case 1:
                    //connessione
                    connessione(myScannerStr);
                    //dichiaro una variabile scelta
                    int sceltaMenù;
                    //do-while per gestire il menù
                    do {
                        //menù
                        System.out.println("===MENù===");
                        System.out.println("1 - Inserisci utente");
                        System.out.println("2 - Aggiorna utente");
                        System.out.println("3 - Cancella utente");
                        System.out.println("4 - Visualizza tutti gli utenti");
                        System.out.println("5 - Esci");
                        //valorizzo la scelta
                        sceltaMenù = myScannerInt.nextInt();
                        //switch per la gestione dei casi scelti
                        switch (sceltaMenù) {
                            //inserisco utente
                            case 1:
                                System.out.println("Inserisci Nome utente:");
                                String nome = myScannerStr.nextLine();
                                System.out.println("Inserisci Email utente:");
                                String email = myScannerStr.nextLine();
                                inserimentoUtente(nome, email);
                                break;
                            //aggiorno utebnte
                            case 2:
                                System.out.println("Inserisci ID utente da modificare:");
                                int id = myScannerInt.nextInt();
                                System.out.println("Inserisci Nome utente:");
                                String nuovoNome = myScannerStr.nextLine();
                                System.out.println("Inserisci Email utente:");
                                String nuovaEmail = myScannerStr.nextLine();
                                modificaUtente(nuovoNome, nuovaEmail, id);
                                break;
                            //elimino utente
                            case 3:
                                System.out.println("Inserisci ID utente da eliminare:");
                                int deleteID = myScannerInt.nextInt();
                                eliminaUtente(deleteID);
                                break;
                            //stampo tutti gli utenti
                            case 4:
                                letturaUtenti();
                                break;
                            //gestione del caso di default
                            default:
                                if (scelta == 5) {
                                    System.out.println("Programma terminato!");
                                } else {
                                    System.out.println("Errore: Scelta non valida.. selezione un opzione valida...");
                                }
                                break;
                        }
                        
                    } while (sceltaMenù != 5); //controllo la condizione
                    break;
                //gestione del caso di default del primo switch
                default: 
                    if (scelta == 2) {
                        System.out.println("Programma terminato!");
                    } else {
                        System.out.println("Errore: Scelta non valida.. selezione un opzione valida...");
                    }
                    break;
            }
        } while (scelta != 2); //gestione della condizione
    }
}