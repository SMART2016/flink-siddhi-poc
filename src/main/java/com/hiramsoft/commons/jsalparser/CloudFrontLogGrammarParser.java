// Generated from CloudFrontLogGrammar.g4 by ANTLR 4.7.1


package com.hiramsoft.commons.jsalparser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CloudFrontLogGrammarParser extends Parser {
    static {
        RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            CloudfrontDelim = 1, HeaderLiteral = 2, VersionLiteral = 3, COMMENT = 4, NoValue = 5,
            SimpleValue = 6, DateValue = 7, QuotedValue = 8, DoubleQuotedValue = 9, LINEBREAK = 10,
            DELIM = 11, ESCAPED_QUOTE = 12, ALLOWED_CHAR = 13;
    public static final int
            RULE_file = 0, RULE_row = 1, RULE_version = 2, RULE_header = 3, RULE_value = 4;
    public static final String[] ruleNames = {
            "file", "row", "version", "header", "value"
    };

    private static final String[] _LITERAL_NAMES = {
            null, null, "'#Fields:'", "'#Version:'", "'#'", "'-'", null, null, null,
            null, null, "' '"
    };
    private static final String[] _SYMBOLIC_NAMES = {
            null, "CloudfrontDelim", "HeaderLiteral", "VersionLiteral", "COMMENT",
            "NoValue", "SimpleValue", "DateValue", "QuotedValue", "DoubleQuotedValue",
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
        return "CloudFrontLogGrammar.g4";
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


    CloudFrontWebLogBuilder builder = new CloudFrontWebLogBuilder();

    public CloudFrontLogGrammarParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class FileContext extends ParserRuleContext {
        public ICloudFrontLogVisitor visitor;
        public VersionContext ver;
        public RowContext row;

        public List<TerminalNode> LINEBREAK() {
            return getTokens(CloudFrontLogGrammarParser.LINEBREAK);
        }

        public TerminalNode LINEBREAK(int i) {
            return getToken(CloudFrontLogGrammarParser.LINEBREAK, i);
        }

        public HeaderContext header() {
            return getRuleContext(HeaderContext.class, 0);
        }

        public VersionContext version() {
            return getRuleContext(VersionContext.class, 0);
        }

        public List<RowContext> row() {
            return getRuleContexts(RowContext.class);
        }

        public RowContext row(int i) {
            return getRuleContext(RowContext.class, i);
        }

        public FileContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public FileContext(ParserRuleContext parent, int invokingState, ICloudFrontLogVisitor visitor) {
            super(parent, invokingState);
            this.visitor = visitor;
        }

        @Override
        public int getRuleIndex() {
            return RULE_file;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof CloudFrontLogGrammarListener)
                ((CloudFrontLogGrammarListener) listener).enterFile(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof CloudFrontLogGrammarListener)
                ((CloudFrontLogGrammarListener) listener).exitFile(this);
        }
    }

    public final FileContext file(ICloudFrontLogVisitor visitor) throws RecognitionException {
        FileContext _localctx = new FileContext(_ctx, getState(), visitor);
        enterRule(_localctx, 0, RULE_file);

        builder.reset();

        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(10);
                ((FileContext) _localctx).ver = version();

                builder.acceptVersion(((FileContext) _localctx).ver.ver);

                setState(12);
                match(LINEBREAK);
                setState(13);
                header();
                setState(14);
                match(LINEBREAK);
                setState(18);
                _errHandler.sync(this);
                _la = _input.LA(1);
                do {
                    {
                        {
                            setState(15);
                            ((FileContext) _localctx).row = row();
                            visitor.accept(((FileContext) _localctx).row.entry);
                        }
                    }
                    setState(20);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                } while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NoValue) | (1L << SimpleValue) | (1L << DateValue) | (1L << QuotedValue) | (1L << DoubleQuotedValue))) != 0));
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
        public CloudFrontWebLogEntry entry;
        public ValueContext a;
        public ValueContext b;

        public TerminalNode LINEBREAK() {
            return getToken(CloudFrontLogGrammarParser.LINEBREAK, 0);
        }

        public TerminalNode EOF() {
            return getToken(CloudFrontLogGrammarParser.EOF, 0);
        }

        public List<TerminalNode> CloudfrontDelim() {
            return getTokens(CloudFrontLogGrammarParser.CloudfrontDelim);
        }

        public TerminalNode CloudfrontDelim(int i) {
            return getToken(CloudFrontLogGrammarParser.CloudfrontDelim, i);
        }

        public List<TerminalNode> NoValue() {
            return getTokens(CloudFrontLogGrammarParser.NoValue);
        }

        public TerminalNode NoValue(int i) {
            return getToken(CloudFrontLogGrammarParser.NoValue, i);
        }

        public List<ValueContext> value() {
            return getRuleContexts(ValueContext.class);
        }

        public ValueContext value(int i) {
            return getRuleContext(ValueContext.class, i);
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
            if (listener instanceof CloudFrontLogGrammarListener)
                ((CloudFrontLogGrammarListener) listener).enterRow(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof CloudFrontLogGrammarListener)
                ((CloudFrontLogGrammarListener) listener).exitRow(this);
        }
    }

    public final RowContext row() throws RecognitionException {
        RowContext _localctx = new RowContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_row);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(27);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        {
                            setState(22);
                            ((RowContext) _localctx).a = value();
                            builder.acceptValue(((RowContext) _localctx).a.val);
                        }
                    }
                    break;
                    case NoValue: {
                        {
                            setState(25);
                            match(NoValue);
                            builder.skipValue();
                        }
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(39);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 3, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(29);
                                match(CloudfrontDelim);
                                setState(35);
                                _errHandler.sync(this);
                                switch (_input.LA(1)) {
                                    case SimpleValue:
                                    case DateValue:
                                    case QuotedValue:
                                    case DoubleQuotedValue: {
                                        {
                                            setState(30);
                                            ((RowContext) _localctx).b = value();
                                            builder.acceptValue(((RowContext) _localctx).b.val);
                                        }
                                    }
                                    break;
                                    case NoValue: {
                                        {
                                            setState(33);
                                            match(NoValue);
                                            builder.skipValue();
                                        }
                                    }
                                    break;
                                    default:
                                        throw new NoViableAltException(this);
                                }
                            }
                        }
                    }
                    setState(41);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 3, _ctx);
                }
                setState(45);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == CloudfrontDelim) {
                    {
                        {
                            setState(42);
                            match(CloudfrontDelim);
                        }
                    }
                    setState(47);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(48);
                _la = _input.LA(1);
                if (!(_la == EOF || _la == LINEBREAK)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
                ((RowContext) _localctx).entry = builder.buildEntry();
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

    public static class VersionContext extends ParserRuleContext {
        public String ver;
        public Token SimpleValue;

        public TerminalNode VersionLiteral() {
            return getToken(CloudFrontLogGrammarParser.VersionLiteral, 0);
        }

        public TerminalNode SimpleValue() {
            return getToken(CloudFrontLogGrammarParser.SimpleValue, 0);
        }

        public List<TerminalNode> CloudfrontDelim() {
            return getTokens(CloudFrontLogGrammarParser.CloudfrontDelim);
        }

        public TerminalNode CloudfrontDelim(int i) {
            return getToken(CloudFrontLogGrammarParser.CloudfrontDelim, i);
        }

        public VersionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_version;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof CloudFrontLogGrammarListener)
                ((CloudFrontLogGrammarListener) listener).enterVersion(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof CloudFrontLogGrammarListener)
                ((CloudFrontLogGrammarListener) listener).exitVersion(this);
        }
    }

    public final VersionContext version() throws RecognitionException {
        VersionContext _localctx = new VersionContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_version);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(51);
                match(VersionLiteral);
                setState(55);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == CloudfrontDelim) {
                    {
                        {
                            setState(52);
                            match(CloudfrontDelim);
                        }
                    }
                    setState(57);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(58);
                ((VersionContext) _localctx).SimpleValue = match(SimpleValue);
                ((VersionContext) _localctx).ver = (((VersionContext) _localctx).SimpleValue != null ? ((VersionContext) _localctx).SimpleValue.getText() : null);
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

    public static class HeaderContext extends ParserRuleContext {
        public ValueContext h;

        public TerminalNode HeaderLiteral() {
            return getToken(CloudFrontLogGrammarParser.HeaderLiteral, 0);
        }

        public List<TerminalNode> CloudfrontDelim() {
            return getTokens(CloudFrontLogGrammarParser.CloudfrontDelim);
        }

        public TerminalNode CloudfrontDelim(int i) {
            return getToken(CloudFrontLogGrammarParser.CloudfrontDelim, i);
        }

        public List<ValueContext> value() {
            return getRuleContexts(ValueContext.class);
        }

        public ValueContext value(int i) {
            return getRuleContext(ValueContext.class, i);
        }

        public HeaderContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_header;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof CloudFrontLogGrammarListener)
                ((CloudFrontLogGrammarListener) listener).enterHeader(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof CloudFrontLogGrammarListener)
                ((CloudFrontLogGrammarListener) listener).exitHeader(this);
        }
    }

    public final HeaderContext header() throws RecognitionException {
        HeaderContext _localctx = new HeaderContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_header);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(61);
                match(HeaderLiteral);
                setState(68);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 6, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(62);
                                match(CloudfrontDelim);
                                setState(63);
                                ((HeaderContext) _localctx).h = value();
                                builder.acceptHeader(((HeaderContext) _localctx).h.val);
                            }
                        }
                    }
                    setState(70);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 6, _ctx);
                }
                setState(74);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == CloudfrontDelim) {
                    {
                        {
                            setState(71);
                            match(CloudfrontDelim);
                        }
                    }
                    setState(76);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
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
            return getToken(CloudFrontLogGrammarParser.SimpleValue, 0);
        }

        public TerminalNode DateValue() {
            return getToken(CloudFrontLogGrammarParser.DateValue, 0);
        }

        public TerminalNode QuotedValue() {
            return getToken(CloudFrontLogGrammarParser.QuotedValue, 0);
        }

        public TerminalNode DoubleQuotedValue() {
            return getToken(CloudFrontLogGrammarParser.DoubleQuotedValue, 0);
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
            if (listener instanceof CloudFrontLogGrammarListener)
                ((CloudFrontLogGrammarListener) listener).enterValue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof CloudFrontLogGrammarListener)
                ((CloudFrontLogGrammarListener) listener).exitValue(this);
        }
    }

    public final ValueContext value() throws RecognitionException {
        ValueContext _localctx = new ValueContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_value);
        try {
            setState(85);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case SimpleValue:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(77);
                    ((ValueContext) _localctx).SimpleValue = match(SimpleValue);
                    ((ValueContext) _localctx).val = (((ValueContext) _localctx).SimpleValue != null ? ((ValueContext) _localctx).SimpleValue.getText() : null);
                }
                break;
                case DateValue:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(79);
                    ((ValueContext) _localctx).DateValue = match(DateValue);

                    ((ValueContext) _localctx).val = (((ValueContext) _localctx).DateValue != null ? ((ValueContext) _localctx).DateValue.getText() : null);
                    ((ValueContext) _localctx).val = _localctx.val.substring(1, _localctx.val.length() - 1);

                }
                break;
                case QuotedValue:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(81);
                    ((ValueContext) _localctx).QuotedValue = match(QuotedValue);

                    ((ValueContext) _localctx).val = (((ValueContext) _localctx).QuotedValue != null ? ((ValueContext) _localctx).QuotedValue.getText() : null);
                    ((ValueContext) _localctx).val = _localctx.val.substring(1, _localctx.val.length() - 1);
                    ((ValueContext) _localctx).val = _localctx.val.replace("\\\"", "\""); // unescape the quotes

                }
                break;
                case DoubleQuotedValue:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(83);
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
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\17Z\4\2\t\2\4\3\t" +
                    "\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\6\2\25\n\2" +
                    "\r\2\16\2\26\3\3\3\3\3\3\3\3\3\3\5\3\36\n\3\3\3\3\3\3\3\3\3\3\3\3\3\5" +
                    "\3&\n\3\7\3(\n\3\f\3\16\3+\13\3\3\3\7\3.\n\3\f\3\16\3\61\13\3\3\3\3\3" +
                    "\3\3\3\4\3\4\7\48\n\4\f\4\16\4;\13\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\7" +
                    "\5E\n\5\f\5\16\5H\13\5\3\5\7\5K\n\5\f\5\16\5N\13\5\3\6\3\6\3\6\3\6\3\6" +
                    "\3\6\3\6\3\6\5\6X\n\6\3\6\2\2\7\2\4\6\b\n\2\3\3\3\f\f\2_\2\f\3\2\2\2\4" +
                    "\35\3\2\2\2\6\65\3\2\2\2\b?\3\2\2\2\nW\3\2\2\2\f\r\5\6\4\2\r\16\b\2\1" +
                    "\2\16\17\7\f\2\2\17\20\5\b\5\2\20\24\7\f\2\2\21\22\5\4\3\2\22\23\b\2\1" +
                    "\2\23\25\3\2\2\2\24\21\3\2\2\2\25\26\3\2\2\2\26\24\3\2\2\2\26\27\3\2\2" +
                    "\2\27\3\3\2\2\2\30\31\5\n\6\2\31\32\b\3\1\2\32\36\3\2\2\2\33\34\7\7\2" +
                    "\2\34\36\b\3\1\2\35\30\3\2\2\2\35\33\3\2\2\2\36)\3\2\2\2\37%\7\3\2\2 " +
                    "!\5\n\6\2!\"\b\3\1\2\"&\3\2\2\2#$\7\7\2\2$&\b\3\1\2% \3\2\2\2%#\3\2\2" +
                    "\2&(\3\2\2\2\'\37\3\2\2\2(+\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*/\3\2\2\2+)\3" +
                    "\2\2\2,.\7\3\2\2-,\3\2\2\2.\61\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\62\3\2" +
                    "\2\2\61/\3\2\2\2\62\63\t\2\2\2\63\64\b\3\1\2\64\5\3\2\2\2\659\7\5\2\2" +
                    "\668\7\3\2\2\67\66\3\2\2\28;\3\2\2\29\67\3\2\2\29:\3\2\2\2:<\3\2\2\2;" +
                    "9\3\2\2\2<=\7\b\2\2=>\b\4\1\2>\7\3\2\2\2?F\7\4\2\2@A\7\3\2\2AB\5\n\6\2" +
                    "BC\b\5\1\2CE\3\2\2\2D@\3\2\2\2EH\3\2\2\2FD\3\2\2\2FG\3\2\2\2GL\3\2\2\2" +
                    "HF\3\2\2\2IK\7\3\2\2JI\3\2\2\2KN\3\2\2\2LJ\3\2\2\2LM\3\2\2\2M\t\3\2\2" +
                    "\2NL\3\2\2\2OP\7\b\2\2PX\b\6\1\2QR\7\t\2\2RX\b\6\1\2ST\7\n\2\2TX\b\6\1" +
                    "\2UV\7\13\2\2VX\b\6\1\2WO\3\2\2\2WQ\3\2\2\2WS\3\2\2\2WU\3\2\2\2X\13\3" +
                    "\2\2\2\13\26\35%)/9FLW";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}