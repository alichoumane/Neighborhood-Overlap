package nomeasure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import utils.CustomLogger;
import utils.Graph;

/**
 * computes neighbourhood overlap on the edges of the given graph, gives the output as a weighted graph or directly alter the graph
 * based on the results.
 */
public class NeighborhoodOverlap {
	
	public static CustomLogger logger = new CustomLogger("NeighborhoodOverlap", Level.FINER);
	
	public static HashMap<String, Double> calculate(Graph<String> graph){
		return calculate(graph, true);
	}
	/**
	 * 
	 * @param graph
	 * @param useWeights
	 * @return
	 */
	public static HashMap<String, Double> calculate(Graph<String> graph, boolean useWeights) {
		logger.log(Level.FINER, "calculating NO weights...\n");
		ArrayList<String> nodes = graph.getAllNodes();
		HashMap<String, Double> weights = new HashMap<>();
		
		for(String a:nodes) {
			for(String b:graph.getSuccessors(a)) {
				//calculate the overlap between a and b
				double overlap = 0;
				overlap =(useWeights)?(overlapUsingWeights(graph,a,b)):overlap(graph, a, b, "o");
				logger.log(Level.FINEST, "weight: "+a+","+b+" = "+overlap+"\n");//out
				weights.put(a+","+b, overlap);
			}
		}
		graph.setWeights(weights);
		
		return weights;
	}
	
	
	public static double overlapUsingWeights(Graph<String> graph, String a, String b) {
		double sumUnion = 0;
		double sumInter = 0;
		double overlap = 0.0;
		
		ArrayList<String> sa = graph.getSuccessors(a);
		ArrayList<String> sb = graph.getSuccessors(b);
		@SuppressWarnings("unchecked")
		ArrayList<String> intersection = (ArrayList<String>)sa.clone();
		intersection.retainAll(sb);
		sa.removeAll(intersection);//keeps only unique values of sa
		sa.remove(b);
		sb.removeAll(intersection);//keeps only unique values of sb
		sb.remove(a);
		//sum union is sum of maximums and sum intersection is sum of minimums
		for(String s:intersection) {
			double wa = graph.getWeight(a, s);
			double wb = graph.getWeight(b, s);
			sumInter+=Math.min(wa, wb);
			sumUnion+=Math.max(wa, wb);
		}
		for(String s:sa) {
			sumUnion+=graph.getWeight(a, s);
		}
		for(String s:sb) {
			sumUnion+=graph.getWeight(b, s);
		}
		
		overlap = (sumInter)/(sumUnion);
		logger.log(Level.FINEST, "weight: "+a+","+b+" = "+overlap+" <=> sumInter("+sumInter+") / sumUnion("+sumUnion+")\n");
		return overlap;
	}
	
	/**
	 * the overlap is the number of common neighbours over the number of all neighbours - 2 (because a and b belongs to neighbours set
	 * of each other)
	 * @param graph
	 * @param a first node
	 * @param b second node
	 * @param mode o for neighbourhood overlap, i for intersection, u for union
	 * @return overlap value
	 */
	public static double overlap(Graph<String> graph, String a, String b, String mode) {
		double nbNUnion = 0;
		double nbNInter = 0;
		ArrayList<String> sa = graph.getSuccessors(a);
		ArrayList<String> sb = graph.getSuccessors(b);
		nbNUnion = sa.size()+sb.size();
		sa.retainAll(sb);
		nbNUnion = nbNUnion - sa.size();
		nbNInter = sa.size();
		double overlap = 0.0;
		overlap = (nbNInter)/(nbNUnion-2);
		if(nbNUnion<=2)overlap = 0;
		if(mode.equals("o")) {
			//round overlap value
			BigDecimal bd = new BigDecimal(overlap);
			bd = bd.setScale(4, java.math.RoundingMode.HALF_UP);
			overlap = bd.doubleValue();
			return overlap;
		}else if(mode.equals("i")) {
			return nbNInter;
		}else if(mode.equals("u")) {
			return nbNUnion-2;
		}else {
			return overlap;
		}
	}
	
}
