package MODEL;


/**
 * 
 * @author K.Misho
 * @date august '15
 * @version 1.0
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import MODEL.XMLBuilder;

public class Scrap {
	//regx expresions
	static  	Pattern pattern 				= null;
	private 	Matcher matcher 				= null;
	private 	Matcher matcher_in 				= null;
	static 		Pattern pattern_in 				= null;
	private 	Matcher matcher_inn 			= null;
	static 		Pattern pattern_inn 			= null;
	private 	Matcher matcher_im 				= null;
	static 		Pattern pattern_im 				= null;
	
	//Found match
	private 	SboBet_Match new_match 			= null;
	
	// product info
	private 	String page_product 			= "";
	private 	String price	 				= "0";
	private 	double odd 						=  0;
	private 	int quantity	 				=  0;
	
	//the associated product
	private 	Match product					= null;
	
	//the title of the product
	private 	String product_info				="";
	
	// scrapping info
	private 	String link_script 				= null;
	// the confirm button for an item from the webPage
	private 	WebElement confirm_script 		= null;
	private 	WebElement bet_element 			= null;
	private 	java.util.Vector<WebElement> bets_element = null;
	private 	String confirm_id 				= "";
	
	//local WebDriver
	private 	WebDriver driver 				= null;
	
	
	public Scrap(WebDriver driv, Match prod) throws ParseException, InterruptedException{
		driver 			= driv;
		bets_element 	= new Vector<WebElement>();
		product 		= prod;
		init();
	}
	
	public SboBet_Match getScrapedMatch(){
		return new_match;
	}
	
	
	public void init() throws ParseException, InterruptedException{
		System.err.println("------------------------");
		Thread.sleep(3000);
		try{
			analyzePage();
		}catch(StaleElementReferenceException e){
			System.err.println("Error in server !!");
			System.err.println(e);
			return;
		}
		
		analyse_bet();
		if(new_match != null)System.err.println(new_match.toString());
		// Only for the client not the server
		if(new_match != null){
			System.err.println(new_match.toString());
			WebElement el_to_scroll_to = driver.findElement(By.id(new_match.getContent()));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el_to_scroll_to);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(0, -150)"); //x value '250' can be altered

			Thread.sleep(1000);
			el_to_scroll_to.click();
			Thread.sleep(2000);
			putStake();
			Thread.sleep(2000);
			driver.switchTo().alert().accept();
		}
		 
		//if(getConfirmElement() != null) getConfirmElement().click();
		System.err.println("++++++++++++++++++++++++");
		//this.closeBrowser();
	}
	
	//analyze all the page to find the corresponding match
	public SboBet_Match analyzePage() throws StaleElementReferenceException{
		
		String 		teams 		= null;
		String 		league   	= null;
		String 		HDP 		= null;
		String 		HDP_H 		= null;
		String 		HDP_A 		= null;
		String 		HDP_UO 		= null;
		String 		HDP_U 		= null;
		String 		HDP_O 		= null;
		String 		time 		= null;
		boolean 	found 		= false;
		
		page_product = driver.getCurrentUrl().toString();
		// Get only non live bets

		WebElement odds_non_live = null;
		try{
			while(driver.findElements(By.id("odds-display-nonlive")).size()==0){}
			
			odds_non_live= driver.findElement(By.id("odds-display-nonlive"));
		}catch(NoSuchElementException e){
			return null;
		}
		
		while(driver.findElements(By.tagName("tbody")).size()==0){}
		java.util.List<WebElement> bodies = odds_non_live.findElements(By.tagName("tbody"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bodies.get(bodies.size()-1));
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			while(driver.findElements(By.id("odds-display-nonlive")).size()==0){}
			
			odds_non_live= driver.findElement(By.id("odds-display-nonlive"));
			while(driver.findElements(By.tagName("tbody")).size()==0){}
			
			bodies = odds_non_live.findElements(By.tagName("tbody"));}catch(NoSuchElementException e){
			return null;
		}
		
		System.err.println("Matches"+"\n"+bodies.size());
		int i = 0; 
		WebElement el_to_scroll_to_home = null,
				   el_to_scroll_to_away = null;
		while(driver.findElements(By.className("Blue")).size()==0){}
		java.util.List<WebElement> span_names = odds_non_live.findElements(By.className("Blue"));
		for(WebElement el : span_names){
			if(el.getText().contains(product.getHome()) || el.getText().contains(product.getAway())){
				el_to_scroll_to_home = el;
				System.err.println("Found 1 : "+el.getText());
				break;
			}
			
		}
		while(driver.findElements(By.className("Red")).size()==0){}
		java.util.List<WebElement> span_names_2 = odds_non_live.findElements(By.className("Red"));
		
		System.err.println("Home : " + product.getHome()+" Away : "+ product.getAway());
		
		for(WebElement el : span_names_2){
			if(el.getText().contains(product.getHome()) || el.getText().contains(product.getAway())){
				el_to_scroll_to_away = el;
				System.err.println("Found 2 : "+el.getText());
				break;
			}
			
		}
		
		if(el_to_scroll_to_away!=null && el_to_scroll_to_home != null){
			//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el_to_scroll_to);
			
		}
		else{
			System.err.println("!!!!!!! Not found");
			return null;
		}
		
		WebElement parentElement = null;
		if(el_to_scroll_to_home != null){ parentElement = el_to_scroll_to_home.findElement(By.xpath("./.."));
		parentElement = parentElement.findElement(By.xpath("./.."));
		parentElement = parentElement.findElement(By.xpath("./.."));
		//System.err.println(parentElement.getText());
		}
		System.err.println("Matches :"+bodies.size()+" | Match index :"+bodies.indexOf(parentElement));
		if(bodies.indexOf(parentElement)>3)bodies = bodies.subList(bodies.indexOf(parentElement)-4,bodies.size()-1);
		
		for(WebElement match : bodies){
			if(found){
				String attr = match.getAttribute("class");
				//System.out.println(attr);
				if(attr.contains("subrow")){
					bets_element.addElement(match);			
				}
				else
					break;
			}else
			if(i>1){
		
				if(i%18==0)((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", match);

				if(match.findElements(By.className("league-row")).size()==0){
					time = match.findElement(By.className("time-column-content")).getText();

					int ii=1;// important information starts from the 3rd td element
					
					//3->match ; 4->HDP 5->HDP_H 6->HDP_A 7->HDP_UO; 8-> HDP_U; 0->HDP_O
					for(WebElement odds : match.findElements(By.tagName("td"))){
						if(ii>=3){
							//System.out.println("TD "+odds.getText());	
							switch(ii){
							case 3: teams = odds.getText();
								break;
							case 4: HDP = odds.getText();
								break;
							case 5: HDP_H = odds.getText();
								break;
							case 6: HDP_A = odds.getText();
								break;
							case 7: HDP_UO = odds.getText();
								break;
							case 8: HDP_U = odds.getText();
								break;
							case 9: HDP_O = odds.getText();
								break;
								default:
									System.err.println("HDP parsing error!!");
							}
						}
						ii++;
						if(ii==10)
							break;// up to the 9th td only FT and UO no HT!!!
					}
			/*
					String attr = match.getAttribute("class");
		    
					System.out.println(attr);
		
					if(!attr.contains("subrow"))
						System.out.println("-------Match---------");
					System.out.println("TIME : "+time);
					System.out.println("-"+teams+" ; "+HDP+":"+HDP_H+":"+HDP_A+":"+HDP_UO+":"+HDP_U+":"+HDP_O+"-");
			*/
					if(teams != null && teams.contains(product.getHome())){
						bet_element = match;
						bets_element.add(match);
						new_match = new SboBet_Match(time,teams.split("\n")[0],teams.split("\n")[1],league);
						new_match.setIssue(product.getIssue());
						found = true;
					}
				}else{
					league = match.getText();
				}
			
			}
			i++;
		
		
		}
		
		return new_match;
	}
	
	public WebElement get_stake_script(){
		String _val = "stake";
		WebElement stake_script = driver.findElement(By.id(_val));
		return stake_script;
	}
	
	public void putStake() {
		get_stake_script().sendKeys("100");
		get_confirm_script().click();
	}
	
	public WebElement get_confirm_script(){
		String _val = "tk:tk:submittc";
		WebElement stake_confirm = driver.findElement(By.id(_val));
		return stake_confirm;
	}
	
	// analyze all the information for the found match
	public void analyse_bet(){
		
		String teams 	= null;
		String HDP 		= null;
		String HDP_H 	= null;
		String HDP_A 	= null;
		String HDP_UO 	= null;
		String HDP_U 	= null;
		String HDP_O 	= null;
		String time 	= null;
		
		for(WebElement match : bets_element){
			
			time = match.findElement(By.className("time-column-content")).getText();
			
			//System.out.println(team_name);
			int ii=1;// important information starts from the 3rd td element
			//3->match ; 4->HDP 5->HDP_H 6->HDP_A 7->HDP_UO; 8-> HDP_U; 0->HDP_O
			// store the element id for the corresponding issue
			for(WebElement odds : match.findElements(By.tagName("td"))){
				if(ii>=3){
					//System.out.println("TD "+odds.getText());	
					switch(ii){
					case 3: teams = odds.getText();
						break;
					case 4: HDP = odds.getText();
						break;
					case 5: HDP_H = odds.getText();
						java.util.List<WebElement> span_h = odds.findElements(By.tagName("span"));
						String content_h = null;
						for(WebElement el : span_h)
							if(el.getAttribute("id") != null)
								content_h = el.getAttribute("id");
						if(new_match.getIssue().substring(0, 1).equalsIgnoreCase("h")&&new_match.getIssue().substring(2).equalsIgnoreCase(HDP))
							new_match.addContent(content_h);
						break;
					case 6: HDP_A = odds.getText();
						java.util.List<WebElement> span_a = odds.findElements(By.tagName("span"));
						String content_a = null;
						for(WebElement el : span_a)
							if(el.getAttribute("id") != null)
								content_a = el.getAttribute("id");
						if(new_match.getIssue().substring(0, 1).equalsIgnoreCase("a")&&new_match.getIssue().substring(2).equalsIgnoreCase(HDP))
							new_match.addContent(content_a);
					
						break;
					case 7: HDP_UO = odds.getText();
						break;
					case 8: HDP_U = odds.getText();
						java.util.List<WebElement> span_u = odds.findElements(By.tagName("span"));
						String content_u = null;
						for(WebElement el : span_u)
							if(el.getAttribute("id") != null)
								content_u = el.getAttribute("id");
						
						if(new_match.getIssue().substring(0, 1).equalsIgnoreCase("u")&&new_match.getIssue().substring(2).equalsIgnoreCase(HDP_UO))
							new_match.addContent(content_u);
				
						break;
					case 9: HDP_O = odds.getText();
						java.util.List<WebElement> span_o = odds.findElements(By.tagName("span"));
						String content_o = null;
						for(WebElement el : span_o)
							if(el.getAttribute("id") != null)
								content_o = el.getAttribute("id");
						if(new_match.getIssue().substring(0, 1).equalsIgnoreCase("o")&&new_match.getIssue().substring(2).equalsIgnoreCase(HDP_UO))
							new_match.addContent(content_o);
				
						break;
						default:
							System.err.println("HDP parsing error!!");
					}
				}
				ii++;
				if(ii==10)
					break;// up to the 9th td only FT and UO no HT!!!
			}
			String attr = match.getAttribute("class");
			
			System.out.println(attr);
			if(!attr.contains("subrow"))
				System.out.println("-------Match---------");
			System.out.println("TIME : "+time);
			System.out.println("-"+teams+" ; "+HDP+":"+HDP_H+":"+HDP_A+":"+HDP_UO+":"+HDP_U+":"+HDP_O+"-");
			
			new_match.addHDP_HA(HDP, Double.parseDouble(HDP_H), Double.parseDouble(HDP_A));
			new_match.addHPD_UO(HDP_UO, Double.parseDouble(HDP_U), Double.parseDouble(HDP_O));
			
		}
	}
	// Finds the element to be clicked to validate an item
	
	public WebElement getConfirmElement(){
		if(link_script == null) return null;
		if(confirm_id.length() <2) return null;
		driver.navigate().to(link_script);	
		String submit_script = "//a[@id=\""+confirm_id+"\"]";	
		confirm_script = driver.findElement(By.xpath(submit_script));
		return confirm_script;
	}
	public WebElement getConfirmElement_(){
		if(link_script == null) return null;
		driver.navigate().to(link_script);	
		String submit_script = "//input[@id=\"add-to-cart-button\"]";	
		confirm_script = driver.findElement(By.xpath(submit_script));
		return confirm_script;
	}
	
	
	// Finds the Element that describes an item
	public String getLinkElement(){
		
		return link_script;
	}
	
	public double getOdd(){
		return odd;
	}
	
	public String getConfirmID(){
		return confirm_id;
	}

	// after parsing the webpage to find its own page
	/**
	 * Old version of analyzing the site
	 * @return
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	public String getPage() throws ParseException, InterruptedException{
		page_product = driver.getCurrentUrl().toString();
		String page_src = driver.getPageSource();
		pattern = Pattern.compile("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"Onex2\">.*?</table>");
		matcher = pattern.matcher(page_src);
		boolean found = false;
		
		System.err.println("\n-------------------------\n");
		while(matcher.find() && !found){
			String mat = matcher.group();
			//System.err.println(" - "+mat);
			pattern_in = Pattern.compile("<tr id=\".*?\">.*?</tr>");
			matcher_in = pattern_in.matcher(mat);
			System.err.println("\n++++++++++++++++++++++++++++++\n");
			while(matcher_in.find() && !found){
				String mat1 = matcher_in.group();
				//System.err.println(" + "+mat1);
				link_script = page_product;
				pattern_inn = Pattern.compile("<td.*?\">.*?</td>");
				matcher_inn = pattern_inn.matcher(mat1);
				System.err.println("\n==========================\n");
				int i= 1;
				while(matcher_inn.find()){
					String mat2 = matcher_inn.group();
				    String team = "";
					pattern_im = Pattern.compile("<span title=\"[a-zA-Z .]+?\" class=\"OddsL\">.*?</span>");
					matcher_im = pattern_im.matcher(mat2);
					while(matcher_im.find()){
						String mat3 = matcher_im.group();
						team = mat3.split(">")[1].split("<")[0];
						System.err.print(" = "+team+"  ");
						//System.err.println("("+mat3+")");
					}
					//System.out.println(product.getHome() +" " + product.getAway() + " = "+ team);
					if(team.equalsIgnoreCase(product.getHome())||team.equalsIgnoreCase(product.getAway()))
						found = true;
					else{
						found = false;
						
					}
					
					if(i==2){
						System.err.print(" = "+"Draw  ");
						i++;
					}
					pattern_im = Pattern.compile("<span class=\"OddsR\">.*?</span>");
					matcher_im = pattern_im.matcher(mat2);
					while(matcher_im.find()){
						String mat3 = matcher_im.group();
						Double odd = Double.parseDouble(mat3.split(">")[1].split("<")[0]);
						this.odd = odd;
						System.err.println(odd);
						if(product.getIssue().equalsIgnoreCase(""+1)){
							if(odd<= Double.parseDouble(product.getOdd_1()))
								System.out.println("1 Ok");
							else
								this.odd = -1;
						}else if(product.getIssue().equalsIgnoreCase(""+2)){
							if(odd<= Double.parseDouble(product.getOdd_1()))
								System.out.println("2 Ok");
							else
								this.odd = -1;
						}else if(product.getIssue().equalsIgnoreCase("x") && i==3){
							if(odd<= Double.parseDouble(product.getOdd_1()))
								System.out.println("3 Ok");
							else
								this.odd = -1;
						}
						i++;
					}
					
					
					pattern_im = Pattern.compile("id=\".*?\">");
					matcher_im = pattern_im.matcher(mat2);
					while(matcher_im.find()){
						String mat3 = matcher_im.group();
						
						if(product.getIssue().equalsIgnoreCase(""+(i-1))){
							confirm_id = mat3.split("\"")[1];
							System.out.println("k1 "+confirm_id);
						}else if(product.getIssue().equalsIgnoreCase(""+(i-3))&&((i-3)!=1)){
							
							confirm_id = mat3.split("\"")[1];
							System.out.println("k2 "+confirm_id);
							
						}
						else if(product.getIssue().equalsIgnoreCase("x") && i==4){
							
							confirm_id = mat3.split("\"")[1];
							System.out.println("k3 "+confirm_id);
						}
						//System.err.println("Script "+confirm_id);
						
					}
					if(i>2&& found == true)
						if(this.odd!=-1){							
							return page_product;
						}
						else{
							link_script = null;
							return "";
						}
					
				}
				System.err.println("\n==========================\n");
			}
			//System.err.println("\n++++++++++++++++++++++++++++++\n");
		}
		//System.out.println(page_src);
		
		System.err.println("\n-------------------------\n");
		
		return page_product;
	}
	public String getPage_() throws ParseException, InterruptedException{
		boolean first = true;
		int count = 0;					// number of occurrences to be stored	
		String page = "";				// the source of the webPage
		
		page_product = driver.getCurrentUrl().toString();
		pattern = Pattern.compile("<a href=\"h[a-zA-Z0-9:/=_?!&;%\"\\s.-]+?\" title=\".*?\".*?EUR .*?[< ]");
		
		
		driver.navigate().to(page_product);		// reload page
		page = driver.getPageSource();
		
		//System.out.println(page.length());
		matcher = pattern.matcher(page);
		double min = Double.parseDouble(product.getOdd_1());
		while (matcher.find() && count<4) {
			//if((matcher.group()).indexOf(product.getProduct())>=0){
			if((matcher.group()).indexOf(product.getHome())>=0 && (matcher.group()).indexOf("EUR")>=0){
			/*	if(first) {		
					// sets the link of this product : (from the regx the URL at the 3 index)
					link_script = matcher.group().split("\"")[1];
					first=false;
				}
			*/	
				//System.err.println(".......... - "+product.getProduct()+" - ............");
			   // System.out.println(matcher.group().split("\"")[1]);
			    int idx = (matcher.group()).indexOf("EUR");
			    String str = (matcher.group()).substring(idx, idx+8);
			    NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
			    Number number = format.parse(str.split(" ")[1]);
			    double d = number.doubleValue();
			    System.err.println("EUR " + d);
			   
			    if(d<min) {
			    	min = d;
					// sets the link of this product : (from the regx the URL at the 3 index)
					link_script = matcher.group().split("\"")[1];

					first=false;
				}

			    count++;
			}
		}
		return page_product;
	}
	
	public void writeFile(String content){
		try {
			
			File file = new File("/users/idinter/Desktop/GI04/filename.txt");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	///
	
	// after parsing the webpage
	public Match getProduct(){
		return product;
	}
	
	public String getInfo(){
		if(link_script == null) return "";
		driver.navigate().to(link_script);	

		// to be developped
		String info = "\"productTitle\"";
		
		//matcher = pattern.matcher(driver.getPageSource());
		
		String submit_script = "//span[@id=\"productTitle\"]";	
		WebElement prod_info = driver.findElement(By.xpath(submit_script));
		product_info = prod_info.getText();
		
		return product_info;
	}

	// after parsing the webpage
	public String getPrice(){
		if(link_script == null) return "";
		// to be developped
		driver.navigate().to(link_script);	
		String info = "\"priceblock_ourprice\"";
		
		//matcher = pattern.matcher(driver.getPageSource());
		
		String submit_script = "//span[@id="+info+"]";	
		WebElement price_info = driver.findElement(By.xpath(submit_script));
		price = price_info.getText();
		return price;
	}
	public void closeBrowser() {
		driver.close();
	}
}
