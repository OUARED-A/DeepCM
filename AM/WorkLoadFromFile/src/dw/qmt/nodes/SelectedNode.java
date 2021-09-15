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

public class SelectedNode extends  Node {
	//-----------------------------------------VARIABLES DECLARATION-----------------------------------------
	       
	        private int baseTable;                     // to be deleted
	        private int predecessorNode;  // identifiant
	      //  private int typePredecessor;//0 table, 1 selected node
	        // Selection predicat (selection in one column and may have may values
	        //-------------------------------------
	         //-------------------------------------
	        private String selectionPredicat;     
	        
	        private int nbrValue;
	       
	        private boolean onConstruction;
	        private boolean columnIsAffected;  // to check selection column name affectation
	        private boolean hasPredecessor;
	        private int implementationMethod;
	       
	        
	//-----------------------------------------CONSTRUCTORS DECLARATION-----------------------------------------
						public SelectedNode(){
							
							this.nodeType=2;
							selectionPredicat="";
							nbrValue=0;
							onConstruction=true;
							this.columnIsAffected=false;
							this.predecessorNode=-1;
							this.hasPredecessor=false;
							this.implementationMethod=1; // 1: sequentiel scan
							
							
						}
						
	
	//-----------------------------------------METHODS DECLARATION-----------------------------------------
						public void setColumnName(String c)
						{
							if(!this.columnIsAffected){
								columnSelectionPredicat=c;
								this.columnIsAffected=true;
							}
							 
						 
						}
						
						public String getColumn(){
							if(this.columnIsAffected) return this.columnSelectionPredicat;
							else return "Column not affected";
						}
						
						public void addValue(String v, String operator)
						{
							if(this.onConstruction){
								valueSelectionPredicat[nbrValue]=v;
								if(this.nbrValue==0)
								    this.selectionPredicat=this.selectionPredicat+this.columnSelectionPredicat+operator+v;
								else
									this.selectionPredicat=this.selectionPredicat+" AND "+this.columnSelectionPredicat+operator+v;
	
								this.nbrValue++;
								super.predicat=this.selectionPredicat;
							}
						}
						
						public boolean equalSelectedNode(SelectedNode node)   //verify
						{
							if(!onConstruction){
								String temp=node.getPredicat();	
							
								if ( temp.compareTo(this.selectionPredicat)==0) 
									return true ;
								else 
									return false;
							}
							else{
								return false; 
							}								
						}
						
						
						public void setConstructionFinished()
						{
							this.onConstruction=false;
						}
						
						public boolean existColumn(SelectedNode nom)
						{
							if ( nom.getColumn().compareTo(this.columnSelectionPredicat)==0) return true ;
							else return false;
							
								
						}
						
					

					/*	public String  getSelectionPredicat()
						{
							return this.selectionPredicat;
						}*/
					//-----------------------------------------------------------------------------------------------------------
						public void setBaseTableNode(int index)
						{
							this.baseTable=index;
							
						}
					//-----------------------------------------------------------------------------------------------------------
						public void setPredecessorNode(int index)
						{
							this.predecessorNode=index;
							this.hasPredecessor=true;
							
						}
					public int getIndexBaseTable(){
						return this.baseTable;
					}
					//-----------------------------------------------------------------------------------------------------------
						public int getpredecessorNode()
						{   if(this.hasPredecessor)
							return this.predecessorNode ;
						    else
						    	return -1;   //before this.baseTable   // to be deleted
							
						}
						
