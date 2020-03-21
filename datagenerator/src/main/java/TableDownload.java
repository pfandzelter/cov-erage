import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TableDownload {

    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(new PropertiesFileCredentialsProvider("AwsCredentials.properties"))
            .withRegion(Regions.EU_CENTRAL_1)
            .build();
    static DynamoDB dynamoDB = new DynamoDB(client);


    public static void main(String[] args) {
        downloadTable("TestLake", "data.csv");
    }

    private static void downloadTable(String tableName, String fileName) {
        ScanRequest scanRequest = new ScanRequest().withTableName((String) tableName);
        ScanResult result = client.scan(scanRequest);

        //Read key names
        Map <String, AttributeValue> firstresult = result.getItems().get(0);
        ArrayList<String> attributeNames = new ArrayList<String>();
        for (String name : firstresult.keySet()) {
            attributeNames.add(name);
        }

        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName);

            String headerLine = "";
            //Write headers
            for (int i = 0; i < attributeNames.size(); i++) {
                headerLine = headerLine + "\"" + attributeNames.get(i) + "\",";
            }
            fw.write(headerLine + "\n");

            for (Map<String, AttributeValue> entry : result.getItems()) {
                String line = "";
                for (int i = 0; i < attributeNames.size(); i++) {
                    char type = 'S';
                    if (entry.get(attributeNames.get(i)).getN() != null) {
                        type = 'N';
                    }
                    switch (type) {
                        case 'S':
                            line = line + "\"" + entry.get(attributeNames.get(i)).getS() + "\", ";
                            break;
                        case 'N':
                            line = line + "\"" + entry.get(attributeNames.get(i)).getN() + "\", ";
                            break;
                    }
                }
                fw.write(line + "\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
