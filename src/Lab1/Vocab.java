//
// COM3101 Lab 1
// Vocab
// Student ID: s216753
// Student Name: Lam Ling Yiu
// Sesson L01

package lab1;

import java.util.*;
import java.io.*;

public class Vocab {

    //Scanners and data structure here
    Scanner kb = new Scanner(System.in);
    HashMap<String, String> myData = new HashMap<>();
    String cmd = "";
    String userInputKey,userInputVal = "";
    boolean isEnd = false;
    
    public void handleCommands() {
     // Repeat until /exit
	 //	   prompt for the next command
	 //	   and call the appropriate methods (doAdd doDelete doFind doImport) to handle it
         //if cmd is /add
         // call doAdd();
         //if cmd /del
         // call doDelete();
         System.out.println("Enter a word in English, or a valid command (/add, /del, /import, /exit)");
         while(!isEnd){
             System.out.print(">");
             cmd = kb.nextLine();

             if(cmd.charAt(0)=='/'){
                if(cmd.equals("/add")){
                    doAdd();
                }else{
                    if(cmd.equals("/del")){
                        doDelete();
                    }else{
                        if(cmd.equals("/import")){
                            doImport();
                        }else{
                            if(cmd.equals("/exit")){
                                isEnd = true;   
                            }else{
                                System.out.println("Invalid command");
                            }                            
                        }                        
                    }                
                }
             }else{
                 doFind(cmd);
             }
         }
    }

    public void doAdd() {
        //ask user to enter a word
        //ask user to enter the meaning
        //save the word
     // prompt the users to enter a word and a meaning pair, and put them into your data structure   
        System.out.print("Enter a word or phase to be added: ");
        userInputKey = kb.nextLine();
        System.out.print("Enter its meaning: ");        
        userInputVal = kb.nextLine();
        myData.put(userInputKey,userInputVal);
    }

    public void doDelete() {
	// prompt the user to enter a word, and remove it (word and meaning) from your data structure
	// show appropriate messages to indicate the results (i.e., successfully removed or nt)
        System.out.print("Enter a word or phase to be deleted: ");
        userInputKey = kb.nextLine();
        System.out.println("Entry deleted:");
        myData.remove(userInputKey);
    }

    public void doFind(String word) {
	// prompt the user to enter a word, 
	// find the meaning from the data structure and show it to the user
	// show an appropriate message if the word is not found
        String value = myData.get(word);
        try{
        if(value.equals("null")){
            System.out.print("'"+word+"' is not found");
        }else{
            System.out.println("'"+word+"' means "+value);
        }                 
        }catch(NullPointerException err){
            System.out.println("'"+word+"' is not found");                 
        }        
        
    }

    public void doImport() {
	// prompt the user to enter a file name
	// load all entries (word and meaning pairs) from the file into your data structure
	// show appripriate messages in case of errors.
		         
        try {
            System.out.print("Enter the name of file to import from:");
            String filename = kb.nextLine();
            File file = new File(filename);
            Scanner inputFile = new Scanner(file);
            int i=0;
            while (inputFile.hasNextLine()) {	
                String readKey,readValue;
                String line = inputFile.nextLine();
                String[] parts = line.split(":");
                readKey = parts[0];
                readValue = parts[1];
                myData.put(readKey,readValue);
                i++;
            }
            System.out.println(i +" entries imported");
        } catch (FileNotFoundException err) {
            //  HANDLE ANY FILE PROBLEMS HERE		
            System.out.println("Error: Input file not found");
            System.exit(1);
        }
    }

    public void load() {
        // load all saved entries from the binary file Vocab.dat into your data structure
	// continue if the file is not found (which is possible if the app is run for the first time)
        try{
            FileInputStream fis = new FileInputStream("Vocabs.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            myData = (HashMap) ois.readObject();
            System.out.println(myData.size()+" entries loaded.");
            ois.close();
            fis.close();
        }catch(Exception err){
            System.out.println("Entries not loaded from file");
        }
    }

    public void save() {
    // Save all entries from your data structure into the binary file Vocab.dat
        try{
            FileOutputStream fos = new FileOutputStream("Vocabs.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(myData);
            oos.close();
            fos.close();
            System.out.print("Record saved");
        }catch(IOException err){
            err.printStackTrace();
        }    
    }

    public static void main(String[] args) {
        Vocab vb = new Vocab();

        vb.load(); //load previously saved entries from the file Vocab.dat (if any)
        vb.handleCommands(); //handle the commands from the user 
        vb.save(); //save all entries back into the file Vocab.dat before exiting

    }
}
