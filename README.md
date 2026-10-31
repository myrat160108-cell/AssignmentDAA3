Implementation Details

Made by: Murat Islam SE-2503

1. Disjoint Set Union (DSU)
Path Compression: Implemented in find(i) to flatten the tree structure during queries.
Union by Rank: Implemented in union(i, j) to attach the smaller depth tree under the root of the deeper tree, preventing unbalanced trees.

2. Minimum Spanning Tree (MST)
Kruskal's Algorithm: Uses edge sorting followed by DSU operations to detect and prevent cycles.
Prim's Algorithm: Built using a priority queue (PriorityQueue<Edge>) starting from an arbitrary node to greedily expand the minimum-cost cut.

3. Connectivity & Components
Topological Sorting (Kahn's Algorithm): Computes in-degrees for vertices in Directed Acyclic Graphs (DAGs) and orders nodes.Detects cycles when processed node count does not match total vertices.
Kosaraju's Algorithm (SCC): Performs two-pass Depth-First Searches (DFS) utilizing vertex completion ordering on a transposed graph to identify strongly connected components.

4.Shortest Paths
Dijkstra's Algorithm: Computes shortest path distances from a source node to all reachable nodes using a Min-Heap/Priority Queue for non-negative edge weights.

5.Experimental Setup & Results
Setup
Environment: OpenJDK 25 / macOS (Apple M-series)
Measurement Method: High-resolution timing via System.nanoTime(), averaged over 5 identical runs per configuration.
Graph Generation: Reproducible random graphs using fixed random seeds (seed = 42).

6.Conclusion
Minimum Spanning Trees: Kruskal is preferred for sparse networks (E≪V 2) due to simple iteration, while Prim is superior on dense grid networks.
Connectivity & Service Dependencies: Topological sorting provides optimal schedule planning for DAG dependencies, while Kosaraju identifies closed isolated loops in transit networks.
Route Optimization: Dijkstra remains the standard algorithm for non-negative road distance calculations in smart city navigation systems.
