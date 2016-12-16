/* Generated By:JavaCC: Do not edit this line. grammarTokenManager.java */
package parser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import classes.AttributeType;
import classes.BooleanFactor;
import classes.BooleanTerm;
import classes.ColumnName;
import classes.CreateTableStatement;
import classes.DeleteStatement;
import classes.DropTableStatement;
import classes.Expression;
import classes.InsertStatement;
import classes.Literal;
import classes.SearchCondition;
import classes.SelectStatement;
import classes.Statement;
import classes.Term;
import lqp.TableManager;
import storageManager.FieldType;
import storageManager.StorageManager;

/** Token Manager. */
public class grammarTokenManager implements grammarConstants
{

  /** Debug output. */
  public static  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public static  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private static final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x10000L) != 0L)
            return 4;
         if ((active0 & 0x200000L) != 0L)
            return 9;
         return -1;
      case 1:
         if ((active0 & 0x10000L) != 0L)
            return 3;
         return -1;
      default :
         return -1;
   }
}
private static final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
static private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
static private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 10:
         return jjStopAtPos(0, 28);
      case 32:
         jjmatchedKind = 7;
         return jjMoveStringLiteralDfa1_0(0x316dc00L);
      case 40:
         return jjStopAtPos(0, 9);
      case 41:
         return jjStopAtPos(0, 13);
      case 42:
         return jjStopAtPos(0, 23);
      case 44:
         return jjMoveStringLiteralDfa1_0(0x100L);
      case 46:
         return jjStopAtPos(0, 6);
      case 67:
         return jjMoveStringLiteralDfa1_0(0x4000000L);
      case 68:
         return jjMoveStringLiteralDfa1_0(0x8480000L);
      case 73:
         return jjMoveStringLiteralDfa1_0(0x10000L);
      case 83:
         return jjMoveStringLiteralDfa1_0(0x200000L);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
