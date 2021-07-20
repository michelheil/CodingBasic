package model

/** *
 * Represents the result of some process. A result can
 * either be a [[Result.Success]] containing the validated object along with informative [[Result.Hint]]s, or a
 * [[Result.Failure]] which contains only failure hints.
 *
 * @tparam A type of contained object
 */
sealed trait Result[+A] {

  /** *
   * Returns whether this object represents a successful result; i.e [[Result.Success]]. If this method returns `true`,
   * it is guaranteed that the corresponding [[Result#isFailure]] returns `false`, and vice versa. For performance
   * reasons, these methods should be implemented separately and not one as negation of the other; even though that
   * should be exactly the case.
   *
   * @return whether result is successful
   */
  def isSuccess: Boolean

  /** *
   * Returns whether this object represents a failed result; i.e [[Result.Failure]]. See documentation on
   * [[Result#isSuccess]] for policy details.
   *
   * @return whether result is failure
   */
  def isFailure: Boolean

  /** *
   * Returns this object wrapped within an [[Option]] object. Naturally, a [[Result.Success]] result is transformed
   * into [[Some]] of result type, and [[Result.Failure]] is transformed into [[None]]. This method should be used
   * sparingly as it discards failure hints.
   *
   * @return cognate as option
   */
  def toOption: Option[A]

  /** *
   * Maps the contained object of this Result by applying the specified function to it. If this instance is a
   * [[Result.Success]], returns new [[Result.Success]] of range of specified function, with specified hints appended
   * to end of existing success hints. Otherwise does nothing and returns this instance as-is.
   *
   * @param func            function to apply to successful result
   * @param additionalHints hints to add to success
   * @tparam B function range
   * @return mapped success object
   */
  def map[B](func: A => B, additionalHints: Result.Hint*): Result[B]

  /** *
   * Maps the contained object of this Result by passing it to the specified function, which itself returns some
   * Result. If this instances is a [[Result.Success]], returns the result of applying the specified function to the
   * contained object. Otherwise, does nothing and returns this instance as-is.
   *
   * @param func            function to apply to successful result
   * @param additionalHints hints to add to success
   * @tparam B function range
   * @return mapped and flattened success object
   */
  def flatMap[B](func: A => Result[B], additionalHints: Result.Hint*): Result[B]

  def foreach(func: A => Any): Unit

  def forFailures(func: Seq[Result.Hint] => Any): Unit

  def getOrElse[B >: A](other: => B): B

  def get[B >: A]: B
}

object Result {

  /** *
   * Currently is an alias of String, but may change into its own type for uniquely typed hints over which type classes
   * can be created. This could allow exhaustive error-handling.
   */
  type Hint = String

  /** *
   * Represents a failed [[Result]], containing failure [[Result.Hint]]s to elaborate on the reason.
   *
   * @param hints failure hints
   */
  case class Failure(hints: Seq[Hint]) extends Result[Nothing] {
    def isSuccess: Boolean = false

    def isFailure: Boolean = true

    def toOption: Option[Nothing] = None

    def map[B](func: Nothing => B, newHints: Hint*): Result[B] = this

    def flatMap[B](func: Nothing => Result[B], newHints: Result.Hint*): Result[B] = this

    def foreach(func: Nothing => Any): Unit = {}

    def forFailures(func: Seq[Result.Hint] => Any): Unit = func(hints)

    def getOrElse[B](other: => B): B = other

    override def get[B >: Nothing]: B = throw new RuntimeException(hints.mkString("\t"))
  }

  object Failure {

    /** *
     * Convenience function for creating a [[Result.Failure]] with a vararg of hints.
     *
     * @param hint   first hint
     * @param others other hints
     * @return failure with specified hints
     */
    def apply(hint: Hint, others: Hint*): Failure = Failure(hint +: others)
  }

  /** *
   * Represents a successful [[Result]], containing the result of relevant operation along with informative
   * [[Result.Hint]]s to elaborate on it if necessary.
   *
   * @param result contained object
   * @param hints  informative hints
   * @tparam A type of contained object
   */
  case class Success[+A](result: A, hints: Seq[Hint]) extends Result[A] {
    def isSuccess: Boolean = true

    def isFailure: Boolean = false

    def toOption: Option[A] = Some(result)

    def map[B](func: A => B, newHints: Hint*): Result[B] = copy(func(result), hints ++ newHints)

    def flatMap[B](func: A => Result[B], newHints: Result.Hint*): Result[B] = func(result) match {
      case success @ Success(_, otherHints) => success.copy(hints = hints ++ otherHints ++ newHints)
      case other => other
    }

    def foreach(func: A => Any): Unit = func(result)

    def forFailures(func: Seq[Result.Hint] => Any): Unit = {}

    def getOrElse[B >: A](other: => B): B = result

    override def get[B >: A]: B = result
  }

  object Success {

    /** *
     * Convenience function for creating a [[Result.Success]] with empty hints.
     *
     * @param result the instance to contain in success
     * @tparam A type of instance
     * @return instance wrapped within a success
     */
    def apply[A](result: A): Success[A] = Success(result, Seq.empty)

    /** *
     * Convenience function for creating a [[Result.Success]] with a vararg of hints.
     *
     * @param result the instance to contain in success
     * @param hint   first hint
     * @param others other hints
     * @tparam A type of instance
     * @return instance wrapped within a success along with specified hints
     */
    def apply[A](result: A, hint: Hint, others: Hint*): Success[A] = Success(result, hint +: others)
  }

}