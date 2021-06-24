## Tooling in Local Machine

1. Install Oracle Java SE Development Kit (JDK) version 1.8.
2. Set the JAVA_HOME environment variable.
        - For zsh shell (echo $SHELL)
            - sudo vi ~/.zprofile
            - Add below in the last line:
                export JAVA_HOME=<java installation path>
3. Download the latest tooling distribution from 
    https://github.com/siddhi-io/distribution/releases/download/v5.1.0/siddhi-tooling-5.1.0.zip.
4. Extract the downloaded zip and navigate to <TOOLING_HOME>/bin. 
        (TOOLING_HOME refers to the extracted folder).
5. Issue the following command in the command prompt (Windows) / terminal (Linux/Mac)
        - For Windows: tooling.bat  
        - For Linux/Mac: ./tooling.sh 
        
6. Once the server is started hit below Url in browser to open siddhi editor:
     http://192.168.1.44:9390/editor


