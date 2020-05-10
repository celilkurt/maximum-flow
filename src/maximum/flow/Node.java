

package maximum.flow;

import java.util.ArrayList;
import java.util.HashMap;


public class Node {
    
    private ArrayList<Node> childList;
    private HashMap<Node, Integer> capacities;
    private String label;
    
    public Node(String label){
        this.label = label;
        childList = new ArrayList<>();
        capacities = new HashMap<>();
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
    
    public void printChildAndCapacity(){
        
        childList.forEach((node) -> {
            System.out.println(label + " nın " + node.getLabel() + " ile " + capacities.get(node) + " kapasitede ilişkisi var.");
        });
    }
}
