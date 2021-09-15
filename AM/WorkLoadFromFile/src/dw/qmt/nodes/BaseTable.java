/*---------------------------------------------------------------------------------------------*/
package dw.qmt.nodes;
import dw.qmt.perform.*;

/*---------------------------------------------------------------------------------------------*/
   // import java.applet.*;
	import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.StringTokenizer;
/*----------------- ---------------------------------------------------------------------------*/
/**************************
* @author Ahcène Boukorça *
***************************/

public class BaseTable extends  Node {
	//-----------------------------------------VARIABLES DECLARATION-----------------------------------------
	        
	        private boolean isFactTable;
	       
	        
	//-----------------------------------------CONSTRUCTORS DECLARATION-----------------------------------------
			public BaseTable(){
					  this.nodeType=1;
					  this.isFactTable=false;
					  
						}
	
	//-----------------------------------------METHODS DECLARATION-----------------------------------------
	    
		public boolean isName(String nom)
		{
			if ( nom.compareTo(nomTable)==0) return true ;
			else return false;
		}
     //-----------------------------------------METHODS DECLARATION-----------------------------------------

		public void  setTableName(String nom)
		{
			nomTable=predicat=nom;
			if(nomTable.compareTo("LINEORDER")==0)this.isFactTable=true;
		}
		public void  setFactTable()
		{
			nomTable=predicat="LINEORDER";
			this.isFactTable=true;
		}
		//-----------------------------------------------------------------------------------------------------

		public String  getTableName()
		{
			return nomTable;
		}
		//-----------------------------------------------------------------------------------------------------
        public boolean isTableFait()
        {
        	return this.isFactTable;
        }
		//-----------------------------------------------------------------------------------------------------

        
	  
	   //------------------------------------------------------------------------------------------------------

	   public long getIOCost (Workload workload){
			return 0;
			
		}
	   public long getIOCostMQO (Workload workload){
			return 0;
			
		}
	   public long getIOCost (IndividualPlan plan){
			return 0;
			
		}
	   public void setMaterialization (Workload workload, boolean val){
	 		super.isMaterialized=val;
	 		super.nbrShar=0;
	         
	 	}
	 //-------------------------------------------------------------------------------------------------------
	   public void calculateNumberRows(Workload workload){
		   if(!this.isSizeCalculated()){
			   getAutoCaractiristics();
		   
		  }
	  }
	   public void getAutoCaractiristics(){
		   
		   try
		   	{  
		   	   
		   	 //  Statement statement = InterfaceAuth.con.createStatement();
		   	    ResultSet  result =null;
		   		String request="select count (*) from ALL_TAB_COLUMNS where TABLE_NAME=\'"+nomTable+"\'" ;
		        result=InterfaceAuth.statement.executeQuery(request );
		        if (result.next()){
		        	int nbrinter=result.getInt(1);
		        	this.initcolumns(nbrinter);
		        }
		   	    result=null;
		        request="select COLUMN_NAME, AVG_COL_LEN,  DATA_LENGTH from ALL_TAB_COLUMNS where TABLE_NAME=\'"+nomTable+"\'" ;
		        result=InterfaceAuth.statement.executeQuery(request );
		        while (result.next()){
		        	String namC=result.getString(1);
		        	namC.toLowerCase();
		        	int av=result.getInt(2);
		        	int size=result.getInt(3);
		        	this.addColumns(namC, av , size);
		        	
		        }
		   	}
		   	catch (SQLException e)
		   	{
		   		System.out.println(" ERROR : ERROR Acces Oracle database ");
		   	}
		   
		    long nbrrow=0;
		    int progress=0;
		    if(nomTable.compareTo("LINEORDER")==0){nbrrow=6000000;progress=1; }
		    if(nomTable.compareTo("DATES")==0){nbrrow=2556;progress=0;}
		    if(nomTable.compareTo("CUSTOMER")==0){nbrrow=30000;progress=1;}
		    if(nomTable.compareTo("SUPPLIER")==0){nbrrow=2000;progress=1;}
		    if(nomTable.compareTo("PART")==0){nbrrow=200000;progress=2;}
		    switch (progress)
	    	{
	    	    case 1:{ nbrrow=nbrrow*Node.SF;  break;}
	    	    case 2:{ nbrrow=(nbrrow * (1 + (int)(Math.log(Node.SF) / Math.log(2))));;  System.out.println(nbrrow); break; }
	    	}
		    if(nbrrow!=0)
		      this.setNodeNumberRows(nbrrow);	
		    else System.out.println(" ERROR : Unknown Table "+ nomTable);
		    setNodeNumberPages();
		    System.out.println(nomTable+"  "+super.getNodeNumberPage());
		   
		  
	  }
	   
	   public void calculateNumberRows(IndividualPlan plan){		  
			   if(!this.isSizeCalculated()){
				   getAutoCaractiristics();			   
			  }	   
		  
	  } 
	   //--------------------------------------------------------------------------------------------------------------------------------
	   public void calculateSelectivity(Workload workload)
		{
		   this.Selectivity=1.0;
		}
	   public void calculateSelectivity(IndividualPlan plan)
		{
		   this.Selectivity=1.0;
		}
	   //--------------------------------------------------------------------------------------------------------------------------------

		 public void calculateShar(long nbrRowFact, IndividualPlan plan){	    	
	    	 
			 if(shar==-1)
	    	   shar= (long) nbrRowFact/super.getNodeNumberRows();
	      }
		 public void calculateShar(long nbrRowFact, Workload workload){	    	
	    	 
			 if(shar==-1)
	    	   shar= (long) nbrRowFact/super.getNodeNumberRows();
	      }
   
		 
		 public String ShowPlan(IndividualPlan p, int nbrSpace)
			{   
			    long rows=getNodeNumberRows();
			    int size=(int)(Math.log(rows) / Math.log(10))+1;
			    String s="";
			    for(int i=0;i<(showSizeOfRows-size) ;i++) s=s+" ";
			    s=s+rows;
				for(int i=0;i<nbrSpace ;i++) s=s+" ";
					s=s+" Access to Base Table :"+nomTable +"("+getNodeId()+")" ; s=s+"\n";
				return s;
			}
		 public String ShowPlan(Workload  p, int nbrSpace)
			{   
			    long rows=getNodeNumberRows();
			    int size=(int)(Math.log(rows) / Math.log(10))+1;
			    String s="";
			    for(int i=0;i<(showSizeOfRows-size) ;i++) s=s+" ";
			    s=s+rows;
				for(int i=0;i<nbrSpace ;i++) s=s+" ";
					s=s+" Access to Base Table :"+nomTable +"("+getNodeId()+")" ; s=s+"\n";
				return s;
			}
		 public String getDependence(Workload w )
			{
				String s="";
				if(!isWrited)
				{	//s=s+this.getTableName();				 
					isWrited=true;	
				}
				return s;
			}
		 public void setPartitionRate(Workload w )
		 {
		     //   
 		 }	
		 public  void materializeNodes(Workload w)
		   {
			     super.nbrShar++;
			    		   
		   }
		 public void rewriteQuery(Workload w, QueryRewriting rq)
		   {
			    rq.addfromClose(this.nomTable);
			   
		   }

		 public void setNecessaryColumn(Workload w, List <String> list){
			 //
		   }
}
