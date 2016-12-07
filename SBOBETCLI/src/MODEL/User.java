package MODEL;

/**
 * 
 * @author K.Misho
 * @date august '15
 * @version 1.0
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class User {
	
	// personal info
	private String username = null;
	private String password = null;
	
	// scrapping info
	private WebElement username_script = null;
	private WebElement password_script = null;
	private WebElement confirm_script = null;
	
	public User(String name, String pass){
		username = name;
		password = pass;
	}
	
	public WebElement get_username_script(WebDriver src){
		String _val = "username";
		username_script = src.findElement(By.id(_val));
		return username_script;
	}
	
	public WebElement get_password_script(WebDriver src){
		String _val = "password";
		password_script = src.findElement(By.id(_val));
		return password_script;
	}
	
	// here we can specify the script that finds the validation button
	public WebElement get_confirm_script(WebDriver src){
		String _val = "//li[@class="+"'sign-in '"+"]";
		confirm_script = src.findElement(By.xpath(_val));
		return confirm_script;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
}