static private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 32:
         if ((active0 & 0x100L) != 0L)
            return jjStopAtPos(1, 8);
         break;
      case 40:
         if ((active0 & 0x20000L) != 0L)
            return jjStopAtPos(1, 17);
         break;
      case 42:
         return jjMoveStringLiteralDfa2_0(active0, 0x1000L);
      case 43:
         return jjMoveStringLiteralDfa2_0(active0, 0x400L);
      case 45:
         return jjMoveStringLiteralDfa2_0(active0, 0x800L);
      case 65:
         return jjMoveStringLiteralDfa2_0(active0, 0x4000L);
      case 69:
         return jjMoveStringLiteralDfa2_0(active0, 0x280000L);
      case 70:
         return jjMoveStringLiteralDfa2_0(active0, 0x1000000L);
      case 73:
         return jjMoveStringLiteralDfa2_0(active0, 0x400000L);
      case 78:
         return jjMoveStringLiteralDfa2_0(active0, 0x10000L);
      case 79:
         return jjMoveStringLiteralDfa2_0(active0, 0x2008000L);
      case 82:
         return jjMoveStringLiteralDfa2_0(active0, 0xc000000L);
      case 86:
         return jjMoveStringLiteralDfa2_0(active0, 0x40000L);
      case 87:
         return jjMoveStringLiteralDfa2_0(active0, 0x100000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
static private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 32:
         if ((active0 & 0x400L) != 0L)
            return jjStopAtPos(2, 10);
         else if ((active0 & 0x800L) != 0L)
            return jjStopAtPos(2, 11);
         else if ((active0 & 0x1000L) != 0L)
            return jjStopAtPos(2, 12);
         break;
      case 65:
         return jjMoveStringLiteralDfa3_0(active0, 0x40000L);
      case 69:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000000L);
      case 72:
         return jjMoveStringLiteralDfa3_0(active0, 0x100000L);
      case 76:
         return jjMoveStringLiteralDfa3_0(active0, 0x280000L);
      case 78:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000L);
      case 79:
         return jjMoveStringLiteralDfa3_0(active0, 0x8000000L);
      case 82:
         return jjMoveStringLiteralDfa3_0(active0, 0x3008000L);
      case 83:
         return jjMoveStringLiteralDfa3_0(active0, 0x410000L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
static private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 32:
         if ((active0 & 0x8000L) != 0L)
            return jjStopAtPos(3, 15);
         break;
      case 65:
         return jjMoveStringLiteralDfa4_0(active0, 0x4000000L);
      case 68:
         return jjMoveStringLiteralDfa4_0(active0, 0x2004000L);
      case 69:
         return jjMoveStringLiteralDfa4_0(active0, 0x390000L);
      case 76:
         return jjMoveStringLiteralDfa4_0(active0, 0x40000L);
      case 79:
         return jjMoveStringLiteralDfa4_0(active0, 0x1000000L);
      case 80:
         return jjMoveStringLiteralDfa4_0(active0, 0x8000000L);
      case 84:
         return jjMoveStringLiteralDfa4_0(active0, 0x400000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
static private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 32:
         if ((active0 & 0x4000L) != 0L)
            return jjStopAtPos(4, 14);
         return jjMoveStringLiteralDfa5_0(active0, 0x8000000L);
      case 67:
         return jjMoveStringLiteralDfa5_0(active0, 0x200000L);
      case 69:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000000L);
      case 73:
         return jjMoveStringLiteralDfa5_0(active0, 0x400000L);
      case 77:
         return jjMoveStringLiteralDfa5_0(active0, 0x1000000L);
      case 82:
         return jjMoveStringLiteralDfa5_0(active0, 0x110000L);
      case 84:
         return jjMoveStringLiteralDfa5_0(active0, 0x4080000L);
      case 85:
         return jjMoveStringLiteralDfa5_0(active0, 0x40000L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
static private int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 32:
         if ((active0 & 0x1000000L) != 0L)
            return jjStopAtPos(5, 24);
         break;
      case 69:
         return jjMoveStringLiteralDfa6_0(active0, 0x41c0000L);
      case 78:
         return jjMoveStringLiteralDfa6_0(active0, 0x400000L);
      case 82:
         return jjMoveStringLiteralDfa6_0(active0, 0x2000000L);
      case 84:
         return jjMoveStringLiteralDfa6_0(active0, 0x8210000L);
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
static private int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 32:
         if ((active0 & 0x100000L) != 0L)
            return jjStopAtPos(6, 20);
         else if ((active0 & 0x200000L) != 0L)
            return jjStopAtPos(6, 21);
         return jjMoveStringLiteralDfa7_0(active0, 0x6090000L);
      case 65:
         return jjMoveStringLiteralDfa7_0(active0, 0x8000000L);
      case 67:
         return jjMoveStringLiteralDfa7_0(active0, 0x400000L);
      case 83:
         return jjMoveStringLiteralDfa7_0(active0, 0x40000L);
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
static private int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0);
      return 7;
   }
   switch(curChar)
   {
      case 32:
         return jjMoveStringLiteralDfa8_0(active0, 0x40000L);
      case 66:
         return jjMoveStringLiteralDfa8_0(active0, 0xa000000L);
      case 70:
         return jjMoveStringLiteralDfa8_0(active0, 0x80000L);
      case 73:
         return jjMoveStringLiteralDfa8_0(active0, 0x10000L);
      case 84:
         return jjMoveStringLiteralDfa8_0(active0, 0x4400000L);
      default :
         break;
   }
   return jjStartNfa_0(6, active0);
}
static private int jjMoveStringLiteralDfa8_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(6, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0);
      return 8;
   }
   switch(curChar)
   {
      case 32:
         if ((active0 & 0x400000L) != 0L)
            return jjStopAtPos(8, 22);
         break;
      case 40:
         if ((active0 & 0x40000L) != 0L)
            return jjStopAtPos(8, 18);
         break;
      case 65:
         return jjMoveStringLiteralDfa9_0(active0, 0x4000000L);
      case 76:
         return jjMoveStringLiteralDfa9_0(active0, 0x8000000L);
      case 78:
         return jjMoveStringLiteralDfa9_0(active0, 0x10000L);
      case 82:
         return jjMoveStringLiteralDfa9_0(active0, 0x80000L);
      case 89:
         return jjMoveStringLiteralDfa9_0(active0, 0x2000000L);
      default :
         break;
   }
   return jjStartNfa_0(7, active0);
}
static private int jjMoveStringLiteralDfa9_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(7, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(8, active0);
      return 9;
   }
   switch(curChar)
   {
      case 32:
         if ((active0 & 0x2000000L) != 0L)
            return jjStopAtPos(9, 25);
         break;
      case 66:
         return jjMoveStringLiteralDfa10_0(active0, 0x4000000L);
      case 69:
         return jjMoveStringLiteralDfa10_0(active0, 0x8000000L);
      case 79:
         return jjMoveStringLiteralDfa10_0(active0, 0x80000L);
      case 84:
         return jjMoveStringLiteralDfa10_0(active0, 0x10000L);
      default :
         break;
   }
   return jjStartNfa_0(8, active0);
}
static private int jjMoveStringLiteralDfa10_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(8, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(9, active0);
      return 10;
   }
   switch(curChar)
   {
      case 32:
         if ((active0 & 0x8000000L) != 0L)
            return jjStopAtPos(10, 27);
         break;
      case 76:
         return jjMoveStringLiteralDfa11_0(active0, 0x4000000L);
      case 77:
         return jjMoveStringLiteralDfa11_0(active0, 0x80000L);
      case 79:
         return jjMoveStringLiteralDfa11_0(active0, 0x10000L);
      default :
         break;
   }
   return jjStartNfa_0(9, active0);
}
static private int jjMoveStringLiteralDfa11_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(9, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(10, active0);
      return 11;
   }
   switch(curChar)
   {
      case 32:
         if ((active0 & 0x10000L) != 0L)
            return jjStopAtPos(11, 16);
         else if ((active0 & 0x80000L) != 0L)
            return jjStopAtPos(11, 19);
         break;
      case 69:
         return jjMoveStringLiteralDfa12_0(active0, 0x4000000L);
      default :
         break;
   }
   return jjStartNfa_0(10, active0);
}
static private int jjMoveStringLiteralDfa12_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(10, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(11, active0);
      return 12;
   }
   switch(curChar)
   {
      case 32:
         if ((active0 & 0x4000000L) != 0L)
            return jjStopAtPos(12, 26);
         break;
      default :
         break;
   }
   return jjStartNfa_0(11, active0);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 14;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 1)
                        kind = 1;
                  }
                  else if ((0x7000000000000000L & l) != 0L)
                  {
                     if (kind > 3)
                        kind = 3;
                  }
                  else if (curChar == 34)
                     jjCheckNAddTwoStates(12, 13);
                  break;
               case 2:
                  if ((0x7000000000000000L & l) != 0L && kind > 3)
                     kind = 3;
                  break;
               case 6:
                  if (curChar == 48 && kind > 4)
                     kind = 4;
                  break;
               case 7:
                  if (curChar == 50)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 11:
                  if (curChar == 34)
                     jjCheckNAddTwoStates(12, 13);
                  break;
               case 12:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(12, 13);
                  break;
               case 13:
                  if (curChar == 34 && kind > 5)
                     kind = 5;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x7fffffe00000000L & l) != 0L)
                  {
                     if (kind > 2)
                        kind = 2;
                  }
                  else if (curChar == 83)
                     jjstateSet[jjnewStateCnt++] = 9;
                  else if (curChar == 73)
                     jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 1:
                  if ((0x7fffffe00000000L & l) != 0L && kind > 2)
                     kind = 2;
                  break;
               case 3:
                  if (curChar == 84 && kind > 4)
                     kind = 4;
                  break;
               case 4:
                  if (curChar == 78)
                     jjstateSet[jjnewStateCnt++] = 3;
                  break;
               case 5:
                  if (curChar == 73)
                     jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 8:
                  if (curChar == 82)
                     jjstateSet[jjnewStateCnt++] = 7;
                  break;
               case 9:
                  if (curChar == 84)
                     jjstateSet[jjnewStateCnt++] = 8;
                  break;
               case 10:
                  if (curChar == 83)
                     jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 12:
                  jjAddStates(0, 1);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 12:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(0, 1);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 14 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   12, 13, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, "\56", "\40", "\54\40", "\50", "\40\53\40", 
