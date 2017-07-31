
package com.razu.server;

import java.net.InetAddress;
import java.util.HashMap;


public class Data {
    
    static final int SERVER_PORT = 8282;
    static InetAddress SERVER_ADDRESS ;
    
    static {
        try {          
            SERVER_ADDRESS = InetAddress.getLocalHost();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    static HashMap<Integer, String> clientNameListByPort = new HashMap();
    static HashMap<String, Integer> clientPortListByName = new HashMap();
   
    
    
}
