package MODEL;

/**
 * 
 * @author M.Leriche
 * @date december '12
 * @version 1.0
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import MODEL.League;
import MODEL.LeagueManager;
import MODEL.Match;
import MODEL.MatchManager;
import MODEL.Market;
import MODEL.MarketManager;
import MODEL.Bet;
import MODEL.BetManager;


public class Robot {
		
	private Spider webScrapper = null;
	public Vector<WebDriver> browsers = null;
	private String site = "https://www.sbobet.com/euro/football";
	private  LeagueManager leagueManager = new LeagueManager();
	private  MatchManager matchManager = new MatchManager();
	private BetManager betManager = new BetManager();
	private MarketManager marketManager = new MarketManager();

	
	public Robot(){
		browsers = new Vector<WebDriver>();
	}
	

	
	public Vector<League> searchLeagues() throws InterruptedException, FileNotFoundException, UnsupportedEncodingException
	{
		try {
			webScrapper = new Spider("",this);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//open the site
		Thread.sleep(2000);
		webScrapper.loadSite();
		
		List<WebElement> WEleagues = webScrapper.getDriver().findElements(By.xpath("//*[@id='ms-all-tos-1-3']/ul/li[*]"));
		
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> links = new ArrayList<String>();
		int i = 0;
		for(WebElement we : WEleagues) {
			List<WebElement> childs = we.findElements(By.xpath(".//a"));
			names.add(we.getText());
			for(WebElement we2 : childs)
			{
				links.add(we2.getAttribute("href"));
			}
			if(names.get(i).contains("Outright")){
            }
			else{
				leagueManager.addLeague(names.get(i), links.get(i));
			}
			i++;
		}
		Vector<League> listLeagues = leagueManager.getLeagues();
		return listLeagues;
	}

	public Vector<Match> searchMatches(League league) throws InterruptedException {
		// We remove all the matches from the database
		matchManager.removeMatches();
		
		// ensuite on charge les données pour ce championnat
		String[] matches;
		WebElement WEmatches1X2Test = webScrapper.getDriver().findElement(By.xpath("//*[@class='NonLiveMarket']"));
		matches = WEmatches1X2Test.getText().split(System.getProperty("line.separator"));
		int i = 0;
		int j = 0;
		ArrayList<String> away = new ArrayList<String>();
		ArrayList<String> home = new ArrayList<String>();
		ArrayList<String> oddsA = new ArrayList<String>();
		ArrayList<String> draw = new ArrayList<String>();
		ArrayList<String> oddsD = new ArrayList<String>();
		ArrayList<String> oddsH = new ArrayList<String>();
		ArrayList<String> date = new ArrayList<String>();
		ArrayList<String> market = new ArrayList<String>();
		
		String marketString=matches[0];

		while((i+j)<matches.length)
		{
			if(matches[i+j].equals("1X2"))
			{
				marketString=matches[i+j];
				j++;
				continue;
			}
			
			if(matches[i+j].equals("First Half 1X2"))
			{
				marketString=matches[i+j];
				j++;
				continue;
			}

			switch(i%9){
				case 0: date.add("date :"+matches[i+j]) ;
				market.add(marketString);
					break;
				case 1: break;
				case 2: oddsH.add(matches[i+j]);
					break;
				case 3: home.add(matches[i+j]);
					break;
				case 4: oddsD.add(matches[i+j]);
					break;
				case 5: draw.add(matches[i+j]);
					break;
				case 6: oddsA.add(matches[i+j]);
					break;
				case 7 : away.add(matches[i+j]);
					break;
				case 8 : break;
			}
			i++;
		}

		i=0;
		while(i<(home.size()))
		{
			if(home.get(i)!=null && market.get(i).equals("1X2"))
			{
				matchManager.addMatch(home.get(i) +"-"+ away.get(i), date.get(i), home.get(i), away.get(i), league.toString(), oddsH.get(i), oddsA.get(i),market.get(i),draw.get(i),oddsD.get(i));
			}
			i++;
		}
		Vector<Match> listMatches = matchManager.getMatches();
		return listMatches;
	}

	
	public Vector<Bet> searchBets2(Vector<Match> listMatches,League league) throws InterruptedException{
		
		
		
		List<WebElement> WEBet;
		List<WebElement> odds;
		List<WebElement> content;
		List<WebElement> detail;
		int index =0;
		for(Match currentMatch : listMatches){
			List<WebElement> WEbets0 = webScrapper.getDriver().findElements(By.xpath("//*[@class='IconMarkets']"));
			if(WEbets0.size()>index){
				if(WEbets0.get(index)==null){
					System.out.println("probleme d index");
					break;
				}
				WEbets0.get(index).click();
			}
			else{
				System.out.println("probleme de size");
				break;
			}
			
			WEBet = webScrapper.getDriver().findElements(By.xpath("//div[@id='od-ma-2-5']/div[@class='MarketBd']/table/tbody/tr/td[*]"));
			odds = webScrapper.getDriver().findElements(By.xpath("//div[@id='od-ma-2-5']/div[@class='MarketBd']/table/tbody/tr/td[*]/a/span[@class='OddsR']"));
			content = webScrapper.getDriver().findElements(By.xpath("//div[@id='od-ma-2-5']/div[@class='MarketBd']/table/tbody/tr/td[*]/a/span[@class='OddsL']"));
			int i = 0;
			while (i < WEBet.size()) {

				//We need to find a solution to solve the problem. In which elements we have 2 pieces of information
				// first one is the odds
				// second one is the content
				betManager.addBet(currentMatch, "1X2",odds.get(i).getText(),content.get(i).getText());
				i++;
			}
		
			WEBet = webScrapper.getDriver().findElements(By.xpath("//div[@id='od-ma-2-3']/div[@class='MarketBd']/table/tbody/tr/td[*]"));
			odds = webScrapper.getDriver().findElements(By.xpath("//div[@id='od-ma-2-3']/div[@class='MarketBd']/table/tbody/tr/td[*]/a/span[@class='OddsR']"));
			content = webScrapper.getDriver().findElements(By.xpath("//div[@id='od-ma-2-3']/div[@class='MarketBd']/table/tbody/tr/td[*]/a/span[@class='OddsL']"));
			detail = webScrapper.getDriver().findElements(By.xpath("//div[@id='od-ma-2-3']/div[@class='MarketBd']/table/tbody/tr/td[*]/a/span[@class='OddsM']"));
			i = 0;
			while (i < WEBet.size()) {
				if(detail.get(i).getText().equals("2.50")){
					betManager.addBet(currentMatch, "Over Under",odds.get(i).getText(),content.get(i).getText(),detail.get(i).getText());
				}
				i++;
			}
			
			JavascriptExecutor js = (JavascriptExecutor) webScrapper.getDriver();
			js.executeScript("window.history.go(-1)");
			index++;
		}
		

		Vector<Bet> listBets = betManager.getBets();
		return listBets;
	}
	
	public Vector<Bet> searchAll(League league,double days) throws InterruptedException{
		//on vide notre betManager et matchManager
		betManager.removeBets();
		matchManager.removeMatches();
		
		//On ne peut pas acceder au 'today'
		int i=0;
		
		Vector<Match> listMatches = new Vector<Match>();
		Vector<Bet> listBets = new Vector<Bet>();
		while(i<=days){
			Thread.sleep(2000);
			webScrapper.loadSite();
			WebElement link = webScrapper.getDriver().findElement(By.xpath("//*[contains(text(),'"+league.toString()+"')]"));
			link.click();
			
			List<WebElement> listDates = webScrapper.getDriver().findElements(By.xpath("//table[@class='TimeTab']/tbody/tr/td/a"));
			if(listDates.get(i).getText().contains("(0)")){
				i++;
				continue;
			}
			listDates.get(i).click();
			listMatches = searchMatches(league);
			listBets = searchBets2(listMatches,league);
			i++;
		}

		System.out.println("print de fin : "+betManager.printBets());
		
		return listBets;
	}

}
