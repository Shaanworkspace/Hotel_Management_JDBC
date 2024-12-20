
import java.util.Scanner;
import java.sql.DriverManager;//Class-> retrieve Input of Data
import java.sql.SQLException;

//All are interfaces
import java.sql.Statement; //Data insert
import java.sql.Connection; //To establish connection
import java.sql.ResultSet; //Run SQL query


public class hotelManagementSystem {
    private static final String url ="jdbc:mysql://localhost:3306/hotel";
    private static final String username="root";
    private static final String password="1234";
    //we are making all private -> not to give access to anyone else

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection connectionObject = DriverManager.getConnection(url,username,password);
            Statement statement = connectionObject.createStatement();
            while(true){
                Scanner sc = new Scanner(System.in);
                System.out.println();
                System.out.println("Hotel Management System");
                System.out.println();
                System.out.println("0. Exit");
                System.out.println("1. Reserve Room");
                System.out.println("2. View Reservation");
                System.out.println("3. Get Details");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("6. Update Mobile No");
                System.out.println();
                System.out.print("Enter your choice : ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 0:
                        exit();
                        sc.close();
                        break;

                    case 1:
                        reserveRoom(sc,statement);
                        break;

                    case 2:
                        viewReserve(statement);
                        break;
                    case 3:
                        getRoomNo(sc,statement);
                        break;
                    case 4:
                        updateReservation(sc,statement);
                        break;
                    case 5:
                        deleteReservation(sc,statement);
                        break;
                    case 6:
                        updateMobileNo(sc,statement);
                        break;
                    default:
                        System.out.println("Invalid Choice");
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    private static void reserveRoom(Scanner sc, Statement statement) {
        System.out.println("Hotel Management System");
        System.out.println();
        System.out.print("Name : ");
        String name =  sc.next();
        sc.nextLine();
        System.out.print("Enter Room No  : ");
        int roomNo = sc.nextInt();
        System.out.print("Contact Number : ");
        String contact = sc.next();

        // saving the query
        String query = "INSERT INTO reservation(guestName,roomNo,mobileNumber) VALUES ('"+name+"','"+roomNo+"','"+contact+"')";

        try{
            int rowAffected = statement.executeUpdate(query);
            if(rowAffected>0) System.out.println("Successfully Executed");
            else System.out.println("Failed Operation.Please Try again Later.");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void viewReserve(Statement statement)throws SQLException {
        String query = "SELECT * FROM reservation";
        try{
            System.out.println("All reservations are : ");
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Current Reservations:");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number      | Reservation Date        |");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            while(resultSet.next()){
                int Sno = resultSet.getInt("Sno");
                String name = resultSet.getString("guestName");
                int roomNo = resultSet.getInt("roomNo");
                String contact = resultSet.getString("mobileNumber");
                String date = resultSet.getTimestamp("reservationDate").toString();
                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s | \n",Sno,name,roomNo,contact,date);
            }
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    private static void getRoomNo(Scanner sc, Statement statement){
        System.out.println("Hotel Management System");
        System.out.println();
        System.out.print("Name : ");
        String name = sc.next();
                sc.nextLine();
        System.out.print("Enter Contact Number  : ");
        String contact = sc.nextLine();
        String query = "SELECT * FROM reservation WHERE guestName = '" + name + "' AND mobileNumber = '" + contact + "'";
        //String query = "SELECT * FROM reservation WHERE mobileNumber = "+contact;

        try{
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                int Sno = resultSet.getInt("Sno");
                String name1 = resultSet.getString("guestName");
                int roomNo = resultSet.getInt("roomNo");
                String contact1 = resultSet.getString("mobileNumber");
                String date = resultSet.getTimestamp("reservationDate").toString();
                System.out.println("Current Reservations:");
                System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
                System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number      | Reservation Date        |");
                System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s | \n",Sno,name1,roomNo,contact1,date);
                System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            }else System.out.println("Reservation not found for the given ID and guest name.");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void updateReservation(Scanner sc, Statement statement) {
        System.out.println("Hotel Management System");
        System.out.println();
        System.out.print("Enter Reservation Id : ");
        int reservationId = sc.nextInt();

        if(!reservationExist(reservationId, statement)){
            System.out.println("Data does not exist");
            return;
        }

        System.out.print("Enter New Guest Name: ");
        String newGuestName = sc.next();
        sc.nextLine();
        System.out.print("Enter New Room No: ");
        int newRoomNo = sc.nextInt();
        System.out.print("Enter New Mobile Number: ");
        String newMobileNo = sc.next();

        String query = "UPDATE reservation SET guestName='"+newGuestName+"',"+ "roomNo = '"+newRoomNo+"',"+ " mobileNumber="+newMobileNo+" WHERE Sno='"+reservationId+"'";
        try{
            int rowAffected=statement.executeUpdate(query);
            if(rowAffected>0) System.out.println("Successfully Executed");
            else System.out.println("Failed Operation.Please Try again Later.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deleteReservation( Scanner sc, Statement statement){
        System.out.println("Hotel Management System");
        System.out.println();
        System.out.println("Enter Reservation Id : ");
        int reservationId = sc.nextInt();

        if(reservationExist(reservationId, statement)){
            System.out.println("Data does not exist");
            return;
        }

        String query = "DELETE FROM reservation WHERE Sno='"+reservationId+"'";

        try{
            int rowAffected=statement.executeUpdate(query);
            if(rowAffected>0) System.out.println("Successfully Executed");
            else System.out.println("Failed Operation.Please Try again Later.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateMobileNo(Scanner sc, Statement statement){
        System.out.println("Hotel Management System");
        System.out.println();
        System.out.print("Enter Reservation Id : ");
        int reservationId = sc.nextInt();

        if(!reservationExist(reservationId, statement)){
            System.out.println("Data does not exist");
            return;
        }
        System.out.print("Enter New Mobile Number: ");
        String newMobileNo = sc.next();

        String query = "UPDATE reservation SET mobileNumber="+newMobileNo+" WHERE Sno='"+reservationId+"'";

        try{
            int rowAffected=statement.executeUpdate(query);
            if(rowAffected>0) System.out.println("Successfully Executed");
            else System.out.println("Failed Operation.Please Try again Later.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean reservationExist(int Sno, Statement statement){
        try {
            String query = "SELECT Sno FROM reservation WHERE Sno = "+Sno;
            try {
                ResultSet resultSet = statement.executeQuery(query);
                return resultSet.next();
            } finally {

            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i = 5;
        while(i!=0){
            System.out.print(".");
            Thread.sleep(100);
            i--;
        }
        System.out.println();
        System.out.println("ThankYou For Using Hotel Reservation System!!!");
    }

}
