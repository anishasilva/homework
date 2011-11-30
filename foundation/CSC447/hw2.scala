/* INSTRUCTIONS
 *
 * WARNING: if you are editing this file with Notepad, Write/Wordpad, or Word, stop now!
 * Download a more sophisticated text editor before editing this file.
 * There are many good free text editors for Windows, such as:
 * http://notepad-plus-plus.org/ (Notepad++)
 * http://www.textpad.com/products/textpad/index.html   (not free, but has a trial period)
 *
 * WARNING: your submissions will be tested with Scala 2.8.1 final.
 * There may be slight incompatibilities between versions, so please
 * ensure that you are using the same version during your development/testing.
 *
 * Complete the exercises below.  For each "EXERCISE" comment, add
 * code immediately below the comment.
 *
 * HINT: type definitions into the Scala interpreter to try them out,
 * then paste working code into this file.
 *
 * TESTING
 *
 * 1. Write solutions for each exercise.
 * 
 * 2. Download "hw2tests.jar".
 *
 * 3. Run the tests on your code with:
 *
 *    scala -cp hw2tests.jar hw2.scala
 *
 * GRADING
 * 
 * 1. Submissions must run (as described in TESTING above) without compilation errors.
 *    Submissions with compilation errors will receive 0 points.
 *    Note that refactoring the code into classes/objects will cause the TESTING process to fail.
 *
 * 2. You must not use while loops or (re)assignment to variables.
 * 
 * SUBMISSION
 *
 * 1. Submit hw2.scala as HW2 in the Desire2Learn dropbox before the deadline.
 *    The file you submit must be ready to run, i.e., do not submit a ZIP or RAR file containing
 *    hw2.scala; do not submit a Word document containing your solutions, etc.
 *
 * 2. Late submissions will not be permitted because solutions will be discussed in class.
 * 
 */


object hw2 extends hw2testsI {

  // EXERCISE 1: complete the following recursive definition of a "map" function
  // for Scala's builtin List type.  You must not use the builtin "map" method.
  def map [X,Y] (xs:List[X], f:X=>Y) : List[Y] = xs match {
		case Nil => Nil
		case y :: ys => f(y)::map(ys,f)
	}

  // EXERCISE 2: complete the following recursive definition of a "filter" function
  // for Scala's builtin List type.  You must not use the builtin "filter" method.
  def filter [X] (xs:List[X], f:X=>Boolean) : List[X] = xs match {
		case Nil => Nil
		case y::ys => if ( f(y) ) y::filter(ys, f) else filter(ys, f)
	}

  val list1 : List[Int] = (1 to 20).toList

  // EXERCISE 3: use "map" to compute a list containing 21..40 from "list1" above.
  // You must use the "map" function that you defined above.
  val list2 : List[Int] = map(list1, (x:Int) => x + 20)

  // EXERCISE 4: use "map" to compute a list containing 21..40 from "list1" above.
  // You must use the "map" method of Scala's builtin List class.
  val list3 : List[Int] = list1.map( x => x + 20)

  // EXERCISE 5: use "filter" to compute a list containing 3,6,9..18 (numbers divisble
  // by 3) from "list1" above.
  // You must use the "filter" function that you defined above.
  val list4 : List[Int] = filter(list1, (x:Int) => x % 3 == 0)

  // EXERCISE 6: use "filter" to compute a list containing 3,6,9..18 (numbers divisble
  // by 3) from "list1" above.
  // You must use the "filter" method of Scala's builtin List class.
  val list5 : List[Int] = list1.filter(x => x % 3 ==0)

  // EXERCISE 7: complete the definition of the following function that computes the length of
  // each String in a list, and returns the original Strings paired with their length.
  // For example:
  // 
  //   stringLengths (List ("the", "rain")) == List (("the", 3), ("rain", 4))
  //
  // You must not use recursion directly in the function.  You can use the "map" method 
  // of the List class.

