package DBConnectivity;

import java.sql.*;
public class DBConnection {

	/*public static void main(String args[])
	{
		try {
			Connection connection= DriverManager.getConnection("jdbc:oracle:thin:@DESKTOP-LQENBF4:1521:XE", "SYSTEM", "Robin_16626");
			Statement stmt = connection.createStatement();
			stmt.execute("drop table loginInfo");
			stmt.execute("drop table UserInfo");
			
			stmt.execute("create table loginInfo(mobileNum varchar(11) Primary Key, password varchar(15))");
			stmt.execute("create table UserInfo(mobileNum varchar(11) Primary Key, name varchar(50), dob varchar(15), balance number,accNum varchar(12)) ");
			System.out.print("Table create....");
			connection.close();
		}
		catch(Exception e)
		{
			System.out.print(e);
		}
	}*/
	
	public int addUserToDB(String name,String num,String dob,String password, String accNum) {
		try {
			Connection connection= DriverManager.getConnection("jdbc:oracle:thin:@DESKTOP-LQENBF4:1521:XE", "SYSTEM", "Robin_16626");
			Statement stmt=connection.createStatement();
			stmt.execute("insert into loginInfo values('"+num+"','"+password+"')");
			stmt.execute("insert into UserInfo values('"+num+"','"+name+"','"+dob+"',0,'"+accNum+"')");
		}
		catch(Exception e)
		{
			System.out.println(e);
			return 0;
		}
		return 1;
	}
	
	public ResultSet returnInfo(String num)
	{
		try {
			Connection connection= DriverManager.getConnection("jdbc:oracle:thin:@DESKTOP-LQENBF4:1521:XE", "SYSTEM", "Robin_16626");
			Statement stmt=connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("select * from UserInfo where mobileNum = '"+num+"'");
			return rs;
			}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}

	public int checkLogin(String num, String pwd)
	{
		try {
			Connection connection= DriverManager.getConnection("jdbc:oracle:thin:@DESKTOP-LQENBF4:1521:XE", "SYSTEM", "Robin_16626");
			Statement stmt=connection.createStatement();
			ResultSet rs;
			rs= stmt.executeQuery("select count(*) from loginInfo where mobileNum='"+num+"'");
			rs.next();
			if(rs.getInt(1)==0)
				return 0;
			rs = stmt.executeQuery("select * from loginInfo where mobileNum = '"+num+"'");
			rs.next();
			if(pwd.equals(rs.getString(2)))
				return 2;
			else
				return 1;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return 3;
		}
	}
	
	public int returnBalance(String num)
	{
		try {
			Connection connection= DriverManager.getConnection("jdbc:oracle:thin:@DESKTOP-LQENBF4:1521:XE", "SYSTEM", "Robin_16626");
			Statement stmt=connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("select * from UserInfo where mobileNum = '"+num+"'");
			rs.next();
			return rs.getInt(4);
		}
		catch(Exception e)
		{
			System.out.println(e);
			return -1;
		}
	}
	
	public int depositAmmount(String num, int ammount)
	{
		try {			
			Connection connection= DriverManager.getConnection("jdbc:oracle:thin:@DESKTOP-LQENBF4:1521:XE", "SYSTEM", "Robin_16626");
			Statement stmt=connection.createStatement();
			stmt.execute("update UserInfo set balance= balance +"+ammount+" where mobileNum = '"+num+"'");
			return 1;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return -1;
		}
	}
	
	public int withDrawlAmmount(String num, int ammount)
	{
		try {			
			Connection connection= DriverManager.getConnection("jdbc:oracle:thin:@DESKTOP-LQENBF4:1521:XE", "SYSTEM", "Robin_16626");
			Statement stmt=connection.createStatement();
			int cBalance=returnBalance(num);
			if(ammount>cBalance)
				return 0;
			stmt.execute("update UserInfo set balance= balance -"+ammount+" where mobileNum = '"+num+"'");
			return 1;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return -1;
		}
	}
	
	public int transferMoney(String num, int ammount, String receiver)
	{
		try {			
			Connection connection= DriverManager.getConnection("jdbc:oracle:thin:@DESKTOP-LQENBF4:1521:XE", "SYSTEM", "Robin_16626");
			Statement stmt=connection.createStatement();
			if(returnBalance(num)<ammount)
				return 0;
			ResultSet rs;
			rs= stmt.executeQuery("select count(*) from loginInfo where mobileNum='"+receiver+"'");
			rs.next();
			if(rs.getInt(1)==0)
				return 1;
			if(withDrawlAmmount(num,ammount)==1 &&depositAmmount(receiver, ammount)==1)
				return 3;
			else 
				return -1;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return -1;
		}
	}
	
	public int updatePassword(String num, String pwd)
	{
		try {			
			Connection connection= DriverManager.getConnection("jdbc:oracle:thin:@DESKTOP-LQENBF4:1521:XE", "SYSTEM", "Robin_16626");
			Statement stmt=connection.createStatement();
			stmt.execute("update loginInfo set password= '"+pwd+"' where mobileNum = '"+num+"'");
			return 1;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return -1;
		}
	}
	
	public int deleteAccount(String num)
	{
		try {			
			Connection connection= DriverManager.getConnection("jdbc:oracle:thin:@DESKTOP-LQENBF4:1521:XE", "SYSTEM", "Robin_16626");
			Statement stmt=connection.createStatement();
			stmt.execute("delete loginInfo where mobileNum='"+num+"'");
			stmt.execute("delete userInfo where mobileNum='"+num+"'");
			return 1;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return -1;
		}
	}
	
}
