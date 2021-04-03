package com.group.pdc_assignment_rpg.utilities;

import java.util.HashMap;

/**
 * Accepts one or more Key/Value pairs for a hash map.
 * To use: Set map's value to BulkHashmapUtility constructor
 * with key value pairs the same as you would put to a hash map.
 * Example: Map<String, String> testMap = BulkHashmapUtility(
 * "Maca", "Uley", "Vin", "Son", "Jess", "Ica"); would make something
 * like {Maca:Uley, Vin:Son, Jess:Ica} key:value.
 * 
 * @author Macauley Cunningham - 19072621
 *
 */
@SuppressWarnings("serial")
public class BulkHashmapUtility extends HashMap<String,String> {

    public BulkHashmapUtility(String...KeyValuePairs) {
        super(KeyValuePairs.length/2);
        
        for(int i=0; i<KeyValuePairs.length; i+=2)
            put(KeyValuePairs[i], KeyValuePairs[i+1]);
    }
}
