package ltd.penny.projects.dynamo.streams.consumer;


import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import ltd.penny.projects.config.ConfigFile;
import ltd.penny.projects.config.Configuration;

public class PurgeData {
    public static void main(String... args) {
        Configuration config = new ConfigFile("src/main/resources/config.yaml");

        RecordWorker worker = new RecordWorker();
        Worker streamWorker = worker.createWorker(config);
        try {
            System.out.println("STARTING STREAM WORKER!");
            streamWorker.run();
        } catch(Exception e) {
            System.out.println("ERROR RUNNING STREAM!");
           e.printStackTrace();
        }
    }
}
