/*---------------------------------------------------------------------------------------------*/
package dw.qmt.nodes;

import java.util.List;
import java.util.StringTokenizer;

import dw.qmt.perform.*;
/*---------------------------------------------------------------------------------------------*/
   // import java.applet.*;
/*----------------- ---------------------------------------------------------------------------*/
/**************************
* @author Ahcène Boukorça *
***************************/

public class JoinNode extends  Node {
	//-----------------------------------------VARIABLES DECLARATION-----------------------------------------
	        private int leftNode, rightNode;
	        public int dimentionTable;
	        public int factTable;
	       // private int indexSelectionFact;
	      //  private int indexSelectionDim;
	        private String joinPredicat;
	        private String column1, column2;
	       // private int indexPredecessorJoin;
	        private int implementationMethod;
	        
	//-----------------------------------------CONSTRUCTORS DECLARATION-----------------------------------------
						public JoinNode(){
						
							this.leftNode=this.rightNode=-1;
							factTable=dimentionTable=-1;	
							//indexPredecessorJoin=indexSelectionFact=indexSelectionDim=-1;
							implementationMethod=1;
							this.nodeType=3;
							 
							
						}
						
						public JoinNode(JoinNode jn){
							
							factTable=jn.factTable  ; dimentionTable=jn.dimentionTable;
							this.nodeType=jn.getNodeType();
							implementationMethod=1;
							this.leftNode=jn.getLeftNode();this.rightNode=jn.getRightNode();
							predicat=joinPredicat=jn.getPredicat();
							/*indexPredecessorJoin=jn.getIndexSelectionJoin();indexSelectionFact=jn.getIndexSelectionFact();indexSelectionDim=jn.getIndexSelectionDim();
							this.joinPredicat=jn.getJoinPredicat();*/
							
						}
						
	//-----------------------------------------METHODS DECLARATION-----------------------------------------
			private void setDimentionTable(int index){
				this.dimentionTable=index;
			}
			private void setFactTable(int index){
				this.factTable=index;
			}
	        public void addBaseTable(BaseTable t, String c){
	        	if(t.isTableFait())
	        	{	this.setFactTable(t.getNodeId());
	        	    column1=c;
	        	}
	        	else
	        	{
	        		this.setDimentionTable(t.getNodeId());
	        		column2=c;
	        	}
	        }
	       
	        public void addPredecessor(BaseTable t, int ind){
	        	
	        	if(t.isTableFait())
	        	{
	        		this.leftNode=ind;
	        	}
	        	else
	        	{
	        		this.rightNode=ind;
	        	}
	        	
	        }
	        public void addPredecessor(int n)
	        {    
	        	if(n!=-1 )  this.leftNode=n;
	        }
	        public void addLeftPredecessor(int n)
	        {    
	        	this.leftNode=n;
	        }
	        public void addRightPredecessor(int n)
	        {    
	        	this.rightNode=n;
	        }
	        public void updateJoinPredicat()
	        {
	        	if(this.dimentionTable!=-1 && this.factTable!=-1) {
	        		this.joinPredicat=column1+" = "+column2; 
	        	    super.predicat=this.joinPredicat;	
	        	}
	        }
	        public int getLeftNode(){
	        	return this.leftNode;
	        }
	        public int getRightNode(){
	        	return this.rightNode;
	        }

	    
	        public boolean equalJoinNode(JoinNode n)
	        {
	        	if((n.getRightNode()==this.rightNode) && (n.getLeftNode()==this.leftNode) ){
	        	  if(this.joinPredicat.compareTo(n.getPredicat())==0){
	        		  return true;
	        	  }
	        	  else return false; 
	        			 
	        	   }
	        	 else return false;
	        }
	      /* */
			
