package MODEL;

import java.util.Vector;

public class Bet {
	private Match match = null;
	private String market = null;
	private String content = null;
	private String odds = null;
	//For some bets we need more information
	private String detail = "";

	public Bet(String marketM,String oddsH,String name1)
	{
		market = marketM;
		odds = oddsH;
		content = name1;
		
	}
	
	public Bet(Match matchID, String marketM,String oddsH,String name1)
	{
		match = matchID;
		market = marketM;
		odds = oddsH;
		content = name1;
		
	}
	public Bet(Match matchID, String marketM,String oddsH,String name1,String detail1)
	{
		match = matchID;
		market = marketM;
		odds = oddsH;
		content = name1;
		detail = detail1;
		
	}

	
	public Match getMatch(){
		return match;
	}
	public String getMarket(){
		return market;
	}
	public String getOdds(){
		return odds;
	}
	public String getContent(){
		return content;
	}
	public String getDetail(){
		return detail;
	}

	public String toString(){
		if(match!=null){
			return "Match "+match.toString()+" ; odds "+odds + "; name : "+content+ "; detail : "+detail;

		}else{
			return "odds "+odds + "; name : "+content+ "; detail : "+detail;

		}
		
	}

}
