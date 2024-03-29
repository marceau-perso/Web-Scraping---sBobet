package MODEL;

import java.util.Vector;

public class MatchManager {
	
	private Vector<Match> matches = null;
	public MatchManager(){
		matches = new Vector<Match>();
	}
	public void addMatch(String idMatch,String dt, String home, String away,String lig){
		matches.add(new Match(idMatch,dt, home, away,lig));
	}
	public void addMatch(String idMatch,String dt, String home, String away,String lig, String oddsH,String oddsA, String market,String draw, String oddsD){
		matches.add(new Match(idMatch,dt, home, away,lig,oddsH,oddsA,market,draw,oddsD));
	}
	
	public Vector<Match> getMatches(){
		return matches;
	}
	
	public String printMatches(){
		String retour="";
		int i= 0;
		for (Match m : matches)
		{
			retour+=("Match "+i+" : "+m.toString()+"\n");
			i++;
		}
		return retour;
	}
	public void removeMatches(){
		matches.clear();
	}

}
