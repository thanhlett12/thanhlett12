package hw4;

//unit4hw
//Group members: Allison Padilla, Thanh Le.

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

//Main driver class
public class DriverClass {
    private static College valenceCollege;

    public static void main(String[] args) {
        valenceCollege = new College();// Initializing College
        mainMenu(); // Calling main menu method
    }
    
    // Displaying menu options
    private static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        int studentId;
        do {
            System.out.println("\nChoose from the following options:");
            System.out.println("\t1. Add a new student");
            System.out.println("\t2. Add/Delete a course");
            System.out.println("\t3. Search for a student");
            System.out.println("\t4. Print fee invoice");
            System.out.println("\t5. Print fee invoice sorted by crn");
            System.out.println("\t0. Exit program\n\n");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                	   valenceCollege.enrollStudent();
                       break;
                case 2:
                    subMenu(scanner);
                    break;
                case 3:
                    System.out.print("Enter the student’s id: ");
                    studentId = scanner.nextInt();
                    
                    boolean found = valenceCollege.searchById(studentId);
                    if (found) {
                        System.out.println("\nStudent found!\n\n");
                    } else {
                        System.out.println("\nNo Student found!\n\n");
                    }
                    break;
                case 4:
                    System.out.print("Enter the student’s id: ");
                    studentId = scanner.nextInt();
                    valenceCollege.printInvoice(studentId);
                    break;
                case 5:
                    System.out.print("Enter the student’s id: ");
                    studentId = scanner.nextInt();
                    valenceCollege.printSortedInvoice(studentId);
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
    
    // Sub-menu for adding/deleting courses
    private static void subMenu(Scanner scanner){
        System.out.print("Enter the student’s id: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        int numberCourse;
        
        Student student = valenceCollege.selection2(studentId);
        if(student != null){
            char c;
                do{
                    System.out.println("Choose from:");
                    System.out.println("A- Add a new course for ["+ student.getName() +"]");
                    System.out.println("D- Delete a course from ["+ student.getName() +"]'s schedule");
                    System.out.println("C- Cancel operation");
                    System.out.print("Enter your selection: ");
                    c = scanner.next().charAt(0);
                    switch(c){
                        case 'a':
                        case 'A':{
                            System.out.print("Enter course Number to add: ");
                            numberCourse = scanner.nextInt();
                            if(valenceCollege.addCourse(studentId, numberCourse)){
                                System.out.println(String.format("[%d/%s] is add successfully", numberCourse, valenceCollege.getNameCourse(numberCourse)));
                            }else{
                                System.out.println(String.format("[%d/%s] don't add successfully", numberCourse, valenceCollege.getNameCourse(numberCourse)));
                            }
                            break;
                        }
                        case 'd':
                        case 'D':{
                            System.out.print("Enter course Number to delete: ");
                            numberCourse = scanner.nextInt();
                            if(valenceCollege.deleteCourse(studentId, numberCourse)){
                                System.out.println(String.format("[%d/%s] is deleted successfully", numberCourse, valenceCollege.getNameCourse(numberCourse)));
                            }else{
                                System.out.println(String.format("[%d/%s] don't delete successfully", numberCourse, valenceCollege.getNameCourse(numberCourse)));
                            }
                            break;
                        }
                        case 'c':
                        case 'C':{
                            break;
                        }
                        default:{
                            System.out.println("Invalid choice. Please try again.");
                        }
                    }
                    if(c == 'a' || c == 'A' || c == 'd' || c == 'D'){
                            System.out.print("Want to display new invoice? Y/N: ");
                            c = scanner.next().charAt(0);
                            if(c == 'y' || c == 'Y'){
                                student.printFeeInvoice();
                            }
                            c = 'c';
                        }
                }while(c != 'C' && c != 'c');
                return;
        }
    }
}

class Student {
    // Attributes
    private int studentId;
    private String studentName;
    private double gpa;
    private ArrayList<Integer> listOfCrns;
    
    // Constructor
    public Student(int studentId, String studentName, double gpa, ArrayList<Integer> listOfCrns) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.gpa = gpa;
        this.listOfCrns = listOfCrns;
    }

    // Getter/Setter methods
    public int getId() {
        return studentId;
    }

    public void setId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return studentName;
    }

