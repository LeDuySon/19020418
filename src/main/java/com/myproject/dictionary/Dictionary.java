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
    
    public Dictionary(){
    }
    
    public void setList(Word w) {
        this.list.add(w);
    }

    public List<Word> getList() {
        return this.list;
    }
}
