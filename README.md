### Install Maven
1) Go to the Maven Download site: https://maven.apache.org/download.cgi
		- Download the “Binary tar.gz archive” file as shown in the below image.
2) Extract the downloaded tar in a folder:
	- tar -xvf apache-maven-3.6.3-bin.tar.gz

3) Edit ~/.bash_profile or /etc/profile and add below lines:
	export M2_HOME="<path to maven>/apache-maven-3.6.3"
	PATH="${M2_HOME}/bin:${PATH}"
	export PATH

### Create a Flink Maven project
1) Run below command to generate a flink maven project:
	 -  mvn archetype:generate 
		-DarchetypeGroupId=org.apache.flink 
		-DarchetypeArtifactId=flink-quickstart-java 
		-DarchetypeVersion=1.2.1
		
   
### Run flink app with input stream (For App.java flink application)
    - run App.main()
    - Open terminal and start netcat with below command
        - nc -lk 9000
        - input text in the netcat terminal
   

### Running kafka on apple silicon M1
- git clone https://github.com/wurstmeister/kafka-docker.git
- build the image locally on the apple m1 machine and use the same in docker-compose

### Running the Flink app (Flink app running as main in Local jvm):
    - docker-compose -f docker-compose-local.yml up
    - run the S3SidhiApp.main
    - ## Produce docker message
      ./kafka_2.11-2.3.0/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic EVENT_STREAM_INPUT
      
      ## Consumer for output topic:
      ./kafka_2.11-2.3.0/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic EVENT_STREAM_OUTPUT --from-beginning
      
      
      ## Message:
          {
              "ID":"12C",
              "Name":"Dipanjan"
           }


### Running the Flink app (Flink app running as cluster localhost:8081):
    - For the building the JOB jar used shade to package jar dependencies and create a fat jar.
        - mvn clean package
    - docker-compose up
    - create jar of the flink app (Job) as below:
        - mvn clean package
    
    - copy the extension jars to the /opt/flink/lib folder in Jobmanager and taskmanager both
            - sudo docker cp <src lib folder> <containerid>:/opt/flink/lib
            - docker restart <containerId>
    - Deploy the job in flink (localhost:8081) with jobmanager by uploading the jar.
            - open flink dashboard with localhost:8081
            - create jar for the application
            - submit the jar as the JOB to the dashboard.
    - ## Produce docker message
      ./kafka_2.11-2.3.0/bin/kafka-console-producer.sh --broker-list kafka:9092 --topic EVENT_STREAM_INPUT
      
      ## Consumer for output topic:
      ./kafka_2.11-2.3.0/bin/kafka-console-consumer.sh --bootstrap-server zookeeper:9092 --topic EVENT_STREAM_OUTPUT --from-beginning  
      
      ## Input Message:
          {
              "ID":"12C",
              "Name":"Dipanjan"
           }
           
           
### Issue with Deploying Application to Flink cluster
   - Siddhi extension function are not identified during runtime as Dynamic loading is failing for the extension code.
        - TEMP Solution:
            - copy the jars to the Jobmanager and Task Manager /opt/flink/lib folder explictly:
                - sudo docker cp <src lib folder> <containerid>:/opt/flink/lib
            - Restart the containers for Job and Task Manager:
                - docker restart <containerId>
        - Permanent Solution:
            - Create a siddhiCep instance:
                - SiddhiCEP cep = SiddhiCEP.getSiddhiEnvironment(env);
            - Register the extension with the cep instance as below:
                - cep.registerExtension("json:toObject", io.siddhi.extension.execution.json.function.ToJSONObjectFunctionExtension.class);
                - cep.registerExtension( "json:getString", io.siddhi.extension.execution.json.function.GetStringJSONFunctionExtension.class);
            - Create DAG as below [use cep.from() instead of define()]:
                 DataStream<Map<String,Object>> output = cep
                                .from("inputStream",inputS,"awsS3")
                                .cql(S3_CQL)
                                .returnAsMap("outputStream");  
                                
   - Problem Flink not processing siddhi CEP operation:
            - https://stackoverflow.com/questions/67656155/not-able-to-process-kafka-json-message-with-flink-siddhi-library/67685761#67685761
            - Solution:
                    - Set the time charateristics of the flink execution environment:
                            - By default flink 1.13 considers event time as the time characteristics and follow below strategy:
                                    - An incoming element is initially put in a buffer where elements are sorted in ascending order based on their timestamp, and when a watermark arrives, all the elements in this buffer with timestamps smaller than that of the watermark are processed
                                    - So unless it recieves watermark in the stream , it will not output or process the data in the stream.
                            - env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);  

        - Remote Debug:
            - https://cwiki.apache.org/confluence/display/FLINK/Remote+Debugging+of+Flink+Clusters
            - https://www.programmersought.com/article/86754644555/
            
   - How to use state checkpointing while using siddhi with flink:
        - Siddhi uses its own internal state which flink has no idea about
         and so flink checkpointing will not work with siddhi state by default
         Unless the sisshi state is not integrated with flink state.
            
                
### Refs
       - https://blogs.oracle.com/javamagazine/streaming-analytics-with-java-and-apache-flink
       - https://stackoverflow.com/questions/54672599/json-file-data-into-kafka-topic
       