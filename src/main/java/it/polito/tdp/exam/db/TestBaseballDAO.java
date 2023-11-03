package it.polito.tdp.exam.db;

import java.util.List;

import it.polito.tdp.exam.model.People;
import it.polito.tdp.exam.model.PeopleWeight;

public class TestBaseballDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BaseballDAO dao = new BaseballDAO();

//		List<People> players = dao.readAllPlayers();
		
		List<PeopleWeight> peopleWeight1 = dao.getPlayersWeightTeamYear("Baltimore Orioles", 2017);
		List<PeopleWeight> peopleWeight2 = dao.getPlayersWeightTeamYear("Baltimore Orioles", 1882);
//		System.out.println(peopleWeight1);
		double size1 = peopleWeight1.size();
		double size2 = peopleWeight2.size();
		System.out.println(size1);
		System.out.println(size2);
		
		int sum1 = 0;
		for(PeopleWeight pw1 : peopleWeight1) {
			sum1 += pw1.getWeight();
		}
		
		int sum2 = 0;
		for(PeopleWeight pw2 : peopleWeight2) {
			sum2 += pw2.getWeight();
		}
		
		double average1 = sum1/size1;
		double average2 = sum2/size2;
		
		System.out.println(Math.abs(average1-average2));
		
		
	}

}
