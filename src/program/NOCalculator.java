package program;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import nomeasure.NeighborhoodOverlap;
import utils.Graph;

public class NOCalculator {
	
	public static String networkFile = "";
	 public static String outputDirectory = "";
    public static boolean weighted = true;
	
	public static void main(String[] args) {
		if(!loadArgs(args))return;
		Graph<String> graph = Graph.loadFromFile(networkFile, weighted);
		NeighborhoodOverlap.calculate(graph, true);
		try {
			System.out.println("writing result graph to "+outputDirectory);
			graph.write(new FileWriter(outputDirectory));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean loadArgs(String[] args) {
		ArrayList<String> argsList = new ArrayList<String>(Arrays.asList(args));

		if(args.length < 2 || args[0].equals("-h")) {
			System.out.println("Please provide the following arguments to run the program:");
			System.out.println("-f followed by the network file name (undirected, one edge per line)");
			System.out.println("-uw to deal with unweighted network (default is weighted)");
			return false;
		}
		
		if(args.length >= 2) {
			int index = -1;
			
			index = argsList.indexOf("-f");
			if(index==-1)return loadArgs(new String[]{"-h"});
			else networkFile = argsList.get(index+1);
			
			File networkF = new File(networkFile);
			
			if(!networkF.exists()) 
			{
				System.out.println(networkFile + " does not exist.");
				return false;
			}

			try{
				outputDirectory=(new File(networkF.getCanonicalPath())).getParentFile().getAbsolutePath();
				outputDirectory+="/network_NO.dat";
				
				index = argsList.indexOf("-uw");
				if(index != -1) weighted = false;
                        
				return true;
			}
			catch(IOException e)
			{
				return false;
			}
		}
		
		return loadArgs(new String[]{"-h"});
	}
}
