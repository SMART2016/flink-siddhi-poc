// Generated from S3RawLogGrammar.g4 by ANTLR 4.7.1


	package com.hiramsoft.commons.jsalparser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link S3RawLogGrammarParser}.
 */
public interface S3RawLogGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link S3RawLogGrammarParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(S3RawLogGrammarParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link S3RawLogGrammarParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(S3RawLogGrammarParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link S3RawLogGrammarParser#row}.
	 * @param ctx the parse tree
	 */
	void enterRow(S3RawLogGrammarParser.RowContext ctx);
	/**
	 * Exit a parse tree produced by {@link S3RawLogGrammarParser#row}.
	 * @param ctx the parse tree
	 */
	void exitRow(S3RawLogGrammarParser.RowContext ctx);
	/**
	 * Enter a parse tree produced by {@link S3RawLogGrammarParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(S3RawLogGrammarParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link S3RawLogGrammarParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(S3RawLogGrammarParser.ValueContext ctx);
}