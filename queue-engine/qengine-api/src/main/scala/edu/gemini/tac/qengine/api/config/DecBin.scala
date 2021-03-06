package edu.gemini.tac.qengine.api.config

import xml.Elem


object DecBin {
  def apply[T](startDeg: Int, endDeg: Int, binValue: T): DecBin[T] =
    new DecBin[T](DecRange(startDeg, endDeg), binValue)

  def inclusive[T](start: Int, end: Int, binValue: T): DecBin[T] =
    DecBin(DecRange.inclusive(start, end), binValue)

  /**
   * Validates that a sequence of DecBin is sorted, non-overlapping, and
   * abutting.
   */
  def validate[T](bins: Seq[DecBin[T]]): Boolean =
    DecRange.validate(bins.map(_.range))

}

/**
 * A grouping of a range of declinations with a parameterized type.
 */
final case class DecBin[T](range: DecRange, binValue: T) {

  def inclusive: DecBin[T] =
    if (range.isInclusive) this else DecBin(range.inclusive, binValue)

  def map[U](f: T => U): DecBin[U] = DecBin(range, f(binValue))
  def updated(newValue: T): DecBin[T] = DecBin[T](range, newValue)

  def toXML : Elem = <DecBin>
    { range.toXML }
    <!-- binValue toXML or string? -->
    </DecBin>
}
