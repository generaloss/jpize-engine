package jpize.tests.mcose.launcher;

import jpize.util.io.FastReader;

public class MoseLauncherCli{

    private final MoseClient client;

    public MoseLauncherCli(){
        this.client = new MoseClient();

        final FastReader reader = new FastReader();
        while(!Thread.interrupted()){
            if(reader.hasNext()){
                final String line = reader.nextLine();
                if(line.equals("run")){
                    client.launch();
                }
                if(line.equals("download")){
                    client.downloadJar();
                }
            }
        }
    }

}
