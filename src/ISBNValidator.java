import java.util.Scanner;
import java.io.File;

/**
 * ISBN Validator imports, verifies, and stores ISBN numbers
 * @version January 20, 2023
 * @author ameliak
 */
public class ISBNValidator {
    private String filename;
    private String[] validNums;
    private String[] invalidNums;

    /**
     * simple constructor; initializes arrays
     * opens datastream from file
     */
    public ISBNValidator()  {
        filename = "isbn_files/isbn3.dat";
        int lines = 0;
        try {
            Scanner in = new Scanner(new File(filename));
            while(in.hasNext()) {
                lines++;
                in.nextLine();
            }
            in.close();
        }
        catch(Exception e)  {
            System.out.println(e.toString());
        }
        validNums = new String[lines];
        invalidNums = new String[lines];
    }

    /**
     * imports .dat file, calls isValidISBN method and stores Strings into corresponding arrays
     * @params none
     * @returns none
     */
    public void importData()    {
        try {
            Scanner in = new Scanner(new File(filename));
            int validCount = 0, invalidCount = 0;
            while(in.hasNext()) {
                String isbn = in.nextLine().trim().strip();
                if(isValidISBN(isbn))
                    validNums[validCount++] = isbn;
                else
                    invalidNums[invalidCount++] = isbn;
            }
            in.close();
        }
        catch(Exception e)  {
            System.out.println(e.toString());
        }
    }

    /**
     * determines validity of supplied ISBN number; called inside importData
     * @param isbn An ISBN number to test
     * @return true if valid, false otherwise
     */
    public boolean isValidISBN(String isbn) {
        int num = 0;
        //243-6-39-545216-4
        /*
        num = num + Integer.parseInt(isbn.substring(0,1));
        num = num + 3 * Integer.parseInt(isbn.substring(1,2));
        num = num + Integer.parseInt(isbn.substring(2,3));
        num = num + 3 * Integer.parseInt(isbn.substring(4,5));
        num = num + Integer.parseInt(isbn.substring(6,7));
        num = num + 3 * Integer.parseInt(isbn.substring(7,8));
        num = num + Integer.parseInt(isbn.substring(9,10));
        num = num + 3 * Integer.parseInt(isbn.substring(10,11));
        num = num + Integer.parseInt(isbn.substring(11,12))
        num = num + 3 * Integer.parseInt(isbn.substring(12,13));
        num = num + Integer.parseInt(isbn.substring(13,14));
        num = num + 3 * Integer.parseInt(isbn.substring(14,15));
        num = num + Integer.parseInt(isbn.substring(16,17));
        return num % 10 == 0 && (isbn.substring(0, 3).equals("979") || isbn.substring(0, 3).equals("978"));
         */
        isbn = isbn.replaceAll("-","");
        for(int i = 1; i < 14; i++){
            if(i % 2 == 0)
            num += Integer.parseInt(isbn.substring(i-1,i)) * 3;
            else num += Integer.parseInt(isbn.substring(i-1,i));
        }
        return num % 10 == 0 && (isbn.substring(0, 3).equals("979") || isbn.substring(0, 3).equals("978"));
    }

    /**
     * output the user-picked ISBN list or quit the application
     */
    public void runProgram()    {
        Scanner userin = new Scanner(System.in);
        System.out.println("* ISBN Validator Program *");
        System.out.println("...Importing data...");
        while(true) {
            System.out.println("All ISBN data has been imported and validated. Would you like to:");
            System.out.println("\t1) View all valid ISBN numbers\n" +
                    "\t2) View all invalid ISBN numbers\n" +
                    "\t3) Quit");
            System.out.print("Your selection: ");
            String userpick = userin.nextLine();
            if(userpick.equals("3"))
                break;
            else if (userpick.equals("1")) {
                for(int i = 0; i < validNums.length && validNums[i] != null; i++)
                    System.out.println(validNums[i]);
            }
            else if (userpick.equals("2")) {
                for(int i = 0; i < invalidNums.length && invalidNums[i] != null; i++)
                    System.out.println(invalidNums[i]);
            }
            else
                System.out.println("Invalid selection, try again.");
        }
    }

    public static void main(String[] args){
        ISBNValidator app = new ISBNValidator ();
        System.out.println("* ISBN Validator Program *");
        System.out.println("...Importing data...");
        app.importData(); // imports data from the text file
        app.runProgram(); // runs using a while loop; see examples
        System.out.println("* End of Program *");
    }
}
