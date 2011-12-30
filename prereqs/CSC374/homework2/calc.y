%{
	
	
#include	<cstdlib>
#include	<cstdio>
#include	<cstring>
#include	<cctype>
#include	<iostream>
#include	<cmath>
	//#include	<fstream>
	
	using namespace	std;
	
	const int	MAX_STRING		= 80;
	
	const int	SUCCESS			= 0;
	
	const int	FAILURE			= -1;
	
	const char	QUOTE_CHAR		= '"';
	
	const char	DECIMAL_PT_CHAR		= '.';
	
	const char	EXPONENT_CHAR1		= 'e';
	
	const char	EXPONENT_CHAR2		= 'E';
	
	const char	COMMENT_CHAR		= '#';
	
	const int	YACC_END_TEXT_LEX	= 0;
	
#define	YYSTYPE			double
	
	extern	double			answer;
	
	int	yylex	();
	
	void	yyerror	(const char*);
	
	%}


%start		expression

%left		PLUS_LEX MINUS_LEX
%left 		STAR_LEX SLASH_LEX MOD_LEX
%right		CARET_LEX
%nonassoc	NUMBER_LEX
%nonassoc	STRING_LEX
%nonassoc	BEGIN_PAREN_LEX END_PAREN_LEX

%%		// Rules start here


expression :	expression PLUS_LEX term
{
	answer = $$ = $1 + $3;
}
|	expression MINUS_LEX term
{
	answer = $$ = $1 - $3;
}
|	term
{
	answer = $$ = $1;
};

term :		term STAR_LEX factor
{
	$$ = $1 * $3;
}
|	term SLASH_LEX factor
{
	$$ = $1 / $3;
}
|	factor
{
	$$ = $1;
}
|	term MOD_LEX factor
{
	$$ = int($1) % int($3);
};

factor : 	var CARET_LEX factor
{
	$$ = pow($1,$3);
}
|	var
{
	$$ = $1;
};

var :		BEGIN_PAREN_LEX expression END_PAREN_LEX
{
	$$ = $2;
}
|	NUMBER_LEX
{
	$$ = $1;
};


%%

double			answer;


/*  PURPOSE:  To hold the current position in the current line of input.
 */
char*		line_ptr = NULL;



/*  PURPOSE:  To return _true_ if there is no more input.  No parameters.
 */
bool	eof_	()	{ return(*line_ptr == '\0'); }



/*  PURPOSE:  To return the next input character or EOF if there are no more.
 *	No parameters.
 */
int	fgetc_	()	{ return( eof_() ? EOF : *line_ptr++ ); }



/*  PURPOSE:  To place C back in the input stream.  No return value.
 */
void	ungetc_	(int c)	{ *--line_ptr = c; }



/*  PURPOSE: To read a complete text lexeme, complete number lexeme, or
 individual other character and place in LEXEME the first LEXEME_SPACE
 characters of the lexeme.  Returns SUCCESS if a lexeme placed in
 LEXEME, FAILURE if encounter EOF, or ERROR on error.
 */
int	get_lexeme	(char*	lexeme,
				 int	lexeme_space // = MAX_STRING
				 )
throw(const char*)

