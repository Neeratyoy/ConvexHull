/* class working with the list of points in the 2D space */
class LinkedLists                                   
{
    Node head=new Node();                           /* the "header" reference to the list */
    
    
    int addNode(Node head,int a,int b)                       /* adding a new point to the list, at the front (like a stack) */
    {
        if(search(head,a,b)==1)
            return 0;
        Node p=new Node(a,b);
        p.next=head.next;
        if(head.next!=null)
	        head.next.previous = p;
        Node q=new Node();
        q=p.next;
        head.next=p;
        p.previous=head;
        p=null;
        q=null;
        head.count++;
        return 1;
    }
    
    int search(Node head,int a,int b)               // function to return 1 or 0 after searching for it
    {
        Node p=head.next;
        while(p!=head && p!=null)
        {
            if(p.getX()==a && p.getY()==b)  
                return 1;
            p=p.next;
        }
        return 0;
    }
    
    void addNode(Node head,Node a)                       /* adding a new point to the list, at the front (like a stack) */
    {
        Node p=new Node(a.getX(),a.getY());
        p.next=head.next;
        Node q=p.next;
        head.next=p;
        p.previous=head;
        q.previous=p;
        p=null;
        q=null;
        head.count++;
    }
    
    int searchAndRemove(Node head,int a,int b)               // function to remove points from the list after searching for it, exchanging its data with the top object 
    {                                              // and then deleting the first object in the list 
        Node p=head.next,q=head.next;
        int flag=0;
        if(p.getX()>=a && p.getX()+5<=a+10)
        {
           if(p.getY()>=b && p.getY()+5<=b+10)
           {
               head.next=p.next;
               Node r=p.next;
               r.previous=head;
               head.count--;
               p=p.next;
               flag=1;
            }
        }
        q=p.next;
        while(q!=null)
        {
            if(q.getX()>=a && q.getX()<=a+10)
            {
                if(q.getY()>=b && q.getY()<=b+10)
                {
                    flag=1;
                    head.count--;
                    Node r=q.next;
                    r.previous=q.previous;
                    p.next=r;
                    q=q.next;
                }
                else
                {
                    q=q.next;
                    p=p.next;
                }
            }
            else
            {
                q=q.next;
                p=p.next;
            }
        }
        return flag;
    }
    
    Node pop(Node head)                                              // shifting the head reference by one in the forward direction along the list
    {
        Node p=head.next;
        if(p==null)
        {
            System.out.println("Underflow");
            System.exit(1);
        }
        else
        {
        //  System.out.println(p.getX()+","+p.getY());
            Node q=p.next;
            q.previous=head;
            head.next=p.next;
            head.count--;
            return p;
        }
        return p;
    }
    
    void printList(Node head)                                        // printing the data contained by each object in the list
    {
        Node p=head.next;
        System.out.println(head.count);
        while(p!=null)
        {
            System.out.println(p.getX()+","+p.getY());
            p=p.next;
        }
    }
    
    void printListReverse(Node q)                                        // printing the data contained by each object in the list
    {
        Node p=q.previous;
        //System.out.println(head.count);
        while(p!=q)
        {
            System.out.println(p.getX()+","+p.getY());
            p=p.previous;
        }
    }
    
    void formCircularList(Node head)
    {
        Node p=head;
        while(p.next!=null)
            p=p.next;
        p.next=head;
        head.previous=p;
        //System.out.println(p.getX()+","+p.getY());
    }
    
    Node top(Node head)
    {
        return head.next;
    }
    
    void sortList(Node head)                                         // function to sort the list
    {
        Node p=head.next;
        int change=0;
        while(p!=null)
        {
            Node q=p.next,t=new Node();
            int min=p.getX();
            int a,b;
            while(q!=null)
            {
                if(q.getX()<min)
                {
                    min=q.getX();
                    t=q;
                    change=1;
                }
                else if(q.getX()==min)
                {
                    if(q.getY()<p.getY())
                    {
                        t=q;
                        change=1;
                    }
                }
                q=q.next;
            }
            if(change==1)
            {
                a=p.getX();
                b=p.getY();
                p.setData(t.getX(),t.getY());
                t.setData(a,b);
                change=0;
            }
            p=p.next;
        }
    }
} //end of class LinkedLists


/* class defining the properties of each point plotted which is an object */
class Node                      
{
    private int x,y;            // x and y co-ordinates of each point
    public int count;
    public Node next,previous;       // reference to the "previous" point in the list of points
    
    int getX()                                          /* getter function for the x xo-ordinate*/
    {
        return x;
    }
    
    int getY()                                          /* getter function for the y co-ordinate */
    {
        return y;
    }
    
    void setData(int a,int b)                           /* setter function for both the x and y co-ordinates */
    {
        x=a;
        y=b;
    }
    
    Node()                                              /* default constructor */
    {
        count=0;
        next=null;
        previous=null;
    }
    
    Node(int a,int b)                                   /* parameterized constructor */
    {
        x=a;
        y=b;
        count=0;
        next=null;
        previous=null;
    }
} //end of class
