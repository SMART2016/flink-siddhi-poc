#!/usr/bin/env bash
# connector start command here.
exec "/opt/kafka/bin/connect-standalone.sh" "/opt/kafka/config/connect-standalone.properties" "/opt/kafka/config/CamelAws2s3SourceConnector.properties"