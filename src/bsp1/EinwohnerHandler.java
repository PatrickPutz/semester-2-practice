package bsp1;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class EinwohnerHandler implements Runnable{

    private Socket client;
    private EinwohnerManager em;

    public EinwohnerHandler(Socket client) {
        this.client = client;
        this.em = new EinwohnerManager();
    }


    public void start() throws DataFileException {

        ArrayList<Einwohner> einwohnerList = em.load();

        System.out.println("Waiting for client command...");
        try(BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {

            String command;

            while((command = br.readLine()) != null){

                System.out.println("command = " + command);
                String[] cmd = command.split(" ");

                if(cmd[0].equalsIgnoreCase("GET")){
                    if(cmd.length == 2){
                        System.out.println("Received GET request...");
                        if(cmd[1].equalsIgnoreCase("steiermark")){
                            searchByBundesland(einwohnerList, bw, cmd[1]);
                            bw.write("GET request fulfilled!");
                            bw.newLine();
                            bw.flush();
                        } else if(cmd[1].equalsIgnoreCase("wien")){
                            searchByBundesland(einwohnerList, bw, cmd[1]);
                            bw.write("GET request fulfilled!");
                            bw.newLine();
                            bw.flush();
                        }
                        else{
                            searchByGeburtsjahrOrdered(einwohnerList, bw, cmd[1]);
                            bw.write("GET request fulfilled!");
                            bw.newLine();
                            bw.flush();
                        }
                    }
                }
                else if(cmd[0].equalsIgnoreCase("exit")){
                    client.close();
                }
                else{
                    bw.write("Unkown command!");
                    bw.newLine();
                    bw.flush();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try{
            start();
        } catch (DataFileException e) {
            e.printStackTrace();
        }
    }

    private void searchByBundesland(ArrayList<Einwohner> ew, BufferedWriter bw, String bundesland) throws IOException {

        for (Einwohner einwohner : ew) {
            if(einwohner.getBundesland().equalsIgnoreCase(bundesland)){
                bw.write(einwohner.toString());
                bw.newLine();
                bw.flush();
            }
        }

    }

    private void searchByGeburtsjahrOrdered(ArrayList<Einwohner> ew, BufferedWriter bw, String geburtsjahr) throws IOException {

        ArrayList<Einwohner> result = ew;
        Collections.sort(result);

        for (Einwohner einwohner : result) {
            if(Integer.parseInt(geburtsjahr) == einwohner.getGeburtsjahr()){
                bw.write(einwohner.toString());
                bw.newLine();
                bw.flush();
            }
        }

    }

}
