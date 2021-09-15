/*---------------------------------------------------------------------------------------------*/
package dw.qmt.nodes;
import dw.qmt.perform.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*---------------------------------------------------------------------------------------------*/


/*----------------- ---------------------------------------------------------------------------*/
/**************************
* @author Ahcène Boukorça *
***************************/

public class Node implements Cloneable{
	//-----------------------------------------------------------------------------------------
	   public static int SF=1;
	   public static int PS=4096;
	   protected long sizeFactTable=6000000;
	   protected int nodeType ;// baseTable1, selection 2 , aggregation 3, joint 4,  root 5
	   protected int idNode;   // node identified  
	   private String nameNode;
	   private long nbrRows;
	   private long nbrPages;
	   protected String predicat="";
	   private double filterFactor;
	   private double sharedFactor;
	   //protected Columns column[];
	   private List<Columns> column ;
	   protected int nbrColumns;
	   protected int tupleAvgSize;
	   protected int tupleSize;
	   protected boolean isMaterialized;
	   private boolean isSizeCalculated;
	   protected long shar;
	   protected double Selectivity;
	   protected String columnSelectionPredicat;
       protected String valueSelectionPredicat[];
       protected int showSizeOfRows;
       protected String nomTable;
       protected boolean isWrited;
       protected boolean partitionCreated; 
       protected double ratePartition;
       protected boolean rateParttionLoaded;
       protected int nbrShar;
       private List <String> necessaryColumn;

	  // private int idLeftNode, idRightNode;
	  // private String aggregationParameter;
	  // public Table basedTable;
	  // public Table joinTable1,joinTable2;
	  // public Table selectionTable;
	  // public Table aggregationTable;
	//-----------------------------------------------------------------------------------------
	   public Node(){
			
			  init();

		         }
	 //------------------------------------------------------------------------------------------

	public Node(int id){
		init();
		idNode=id;
		
	}
	//-----------------------------------------------------------------------------------------

	public Node(int id, String nom){
		init();
		idNode=id;
		nameNode=nom;
		
	}/**/
	
