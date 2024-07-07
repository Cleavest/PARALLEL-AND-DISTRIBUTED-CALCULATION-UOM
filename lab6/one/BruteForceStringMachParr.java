package one;

import utils.TimeHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cleavest on 13/4/2024
 */
public class BruteForceStringMachParr {
    public static void main(String args[]) throws IOException {
        TimeHelper timeHelper = new TimeHelper();
        int numThreads = Runtime.getRuntime().availableProcessors();

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

        timeHelper.start();

        StringMachThread[] threads = new StringMachThread[numThreads];

        for (int i = 0; i < numThreads; i++)
        {
            threads[i] = new StringMachThread(i, numThreads, matchLength,text, pattern, m);
            threads[i].start();
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
                list.addAll(threads[i].getMatchList());
            } catch (InterruptedException e) {}
        }

        list.forEach(i -> System.out.print(i+" "));

        timeHelper.end();

        System.out.println();
        System.out.println("Total matches " + list.size());
        System.out.println("time " + timeHelper.getTime());

    }
}

class StringMachThread extends Thread {

    private int myId;
    private int myStart;
    private int myStop;
    private char[] text;
    private char[] pattern;
    private int patternLength;
    private List<Integer> matchList;

    public StringMachThread(int id, int numThreads, long size, char[] text, char[] pattern, int patternLength) {
        this.text = text;
        this.pattern = pattern;
        this.patternLength = patternLength;
        this.matchList = new ArrayList<>();
        myId = id;
        myStart = (int) (myId * (size / numThreads));
        myStop = (int) (myStart + (size / numThreads));
        if (myId == (numThreads - 1)) myStop = (int) size;
    }

    @Override
    public void run() {
        for (int j = myStart; j < myStop; j++) {
            int i;
            for (i = 0; i < patternLength && pattern[i] == text[i + j]; i++);
            if (i >= patternLength) {
                matchList.add(j);
            }
        }
    }

    public List<Integer> getMatchList() {
        return matchList;
    }
}
