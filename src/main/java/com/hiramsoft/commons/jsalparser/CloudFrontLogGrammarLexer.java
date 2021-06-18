// Generated from CloudFrontLogGrammar.g4 by ANTLR 4.7.1


package com.hiramsoft.commons.jsalparser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CloudFrontLogGrammarLexer extends Lexer {
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
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };

    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

    public static final String[] ruleNames = {
            "CloudfrontDelim", "HeaderLiteral", "VersionLiteral", "COMMENT", "NoValue",
            "SimpleValue", "DateValue", "QuotedValue", "DoubleQuotedValue", "LINEBREAK",
            "DELIM", "ESCAPED_QUOTE", "ALLOWED_CHAR"
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


    CloudFrontWebLogBuilder builder = new CloudFrontWebLogBuilder();


    public CloudFrontLogGrammarLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
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
    public String[] getChannelNames() {
        return channelNames;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\17n\b\1\4\2\t\2\4" +
                    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
                    "\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\5\2 \n\2\3\3\3\3\3\3\3\3\3\3\3\3" +
                    "\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3" +
                    "\7\6\7:\n\7\r\7\16\7;\3\b\3\b\3\b\7\bA\n\b\f\b\16\bD\13\b\3\b\3\b\3\t" +
                    "\3\t\3\t\3\t\7\tL\n\t\f\t\16\tO\13\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\7" +
                    "\nY\n\n\f\n\16\n\\\13\n\3\n\3\n\3\n\3\13\5\13b\n\13\3\13\3\13\5\13f\n" +
                    "\13\3\f\3\f\3\r\3\r\3\r\3\16\3\16\2\2\17\3\3\5\4\7\5\t\6\13\7\r\b\17\t" +
                    "\21\n\23\13\25\f\27\r\31\16\33\17\3\2\3\6\2\13\f\17\17\"\"$$\2y\2\3\3" +
                    "\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2" +
                    "\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3" +
                    "\2\2\2\2\33\3\2\2\2\3\37\3\2\2\2\5!\3\2\2\2\7*\3\2\2\2\t\64\3\2\2\2\13" +
                    "\66\3\2\2\2\r9\3\2\2\2\17=\3\2\2\2\21G\3\2\2\2\23R\3\2\2\2\25e\3\2\2\2" +
                    "\27g\3\2\2\2\31i\3\2\2\2\33l\3\2\2\2\35 \7\13\2\2\36 \5\27\f\2\37\35\3" +
                    "\2\2\2\37\36\3\2\2\2 \4\3\2\2\2!\"\7%\2\2\"#\7H\2\2#$\7k\2\2$%\7g\2\2" +
                    "%&\7n\2\2&\'\7f\2\2\'(\7u\2\2()\7<\2\2)\6\3\2\2\2*+\7%\2\2+,\7X\2\2,-" +
                    "\7g\2\2-.\7t\2\2./\7u\2\2/\60\7k\2\2\60\61\7q\2\2\61\62\7p\2\2\62\63\7" +
                    "<\2\2\63\b\3\2\2\2\64\65\7%\2\2\65\n\3\2\2\2\66\67\7/\2\2\67\f\3\2\2\2" +
                    "8:\5\33\16\298\3\2\2\2:;\3\2\2\2;9\3\2\2\2;<\3\2\2\2<\16\3\2\2\2=B\7]" +
                    "\2\2>A\5\33\16\2?A\5\27\f\2@>\3\2\2\2@?\3\2\2\2AD\3\2\2\2B@\3\2\2\2BC" +
                    "\3\2\2\2CE\3\2\2\2DB\3\2\2\2EF\7_\2\2F\20\3\2\2\2GM\7$\2\2HL\5\31\r\2" +
                    "IL\5\27\f\2JL\5\33\16\2KH\3\2\2\2KI\3\2\2\2KJ\3\2\2\2LO\3\2\2\2MK\3\2" +
                    "\2\2MN\3\2\2\2NP\3\2\2\2OM\3\2\2\2PQ\7$\2\2Q\22\3\2\2\2RS\7$\2\2ST\7$" +
                    "\2\2TZ\3\2\2\2UY\5\31\r\2VY\5\27\f\2WY\5\33\16\2XU\3\2\2\2XV\3\2\2\2X" +
                    "W\3\2\2\2Y\\\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[]\3\2\2\2\\Z\3\2\2\2]^\7$\2\2" +
                    "^_\7$\2\2_\24\3\2\2\2`b\7\17\2\2a`\3\2\2\2ab\3\2\2\2bc\3\2\2\2cf\7\f\2" +
                    "\2df\7\17\2\2ea\3\2\2\2ed\3\2\2\2f\26\3\2\2\2gh\7\"\2\2h\30\3\2\2\2ij" +
                    "\7^\2\2jk\7$\2\2k\32\3\2\2\2lm\n\2\2\2m\34\3\2\2\2\r\2\37;@BKMXZae\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}