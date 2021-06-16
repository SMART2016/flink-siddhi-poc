package com.stackidentity.rae.app.s3.transform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackidentity.rae.app.s3.model.Root;


public class JsonToS3 {
    static String input1 = "{\"awsS3\":{\"ResourceType\":\"aws.S3\",\"Details\":{\"Name\":\"crossplane-test\",\"CreationDate\":\"2020-08-17T11:28:05+00:00\"},\"Encryption\":{\"ServerSideEncryptionConfiguration\":{\"Rules\":[{\"ApplyServerSideEncryptionByDefault\":{\"SSEAlgorithm\":\"aws:kms1\",\"KMSMasterKeyID\":\"arn:aws:kms:us-west-2:440575206858:key/d9b59b5e-3618-4972-9f4f-17ce084a819d\"}}]}},\"AccessBlock\":{\"PublicAccessBlockConfiguration\":{\"BlockPublicAcls\":true,\"IgnorePublicAcls\":true,\"BlockPublicPolicy\":true,\"RestrictPublicBuckets\":true}},\"Location\":{\"LocationConstraint\":\"us-west-2\"}}}";
    static String input = "{\n" +
            "    \"awsS3\": {\n" +
            "        \"ResourceType\": \"aws.S3\",\n" +
            "        \"Details\": {\n" +
            "            \"Name\": \"crossplane-test\",\n" +
            "            \"CreationDate\": \"2020-08-17T11:28:05+00:00\"\n" +
            "        },\n" +
            "        \"Acl\": {\n" +
            "            \"Owner\": {\n" +
            "                \"DisplayName\": \"venkat\",\n" +
            "                \"ID\": \"70fdbc3521ad0b517f8452da0d462365d4e59f0f8b3545847017aa893d0545c4\"\n" +
            "            },\n" +
            "            \"Grants\": [\n" +
            "                {\n" +
            "                    \"Grantee\": {\n" +
            "                        \"DisplayName\": \"venkat\",\n" +
            "                        \"ID\": \"70fdbc3521ad0b517f8452da0d462365d4e59f0f8b3545847017aa893d0545c4\",\n" +
            "                        \"Type\": \"CanonicalUser\"\n" +
            "                    },\n" +
            "                    \"Permission\": \"FULL_CONTROL\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"Grantee\": {\n" +
            "                        \"Type\": \"Group\",\n" +
            "                        \"URI\": \"http://acs.amazonaws.com/groups/s3/LogDelivery\"\n" +
            "                    },\n" +
            "                    \"Permission\": \"WRITE\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"Grantee\": {\n" +
            "                        \"Type\": \"Group\",\n" +
            "                        \"URI\": \"http://acs.amazonaws.com/groups/s3/LogDelivery\"\n" +
            "                    },\n" +
            "                    \"Permission\": \"READ_ACP\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        \"Encryption\": {\n" +
            "            \"ServerSideEncryptionConfiguration\": {\n" +
            "                \"Rules\": [\n" +
            "                    {\n" +
            "                        \"ApplyServerSideEncryptionByDefault\": {\n" +
            "                            \"SSEAlgorithm\": \"aws:kms1\",\n" +
            "                            \"KMSMasterKeyID\": \"arn:aws:kms:us-west-2:440575206858:key/d9b59b5e-3618-4972-9f4f-17ce084a819d\"\n" +
            "                        }\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        \"Logging\": {\n" +
            "            \"LoggingEnabled\": {\n" +
            "                \"TargetBucket\": \"crossplane-test\",\n" +
            "                \"TargetPrefix\": \"\"\n" +
            "            }\n" +
            "        },\n" +
            "        \"AccessBlock\": {\n" +
            "            \"PublicAccessBlockConfiguration\": {\n" +
            "                \"BlockPublicAcls\": true,\n" +
            "                \"IgnorePublicAcls\": true,\n" +
            "                \"BlockPublicPolicy\": true,\n" +
            "                \"RestrictPublicBuckets\": true\n" +
            "            }\n" +
            "        },\n" +
            "        \"Location\": {\n" +
            "            \"LocationConstraint\": \"us-west-2\"\n" +
            "        }\n" +
            "    }\n" +
            "}";
    public static String getInput(){
        return input1;
    }
    public static Root convert(String inputEvent){
        if (inputEvent == ""){
            inputEvent = input;
        }

        // ObjectMapper instantiation
        ObjectMapper objectMapper = new ObjectMapper();

        // Deserialization into the `Employee` class
        Root s3Root = null;
        try {
            s3Root = objectMapper.readValue(inputEvent, Root.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // Print information
        return s3Root;
    }

}
