package MODEL;

/**
 * 
 * @author K.Misho
 * @date august '15
 * @version 1.0
 */

public class BetResult {
	
	private String match_id = null;
	private String result = null;
	private String issue = null;
	private String odd = null;
	private String url_link = null;
	private String content_id = null;
	
	/**
	 * 
	 * @param match
	 * @param res
	 * @param issue
	 * @param odd
	 * @param url
	 * @param id
	 */
	public BetResult(String match,String res, String issue, String odd, String url, String id){
		match_id = match;
		result = res;
		this.issue = issue;
		this.odd = odd;
		url_link = url;
		content_id = id;
	}
	
	public String getMatch(){
		return match_id;
	}
	
	public String getResult(){
		return result;
	}
	
	public String getIssue(){
		return issue;
	}
	
	public String getOdd(){
		return odd;
	}
	
	public String getUrl(){
		return url_link;
	}
	
	public String getId(){
		return content_id;
	}
	
	public String toString(){
		return match_id + " + " + issue + " + " + odd + " + " + url_link + " + " + content_id ;
	}

}
