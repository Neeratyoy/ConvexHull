import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConvexHull extends LinkedLists 
{
    JFrame frame;
    JPanel top,down;
    JButton buttonDraw,buttonRefresh;
    Panel drawPanel;
    Node head=new Node();
    
    public static void main()
    {
        ConvexHull gui = new ConvexHull();
        gui.go();
    } // end of main of class ConvexHull
    
    public void go()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,700);
        frame.setVisible(true);
        buttonDraw = new JButton("          Draw          ");
        buttonRefresh = new JButton("         Refresh        ");
        
        top=new JPanel();
        down=new JPanel();
        top.add(buttonDraw);
        top.add(buttonRefresh);
        //frame.getContentPane().add(BorderLayout.SOUTH,buttonRefresh);
        frame.getContentPane().add(BorderLayout.SOUTH,top);
        
        drawPanel = new Panel(); 
       
        frame.getContentPane().add(BorderLayout.CENTER,drawPanel);
        drawPanel.refresh();
        drawPanel.repaint();
        
        drawPanel.addMouseListener(new MyDrawPanel());
        
        buttonRefresh.addActionListener(new RefreshButton());
        buttonDraw.addActionListener(new DrawDiagram());
        
        frame.pack();
    } // end of method go() of class ConvexHull
    
    class RefreshButton implements ActionListener       // event for the 'Refresh' button
    {
        public void actionPerformed(ActionEvent e)
        {
            frame.getContentPane().add(BorderLayout.CENTER,drawPanel);
            head.next=null;
            head.count=0;
            drawPanel.refresh();
            drawPanel.repaint();
            System.out.println("Panel Refreshed");
        }
    } // end of class RefreshButton implements ActionListener
    
    class DrawDiagram implements ActionListener       // event for the 'Draw' button
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Drawing the ConvexHull Diagram for the given points");
            sortList(head);
            printList(head);
            formCircularList(head);
            System.out.println();
            printListReverse(head);
            drawPanel.draw();
            drawPanel.repaint();
            //drawPolygon();
        }
    } // end of class DrawDiagram implements ActionListener 
    
    class MyDrawPanel implements MouseListener          // event to get the mouse clicks
    {      
        public void mouseExited(MouseEvent e)
        {}

        public void mousePressed(MouseEvent e)
        {}
        
        public void mouseEntered(MouseEvent e)
        {}
        
        public void mouseReleased(MouseEvent e) 
        {}
  
        public void mouseClicked(MouseEvent e) 
        {
            switch(e.getModifiers()) 
            {
                case InputEvent.BUTTON1_MASK: 
                {
                    int a=e.getX(),b=e.getY();
                    
                    if(addNode(head,a,b)==1)
                    {
                        drawPanel.plot(a,b);
                        drawPanel.repaint();
                        System.out.println("Point Plotted : ("+a+","+b+")");
                    }
                    break;
                }
                case InputEvent.BUTTON3_MASK: 
                {
                    //System.out.println("That's the RIGHT button");
                    int a=e.getX(),b=e.getY();
                    if((searchAndRemove(head,a,b))==1)
                    {
                        System.out.println("Point Deleted : ("+a+","+b+")");
                        drawPanel.undo(a,b);
                        drawPanel.repaint();                 
                    }
                    break;
                }
            }
        }
    } // end of class MyDrawPanel implements MouseListener
    
    class Panel extends JPanel                 // refreshing the panel and plotting points
    {
        int refresh=0,panel=1,undo=0,draw=0,x,y;
        
        public void refresh()
        {
            refresh=1;
            panel=0;
            undo=0;
            draw=0;
        }
        
        public void plot(int a,int b)
        {
            refresh=0;
            panel=1;
            undo=0;
            draw=0;
            x=a;
            y=b;
        }
        
        public void draw()
        {
            refresh=0;
            panel=1;
            draw=1;
            undo=0;
        }
        
        public void undo(int a,int b)
        {
            refresh=0;
            panel=1;
            undo=1;
            draw=0;
            x=a;
            y=b;
        }
        
        public void paintComponent(Graphics g)
        {
            if(refresh==1)
            {
                g.setColor(Color.white);
                g.fillRect(0,0,this.getWidth(),this.getHeight());
                System.out.print("\f");
            }
            else if(draw==1)
            {
                Draw d=new Draw();
                d.phase1(head);
                d.phase2(head);
                if(d.count1>1)
                {
                    for(int i=1;i<d.count1;i++){
                        g.drawLine(d.stack1[i-1].getX(),d.stack1[i-1].getY(),d.stack1[i].getX(),d.stack1[i].getY());
                        System.out.println(d.stack1[i-1].getX()+","+d.stack1[i-1].getY()+"    "+d.stack1[i].getX()+","+d.stack1[i].getY());
                    }
                }
                if(d.count2>1)
                {
                    for(int i=1;i<d.count2;i++){
                        g.drawLine(d.stack2[i-1].getX(),d.stack2[i-1].getY(),d.stack2[i].getX(),d.stack2[i].getY());
                        System.out.println(d.stack2[i-1].getX()+","+d.stack2[i-1].getY()+"    "+d.stack2[i].getX()+","+d.stack2[i].getY());
                    }
                }
                
            }
            else if(undo==0)
            {
                g.setColor(Color.black);
                g.fillOval(x,y,5,5);
            }
            else
            {
                g.setColor(Color.white);
                g.fillRect(x,y,10,10);
            }
            //g.drawLine(100,200,700,1000);
        }
        
    }//end of class Panel
} // end of class ConvexHull