	public Object clone() throws CloneNotSupportedException {
		 return super.clone();
		 }
	private void init()
	{
		idNode=0;
		nameNode="";
		this.nbrColumns=0;		 
		this.column = new ArrayList<Columns>();
		this.isMaterialized=false;
		isSizeCalculated=false;
		shar=-1;
		columnSelectionPredicat="";
		valueSelectionPredicat=new String[10];
		sizeFactTable=6000000;
		showSizeOfRows=(int)(Math.log(sizeFactTable*Node.SF) / Math.log(10))+1;
		if(showSizeOfRows <10) showSizeOfRows=10;;
		this.isWrited=false;
		partitionCreated=false;
		ratePartition=1.0;
		rateParttionLoaded=false;
		nbrShar=0;
		this.necessaryColumn=new   ArrayList<String>();
	
	}
	public void setNodeID(int id)
	{
		this.idNode=id;
	}
	//-----------------------------------------------------------------------------------------
	public void initcolumns(int nbrCol){
		//column=new Columns[nbrCol];
		nbrColumns=0;
		this.tupleAvgSize=this.tupleSize=0;
	}/**/
	//----------------------------------------------------------------------------------------
	protected void addColumns(String nameColumn,int avgSize,int sizeColumn){
		Columns c =new Columns();
		c.setNameColumn(nameColumn);
		c.setAvgSizeColumn(avgSize);
		c.setSizeColumn(sizeColumn);
		c.setTableName(this.nameNode);
		this.tupleAvgSize+=avgSize;
		this.tupleSize+=sizeColumn;
		column.add(c);
		nbrColumns++;
	}
	//----------------------------------------------------------------------------------------
		protected void addColumns(Columns c){
			/*column[nbrColumns]=new Columns();
			column[nbrColumns].setNameColumn(c.getName());
			column[nbrColumns].setAvgSizeColumn(c.getAverageSize());
			column[nbrColumns].setSizeColumn(c.getSize());
			column[nbrColumns].setTableName(c.getTableName());*/
			column.add(c);
			this.tupleAvgSize+=c.getAverageSize();
			this.tupleSize+=c.getSize();
			nbrColumns++;
		}
		protected void addColumns(Node n){
			int nbrCol=n.getNumberColumns();
			Columns c=new Columns();
			    for(int ind=0; ind<nbrCol; ind++){
			    	c=n.getColumn(ind);
			    	this.addColumns(c);
			     }
		}
    //---------------------------------------------------------------------------------------------------------
		public int getNbrColumns()
		{
			return this.column.size();
		}
	// ---------------------------------------------------------------------------------------------------------
		public Columns getColumnOfIndex(int ind){
			
			if(ind<nbrColumns)return column.get(ind);
			else
			{
				System.out.println(" ERROR : Problem of Culumn Index "+ idNode);
				return null;
			}
		}
		//-----------------------------------------------------------------------------------------
		public String getAllColumnsName()
		{
			String message="";
			for(int i=0; i<this.nbrColumns;i++)
				message =message+column.get(i).getName()+" ";
			return message;
			
		}
		//-----------------------------------------------------------------------------------------
 		public boolean isColumnOfTable(String columnname)
 		{
 			boolean ret=false;
 			for(int i=0; i<this.nbrColumns;i++)
 			{
 				
 				if(column.get(i).getName().compareTo(columnname)==0){ret=true; break;}
 			}
 			 
 			return ret;
 			
 		}
 		public String  getPredicat()
		{
			return this.predicat;
		}public String  getTableName()
		{
			return this.nomTable;
		}
		protected void addNecessaryColumn(String c){
			boolean exist=false;
			for(int i=0; i<necessaryColumn.size();i++)
				if(necessaryColumn.get(i).compareTo(c)==0) {exist=true; break;}
			if(!exist) necessaryColumn.add(c);
		}
		public String getNecessaryColumn()
		{
			String s=necessaryColumn.get(0);
			for(int i=1; i<necessaryColumn.size(); i++){
				s=s+","+necessaryColumn.get(i);
			}
			return s;
		}
	//--------------------------------------------------------------------------------------------
	  /* public void autoGetColumnsCaracteristics()
	   {
		   try
	   	{
	   	    Statement statement = InterfaceAuth.con.createStatement();
	   	    ResultSet  result =null;
	   		String request="select count (*) from ALL_TAB_COLUMNS where TABLE_NAME=\'"+this.nameNode+"\'" ;
	        result=statement.executeQuery(request );
	        if (result.next()){
	        	int nbrinter=result.getInt(1);
	        	this.initcolumns(nbrinter);
	        }
	   	    result=null;
	        request="select COLUMN_NAME, AVG_COL_LEN,  DATA_LENGTH from ALL_TAB_COLUMNS where TABLE_NAME=\'"+this.nameNode+"\'" ;
	        result=statement.executeQuery(request );
	        while (result.next()){
	        	String namC=result.getString(1);
	        	int av=result.getInt(2);
	        	int size=result.getInt(3);
	        	this.addColumns(namC, av , size);
	        	
	        }
	   	}
	   	catch (SQLException e)
	   	{
	   		//
	   	}
	   	
		   
	   }*/
	//-----------------------------------------------------------------------------------------

	public void setNodeType(int v){
		nodeType=v;
	}
	//-----------------------------------------------------------------------------------------

	public int getNodeType(){
		return nodeType;
	}
	//-----------------------------------------------------------------------------------------

	public void setNodeFactor(double f){
			this.filterFactor=f;
	}
	//-----------------------------------------------------------------------------------------

	public double getNodeFactor(){
			return this.filterFactor;
	}
	//-----------------------------------------------------------------------------------------

	public void setNodeSharedFactor(double f){
				this.sharedFactor=f;
	}
	//-----------------------------------------------------------------------------------------

	public double getNodeSharedFactor(){
				return this.sharedFactor;
			}
	//-----------------------------------------------------------------------------------------
	public int getNodeId(){
		return this.idNode;
	}
	//-----------------------------------------------------------------------------------------
   
	//-----------------------------------------------------------------------------------------
    
	//-----------------------------------------------------------------------------------------
    public String getNodeName(){
		return nameNode;
	}
	//-----------------------------------------------------------------------------------------
    public void setNodeName(String name){
		 nameNode=name;
	}
  //-----------------------------------------------------------------------------------------
   /* public String getPredicat(){
		return this.predicat;
	}*/
	//-----------------------------------------------------------------------------------------
    public void setPredicat(String s){
		 this.predicat=s;
	}
	//-----------------------------------------------------------------------------------------

