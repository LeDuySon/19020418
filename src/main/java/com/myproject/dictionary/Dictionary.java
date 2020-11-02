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
import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private List<Word> list = new ArrayList<Word>();
    private int num_word;
    
    public Dictionary(){
        num_word = 0;
    }

    public int getNum_word() {
        return num_word;
    }
    
    
    
    public void setList(Word w) {
        num_word++;
        this.list.add(w);
    }

    public List<Word> getList() {
        return this.list;
    }
}
