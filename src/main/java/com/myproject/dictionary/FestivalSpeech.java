/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myproject.dictionary;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ProcessBuilder;
/**
 *
 * @author son
 */
public class FestivalSpeech {
    private static final String file = "voice.txt";
    public void filexec(String s){
        try {
            FileWriter myWriter = new FileWriter(file);
//            PrintWriter pw = new PrintWriter(file);
//            pw.close();
            myWriter.write(s);
            myWriter.close();
            System.out.println("Successfully saved!");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
    public void speak(String s) throws IOException{
        filexec(s);
        String speaker = "festival --tts " + file;
        String[] args = new String[] {"/bin/bash", "-c", speaker, "with", "args"};
        Process proc = new ProcessBuilder(args).start();  
    }
    
//    public static void main(String[] args) throws IOException {
//        FestivalSpeech t = new FestivalSpeech();
//        t.speak("i love you");
//    }
}
