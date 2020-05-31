

package maximum.flow.entity;

import java.util.ArrayList;
import java.util.HashMap;


public class Node {
    
    private ArrayList<Node> childList;
    private HashMap<Node, Integer> capacities;
    private HashMap<Node, Integer> capacitiesBackup;
    private String label;
    
    public Node(String label){
        this.label = label;
        childList = new ArrayList<>();
        capacities = new HashMap<>();
        capacitiesBackup = new HashMap<>();
    }

    public ArrayList<Node> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<Node> childList) {
        this.childList = childList;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public HashMap<Node, Integer> getCapacities() {
        return capacities;
    }

    public void setCapacities(HashMap<Node, Integer> capacities) {
        this.capacities = capacities;
    }
    
    public void addNode(Node newNode,int capacity){
        
        if(!childList.contains(newNode)){
            childList.add(newNode);
            capacities.put(newNode,capacity);
        }else{
            capacities.replace(newNode, capacity);
        }
    }
    
    public void addNodeToBackup(Node newNode,int capacity){
        
        if(!capacitiesBackup.containsKey(newNode)){
            capacitiesBackup.put(newNode,capacity);
        }else{
            capacitiesBackup.replace(newNode, capacity);
        }
    }

    public HashMap<Node, Integer> getCapacitiesBackup() {
        return capacitiesBackup;
    }

    public void setCapacitiesBackup(HashMap<Node, Integer> capacitiesBackup) {
        this.capacitiesBackup = capacitiesBackup;
    }
    
    public int getSpentCapacity(Node node){
        
        if(capacities.containsKey(node) && capacitiesBackup.containsKey(node))
            return capacitiesBackup.get(node) - capacities.get(node);
        
        return 0;
        
    }
    
    
   
    public void printChildAndCapacity(){
        
        childList.forEach((node) -> {
            System.out.println(label + " nın " + node.getLabel() + " ile " + capacities.get(node) + " kapasitede ilişkisi var.");
        });
    }
}