"\40\55\40", "\40\52\40", "\51", "\40\101\116\104\40", "\40\117\122\40", 
"\111\116\123\105\122\124\40\111\116\124\117\40", "\40\50", "\40\126\101\114\125\105\123\40\50", 
"\104\105\114\105\124\105\40\106\122\117\115\40", "\40\127\110\105\122\105\40", "\123\105\114\105\103\124\40", 
"\104\111\123\124\111\116\103\124\40", "\52", "\40\106\122\117\115\40", "\40\117\122\104\105\122\40\102\131\40", 
"\103\122\105\101\124\105\40\124\101\102\114\105\40", "\104\122\117\120\40\124\101\102\114\105\40", "\12", };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static protected SimpleCharStream input_stream;
static private final int[] jjrounds = new int[14];
static private final int[] jjstateSet = new int[28];
static protected char curChar;
/** Constructor. */
public grammarTokenManager(SimpleCharStream stream){
   if (input_stream != null)
      throw new TokenMgrError("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", TokenMgrError.STATIC_LEXER_ERROR);
   input_stream = stream;
}

/** Constructor. */
public grammarTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
static public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
static private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 14; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
static public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
static public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

static protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

static int curLexState = 0;
static int defaultLexState = 0;
static int jjnewStateCnt;
static int jjround;
static int jjmatchedPos;
static int jjmatchedKind;

/** Get the next Token. */
public static Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
         matchedToken = jjFillToken();
         return matchedToken;
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

static private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
static private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
static private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

}