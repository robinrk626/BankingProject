package UI;

import java.sql.*;
import DBConnectivity.*;
public class optionPage {
	String num;
	
	// constructor  
	optionPage(String number)
	{
		num=number;
	}
	
	public void options() throws InterruptedException
	{
		String op;
		do
		{
			System.out.print("1. To Deposit Ammount  \n2. To WithDrawl  \n3. To Tranfer To another Account \n4. To update Password \n5. To Delete Account \n6. To check Balance \n7. To View Ypur Information \n 8.To log out");
			op=frstPage.scan.next();
			switch(op)
			{
				case "1":
					System.out.print("Enter Ammount to deposit   ");
					String enterammount=frstPage.scan.next();
					int ammount;
					try {
						ammount=Integer.parseInt(enterammount);
						if(new DBConnection().depositAmmount(num, ammount)==1)
						{
							System.out.print("\nAmmount Deposited   \n");
							System.out.println("Current Balance = "+new DBConnection().returnBalance(num));
							
						}
						else
							System.out.println("\nAmmount is not Deposited   \n");
					}
					catch(Exception e)
					{
						System.out.println(" \nAmmount Should be Integer only    \n");
					}
					Thread.sleep(1000);
					break;
				case "2":
					System.out.print("Enter Ammount to WithDrawl   ");
					String EnterAmmount=frstPage.scan.next();
					try
					{
						int Ammount=Integer.parseInt(EnterAmmount);
						int check=new DBConnection().withDrawlAmmount(num, Ammount);
						if(check==1)
						{
							System.out.println("\nAmmount Detucted from accunt  ");
							System.out.println("Current Balance = "+new DBConnection().returnBalance(num));
						}
						else if(check==0)
							System.out.println("\nInsufficient Balance   \n");
						else 
							System.out.println("\nServer Error... \n");
					}
					catch(Exception e)
					{
						System.out.println("\n Ammount Should be Integer only    \n");
					}
					Thread.sleep(2000);
					break;
				case "3":
					System.out.print("Enter Ammount to Transfer   ");
					int tAmmount=0;
					try {
						String enterAmmount=frstPage.scan.next();
						tAmmount= Integer.parseInt(enterAmmount);
						try
						{
							System.out.print("Enter linked Mpbile num to transfer    ");
							String accNum=frstPage.scan.next();
							long check=Long.parseLong(accNum);
							if(accNum.equals(Long.toString(check)))
							{
								int checkStatus= new DBConnection().transferMoney(num,tAmmount,accNum);
								if(checkStatus==0)
									System.out.println("\nInsufficient Balance ...\n");
								else if(checkStatus==1)
									System.out.println("\nEntered num Does Not exist ...\n");
								else if(checkStatus==3)
								{
									System.out.println("\nMoney Transfered ...\n");
									System.out.println("Current Balance = "+new DBConnection().returnBalance(num));
								}
								else
									System.out.print("Server Error.. ");	
							}
							else
								System.out.println("\nWrong Mobile Num   \n");
						}
						catch(Exception e)
						{
							System.out.println("\nAccount Num conatin only Numbers \n");
						}
					}
					catch(Exception e)
					{
						System.out.print("Transfer Ammount can be integer only   ");
					}
					Thread.sleep(1000);
					break;
				case "4":
					System.out.print("Enter new Password    ");
					String pwd=frstPage.scan.next();
					if(new DBConnection().updatePassword(num, pwd)==1)
						System.out.println("\nPassword Updated ...\n");
					else
						System.out.println("\nServer Problem... \n");
					break;
				case "5":
					System.out.print("Are You sure?     y/n");
					switch(frstPage.scan.next())
					{
						case "y":
							if(new DBConnection().returnBalance(num)!=0)
							{
								System.out.println("\nFirst WithDrawl All ammount from account  \n");
								Thread.sleep(1000);
								break;
							}
							
							if(new DBConnection().deleteAccount(num)==1)
							{
								System.out.println("Account Deleted \n");
								op="7";
							}
							else
								System.out.print("\nThere is some problem while deleting account\n");
							Thread.sleep(1000);
							break;
						case "n":
							System.out.println("Request Declined\n");
							Thread.sleep(1000);
							break;
						default:
							System.out.println("Wrong Input, So Request Declined\n");
							Thread.sleep(1000);
							break;
					}
					break;
				case "6":
					int balance=new DBConnection().returnBalance(num);
					if(balance==-1)
						System.out.println("Server problem ...   \n");
					else
						System.out.println(" \nBalance  =  "+balance+"\n");
					Thread.sleep(1500);
					break;
				case "7":
					ResultSet rs =new DBConnection().returnInfo(num);
					try {
						rs.next();
						System.out.println("Name = "+rs.getString(2)+"\nMobile Num = "+rs.getString(1)+"\nDOB  "+rs.getString(3));
					} 
					catch (Exception e) {
						System.out.print(e);						
					}
					Thread.sleep(1000);
					break;
				case "8":
					break;
				default :
					System.out.print("Wrong Input   ");
			}
		}
		while(!op.equals("8"));
	}

}