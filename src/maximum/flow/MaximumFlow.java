/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maximum.flow;

import maximum.flow.graphics.GUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static maximum.flow.GraphUtils.*;

public class MaximumFlow {

    
    
    
    
    public static void main(String[] args) {
        
        nodes = new HashMap<>();
        GUI myGUI = new GUI();
        myGUI.setVisible(true);
        
        
    }
    
    public static String readFile(String fileName){
        
        try {
            
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            
            while (sc.hasNextLine()){
                
                String str = sc.nextLine();
                System.out.println(str);
                createGraph(str);
            }
            
            return "Dosya okundu.";
                
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MaximumFlow.class.getName()).log(Level.SEVERE, null, ex);
            return "Dosya okunamadı!";
        }
    }
    
    //Eğer label adında bir node varsa döndürür yoksa yeni bir node oluşturur bunu nodes collectionuna ekler ve döndürür.
    
    
    
    
}
