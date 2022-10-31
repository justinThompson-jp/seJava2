package Connoisseur;

/* Source: pathnames class from 
			https://stackabuse.com/java-list-files-in-a-directory/ 
	Justin Thompson
*/

import java.util.Scanner;
import java.io.*;

public class ViewDirectory {
	
	static int dirLength;
	static String[] pathnames = null; // Creates an array in which we will store the names of files and directories
	
	public void Directory() {
		
        Scanner userIn = new Scanner(System.in);
		String path;
		
		while (pathnames == null) {
			System.out.println("Insert directory path: ");
			path = "C:\\";
			
	        // Create new File instance by converting the given pathname string into an abstract pathname
	        File f = new File(path);
	        
	        // Populate array with names of files and directories
	        pathnames = f.list();
        }
        userIn.close();
        
        // For each pathname in the pathnames array print the names of files and directories
        for (String pathname : pathnames) {
            System.out.println(pathname);
        }	
        dirLength = pathnames.length;
	}
/*
	public static void main(String[] args) {
		Directory();
	}
*/
}