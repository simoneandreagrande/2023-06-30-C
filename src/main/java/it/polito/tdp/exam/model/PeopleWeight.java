package it.polito.tdp.exam.model;

import java.util.Objects;

public class PeopleWeight {
	
	private People player;
	private int weight;
	
	
	public People getPlayer() {
		return player;
	}
	public void setPlayer(People player) {
		this.player = player;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	@Override
	public int hashCode() {
		return Objects.hash(player, weight);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PeopleWeight other = (PeopleWeight) obj;
		return Objects.equals(player, other.player) && weight == other.weight;
	}
	@Override
	public String toString() {
		return "PeopleWeight [player=" + player + ", weight=" + weight + "]";
	}
	public PeopleWeight(People player, int weight) {
		this.player = player;
		this.weight = weight;
	}
	
	
	
}
