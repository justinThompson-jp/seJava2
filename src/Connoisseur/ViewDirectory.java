package Connoisseur;

/* Source: pathnames class from 
 *		   https://stackabuse.com/java-list-files-in-a-directory/ 
 *	@author: Justin Thompson
 *	Edited by Jacob Crawford
 */

//import java.util.Scanner;
import java.io.*;

public class ViewDirectory {
	
	static int dirLength;
	static String[] pathnames = null; // Creates an array in which we will store the names of files and directories
	
	public void Directory(String _path) {
		
		String path = _path;
		
        // Create new File instance by converting the given pathname string into an abstract pathname
        File f = new File(path);
        
        // Populate array with names of files and directories
        pathnames = f.list();

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