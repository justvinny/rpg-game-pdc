/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.observer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public abstract class CustomObservable {
    
    protected List<CustomObserver> observers;
    
    public CustomObservable() {
        observers = new ArrayList<>();
    }
    
    public abstract void addObserver(CustomObserver observer);
    public abstract void removeObserver(CustomObserver observer);
    public abstract void notifyObservers();
}