						 public void setPredicat(String s){
							 this.selectionPredicat=predicat=s;
							 
							 
						}
					/*	public void  setpredecessorType(int id)
						{
							this.typePredecessor=id;
							
						}*/
				  //------------------------------------------------------------------------------------------------------------
						public boolean getPredecessorStat()
						{
							return this.hasPredecessor;
						}
				 
			
			    //-----------------------------------------------------------------------------------------------------------------
						public void setSelectivity(double s)
						{
							this.Selectivity=s;
						}
						public void calculateSelectivity(Workload workload)
						{
							workload.getNode(this.predecessorNode).calculateSelectivity(workload);
						}
						public void calculateSelectivity(IndividualPlan plan)
						{
							plan.getNode(this.predecessorNode).calculateSelectivity(plan);
						}
				// ---------------------------------------------------------------------------------------------------------------
						public void calculateNumberRows(Workload workload){
							if(!this.isSizeCalculated()){
							if(this.hasPredecessor){
								//System.out.println(idNode+" Selection with predecessor  "+ predecessorNode);
								workload.getNode(this.predecessorNode).calculateNumberRows(workload);
								this.addColumns(workload.getNode(this.predecessorNode));
								double doubleNbrRow=workload.getNode(this.predecessorNode).getNodeNumberRows() * this.Selectivity;
								
								long nbr= (long)doubleNbrRow;
								this.setNodeNumberRows(nbr);	
								
								setNodeNumberPages();
								//System.out.println("Select Node" +idNode+" row   "+ nbr+ "   shar  "+ shar + "  sel  "+ Selectivity+ " predecessor "+ predecessorNode);
								
								//super.tupleAvgSize=InterfaceQMT.workload.getNode(this.predecessorNode).getTupleAvrageSize();
								//super.tupleSize=InterfaceQMT.workload.getNode(this.predecessorNode).getTupleSize();
							//	super.setNodeNumberPages();
							}
							else
								System.out.println(" ERROR : Problem of predecessor of selection node "+ super.idNode);
							}
						}
						public void calculateNumberRows(IndividualPlan plan){
							if(!this.isSizeCalculated()){
							if(this.hasPredecessor){
								
								plan.getNode(this.predecessorNode).calculateNumberRows(plan);
								this.addColumns(plan.getNode(this.predecessorNode));
								double doubleNbrRow=plan.getNode(this.predecessorNode).getNodeNumberRows() * this.Selectivity;
								
								long nbr= (long)doubleNbrRow;
								this.setNodeNumberRows(nbr);	
								//if(nbr==0) System.out.println(idNode+"  selectivity select "+ Selectivity);
								setNodeNumberPages();
								//System.out.println("Select Node" +idNode+" row   "+ nbr+ "   shar  "+ shar + "  sel  "+ Selectivity+ " predecessor "+ predecessorNode);
								
								//super.tupleAvgSize=InterfaceQMT.workload.getNode(this.predecessorNode).getTupleAvrageSize();
								//super.tupleSize=InterfaceQMT.workload.getNode(this.predecessorNode).getTupleSize();
							//	super.setNodeNumberPages();
							}
							else
								System.out.println(" ERROR : Problem of predecessor of selection node "+ super.idNode);
							}
						}
						// ---------------------------------------------------------------------------------------------------------------
						public long getIOCost (Workload workload){
							if (super.isMaterialized) { return 0;}
							switch (this.implementationMethod)
							{
								case 1: { long costSel=(long)(  workload.getNode(this.predecessorNode).getNodeNumberPage());
									      long cost= workload.getNode(this.predecessorNode).getIOCost(workload)+ costSel;
										   
									       return cost; }
								default : {System.out.println(" ERROR : Methode implementation of selection node "+ super.idNode);
								            return -1;}
							}
							
						}
						// ---------------------------------------------------------------------------------------------------------------
						public long getIOCostMQO (Workload workload){
							if (super.isMaterialized) {return 0;}
							super.isMaterialized=true;
							switch (this.implementationMethod)
							{
								case 1: {  long costSel=(long)(  workload.getNode(this.predecessorNode).getNodeNumberPage());
									       long cost= workload.getNode(this.predecessorNode).getIOCostMQO(workload)+ costSel;
									   return cost; }
								default : {System.out.println(" ERROR : Methode implementation of selection node "+ super.idNode);
								            return -1;}
							}
							
						}
						public void setMaterialization (Workload workload, boolean val){
					 		super.isMaterialized=val;
					 		//workload.getNode(this.predecessorNode).setMaterialization(workload, val);
					         
					 	}
						public long getIOCost (IndividualPlan plan){
							if (super.isMaterialized) {return 0;}
							switch (this.implementationMethod)
							{
								case 1: { long costSel=(long)(  plan.getNode(this.predecessorNode).getNodeNumberPage());
									      long cost= plan.getNode(this.predecessorNode).getIOCost(plan)+ costSel;
									   return cost; }
								default : {System.out.println(" ERROR : Methode implementation of selection node "+ super.idNode);
								            return -1;}
							}
							
						}
						
						
						 public void calculateShar(long nbrRowFact, IndividualPlan plan){
						    	
						    	   if(shar==-1){
							       plan.getNode(this.predecessorNode).calculateShar(nbrRowFact,plan); 
						    	   shar=plan.getNode(this.predecessorNode).getShar();
						    			   
						    	//   System.out.println(idNode+" Shar  "+ shar);
						    	   }
						    }
						 public void calculateShar(long nbrRowFact, Workload workload){
						    	
					    	   if(shar==-1){
						       workload.getNode(this.predecessorNode).calculateShar(nbrRowFact,workload); 
					    	   shar=workload.getNode(this.predecessorNode).getShar();
					    			   
					    	//   System.out.println(idNode+" Shar  "+ shar);
					    	   }
					    }
						 public String ShowPlan(IndividualPlan p, int nbrSpace)
							{
							   long rows=getNodeNumberRows();
							    int size=(int)(Math.log(rows) / Math.log(10))+1;
							    String s="";
							    for(int i=0;i<(showSizeOfRows-size) ;i++) s=s+" ";
							    s=s+rows;
							    
								for(int i=0;i<nbrSpace ;i++) s=s+" ";
							     s=s+" Selection Operation :"+this.selectionPredicat +"("+getNodeId()+") : " +" with predecessor=" + this.predecessorNode;  s=s+"\n";
									s=s+p.getNode(predecessorNode).ShowPlan(p, nbrSpace+1);
								return s;
							}
						 public String ShowPlan(Workload p, int nbrSpace)
							{
							   long rows=getNodeNumberRows();
							    int size=(int)(Math.log(rows) / Math.log(10))+1;
							    String s="";
							    for(int i=0;i<(showSizeOfRows-size) ;i++) s=s+" ";
							    s=s+rows;
							    
								for(int i=0;i<nbrSpace ;i++) s=s+" ";
							     s=s+" Selection Operation :"+this.selectionPredicat +"("+getNodeId()+") : " +" with predecessor=" + this.predecessorNode;  s=s+"\n";
									s=s+p.getNode(predecessorNode).ShowPlan(p, nbrSpace+1);
								return s;
							}
						 public String getDependence(Workload w )
							{
								String s="";
								if(!isWrited)
								{
									s=s+w.getNode(predecessorNode).getDependence(w);
									String tmp="";
									if(w.getNode(predecessorNode) instanceof BaseTable) tmp=w.getNode(predecessorNode).getTableName();
									else if (w.getNode(predecessorNode) instanceof SelectedNode) tmp="S"+predecessorNode;
									else if (w.getNode(predecessorNode) instanceof JoinNode) tmp="J"+predecessorNode;
									
									s=s+"S"+idNode+"("+ this.selectionPredicat +")"+"\t1\t"+tmp+"\n";
									isWrited=true;	
								}
								return s;
							}
						 public void createPartitionSchema(Workload w )
						   	{
							 //System.out.println(Selectivity);
							 boolean ok=true;
							 //if(selectionPredicat.indexOf("AND")!=-1||selectionPredicat.indexOf("OR")!=-1)ok=false;
							 if(this.Selectivity>0.1 && this.Selectivity<0.7 && ok)
							    w.insertPartitionPredict(predicat);
						   	}
						 public void setPartitionRate(Workload w )
						 {
							 if(!super.rateParttionLoaded){
								 if (w.isPartitionPredicat(selectionPredicat)){
									// w.getNode(this.predecessorNode).setPartitionRate(Selectivity,w);
									 super.ratePartition=Selectivity; 
									  System.out.println("RATE PARTITION OK "+selectionPredicat );
 								 }
								// else super.ratePartition=1.0;
							  // w.getNode(this.predecessorNode).setPartitionRate(w);
							   super.rateParttionLoaded=true;
							   }
  						 }	
			
						 public  void materializeNodes(Workload w)
						   {
							    
							   super.nbrShar++;
							   w.getNode(predecessorNode).materializeNodes(w);
							   
						   }
						 public void rewriteQuery(Workload w, QueryRewriting rq)
						   {
							  if(selectionPredicat.indexOf(" OR")!=-1) rq.addselectionCondition("("+selectionPredicat+")");
							  else rq.addselectionCondition(selectionPredicat);
							  w.getNode(predecessorNode).rewriteQuery(w, rq);
						   }
						 public void setNecessaryColumn(Workload w, List <String> list){
							  //
						   }
}
