package one;

import utils.TimeHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class BruteForceStringMatch {

    public static void main(String args[]) throws IOException {
        TimeHelper timeHelper = new TimeHelper();
        if (args.length != 2) {
			System.out.println("BruteForceStringMatch  <file name> <pattern>");
			System.exit(1);
        }

        String fileString = new String(Files.readAllBytes(Paths.get(args[0])));//, StandardCharsets.UTF_8);
        char[] text = new char[fileString.length()]; 
        int n = fileString.length();
        for (int i = 0; i < n; i++) { 
            text[i] = fileString.charAt(i); 
        } 
 
        String patternString = new String(args[1]);                                                
        char[] pattern = new char[patternString.length()]; 
        int m = patternString.length(); 
        for (int i = 0; i < m; i++) { 
            pattern[i] = patternString.charAt(i); 
        }

        int matchLength = n - m;
        char[] match = new char[matchLength+1]; 
        for (int i = 0; i <= matchLength; i++) { 
            match[i] = '0'; 
        }

        timeHelper.start();

        /*ArrayList<Integer> match = new ArrayList<Integer>(); */
        int matchCount = 0;
        for (int j = 0; j < matchLength; j++) {
			int i;
      		for (i = 0; i < m && pattern[i] == text[i + j]; i++);
				if (i >= m) {
	         		match[j] = '1';
					matchCount++;
                }        
        }


        for (int i = 0; i < matchLength; i++) { 
            if (match[i] == '1') System.out.print(i+" ");
        }

        timeHelper.end();

        System.out.println();
        System.out.println("Total matches " + matchCount);
        System.out.println("time " + timeHelper.getTime());
        
   }
}