	//-----------------------------------------------------------------------------------------
   
	//-----------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
    protected void setNodeNumberRows(long row){
		this.nbrRows=row;
		setNodeNumberPages();
	}
    //-----------------------------------------------------------------------------------------
  /*  public void setNodeNumberRows(long size,int t){

    	switch (t)
    	{
    	case 1:{ size=size*Node.SF;  break;}
    	case 3:{ size=(size * (1 + (int)(Math.log(Node.SF) / Math.log(10))));;   break;}
    	case 4:{ size=size*Node.SF ;   break;}
    	case 5:{ size=size*Node.SF;   break;}
    	}
		this.nbrRows=size;
	}*/
    //-----------------------------------------------------------------------------------------
    public long getNodeNumberRows(){
		return this.nbrRows;
		
	}
    public boolean isSizeCalculated()
    {
    	return this.isSizeCalculated;
    }
    //-----------------------------------------------------------------------------------------
    protected void setNodeNumberPages()
    {
    	if(this.tupleAvgSize!=0)
    		 {this.nbrPages=(long)(double)(this.nbrRows*this.tupleAvgSize/Node.PS);
    		  isSizeCalculated=true;
    		 }
    	else {
    		   this.nbrPages=0;
    		   isSizeCalculated=true;
    		   System.out.println(" ERROR  : Node without columns");
    	      }
    	
    }
    //-----------------------------------------------------------------------------------------
   public long getNodeNumberPage()
   {
	  // if( this.ratePartition!=1.0)System.out.println(this.idNode+" RATE : "+this.ratePartition);
	 long nbr=this.nbrPages;
	   return nbr;
   }
  //-----------------------------------------------------------------------------------------
    public int getTupleAvrageSize(){
		return this.tupleAvgSize;
	}
  //-----------------------------------------------------------------------------------------
    public int getTupleSize(){
		return this.tupleSize;
	} 
  //-----------------------------------------------------------------------------------------
    public int getNumberColumns(){
		return this.nbrColumns;
	} 
  //-----------------------------------------------------------------------------------------
    public Columns getColumn(int index){
		return this.column.get(index);
	} 
  // ----------------------------------------------------------------------------------------
    
	//-----------------------------------------------------------------------------------------
  	public String showDescriptionNode()
  	{
  		String message="" ;
  		switch(this.nodeType){
  		case 1:{message=message+"The node "+getNodeId()+" is based table -"+getNodeName()+"- with size "+nbrRows;   break;}
  		case 2:{message=message+"The node "+getNodeId()+" is a "+getNodeName()+ " with the selection predicat "+predicat;   break;}
  		case 3:{message=message+"The node "+getNodeId()+" is a "+getNodeName()+" with the joint predicat "+predicat;   break;}
  		case 4:{message=message+"The node "+getNodeId()+" is a "+getNodeName()+" with the aggregation parameter is "+this.predicat;   break;}
  		case 5:{message=message+"The node "+getNodeId()+" is a "+getNodeName()+" of the query "+this.predicat;   break;}

  		}
  		message=message+"\n" ;
        return message;
  	} 
  //-----------------------------------------------------------------------------------------
  	public String showNodeColumns()
  	{
  		String message="" ;
  		
  		switch(this.nodeType){
  		case 1:{message=message+"The node "+getNodeId()+" is based table -"+getNodeName()+"- nbrRows="+nbrRows+" nbrPage="+nbrPages+".";   break;}
  		case 2:{message=message+"The node "+getNodeId()+" is a "+getNodeName()+"- nbrRows="+nbrRows+" nbrPage="+nbrPages+".";   break;}
  		case 3:{message=message+"The node "+getNodeId()+" is a "+getNodeName()+"- nbrRows="+nbrRows+" nbrPage="+nbrPages+".";   break;}
  		case 4:{message=message+"The node "+getNodeId()+" is a "+getNodeName()+"- nbrRows="+nbrRows+" nbrPage="+nbrPages+".";   break;}
  		case 5:{message=message+"The node "+getNodeId()+" is a "+getNodeName()+"- nbrRows="+nbrRows+" nbrPage="+nbrPages+".";   break;}

  		}
  		message=message+"\n";
  		for (int i=0; i<this.nbrColumns;i++){
  			message=message+"                  - The column n° "+i+":"+column.get(i).getName()+" of table "+column.get(i).getTableName()+" with size ="+column.get(i).getSize()+" and average size="+column.get(i).getAverageSize()+"\n";	
  		}
  		message=message+"\n" ;
        return message;
  	} 
 // ----------------------------------------------------------------------------------------
    public long getShar(){
    
    	return this.shar;
    	
    }
	 //---------------------------------------------------------------------------------------------------------------
	public String getinfo()
       {
    	   String s="";
    	   s=this.getNodeId()+", "+nodeType+" ,0,"+this.getPredicat()+",0.0";
    	   return s;	    	   
       }
 	public double getSelectivity()
	{
  		return this.Selectivity;
	}

