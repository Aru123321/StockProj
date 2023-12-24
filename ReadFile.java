import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadFile {
    private String filePath;
    private List<HashMap<String, String>> data;

    public ReadFile(String filePath) {
        this.filePath = filePath;
        this.data = new ArrayList<>();
        readCSV();
    }

    private void readCSV() {
        List<HashMap<String, String>> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            String[] headers = br.readLine().split(","); // Read the header line

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                HashMap<String, String> map = new HashMap<>();

                for (int i = 0; i < values.length; i++) {
                    map.put(headers[i], values[i]);
                }

                dataList.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.data = dataList;
    }

    public List<HashMap<String, String>> getData() {
        return this.data;
    }

    // need to add sth like modify data
    // better change the name like dataObjects or sth else
    // and set readCSV as a static method
}
