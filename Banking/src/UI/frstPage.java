package UI;
import DBConnectivity.*;
import java.util.Scanner;

public class frstPage {
	static Scanner scan;
	
	public static void main(String[] args) throws InterruptedException {
		String op;
		try {
		do
		{
			System.out.print("1. To create a account \n2. To login to existing account \n3. To quit   ");
			scan=new Scanner(System.in);	
			op= scan.next();
			switch(op)
			{
				case "1":
					new createUser().addNewUser();
					break;
				case "2":
					String num;
					while(true)
					{
						Thread.sleep(1000);
						System.out.print("Enter Linked Mobile num   ");
						num=scan.next();
						try
						{
							long n= Long.parseLong(num);
							if(num.equals(Long.toString(n)))
								break;
							else
								System.out.println("\nEnter valid num \n");
							
						}
						catch(Exception e)
						{
							System.out.println("Mobile num should contain only numbers  \n");
						}
					}
					System.out.print("Enter Password   ");
					String pwd=scan.next();
					int status=new DBConnection().checkLogin(num, pwd);
					if(status==0)
						System.out.println("Mobile num Does not exist   \n");
					else if(status==1)
						System.out.println("\nWrong Password.. \n");
					else if(status==2)
						new optionPage(num).options();
					else
						System.out.println("Server Error...   ");
					break;
				case "3":
					System.out.print("  ..Thanks for using..");
					break;
				default:
					System.out.print("Wrong Input ");
			}
		}while(!op.equals("3"));
		scan.close();
		}
		catch(Exception e) {
			System.out.print(e);}
	}

}