import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;

public class Main {
	
	public static void main(String[] args) throws ParseException, FileNotFoundException, IOException {
//		String path = "C:\\Users\\austi\\Desktop\\nest.json";
//		
//		JSONParser parser = new JSONParser();
//		Reader reader = new FileReader(path);
//		
//		Object obj = parser.parse(reader);
//		
//		JSONObject jObj= (JSONObject) obj;
//		
//		JSONObject kObj= (JSONObject) jObj.get("a");
//		
//		long b = (long) kObj.get("b");
//		
//		System.out.println(b); 
//		
//		reader.close(); //Closes Reader
//		
//		
//		JSONObject newObj = new JSONObject();
//		JSONObject newObj2 = new JSONObject();
//		
//		newObj2.put("b", 12);
//		newObj.put("a", newObj2);
//		
//		FileWriter file = new FileWriter(path);
//		file.write(newObj.toJSONString());
//		file.flush();
//		file.close();
		
		Database d = new Database();
		d.readJSON("C:\\Users\\austi\\Desktop\\JSON_Write Test.json");
		
		d.printTickets();
		d.printMessages();
		
		d.saveJSON("C:\\Users\\austi\\Desktop\\JSON_Write Test.json");
	}
}
