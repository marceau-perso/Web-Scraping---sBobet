package MODEL;

/**
 * 
 * @author K.Misho
 * @date august '15
 * @version 1.0
 */

public class Product {
	private String t_home;
	private String t_away;
	private String m_champ;
	private String url;
	private String quantity = "1";

	// the reference price as criteria
	private double  m_odd;
	String m_issue ="1";

	public Product(String home,String away, String champ,String issue, double odd,
			String quantity)  {
		t_home = home;
		t_away = away;
		this.m_champ = champ;
		this. m_odd = odd;
		this.m_issue = issue;
		this.quantity = quantity;
	}

	public Product(String home, String champ, String issue,double odd,
			String quantity) {
		t_home = home;
		t_away = "";
		this.m_champ = champ;
		this. m_odd = odd;
		this.m_issue = issue;
		this.quantity = quantity;
	}

	public String getHome() {
		return t_home;
	}

	public String getAway() {
		return t_away;
	}
	
	public String getChamp() {
		return m_champ;
	}

	public String getUrl() {
		return url;
	}

	public int getQuantityInt() {
		return Integer.parseInt(quantity);
	}

	public void setQuantityInt(int val) {
		this.quantity = ""+val;
	}
	
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String val) {
		this.quantity = val;
	}


	public Double getOdd() {
		return  m_odd;
	}
	
	public String getIssue() {
		return  m_issue;
	}

	public void setOdd(double price_criteria) {
		this. m_odd = price_criteria;
	}

}
