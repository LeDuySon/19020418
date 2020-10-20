/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myproject.dictionary;

//import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.beans.PropertyVetoException;
import java.util.Locale;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Voice;
/**
 *
 * @author son
 */
public class Text2Speech {
//    private static final String speaker = "kevin16";
//    
//    public void speakText(String text){
//        Voice voice;
//        System.out.println("haha");
//
//        VoiceManager vm = VoiceManager.getInstance();
//        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
//        voice = vm.getVoice("kevin16");
//        voice.allocate();
//        voice.speak("hello world");  
//        System.out.println("haha");
//    }
//    
//    public static void main(String[] args) {
//        Text2Speech test = new Text2Speech();
//        test.speakText("a");
//    }
    SynthesizerModeDesc desc;
    Synthesizer synthesizer;
    Voice voice;

    public void init(String voiceName) throws EngineException, AudioException, EngineStateError, PropertyVetoException {
        if (desc == null) {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            desc = new SynthesizerModeDesc(Locale.US);
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            synthesizer = Central.createSynthesizer(desc);
            synthesizer.allocate();
            synthesizer.resume();
            SynthesizerModeDesc smd = (SynthesizerModeDesc) synthesizer.getEngineModeDesc();
            Voice[] voices = smd.getVoices();
            for (Voice voice1 : voices) {
                if (voice1.getName().equals(voiceName)) {
                    voice = voice1;
                    break;
                }
            }
            synthesizer.getSynthesizerProperties().setVoice(voice);
        }
    }

    public void terminate() throws EngineException, EngineStateError {
        synthesizer.deallocate();
    }

    public void doSpeak(String speakText) throws EngineException, AudioException, IllegalArgumentException, InterruptedException {
        synthesizer.speakPlainText(speakText, null);
        synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
    }

    public static void main(String[] args) throws Exception {
        Text2Speech su = new Text2Speech();
        su.init("kevin16");

        su.doSpeak("Welcome to audio world.");
        su.terminate();
    }
}
