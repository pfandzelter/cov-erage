import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class PLZFaker {

    ArrayList<String> plzs = new ArrayList<String>();
    Random r = new Random();

    PLZFaker() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("postleitzahlen.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                plzs.add(line.split(",")[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPlz() {
        return plzs.get(r.nextInt(plzs.size()));
    }
}
