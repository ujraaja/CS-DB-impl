/* Generated By:JavaCC: Do not edit this line. grammar.java */
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

public class grammar implements grammarConstants {
  public static void main(String args []) throws ParseException, FileNotFoundException
  {
    if(args.length != 1) {
        System.out.println("Input file name is missing...");
        return;
    }
    System.setIn(new FileInputStream(args[0]));
    grammar parser = new grammar(System.in);
    new StorageManager();
    new TableManager();
    int i = 1;
    while (true)
    {
      try
      {
        Statement stmt = grammar.statement();
        if(stmt != null)
            stmt.execute();
      }
      catch (Exception e)
      {
        System.out.println("NOK.");
        System.out.println(e.getMessage());
        e.printStackTrace();
        grammar.ReInit(System.in);
        break;
      }
      catch (Error e)
      {
        System.out.println("Incorrect statement...");
        System.out.println(e.getMessage());
        e.printStackTrace();
        grammar.ReInit(System.in);
        break;
      }
    }
  }

  static final public classes.Integer integer() throws ParseException {
  classes.Integer itgr = new classes.Integer();
  String s = "";
  Token tok;
    label_1:
    while (true) {
      tok = jj_consume_token(digit);
                      s += tok;
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case digit:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
    }
    itgr.value = java.lang.Integer.parseInt(s);
    {if (true) return itgr;}
    throw new Error("Missing return statement in function");
  }

  static final public String table_name() throws ParseException {
  String s;
    s = attribute_name();
    {if (true) return s;}
    throw new Error("Missing return statement in function");
  }

  static final public String attribute_name() throws ParseException {
  String s = "";
  Token tok;
    tok = jj_consume_token(letter);
                             s += tok;
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case digit:
      case letter:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case digit:
        tok = jj_consume_token(digit);
                              s += tok;
        break;
      case letter:
        tok = jj_consume_token(letter);
                                 s += tok;
        break;
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    {if (true) return s;}
    throw new Error("Missing return statement in function");
  }

  static final public ColumnName column_name() throws ParseException {
  ColumnName columnName = new ColumnName();
    if (jj_2_1(2147483647)) {
      columnName.tableName = table_name();
      jj_consume_token(6);
    } else {
      ;
    }
    columnName.attribute = attribute_name();
    {if (true) return columnName;}
    throw new Error("Missing return statement in function");
  }

  static final public Literal literal() throws ParseException {
  Literal lit = new Literal();
  Token tok;
    tok = jj_consume_token(literal_tok);
    lit.value = tok.toString();
    lit.value = lit.value.substring(1, lit.value.length()-1);
    {if (true) return lit;}
    throw new Error("Missing return statement in function");
  }