  def stringLengths (xs:List[String]) : List[(String, Int)] = xs zip xs.map(x => x.length)


  // EXERCISE 8: complete the function definition for "delete1" that takes
  // an element "x" and a list "ys", then returns the list where any
  // occurrences of "x" in "ys" have been removed.  Your definition of
  // "delete1" MUST be recursive.
  //
  // For example:
  //
  //   delete2 ("the", List ("the","the","was","a","product","of","the","1980s"))
  //   == List ("was","a","product","of","1980s")
  //

  def delete1 [X] (x:X, ys:List[X]) : List[X] = ys match {
		case Nil => Nil
		case y::ys => if(x == y) delete1(x,ys) else y::delete1(x,ys)
	}

  // EXERCISE 9: complete the function definition for "delete2" below.  It must 
  // have the same behavior as "delete1".  It must be written using "for comprehensions" 
  // and not use recursion explicitly.
  def delete2 [X] (x:X, ys:List[X]) : List[X] = 
		for (y <- ys if y != x)
			yield y

  // EXERCISE 10: complete the function definition for "delete3" below.  It must 
  // have the same behavior as "delete1".  It must be written using the
  // builtin "filter" method for Lists and not use recursion explicitly.
  def delete3 [X] (x:X, ys:List[X]) : List[X] = ys.filter(z => z != x)

  // EXERCISE 11: complete the function definition for "removeDupes1" below.
  // It takes a list as argument, then returns the same list with
  // consecutive duplicate elements compacted to a single element.  
  // Duplicate elements that are separated by at least one distinct
  // element should be left alone.
  //
  // For example:
  // 
  //   removeDupes1 (List (1,1,2,3,3,3,4,4,5,6,7,7,8,9,2,2,2,9))
  //   == List (1,2,3,4,5,6,7,8,9,2,9)
  //
  def removeDupes1 [X] (xs:List[X]) : List[X] = xs match {
		case Nil => Nil
		case x::y::ys => if(x == y) removeDupes1(x::ys) else x::removeDupes1(y::ys)
		case y::Nil => y::Nil
	}
  
  // EXERCISE 12: write a function "removeDupes2" that behaves like "removeDupes1",
  // but also includes a count of the number of consecutive duplicate
  // elements in the original list (thus producing a simple run-length
  // encoding).  The counts are paired with each element in the output
  // list.
  //
  // For example:
  // 
  //   removeDupes2 (List (1,1,2,3,3,3,4,4,5,6,7,7,8,9,2,2,2,9))
  //   == List ((2,1),(1,2),(3,3),(2,4),(1,5),(1,6),(2,7),(1,8),(1,9),(3,2),(1,9))
  //
  def removeDupes2 [X] (xs:List[X]) : List[(Int, X)] = xs match {
	case Nil => Nil
	case y :: ys => {
		val (u, us) = xs.span(_ == y)
		(u.length, u.head) :: removeDupes2(us)
	}
}

  // EXERCISE 13: complete the following definition of a function that splits a list
  // into a pair of two lists.  The offset for the the split position is given
  // by the Int argument.
  // The behavior is determined by:
  //
  //   for all n, xs:
  //     splitAt (n, xs) == (take (n, xs), drop (n, xs))   
  //
  // Your definition of "splitAt" must be recursive and must not use "take" or "drop".
  //
  // Your definition of "splitAt" must only travere the list once.  So
  // you cannot define your own versions of "take"/"drop" and use them
  // (because that would entail one traversal of the list with "take"
  // and then a second traversal with "drop").


  def splitAt [X] (n:Int, xs:List[X]) : (List[X], List[X]) = (n, xs) match { 
	case (_, Nil) => (Nil, Nil) 
	case (i, ys) if i < 1 => (Nil, ys) 
	case (u, y :: ys) => {
		val (split1, split2) = splitAt(u-1, ys);
		(y :: split1, split2);
	}
}

