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
import java.util.Random;
//Code based on https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/AppendixSampleDataCodeJava.html

public class CsvFaker {

    public static void main(String[] args) {
        createSampleDroplets("TestLake1Mio6Clusters.csv", 1000000);
    }


    private static void createSampleDroplets(String fileName, int number) {

        Random r = new Random();
        r.setSeed(System.currentTimeMillis());
        Faker faker = new Faker();
        PLZFaker plzFaker = new PLZFaker();
        System.out.println("generating cluster(s)...");
        plzFaker.generatePlzCluster(6);
        System.out.println("Clusers generated.");
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName);

            String headerLine = "\"gender\",\"coronaVirus\",\"postalCode\",\"loneliness\",\"diarrhea\",\"userId\",\"insomnia\",\"limbPain\",\"coughing\",\"generalHealth\",\"soreThroat\",\"temperature\",\"runnyNose\",\"headache\",\"id\",\"numberOfContacts\",\"timestamp\",\"yearOfBirth\",";
            fw.write(headerLine + "\n");

            for (int i = 0; i < number; i++) {
                if (i % 1000 == 0) {
                    System.out.println("Generating droplet#" + i);
                }
                String plz = plzFaker.getPlz();

                int corona = 1;
                int rand = r.nextInt(100);
                if (plzFaker.isPlzInCluster(plz)) {
                    //increase probability of 2 and 3
                    if (rand > 50) {
                        corona = 2;
                    }
                    if (rand > 60) {
                        corona = 3;
                    }
                    if (rand > 70) {
                        corona = 4;
                    }
                    if (rand > 80) {
                        corona = 5;
                    }
                } else {
                    if (rand > 70) {
                        corona = 2;
                    }
                    if (rand > 72) {
                        corona = 3;
                    }
                    if (rand > 75) {
                        corona = 4;
                    }
                    if (rand > 80) {
                        corona = 5;
                    }
                }

                String line = "";
                line += "\"" + faker.number().numberBetween(1, 4) + "\",";
                line += "\"" + corona + "\",";
                line += "\"" + plz + "\",";
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
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