			public void calculateSelectivity(Workload workload)
			{
				workload.getNode(this.leftNode).calculateSelectivity(workload);
				workload.getNode(this.rightNode).calculateSelectivity(workload);
				super.Selectivity=workload.getNode(this.leftNode).getSelectivity()*
						           workload.getNode(this.rightNode).getSelectivity();
			}
			public void calculateSelectivity(IndividualPlan plan )
			{
				plan.getNode(this.leftNode).calculateSelectivity(plan);
				plan.getNode(this.rightNode).calculateSelectivity(plan);
				super.Selectivity=plan.getNode(this.leftNode).getSelectivity()*
						           plan.getNode(this.rightNode).getSelectivity();
			}
			public void calculateShar(long nbrRowFact, Workload workload){
		    	
		    	   if(shar==-1){
				   workload.getNode(this.leftNode).calculateShar(nbrRowFact,workload); 
		    	   long lShar=workload.getNode(this.leftNode).getShar();
		    	   workload.getNode(this.rightNode).calculateShar(nbrRowFact,workload); 
		    	   long rShar=workload.getNode(this.rightNode).getShar();
		    	   if(lShar<rShar) shar=lShar;
		    	   else shar =rShar;
		    	   }   			   
		    	
		    }
			public void calculateShar(long nbrRowFact, IndividualPlan plan){
		    	
		    	   if(shar==-1){
				   plan.getNode(this.leftNode).calculateShar(nbrRowFact,plan); 
		    	   long lShar=plan.getNode(this.leftNode).getShar();
		    	   plan.getNode(this.rightNode).calculateShar(nbrRowFact,plan); 
		    	   long rShar=plan.getNode(this.rightNode).getShar();
		    	   if(lShar<rShar) shar=lShar;
		    	   else shar =rShar;
		    	   }   			   
		    	
		    }

	// ---------------------------------------------------------------------------------------------------------------
			public void calculateNumberRows(Workload workload){
				if(!this.isSizeCalculated()){
					//System.out.println(idNode+" Join with predecessor  "+ leftNode+"   "+ rightNode);
					workload.getNode(this.leftNode).calculateNumberRows(workload);
					workload.getNode(this.rightNode).calculateNumberRows(workload);
					//System.out.println(InterfaceQMT.workload.getNode(this.leftNode).getNodeId()+"  left  "+leftNode +"  number node"+ InterfaceQMT.workload.getNode(this.rightNode).getNodeId()+"  right  "+rightNode );
					
					
					this.addColumns(workload.getNode(this.leftNode));
					this.addColumns(workload.getNode(this.rightNode));
					//double sel =workload.getNode(this.leftNode).getSelectivity();
					//long num=workload.getNode(this.rightNode).getNodeNumberRows();
					//long sh=workload.getNode(this.rightNode).getShar();
					double doubleNbrRow=workload.getNode(this.leftNode).getSelectivity() * workload.getNode(this.rightNode).getNodeNumberRows()* workload.getNode(this.rightNode).getShar();
					long nbr= (long)doubleNbrRow;
					this.setNodeNumberRows(nbr);	
					setNodeNumberPages();
				//	System.out.println("Join Node " +idNode+" row   "+ nbr+ "   shar right "+ sh + "  sel left "+ sel+" left "+leftNode+" right "+rightNode);
					
				
				
				}
			}
			public void calculateNumberRows(IndividualPlan plan){
				if(!this.isSizeCalculated()){
					//System.out.println(idNode+" Join with predecessor  "+ leftNode+"   "+ rightNode);
					plan.getNode(this.leftNode).calculateNumberRows(plan);
					plan.getNode(this.rightNode).calculateNumberRows(plan);
					//System.out.println(InterfaceQMT.workload.getNode(this.leftNode).getNodeId()+"  left  "+leftNode +"  number node"+ InterfaceQMT.workload.getNode(this.rightNode).getNodeId()+"  right  "+rightNode );
					
					
					this.addColumns(plan.getNode(this.leftNode));
					this.addColumns(plan.getNode(this.rightNode));
					//double sel =plan.getNode(this.leftNode).getSelectivity();
					//long num=plan.getNode(this.rightNode).getNodeNumberRows();
					//long sh=plan.getNode(this.rightNode).getShar();
					double doubleNbrRow=plan.getNode(this.leftNode).getSelectivity() * plan.getNode(this.rightNode).getNodeNumberRows()* plan.getNode(this.rightNode).getShar();
					long nbr= (long)doubleNbrRow;
					this.setNodeNumberRows(nbr);	
					setNodeNumberPages();
				//	System.out.println("Join Node " +idNode+" row   "+ nbr+ "   shar right "+ sh + "  sel left "+ sel+" left "+leftNode+" right "+rightNode);
					
				
				
				}
			}
			// ---------------------------------------------------------------------------------------------------------------
			public long getIOCost (Workload workload){
				if (super.isMaterialized) {return 0;}
				switch (this.implementationMethod)
				{
					case 1: { long costLeft= workload.getNode(this.leftNode).getIOCost(workload);
					          long costRight= workload.getNode(this.rightNode).getIOCost(workload);
					          double r=workload.getNode(this.rightNode).getPartitionRate();
					          
					          if(r!=1.0)
					          {
					        	 costLeft=(long)((double)costLeft*r);
					             costRight=(long)((double)costRight*r); 
					             //System.out.println(r);
					          }
					          long left=workload.getNode(this.leftNode).getNodeNumberPage();
					          if(workload.getNode(this.leftNode ) instanceof BaseTable && r!=1.0)
					          {
					        	  left=(long)((double) left*r); //
					          }
					          long cost=3*(left+workload.getNode(this.rightNode).getNodeNumberPage());;
							  cost=cost+costLeft+costRight; 
						   return cost; }
					default : {System.out.println(" ERROR : Methode implementation of selection node "+ super.idNode);
					            return -1;}
				}
				
			}
			public long getIOCostMQO (Workload workload){
				if (super.isMaterialized) {return 0;}
				super.isMaterialized=true;
				switch (this.implementationMethod)
				{
					case 1: { long costLeft= workload.getNode(this.leftNode).getIOCostMQO(workload);
					          long costRight= workload.getNode(this.rightNode).getIOCostMQO(workload);
					          double r=workload.getNode(this.rightNode).getPartitionRate();
					          costLeft=(long)((double)costLeft*r);
					          costRight=(long)((double)costRight*r);
					          long cost=3*(workload.getNode(this.leftNode).getNodeNumberPage()+workload.getNode(this.rightNode).getNodeNumberPage());;
							  cost=cost+costLeft+costRight; 
						   return cost; 
						   }
					default : {System.out.println(" ERROR : Methode implementation of selection node "+ super.idNode);
					            return -1;}
				}
				
			}
			public void setMaterialization (Workload workload, boolean val){
		 		super.isMaterialized=val;
		 		super.nbrShar=0;
		 		//workload.getNode(this.leftNode).setMaterialization(workload, val);
		 		//workload.getNode(this.rightNode).setMaterialization(workload, val);
		         
		 	}
			
