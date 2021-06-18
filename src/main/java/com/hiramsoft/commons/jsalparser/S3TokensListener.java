package com.hiramsoft.commons.jsalparser;// Generated from S3Tokens.g4 by ANTLR 4.7.1

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link S3TokensParser}.
 */
public interface S3TokensListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link S3TokensParser#value}.
     *
     * @param ctx the parse tree
     */
    void enterValue(S3TokensParser.ValueContext ctx);

    /**
     * Exit a parse tree produced by {@link S3TokensParser#value}.
     *
     * @param ctx the parse tree
     */
    void exitValue(S3TokensParser.ValueContext ctx);
}