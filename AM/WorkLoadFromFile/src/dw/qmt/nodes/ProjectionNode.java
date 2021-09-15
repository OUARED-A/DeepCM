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

public class ProjectionNode extends  Node {
	//-----------------------------------------VARIABLES DECLARATION-----------------------------------------
	      //  private String projectionPredicat;
	        private int Predecessor;
	        private int implementationMethod;
	//-----------------------------------------CONSTRUCTORS DECLARATION-----------------------------------------
						public ProjectionNode (){
							
							Predecessor=-1;
						//	projectionPredicat="";
							this.nodeType=4;
							implementationMethod=1; //sequential access
							
						}

	//-----------------------------------------METHODS DECLARATION-----------------------------------------
						/*public String getProjectionPredicat(){
				        	return this.projectionPredicat;
				        }*/
						public void setProjectionPredicat(String pred){
							
				        	 super.predicat=pred;
				        }
						public void setIndexPredecessor(int n)
				        {
				        	this.Predecessor=n;
				        }
						public int getIndexPredecessor()
				        {
				        	return this.Predecessor;
				        }
						
						public void calculateSelectivity(Workload workload)
						{
							workload.getNode(this.Predecessor).calculateSelectivity(workload);
							 super.Selectivity=1.0;
						}
						public void calculateSelectivity(IndividualPlan plan)
						{
							plan.getNode(this.Predecessor).calculateSelectivity(plan);
							 super.Selectivity=1.0;
						}
						public void calculateShar(long nbrRowFact, Workload workload){
					    	
							   if(shar==-1){
					    	   workload.getNode(this.Predecessor).calculateShar(nbrRowFact,workload); 
					    	   shar =workload.getNode(this.Predecessor).getShar();
							   }
					    	
					    }
						public void calculateShar(long nbrRowFact, IndividualPlan plan){
					    	
							   if(shar==-1){
					    	   plan.getNode(this.Predecessor).calculateShar(nbrRowFact,plan); 
					    	   shar =plan.getNode(this.Predecessor).getShar();
							   }
					    	
					    }

