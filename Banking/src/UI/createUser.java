package UI;
import DBConnectivity.*;
import java.util.*;

public class createUser {
	
	String getNum()
	{
		String num;
		while(true)
		{
			System.out.print("Enter mobile num  ");
			try
				{
				num= frstPage.scan.next();
				if(num.length()!=10)
					System.out.println("\nNum should be of length 10 , So, enter num again   ");
				else if(num.equals(Long.toString(Long.parseLong(num))))
					return num;
				else 
					System.out.println("\n Mobile num can not start with 0");
			}
			catch(Exception e)
			{
				System.out.println("\nMobile num will conatin only numbers  ");
			}
		}
	}
	
	String getName()
	{
		frstPage.scan.nextLine();
		while(true)
		{
			String name = frstPage.scan.nextLine();
			if(name.length()!=0)
				return name;
			else
				System.out.println("Enter Valid name:   ");
		}
	}
	
	String getPassword()
	{
		frstPage.scan.nextLine();
		while(true)
		{
			String pwd = frstPage.scan.nextLine();
			if(pwd.length()!=0)
			{	
				System.out.print("Enter PAssword Again  :");
				String repwd = frstPage.scan.nextLine();
				if(repwd.equals(pwd))
					return pwd;
			}
			else
				System.out.println("Enter Valid Password:   ");
		}
	}
	
	String getDOB()
	{
		System.out.println("Enter Date of Birth Details  ");
		String dob="";
		int year,month,day;
		while(true)
		{
			String Year;
			System.out.println("Enter Year:  ");
			try {
				Year=frstPage.scan.next();
				if(Year.length()==4)
				{
					year=Integer.parseInt(Year);
					if(Year.equals(Integer.toString(year)) && (year>1920) && year<2022)
					{
						dob=Year;
						break;
					}
					else
						System.out.println("Enter Valid Value  ");
				}
				else
					System.out.println("Year Should be of length 4  ");
			}
			catch(Exception e)
			{
				System.out.println("Enter only numeric value  ");
			}
		}
		
		while(true)
		{
			String Month;
			System.out.print("Enter Month:  ");
			try {
				Month=frstPage.scan.next();	
				month=Integer.parseInt(Month);
				if(month>0 && month<13)
				{
					dob= dob+"/"+Month;
					break;
				}
				else
					System.out.println("Enter Valid Value  ");
			}
			catch(Exception e)
			{
				System.out.println("Enter only numeric value  ");
			}
		}
		
		while(true)
		{
			String Day;
			int values[]= {31,28,31,30,31,30,31,31,30,31,30,31};
			System.out.println("Enter Day:  ");
			try {
				Day=frstPage.scan.next();
				day=Integer.parseInt(Day);
				if(day>0 && day<=values[month-1])
				{
					dob=dob+"/"+Day;
					break;
				}
				else
					System.out.println("Enter Valid Value  ");
			}
			catch(Exception e)
			{
				System.out.println("Enter only numeric value  ");
			}
		}
			
		return dob;
	}
	String generateNum()
	{
		while(true) {
			String accNum=Integer.toString(new Random().nextInt(100000))+Integer.toString(new Random().nextInt(100000));
			if(accNum.length()==10)
				return accNum;
		}
	}
	
	public void addNewUser() throws InterruptedException {
		System.out.print("Enter Your Name:   ");	
		String name=getName();
		String num=getNum();
		String dob= getDOB();

		System.out.println("Enter Password  " );
		String password= getPassword();
		String accNum = generateNum();
		if(new DBConnection().checkLogin(num, "")==0)
		{
			if(new DBConnection().addUserToDB(name, num, dob,password,accNum)==1)
			{
				System.out.println("Please note down your account num     "+accNum);
				Thread.sleep(1000);
				System.out.println("Thanks For creating Account   ");
				new optionPage(num).options();
			}
			else
				System.out.println("Account can not be created   ");
		}
		else
			System.out.println("Num allready Exist");
		Thread.sleep(1000);
	}
}