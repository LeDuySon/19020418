    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myproject.dictionary.utils;

/**
 *
 * @author son
 */

public class Pairs<X, Y> {
    private X x;
    private Y y;

    public Pairs(){

    }

    public Pairs(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getFirst() {
        return x;
    }

    public void setFirst(X x) {
        this.x = x;
    }

    public Y getSecond() {
        return y;
    }

    public void setSecond(Y y) {
        this.y = y;
    }
}