				// ---------------------------------------------------------------------------------------------------------------
						public void calculateNumberRows(Workload workload){
							if(!this.isSizeCalculated()){
								//System.out.println(idNode+" projection with predecessor  "+ Predecessor);
								workload.getNode(this.Predecessor).calculateNumberRows(workload);
								this.addColumns(workload.getNode(this.Predecessor));
								
								double doubleNbrRow= workload.getNode(this.Predecessor).getNodeNumberRows();
								long nbr= (long)doubleNbrRow;
								this.setNodeNumberRows(nbr);								
								setNodeNumberPages();
								
							
							
							}
						}
						public void calculateNumberRows(IndividualPlan plan){
							if(!this.isSizeCalculated()){
								//System.out.println(idNode+" projection with predecessor  "+ Predecessor);
								plan.getNode(this.Predecessor).calculateNumberRows(plan);
								this.addColumns(plan.getNode(this.Predecessor));
								
								double doubleNbrRow= plan.getNode(this.Predecessor).getNodeNumberRows();
								long nbr= (long)doubleNbrRow;
								this.setNodeNumberRows(nbr);								
								setNodeNumberPages();
								
							
							
							}
						}
						// ---------------------------------------------------------------------------------------------------------------
						public long getIOCost (Workload workload){
							if (super.isMaterialized) {return 0;}
							switch (this.implementationMethod)
							{
								case 1: { long costPredecessor= workload.getNode(this.Predecessor).getIOCost(workload);
								          long cost=workload.getNode(this.Predecessor).getNodeNumberPage();
										  cost=cost+costPredecessor; 
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
								case 1: { long costPredecessor= workload.getNode(this.Predecessor).getIOCostMQO(workload);
								          long cost=workload.getNode(this.Predecessor).getNodeNumberPage();
										  cost=cost+costPredecessor; 
									   return cost; }
								default : {System.out.println(" ERROR : Methode implementation of selection node "+ super.idNode);
								            return -1;}
							}
							
						}
						public void setMaterialization (Workload workload, boolean val){
					 		super.isMaterialized=val;
					 		super.nbrShar=0;
					 		//workload.getNode(this.Predecessor).setMaterialization(workload, val);
					         
					 	}
						public long getIOCost (IndividualPlan plan){
							if (super.isMaterialized) return 0;
							switch (this.implementationMethod)
							{
								case 1: { long costPredecessor= plan.getNode(this.Predecessor).getIOCost(plan);
								          long cost=plan.getNode(this.Predecessor).getNodeNumberPage();
										  cost=cost+costPredecessor; 
									   return cost; }
								default : {System.out.println(" ERROR : Methode implementation of selection node "+ super.idNode);
								            return -1;}
							}
							
						}
						 public String getDependence(Workload w )
							{
								String s="";
								if(!isWrited)
								{
									s=s+w.getNode(this.Predecessor).getDependence(w);
									s=s+"P"+idNode+"\t1\t"+Predecessor+"\n";
									isWrited=true;	
								}
								return s;
							}
						 public String ShowPlan(IndividualPlan p, int nbrSpace)
							{
							   long rows=getNodeNumberRows();
							    int size=(int)(Math.log(rows) / Math.log(10))+1;
							    String s="";
							    for(int i=0;i<(showSizeOfRows-size) ;i++) s=s+" ";
							    s=s+rows;
							    
								for(int i=0;i<nbrSpace ;i++) s=s+" ";
							     s=s+" Selection Operation :"+ "("+getNodeId()+") : " +" with predecessor=" + this.Predecessor;  s=s+"\n";
									s=s+p.getNode(Predecessor).ShowPlan(p, nbrSpace+1);
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
							     s=s+" Projection Operation :"+  "("+getNodeId()+") : " +" with predecessor=" + this.Predecessor;  s=s+"\n";
									s=s+p.getNode(Predecessor).ShowPlan(p, nbrSpace+1);
								return s;
							}
						 
						
				    // ---------------------------------------------------------------------------------------------------------------
						 public void createPartitionSchema(Workload w )
						   	{
							    w.getNode(this.Predecessor).createPartitionSchema(w);
						   	}
						// ---------------------------------------------------------------------------------------------------------------
						 public void setPartitionRate(Workload w )
						   	{
							   if(!super.rateParttionLoaded){
							    w.getNode(this.Predecessor).setPartitionRate(w);
							    super.rateParttionLoaded=true;
							   }
 						   	}
						 public  void materializeNodes(Workload w)
						   {
							    
							   super.nbrShar++;
							   w.getNode(Predecessor).materializeNodes(w);
							   
						   }
						 public void rewriteQuery(Workload w, QueryRewriting rq)
						   {
							  rq.addProjection(predicat); 
							  w.getNode(Predecessor).rewriteQuery(w, rq);
						   }
						 public void setNecessaryColumn(Workload w, List <String> list){
							 String string =predicat;
							 string =string.replaceAll(",", " ");
							 string =string.replaceAll("[()]", " ");
							 string =string.replace ('*', ' ');
							 string =string.replace('-', ' ');
							 string =string.replace('+', ' ');
							 
							 StringTokenizer st = new StringTokenizer(string);
						     while (st.hasMoreTokens()) {
						    	 String s=st.nextToken();
						    	 if(s.indexOf("LO_")!=-1 ||s.indexOf("D_")!=-1 ||s.indexOf("S_")!=-1 ||s.indexOf("C_")!=-1 ||s.indexOf("P_")!=-1 )
						    	 {
						    		 s=s.replace(" ", "");
						    		 super.addNecessaryColumn(s);
						    		 list.add(s);
						    	 }
 						     }
						     w.getNode(Predecessor).setNecessaryColumn(w, list);
						   }
						 
}
