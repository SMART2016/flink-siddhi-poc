// Generated from S3TypedLogGrammar.g4 by ANTLR 4.7.1

package com.hiramsoft.commons.jsalparser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class S3TypedLogGrammarParser extends Parser {
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
            RULE_file = 0, RULE_typedRow = 1, RULE_value = 2;
    public static final String[] ruleNames = {
            "file", "typedRow", "value"
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
        return "S3TypedLogGrammar.g4";
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

    public S3TypedLogGrammarParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class FileContext extends ParserRuleContext {
        public IS3LogVisitor visitor;
        public TypedRowContext typedRow;

        public List<TypedRowContext> typedRow() {
            return getRuleContexts(TypedRowContext.class);
        }

        public TypedRowContext typedRow(int i) {
            return getRuleContext(TypedRowContext.class, i);
        }

        public FileContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public FileContext(ParserRuleContext parent, int invokingState, IS3LogVisitor visitor) {
            super(parent, invokingState);
            this.visitor = visitor;
        }

        @Override
        public int getRuleIndex() {
            return RULE_file;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof S3TypedLogGrammarListener) ((S3TypedLogGrammarListener) listener).enterFile(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof S3TypedLogGrammarListener) ((S3TypedLogGrammarListener) listener).exitFile(this);
        }
    }

    public final FileContext file(IS3LogVisitor visitor) throws RecognitionException {
        FileContext _localctx = new FileContext(_ctx, getState(), visitor);
        enterRule(_localctx, 0, RULE_file);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(9);
                _errHandler.sync(this);
                _la = _input.LA(1);
                do {
                    {
                        {
                            setState(6);
                            ((FileContext) _localctx).typedRow = typedRow();
                            visitor.accept(((FileContext) _localctx).typedRow.entry);
                        }
                    }
                    setState(11);
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

    public static class TypedRowContext extends ParserRuleContext {
        public S3LogEntry entry;
        public ValueContext bucketOwner;
        public ValueContext bucket;
        public ValueContext time;
        public ValueContext remoteIp;
        public ValueContext requester;
        public ValueContext requestId;
        public ValueContext operation;
        public ValueContext key;
        public ValueContext requestUri;
        public ValueContext httpStatus;
        public ValueContext errorCode;
        public ValueContext bytesSent;
        public ValueContext objectSize;
        public ValueContext totalTime;
        public ValueContext turnAroundTime;
        public ValueContext referrer;
        public ValueContext userAgent;
        public ValueContext versionId;
        public ValueContext extraValue;

        public List<TerminalNode> DELIM() {
            return getTokens(S3TypedLogGrammarParser.DELIM);
        }

        public TerminalNode DELIM(int i) {
            return getToken(S3TypedLogGrammarParser.DELIM, i);
        }

        public TerminalNode LINEBREAK() {
            return getToken(S3TypedLogGrammarParser.LINEBREAK, 0);
        }

        public TerminalNode EOF() {
            return getToken(S3TypedLogGrammarParser.EOF, 0);
        }

        public List<TerminalNode> NoValue() {
            return getTokens(S3TypedLogGrammarParser.NoValue);
        }

        public TerminalNode NoValue(int i) {
            return getToken(S3TypedLogGrammarParser.NoValue, i);
        }

        public List<ValueContext> value() {
            return getRuleContexts(ValueContext.class);
        }

        public ValueContext value(int i) {
            return getRuleContext(ValueContext.class, i);
        }

        public TypedRowContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_typedRow;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof S3TypedLogGrammarListener)
                ((S3TypedLogGrammarListener) listener).enterTypedRow(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof S3TypedLogGrammarListener)
                ((S3TypedLogGrammarListener) listener).exitTypedRow(this);
        }
    }

    public final TypedRowContext typedRow() throws RecognitionException {
        TypedRowContext _localctx = new TypedRowContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_typedRow);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                ((TypedRowContext) _localctx).entry = new S3LogEntry();
                setState(18);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(14);
                        ((TypedRowContext) _localctx).bucketOwner = value();
                        _localctx.entry.setBucketOwner(((TypedRowContext) _localctx).bucketOwner.val);
                    }
                    break;
                    case NoValue: {
                        setState(17);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(20);
                match(DELIM);
                setState(25);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(21);
                        ((TypedRowContext) _localctx).bucket = value();
                        _localctx.entry.setBucket(((TypedRowContext) _localctx).bucket.val);
                    }
                    break;
                    case NoValue: {
                        setState(24);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(27);
                match(DELIM);
                setState(32);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(28);
                        ((TypedRowContext) _localctx).time = value();
                        _localctx.entry.parseTime(((TypedRowContext) _localctx).time.val);
                    }
                    break;
                    case NoValue: {
                        setState(31);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(34);
                match(DELIM);
                setState(39);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(35);
                        ((TypedRowContext) _localctx).remoteIp = value();
                        _localctx.entry.setRemoteIpAddress(((TypedRowContext) _localctx).remoteIp.val);
                    }
                    break;
                    case NoValue: {
                        setState(38);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(41);
                match(DELIM);
                setState(46);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(42);
                        ((TypedRowContext) _localctx).requester = value();
                        _localctx.entry.setRequester(((TypedRowContext) _localctx).requester.val);
                    }
                    break;
                    case NoValue: {
                        setState(45);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(48);
                match(DELIM);
                setState(53);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(49);
                        ((TypedRowContext) _localctx).requestId = value();
                        _localctx.entry.setRequestId(((TypedRowContext) _localctx).requestId.val);
                    }
                    break;
                    case NoValue: {
                        setState(52);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(55);
                match(DELIM);
                setState(60);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(56);
                        ((TypedRowContext) _localctx).operation = value();
                        _localctx.entry.setOperation(((TypedRowContext) _localctx).operation.val);
                    }
                    break;
                    case NoValue: {
                        setState(59);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(62);
                match(DELIM);
                setState(67);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(63);
                        ((TypedRowContext) _localctx).key = value();
                        _localctx.entry.setKey(((TypedRowContext) _localctx).key.val);
                    }
                    break;
                    case NoValue: {
                        setState(66);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(69);
                match(DELIM);
                setState(74);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(70);
                        ((TypedRowContext) _localctx).requestUri = value();
                        _localctx.entry.setRequestUri(((TypedRowContext) _localctx).requestUri.val);
                    }
                    break;
                    case NoValue: {
                        setState(73);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(76);
                match(DELIM);
                setState(81);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(77);
                        ((TypedRowContext) _localctx).httpStatus = value();
                        _localctx.entry.setHttpStatus(Integer.parseInt(((TypedRowContext) _localctx).httpStatus.val));
                    }
                    break;
                    case NoValue: {
                        setState(80);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(83);
                match(DELIM);
                setState(88);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(84);
                        ((TypedRowContext) _localctx).errorCode = value();
                        _localctx.entry.setErrorCode(((TypedRowContext) _localctx).errorCode.val);
                    }
                    break;
                    case NoValue: {
                        setState(87);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(90);
                match(DELIM);
                setState(95);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(91);
                        ((TypedRowContext) _localctx).bytesSent = value();
                        _localctx.entry.setBytesSent(Long.parseLong(((TypedRowContext) _localctx).bytesSent.val));
                    }
                    break;
                    case NoValue: {
                        setState(94);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(97);
                match(DELIM);
                setState(102);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(98);
                        ((TypedRowContext) _localctx).objectSize = value();
                        _localctx.entry.setObjectSize(Long.parseLong(((TypedRowContext) _localctx).objectSize.val));
                    }
                    break;
                    case NoValue: {
                        setState(101);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(104);
                match(DELIM);
                setState(109);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(105);
                        ((TypedRowContext) _localctx).totalTime = value();
                        _localctx.entry.setTotalTime(Long.parseLong(((TypedRowContext) _localctx).totalTime.val));
                    }
                    break;
                    case NoValue: {
                        setState(108);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(111);
                match(DELIM);
                setState(116);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(112);
                        ((TypedRowContext) _localctx).turnAroundTime = value();
                        _localctx.entry.setTurnAroundTime(Long.parseLong(((TypedRowContext) _localctx).turnAroundTime.val));
                    }
                    break;
                    case NoValue: {
                        setState(115);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(118);
                match(DELIM);
                setState(123);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(119);
                        ((TypedRowContext) _localctx).referrer = value();
                        _localctx.entry.setReferrer(((TypedRowContext) _localctx).referrer.val);
                    }
                    break;
                    case NoValue: {
                        setState(122);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(125);
                match(DELIM);
                setState(130);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(126);
                        ((TypedRowContext) _localctx).userAgent = value();
                        _localctx.entry.setUserAgent(((TypedRowContext) _localctx).userAgent.val);
                    }
                    break;
                    case NoValue: {
                        setState(129);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(132);
                match(DELIM);
                setState(137);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SimpleValue:
                    case DateValue:
                    case QuotedValue:
                    case DoubleQuotedValue: {
                        setState(133);
                        ((TypedRowContext) _localctx).versionId = value();
                        _localctx.entry.setVersionId(((TypedRowContext) _localctx).versionId.val);
                    }
                    break;
                    case NoValue: {
                        setState(136);
                        match(NoValue);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(148);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 20, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(139);
                                match(DELIM);
                                setState(144);
                                _errHandler.sync(this);
                                switch (_input.LA(1)) {
                                    case SimpleValue:
                                    case DateValue:
                                    case QuotedValue:
                                    case DoubleQuotedValue: {
                                        setState(140);
                                        ((TypedRowContext) _localctx).extraValue = value();
                                        _localctx.entry.getExtras().add(((TypedRowContext) _localctx).extraValue.val);
                                    }
                                    break;
                                    case NoValue: {
                                        setState(143);
                                        match(NoValue);
                                    }
                                    break;
                                    default:
                                        throw new NoViableAltException(this);
                                }
                            }
                        }
                    }
                    setState(150);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 20, _ctx);
                }
                setState(154);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == DELIM) {
                    {
                        {
                            setState(151);
                            match(DELIM);
                        }
                    }
                    setState(156);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(157);
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
            return getToken(S3TypedLogGrammarParser.SimpleValue, 0);
        }

        public TerminalNode DateValue() {
            return getToken(S3TypedLogGrammarParser.DateValue, 0);
        }

        public TerminalNode QuotedValue() {
            return getToken(S3TypedLogGrammarParser.QuotedValue, 0);
        }

        public TerminalNode DoubleQuotedValue() {
            return getToken(S3TypedLogGrammarParser.DoubleQuotedValue, 0);
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
            if (listener instanceof S3TypedLogGrammarListener) ((S3TypedLogGrammarListener) listener).enterValue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof S3TypedLogGrammarListener) ((S3TypedLogGrammarListener) listener).exitValue(this);
        }
    }

    public final ValueContext value() throws RecognitionException {
        ValueContext _localctx = new ValueContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_value);
        try {
            setState(167);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case SimpleValue:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(159);
                    ((ValueContext) _localctx).SimpleValue = match(SimpleValue);
                    ((ValueContext) _localctx).val = (((ValueContext) _localctx).SimpleValue != null ? ((ValueContext) _localctx).SimpleValue.getText() : null);
                }
                break;
                case DateValue:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(161);
                    ((ValueContext) _localctx).DateValue = match(DateValue);

                    ((ValueContext) _localctx).val = (((ValueContext) _localctx).DateValue != null ? ((ValueContext) _localctx).DateValue.getText() : null);
                    ((ValueContext) _localctx).val = _localctx.val.substring(1, _localctx.val.length() - 1);

                }
                break;
                case QuotedValue:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(163);
                    ((ValueContext) _localctx).QuotedValue = match(QuotedValue);

                    ((ValueContext) _localctx).val = (((ValueContext) _localctx).QuotedValue != null ? ((ValueContext) _localctx).QuotedValue.getText() : null);
                    ((ValueContext) _localctx).val = _localctx.val.substring(1, _localctx.val.length() - 1);
                    ((ValueContext) _localctx).val = _localctx.val.replace("\\\"", "\""); // unescape the quotes

                }
                break;
                case DoubleQuotedValue:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(165);
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
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13\u00ac\4\2\t\2" +
                    "\4\3\t\3\4\4\t\4\3\2\3\2\3\2\6\2\f\n\2\r\2\16\2\r\3\3\3\3\3\3\3\3\3\3" +
                    "\5\3\25\n\3\3\3\3\3\3\3\3\3\3\3\5\3\34\n\3\3\3\3\3\3\3\3\3\3\3\5\3#\n" +
                    "\3\3\3\3\3\3\3\3\3\3\3\5\3*\n\3\3\3\3\3\3\3\3\3\3\3\5\3\61\n\3\3\3\3\3" +
                    "\3\3\3\3\3\3\5\38\n\3\3\3\3\3\3\3\3\3\3\3\5\3?\n\3\3\3\3\3\3\3\3\3\3\3" +
                    "\5\3F\n\3\3\3\3\3\3\3\3\3\3\3\5\3M\n\3\3\3\3\3\3\3\3\3\3\3\5\3T\n\3\3" +
                    "\3\3\3\3\3\3\3\3\3\5\3[\n\3\3\3\3\3\3\3\3\3\3\3\5\3b\n\3\3\3\3\3\3\3\3" +
                    "\3\3\3\5\3i\n\3\3\3\3\3\3\3\3\3\3\3\5\3p\n\3\3\3\3\3\3\3\3\3\3\3\5\3w" +
                    "\n\3\3\3\3\3\3\3\3\3\3\3\5\3~\n\3\3\3\3\3\3\3\3\3\3\3\5\3\u0085\n\3\3" +
                    "\3\3\3\3\3\3\3\3\3\5\3\u008c\n\3\3\3\3\3\3\3\3\3\3\3\5\3\u0093\n\3\7\3" +
                    "\u0095\n\3\f\3\16\3\u0098\13\3\3\3\7\3\u009b\n\3\f\3\16\3\u009e\13\3\3" +
                    "\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\u00aa\n\4\3\4\2\2\5\2\4\6\2" +
                    "\3\3\3\b\b\2\u00c1\2\13\3\2\2\2\4\17\3\2\2\2\6\u00a9\3\2\2\2\b\t\5\4\3" +
                    "\2\t\n\b\2\1\2\n\f\3\2\2\2\13\b\3\2\2\2\f\r\3\2\2\2\r\13\3\2\2\2\r\16" +
                    "\3\2\2\2\16\3\3\2\2\2\17\24\b\3\1\2\20\21\5\6\4\2\21\22\b\3\1\2\22\25" +
                    "\3\2\2\2\23\25\7\3\2\2\24\20\3\2\2\2\24\23\3\2\2\2\25\26\3\2\2\2\26\33" +
                    "\7\t\2\2\27\30\5\6\4\2\30\31\b\3\1\2\31\34\3\2\2\2\32\34\7\3\2\2\33\27" +
                    "\3\2\2\2\33\32\3\2\2\2\34\35\3\2\2\2\35\"\7\t\2\2\36\37\5\6\4\2\37 \b" +
                    "\3\1\2 #\3\2\2\2!#\7\3\2\2\"\36\3\2\2\2\"!\3\2\2\2#$\3\2\2\2$)\7\t\2\2" +
                    "%&\5\6\4\2&\'\b\3\1\2\'*\3\2\2\2(*\7\3\2\2)%\3\2\2\2)(\3\2\2\2*+\3\2\2" +
                    "\2+\60\7\t\2\2,-\5\6\4\2-.\b\3\1\2.\61\3\2\2\2/\61\7\3\2\2\60,\3\2\2\2" +
                    "\60/\3\2\2\2\61\62\3\2\2\2\62\67\7\t\2\2\63\64\5\6\4\2\64\65\b\3\1\2\65" +
                    "8\3\2\2\2\668\7\3\2\2\67\63\3\2\2\2\67\66\3\2\2\289\3\2\2\29>\7\t\2\2" +
                    ":;\5\6\4\2;<\b\3\1\2<?\3\2\2\2=?\7\3\2\2>:\3\2\2\2>=\3\2\2\2?@\3\2\2\2" +
                    "@E\7\t\2\2AB\5\6\4\2BC\b\3\1\2CF\3\2\2\2DF\7\3\2\2EA\3\2\2\2ED\3\2\2\2" +
                    "FG\3\2\2\2GL\7\t\2\2HI\5\6\4\2IJ\b\3\1\2JM\3\2\2\2KM\7\3\2\2LH\3\2\2\2" +
                    "LK\3\2\2\2MN\3\2\2\2NS\7\t\2\2OP\5\6\4\2PQ\b\3\1\2QT\3\2\2\2RT\7\3\2\2" +
                    "SO\3\2\2\2SR\3\2\2\2TU\3\2\2\2UZ\7\t\2\2VW\5\6\4\2WX\b\3\1\2X[\3\2\2\2" +
                    "Y[\7\3\2\2ZV\3\2\2\2ZY\3\2\2\2[\\\3\2\2\2\\a\7\t\2\2]^\5\6\4\2^_\b\3\1" +
                    "\2_b\3\2\2\2`b\7\3\2\2a]\3\2\2\2a`\3\2\2\2bc\3\2\2\2ch\7\t\2\2de\5\6\4" +
                    "\2ef\b\3\1\2fi\3\2\2\2gi\7\3\2\2hd\3\2\2\2hg\3\2\2\2ij\3\2\2\2jo\7\t\2" +
                    "\2kl\5\6\4\2lm\b\3\1\2mp\3\2\2\2np\7\3\2\2ok\3\2\2\2on\3\2\2\2pq\3\2\2" +
                    "\2qv\7\t\2\2rs\5\6\4\2st\b\3\1\2tw\3\2\2\2uw\7\3\2\2vr\3\2\2\2vu\3\2\2" +
                    "\2wx\3\2\2\2x}\7\t\2\2yz\5\6\4\2z{\b\3\1\2{~\3\2\2\2|~\7\3\2\2}y\3\2\2" +
                    "\2}|\3\2\2\2~\177\3\2\2\2\177\u0084\7\t\2\2\u0080\u0081\5\6\4\2\u0081" +
                    "\u0082\b\3\1\2\u0082\u0085\3\2\2\2\u0083\u0085\7\3\2\2\u0084\u0080\3\2" +
                    "\2\2\u0084\u0083\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u008b\7\t\2\2\u0087" +
                    "\u0088\5\6\4\2\u0088\u0089\b\3\1\2\u0089\u008c\3\2\2\2\u008a\u008c\7\3" +
                    "\2\2\u008b\u0087\3\2\2\2\u008b\u008a\3\2\2\2\u008c\u0096\3\2\2\2\u008d" +
                    "\u0092\7\t\2\2\u008e\u008f\5\6\4\2\u008f\u0090\b\3\1\2\u0090\u0093\3\2" +
                    "\2\2\u0091\u0093\7\3\2\2\u0092\u008e\3\2\2\2\u0092\u0091\3\2\2\2\u0093" +
                    "\u0095\3\2\2\2\u0094\u008d\3\2\2\2\u0095\u0098\3\2\2\2\u0096\u0094\3\2" +
                    "\2\2\u0096\u0097\3\2\2\2\u0097\u009c\3\2\2\2\u0098\u0096\3\2\2\2\u0099" +
                    "\u009b\7\t\2\2\u009a\u0099\3\2\2\2\u009b\u009e\3\2\2\2\u009c\u009a\3\2" +
                    "\2\2\u009c\u009d\3\2\2\2\u009d\u009f\3\2\2\2\u009e\u009c\3\2\2\2\u009f" +
                    "\u00a0\t\2\2\2\u00a0\5\3\2\2\2\u00a1\u00a2\7\4\2\2\u00a2\u00aa\b\4\1\2" +
                    "\u00a3\u00a4\7\5\2\2\u00a4\u00aa\b\4\1\2\u00a5\u00a6\7\6\2\2\u00a6\u00aa" +
                    "\b\4\1\2\u00a7\u00a8\7\7\2\2\u00a8\u00aa\b\4\1\2\u00a9\u00a1\3\2\2\2\u00a9" +
                    "\u00a3\3\2\2\2\u00a9\u00a5\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa\7\3\2\2\2" +
                    "\31\r\24\33\")\60\67>ELSZahov}\u0084\u008b\u0092\u0096\u009c\u00a9";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}