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
		
### Start the Flink Jobmanager and Taskmanager
    - docker-compose up
   
### Run input stream
    - Open terminal and start netcat with below command
    - nc -lk 9000
   
### Create the application and deploy it as job to the Jobmanager
    - open flink dashboard with localhost:8081
    - create jar for the application
    - submit the jar as the JOB to the dashboard.