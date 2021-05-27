package com.hiramsoft.commons.jsalparser;// Generated from S3Tokens.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class S3TokensParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NoValue=1, SimpleValue=2, DateValue=3, QuotedValue=4, DoubleQuotedValue=5, 
		LINEBREAK=6, DELIM=7, ESCAPED_QUOTE=8, ALLOWED_CHAR=9;
	public static final int
		RULE_value = 0;
	public static final String[] ruleNames = {
		"value"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'-'", null, null, null, null, null, "' '"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "NoValue", "SimpleValue", "DateValue", "QuotedValue", "DoubleQuotedValue", 
		"LINEBREAK", "DELIM", "ESCAPED_QUOTE", "ALLOWED_CHAR"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "S3Tokens.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public S3TokensParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ValueContext extends ParserRuleContext {
		public String val;
		public Token SimpleValue;
		public Token DateValue;
		public Token QuotedValue;
		public Token DoubleQuotedValue;
		public TerminalNode SimpleValue() { return getToken(S3TokensParser.SimpleValue, 0); }
		public TerminalNode DateValue() { return getToken(S3TokensParser.DateValue, 0); }
		public TerminalNode QuotedValue() { return getToken(S3TokensParser.QuotedValue, 0); }
		public TerminalNode DoubleQuotedValue() { return getToken(S3TokensParser.DoubleQuotedValue, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof S3TokensListener ) ((S3TokensListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof S3TokensListener ) ((S3TokensListener)listener).exitValue(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_value);
		try {
			setState(10);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SimpleValue:
				enterOuterAlt(_localctx, 1);
				{
				setState(2);
				((ValueContext)_localctx).SimpleValue = match(SimpleValue);
				((ValueContext)_localctx).val =  (((ValueContext)_localctx).SimpleValue!=null?((ValueContext)_localctx).SimpleValue.getText():null);
				}
				break;
			case DateValue:
				enterOuterAlt(_localctx, 2);
				{
				setState(4);
				((ValueContext)_localctx).DateValue = match(DateValue);

						((ValueContext)_localctx).val =  (((ValueContext)_localctx).DateValue!=null?((ValueContext)_localctx).DateValue.getText():null);
						((ValueContext)_localctx).val =  _localctx.val.substring(1, _localctx.val.length()-1);
					
				}
				break;
			case QuotedValue:
				enterOuterAlt(_localctx, 3);
				{
				setState(6);
				((ValueContext)_localctx).QuotedValue = match(QuotedValue);

							((ValueContext)_localctx).val =  (((ValueContext)_localctx).QuotedValue!=null?((ValueContext)_localctx).QuotedValue.getText():null);
							((ValueContext)_localctx).val =  _localctx.val.substring(1, _localctx.val.length()-1);
							((ValueContext)_localctx).val =  _localctx.val.replace("\\\"", "\""); // unescape the quotes
						
				}
				break;
			case DoubleQuotedValue:
				enterOuterAlt(_localctx, 4);
				{
				setState(8);
				((ValueContext)_localctx).DoubleQuotedValue = match(DoubleQuotedValue);

							((ValueContext)_localctx).val =  (((ValueContext)_localctx).DoubleQuotedValue!=null?((ValueContext)_localctx).DoubleQuotedValue.getText():null);
							((ValueContext)_localctx).val =  _localctx.val.substring(2, _localctx.val.length()-2);
							((ValueContext)_localctx).val =  _localctx.val.replace("\\\"\\\"", "\"\""); // unescape the quotes
						
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13\17\4\2\t\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2\r\n\2\3\2\2\2\3\2\2\2\2\20\2\f\3\2\2"+
		"\2\4\5\7\4\2\2\5\r\b\2\1\2\6\7\7\5\2\2\7\r\b\2\1\2\b\t\7\6\2\2\t\r\b\2"+
		"\1\2\n\13\7\7\2\2\13\r\b\2\1\2\f\4\3\2\2\2\f\6\3\2\2\2\f\b\3\2\2\2\f\n"+
		"\3\2\2\2\r\3\3\2\2\2\3\f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}