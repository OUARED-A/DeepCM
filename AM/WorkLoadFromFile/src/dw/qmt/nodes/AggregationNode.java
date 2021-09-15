	package dw.qmt.nodes;
 /*---------------------------------------------------------------------------------------------*/
   // import java.applet.*;
/*----------------- ---------------------------------------------------------------------------*/
/**************************
* @author Ahcène Boukorça *
***************************/

public class AggregationNode extends  Node {
	            
	            private String groupbyPredicat;
	            private String orderbyPredicat;

	            public AggregationNode(){
	            	groupbyPredicat="";
	            	orderbyPredicat="";
	            	
	            }
	 
	            public void addGroupbyPredicat(String s){
	            	this.groupbyPredicat=s;
	            }
	            public void addOrderbyPredicat(String s){
	            	this.orderbyPredicat=s;
	            }
	            public String getGroupByPredicat(){
	            	return this.groupbyPredicat;
	            }
	            public String getOrderByPredicat(){
	            	return this.orderbyPredicat;
	            }
}