    //------------------------  Polymorphisme-----------------------------------------------------------------
 	public void setMaterialization (Workload workload, boolean val){
  		System.out.println(" ERROR : SetMaterializartion  Function of subclass not used "+idNode);
 	}

  	public long getIOCost (Workload worklaod){
  		System.out.println(" ERROR : Cost Function of subclass not used "+idNode);
  		return 0;
	}
  	public long getIOCostMQO (Workload worklaod){
  		System.out.println(" ERROR : Cost MQO Function of subclass not used "+idNode);
  		return 0;
	}
  	public long getIOCost (IndividualPlan plan){
  		System.out.println(" ERROR : Cost Function of subclass not used "+idNode);
  		return 0;
	}
  	public void calculateNumberRows(Workload worklaod){
  		System.out.println(" ERROR : Calculate Number rows function  of subclass not used "+idNode);
  	}
  	public void calculateNumberRows(IndividualPlan plan){
  		System.out.println(" ERROR : Calculate Number rows function  of subclass not used "+idNode);
  	}
 
  	public void calculateSelectivity(Workload worklaod)
	{
  		System.out.println(" ERROR : Calculate Selectivity function  of subclass not used "+idNode);
	}
  	public void calculateSelectivity(IndividualPlan plan)
	{
  		System.out.println(" ERROR : Calculate Selectivity function  of subclass not used "+idNode);
	}
 // ----------------------------------------------------------------------------------------
    public void calculateShar(long nbrRowFact, Workload workload){
    
    	System.out.println(" ERROR : Calculate Shar function  of subclass not used "+idNode);
    	
    }
    public void calculateShar(long nbrRowFact, IndividualPlan plan){
        
    	System.out.println(" ERROR : Calculate Shar function  of subclass not used "+idNode);
    	
    }
    public String ShowPlan(IndividualPlan p, int index)
	{
		String s="";		
			s=" ERROR : Show Plan function  of subclass not used "; s=s+"\n";
		return s;
	}
    public String ShowPlan(Workload w, int index)
	{
		String s="";		
			s=" ERROR : Show Plan function  of subclass not used "; s=s+"\n";
		return s;
	}
    public String getDependence(Workload w )
	{
		String s="";		
			s=" ERROR : Show Plan function  of subclass not used "; s=s+"\n";
		return s;
	}
    public void createPartitionSchema(Workload w )
   	{
    		System.out.println(" ERROR : Create Partition function  of subclass not used ");
    }
    public boolean isPartitionCreated()
	 {
		 return partitionCreated;
	 }
    public void setPartitionRate(Workload w){
    	System.out.println(" ERROR : Set Partition rate function  of subclass not used ");
    	 
    }
    public void setPartitionRate(double d){
    	this.ratePartition=d;
    }
    public double getPartitionRate(){
    	return this.ratePartition;
    }
   public  void materializeNodes(Workload w)
   {
	   System.out.println(" ERROR : Materialize Nodes function  of subclass not used ");
   }
   public boolean isMaterialized()
   {
	   return isMaterialized;
   }
   public void rewriteQuery(Workload w, QueryRewriting rq)
   {
	   System.out.println(" ERROR : Query rewrite function  of subclass not used ");
   }
   public void setNecessaryColumn(Workload w, List <String> list){
	   System.out.println(" ERROR : Set necessary column function  of subclass not used ");

   }
   
}
