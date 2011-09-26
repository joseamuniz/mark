package com.mark.similarity

/**
 * Two objects are mensurable if we can compute a distance between them.
 *
 * The distance is an integer measure of similarity. In this case, a distance
 * of zero means the objects are identical (the same object), whereas their
 * distance is Int.MaxValue if they are not similar at all.
 *
 * Distances cannot be negative
 */

trait Distance[X, Y] extends ((X,X) => Y) {
    def apply(origin : X, dest : X) : Y;
}