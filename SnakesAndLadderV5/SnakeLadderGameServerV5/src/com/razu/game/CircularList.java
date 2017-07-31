
package com.razu.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CircularList {
    private HashMap<String, String> map;
    private List<String> list;
    
    public CircularList(List<String> list) {
        this.list = list;
        map = makeTheListCircular(list);
    }
    
    private HashMap<String, String> makeTheListCircular(List<String> list){
        HashMap<String, String> mapHere = new HashMap();
        int size = list.size();        
        int nextIndex = 1;
        int lastIndex = size - 1;
        
        for(String element : list){
            String nextElement = list.get(nextIndex);
            mapHere.put(element, nextElement);
            
            if(nextIndex == lastIndex)
                nextIndex = 0;
            else 
                nextIndex++;
        }      
        return mapHere;
    }
    
    public HashMap<String, String> remove(String element){
        this.list.remove(element);
        return map=makeTheListCircular(this.list);
    }

    public HashMap<String, String> getMap() {
        return map;
    }
    
    
    
    public static void main(String[] arg){
        List<String> listHere = new ArrayList();
        for(int i=1; i<=4; i++){
            listHere.add("e"+i);
        }
        
        CircularList circularList = new CircularList(listHere);
        System.out.println(circularList.map);
        
        circularList.remove("e3");
        System.out.println(circularList.map);
    }
    
}
