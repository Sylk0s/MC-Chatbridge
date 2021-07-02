import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class ServerListener {

    static JSONObject json;

    public static void main(String[] args) throws IOException {
        Runtime r = Runtime.getRuntime();
        Process p;
        BufferedReader in;
        String line;
        File configs = new File("configs.json");

        if(!configs.exists()) {
            System.out.println("No configs.json file found...");
            System.out.println("Creating new configs.json file");
            if (!configs.createNewFile()) {
                System.out.println("Cannot create new configs file");
            } else {
                System.out.println("Please go edit this file with the proper info");
            }
            return;
        }

        try {
            json = (JSONObject) new JSONParser().parse(new FileReader(configs.getPath()));
            Bot.channelID = (String) json.get("channelID");
            System.out.println(Bot.channelID);
            Bot.token = (String) json.get("token");
            System.out.println(Bot.token);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //java -jar MCServerListener.main.jar java -jar Server.jar -nogui

        p = r.exec(args);

        System.out.println("Server Starting...");

        in = new BufferedReader(new InputStreamReader(p.getInputStream()));

        Bot.getBot();

        while((line = in.readLine()) != null) {
            System.out.println("From Listener: " + line);
            if(Bot.isReady)
                Bot.getBot().sendMessage(line);
        }

        System.out.println("after line == null");
        System.out.flush();
    }
}
