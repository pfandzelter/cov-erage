import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.github.javafaker.Faker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
//Code based on https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/AppendixSampleDataCodeJava.html

public class CsvFaker {

    public static void main(String[] args) {
        createSampleDroplets("csvFaker.csv", 100000);
    }


    private static void createSampleDroplets(String fileName, int number) {

        Faker faker = new Faker();
        PLZFaker plzFaker = new PLZFaker();
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName);

            String headerLine = "\"gender\",\"coronaVirus\",\"postalCode\",\"loneliness\",\"diarrhea\",\"userId\",\"insomnia\",\"limbPain\",\"coughing\",\"generalHealth\",\"soreThroat\",\"temperature\",\"runnyNose\",\"headache\",\"id\",\"numberOfContacts\",\"timestamp\",\"yearOfBirth\",";
            fw.write(headerLine + "\n");

            for (int i = 0; i < number; i++) {
                String line = "";
                line += "\"" + faker.number().numberBetween(1, 4) + "\",";
                line += "\"" + faker.number().numberBetween(1, 4) + "\",";
                line += "\"" + plzFaker.getPlz() + "\",";
                line += "\"" + faker.number().numberBetween(1, 6) + "\",";
                line += "\"" + faker.number().numberBetween(1, 3) + "\",";
                line += "\"" + faker.artist().name() + "\",";
                line += "\"" + faker.number().numberBetween(1, 6) + ",\"";
                line += "\"" + faker.number().numberBetween(1, 4) + "\",";
                line += "\"" + faker.number().numberBetween(1, 4) + "\",";
                line += "\"" + faker.number().numberBetween(1, 6) + "\",";
                line += "\"" + faker.number().numberBetween(1, 4) + "\",";
                line += "\"" + faker.number().numberBetween(1, 4) + "\",";
                line += "\"" + faker.number().numberBetween(1, 4) + "\",";
                line += "\"" + faker.number().numberBetween(1, 4) + "\",";
                line += "\"" + faker.idNumber().valid() + "\",";
                line += "\"" + faker.number().numberBetween(1, 1000) + "\",";
                line += "\"" + new Date().getTime() + "\",";
                line += "\"" + faker.number().numberBetween(1950, 2010) + "\",";
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
