/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maximum.flow;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author asus
 */
public class GraphGraphic extends javax.swing.JFrame {

    /**
     * Creates new form GraphGraphic
     */
    public GraphGraphic() {
        initComponents();
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(new Color(0,85,0));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        paintNodes(g,MaximumFlow.startNode,new ArrayList<>(),100,getHeight()/2,100);
        paintRelations(g,MaximumFlow.startNode,new ArrayList<>());
        //g.setColor(Color.red);
        //g.fillOval(20, 50, 100, 100);
        
        
    }
    
    //Coordinat map
    private HashMap<Node,Coordinat> coorMap = new HashMap<>();
    
    
    public void paintNodes(Graphics g,Node tempRoot,ArrayList<Node> blackList, int x, int y, int nodeDistance){
        
        blackList.add(tempRoot);
        
        if(!coorMap.containsKey(tempRoot)){
            
            drawNode(g,x,y,25,25,tempRoot.getLabel());
            coorMap.put(tempRoot, new Coordinat(x,y));
        }
        x += nodeDistance;
        
        int count = 0;
        for(Node node: tempRoot.getChildList())
            if(!coorMap.containsKey(node))
                count++;
        
        y -= ((count - 1)*nodeDistance)/2;
        
        
        for(Node node:tempRoot.getChildList())
            if(!coorMap.containsKey(node)){
                
                
                drawNode(g,x,y,25,25,node.getLabel());
                coorMap.put(node, new Coordinat(x,y));
                
                y += nodeDistance;
            }
        
        for(Node node: tempRoot.getChildList())
            if(!blackList.contains(node)){
                paintNodes(g,node,blackList,x,getHeight()/2,nodeDistance);
            }
                
            
        
    }
    
    public void drawNode(Graphics g,int x, int y,int width, int height,String label){
    
        g.setColor(Color.red);
        g.fillOval(x, y, width, height);
        g.setColor(Color.white);
        g.drawString(label, x+width/2, y+height/2);
    
    }
    
    public void paintRelations(Graphics g,Node tempRoot,ArrayList<Node> blackList){
        
        Coordinat coor = coorMap.get(tempRoot);
        
        
        for(Node node:tempRoot.getChildList())
            if(!blackList.contains(node)){
            
                Coordinat coor2 = coorMap.get(node);
                g.setColor(Color.black);
                g.drawLine(coor.getX()+12, coor.getY()+12, coor2.getX()+ 12, coor2.getY()+12);

                g.setColor(Color.white);
                g.drawString(String.valueOf(tempRoot.getCapacities().get(node)), coor.getX()*2/3 + coor2.getX()*1/3 + 12 , coor.getY()*2/3 + coor2.getY()*1/3 + 12);
           
            }
        
        blackList.add(tempRoot);
            
        for(Node node: tempRoot.getChildList())
            if(!blackList.contains(node))
                 paintRelations(g,node,blackList);
        
        
    }
    
    public void updateGraphic(){
        
        
        revalidate();
        repaint();
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GraphGraphic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GraphGraphic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GraphGraphic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GraphGraphic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GraphGraphic().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
