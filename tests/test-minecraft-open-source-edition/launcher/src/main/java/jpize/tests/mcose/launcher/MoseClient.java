package jpize.tests.mcose.launcher;

import jpize.net.NetUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MoseClient{

    public String jarUrl = "https://m119.syncusercontent.com/mfs-60:8c80592a794664aabb0a87399d14e2d4=============================/p/minecraft-osp-release-1.0.jar?allowdd=0&datakey=bfC/3rsbzQDM5c35Cdu0jD+9HrpnBB1KExFGmJifHlpAG+1D3dYI6tIy8EmNJMQQwRFgPTVlGx/uWlor5pe6qPzf5+ThAFGSNGwMkAV8RksHOjKbzjsTboFL4pp/bDTbXzkW0rBfH4iW7OLCApAXXT0f2PeGhuBaYGT8/NKLg/mjJrR/4zvgI5VEhU8rlYmbu0PiyIovvDFACkD8OgRby21sCAp+2AjRHFOmJ3NHxJGL2C0wd1LamQV7qpOM6Hm1G2MV6HL4mcpgX/w2H2EenWieRUYRh6cZXE2mNe0PhYMnbRSzSKTqPLUBTselUbz+M//728DTvZh8nv9BKt/9gA&engine=ln-3.1.38&errurl=FfHYCTnXTuStPcXxSgOV/9KX/w1lXcxzMAq3OTjdEJKTTy3vbBChL2bE7Vu3jnIrRvgzdYET0GP1ZZ5dyS4olk127dshuxE1h0pPItdXBTk+Sw1FckiXM09IC1h2gIS0ALtzXh0vDbnwA879aZah+MsD6tNjIYH3snsomzSh1erHRVbCSMP0ciSYuDl+qN78N/4qvcUmYYPWmajIyfZZvR1lQm37w42EEjMxr2c1F5+A/Dce6jKvcDGUB2hNeIL9nJOkoo8VLskRF0uoaz9WBmmGLePebeiGhXBxmNeLEGEov3fFOazqvKZr4sURFEvZ6Mvziw0Kg3H53yavDB5uXQ==&header1=Q29udGVudC1UeXBlOiB1bmRlZmluZWQ&header2=Q29udGVudC1EaXNwb3NpdGlvbjogYXR0YWNobWVudDsgZmlsZW5hbWU9Im1pbmVjcmFmdC1vc3AtcmVsZWFzZS0xLjAuamFyIjtmaWxlbmFtZSo9VVRGLTgnJ21pbmVjcmFmdC1vc3AtcmVsZWFzZS0xLjAuamFyOw&ipaddress=3118840829&linkcachekey=08456c5d0&linkoid=2139720011&mode=101&sharelink_id=14115101730011&timestamp=1695305981253&uagent=e48b8411abf8d2b6800305a39746cf60361c2aee&signature=8b30203f1c8bc78b91dfcb01887fb6731c9e780c&cachekey=60:8c80592a794664aabb0a87399d14e2d4=============================";
    public String jarName = "alpha-1.0.0.jar";
    public String classPath = "jpize.tests.mcose.Main";
    public String gameDir = "/home/user/Documents/lang/java/projects/JPize/tests/test-minecraft-open-source-edition/launcher/mcose/";
    public int memoryMb = 512;
    public String username = "GeneralPashon";
    public String session = "54_54-iWantPizza-54_54";
    public int winWidth = 1280;
    public int winHeight = 720;

    public void launch(){
        try{
            final ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(gameDir));
            processBuilder.command(getParameters());
            final Process process = processBuilder.start();

            // Debug
            if(process.info().commandLine().isPresent())
                System.out.println("Launch (" + process.info().commandLine().get() + ")");
            else
                System.out.println("Launch");

            // Print input
            try{
                final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while(!Thread.interrupted() && process.isAlive()){
                    final String line = reader.readLine();
                    if(line != null)
                        System.out.println(line);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void downloadJar(){
        final File jarFile = new File(gameDir + "/" + jarName);
        if(!jarFile.exists()){
            System.out.println("Downloading '" + jarName + "'...");
            NetUtils.downloadToFile(jarUrl, jarFile);
            System.out.println("Done.");
        }
    }

    private List<String> getParameters(){
        final List<String> parameters = new ArrayList<>();
        parameters.add("java");

        parameters.add("-Xms" + (memoryMb / 2) + "M");
        parameters.add("-Xmx" + memoryMb + "M");

        // parameters.add("-cp");
        parameters.add("-jar");
        parameters.add(jarName);
        // parameters.add(classPath);

        parameters.add("--username");
        parameters.add(username);
        parameters.add("--sessionToken");
        parameters.add(session);
        parameters.add("--gameDir");
        parameters.add(gameDir);
        parameters.add("--width");
        parameters.add(String.valueOf(winWidth));
        parameters.add("--height");
        parameters.add(String.valueOf(winHeight));

        return parameters;
    }

}
