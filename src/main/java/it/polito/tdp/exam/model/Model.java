package it.polito.tdp.exam.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.exam.db.BaseballDAO;

public class Model {
	
	private BaseballDAO dao;
	private Graph<Integer, DefaultWeightedEdge> grafo ;
	private Map<Integer, List<PeopleWeight>> annoToPlayers;

	
	public Model() {
		this.dao = new BaseballDAO();
		this.annoToPlayers = new HashMap<Integer, List<PeopleWeight>>();
		
	}
	
	public void creaGrafo(String name) {
		BaseballDAO dao = new BaseballDAO() ;
		
	    // Crea grafo non orientato e pesato
	    this.grafo = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);

	    // Al grafo aggiungi i vertici
	    List<Integer> vertici = this.dao.getVertici(name);
	    Graphs.addAllVertices(this.grafo, vertici);
	    
	    
	  //Leggere i giocatori per ogni anno e per ogni squadra
        this.annoToPlayers.clear();
        for (int anno : vertici) {
            this.annoToPlayers.put(anno, this.dao.getPlayersWeightTeamYear(name, anno));
        }

	    // Verifica ogni coppia di anni e crea un arco con il peso corrispondente
	    for (int i = 0; i <vertici.size(); i++) {
			for (int j = i+1; j < vertici.size(); j++) {
				List<PeopleWeight> giocatori1 = new ArrayList<PeopleWeight>(this.annoToPlayers.get(vertici.get(i)));
				List<PeopleWeight> giocatori2 = new ArrayList<PeopleWeight>(this.annoToPlayers.get(vertici.get(j)));
				double peso = Math.abs(getAverageWeight(giocatori1) - getAverageWeight(giocatori2));
					Graphs.addEdgeWithVertices(this.grafo, vertici.get(i), vertici.get(j), peso);
				}
			}
		
	}

     
	
	public double getAverageWeight(List<PeopleWeight> giocatori) {
	    double sum = 0.0;
	    for (PeopleWeight pw : giocatori) {
	        sum += pw.getWeight();
	    }
	    double media = sum/giocatori.size();
	    return media;
	}

	public Set<Integer> getVertici() {
		return this.grafo.vertexSet();
	}


	public Set<DefaultWeightedEdge> getArchi() {
		return this.grafo.edgeSet();
	}
	

	public List<String> getTeamsNames() {
		return this.dao.getTeamsNames();
	}

	public List<Dettaglio> getDettagli(int anno) {
		List<Dettaglio> result = new ArrayList<Dettaglio>();
		List<Integer> adiacenti = Graphs.neighborListOf(this.grafo, anno);
		
		// aggiungo a dettaglio anno e peso associati
	    for(Integer nodo : adiacenti) {
	        DefaultWeightedEdge arco = this.grafo.getEdge(anno, nodo);
	        double peso = this.grafo.getEdgeWeight(arco);
	        result.add(new Dettaglio(nodo, peso)); // Aggiunge il peso come intero a Dettaglio
	    }
	    
	    // Ordino la lista in ordine decrescente utilizzando un comparatore personalizzato
	    Collections.sort(result);
	    
	    return result;
	}
	
}
