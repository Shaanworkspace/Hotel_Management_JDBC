

# Hotel Management System

A brief description of what this project does and who it's for


## Initialisation

-STEP 1 : import libraries then setup required functions

    import java.util.Scanner;
    import java.sql.DriverManager;
    import java.sql.SQLException;

    //All are interfaces
    import java.sql.Statement; //Data insert
    import java.sql.Connection; //To establish connection
    import java.sql.ResultSet; //Run SQL query
    private static void reserveRoom(){}
    private static void deleteReservation(){}
    private static void viewReserve(){}
    private static void getRoomNo(){}
    private static void updateRoomNo(){}
    private static void updateMobileNo(){}

-STEP 2 : Inside Class provide 3 variables as every database have url , username ,password

    private static final String url ="jdbc:mysql://localhost:3306/hotel";
    private static final String username="root";
    private static final String password="1234";
url -> will be by coping from WORKBENCH
private -> Access Only Class as very sensitive database
static final-> we can access without obj and no changes 
these are usefull to connect with database 


-STEP 3 : Now main method we set as our main class can have these two exception 

    public static void main(String[] args)throws SQLException, ClassNotFoundException {

    }

--STEP 4: Loading driver inside main class by use of exception handling

    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
    }catch (ClassNotFoundException e){
        System.out.println(e.getMessage());
    }

To connect with databases we have added the username password , url etc now time to include drivers to connect with databases "com.mysql.cj"-> pakage which have all the drivers me "jdbc ke Drivers" ko load kro
"forName" -> method 


## Building Connection block(main block of Project)

--STEP 5: just after the previous block give us another try and catch block 

            try{
                Connection connectionObject = DriverManager.getConnection(url,username,password);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
Connection is a interface as we now want ot connect our ide to db as we cant get instance of interface directly so we need a class which extends interface so this is DriverManager class contain .getConnection()

Now connection stored in connectionObject instance 

--STEP 6:The main menu visible these is Code after Connection 


                while(true){
                    Scanner sc = new Scanner(System.in);
                    System.out.println();
                    System.out.println("Hotel Management System");
                    System.out.println();
                    System.out.println("0. Exit");
                    System.out.println("1. Reserve Room");
                    System.out.println("2. View Reservation");
                    System.out.println("3. Get Room Details");
                    System.out.println("4. Update Reservation");
                    System.out.println("5. Delete Reservation");
                    System.out.println();
                    System.out.println("Enter your choice");
                    int choice = sc.nextInt();

Also after is there is switch statement 

                    switch (choice) {
                        case 0:
                            exit();
                            sc.close();
                            break;

                        case 1:
                            reserveRoom(connectionObject,sc);
                            break;

                        case 2:
                            viewReserve(connectionObject);
                            break;
                        case 3:
                            getRoomNo(connectionObject,sc);
                            break;
                        case 4:
                            updateReservation(connectionObject,sc);
                            break;
                        case 5:
                            deleteReservation(connectionObject,sc);
                            break;
                        default:
                            System.out.println("Invalid Choice");
                    }
                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }    
we have multiple catch block as Other is for exit() function may be it throw error 
## Lessons Learned

What did you learn while building this project? What challenges did you face and how did you overcome them?

-1. Instance : try to make all the instance to main class and pass to the all the functions you make like here we have made two instance scanner ka and Connection vala , and paswed to every funvtion so that :

Example : we have 1000 user of a application having 5 functions and we have made diffrent instance of scanner in every function we made and suppose they are all are using all function at a time to thee will 5000 instance So very Much Load On Server Due to Instance 

    reserveRoom(Connection connection, Scanner sc)

-2. Use case :

    .executeUpdate(query) => use to INSERT , DELETE , UPDATE
        returns a integer value->row affected
    .executeQuery(query) => retrieve , print
        returns a Data Stored in ResultSet ka obeject 
Now to print data in resultset ionstance we can iterate through while loop with a method 
        
        while(resultsetObject.next()){}

-3. Date/Time Storage

    String date = resultSet.getTimestamp("reservationDate").toString();
                //This is most important as date Storage 
-4 Use of c in java

    System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s | \n",Sno,name,roomNo,contact,date);

-5 USE case difference

    String firstName = sc.next(); // Reads only the first token (word)
    String firstName = sc.next(); // Reads only the first token (word)
    sc.nextLine(); // Consumes the leftover newline character
## Errors

-Error 1: In reserve room block -> the cursur was not stopping to enter the name ,it was moving to room no diectly
 so changes are as follows to fix it

    System.out.print("Name : ");
        String name =  sc.next();
        sc.nextLine();

-Error 2: "Column count doesn't match value count at row 1
" while filling detail as name room No etc To solve it we neet to give exact amount of data we created columns, so removed sno as i have not given input for it 

    String query = "INSERT INTO reservation(Sno,guestName,roomNo,mobileNumber) VALUES 
            Changed to.... 
    String query = "INSERT INTO reservation(guestName,roomNo,mobileNumber) VALUES ('"+name+"','"+roomNo+"','"+contact+"')";
