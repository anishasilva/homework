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
 * 2. Download "hw1tests.jar".
 *
 * 3. Run the tests on your code with:
 *
 *    scala -cp hw1tests.jar hw1.scala
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
 * 1. Submit hw1.scala as HW1 in the Desire2Learn dropbox before the deadline.
 *    The file you submit must be ready to run, i.e., do not submit a ZIP or RAR file containing
 *    hw1.scala; do not submit a Word document containing your solutions, etc.
 *
 * 2. Late submissions will not be permitted because solutions will be discussed in class.
 * 
 */


// EXAMPLE: declare the identifier "s1" with the Int value 7
val s1:Int = 7

// EXERCISE 1: declare the identifier "b1" with the Boolean value true.
// In this, AND ALL SUBSEQUENT EXERCISES, include the type of the identifier
// being defined (as in "s1:Int" above).
val b1:Boolean = true

// EXAMPLE: declare the identifier "s2" with an Int expression 9 * 7
val s2:Int = 9 * 7

// EXERCISE 2: declare the identifier "s3" with an Int expression 5 / 2
val s3:Int = 5/2

// EXERCISE 3: declare the identifier "p1" with a pair consisting of
// the Int 7 and the String "hello"
val p1:Pair[Int, String] = (7, "hello")


// EXERCISE 4: declare the identifier "t1" with a triple consisting of
// the Int 7, the String "hello", and the Boolean false
val t1:Triple[Int, String, Boolean] = (7, "hello", false)


// EXERCISE 5: declare the identifier "l1" with a list consisting of
// the Int values from 10 down to 5 (inclusive).  You must use the List 
// constructor.
val l1:List[Int] = List(10,9,8,7,6,5)


// EXERCISE 6: declare the identifier "l2" with a list consisting of
// the Int values from 15 up to 20 (inclusive).  You must use the List 
// constructor.
val l2:List[Int] = List(15,16,17,18,19,20)


// EXERCISE 7: declare the identifier "l3" with a list consisting of
// the String values "a", "b", "c".  You must
// use the List constructor.
val l3:List[String] = List("a","b","c")


// EXERCISE 8: write a function "add4" that takes 4 Int arguments and 
// returns their sum.
  def add4 (w:Int, x:Int, y:Int, z:Int) : Int = (w+x+y+z) 

// EXERCISE 9: write a function "split" that takes a pair of an Int and 
// a String, and returns a pair of a String and an Int (with the values from 
// the pair passed an argument.  E.g., split (p1) should return ("hello", 7).
// You can use "p._1" and "p._2" to access the first and second components
// of a pair.
def split (p:(Int,String)) : (String,Int) = p match { case (x,y) => (y,x) }

// EXERCISE 10: Write a recursive function "otpu" ("upto" backwards)
// that takes two Int parameters "start" and "end" and produces a "List[Int]"
// that counts DOWN from "start" to "end" (inclusive at both ends) one at
// a time.  If "start < end", the empty list must be returned.
def otpu(start:Int, end:Int) : List[Int] = if (start<end)  Nil else start :: otpu(start-1, end)



// You must leave the following line alone.  
// It runs the tests on your definitions above.
// Altering it in your submitted file will result in 0 points for this assignment.

hw1tests.run (b1, s3, p1, t1, l1, l2, l3, add4, split, otpu)

