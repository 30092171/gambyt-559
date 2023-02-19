import java.util.ArrayList; // import the ArrayList class


public class Ticket {

	public String name = "";
	public long assignee = -1;
	public long status = -1;
	public ArrayList<String> subscribers = new ArrayList<String>();
	public String description = "";
	public String dateAssigned = "0000-00-00";
	public long priority = -1;

	
	public void PrintTicketInfo() {
		
		System.out.println("Name: " + name);
		System.out.println("assignee: " + assignee);
		System.out.println("status: " + status);
		
		System.out.println("Subscribers: ");
		for(int i = 0; i < subscribers.size();i++) {
			System.out.println("   " + subscribers.get(i));
		}
		
		System.out.println("description: " + description);
		System.out.println("dateAssigned: " + dateAssigned);
		System.out.println("priority: " + priority + "\n");
		
	
	}
}
