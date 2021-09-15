/*---------------------------------------------------------------------------------------------*/
	package dw.qmt.perform;
/*---------------------------------------------------------------------------------------------*/
   // import java.applet.*;
/*----------------- ---------------------------------------------------------------------------*/
/**************************
* @author Ahcène Boukorça *
***************************/

public class PlanTree {
	//-----------------------------------------VARIABLES DECLARATION-----------------------------------------
	                    private PlanTree nextPlans;
	                    private int nbrPlan;
	                    private int index;
	                   
	//-----------------------------------------CONSTRUCTORS DECLARATION-----------------------------------------
						public PlanTree(int nbr){
							this.nbrPlan=nbr;
							index=-1;
						}
						public PlanTree(){
							this.nbrPlan=0;
							index=-1;
						}
    //               
					
	//-----------------------------------------METHODS DECLARATION-----------------------------------------
						
						public void addNextPlan(int nbr){
							nextPlans =new PlanTree(nbr);
							
						}
						public int getplan()
						{
							index++;
							if(index>=nbrPlan)return -1;
							else return index;
						}
						
						
						public PlanTree getNextPlan()
						{
							return this.nextPlans;
						}
						public int getNbrPlan()
						{
							return this.nbrPlan;
						}
						
						public void setStartIndex()
						{
							index=-1;
						}
    //               ______
    //               _____________________________________________________________________________
						
						
	                  


}
