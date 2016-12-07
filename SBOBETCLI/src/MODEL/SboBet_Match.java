package MODEL;


/**
 * 
 * @author K.Misho
 * @date august '15
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SboBet_Match {
	private String id 								= null;
	private String date 							= null;
	private String hTeam 							= null;
	private String aTeam 							= null;
	private String league 							= null;
	private HashMap<String,Vector<Double>> HDP_HA 	= null;
	private HashMap<String,Vector<Double>> HDP_UO 	= null;
	private BooleanProperty confirm 				= null;
	private ObservableList<String> issues 			= null;
	private String issue 							= null;
	private String issue_content 					= null;
	
	public SboBet_Match(String dt, String home, String away,String leag){
		date 					= dt;
		hTeam 					= home;
		aTeam 					= away;
		league 					= leag;
		ArrayList<String> arr 	= new ArrayList<String>();
		issues 					=  FXCollections.observableArrayList(arr);
	
		confirm 				= new SimpleBooleanProperty(true);

		issue 					= "";
		HDP_HA 					= new HashMap<String,Vector<Double>>();
		HDP_UO 					= new HashMap<String,Vector<Double>>();
	}

	public void addIssue(String issue){
		if(issues.size()==0) this.issue = issue;
		issues.add(issue);
	}
	
	public void addContent(String content){
		issue_content = content;
		System.err.println("Content"+issue_content);
	}
	
	public String getContent(){
		return issue_content;
	}
	
	public String getDate(){
		return date;
	}
	
	public HashMap<String,Vector<Double>> getHDP_HA(){
		return HDP_HA;
	}
	
	public void addHDP_HA(String hdp_HA, Double home, Double away){
		Vector<Double> odds = new Vector<Double>();
		odds.add(home); odds.add(away);
		HDP_HA.put(hdp_HA, odds);
	}
	
	public void addHPD_UO(String hdp_UO, Double under, Double over){
		Vector<Double> odds = new Vector<Double>();
		odds.add(under); odds.add(over);
		HDP_UO.put(hdp_UO, odds);
	}
	
	//parse : H_0.50 | A_0-0.5 | U_2-2.5 ...
	public Double getOdd(){
		if(issue.substring(0, 1).equalsIgnoreCase("H"))
			for(String s : HDP_HA.keySet()){
				if(issue.substring(2).equalsIgnoreCase(s))
					return HDP_HA.get(s).get(0);
			}
			
		if(issue.substring(0, 1).equalsIgnoreCase("A"))
			for(String s : HDP_HA.keySet()){
				if(issue.substring(2).equalsIgnoreCase(s))
					return HDP_HA.get(s).get(1);
			}
		
		if(issue.substring(0, 1).equalsIgnoreCase("U"))
			for(String s : HDP_UO.keySet()){
				if(issue.substring(2).equalsIgnoreCase(s))
					return HDP_UO.get(s).get(0);
			}
			
		if(issue.substring(0, 1).equalsIgnoreCase("O"))
			for(String s : HDP_UO.keySet()){
				if(issue.substring(2).equalsIgnoreCase(s))
					return HDP_UO.get(s).get(1);
			}
		
		return -1.0;
	}
	
	public HashMap<String,Vector<Double>> getHDP_UO(){
		return HDP_UO;
	}
	
	public String getMatch(){
		return hTeam+" - " + aTeam;
	}
	
	public String getLigue(){
		return league;
	}
	
	public String getHome(){
		return hTeam;
	}
	
	public String getAway(){
		return aTeam;
	}
	
	public BooleanProperty confirmProperty(){
		return confirm;
	}
	public String getIssue(){
		return issue;
	}
	
	public String getChoice(){
		return issue;
	}
	
	public void setIssue(String iss){
		
		issue = iss;
	}
	
	public ObservableList<String> getIssues(){
		return issues;
	}
	
	
	public String toString(){
		String odds = "";
		odds+="HPD_HA\n";
		for(String hdp : HDP_HA.keySet()){
			odds+="\t"+hdp+"\t"+HDP_HA.get(hdp)+"\n";
		}
		odds+="HPD_UO\n";
		for(String hdp : HDP_UO.keySet()){
			odds+="\t"+hdp+"\t"+HDP_UO.get(hdp)+"\n";
		}
		return date + " |  " + hTeam + " - " + aTeam + " - " +league+"\n"+odds;
	}
}