  // EXERCISE 14: Write a recursive function "lineBreak" that takes an Int "n" and a
  // list "xs", then splits "xs" into a list of lists, where each list
  // except the last has length "n".  You should use the builtin "splitAt" from
  // Scala's List class.
  def lineBreak [X] (n:Int, xs:List[X]) : List[List[X]] = xs match {
	case Nil => Nil
	case xs if xs.length < n => List(xs)
	case _ => {
		val (split1, split2) = xs.splitAt(n)
		split1 :: lineBreak(n, split2)
	}
}

  // EXERCISE 15: Write a recursive function "splitAt2" that differs from "splitAt" by
  // only allowing breaks when a space is seen, i.e., it keeps going
  // when it has seen n characters until it sees the first space.  The
  // trailing space should be placed at the end of the first string in
  // the result pair.  The type is now limited to List[Char]
  //
  // For example:
  //
  //   scala> val (xs1, xs2) = splitAt2 (10, "The rain in Spain falls mainly on the plain.".toList)
  //   xs1: List[Char] = List(T, h, e,  , r, a, i, n,  , i, n,  )
  //   xs2: List[Char] = List(S, p, a, i, n,  , f, a, l, l, s,  , m, a, i, n, l, y,  , o, n,  , t, h, e,  , p, l, a, i, n, .)
  //
  //   scala> println (">>" + new String (xs1.toArray) + "<<")
  //   >>The rain in <<
  //
  //   scala> println (">>" + new String (xs2.toArray) + "<<")
  //   >>Spain falls mainly on the plain.<<
  //
  //   scala> val (xs3, xs4) = splitAt2 (10, xs2)
  //   xs3: List[Char] = List(S, p, a, i, n,  , f, a, l, l, s,  )
  //   xs4: List[Char] = List(m, a, i, n, l, y,  , o, n,  , t, h, e,  , p, l, a, i, n, .)
  //
  //   scala> val (xs5, xs6) = splitAt2 (10, xs4)
  //   xs5: List[Char] = List(m, a, i, n, l, y,  , o, n,  , t, h, e,  )
  //   xs6: List[Char] = List(p, l, a, i, n, .)



def splitAt2 (n:Int, xs:List[Char]) : (List[Char], List[Char]) = (n, xs) match {
	case (_, Nil) => (Nil, Nil)
	case (i, y::ys) if i < 1 => if (y == ' ') (y::Nil, ys) else {
		val (split1, split2) = splitAt2(0, ys)
		(y:: split1, split2)
	}
	case (u, y::ys) => {
		val (split1, split2) = splitAt2(u-1, ys)
		(y :: split1, split2)
	}
}



  // EXERCISE 16: Write a new "lineBreak2" that is the same as "lineBreak" but
  // calls "splitAt2" instead of "splitAt".
  //
  // For example:
  //
  //   scala> lineBreak2 (10, "The rain in Spain falls mainly on the plain.".toList)               
  //   res6: List[List[Char]] = List(List(T, h, e,  , r, a, i, n,  , i, n,  ), List(S, p, a, i, n,  , f, a, l, l, s,  ), List(m, a, i, n, l, y,  , o, n,  , t, h, e,  ), List(p, l, a, i, n, .))
  //
  //   scala> lineBreak2 (10, "The rain in Spain falls mainly on the plain.".toList).map (xs => new String (xs.toArray)).foreach (s => println (">>" + s + "<<"))
  //   >>The rain in <<
  //   >>Spain falls <<
  //   >>mainly on the <<
  //   >>plain.<<

  def lineBreak2 (n:Int, xs:List[Char]) : List[List[Char]] = xs match {
	case Nil => Nil
	case xs if xs.length < n => List(xs)
	case _ => {
		val (split1, split2) = splitAt2(n, xs)
		split1 :: lineBreak2(n, split2)
	}
}

}

// You must leave the following lines alone.  
// They run the tests on your definitions above.
// Altering it in your submitted file will result in 0 points for this assignment.

hw2tests.run (hw2)
