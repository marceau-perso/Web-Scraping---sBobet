package MODEL;

import java.util.Vector;

public class BetManager {
	private Vector<Bet> bets = null;
	
	public BetManager(){
		bets = new Vector<Bet>();
	}
	public void addBet(String marketM,String odds,String name ){
		bets.add(new Bet(marketM,odds,name));
	}
	public void addBet(Match matchID, String marketM,String odds,String name ){
		bets.add(new Bet(matchID,marketM,odds,name));
	}
	public void addBet(Match matchID, String marketM,String odds,String name,String detail){
		bets.add(new Bet(matchID,marketM,odds,name,detail));
	}

	public Vector<Bet> getBets(){
		return bets;
	}
	public String printBets(){
		String retour="";
		int i= 0;
		for (Bet b : bets)
		{
			retour+=("Bet "+i+" : "+b.toString()+"\n");
			i++;
		}
		return retour;
	}
	
	public void removeBets(){
		bets.clear();
	}

}