    public void setName(String studentName) {
        this.studentName = studentName;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    public ArrayList<Integer> getListOfCrns(){
        return this.listOfCrns;
    }
    
    // Method to calculate total payment for courses
    private double calculateTotalPayment() {
        double creditHourCost = 120.25;
        double serviceCharge = 35.00;
        double discountPercentage = 0.25;
        double discountThreshold = 700.00;
        int totalCreditHours = 0;
        
        int[] valenceCollege = {4587, 4599, 8997, 9696, 4580, 2599, 1997, 3696};
        String[] course = {"MAT 236", "COP 220", "GOL 124", "COP 100", "MAT 136", "COP 260", "CAP 424", "KOL 110"};
        int[] creditHours = {4, 3, 1, 3, 1, 3, 1, 2};
        
        for(int i=0; i<this.listOfCrns.size(); i++){
            for(int j=0; j<valenceCollege.length; j++)
                if(this.listOfCrns.get(i) == valenceCollege[j]){
                    totalCreditHours += creditHours[j];
                    break;
                }
        }
        double totalPayment = totalCreditHours * creditHourCost + serviceCharge;
    
        if (gpa >= 3.5 && totalPayment > discountThreshold) {
            totalPayment -= totalPayment * discountPercentage;
        }
        return totalPayment;
    }

    // Method to calculate total payment for courses
    public void printFeeInvoice() {
        double totalPayment = calculateTotalPayment();

        // Print the invoice
        System.out.println("\t\tVALENCE COLLEGE");
        System.out.println("\t\tORLANDO FL 10101");
        System.out.println("\t\t---------------------\n");
        System.out.println("\t\tFee Invoice Prepared for Student:");
        System.out.println("\t\t" + studentId + "-" + studentName);
        System.out.println("\n\t\t1 Credit Hour = $120.25\n");
        System.out.println(String.format("\t\t%-5s %-10s %-10s", "CRN", "CR_PREFIX", "CR_HOURS"));
        
        int[] valenceCollege = {4587, 4599, 8997, 9696, 4580, 2599, 1997, 3696};
        String[] course = {"MAT 236", "COP 220", "GOL 124", "COP 100", "MAT 136", "COP 260", "CAP 424", "KOL 110"};
        int[] creditHours = {4, 3, 1, 3, 1, 3, 1, 2};
        
        for(int i=0; i<this.listOfCrns.size(); i++){
            for(int j=0; j<valenceCollege.length; j++)
                if(this.listOfCrns.get(i) == valenceCollege[j]){
                    System.out.println(String.format("\t\t%-5d %-10s %-10d $%7.2f", this.listOfCrns.get(i), course[j], creditHours[j], creditHours[j]*120.25));
                    break;
                }
        }
        System.out.println(String.format("\t\t%-5s %-21s $%7.2f\n", "", "Health & id fees", 35.0));
        System.out.println("\t\t--------------------------------------\n");
        if (gpa >= 3.5 && totalPayment > 700*0.75) {
            System.out.println(String.format("\t\t%27s $%7.2f", " ", totalPayment/0.75));
            System.out.println(String.format("\t\t%27s-$%7.2f", " ", totalPayment/0.75 - totalPayment));
            System.out.println("\t\t                             ----------");
        }
        System.out.println(String.format("\t\t%-5s %-21s $%7.2f\n", "", "TOTAL PAYMENTS", totalPayment));
    }
    
    // Method to display courses
    public void Show(){
        System.out.println("Here are the courses ["+ studentName +"] is taking:");
        System.out.println(String.format("\t\t%-5s %-15s %-10s", "CRN", "PREFIX", "CR_HOURS"));
        
        int[] valenceCollege = {4587, 4599, 8997, 9696, 4580, 2599, 1997, 3696};
        String[] course = {"MAT 236", "COP 220", "GOL 124", "COP 100", "MAT 136", "COP 260", "CAP 424", "KOL 110"};
        int[] creditHours = {4, 3, 1, 3, 1, 3, 1, 2};
        
        for(int i=0; i<this.listOfCrns.size(); i++){
            for(int j=0; j<valenceCollege.length; j++)
                if(this.listOfCrns.get(i) == valenceCollege[j]){
                    System.out.println(String.format("\t\t%-5d %-15s %-10d", this.listOfCrns.get(i), course[j], creditHours[j]));
                    break;
                }
        }
    }
}

class College {
	 // List of students
   private ArrayList<Student> list;
   
