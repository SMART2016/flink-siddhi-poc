// Generated from S3RawLogGrammar.g4 by ANTLR 4.7.1


package com.hiramsoft.commons.jsalparser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class S3RawLogGrammarParser extends Parser {
    static {
        RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            NoValue = 1, SimpleValue = 2, DateValue = 3, QuotedValue = 4, DoubleQuotedValue = 5,
            LINEBREAK = 6, DELIM = 7, ESCAPED_QUOTE = 8, ALLOWED_CHAR = 9;
    public static final int
            RULE_file = 0, RULE_row = 1, RULE_value = 2;
    public static final String[] ruleNames = {
            "file", "row", "value"
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
    public String getGrammarFileName() {
        return "S3RawLogGrammar.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public S3RawLogGrammarParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class FileContext extends ParserRuleContext {
        public List<List<String>> data;
        public RowContext row;

        public RowContext row() {
            return getRuleContext(RowContext.class, 0);
        }

        public FileContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_file;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof S3RawLogGrammarListener) ((S3RawLogGrammarListener) listener).enterFile(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof S3RawLogGrammarListener) ((S3RawLogGrammarListener) listener).exitFile(this);
        }
    }

    public final FileContext file() throws RecognitionException {
        FileContext _localctx = new FileContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_file);
        ((FileContext) _localctx).data = new ArrayList<List<String>>();
        try {
            enterOuterAlt(_localctx, 1);
            {
                {
                    setState(6);
                    ((FileContext) _localctx).row = row();
                    _localctx.data.add(((FileContext) _localctx).row.list);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class RowContext extends ParserRuleContext {
        public List<String> list;
        public ValueContext a;
        public ValueContext b;

        public TerminalNode LINEBREAK() {
            return getToken(S3RawLogGrammarParser.LINEBREAK, 0);
        }

        public TerminalNode EOF() {
            return getToken(S3RawLogGrammarParser.EOF, 0);
        }

        public List<TerminalNode> NoValue() {
            return getTokens(S3RawLogGrammarParser.NoValue);
        }

        public TerminalNode NoValue(int i) {
            return getToken(S3RawLogGrammarParser.NoValue, i);
        }

        public List<ValueContext> value() {
            return getRuleContexts(ValueContext.class);
        }

        public ValueContext value(int i) {
            return getRuleContext(ValueContext.class, i);
        }

        public List<TerminalNode> DELIM() {
            return getTokens(S3RawLogGrammarParser.DELIM);
        }

        public TerminalNode DELIM(int i) {
            return getToken(S3RawLogGrammarParser.DELIM, i);
        }

        public RowContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_row;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof S3RawLogGrammarListener) ((S3RawLogGrammarListener) listener).enterRow(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof S3RawLogGrammarListener) ((S3RawLogGrammarListener) listener).exitRow(this);
        }
    }

    public final RowContext row() throws RecognitionException {
        RowContext _localctx = new RowContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_row);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                ((RowContext) _localctx).list = new ArrayList<String>();
                setState(14);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(10);
                        ((RowContext) _localctx).a = value();
                        _localctx.list.add(((RowContext) _localctx).a.val);
                    }
                    break;
                    case NoValue: {
                        setState(13);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(25);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == DELIM) {
                    {
                        {
                            setState(16);
                            match(DELIM);
                            setState(21);
                            _errHandler.sync(this);
                            switch (_input.LA(1)) {
                                case SimpleValue:
                                case DateValue:
                                case QuotedValue:
                                case DoubleQuotedValue: {
                                    setState(17);
                                    ((RowContext) _localctx).b = value();
                                    _localctx.list.add(((RowContext) _localctx).b.val);
                                }
                                break;
                                case NoValue: {
                                    setState(20);
                                    match(NoValue);
                                }
                                break;
                                default:
                                    throw new NoViableAltException(this);
                            }
                        }
                    }
                    setState(27);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(28);
                _la = _input.LA(1);
                if (!(_la == EOF || _la == LINEBREAK)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class ValueContext extends ParserRuleContext {
        public String val;
        public Token SimpleValue;
        public Token DateValue;
        public Token QuotedValue;
        public Token DoubleQuotedValue;

        public TerminalNode SimpleValue() {
            return getToken(S3RawLogGrammarParser.SimpleValue, 0);
        }

        public TerminalNode DateValue() {
            return getToken(S3RawLogGrammarParser.DateValue, 0);
        }

        public TerminalNode QuotedValue() {
            return getToken(S3RawLogGrammarParser.QuotedValue, 0);
        }

        public TerminalNode DoubleQuotedValue() {
            return getToken(S3RawLogGrammarParser.DoubleQuotedValue, 0);
        }

        public ValueContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_value;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof S3RawLogGrammarListener) ((S3RawLogGrammarListener) listener).enterValue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof S3RawLogGrammarListener) ((S3RawLogGrammarListener) listener).exitValue(this);
        }
    }

    public final ValueContext value() throws RecognitionException {
        ValueContext _localctx = new ValueContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_value);
        try {
            setState(38);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case SimpleValue:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(30);
                    ((ValueContext) _localctx).SimpleValue = match(SimpleValue);
                    ((ValueContext) _localctx).val = (((ValueContext) _localctx).SimpleValue != null ? ((ValueContext) _localctx).SimpleValue.getText() : null);
                }
                break;
                case DateValue:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(32);
                    ((ValueContext) _localctx).DateValue = match(DateValue);

                    ((ValueContext) _localctx).val = (((ValueContext) _localctx).DateValue != null ? ((ValueContext) _localctx).DateValue.getText() : null);
                    ((ValueContext) _localctx).val = _localctx.val.substring(1, _localctx.val.length() - 1);

                }
                break;
                case QuotedValue:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(34);
                    ((ValueContext) _localctx).QuotedValue = match(QuotedValue);

                    ((ValueContext) _localctx).val = (((ValueContext) _localctx).QuotedValue != null ? ((ValueContext) _localctx).QuotedValue.getText() : null);
                    ((ValueContext) _localctx).val = _localctx.val.substring(1, _localctx.val.length() - 1);
                    ((ValueContext) _localctx).val = _localctx.val.replace("\\\"", "\""); // unescape the quotes

                }
                break;
                case DoubleQuotedValue:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(36);
                    ((ValueContext) _localctx).DoubleQuotedValue = match(DoubleQuotedValue);

                    ((ValueContext) _localctx).val = (((ValueContext) _localctx).DoubleQuotedValue != null ? ((ValueContext) _localctx).DoubleQuotedValue.getText() : null);
                    ((ValueContext) _localctx).val = _localctx.val.substring(2, _localctx.val.length() - 2);
                    ((ValueContext) _localctx).val = _localctx.val.replace("\\\"\\\"", "\"\""); // unescape the quotes

                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13+\4\2\t\2\4\3\t" +
                    "\3\4\4\t\4\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\5\3\21\n\3\3\3\3\3\3\3\3\3" +
                    "\3\3\5\3\30\n\3\7\3\32\n\3\f\3\16\3\35\13\3\3\3\3\3\3\4\3\4\3\4\3\4\3" +
                    "\4\3\4\3\4\3\4\5\4)\n\4\3\4\2\2\5\2\4\6\2\3\3\3\b\b\2-\2\b\3\2\2\2\4\13" +
                    "\3\2\2\2\6(\3\2\2\2\b\t\5\4\3\2\t\n\b\2\1\2\n\3\3\2\2\2\13\20\b\3\1\2" +
                    "\f\r\5\6\4\2\r\16\b\3\1\2\16\21\3\2\2\2\17\21\7\3\2\2\20\f\3\2\2\2\20" +
                    "\17\3\2\2\2\21\33\3\2\2\2\22\27\7\t\2\2\23\24\5\6\4\2\24\25\b\3\1\2\25" +
                    "\30\3\2\2\2\26\30\7\3\2\2\27\23\3\2\2\2\27\26\3\2\2\2\30\32\3\2\2\2\31" +
                    "\22\3\2\2\2\32\35\3\2\2\2\33\31\3\2\2\2\33\34\3\2\2\2\34\36\3\2\2\2\35" +
                    "\33\3\2\2\2\36\37\t\2\2\2\37\5\3\2\2\2 !\7\4\2\2!)\b\4\1\2\"#\7\5\2\2" +
                    "#)\b\4\1\2$%\7\6\2\2%)\b\4\1\2&\'\7\7\2\2\')\b\4\1\2( \3\2\2\2(\"\3\2" +
                    "\2\2($\3\2\2\2(&\3\2\2\2)\7\3\2\2\2\6\20\27\33(";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}