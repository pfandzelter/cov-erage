import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class PLZFaker {

    ArrayList<String> plzs = new ArrayList<String>();
    ArrayList<String> clusters = new ArrayList<>();
    Random r = new Random();

    PLZFaker() {
        r.setSeed(System.currentTimeMillis());
        try {
            BufferedReader br = new BufferedReader(new FileReader("postleitzahlen.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                plzs.add(line.split(",")[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        plzs.sort(new Comparator<String>() {
            public int compare(String o1, String o2) {
                int p1 = Integer.parseInt(o1);
                int p2 = Integer.parseInt(o2);
                if (p1 < p2) {
                    return -1;
                }
                if (p1 == p2) {
                    return 0;
                }
                return 1;
            }
        });
    }

    public String getPlz() {
        return plzs.get(r.nextInt(plzs.size()));
    }

    public void generatePlzCluster(int count) {
        clusters = new ArrayList<>();
        for (int c = 0; c < count; c++) {
            int idx = r.nextInt(plzs.size());
            for (int i = idx - 10; i < idx + 10; i++) {
                if (i >= 0 && i < plzs.size()) {
                    clusters.add(plzs.get(i));
                }
            }
        }
    }

    public boolean isPlzInCluster(String plz) {
        for (String p : clusters) {
            if (p.equals(plz)) {
                return true;
            }
        }
        return false;
    }


}