			public long getIOCost (IndividualPlan plan){
				if (super.isMaterialized) return 0;
				switch (this.implementationMethod)
				{
					case 1: { long costLeft= plan.getNode(this.leftNode).getIOCost(plan);
					          long costRight= plan.getNode(this.rightNode).getIOCost(plan);
					          double r=plan.getNode(this.rightNode).getPartitionRate();
					          costLeft=(long)((double)costLeft*r);
					          costRight=(long)((double)costRight*r);
					          long cost=3*(plan.getNode(this.leftNode).getNodeNumberPage()+plan.getNode(this.rightNode).getNodeNumberPage());;
							  cost=cost+costLeft+costRight; 
						   return cost; }
					default : {System.out.println(" ERROR : Methode implementation of selection node "+ super.idNode);
					            return -1;}
				}
				
			}
	      
	       
	    // ---------------------------------------------------------------------------------------------------------------
			public String ShowPlan(IndividualPlan p, int nbrSpace)
			{
				long rows=getNodeNumberRows();
			    int size=(int)(Math.log(rows) / Math.log(10))+1;
			    String s="";
			    for(int i=0;i<(showSizeOfRows-size) ;i++) s=s+" ";
			    s=s+rows;
			    
				   for(int i=0;i<nbrSpace ;i++) s=s+" ";
				     s=s+" Join Operation :"+predicat +"("+getNodeId()+") : " +" with left=" + this.leftNode +" and right="+ this.rightNode;  s=s+"\n";
					 s=s+p.getNode(leftNode).ShowPlan(p, nbrSpace+1);
					 s=s+p.getNode(rightNode).ShowPlan(p, nbrSpace+1);
				return s;
			}
		    // ---------------------------------------------------------------------------------------------------------------
				public String ShowPlan(Workload p, int nbrSpace)
				{
					long rows=getNodeNumberRows();
				    int size=(int)(Math.log(rows) / Math.log(10))+1;
				    String s="";
				    for(int i=0;i<(showSizeOfRows-size) ;i++) s=s+" ";
				    s=s+rows;
				    
					   for(int i=0;i<nbrSpace ;i++) s=s+" ";
					     s=s+" Join Operation :"+predicat +"("+getNodeId()+") : " +" with left=" + this.leftNode +" and right="+ this.rightNode;  s=s+"\n";
						 s=s+p.getNode(leftNode).ShowPlan(p, nbrSpace+1);
						 s=s+p.getNode(rightNode).ShowPlan(p, nbrSpace+1);
					return s;
				}
				
