package dcompiler.io; 

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileLoader { 
    
    public static String[] getResourceFile(String file) {
	Scanner scan;
	try {
	    scan = new Scanner(new FileReader(file));
	} catch (IOException e) {
	    return null;
	}	
	List<String> lines = new ArrayList<>();
	while (scan.hasNextLine()) {
	    lines.add(scan.nextLine());
	}
	return lines.toArray(new String[0]);
    }
    
} 

