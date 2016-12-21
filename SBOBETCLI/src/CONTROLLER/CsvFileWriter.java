package CONTROLLER;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import MODEL.Bet;
import MODEL.BetManager;
import MODEL.League;
import MODEL.Robot;


/**
 * @author marceau
 * 
 */
public class CsvFileWriter {
	
	
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ";";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	//CSV file header
	private static final String FILE_HEADER = "League;Home;Away;Market;Content;odds;detail";

	public static void writeCsvFile(String fileName, Vector<Bet> listBets,League league) {
		
		
		FileWriter fileWriter = null;
				
		try {
			fileWriter = new FileWriter(fileName);

			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());
			
			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			//Write a new student object list to the CSV file
			for (Bet bet : listBets) {
				fileWriter.append(String.valueOf(league.toString()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(bet.getMatch().getHome());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(bet.getMatch().getAway());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(bet.getMarket());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(bet.getContent());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(bet.getOdds()));
				if(bet.getDetail().equals("2.50")){
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(bet.getDetail()));
				}

				fileWriter.append(NEW_LINE_SEPARATOR);
			}

			System.out.println("CSV file was created successfully !!!");
			
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
			}
			
		}
	}
}