		  //------------------------------------------------------------------------------------------------------------------------
				 public String getDependence(Workload w )
					{
						String s="";
						if(!isWrited)
						{
							s=s+w.getNode(leftNode).getDependence(w);
							s=s+w.getNode(rightNode).getDependence(w);
							String tmp1="";String tmp2="";
							if(w.getNode(leftNode) instanceof BaseTable) tmp1=w.getNode(leftNode).getTableName();
							else if (w.getNode(leftNode) instanceof SelectedNode) tmp1="S"+leftNode;
							else if (w.getNode(leftNode) instanceof JoinNode) tmp1="J"+leftNode;
							
							if(w.getNode(rightNode) instanceof BaseTable) tmp2=w.getNode(rightNode).getTableName();
							else if (w.getNode(rightNode) instanceof SelectedNode) tmp2="S"+rightNode;
							else if (w.getNode(rightNode) instanceof JoinNode) tmp2="J"+rightNode;
							
							s=s+"J"+idNode+"\t1\t"+tmp1+"\n";
							s=s+"J"+idNode+"\t1\t"+tmp2+"\n";
							isWrited=true;	
						}
						return s;
					}
		//--------------------------------------------------------------------------------------------------------------------------------
				 public void createPartitionSchema(Workload w )
				   	{
					 if(!super.partitionCreated){
						// System.out.println(idNode+"   ...  "+ w.getNode(this.getLeftNode()).getNodeId());
					   if(w.getNode (this.getLeftNode()) instanceof BaseTable ||w.getNode(this.getLeftNode()) instanceof SelectedNode ||
							             (w.getNode(this.getLeftNode()) instanceof JoinNode && w.getNode(this.getLeftNode()).isPartitionCreated() )){
						   if(w.getNode(this.getRightNode()) instanceof SelectedNode){
							   w.getNode (this.getRightNode()).createPartitionSchema(w);
							   super.partitionCreated=true;
							 //  System.out.println(idNode+"---"+w.getNode (this.getRightNode()).getNodeId());
						   }
						   else super.partitionCreated=true;
					   }
					   else
					   {
						 // System.out.println(idNode+"+++++"+ w.getNode(this.getLeftNode()).getNodeId());
						   w.getNode (this.getLeftNode()).createPartitionSchema(w);
							 //super.partitionCreated=true;

					   }
				   	 
					 
					 }
		}
		//------------------------------------------------------------------------------------------------------------------------------
				
	public void setPartitionRate(Workload w )
		 {
		   if(!super.rateParttionLoaded){
			    w.getNode(rightNode).setPartitionRate(w);	
			   if(w.getNode (this.getLeftNode()) instanceof JoinNode  )
			   {
				   w.getNode(leftNode).setPartitionRate(w);	
				   
			   }
	    
	    	 super.rateParttionLoaded=true;
		   }
		  
		 }	
	 public  void materializeNodes(Workload w)
	   {
		   if (super.nbrShar>1)super.isMaterialized=true;
		   else {
		   super.nbrShar++;
		   w.getNode(leftNode).materializeNodes(w);
		   }
		   
	   }
	 public void rewriteQuery(Workload w, QueryRewriting rq)
	   {
		   if(isMaterialized)rq.addfromClose("MV"+idNode);
		   else {   rq.addjoinCondition(predicat);
		      w.getNode(leftNode).rewriteQuery(w, rq);
		      w.getNode(rightNode).rewriteQuery(w, rq);
		   }
	   }
	 public void setNecessaryColumn(Workload w, List <String> list){
		
		 for(int i=0; i<list.size();i++)
			 super.addNecessaryColumn(list.get(i));
		 String string =predicat;
		 string =string.replaceAll(",", " ");
		 string =string.replaceAll("[()]", " ");
		 string =string.replace ('*', ' ');
		 string =string.replace('-', ' ');
		 string =string.replace('+', ' ');
		 string =string.replace('=', ' ');
		 
		 StringTokenizer st = new StringTokenizer(string);
	     while (st.hasMoreTokens()) {
	    	 String s=st.nextToken();
	    	 s=s.replace(" ", "");
	    	 if(s.indexOf("LO_")==-1  )
	    	 {
	    		 String debut=s.substring(0,2);
	    		// System.out.print(debut+"   ---->  ");
	    		 for(int i=0; i<list.size();i++)
	    			 if(list.get(i).indexOf(debut)!=-1){
	    				 list.remove(i);
	    			 }
	    		 //for(int i=0; i<list.size();i++)
	    		//	 System.out.print(list.get(i)+",") ;
	    		 //System.out.println();
	    	 }
	    	 else
	    		 list.add(s);
		     }
	     
	     w.getNode(leftNode).setNecessaryColumn(w, list);
	   }
}
