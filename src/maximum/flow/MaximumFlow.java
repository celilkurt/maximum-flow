/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maximum.flow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MaximumFlow {

    public static HashMap<String, Node> nodes;
    public static Node startNode;
    public static Node finishNode;
    private static boolean isGraph = false;
    
    
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
                addNodes(str);
            }
            
            return "Dosya okundu.";
                
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MaximumFlow.class.getName()).log(Level.SEVERE, null, ex);
            return "Dosya okunamadı!";
        }
    }
    
    //Eğer label adında bir node varsa döndürür yoksa yeni bir node oluşturur bunu nodes collectionuna ekler ve döndürür.
    public static Node getOrCreateNode(String label){
        
        if(nodes.containsKey(label))
            return nodes.get(label);
        else{
            Node newNode = new Node(label);
            nodes.put(label, newNode);
            return newNode;
        }
    }
    
    public static String addNodes(String str){
        
        isGraph = false;
        String[] words = str.split(" ");
        
        if(words[0].equals("#")){
            startNode = getOrCreateNode(words[1]);
            
            return "Giriş node'u kayıt edildi.";
        }else if(words[0].equals("@")){
            finishNode = getOrCreateNode(words[1]);
            
            return "Çıkış node'u kayıt edildi.";
        }else if(words.length%2 == 1){
            
            Node root = getOrCreateNode(words[0]);
            
            for(int i = 1; i < words.length; i+=2){
                
                Node newNode = getOrCreateNode(words[i]);
                int capacity = Integer.valueOf(words[i+1]);
                
                root.addNode(newNode, capacity);//newNode daha önce eklenmediyse ekler eklendiyse kapasitesini günceller.
                newNode.addNode(root,capacity);
                
                root.printChildAndCapacity();//root'un bütün komşularını kapasiteleri ile yazdırır.
            }
            return "Graph düzenlendi.";
        }else{
            return "Geçersiz komut";
        }
        
        
    }
    
    
    public static void clearGraph(){
        startNode = null;
        finishNode = null;
        isGraph = false;
        nodes.clear();
        
    }
    
    public static boolean checkGraph(){
        
        return startNode != null && finishNode != null;
    }
    
    public static void checkGraph2(Node tempRoot,ArrayList<Node> path){
        
        ArrayList<Node> clonePath = (ArrayList<Node>)path.clone();
        clonePath.add(tempRoot);
        
        clonePath.forEach( (node) -> {
                System.out.println(node.getLabel());
            });
        
        System.out.println("");
        
        if(clonePath.size() == nodes.size()){
            System.out.println("---------------------------------\n" 
                    + "Graph kurallara uygun");
            isGraph = true;
            return;
        }else if(isGraph)
            return;
        
        
        
        for(Node node: tempRoot.getChildList())
            if(!clonePath.contains(node)){
                checkGraph2(node,clonePath);
            }
                
        
        path.clear();
    }
    
}
