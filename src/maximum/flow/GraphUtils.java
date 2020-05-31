

package maximum.flow;

import maximum.flow.entity.Node;
import java.util.ArrayList;
import java.util.HashMap;


public  class GraphUtils {
    
    public static Node sourceNode;
    public static Node targetNode;
    public static boolean isGraph = false;
    public static int totalCapacity;
    public static int totalCut = 0;
    public static HashMap<String, Node> nodes;
    
    public static Node getOrCreateNode(String label){
        
        if(nodes.containsKey(label))
            return nodes.get(label);
        else{
            Node newNode = new Node(label);
            nodes.put(label, newNode);
            return newNode;
        }
    }
    
    public static String createGraph(String str){
        
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
                root.addNodeToBackup(newNode,capacity);
                
            }
            root.printChildAndCapacity();//root'un bütün komşularını kapasiteleri ile yazdırır.
            return "Graph düzenlendi.";
        
        }else{
            return "Geçersiz komut";
        }
    }
    
    
    public static void clearGraph(){
        
        totalCut = 0;
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
    
    private static String message = "";
    
    public static String calMinCut(){
        
        message = "Kesilmesi gereken bağlantılar: "; 
        findMinCut(sourceNode,new ArrayList<>());
        message += "\nMinimum " +  String.valueOf(totalCut) + " kapasiteli kesim yapılmalı.";
        return message;
    }
    
    public static void findMinCut(Node tempRoot, ArrayList<Node> blackList){
        
        blackList.add(tempRoot);
        
        for(Node node: tempRoot.getCapacities().keySet()){
            
            if(tempRoot.getCapacities().get(node) == 0){
                
                totalCut += tempRoot.getCapacitiesBackup().get(node);
                tempRoot.getCapacitiesBackup().put(node, 0);
                message += tempRoot.getLabel() + "-" + node.getLabel() + "  ";
                resetCapacities();
                findMaxCapacity(sourceNode,Integer.MAX_VALUE);
            }else if(!blackList.contains(node) && tempRoot.getSpentCapacity(node) != 0)
                findMinCut(node,blackList);
                
        }
        
    }
    
    
    public static void resetCapacities(){
        
        for(Node node: nodes.values()){
            
            for(Node tempNode: node.getChildList()){
                node.getCapacities().replace(tempNode, node.getCapacitiesBackup().get(tempNode));
            }
            
        }
        
    }
    
}
