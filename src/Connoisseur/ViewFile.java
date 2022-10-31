package Connoisseur;

/* Source: https://stackoverflow.com/questions/16952727/how-to-use-readattributes-method 
 * Justin Thompson
 */

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class ViewFile {
	
	private static void FileAttributes(){
		Scanner userIn = new Scanner(System.in);
    	String pathname;
    	ArrayList<String> mdata = new ArrayList<String>();
        BasicFileAttributes attr;
        
        System.out.println("Insert file path: ");
        pathname = userIn.nextLine();
        userIn.close();
        
        Path path = Paths.get(pathname);
        try {
         attr = Files.readAttributes(path, BasicFileAttributes.class);
         mdata.add(attr.creationTime() + "");
         mdata.add(attr.lastAccessTime() + "");
         mdata.add(attr.lastModifiedTime() + "");
         mdata.add(attr.size() + " bytesT");
         System.out.println(mdata);
         
         for (int i = 0; i < mdata.size(); i++) {
        	 String s = mdata.get(i);
        	 String t = s.substring(0, s.indexOf("T"));
        	 mdata.set(i, t);
        	 System.out.println(mdata.get(i));
         }
        } catch (IOException e) {
         System.out.println("Invalid file path: " + e.getMessage());
        }
	}
    public static void main(String[] args) throws IOException {
    	FileAttributes();
    }
}