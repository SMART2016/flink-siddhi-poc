grammar CloudFrontLogGrammar;

@header{

	package com.hiramsoft.commons.jsalparser;
}

@members{
	CloudFrontWebLogBuilder builder = new CloudFrontWebLogBuilder();
}

import S3Tokens;

file [ICloudFrontLogVisitor visitor]
@init {
	builder.reset();
	}
	:
	ver=version {
		builder.acceptVersion($ver.ver);
	}
		LINEBREAK
		header
		LINEBREAK

		(row {visitor.accept($row.entry);})+
	;

row returns [CloudFrontWebLogEntry entry]
	:
		(  (a=value {builder.acceptValue($a.val);}) | (NoValue {builder.skipValue();}) )
		(
			CloudfrontDelim
			( (b=value {builder.acceptValue($b.val);}) | (NoValue {builder.skipValue();}) )
		)*
		CloudfrontDelim*
		(LINEBREAK | EOF) {$entry = builder.buildEntry();}
	;

version returns [String ver]
	: VersionLiteral CloudfrontDelim* SimpleValue {$ver = $SimpleValue.text;}
	;

header
	:
	HeaderLiteral
    ( CloudfrontDelim h=value {builder.acceptHeader($h.val);} )*
    CloudfrontDelim*
	;

CloudfrontDelim
	: '\t' | DELIM
	;

HeaderLiteral
	: '#Fields:'
	;

VersionLiteral
	: '#Version:'
	;

COMMENT
	: '#'
	;