# Neighborhood-Overlap
This program implements the Neighbourhood Overlap measure "A new generalization of edge overlap to weighted networks". 
In fact, this measure works equally for unweighted and weighted networks. 

For more details read the article: https://arxiv.org/abs/2002.04426

Choumane, A. (2020). A new generalization of edge overlap to weighted networks. IJAIA, Vol.11, No.1, January 2020.

#############
Compile
#############

To compile the source code again, execute ./compile.sh under Linux. Make sure you have JDK 8 installed.

#############
Run
#############

The minimum command to run the algorithm is:
java -jar NeighborhoodOverlap.jar -f network.dat

where network.dat is an undirected, weighted network (one edge per line), as shown in the given sample network file.

If your network is unweighted, use the following command:
java -jar NeighborhoodOverlap.jar -f network.dat -uw

#############
Output
#############

The output of this program is a new network file that includes the Neighbourhood Overlap weights for each edge in the network. 

Contact us for any question: ali.choumane@ul.edu.lb
