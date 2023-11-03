package it.polito.tdp.exam.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.exam.model.People;
import it.polito.tdp.exam.model.PeopleWeight;
import it.polito.tdp.exam.model.Team;

public class BaseballDAO {

	public List<People> readAllPlayers() {
		String sql = "SELECT * " + "FROM people";
		List<People> result = new ArrayList<People>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new People(rs.getString("playerID"), rs.getString("birthCountry"), rs.getString("birthCity"),
						rs.getString("deathCountry"), rs.getString("deathCity"), rs.getString("nameFirst"),
						rs.getString("nameLast"), rs.getInt("weight"), rs.getInt("height"), rs.getString("bats"),
						rs.getString("throws"), getBirthDate(rs), getDebutDate(rs), getFinalGameDate(rs),
						getDeathDate(rs)));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Team> readAllTeams() {
		String sql = "SELECT * " + "FROM  teams";
		List<Team> result = new ArrayList<Team>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Team(rs.getInt("iD"), rs.getInt("year"), rs.getString("teamCode"), rs.getString("divID"),
						rs.getInt("div_ID"), rs.getInt("teamRank"), rs.getInt("games"), rs.getInt("gamesHome"),
						rs.getInt("wins"), rs.getInt("losses"), rs.getString("divisionWinnner"),
						rs.getString("leagueWinner"), rs.getString("worldSeriesWinnner"), rs.getInt("runs"),
						rs.getInt("hits"), rs.getInt("homeruns"), rs.getInt("stolenBases"), rs.getInt("hitsAllowed"),
						rs.getInt("homerunsAllowed"), rs.getString("name"), rs.getString("park")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	// =================================================================
	// ==================== HELPER FUNCTIONS =========================
	// =================================================================

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getBirthDate(ResultSet rs) {
		try {
			if (rs.getDate("birth_date") != null) {
				return rs.getDate("birth_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getDebutDate(ResultSet rs) {
		try {
			if (rs.getDate("debut_date") != null) {
				return rs.getDate("debut_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getFinalGameDate(ResultSet rs) {
		try {
			if (rs.getDate("finalgame_date") != null) {
				return rs.getDate("finalgame_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getDeathDate(ResultSet rs) {
		try {
			if (rs.getDate("death_date") != null) {
				return rs.getDate("death_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getTeamsNames() {
		String sql = "SELECT DISTINCT t.name as name " +
					"FROM  teams t "+
					"ORDER BY name ASC";
		List<String> result = new ArrayList<String>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new String(rs.getString("name")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Integer> getVertici(String name){
		String sql = "SELECT DISTINCT t.year "
				+ "FROM teams t "
				+ "WHERE t.name = ?";
		
//		SELECT s.playerID, SUM(s.salary) as salaryTot
//		FROM salaries s
//		WHERE s.year = 2010
//		GROUP BY s.playerID
//		HAVING salaryTot>5000000
		
		List<Integer> result = new ArrayList<Integer>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, name);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(rs.getInt("year"));
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	
	public List<PeopleWeight> getPlayersWeightTeamYear(String name, int anno){
//		String sql = "SELECT DISTINCT people.* "
//				+ "FROM people, teams, appearances "
//				+ "WHERE appearances.playerID = people.playerID "
//				+ "AND teams.teamCode = appearances.teamCode "
//				+ "AND teams.year = ? "
//				+ "AND teams.name = ? AND people.weight IS NOT NULL";
		
		String sql = "SELECT DISTINCT people.* "
				+ "FROM people "
				+ "JOIN appearances ON people.playerID = appearances.playerID "
				+ "JOIN teams ON appearances.teamCode = teams.teamCode "
				+ "WHERE teams.year = ? AND teams.name = ? "
				+ "AND appearances.year = teams.year";


		List<PeopleWeight> result = new ArrayList<PeopleWeight>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setString(2, name);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				People player = new People(rs.getString("playerID"), rs.getString("birthCountry"), rs.getString("birthCity"),
						rs.getString("deathCountry"), rs.getString("deathCity"), rs.getString("nameFirst"),
						rs.getString("nameLast"), rs.getInt("weight"), rs.getInt("height"), rs.getString("bats"),
						rs.getString("throws"), getBirthDate(rs), getDebutDate(rs), getFinalGameDate(rs),
						getDeathDate(rs));

				result.add(new PeopleWeight(player, rs.getInt("weight")));
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	

}
