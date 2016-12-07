package MODEL;

public class Market {
	
	private String market = null;
	private String id = null;
	
	
	public Market(String marketM,String idM)
	{
		market = marketM;
		id=idM;
	}
	public String getId(){
		return id;
	}
	
	public String getMarket(){
		return market;
	}

	public String toString(){
		return market;
		
	}

}
