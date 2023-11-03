package it.polito.tdp.exam.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestModel {

    public static void main(String[] args) {
        // Creazione di una lista di giocatori di test con pesi noti
    	List<People> giocatori = new ArrayList<>();
        giocatori.add(new People("player1", "Italy", "Milan", "Italy", "Rome", "John", "Doe", 40, 180, "Right", "Right", LocalDate.of(1990, 1, 1), LocalDate.of(2010, 1, 1), LocalDate.of(2020, 1, 1), LocalDate.of(2099, 1, 1)));
        giocatori.add(new People("player2", "USA", "New York", "USA", "Los Angeles", "Jane", "Smith", 40, 170, "Left", "Right", LocalDate.of(1995, 2, 2), LocalDate.of(2012, 2, 2), LocalDate.of(2022, 2, 2), LocalDate.of(2099, 2, 2)));
        giocatori.add(new People("player3", "Spain", "Madrid", "Spain", "Barcelona", "David", "Garcia", 40, 190, "Right", "Right", LocalDate.of(1985, 3, 3), LocalDate.of(2008, 3, 3), LocalDate.of(2018, 3, 3), LocalDate.of(2099, 3, 3)));
        
        // Creazione di un'istanza del modello
        Model model = new Model();
        
        // Conversione degli oggetti People in PeopleWeight
        List<PeopleWeight> giocatoriWeight = new ArrayList<>();
        for (People giocatore : giocatori) {
            giocatoriWeight.add(new PeopleWeight(giocatore, giocatore.getWeight()));
        }
        
        // Chiamata al metodo getAverageWeight con la lista di giocatori di test
        double media = model.getAverageWeight(giocatoriWeight);
        
        // Verifica se il risultato ottenuto corrisponde al risultato atteso
        assertEquals(40.0, media, 0.01);
        System.out.println(media);
        
    }
    
    
    private static void assertEquals(double expected, double actual, double tolerance) {
        if (Math.abs(expected - actual) > tolerance) {
            throw new AssertionError("Expected: " + expected + ", but got: " + actual);
        }
    }
    
}
