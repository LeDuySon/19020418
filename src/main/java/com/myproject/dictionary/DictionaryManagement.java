/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myproject.dictionary;


/**
 *
 * @author son
 */
import java.util.Scanner;
import java.io.File;  // Import the File class
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import com.myproject.dictionary.utils.*;
import java.util.stream.Collectors;
import java.util.regex.*;


// fuzzy seach
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;

public class DictionaryManagement {
    public Dictionary dict;
    private Utils utils = new Utils();
    private static final String fileName = "dictionaries.txt";
    
    public DictionaryManagement(){
        dict = new Dictionary();
    }
    public static String getFileName() {
        return fileName;
    }
    
    public String showAllWords(){
        String show = "";
        show += "No\t| English\t\t\t| Vietnamese";
        System.out.println(dict.getList().size());
        for(int i = 0; i < dict.getList().size(); i++){
            show += (i+1) + "\t| " + dict.getList().get(i).getWord_target();
            for(int j = 0; j < 30-dict.getList().get(i).getWord_target().length(); j++){
                show += " ";
            }
            show += "| "+dict.getList().get(i).getWord_explain();
            show += "\n";
            System.out.println(show);
        }
        System.out.println(show);
          
        return show;
    }

    public void insertFromCommandline() {
        System.out.println("Insert: ");
        Scanner sc = new Scanner(System.in);
        Word w = new Word();
        w.setWord_target(sc.nextLine());
        w.setWord_explain(sc.nextLine());
        dict.setList(w);
    }

