// Generated from S3TypedLogGrammar.g4 by ANTLR 4.7.1

	package com.hiramsoft.commons.jsalparser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link S3TypedLogGrammarParser}.
 */
public interface S3TypedLogGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link S3TypedLogGrammarParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(S3TypedLogGrammarParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link S3TypedLogGrammarParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(S3TypedLogGrammarParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link S3TypedLogGrammarParser#typedRow}.
	 * @param ctx the parse tree
	 */
	void enterTypedRow(S3TypedLogGrammarParser.TypedRowContext ctx);
	/**
	 * Exit a parse tree produced by {@link S3TypedLogGrammarParser#typedRow}.
	 * @param ctx the parse tree
	 */
	void exitTypedRow(S3TypedLogGrammarParser.TypedRowContext ctx);
	/**
	 * Enter a parse tree produced by {@link S3TypedLogGrammarParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(S3TypedLogGrammarParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link S3TypedLogGrammarParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(S3TypedLogGrammarParser.ValueContext ctx);
}