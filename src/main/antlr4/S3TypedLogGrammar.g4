grammar S3TypedLogGrammar;

import S3Tokens;

@header{
	package com.hiramsoft.commons.jsalparser;
}

file [IS3LogVisitor visitor]
	: (typedRow {visitor.accept($typedRow.entry);})+
	;

typedRow returns [S3LogEntry entry]
	:
	 	{$entry = new S3LogEntry();}
	 	(bucketOwner=value {$entry.setBucketOwner($bucketOwner.val);} | NoValue) DELIM
		(bucket=value {$entry.setBucket($bucket.val);} | NoValue) DELIM
		(time=value {$entry.parseTime($time.val);} | NoValue ) DELIM
		(remoteIp=value {$entry.setRemoteIpAddress($remoteIp.val);} | NoValue) DELIM
		(requester=value {$entry.setRequester($requester.val);} | NoValue) DELIM
		(requestId=value {$entry.setRequestId($requestId.val);} | NoValue) DELIM
		(operation=value {$entry.setOperation($operation.val);} | NoValue) DELIM
		(key=value {$entry.setKey($key.val);} | NoValue) DELIM
		(requestUri=value {$entry.setRequestUri($requestUri.val);} | NoValue) DELIM
		(httpStatus=value {$entry.setHttpStatus(Integer.parseInt($httpStatus.val));} | NoValue) DELIM
		(errorCode=value {$entry.setErrorCode($errorCode.val);} | NoValue) DELIM
		(bytesSent=value {$entry.setBytesSent(Long.parseLong($bytesSent.val));} | NoValue) DELIM
		(objectSize=value {$entry.setObjectSize(Long.parseLong($objectSize.val));} | NoValue) DELIM
		(totalTime=value {$entry.setTotalTime(Long.parseLong($totalTime.val));} | NoValue) DELIM
		(turnAroundTime=value {$entry.setTurnAroundTime(Long.parseLong($turnAroundTime.val));} | NoValue) DELIM
		(referrer=value {$entry.setReferrer($referrer.val);} | NoValue) DELIM
		(userAgent=value {$entry.setUserAgent($userAgent.val);} | NoValue) DELIM
		(versionId=value {$entry.setVersionId($versionId.val);} | NoValue)

		// have to account for "extras" being added later per spec
		(DELIM
			(extraValue=value {$entry.getExtras().add($extraValue.val);} | NoValue )
		)* DELIM* (LINEBREAK | EOF)
	;