{
	
    //	I.  Parameter validity check:
	
    if	(lexeme == NULL)
		throw "NULL ptr to get_lexeme()";
	
	
    //	II.  Get the lexeme:
	
    int	index = 0;
	
    while  ( !eof_() )
	{
		int c = fgetc_();
		
		
		//  Double check EOF:
		
		if  (c == EOF)
			return(FAILURE);
		
		
		//  Read past whitespace:
		
		if  ( isspace(c) )
			continue;
		
		
		if  (c == COMMENT_CHAR)			//  Read past comments:
	    {
			
			//	Read until EOF or EOLN:
			
			while  ( !eof_() )
				if  ( fgetc_() ==  '\n' )
					break;
			
			if  ( eof_() )
				return(FAILURE);
			
	    }
		else  if  ( isalpha(c)  ||  (c == '_') )	//  Read text lexeme:
	    {
			lexeme[index++] = c;
			
			
			//	Read until EOF, end of text, or out of space:
			
			while  ( !eof_()  &&  (index < (lexeme_space-1)) )
			{
				c = fgetc_();
				
				if  (c == EOF)
					break;
				
				if  ( !isalnum(c)  &&  (c != '_') )
				{
					ungetc_(c);
					break;
				}
				
				lexeme[index++] = c;
			}
			
			
			//  End lexeme string:
			
			lexeme[index] = '\0';
			
			
			//	Consume remainder of text lexmme (if any):
			
			if  ( (index == (lexeme_space-1))  &&  !eof_()  &&
				 ( isalnum(c) || (c == '_') )
				 )
			{
				bool	have_read_extra_char  = false;
				
				
				//  Get the text:
				
				do  {
					c = fgetc_();
					
					if  (c == EOF)
						break;
					
					if  ( !isalnum(c)  &&  (c != '_') )
					{
						ungetc_(c);
						break;
					}
					
					have_read_extra_char = true;
				}
				while  ( !eof_() );
				
			}
			
			
			return(SUCCESS);
	    }
		else  if  ( isdigit(c)  ||  (c == '-') )	//  Read numeric lexeme:
	    {
			bool	hav_seen_decimal_pt	= false;
			
			lexeme[index++] = c;
			
			
			//  If readed a minus sign, enshur it's part of a number:
			
			if  (c == '-')
			{
				
				//  Check if it's the last char:
				
				if  ( eof_() )
				{
					lexeme[index] = '\0';
					return(SUCCESS);
				}
				
				c = fgetc_();
				
				if  (c == EOF)
				{
					lexeme[index] = '\0';
					return(SUCCESS);
				}
				
				
				//  See if next char is digit:
				
				if  ( ! isdigit(c) )
				{
					ungetc_(c);
					lexeme[index] = '\0';
					return(SUCCESS);
				}
				
				
				//  If it is a digit, put it back and continue reading number:
				
				ungetc_(c);
			}
			
			
			//	Read until EOF, end of digits, or out of space:
			
			while  ( !eof_()  &&  (index < (lexeme_space-1)) )
			{
				c = fgetc_();
				
				if  (c == EOF)
					break;
				
				if  (c == DECIMAL_PT_CHAR)
				{
					
					if  (hav_seen_decimal_pt)
					{
						ungetc_(c);
						break;
					}
					
					hav_seen_decimal_pt = true;
				}
				else  if  ( !isdigit(c) )
				{
					
					//	Read exponent of scientific notation:
					
					if  ( (c == EXPONENT_CHAR1)  ||  (c == EXPONENT_CHAR2) )
					{
						int exp_char = c;
						
						
						// Read expoenent sign:
						
						int sign_char = fgetc_();
						
						if  (sign_char == EOF)
						{
							ungetc_(exp_char);
							break;
						}
						
						if  ( (sign_char != '+')  &&  (sign_char != '-') )
						{
							ungetc_(sign_char);
							ungetc_(exp_char);
							break;
						}
						
						
						//  Read 1st digit of exponent magnitude:
						
						int digit_char = fgetc_();
						
						if  (digit_char == EOF)
						{
							ungetc_(sign_char);
							ungetc_(exp_char);
							break;
						}
						
						if  ( !isdigit(digit_char) )
						{
							ungetc_(digit_char);
							ungetc_(sign_char);
							ungetc_(exp_char);
							break;
						}
						
						
						//  Successive chars are definitely for an exponent:
						//  put them in LEXEME (as many as will fit) and
						//  gather additional exponent digits:
						
						lexeme[index++] = exp_char;
						
						if  (index >= (lexeme_space-1))
						{
							ungetc_(digit_char);
							ungetc_(sign_char);
							break;
						}
						
						lexeme[index++] = sign_char;
						
						if  (index >= (lexeme_space-1))
						{
							ungetc_(digit_char);
							break;
						}
						
						lexeme[index++] = digit_char;
						
						while  (index < (lexeme_space-1))
						{
							
							if  ( (digit_char = fgetc_()) == EOF )
								break;
							
							if  ( !isdigit(digit_char) )
							{
								ungetc_(digit_char);
								break;
							}
							
							lexeme[index++] = digit_char;
						}
						
						break;
					}
					
					ungetc_(c);
					break;
				}
				
				lexeme[index++] = c;
			}
			
			
			//  End lexeme string:
			
			lexeme[index] = '\0';
			
			
			//	Consume remainder of numeric lexmme (if any):
			
			if  ( (index == (lexeme_space-1))  &&  !eof_()  &&
				 ( isalnum(c) || (c == '_') )
				 )
			{
				bool	have_read_extra_char  = false;
				
				
				//  Get the chars:
				
				do  {
					c = fgetc_();
					
					if  (c == EOF)
						break;
					
					if  (c == DECIMAL_PT_CHAR)
					{
						
						if  (hav_seen_decimal_pt)
						{
							ungetc_(c);
							break;
						}
						
						hav_seen_decimal_pt = true;
					}
					else  if  ( !isdigit(c) )
					{
						ungetc_(c);
						break;
					}
					
					have_read_extra_char = true;
				}
				while  ( !eof_() );
				
			}
			
			
			return(SUCCESS);
	    }
		else  if  (c == QUOTE_CHAR)		// Read quote-delinated string
	    {
			
			//	Read until EOF, see ending quote, or out of space:
			
			while  ( !eof_()  &&  (index < (lexeme_space-1)) )
			{
				c = fgetc_();
				
				if  ( (c == QUOTE_CHAR)  ||  (c == EOF) )
					break;
				
				lexeme[index++] = c;
			}
			
			
			//  End lexeme string:
			
			lexeme[index] = '\0';
			return(SUCCESS);
	    }
		else if ((c=='<')||(c=='>')||(c=='=')||(c=='!')) // Read C-style compare
	    {
			lexeme[0] = c;
			
			int next = fgetc_();	// Inspect next char:
			
			if  (next == '=')
			{
				lexeme[1] = next;
				lexeme[2] = '\0';
			}
			else
			{
				ungetc_(next);
				lexeme[1] = '\0';
			}
			
			return(SUCCESS);
	    }
		else
	    {
			lexeme[0] = c;
			lexeme[1] = '\0';
			return(SUCCESS);
	    }
		
	}
	
	
    //	Get here only if have seen EOF:
	
    return(FAILURE);
}



