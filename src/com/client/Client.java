package client;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import classes.Booker;
import classes.Hotel;
import classes.Room;
import classes.Transaction;
import util.Constants;
import util.RegularExpression;;
/**
 * @author Yu Jun 2015/5/16.
 *
 */
public class Client {
	
	protected BufferedReader console;
	protected RMIClient rmiClient;
	protected int serverNO;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = new Client(args[0], args[1]);
		client.loop();
	}
	
	public Client(final String path, String serverType){
		rmiClient = null;
		try{
			rmiClient= new RMIClient(path);
		  	if(serverType.equalsIgnoreCase("rmi")){
		  		  serverNO = 1;
		  	} else if(serverType.equalsIgnoreCase("corba")){
		  		  serverNO = 2;
		  	} else {
		  		  System.out.println("NO Such Server!");
		  	}
		}catch(Exception e){ 
			e.printStackTrace();
			System.exit(1);
		}
		console = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void loop(){			
//		System.out.println("Please Select the type of Server? RMI (1) or CORBA (2)");
//		String serverType = null;
//		try {
//			serverType = console.readLine().trim();
//			while (true) {
//				if (serverType.equalsIgnoreCase(Constants.RMI)) {
//					serverNO = 1;
//					break;
//				} else if (serverType.equalsIgnoreCase(Constants.CORBA)) {
//					serverNO = 2;
//					break;
//				} else {
//					System.out.println("Please Input Correct Type! RMI (1) or CORBA (2)");
//				}
//			}
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		 System.out.println("*******************************************************************************");
		 System.out.println(Constants.CITY+"\t--Query the Cities Supported by the System!");
		 System.out.println(Constants.HOTEL+"\t--Input the City Name to get the hotels "
		 		+ "Supported this city!");
		 System.out.println(Constants.LISTHOTEL+"\t--Input the City Name to get the all the hotel information! "
			 		+ "Supported this city!");
		 System.out.println(Constants.ROOMRATE+"\t--Query the Room Rate of your requested Hotel!");
		 System.out.println(Constants.VACANT+ "\t--Input hotel ID, Check-in and Check-out Dates"
		 		+ "to get the vacant rooms in this hotel!");
		 System.out.println(Constants.BOOK+"\t--Input Check-in and Check-out Dates, hotel ID, "
		 		+ "room ID and you information to book the room you specify!");
		 System.out.println(Constants.USER+"\t--Input U(USER) to get information about the informatin about the User!");
		 System.out.println(Constants.QUIT+"\t--Input Q(QUIT) to exit the system!");
		 System.out.println("*******************************************************************************");
		 System.out.println("Please Use the following Commands to "
					+ "get the results you request! ");
		while(true){	
			String line = null;
			try{
				System.out.println();
				System.out.println();
				System.out.print("Enter Request: C | LH <cityname> | HI <cityname>"
						+ " | RR | V | B | U |Q: ");
				line = console.readLine().trim();
			 if (line.equalsIgnoreCase(Constants.CITY)) {
			      listCity();
			      } else if (line.toUpperCase().startsWith(Constants.HOTEL)) {
			        listHotel(losePrefix(line, Constants.HOTEL));
			      } else if (line.toUpperCase().startsWith(Constants.LISTHOTEL)){
			    	listHotelinfo(losePrefix(line, Constants.LISTHOTEL));	    	
			      } else if (line.equalsIgnoreCase(Constants.ROOMRATE)) {
			    	 int flag = 0;
			  		 int count = 0;
			    	 while(true){ 
				    	  System.out.print("Brand: ");
				    	  String brand =console.readLine().trim();
				    	  while((brand.equalsIgnoreCase("hilton")==false) && (brand.equalsIgnoreCase("chevron")==false) && (brand.equalsIgnoreCase("regent")==false)){
				    		    if(++count == 3){
				    			  System.out.println("You have tried 3 times!");
				    			  flag=1;
				    			  break;
				    		  }
				    		  System.out.print("NO This Brand! Try Again: ");
				    		  brand =console.readLine().trim();	
				    	  } 
				    	  if(flag==1){
				    		  break;
				    	  }
				     count=0;
				    	 System.out.print("Hotel NO.: ");
					     String hotelID = console.readLine().trim();
					    	while((hotelID.equals(""))||(RegularExpression.isNumeric(hotelID)==false)){  
					    		  if(++count == 3){
					    			  System.out.println("You have tried 3 times!");
					    			  flag=1;
					    			  break;
					    		  }
					    		  System.out.print("The Hotel NO. is illegal! Try Again: ");
					    		  hotelID = console.readLine().trim();	
					    	  }
					    	  if(flag==1){
					    		  break;
					    	  }  	
			    	  if (!(((brand.equalsIgnoreCase("hilton"))&&(Integer.parseInt(hotelID)%3 == 1))||
			    			  ((brand.equalsIgnoreCase("chevron"))&&(Integer.parseInt(hotelID)%3 == 2))||
			    			  ((brand.equalsIgnoreCase("regent"))&&(Integer.parseInt(hotelID)%3 == 0)))){
			    		  System.out.println("The hotel name is not corresponding to the Hotel Number! "
			    		  		+ " Please Input Correct Hotel Number");
			    		  break;
			    	  }
			    	  boolean room = getRoomrate(hotelID);
			    	  if(room == false){
			    		 System.out.println("Go Back to Main Menu! ");
			    	  }
			    	  break;
			    	  }
			      } else if (line.equalsIgnoreCase(Constants.VACANT)){
			    	  int flag = 0;
			    	  int count = 0;
			    	 while(true){
			    	  System.out.print("Brand: ");
			    	  String brand = console.readLine().trim();
			    	  while((brand.equalsIgnoreCase("hilton")==false) && (brand.equalsIgnoreCase("chevron")==false) && (brand.equalsIgnoreCase("regent")==false)){
			    		  if(++count == 3){
			    			  System.out.println("You have tried 3 times!");
			    			  flag=1;
			    			  break;
			    		  }
			    		  System.out.print("NO This Brand! Try Again: ");
			    		  brand =console.readLine().trim();
			    	  } 
			    	  if(flag==1){
			    		  break;
			    	  }
			    	  count=0;
			    	  System.out.print("Hotel NO.: ");
			    	  String hotelid = console.readLine().trim();
			    	  while((hotelid.equals("")) || (RegularExpression.isNumeric(hotelid)==false)){ 
			    		  if(++count == 3){
			    			  System.out.println("You have tried 3 times!");
			    			  flag=1;
			    			  break;
			    		  }
			    		  System.out.print("The Hotel NO. is illegal! Try Again: ");
			    		  hotelid = console.readLine().trim();		    		 
			    	  }
			    	  if(flag==1){
			    		  break;
			    	  }
			    	  if (!(((brand.equalsIgnoreCase("hilton"))&&(Integer.parseInt(hotelid)%3 == 1))||
			    			  ((brand.equalsIgnoreCase("chevron"))&&(Integer.parseInt(hotelid)%3 == 2))||
			    			  ((brand.equalsIgnoreCase("regent"))&&(Integer.parseInt(hotelid)%3 == 0)))){
			    		  System.out.println("The hotel name is not corresponding to the Hotel Number! "
			    		  		+ " Please Input Correct Hotel Number");
			    		  break;
			    	  }
			    	  count=0;
			    	  System.out.print("Check-in date: ");
			    	  String checkindate = console.readLine().trim();
			    	  System.out.print("Check-out date: ");
			    	  String checkoutdate = console.readLine().trim();
			    	  while(RegularExpression.checkDate(checkindate, checkoutdate)==false){
			    		  if(++count == 3){
			    			  System.out.println("You have tried 3 times!");
			    			  flag=1;
			    			  break;
			    		  }
			    		  System.out.println("The Date is illegal!");
			    		  System.out.print("Check-in date: ");
				    	  checkindate = console.readLine().trim();
				    	  System.out.print("Check-out date: ");
				    	  checkoutdate = console.readLine().trim();
				    	  System.out.println();
				    	 
			    	  }
			    	  if(flag==1){
			    		  break;
			    	  }
			    	  boolean vacant = listVacantRooms(hotelid,checkindate,checkoutdate);
			    	  if(vacant == true){
			    		  System.out.println("These rooms are vacant, and You can select whatever you like!");
			    	  }else{
			    		  System.out.println("Go Back to Main Menu!");
			    	  }
			    	  break;
				    }
			      }else if (line.equalsIgnoreCase(Constants.USER)){
			    	  int count = 0;
			    	  int flag =  0;
				    	 while(true){
//				    	  System.out.print("Brand: ");
//				    	  String brand = console.readLine().trim();
//				    	  while((brand.equalsIgnoreCase("hilton")==false) && (brand.equalsIgnoreCase("chevron")==false) && (brand.equalsIgnoreCase("regent")==false)){
//				    		  if(++count == 3){
//				    			  System.out.println("You have tried 3 times!");
//				    			  flag=1;
//				    			  break;
//				    		  }
//				    		  System.out.print("NO This Brand! Try Again: ");
//				    		  brand =console.readLine().trim();
//				    	  } 
//				    	  if(flag==1){
//				    		  break;
//				    	  }
//				    	  count=0;
				    	  System.out.print("Do You hava an  Account in this Hotel (YES/NO) ? ");
				    	  String temp = console.readLine();
				    	  if(temp.equalsIgnoreCase("NO")){
				    		  System.out.print("Input Your ID NO.: ");
				    		  String bookerid = console.readLine().trim();
					    	  while((bookerid.equals("")) || (RegularExpression.isNumeric(bookerid)==false)){ 
					    		  if(++count == 3){
					    			  System.out.println("You have tried 3 times!");
					    			  flag=1;
					    			  break;
					    		  }
					    		  System.out.print("Your ID NO. is illegal! Try Again: ");
					    		  bookerid = console.readLine().trim();		    		 
					    	  }
					    	  if(flag==1){
					    		  break;
					    	  }
					    	  count=0;
				    		  System.out.print("Your Name: ");
				    		 String bookername = console.readLine();
				    		  System.out.print("Input Your Password: ");
				    		 String passwd = console.readLine();
				    		 while((passwd.equals(""))|| (RegularExpression.checkPassword(passwd)==false)){
				    			  if(++count == 3){
					    			  System.out.println("You have tried 3 times!");
					    			  flag=1;
					    			  break;
					    		  }
				    			  System.out.println("Password is illegal! Try Again!");
				    			  System.out.print("Input Your Password: ");
					    		  passwd = console.readLine();
				    		  }
				    		  if(flag==1){
					    		  break;
					    	  }
					    	  count=0;
				    		  System.out.print("Confirm Your Password: ");
				    		  String passwd1 = console.readLine();
				    		  while((passwd1.equals(""))||(RegularExpression.checkPassword(passwd)==false)){
				    			  if(++count == 3){
					    			  System.out.println("You have tried 3 times!");
					    			  flag=1;
					    			  break;
					    		  }
				    			  System.out.println("Password is illegal! Try Again!");
				    			  System.out.print("Comfirm Your Password: ");
					    		  passwd1 = console.readLine();
				    		  }
				    		  if(flag==1){
					    		  break;
					    	  }
					    	  count=0;
				    		  while(passwd.equals(passwd1)==false){
				    			  if(++count == 3){
					    			  System.out.println("You have tried 3 times!");
					    			  flag=1;
					    			  break;
					    		  }
				    			  System.out.println("Password is not equal! Try Again!");
				    			  System.out.print("Input Your Password: ");
					    		  passwd = console.readLine();
					    		  System.out.print("Confirm Your Password: ");
					    		  passwd1 = console.readLine();
				    		  }
				    		  if(flag==1){
					    		  break;
					    	  }
					    	  count=0;
				    		  System.out.print("Your Tele NO.: ");
				    		  String tele = console.readLine().trim();
				    		  while(RegularExpression.checkTel(tele)==false){  
					    		  if(++count == 3){
					    			  System.out.println("You have tried 3 times!");
					    			  flag=1;
					    			  break;
					    		  }
					    		  System.out.print("You Tele NO. is illegal! Try Again: ");
					    		  tele = console.readLine().trim();		    		 
					    	  }
					    	  if(flag==1){
					    		  break;
					    	  }
					    	  count=0;
				    		  System.out.print("Your E-mail: ");
				    		  String mail = console.readLine().trim();
				    		  while(RegularExpression.checkEmai(mail)==false){ 
					    		  if(++count == 3){
					    			  System.out.println("You have tried 3 times!");
					    			  flag=1;
					    			  break;
					    		  }
					    		  System.out.print("Your Email is illegal! Try Again: ");
					    		  mail = console.readLine().trim();		    		 
					    	  }
					    	  if(flag==1){
					    		  break;
					    	  }
					    	  boolean isinsert = insertBookers(bookerid, bookername, tele, mail, passwd);
				    	  }
				    	  if((!temp.equalsIgnoreCase("YES"))&& (!temp.equalsIgnoreCase("NO"))){
				    		  System.out.println("Input illegal, exit!");
				    		  break;
				    	  }
				    	  count=0;
				    	  System.out.print("Your Account NO.: ");
				    	  String bookerid = console.readLine().trim();
				    	  while(RegularExpression.isNumeric(bookerid)==false){ 
				    		  if(++count == 3){
				    			  System.out.println("You have tried 3 times!");
				    			  flag=1;
				    			  break;
				    		  }
				    		  System.out.print("Your Account NO. is illegal! Try Again: ");
				    		  bookerid = console.readLine().trim();		    		 
				    	  }
				    	  if(flag==1){
				    		  break;
				    	  }
				    	  count=0;
				    	  System.out.print("Your PassWord: ");
				    	  String passwd = console.readLine();
				    	  while(login(bookerid,passwd)==false){
				    		  if(++count == 3){
				    			  System.out.println("You have tried 3 times!");
				    			  flag=1;
				    			  break;
				    		  }
				    		  System.out.print("Your Password is not Correct or Your Account ID doesn't exit! Try Again: ");
				    		  passwd = console.readLine().trim();		    		 
				    	  }
				    	  if(flag==1){
				    		  break;
				    	  }
				    	  boolean booker = listBookerinfo(bookerid);
				    	  if(booker == true){
				    		  System.out.println();
				    		  System.out.println("This is Your Personal information!");
				    	  }else{
				    			  System.out.println("Go Back to Main Menu!");
				    		  }
				    	  break;
				    	  }  
			      }else if (line.equalsIgnoreCase(Constants.BOOK)){
			    	    int count = 0;
			    	    int flag = 0;
				    	 while(true){ 
				    	  System.out.print("Do You hava an  Account in this Hotel (YES/NO) ? ");
				    	  String temp = console.readLine();
				    	  if(temp.equalsIgnoreCase("NO")){
				    		  System.out.print("Input Your ID NO.: ");
				    		  String bookerid = console.readLine().trim();
					    	  while((bookerid.equals(""))||(RegularExpression.isNumeric(bookerid)==false)){ 
					    		  if(++count == 3){
					    			  System.out.println("You have tried 3 times!");
					    			  flag=1;
					    			  break;
					    		  }
					    		  System.out.print("Your ID NO. is illegal! Try Again: ");
					    		  bookerid = console.readLine().trim();		    		 
					    	  }
					    	  if(flag==1){
					    		  break;
					    	  }
					    	 count=0;
				    		 System.out.print("Your Name: ");
				    		 String bookername = console.readLine();
				    		 System.out.print("Input Your Password: ");
				    		 String passwd = console.readLine();
				    		 while((passwd.equals("")) || (RegularExpression.checkPassword(passwd)==false)){
				    			  if(++count == 3){
					    			  System.out.println("You have tried 3 times!");
					    			  flag=1;
					    			  break;
					    		  }
				    			  System.out.println("Password is illegal! Try Again!");
				    			  System.out.print("Input Your Password: ");
					    		  passwd = console.readLine();
				    		  }
				    		  if(flag==1){
					    		  break;
					    	  }
					    	  count=0;
				    		  System.out.print("Confirm Your Password: ");
				    		  String passwd1 = console.readLine();
				    		  while((passwd1.equals(""))||(RegularExpression.checkPassword(passwd)==false)){
				    			  if(++count == 3){
					    			  System.out.println("You have tried 3 times!");
					    			  flag=1;
					    			  break;
					    		  }
				    			  System.out.println("Password is illegal! Try Again!");
				    			  System.out.print("Comfirm Your Password: ");
					    		  passwd1 = console.readLine();
				    		  }
				    		  if(flag==1){
					    		  break;
					    	  }
					    	  count=0;
				    		  while(passwd.equals(passwd1)==false){
				    			  if(++count == 3){
					    			  System.out.println("You have tried 3 times!");
					    			  flag=1;
					    			  break;
					    		  }
				    			  System.out.println("Password is not equal! Try Again!");
				    			  System.out.print("Input Your Password: ");
					    		  passwd = console.readLine();
					    		  System.out.print("Confirm Your Password: ");
					    		  passwd1 = console.readLine();
				    		  }
				    		  if(flag==1){
					    		  break;
					    	  }
					    	  count=0;
				    		  System.out.print("Your Tele NO.: ");
				    		  String tele = console.readLine().trim();
				    		  while(RegularExpression.checkTel(tele)==false){  
					    		  if(++count == 3){
					    			  System.out.println("You have tried 3 times!");
					    			  flag=1;
					    			  break;
					    		  }
					    		  System.out.print("You Tele NO. is illegal! Try Again: ");
					    		  tele = console.readLine().trim();		    		 
					    	  }
					    	  if(flag==1){
					    		  break;
					    	  }
					    	  count=0;
				    		  System.out.print("Your E-mail: ");
				    		  String mail = console.readLine().trim();
				    		  while(RegularExpression.checkEmai(mail)==false){ 
					    		  if(++count == 3){
					    			  System.out.println("You have tried 3 times!");
					    			  flag=1;
					    			  break;
					    		  }
					    		  System.out.print("Your Email is illegal! Try Again: ");
					    		  mail = console.readLine().trim();		    		 
					    	  }
					    	  if(flag==1){
					    		  break;
					    	  }
					    	  boolean isinsert = insertBookers(bookerid, bookername, tele, mail, passwd);
				    	  }
				    	  //---------------------------------------//
				    	  if((!temp.equalsIgnoreCase("YES"))&& (!temp.equalsIgnoreCase("NO"))){
				    		  System.out.println("Input illegal, exit!");
				    		  break;
				    	  }
				    	  count=0;
				    	  System.out.print("Your Account NO.: ");
				    	  String bookerid = console.readLine().trim();
				    	  while((bookerid.equals(""))||(RegularExpression.isNumeric(bookerid)==false)){ 
				    		  if(++count == 3){
				    			  System.out.println("You have tried 3 times!");
				    			  flag=1;
				    			  break;
				    		  }
				    		  System.out.print("Your Account NO. is illegal! Try Again: ");
				    		  bookerid = console.readLine().trim();		    		 
				    	  }
				    	  if(flag==1){
				    		  break;
				    	  }
				    	  count=0;
				    	  System.out.print("Your PassWord: ");
				    	  String passwd = console.readLine();
				    	  while(login(bookerid,passwd)==false){
				    		  if(++count == 3){
				    			  System.out.println("You have tried 3 times!");
				    			  flag=1;
				    			  break;
				    		  }
				    		  System.out.print("Your Password is not Correct or Your Account ID doesn't exit! Try again: ");
				    		  passwd = console.readLine().trim();		    		 
				    	  }
				    	  if(flag==1){
				    		  break;
				    	  }
				    	  count=0;
				    	  System.out.print("Brand: ");
				    	  String brand = console.readLine().trim();
				    	  while((brand.equalsIgnoreCase("hilton")==false) && (brand.equalsIgnoreCase("chevron")==false) && (brand.equalsIgnoreCase("regent")==false)){
				    		  if(++count == 3){
				    			  System.out.println("You have tried 3 times!");
				    			  flag=1;
				    			  break;
				    		  }
				    		  System.out.print("NO This Brand! Try Again: ");
				    		  brand =console.readLine().trim();
				    	  } 
				    	  if(flag==1){
				    		  break;
				    	  }
				    	  count=0;
				    	  System.out.print("Hotel NO.: ");
				    	  String hotelid = console.readLine().trim();
				    	  while((hotelid.equals("")) || (RegularExpression.isNumeric(hotelid)==false)){ 
				    		  if(++count == 3){
				    			  System.out.println("You have tried 3 times!");
				    			  flag=1;
				    			  break;
				    		  }
				    		  System.out.print("The Hotel NO. is illegal! Try Again: ");
				    		  hotelid = console.readLine().trim();		    		 
				    	  }
				    	  if(flag==1){
				    		  break;
				    	  }
				    	  count=0;
				    	  while ((RegularExpression.isNumeric(hotelid)==false)||!(((brand.equalsIgnoreCase("hilton"))&&(Integer.parseInt(hotelid)%3 == 1))||
				    			  ((brand.equalsIgnoreCase("chevron"))&&(Integer.parseInt(hotelid)%3 == 2))||
				    			  ((brand.equalsIgnoreCase("regent"))&&(Integer.parseInt(hotelid)%3 == 0)))){
				    		  if (++count == 3) {
				    			  System.out.println("You have tried 3 times!");
				    			  flag=1;
				    			  break;
				    		  }
				    		  
				    		  System.out.print("No Hotel Number in this Hotel\n"
				    		  		+ "Please Input Correct Hotel Number:");
				    		  hotelid = console.readLine().trim();	
				    	  }
				    	  if(flag==1){
				    		  break;
				    	  }
				    	  count=0;
				    	  System.out.print("Room NO.: ");
				    	  String roomid = console.readLine().trim();
				    	  while(RegularExpression.isNumeric(roomid)==false){ 
				    		  if(++count == 3){
				    			  System.out.println("You have tried 3 times!");
				    			  flag=1;
				    			  break;
				    		  }
				    		  System.out.print("The Room NO. is illegal! Try Again: ");
				    		  roomid = console.readLine().trim();		    		 
				    	  }
				    	  if(flag==1){
				    		  break;
				    	  }
				    	  count=0;
				    	  System.out.print("Check-in date: ");
				    	  String checkindate = console.readLine().trim();
				    	  System.out.print("Check-out date: ");
				    	  String checkoutdate = console.readLine().trim();
				    	  while(RegularExpression.checkDate(checkindate, checkoutdate)==false){
				    		  if(++count == 3){
				    			  System.out.println("You have tried 3 times!");
				    			  flag=1;
				    			  break;
				    		  }
				    		  System.out.println("The Date is illegal!");
				    		  System.out.print("Check-in date: ");
					    	  checkindate = console.readLine().trim();
					    	  System.out.print("Check-out date: ");
					    	  checkoutdate = console.readLine().trim();
					    	  System.out.println();
					    	 
				    	  }
				    	  if(flag==1){
				    		  break;
				    	  }
					     count=0;
				    	 System.out.print("Your credit NO.: ");
				    	 String credit = console.readLine().trim();
				    	 while(RegularExpression.checkCreditNO(credit)==false){
					    	if(++count == 3){
					    		System.out.println("You have tried 3 times!");
					    		flag=1;
					    		break;
					    	}
					    	System.out.print("Your Credit NO. is illegal! Try Again: ");
					    	credit = console.readLine().trim();		    		 
					    }
					    if(flag==1){
					    	break;
					    }
				    	boolean bookinfo = booking(checkindate, checkoutdate, hotelid, roomid, 
				    		               bookerid, credit, brand);
				    	if(bookinfo == true){
				    	}else{	   		
				    		  System.out.println("Booking Failed! Go Back to the Main Menu!");
				    	}
				    	 break;		   
					    }  
			      }else if(line.equalsIgnoreCase(Constants.QUIT)){
			    	  quit();
			    	  System.exit(0);
			      }else {
			    	  System.out.println("Unrecognised command");
			      }
			}catch(IOException e){
			     e.printStackTrace();
			     System.exit(1);
			}
			
		}
	}
	
	public String losePrefix(String str, String prefix) {
		    int index = prefix.length();
		    String ret = str.substring(index).trim();
		    return ret;
	 }
	
	public boolean login(String bookerID, String passwd){
		
		List<Booker> bookers = new ArrayList<Booker>();
		bookers = this.rmiClient.queryBookerinfo(bookerID);
		if(bookers.size()==0){
			return false;
		}else{
		for(Booker booker:bookers){
			if(booker.getPasswd().equals(passwd)){
				return true;
			}
		}
		return false;
		}
	  }
	
	public void listCity(){
		
		List<String> cities = new ArrayList<String>();
		cities = this.rmiClient.queryCity(serverNO);
		int i=1;
		if(cities.size()==0){
			System.out.println("NO Cities int this System!");
		}else{
		for(String city:cities){
			System.out.println(i+". "+city);
			++i;
		}
		}
	}
	
	public void listHotel(String cityname){
		List<String> hotels = new ArrayList<String>();
		String cityName = cityname.toLowerCase();
		hotels = this.rmiClient.queryByCityName(cityName, serverNO);
		if(hotels.size()==0){
			System.out.println("NO Hotels in "+ cityname +" City!");
		}else{
			System.out.println("The Hotels: ");
			int i = 1;
		for(String hotel:hotels){
			System.out.println(i+". "+ hotel);
			++i;
		}
		}
	} 
	
	public void listHotelinfo(String cityname){	
		String cityName = cityname.toLowerCase();
			List<Hotel> hotels = new ArrayList<Hotel>();
			//System.out.println(cityname);
			hotels = this.rmiClient.queryHotel(cityName, serverNO);	
			//System.out.println(hotels.size());
				if(hotels.size()!=0){
					for(Hotel hotel:hotels){
						System.out.print("Brand: "+ hotel.getBandstr());
						System.out.print("\tHotel NO.: "+ hotel.getHotelID());
						System.out.print("\tCity Name: "+ hotel.getCitystr());
						System.out.print("\tLocation: "+ hotel.getLocation());
						System.out.print("\tRoom Rate: "+ hotel.getRoomrate()+" RMB");
						System.out.print("\tTotal Rooms Number: "+ hotel.getRoomNO());
						System.out.print("\tVacant Rooms Number: "+ hotel.getVacantrooms());
						System.out.println();
					}
				} else {
					System.out.println("No hotels in "+cityname+" City");
				}
			
		}
	
	public boolean listBookerinfo(String bookerID){
		if(RegularExpression.isNumeric(bookerID)==false){
			System.out.print("Booker NO. is illegal"); 
			return false;
		}
	  		  List<Booker> bookers = new ArrayList<Booker>();
	  		  bookers = this.rmiClient.queryBookerinfo(bookerID);
	  		  if(bookers.size()!=0){
	  			  int i=1;
	  			  for(Booker booker:bookers){
	  				 System.out.println(i+"."+" Booker ID: "+ booker.getBookerID()+"\tName: "+booker.getBookerName()+"\tTel.: "+
	  			  booker.getTelePhone() +"\tMail Box: "+booker.getEmail());
	  				 System.out.println("   Booking information: "+booker.getBookinfo1());
	  				 System.out.println("   " + booker.getBookinfo2());
	  				 System.out.println();
	  				 ++i;
	  			  }
	  			  return true;
	  		  }else{
	  			  System.out.println("NO this Booker information!");
	  			  return false;
	  		  }
	  	  
		
	}
	
	public boolean getRoomrate(String hotelID){		 
	  
		String roomrate = this.rmiClient.queryRoomrates(hotelID, serverNO);
		if("".equals(roomrate) || roomrate==null){
			System.out.println("This hotel doesn't exist!");
			return false;
		}else{
			System.out.println("The Room rate: " + roomrate+" RMB");
			return true;
		}
	}
	
	public boolean listVacantRooms(String hotelID, String checkindate, 
			String checkoutdate){
		List<Room> rooms = new ArrayList<Room>();
		rooms = this.rmiClient.queryVacantrooms(hotelID, checkindate, checkoutdate,serverNO); 
		//System.out.println(rooms.size()); Can't be executed
		if(rooms != null){
			for(Room room:rooms){
				System.out.println("The Vacant Room NO.: "+room.getRoomID());
			}
			System.out.println();
			return true;
		}else{
			System.out.println("NO Vacant rooms or The hotel NO. you input doesn't exist!");
		}
		return false;
	}
	
	public boolean insertBookers(String bookerID, String bookerName, String telePhone, String email, String passwd){
		boolean insertinfo;  
		insertinfo = this.rmiClient.insertBookers(bookerID, bookerName, telePhone, email, passwd);
	  		  if(insertinfo == true){
	  			  System.out.println("ADD BOOKER SUCCESSUFLLY!");
	  			  return true;
	  		  }else{
	  			 System.out.println("ADD BOOKER FAILED!");
	  			  return false;
	  		  }
		
	  }
	
	public boolean booking(String checkinDate, String checkoutDate, String hotelID, String roomID, 
			String bookerID, String creditNO, String brand){
		String transacID = checkinDate + checkoutDate + hotelID + roomID + bookerID; 
		String book=null;
		book = this.rmiClient.booking(checkinDate, checkoutDate, hotelID, roomID, bookerID, creditNO, brand, serverNO);
		if(book.equalsIgnoreCase(Constants.BOOKSUCCESSFUL)){
		       	System.out.println("Booking Successfully!");
		        System.out.println();
				System.out.println("Transaction information:");
				System.out.print("Hotel:  "+brand);
		        queryTrans(transacID, serverNO);
		        System.out.print("\tCheck-in Date: "+checkinDate+"\tCheck-out Date: "+checkoutDate);
		        return true;
		}else{
		       	System.out.println(book);
		}
	  	  
	  	  return false;
	}
	
	public void queryTrans(String transactionID, int serverNO){
		Transaction trans = new Transaction();
		trans = this.rmiClient.queryTransaction(transactionID, serverNO);
		if(trans==null){
			System.out.println("Your Transaction infomation doesn't exit right now!");
		}else{
			System.out.println(" Booker ID: "+ trans.getBookerID()+"\tHotel NO.: "+trans.getHotelID()+
					"\tRoom NO.: "+trans.getRoomID()+"\tBook Date: "+trans.getBookDT());
		}
		
	}
	public void quit(){
		this.rmiClient.sendMSGclose();
	}
}


