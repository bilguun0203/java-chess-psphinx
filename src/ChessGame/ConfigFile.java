package ChessGame;

import java.io.*;
import java.util.Properties;

/**
 * Created by bilguun on 11/13/16.
 */
public class ConfigFile {

    private String fileName = "config.txt";
    private Properties propIn = new Properties();
    private Properties propOut = new Properties();
    private InputStream input = null;
    private OutputStream output = null;

    public ConfigFile() {}

    public String getConfig(String var){
        String value = "";

        try {
            input = new FileInputStream(fileName);
            propIn.load(input);
            value = propIn.getProperty(var);
        } catch (IOException ex) {
            ex.printStackTrace();
        }  finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public void setConfig(String var, String val){

        try {
            output = new FileOutputStream("config.txt");
            propOut.setProperty(var, val);
            propOut.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
