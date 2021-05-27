grammar S3RawLogGrammar;

@header{

	package com.hiramsoft.commons.jsalparser;
}

import S3Tokens;

file returns [List<List<String>> data]
@init {$data = new ArrayList<List<String>>();}
	: (row {$data.add($row.list);})
	;

row returns [List<String> list]
	:
		{$list = new ArrayList<String>();}
		( a=value {$list.add($a.val);} | NoValue )
		( DELIM
			(b=value {$list.add($b.val);} | NoValue)
		)*
		(LINEBREAK | EOF)
	;