class Draw
{
    Node[] stack1,stack2;
    int count1,count2;
    
    int turn(Node a,Node b,Node c)
    {
        return (((c.getY()-b.getY())*(b.getX()-a.getX()))-((b.getY()-a.getY())*(c.getX()-b.getX())));
    }
    
    int leftTurn(Node a,Node b,Node c)
    {
        if(turn(a,b,c)<0)
            return 1;
        return 0;
    }
    
    void phase1(Node head)
    {
        count1=0;
        stack1=new Node[head.count];
        Node p=head.next;
        if(head.count!=0)
            stack1[count1++]=p;
        else
            return;
        p=p.next;
        while(p!=head)
        {
            if(stack1[count1-1].getX()==p.getX() && stack1[count1-1].getY()==p.getY())
                continue;
            else
            {
                stack1[count1++]=p;
                if(count1<2)
                    continue;
                else
                {
                    int i=count1-1;
                    while(i>1 && leftTurn(stack1[i-2],stack1[i-1],stack1[i])==1)
                    {
                        if(leftTurn(stack1[i-2],stack1[i-1],stack1[i])==1)
                        {
                                Node t=stack1[i];
                                stack1[i]=null;      --i;    --count1;
                                stack1[i]=t;  
                        }
                    }
                }
            }
            p=p.next;
        }
        System.out.println();
        for(int i=0;i<count1;i++)
        {
                  System.out.println(stack1[i].getX()+","+stack1[i].getY());
        }
    }        
        
    void phase2(Node head)
    {
        count2=0;
        stack2=new Node[head.count];
        Node p=head.previous;
        if(head.count!=0)
            stack2[count2++]=p;
        else
            return;
        p=p.previous;
        while(p!=head)
        {
            if(stack2[count2-1].getX()==p.getX() && stack2[count2-1].getY()==p.getY())
                continue;
            else
            {
                stack2[count2++]=p;
                if(count2<2)
                    continue;
                else
                {
                    int i=count2-1;
                    while(i>1 && leftTurn(stack2[i-2],stack2[i-1],stack2[i])==1)
                    {
                        if(leftTurn(stack2[i-2],stack2[i-1],stack2[i])==1)
                        {
                            Node t=stack2[i];
                            stack2[i]=null;      --i;    --count2;
                            stack2[i]=t;  
                        }
                    }
                }
            }
            p=p.previous;
        }
        System.out.println();
        for(int i=0;i<count2;i++)
        {
            System.out.println(stack2[i].getX()+","+stack2[i].getY());
        }   
    }
}
