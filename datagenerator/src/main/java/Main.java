import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Date;
//Code based on https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/AppendixSampleDataCodeJava.html

public class Main {

    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(new PropertiesFileCredentialsProvider("AwsCredentials.properties"))
            .withRegion(Regions.EU_CENTRAL_1)
            .build();
    static DynamoDB dynamoDB = new DynamoDB(client);

    public static void main(String[] args) {
        deleteTable("TestLake");
        createTable("TestLake", 5, 5, "id", "S", "timestamp", "S");
        loadSampleDroplets("TestLake", 1000);
    }

    private static void deleteTable(String tableName) {
        Table table = dynamoDB.getTable(tableName);
        try {
            System.out.println("Issuing DeleteTable request for " + tableName);
            table.delete();
            System.out.println("Waiting for " + tableName + " to be deleted...this may take a while...");
            table.waitForDelete();
        }
        catch (Exception e) {
            System.err.println("DeleteTable request failed for " + tableName);
            System.err.println(e.getMessage());
        }
    }

    private static void createTable(String tableName, long readCapacityUnits, long writeCapacityUnits,
                                    String partitionKeyName, String partitionKeyType, String sortKeyName, String sortKeyType) {
        try {

            ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
            keySchema.add(new KeySchemaElement().withAttributeName(partitionKeyName).withKeyType(KeyType.HASH)); // Partition key
            ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
            attributeDefinitions
                    .add(new AttributeDefinition().withAttributeName(partitionKeyName).withAttributeType(partitionKeyType));

            if (sortKeyName != null) {
                keySchema.add(new KeySchemaElement().withAttributeName(sortKeyName).withKeyType(KeyType.RANGE)); // Sort key
                attributeDefinitions
                        .add(new AttributeDefinition().withAttributeName(sortKeyName).withAttributeType(sortKeyType));
            }

            CreateTableRequest request = new CreateTableRequest().withTableName(tableName).withKeySchema(keySchema)
                    .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(readCapacityUnits)
                            .withWriteCapacityUnits(writeCapacityUnits));
            request.setAttributeDefinitions(attributeDefinitions);

            System.out.println("Issuing CreateTable request for " + tableName);
            Table table = dynamoDB.createTable(request);
            System.out.println("Waiting for " + tableName + " to be created...this may take a while...");
            table.waitForActive();
        }
        catch (Exception e) {
            System.err.println("CreateTable request failed for " + tableName);
            System.err.println(e.getMessage());
        }
    }

    private static void loadSampleDroplets(String tableName, int number) {

        Table table = dynamoDB.getTable(tableName);
        Faker faker = new Faker();


        try {

            System.out.println("Adding data to " + tableName + " ...");

            for (int i = 0; i < number ; i++) {

                Item item = new Item().withPrimaryKey("id", faker.idNumber().valid())
                        .withString("timestamp", "" + new Date().getTime())
                        .withString("userId", faker.artist().name())
                        .withString("postalCode", "" + faker.number().numberBetween(10000, 99999))
                        .withInt("gender", faker.number().numberBetween(1,4))
                        .withInt("yearOfBirth", faker.number().numberBetween(1950, 2010))
                        .withInt("generalHealth", faker.number().numberBetween(1, 6))
                        .withInt("coronaVirus", faker.number().numberBetween(1, 4))
                        .withInt("numberOfContacts", faker.number().numberBetween(1, 1000))
                        .withInt("coughing", faker.number().numberBetween(1, 4))
                        .withInt("temperature", faker.number().numberBetween(1,4))
                        .withInt("headache", faker.number().numberBetween(1, 4))
                        .withInt("soreThroat", faker.number().numberBetween(1, 4))
                        .withInt("runnyNose", faker.number().numberBetween(1, 4))
                        .withInt("limbPain", faker.number().numberBetween(1, 4))
                        .withInt("diarrhea", faker.number().numberBetween(1, 3))
                        .withInt("loneliness", faker.number().numberBetween(1, 6))
                        .withInt("insomnia", faker.number().numberBetween(1, 6));
                table.putItem(item);
            }
        }
        catch (Exception e) {
            System.err.println("Failed to create item in " + tableName);
            System.err.println(e.getMessage());
        }

    }
}
