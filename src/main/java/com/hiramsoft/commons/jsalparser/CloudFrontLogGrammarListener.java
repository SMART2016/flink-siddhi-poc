// Generated from CloudFrontLogGrammar.g4 by ANTLR 4.7.1


package com.hiramsoft.commons.jsalparser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CloudFrontLogGrammarParser}.
 */
public interface CloudFrontLogGrammarListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link CloudFrontLogGrammarParser#file}.
     *
     * @param ctx the parse tree
     */
    void enterFile(CloudFrontLogGrammarParser.FileContext ctx);

    /**
     * Exit a parse tree produced by {@link CloudFrontLogGrammarParser#file}.
     *
     * @param ctx the parse tree
     */
    void exitFile(CloudFrontLogGrammarParser.FileContext ctx);

    /**
     * Enter a parse tree produced by {@link CloudFrontLogGrammarParser#row}.
     *
     * @param ctx the parse tree
     */
    void enterRow(CloudFrontLogGrammarParser.RowContext ctx);

    /**
     * Exit a parse tree produced by {@link CloudFrontLogGrammarParser#row}.
     *
     * @param ctx the parse tree
     */
    void exitRow(CloudFrontLogGrammarParser.RowContext ctx);

    /**
     * Enter a parse tree produced by {@link CloudFrontLogGrammarParser#version}.
     *
     * @param ctx the parse tree
     */
    void enterVersion(CloudFrontLogGrammarParser.VersionContext ctx);

    /**
     * Exit a parse tree produced by {@link CloudFrontLogGrammarParser#version}.
     *
     * @param ctx the parse tree
     */
    void exitVersion(CloudFrontLogGrammarParser.VersionContext ctx);

    /**
     * Enter a parse tree produced by {@link CloudFrontLogGrammarParser#header}.
     *
     * @param ctx the parse tree
     */
    void enterHeader(CloudFrontLogGrammarParser.HeaderContext ctx);

    /**
     * Exit a parse tree produced by {@link CloudFrontLogGrammarParser#header}.
     *
     * @param ctx the parse tree
     */
    void exitHeader(CloudFrontLogGrammarParser.HeaderContext ctx);

    /**
     * Enter a parse tree produced by {@link CloudFrontLogGrammarParser#value}.
     *
     * @param ctx the parse tree
     */
    void enterValue(CloudFrontLogGrammarParser.ValueContext ctx);

    /**
     * Exit a parse tree produced by {@link CloudFrontLogGrammarParser#value}.
     *
     * @param ctx the parse tree
     */
    void exitValue(CloudFrontLogGrammarParser.ValueContext ctx);
}