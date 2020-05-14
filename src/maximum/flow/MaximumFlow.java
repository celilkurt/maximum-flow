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
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MaximumFlow {

    public static HashMap<String, Node> nodes;
    public static HashMap<Node,HashMap<Node, Integer>> curCapacities;
    public static Node sourceNode;
    public static Node targetNode;
    private static boolean isGraph = false;
    public static int totalCapacity;
    
    
    public static void main(String[] args) {
        nodes = new HashMap<>();
        curCapacities = new HashMap<>();
        
        
        
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
            
            sourceNode = getOrCreateNode(words[1]);
            return "Giriş node'u kayıt edildi.";
            
        }else if(words[0].equals("@")){
            
            targetNode = getOrCreateNode(words[1]);
            return "Çıkış node'u kayıt edildi.";
            
        }else if(words.length%2 == 1){
            
            Node root = getOrCreateNode(words[0]);
            for(int i = 1; i < words.length; i+=2){
                
                Node newNode = getOrCreateNode(words[i]);
                int capacity = Integer.valueOf(words[i+1]);
                root.addNode(newNode, capacity);//newNode daha önce eklenmediyse ekler eklendiyse kapasitesini günceller.
                //newNode.addNode(root,capacity);
                root.printChildAndCapacity();//root'un bütün komşularını kapasiteleri ile yazdırır.
                
            }
            return "Graph düzenlendi.";
        
        }else{
            return "Geçersiz komut";
        }
        
        
    }
    
    
    public static void clearGraph(){
        totalCapacity = 0;
        sourceNode = null;
        targetNode = null;
        isGraph = false;
        nodes.clear();
        
    }
    
    public static boolean checkGraph(){
        
        return sourceNode != null && targetNode != null;
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
    
    public void calMaxFlow(){//calculate maximum flow
        
        defCurCapacities();
        findMaxCapacity(sourceNode,Integer.MAX_VALUE);
        
        
        
    }
    
    public static int findMaxCapacity(Node tempRoot,int minCapacity){
        
        if(tempRoot == targetNode)//hedefe ulaşıldıysa.
        {
            totalCapacity += minCapacity;
            return minCapacity;
        }else if(tempRoot.getChildList().isEmpty())//Eğer çıkmaz sokak ise
            return 0;
        
        int spentCapacity = 0;
        for(Node node: tempRoot.getChildList()){
            
            int curCapacity = tempRoot.getCapacities().get(node);
            int tempMin;
            
            if(curCapacity< minCapacity){
                tempMin =  findMaxCapacity(node,curCapacity);
                
            }else{
                tempMin = findMaxCapacity(node,minCapacity);
                
            }
            tempRoot.getCapacities().put(node,curCapacity-tempMin);
            spentCapacity += tempMin;
            minCapacity -= tempMin;
            //minCapacity den tempMin'i çıkar eğer minCapacity 0 dan büyükse işlme devam et değilse işlemi bitir.
            //Bu arada döndürülecek değeri de geçici bir değişkende biriktirmek gerek bir üstteki borudan çıkarabilmek için.
            
        }
        
       return spentCapacity; 
    }
    
    public void defCurCapacities(){
        
        nodes.values().forEach((Node node) -> {
            curCapacities.put(node, (HashMap<Node,Integer>)node.getCapacities().clone());
        });
        //for(Node node: nodes.values())
        //    curCapacities.put(node, (HashMap<Node,Integer>)node.getCapacities().clone());
             
    }
    
}
