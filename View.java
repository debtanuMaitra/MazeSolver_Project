/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;
 
import Algotithms.DFS;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
 
/**
 *
 * @author Debtanu Maitra
 */
public class View extends JFrame implements ActionListener, MouseListener{
 
    /**
     * Values : 0 = not visited
     *          1 = blocked wall
     *          2 = visited
     *          9 = target
     */
 
 
    private int[][] maze = 
        {    
        {1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,1,0,1,0,1,0,0,0,0,0,1},
        {1,0,1,0,0,0,1,0,1,1,1,0,1},
        {1,0,0,0,1,1,1,0,0,0,0,0,1},
        {1,0,1,0,0,0,0,0,1,1,1,0,1},
        {1,0,1,0,1,1,1,0,1,0,0,0,1},
        {1,0,1,0,1,0,0,0,1,1,1,0,1},
        {1,0,1,0,1,1,1,0,1,0,1,0,1},
        {1,0,0,0,0,0,0,0,0,0,1,9,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1}
    };
 
    private int[] target = {8, 11}; //It is the target i.e. 9 in the maze above
    private List<Integer> path = new ArrayList<Integer>();
 
    JButton submitButton;
    JButton cancelButton;
    JButton clearButton;
 
    public View(){
        this.setTitle("Maze Solver"); //Set title of the GUI
        this.setSize(520,500); //Set size of the GUI
        this.setLayout(null); //Set background
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Upon clicking 'X', it closes the GUI
        this.setLocationRelativeTo(null); //Appear in the middle of the window upon start
        
        //Submit Button
        submitButton = new JButton("Submit"); 
        submitButton.addActionListener(this); //Adding action listener to button
        submitButton.setBounds(120, 400, 80, 30); //Button position and size
        
        //Clear Button
        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        clearButton.setBounds(200, 400, 80, 30);
 
        //Cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        cancelButton.setBounds(280, 400, 80, 30);
 
        this.addMouseListener(this); //Adding mouse listener so that we can change the ending position by clicking anywhere in the maze
 
        this.add(submitButton); //Adding button to GUI
        this.add(clearButton);
        this.add(cancelButton);
    }
 
    @Override
    public void paint(Graphics g){
        super.paint(g);
        for(int row=0; row<maze.length; row++){
            for(int col=0; col<maze[0].length; col++){
                Color color;
                switch(maze[row][col]){
                    case 1 : color = Color.BLACK; break;
                    case 9 : color = Color.RED; break;
                    default : color = Color.WHITE; break;
                }
                //To color the maze by black, white and ending with red
                g.setColor(color);
                g.fillRect(40*col, 40*row, 40, 40);
                
                //To color the rectangular boarder with black
                g.setColor(Color.BLACK);
                g.drawRect(40*col, 40*row, 40, 40);
            }
        }
        
        //We iterate the path in the interval of 2, 1st is X and 2nd is Y for every interval
        for(int p=0; p<path.size(); p+=2){
            int pathX = path.get(p);
            int pathY = path.get(p+1);
            
            //To color the path in green
            g.setColor(Color.GREEN);
            g.fillRect(40*pathY, 40*pathX, 40, 40);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == submitButton){
            try{
                /*When we click the submit button, the following function is called
                and path is created
                */ 
                DFS.searchPath(maze, 1, 1, path);
                this.repaint();
            }
            catch(Exception excp){
                JOptionPane.showMessageDialog(null, excp.toString());
            }
        }
 
        if(e.getSource() == cancelButton){
            int flag = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit", "Submit", JOptionPane.YES_NO_OPTION);
            //If we click 'YES' in the dialog box, it will return 0 to this flag variable, otherwise return 1 for clicking 'NO'
            if(flag == 0){
                System.exit(0);
            }
        }
 
        if(e.getSource() == clearButton){
            path.clear(); //Clear the path array
            //Change the 2's again to 0's. So again the path can be found
            for(int row=0; row<maze.length; row++){
                for(int col=0; col<maze[0].length; col++){
                    if(maze[row][col]==2){
                        maze[row][col]=0;
                    }
                }
            }
            this.repaint(); //This will clear the green path
        }
    }
 
    @Override
    public void mouseClicked(MouseEvent e){
        //We can change the target by folloeing this code
        if(e.getX()>=0 && e.getX()<=maze[0].length*40 && e.getY()>=0 && e.getY()<=maze.length*40){
            int row = e.getY()/40;
            int col = e.getX()/40;
 
            if(maze[row][col]==1){
                return;
            }
            Graphics g = getGraphics();
            //Change the previous target color to White
            g.setColor(Color.WHITE);            
            g.fillRect(40*target[1], 40*target[0], 40, 40);
            //Make the new target box to RED
            g.setColor(Color.red);
            g.fillRect(col*40, row*40, 40, 40);
            maze[target[0]][target[1]] = 0; // Change the previous target to 0
            maze[row][col] = 9; // Change the new target to 9
            //Change the target row and coloumn
            target[0] =row;
            target[1] =col;
        }
    }
 
    @Override
    public void mousePressed(MouseEvent e){
 
    }
 
    @Override
    public void mouseReleased(MouseEvent e){
 
    }
 
    @Override
    public void mouseEntered(MouseEvent e){
 
    }
 
    @Override
    public void mouseExited(MouseEvent e){
 
    }
 
    public static void main(String[] args){
        View gui = new View();
        gui.setVisible(true); //By default, the JFrame is set to be in invisible. So, we have to set the visi
    }
}
