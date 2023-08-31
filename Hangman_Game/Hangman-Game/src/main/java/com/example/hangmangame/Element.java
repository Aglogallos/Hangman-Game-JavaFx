package com.example.hangmangame;

class Element implements Comparable<Element> {

    int position;
    int letter;
    int quantity;

    // Constructor

    Element(int position, int letter, int quantity)
    {
        this.position = position;
        this.letter = letter;
        this.quantity = quantity;
    }

    public int compareTo(Element e ){
        return this.quantity - e.quantity;
    }
}

