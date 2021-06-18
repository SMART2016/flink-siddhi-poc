package com.hiramsoft.commons.jsalparser;// Generated from S3Tokens.g4 by ANTLR 4.7.1

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class S3TokensLexer extends Lexer {
    static {
        RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            NoValue = 1, SimpleValue = 2, DateValue = 3, QuotedValue = 4, DoubleQuotedValue = 5,
            LINEBREAK = 6, DELIM = 7, ESCAPED_QUOTE = 8, ALLOWED_CHAR = 9;
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };

    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

    public static final String[] ruleNames = {
            "NoValue", "SimpleValue", "DateValue", "QuotedValue", "DoubleQuotedValue",
            "LINEBREAK", "DELIM", "ESCAPED_QUOTE", "ALLOWED_CHAR"
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


    public S3TokensLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @Override
    public String getGrammarFileName() {
        return "S3Tokens.g4";
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
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\13M\b\1\4\2\t\2\4" +
                    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2" +
                    "\3\3\6\3\31\n\3\r\3\16\3\32\3\4\3\4\3\4\7\4 \n\4\f\4\16\4#\13\4\3\4\3" +
                    "\4\3\5\3\5\3\5\3\5\7\5+\n\5\f\5\16\5.\13\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6" +
                    "\3\6\7\68\n\6\f\6\16\6;\13\6\3\6\3\6\3\6\3\7\5\7A\n\7\3\7\3\7\5\7E\n\7" +
                    "\3\b\3\b\3\t\3\t\3\t\3\n\3\n\2\2\13\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n" +
                    "\23\13\3\2\3\6\2\13\f\17\17\"\"$$\2W\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2" +
                    "\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23" +
                    "\3\2\2\2\3\25\3\2\2\2\5\30\3\2\2\2\7\34\3\2\2\2\t&\3\2\2\2\13\61\3\2\2" +
                    "\2\rD\3\2\2\2\17F\3\2\2\2\21H\3\2\2\2\23K\3\2\2\2\25\26\7/\2\2\26\4\3" +
                    "\2\2\2\27\31\5\23\n\2\30\27\3\2\2\2\31\32\3\2\2\2\32\30\3\2\2\2\32\33" +
                    "\3\2\2\2\33\6\3\2\2\2\34!\7]\2\2\35 \5\23\n\2\36 \5\17\b\2\37\35\3\2\2" +
                    "\2\37\36\3\2\2\2 #\3\2\2\2!\37\3\2\2\2!\"\3\2\2\2\"$\3\2\2\2#!\3\2\2\2" +
                    "$%\7_\2\2%\b\3\2\2\2&,\7$\2\2\'+\5\21\t\2(+\5\17\b\2)+\5\23\n\2*\'\3\2" +
                    "\2\2*(\3\2\2\2*)\3\2\2\2+.\3\2\2\2,*\3\2\2\2,-\3\2\2\2-/\3\2\2\2.,\3\2" +
                    "\2\2/\60\7$\2\2\60\n\3\2\2\2\61\62\7$\2\2\62\63\7$\2\2\639\3\2\2\2\64" +
                    "8\5\21\t\2\658\5\17\b\2\668\5\23\n\2\67\64\3\2\2\2\67\65\3\2\2\2\67\66" +
                    "\3\2\2\28;\3\2\2\29\67\3\2\2\29:\3\2\2\2:<\3\2\2\2;9\3\2\2\2<=\7$\2\2" +
                    "=>\7$\2\2>\f\3\2\2\2?A\7\17\2\2@?\3\2\2\2@A\3\2\2\2AB\3\2\2\2BE\7\f\2" +
                    "\2CE\7\17\2\2D@\3\2\2\2DC\3\2\2\2E\16\3\2\2\2FG\7\"\2\2G\20\3\2\2\2HI" +
                    "\7^\2\2IJ\7$\2\2J\22\3\2\2\2KL\n\2\2\2L\24\3\2\2\2\f\2\32\37!*,\679@D" +
                    "\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}