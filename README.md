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
    - make entry in /etc/hosts and save:
        127.0.0.1 kafka
        127.0.0.1 kafka
    - create jar of the flink app (Job) as below:
        - mvn clean package
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