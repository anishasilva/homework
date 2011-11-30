//Is it working?
//Yes, it is working.  parses pong.el and doesn't work on this file as well as "foo, bar".

import java.io.{BufferedReader,FileReader,Reader}
import scala.util.parsing.combinator._

object LispParser extends JavaTokenParsers {

  // BEGIN CHANGES HERE

  def sexp:Parser[Any] = 
    "(" ~ rep (sexp) ~ ")" |
     variable ~ rep(sexp) |
     """"""" ~ stringChars ~ """"""" ~ rep (sexp) |
     ":" ~ rep(sexp) |
     "[" ~ rep (sexp) ~ "]" |
     "+" ~ rep (sexp) |
     "=" ~ rep (sexp) |
     "*" ~ rep (sexp) |
     "%" ~ rep (sexp) |
     """/""" ~ rep (sexp) |
     """\""" ~ rep (sexp) |
     """<""" ~ rep (sexp) |
     """>""" ~ rep (sexp) |
     """{""" ~ rep (sexp) |
     """}""" ~ rep (sexp) 
     
    // Add more cases to "sexp".
    // Don't forget to put "|" at the end of all but the last line.

  // You can (and should) add more definitions here.

  def variable:Parser[Any] = """[- '_a-zA-Z0-9?][- ._0-9a-zA-Z?]*""".r
  def stringChars:Parser[Any] = """[-.\/:%<>{}(),* A-z0-9]+""".r


  // END CHANGES HERE

  def prog:Parser[Any] = 
    rep (sexp)

  def test (filename:String) = {
    val reader = new BufferedReader (new FileReader (filename))
    parseAll (prog, reader) match {
      case Success (ast, _) => 
        println ("Successfully parsed: " + filename)
        println ("Produced: " + ast)
      case x => println ("Failed to parse: " + filename + " due to: " + x)
    }
    reader.close
  }

  def main (args:Array[String]) {
    for (arg <- args) {
      test (arg)
    }
  }
}