/*  PURPOSE: To get the next lexeme text, set YYLVAL to its corresponding
 *	value, and to return the corresponding lexeme code.
 */
int	yylex	()

{
    char	text[MAX_STRING];
	
    if  (get_lexeme(text,MAX_STRING) == FAILURE)
		return(0);
	
    yylval = 0.0;
	
    if  ( strcmp(text,"+") == 0) return(PLUS_LEX);
    if  ( strcmp(text,"-") == 0) return(MINUS_LEX);
    if  ( strcmp(text,"*") == 0) return(STAR_LEX);
    if  ( strcmp(text,"/") == 0) return(SLASH_LEX);
    if  ( strcmp(text,"^") == 0) return(CARET_LEX);
    if  ( strcmp(text,"(") == 0) return(BEGIN_PAREN_LEX);
    if  ( strcmp(text,")") == 0) return(END_PAREN_LEX);
    if  ( strcmp(text,"@") == 0) return(MOD_LEX);
	
    if  ( isdigit(text[0]) )
	{
		yylval = atof(text);
		return(NUMBER_LEX);
	}
	
    if  ( isalpha(text[0])  ||  (text[0] == '_') )
	{
		return(STRING_LEX);
	}
	
    cerr << "What is \"" << text << "\"?" << endl;
    return(-1);
}


void	yyerror	(const char* s)

{
    cerr << "Parse error:" << s << endl;
}



int	main	(int argc, char* argv[])

{
	
    if  (argc < 2)
	{
		cerr << "Usage: calc <quotedStringWithExpression>" << endl;
		return(EXIT_FAILURE);
	}
	
    line_ptr = argv[1];
	
    if  (yyparse() != SUCCESS)
	{
		return(EXIT_FAILURE);
	}
	
    cout << answer << endl;
    return(EXIT_SUCCESS);
}