    public void insertFromFile() {
        File info = new File(fileName);
        try {
            if (info.createNewFile()) {
                System.out.println("File created: " + info.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred when create file");
            e.printStackTrace();
        }

        try {
            Scanner myReader = new Scanner(info);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split("-_-");
                
//                System.out.println(data[1]);
//                break;
                
                String[] explainL = data[1].split("--splitinhere--");
                String rs = "";
                for(int i = 0; i < explainL.length; i++){
                    rs += explainL[i] + "\n";
                }
                dict.setList(new Word(data[0].trim(), rs));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred when trying to reading file");
            e.printStackTrace();
        }

    }

    public void dictionaryExportToFile() {
        File info = new File(fileName);
        try {
            if (info.createNewFile()) {
                System.out.println("File created: " + info.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred when create file");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter(fileName);
            PrintWriter pw = new PrintWriter(fileName);
            pw.close();
            String exp = "";
            for (int i = 0; i < dict.getList().size(); i++) {
                exp = "";
                myWriter.write(dict.getList().get(i).getWord_target() + "-_-");
                for(int j = 0; j < dict.getList().get(i).getWord_explain().split("\n").length; j++){
                    exp += dict.getList().get(i).getWord_explain().split("\n")[j] + "--splitinhere--";
                }
                exp += "\n";
                myWriter.write(exp);
            }
            myWriter.close();
            System.out.println("Successfully saved dictionary to file!");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String suggestSearch(String s) {
        int index = BinarySearch(s);
        if(index < 0){
            index = -index -1;
        }
        String rs = "";
        if(index >= 0){
            for(int i = index+1; i < dict.getList().size();i++){
                if(!dict.getList().get(i).getWord_target().startsWith(s)){
                    break;
                }
                rs += dict.getList().get(i).getWord_target();
                rs += "-_-";
            }
        }
        return rs;

    }
    
    public int BinarySearch(String keyWord) {
        keyWord = keyWord.replaceAll("[ ]+", " ");
        System.out.println(keyWord);
        int left = 0;
        int right = dict.getList().size() - 1;
        if(keyWord == null){
            return -1;
        }
        if(keyWord.equals("aba")){
            System.out.println("WTF????????????");
        }
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int compareResult = dict.getList().get(mid).getWord_target().compareTo(keyWord);
            if (compareResult == 0) {
                return mid;
            } else {
                if (compareResult < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        /* Because when return only -left or -right, if value return is 0, we dunno it is right meaning
         *  of key word (value return is higher or equal 0) or it is closet word with key word (value return
         *  is lower or equal 0) because of 0 == -0, so that i decrease value return when find a closet word
         *  by 1, to make it always lower than 0. Then when show closet result, we need to plus 1 to get a
         *  right closet word.h
         */
        if (left >= dict.getList().size()) return -right - 1;
        if (right < 0) return -left - 1;
        if (dict.getList().get(left).getWord_target().compareTo(keyWord) <= dict.getList().get(right).getWord_target().compareTo(keyWord))
        //if (distance(wordsList.get(left).getWordTarget(), keyWord) <= distance(wordsList.get(right).getWordTarget(), keyWord))
            return -left - 1;
        else return -right - 1;
    }
//    public int BinarySearch(String wordF) {
//        int lo = 0;
//        int hi = dict.getList().size() - 1;
//        while (lo <= hi) {
//            int mid = lo + (hi - lo) / 2;
//            int compareI = dict.getList().get(mid).getWord_target().compareTo(wordF);
//            if (compareI > 0) hi = mid - 1;
//            else if (compareI < 0) lo = mid + 1;
//            else return mid;
//        }
//        if(lo >= dict.getList().size()){
//            return -hi-1;
//        }else if(hi < 0){
//            return -lo-1;
//        }
//        return -hi-1;
//        
//    }

    public String dictionaryLookup(String s) {
//        for(int i = 0; i < dict.num_element; i++){
//            Word w = new Word();
//            w = dict.getList().get(i);
//            if(w.getWord_target().equals(s)){
//                return w.getWord_explain();
//            }
//        }
        
        int rs = BinarySearch(s);
        if (rs >= 0) {
            return dict.getList().get(rs).getWord_explain();
        }
        return "Not find";
    }
    
    public String fuzzySearch(String s){
        List<String> choice = dict.getList().stream().map(ele -> ele.getWord_target()).collect(Collectors.toList());
//        for(int i = 0; i < choice.size(); i++){
//            System.out.println(choice.get(i));
//        }
        List<ExtractedResult> match = FuzzySearch.extractTop(s, choice, 5);
        String rs = "";
        for(int i = 0; i < match.size(); i++){
            String cpr = match.get(i).getString();
            if(cpr.length() == s.length()){
                rs = cpr;
                break;
            }
        }
//        System.out.println(rs);
        return rs;
    }
    public String dictionarySearcher(String s){
        List <Pairs<String, Integer>> score = new ArrayList<Pairs<String, Integer>>();
        for (int i = 0; i < dict.getList().size(); i++){
            Pairs<String, Integer> p = new Pairs<String, Integer>();
            String s1 = dict.getList().get(i).getWord_target();
//            int rs = utils.calculate(s1, s);
            int lg = utils.LongestCommonSubString(s1, s);
//            int mergeRs = (2*lg + rs)/3;
            p.setFirst(s1);
            p.setSecond(lg);
            score.add(p);
        }
        Collections.sort(score, (a, b) -> Integer.compare(a.getSecond(), b.getSecond()));
        String rs = "";
        for(int i = score.size()-1; i >= 0 ; i--){
            rs += score.get(i).getFirst();
            rs += "-_-";
            
        }
        return rs;
    }

    public int editWord(String s, String s1) {
//        Scanner sc = new Scanner(System.in);

//        System.out.println("You want to edit: ");
//        System.out.println("1. Word Target");
//        System.out.println("2. Word Explain");
//        int choice = sc.nextInt();
//            System.out.println("What word you want to edit its explain?");
//            s = sc.next();
        int rs = BinarySearch(s);
        if (rs >= 0) {
            dict.getList().get(rs).setWord_explain(s1);
            return 1;
        } else {
            return -1;
        }
//        }else if(choice == 2){
//            String s;
//            System.out.println("What word you want to edit its target?");
//            s = sc.next();
//            int rs = BinarySearch(s);
//            if(rs != -1){
//                dict.getList().get(rs).setWord_target(sc.next());
//            }else{
//                System.out.println("Cant find word that you want to edit?");
//            }
//        }
    }
    
    public void addWord(String s1, String s2){
        dict.setList(new Word(s1, s2));      
        Collections.sort(dict.getList(), (a, b) -> a.getWord_target().compareTo(b.getWord_target()));
    }
    
    public void appendContent(String appendW, int index){
        String oldW = dict.getList().get(index).getWord_explain();
        String newW = oldW + "\n" + appendW;
//        System.out.println(newW);
        dict.getList().get(index).setWord_explain(newW);
    }
    public String removeWord(String s) {
        int rs = BinarySearch(s);
        if (rs != -1) {
            dict.getList().remove(rs);
            return "Removed!";
        } else {
            return "Word not exist";
        }
    }

}