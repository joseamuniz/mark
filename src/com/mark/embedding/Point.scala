package com.mark.embedding

import com.mark.similarity._

/**
 * A point represents an n-dimensional coordinate in an Euclidean space.
 *
 * Along with the coordinate values across each dimension, this class contains
 * methods to compute Euclidean distances Point objects.
 *
 */

class Point[N : Numeric](val args: N*) {

  private val dim = args.size
  private val coordinates = args.toList
  private val num = implicitly[Numeric[N]]

  def distanceTo = {
    def distance = new Distance[Point[N], Double] {
      /**
       * The distance between two points x and y, where
       *
       *  x = (x1,x2,...,xn) and
       *  y = (y1,y2,...,yn) is given by
       *
       * the magnitude of vector x-y, which can be computed as:
       *
       * d(x,y) = |x - y|
       *        = sqrt[(x1 - y1)^2 + (x2 - y2)^2 + ... + (xn - yn)^2]
       */
      def apply(origin: Point[N], dest: Point[N]): Double = {
        math.sqrt(
            ((origin coordinates) zip (dest coordinates))
            .map({case (x, y) => math.pow(num.toDouble(num.minus(x,y)), 2)})
            .foldRight(0.0)(_ + _))
      }

    }

    distance(Point.this, _ : Point[N])
  }
}
