package bsp1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class EinwohnerManager {

    public ArrayList<Einwohner> load() throws DataFileException {
        ArrayList<Einwohner> result = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(".\\data\\testdata-einwohner.csv"))){

            br.readLine();
            String line;

            while((line = br.readLine()) != null){

                String[] data = line.split(";");
                Einwohner ew = new Einwohner(
                        Integer.parseInt(data[0]),
                        data[1],
                        data[2],
                        Integer.parseInt(data[3])
                );
                result.add(ew);

            }


        } catch (FileNotFoundException e) {
            throw new DataFileException(e);
        } catch (IOException e) {
            throw new DataFileException(e);
        }

        Collections.sort(result, new GeburtsjahrDescNameAscComparator());
        return result;
    }

}