   // Constructor
   public College() {
       list = new ArrayList<>();
   }
   
   // Method to enroll a student
   public void enrollStudent() {
       Scanner scanner = new Scanner(System.in);
       System.out.print("Enter the student's id: ");
       int studentId = scanner.nextInt();

       if (searchById(studentId)) {
           System.out.println("Sorry, " + studentId + "  is already assigned to another student.");
           enrollStudent();
       } else {
           scanner.nextLine();
           System.out.print("Enter student's name: ");
           String studentName = scanner.nextLine();
       
           System.out.println("\n\nEnter how many courses "+ studentName +" is taking?");
           int numCourses = scanner.nextInt();
           scanner.nextLine(); 
           System.out.print("Enter the " + numCourses + " course numbers\n");
           ArrayList<Integer> listCourse = new ArrayList<>();
           
           for (int i = 0; i < numCourses; i++) {
               Integer courseCode = scanner.nextInt();
               listCourse.add(courseCode);
           }
           double gpa;
           System.out.print("Enter "+ studentName +"'s current gpa: ");
           gpa = scanner.nextDouble();
           Student student = new Student(studentId, studentName, gpa, listCourse);
           list.add(student);
           System.out.println("Student added successfully.");
       }
   }

   // Method to search for a student by ID
   public boolean searchById(int studentId) {
       for (int i=0; i<list.size(); i++) {
           if (list.get(i).getId() == studentId) {
               return true;
           }
       }
       return false;
   }
   
   // Method to add a course for a student
   public boolean addCourse(int studentId, int crn) {
       for (int i=0; i<list.size(); i++) {
           if (list.get(i).getId() == studentId) {
               ArrayList<Integer> listOfCrns = list.get(i).getListOfCrns();
               for(int j=0; j<listOfCrns.size(); j++)
                   if(listOfCrns.get(j) == crn)
                       return false;
               listOfCrns.add(crn);
               return true;
           }
       }
       return false;
   }

   // Method to delete a course for a student
   public boolean deleteCourse(int studentId, int crn) {
       for (int i=0; i<list.size(); i++) {
           if (list.get(i).getId() == studentId) {
               ArrayList<Integer> listOfCrns = list.get(i).getListOfCrns();
               for(int j=0; j<listOfCrns.size(); j++)
                   if(listOfCrns.get(j) == crn){
                       listOfCrns.remove(j);
                       return true;
                   }
           }
       }
       return false;
   }

   // Method to print fee invoice for a student
   public void printInvoice(int studentId) {
       for (int i=0; i<list.size(); i++) {
           if (list.get(i).getId() == studentId) {
               list.get(i).printFeeInvoice();
               return;
           }
       }
       System.out.println("Student not found.");
   }

   // Method to print fee invoice sorted by CRN for a student
   public void printSortedInvoice(int studentId) {
       for (Student student : list) {
           if (student.getId() == studentId) {
               ArrayList<Integer> sortedListOfCrns = new ArrayList<>(student.getListOfCrns());
               Collections.sort(sortedListOfCrns);
               Student sortedStudent = new Student(student.getId(), student.getName(), student.getGpa(), sortedListOfCrns);
               sortedStudent.printFeeInvoice();
               return;
           }
       }
       System.out.println("Student not found.");
   }
   
   // Method to get course name based on CRN
   String getNameCourse(int id){
       int[] valenceCollege = {4587, 4599, 8997, 9696, 4580, 2599, 1997, 3696};
       String[] course = {"MAT 236", "COP 220", "GOL 124", "COP 100", "MAT 136", "COP 260", "CAP 424", "KOL 110"};
       int[] creditHours = {4, 3, 1, 3, 1, 3, 1, 2};
       for(int i=0; i<valenceCollege.length; i++)
           if(id == valenceCollege[i])
               return course[i];
       return "";
   }
   
   // Method to retrieve student information and display courses
   public Student selection2(int studentId){
       for (Student student : list) 
           if (student.getId() == studentId){
               student.Show();
               return student;
           }
       System.out.println("Student not found.");
       return null;
   }
}
