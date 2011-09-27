package com.mark.embedding

import com.mark.similarity.Distance

/**
 * An embedder maps a set of points {p_i} of type X  and a distance function
 * d(p_i, p_j) into a set of points {x_i} in T = R^n minimizing the objective 
 * function: 
 * 
 * 					\sum_{i,j} | d(p_i, p_j) - |x_i - x_j| |
 *
 * Here, |x_i - x_j| represents the Euclidean distance between points i and j.
 *  
 */
trait Embedder[X, Y] {
  
 
    /**
     * Perform the embedding of <tt>points</tt> into T = R^n using distance
     * as the distance metric. 
     * 
     * @param points The points to be embedded
     * @distance The (non-null) distance function mapping any pair of objects
     * 			 of type X to the distance between them. Although not necessary
     * 			 a good distance function should satisfy the properties of a 
     * 			 normed space, or at least a metric space.
     * 
     * @see http://en.wikipedia.org/wiki/Normed_vector_space
     * @see http://en.wikipedia.org/wiki/Metric_space
     * 			
     * 
     *  
     */
	def embed(points : Set[X], distance : Distance[X,Y]) : Set[Point[Y]];

}