  static final public List<AttributeType> attribute_type_list() throws ParseException {
  List<AttributeType> attributeTypes = new ArrayList<AttributeType>();
  String an;
  Token tok;
  AttributeType attributeType;
    an = attribute_name();
    jj_consume_token(7);
    tok = jj_consume_token(data_type);
            attributeType = new AttributeType();
            attributeType.attribute = an;
            attributeType.type = (tok.toString().equals("INT")?FieldType.INT:FieldType.STR20);
            attributeTypes.add(attributeType);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 8:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
      jj_consume_token(8);
      an = attribute_name();
      jj_consume_token(7);
      tok = jj_consume_token(data_type);
                attributeType = new AttributeType();
                attributeType.attribute = an;
                attributeType.type = (tok.toString().equals("INT")?FieldType.INT:FieldType.STR20);
                attributeTypes.add(attributeType);
    }
    {if (true) return attributeTypes;}
    throw new Error("Missing return statement in function");
  }

  static final public Term term() throws ParseException {
  Term term;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case letter:
      term = column_name();
      break;
    case digit:
      term = integer();
      break;
    case literal_tok:
      term = literal();
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return term;}
    throw new Error("Missing return statement in function");
  }

  static final public List<Term> value_list() throws ParseException {
  Term term;
  List<Term> terms = new ArrayList<Term>();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case digit:
      term = integer();
      break;
    case literal_tok:
      term = literal();
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
            terms.add(term);
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 8:
        ;
        break;
      default:
        jj_la1[6] = jj_gen;
        break label_4;
      }
      jj_consume_token(8);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case digit:
        term = integer();
        break;
      case literal_tok:
        term = literal();
        break;
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
              terms.add(term);
    }
    {if (true) return terms;}
    throw new Error("Missing return statement in function");
  }

  static final public Expression expression() throws ParseException {
  Expression exp = new Expression();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case digit:
    case letter:
    case literal_tok:
      exp.term1 = term();
      break;
    case 9:
      jj_consume_token(9);
      exp.term1 = term();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 10:
        jj_consume_token(10);
                exp.operator = '+';
        break;
      case 11:
        jj_consume_token(11);
                  exp.operator = '-';
        break;
      case 12:
        jj_consume_token(12);
                  exp.operator = '*';
        break;
      default:
        jj_la1[8] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      exp.term2 = term();
      jj_consume_token(13);
      break;
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return exp;}
    throw new Error("Missing return statement in function");
  }

  static final public BooleanFactor boolean_factor() throws ParseException {
  BooleanFactor bf = new BooleanFactor();
  Token tok;
    bf.expression1 = expression();
    jj_consume_token(7);
    tok = jj_consume_token(comp_op);
    jj_consume_token(7);
                              bf.operator = tok.toString().charAt(0);
    bf.expression2 = expression();
    {if (true) return bf;}
    throw new Error("Missing return statement in function");
  }

  static final public BooleanTerm boolean_term() throws ParseException {
  BooleanTerm bt = new BooleanTerm();
    bt.booleanFactor = boolean_factor();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 14:
      jj_consume_token(14);
      bt.booleanTerm = boolean_term();
      break;
    default:
      jj_la1[10] = jj_gen;
      ;
    }
    {if (true) return bt;}
    throw new Error("Missing return statement in function");
  }

  static final public SearchCondition search_condition() throws ParseException {
  SearchCondition sc = new SearchCondition();
    sc.booleanTerm = boolean_term();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 15:
      jj_consume_token(15);
      sc.searchCondition = search_condition();
      break;
    default:
      jj_la1[11] = jj_gen;
      ;
    }
    {if (true) return sc;}
    throw new Error("Missing return statement in function");
  }

  static final public InsertStatement insert_statement() throws ParseException {
  InsertStatement stmt = new InsertStatement();
  String s;
    jj_consume_token(16);
    stmt.tableName = table_name();
    jj_consume_token(17);
    s = attribute_name();
                           stmt.attributeList.add(s);
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 8:
        ;
        break;
      default:
        jj_la1[12] = jj_gen;
        break label_5;
      }
      jj_consume_token(8);
      s = attribute_name();
                             stmt.attributeList.add(s);
    }
    jj_consume_token(13);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 18:
      jj_consume_token(18);
      stmt.valueList = value_list();
      jj_consume_token(13);
      break;
    case 7:
      jj_consume_token(7);
      stmt.selectStatement = select_statement();
      break;
    default:
      jj_la1[13] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return stmt;}
    throw new Error("Missing return statement in function");
  }

  static final public DeleteStatement delete_statement() throws ParseException {
  DeleteStatement stmt = new DeleteStatement();
    jj_consume_token(19);
    stmt.tableName = table_name();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 20:
      jj_consume_token(20);
      stmt.searchCondition = search_condition();
      break;
    default:
      jj_la1[14] = jj_gen;
      ;
    }
    {if (true) return stmt;}
    throw new Error("Missing return statement in function");
  }

  static final public SelectStatement select_statement() throws ParseException {
  SelectStatement stmt = new SelectStatement();
  ColumnName cn;
  String s;
    jj_consume_token(21);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 22:
      jj_consume_token(22);
                  stmt.isDistinct = true;
      break;
    default:
      jj_la1[15] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 23:
      jj_consume_token(23);
          stmt.selectList = null;
      break;
    case letter:
      cn = column_name();
          stmt.selectList = new ArrayList<ColumnName>();
          stmt.selectList.add(cn);
      label_6:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 8:
          ;
          break;
        default:
          jj_la1[16] = jj_gen;
          break label_6;
        }
        jj_consume_token(8);
        cn = column_name();
                               stmt.selectList.add(cn);
      }
      break;
    default:
      jj_la1[17] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(24);
    s = table_name();
      stmt.tableList = new ArrayList<String>();
      stmt.tableList.add(s);
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 8:
        ;
        break;
      default:
        jj_la1[18] = jj_gen;
        break label_7;
      }
      jj_consume_token(8);
      s = table_name();
        stmt.tableList.add(s);
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 20:
      jj_consume_token(20);
      stmt.searchCondition = search_condition();
      break;
    default:
      jj_la1[19] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 25:
      jj_consume_token(25);
      stmt.columnName = column_name();
      break;
    default:
      jj_la1[20] = jj_gen;
      ;
    }
    {if (true) return stmt;}
    throw new Error("Missing return statement in function");
  }

  static final public CreateTableStatement create_table_statement() throws ParseException {
  CreateTableStatement stmt = new CreateTableStatement();
    jj_consume_token(26);
    stmt.tableName = table_name();
    jj_consume_token(17);
    stmt.attrTypeList = attribute_type_list();
    jj_consume_token(13);
    {if (true) return stmt;}
    throw new Error("Missing return statement in function");
  }

  static final public DropTableStatement drop_table_statement() throws ParseException {
  DropTableStatement stmt = new DropTableStatement();
    jj_consume_token(27);
    stmt.tableName = table_name();
    {if (true) return stmt;}
    throw new Error("Missing return statement in function");
  }

  static final public Statement statement() throws ParseException {
  Statement statement = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 16:
    case 19:
    case 21:
    case 26:
    case 27:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 26:
        statement = create_table_statement();
        break;
      case 27:
        statement = drop_table_statement();
        break;
      case 21:
        statement = select_statement();
        break;
      case 19:
        statement = delete_statement();
        break;
      case 16:
        statement = insert_statement();
        break;
      default:
        jj_la1[21] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[22] = jj_gen;
      ;
    }
    jj_consume_token(28);
    {if (true) return statement;}
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_3R_12() {
    if (jj_scan_token(letter)) return true;
    return false;
  }

  static private boolean jj_3_1() {
    if (jj_3R_8()) return true;
    if (jj_scan_token(6)) return true;
    return false;
  }

  static private boolean jj_3R_11() {
    if (jj_scan_token(digit)) return true;
    return false;
  }

  static private boolean jj_3R_10() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_11()) {
    jj_scanpos = xsp;
    if (jj_3R_12()) return true;
    }
    return false;
  }

  static private boolean jj_3R_8() {
    if (jj_3R_9()) return true;
    return false;
  }

  static private boolean jj_3R_9() {
    if (jj_scan_token(letter)) return true;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_10()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public grammarTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[23];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x2,0x6,0x6,0x100,0x26,0x22,0x100,0x22,0x1c00,0x226,0x4000,0x8000,0x100,0x40080,0x100000,0x400000,0x100,0x800004,0x100,0x100000,0x2000000,0xc290000,0xc290000,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[1];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public grammar(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public grammar(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new grammarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public grammar(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new grammarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public grammar(grammarTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(grammarTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[29];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 23; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 29; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

  static private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
