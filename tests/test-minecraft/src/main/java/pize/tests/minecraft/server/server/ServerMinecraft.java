package pize.tests.minecraft.server.server;

public class ServerMinecraft{
/*
    protected final ServerConfiguration serverConfig;
    private final NetworkSystem networkSystem;
    private final ServerStatusResponse statusResponse = new ServerStatusResponse();
    private final Map<RegistryKey<IWorld>, ServerWorld> worlds = Maps.newLinkedHashMap();
    private PlayerList playerList;
    private int tickCounter;
    private long nanoTimeSinceStatusRefresh;
    private long serverTime = System.currentTimeMillis();
    private float tickTime;

    public ServerMinecraft(ServerConfiguration serverConfig){
        this.serverConfig = serverConfig;
        this.networkSystem = new NetworkSystem(this);
    }

    public void start(){
        Thread thread = new Thread( ()->{

            try{
                this.serverTime = System.currentTimeMillis();
                this.statusResponse.setServerDescription(new StringTextComponent(this.motd));
                this.statusResponse.setVersion(new ServerStatusResponse.Version(SharedConstants.getVersion().getName(),SharedConstants.getVersion().getProtocolVersion()));
                this.applyServerIconToResponse(this.statusResponse);

                while(this.serverRunning){
                    long i = System.currentTimeMillis() - this.serverTime;
                    if(i > 2000L && this.serverTime - this.timeOfLastWarning >= 15000L){
                        long j = i/50L;
                        LOGGER.warn("Can't keep up! Is the server overloaded? Running {}ms or {} ticks behind",i,j);
                        this.serverTime += j*50L;
                        this.timeOfLastWarning = this.serverTime;
                    }

                    this.serverTime += 50L;
                    LongTickDetector longtickdetector = LongTickDetector.func_233524_a_("Server");
                    this.func_240773_a_(longtickdetector);
                    this.profiler.startTick();
                    this.profiler.startSection("tick");
                    this.tick(this::isAheadOfTime);
                    this.profiler.endStartSection("nextTickWait");
                    this.isRunningScheduledTasks = true;
                    this.runTasksUntil = Math.max(System.currentTimeMillis() + 50L,this.serverTime);
                    this.runScheduledTasks();
                    this.profiler.endSection();
                    this.profiler.endTick();
                    this.func_240795_b_(longtickdetector);
                    this.serverIsRunning = true;
                }
            }catch(Throwable throwable1){
                LOGGER.error("Encountered an unexpected exception",throwable1);
                CrashReport crashreport;
                if(throwable1 instanceof ReportedException){
                    crashreport = this.addServerInfoToCrashReport(( (ReportedException) throwable1 ).getCrashReport());
                }else{
                    crashreport = this.addServerInfoToCrashReport(new CrashReport("Exception in server tick loop",throwable1));
                }

                File file1 = new File(new File(this.getDataDirectory(),"crash-reports"),"crash-" + ( new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss") ).format(new Date()) + "-server.txt");
                if(crashreport.saveToFile(file1)){
                    LOGGER.error("This crash report has been saved to: {}",(Object) file1.getAbsolutePath());
                }else{
                    LOGGER.error("We were unable to save this crash report to disk.");
                }

                this.finalTick(crashreport);
            }finally{
                try{
                    this.serverStopped = true;
                    this.stop();
                }catch(Throwable throwable){
                    LOGGER.error("Exception stopping the server",throwable);
                }finally{
                    this.systemExitNow();
                }

            }

        },"Server thread");
        thread.start();
    }

    protected void func_240787_a_(IChunkStatusListener p_240787_1_){
        IServerWorldInfo iserverworldinfo = this.serverConfig.getServerWorldInfo();
        DimensionGeneratorSettings dimensiongeneratorsettings = this.serverConfig.getDimensionGeneratorSettings();
        boolean flag = dimensiongeneratorsettings.func_236227_h_();
        long i = dimensiongeneratorsettings.getSeed();
        long j = BiomeManager.getHashedSeed(i);
        List<ISpecialSpawner> list = ImmutableList.of(new PhantomSpawner(),new PatrolSpawner(),new CatSpawner(),new VillageSiege(),new WanderingTraderSpawner(iserverworldinfo));
        SimpleRegistry<Dimension> simpleregistry = dimensiongeneratorsettings.func_236224_e_();
        Dimension dimension = simpleregistry.getValueForKey(Dimension.OVERWORLD);
        ChunkGenerator chunkgenerator;
        DimensionType dimensiontype;
        if(dimension == null){
            dimensiontype = this.field_240767_f_.func_230520_a_().getOrThrow(DimensionType.OVERWORLD);
            chunkgenerator = DimensionGeneratorSettings.func_242750_a(this.field_240767_f_.getRegistry(Registry.BIOME_KEY),this.field_240767_f_.getRegistry(Registry.NOISE_SETTINGS_KEY),( new Random() ).nextLong());
        }else{
            dimensiontype = dimension.getDimensionType();
            chunkgenerator = dimension.getChunkGenerator();
        }

        ServerWorld serverworld = new ServerWorld(this,this.backgroundExecutor,this.anvilConverterForAnvilFile,iserverworldinfo,World.OVERWORLD,dimensiontype,p_240787_1_,chunkgenerator,flag,j,list,true);
        this.worlds.put(World.OVERWORLD,serverworld);
        DimensionSavedDataManager dimensionsaveddatamanager = serverworld.getSavedData();
        this.func_213204_a(dimensionsaveddatamanager);
        this.field_229733_al_ = new CommandStorage(dimensionsaveddatamanager);
        WorldBorder worldborder = serverworld.getWorldBorder();
        worldborder.deserialize(iserverworldinfo.getWorldBorderSerializer());
        if(!iserverworldinfo.isInitialized()){
            try{
                func_240786_a_(serverworld,iserverworldinfo,dimensiongeneratorsettings.hasBonusChest(),flag,true);
                iserverworldinfo.setInitialized(true);
                if(flag){
                    this.func_240778_a_(this.serverConfig);
                }
            }catch(Throwable throwable1){
                CrashReport crashreport = CrashReport.makeCrashReport(throwable1,"Exception initializing level");

                try{
                    serverworld.fillCrashReport(crashreport);
                }catch(Throwable throwable){
                }

                throw new ReportedException(crashreport);
            }

            iserverworldinfo.setInitialized(true);
        }

        this.getPlayerList().func_212504_a(serverworld);
        if(this.serverConfig.getCustomBossEventData() != null){
            this.getCustomBossEvents().read(this.serverConfig.getCustomBossEventData());
        }

        for(Map.Entry<RegistryKey<Dimension>, Dimension> entry: simpleregistry.getEntries()){
            RegistryKey<Dimension> registrykey = entry.getKey();
            if(registrykey != Dimension.OVERWORLD){
                RegistryKey<World> registrykey1 = RegistryKey.getOrCreateKey(Registry.WORLD_KEY,registrykey.getLocation());
                DimensionType dimensiontype1 = entry.getValue().getDimensionType();
                ChunkGenerator chunkgenerator1 = entry.getValue().getChunkGenerator();
                DerivedWorldInfo derivedworldinfo = new DerivedWorldInfo(this.serverConfig,iserverworldinfo);
                ServerWorld serverworld1 = new ServerWorld(this,this.backgroundExecutor,this.anvilConverterForAnvilFile,derivedworldinfo,registrykey1,dimensiontype1,p_240787_1_,chunkgenerator1,flag,j,ImmutableList.of(),false);
                worldborder.addListener(new IBorderListener.Impl(serverworld1.getWorldBorder()));
                this.worlds.put(registrykey1,serverworld1);
            }
        }

    }

    private void loadInitialChunks(IChunkStatusListener p_213186_1_){
        ServerWorld serverworld = this.func_241755_D_();
        LOGGER.info("Preparing start region for dimension {}",serverworld.getDimensionKey().getLocation());
        BlockPos blockpos = serverworld.getSpawnPoint();
        p_213186_1_.start(new ChunkPos(blockpos));
        ServerChunkProvider serverchunkprovider = serverworld.getChunkProvider();
        serverchunkprovider.getLightManager().func_215598_a(500);
        this.serverTime = Util.milliTime();
        serverchunkprovider.registerTicket(TicketType.START,new ChunkPos(blockpos),11,Unit.INSTANCE);

        while(serverchunkprovider.getLoadedChunksCount() != 441){
            this.serverTime = Util.milliTime() + 10L;
            this.runScheduledTasks();
        }

        this.serverTime = Util.milliTime() + 10L;
        this.runScheduledTasks();

        for(ServerWorld serverworld1: this.worlds.values()){
            ForcedChunksSaveData forcedchunkssavedata = serverworld1.getSavedData().get(ForcedChunksSaveData::new,"chunks");
            if(forcedchunkssavedata != null){
                LongIterator longiterator = forcedchunkssavedata.getChunks().iterator();

                while(longiterator.hasNext()){
                    long i = longiterator.nextLong();
                    ChunkPos chunkpos = new ChunkPos(i);
                    serverworld1.getChunkProvider().forceChunk(chunkpos,true);
                }
            }
        }

        this.serverTime = Util.milliTime() + 10L;
        this.runScheduledTasks();
        p_213186_1_.stop();
        serverchunkprovider.getLightManager().func_215598_a(5);
        this.func_240794_aZ_();
    }

    public boolean save(boolean suppressLog,boolean flush,boolean forced){
        boolean flag = false;

        for(ServerWorld serverworld: this.getWorlds()){
            if(!suppressLog){
                LOGGER.info("Saving chunks for level '{}'/{}",serverworld,serverworld.getDimensionKey().getLocation());
            }

            serverworld.save((IProgressUpdate) null,flush,serverworld.disableLevelSaving && !forced);
            flag = true;
        }

        ServerWorld serverworld1 = this.func_241755_D_();
        IServerWorldInfo iserverworldinfo = this.serverConfig.getServerWorldInfo();
        iserverworldinfo.setWorldBorderSerializer(serverworld1.getWorldBorder().getSerializer());
        this.serverConfig.setCustomBossEventData(this.getCustomBossEvents().write());
        this.anvilConverterForAnvilFile.saveLevel(this.field_240767_f_,this.serverConfig,this.getPlayerList().getHostPlayerData());
        return flag;
    }

    public void stop(){
        LOGGER.info("Stopping server");
        if(this.getNetworkSystem() != null){
            this.getNetworkSystem().terminateEndpoints();
        }

        if(this.playerList != null){
            LOGGER.info("Saving players");
            this.playerList.saveAllPlayerData();
            this.playerList.removeAllPlayers();
        }

        LOGGER.info("Saving worlds");

        for(ServerWorld serverworld: this.getWorlds()){
            if(serverworld != null){
                serverworld.disableLevelSaving = false;
            }
        }

        this.save(false,true,false);

        for(ServerWorld serverworld1: this.getWorlds()){
            if(serverworld1 != null){
                try{
                    serverworld1.close();
                }catch(IOException ioexception1){
                    LOGGER.error("Exception closing the level",(Throwable) ioexception1);
                }
            }
        }

        if(this.snooper.isSnooperRunning()){
            this.snooper.stop();
        }

        this.resourceManager.close();

        try{
            this.anvilConverterForAnvilFile.close();
        }catch(IOException ioexception){
            LOGGER.error("Failed to unlock level {}",this.anvilConverterForAnvilFile.getSaveName(),ioexception);
        }

    }

    protected void tick(BooleanSupplier hasTimeLeft){
        long i = System.currentTimeMillis();
        ++this.tickCounter;
        this.updateTimeLightAndEntities(hasTimeLeft);
        if(i - this.nanoTimeSinceStatusRefresh >= 5000000000L){
            this.nanoTimeSinceStatusRefresh = i;
            this.statusResponse.setPlayers(new ServerStatusResponse.Players(this.getMaxPlayers(),this.getCurrentPlayerCount()));
            GameProfile[] agameprofile = new GameProfile[Math.min(this.getCurrentPlayerCount(),12)];
            int j = MathHelper.nextInt(this.random,0,this.getCurrentPlayerCount() - agameprofile.length);

            for(int k = 0; k < agameprofile.length; ++k){
                agameprofile[k] = this.playerList.getPlayers().get(j + k).getGameProfile();
            }

            Collections.shuffle(Arrays.asList(agameprofile));
            this.statusResponse.getPlayers().setPlayers(agameprofile);
        }

        if(this.tickCounter%6000 == 0){
            LOGGER.debug("Autosave started");
            this.profiler.startSection("save");
            this.playerList.saveAllPlayerData();
            this.save(true,false,false);
            this.profiler.endSection();
            LOGGER.debug("Autosave finished");
        }

        this.profiler.startSection("snooper");
        if(!this.snooper.isSnooperRunning() && this.tickCounter > 100){
            this.snooper.start();
        }

        if(this.tickCounter%6000 == 0){
            this.snooper.addMemoryStatsToSnooper();
        }

        this.profiler.endSection();
        this.profiler.startSection("tallying");
        long l = this.tickTimeArray[this.tickCounter%100] = Util.nanoTime() - i;
        this.tickTime = this.tickTime*0.8F + (float) l/1000000.0F*0.19999999F;
        long i1 = Util.nanoTime();
        this.frameTimer.addFrame(i1 - i);
        this.profiler.endSection();
    }

    protected void updateTimeLightAndEntities(BooleanSupplier hasTimeLeft){
        this.profiler.startSection("commandFunctions");
        this.getFunctionManager().tick();
        this.profiler.endStartSection("levels");

        for(ServerWorld serverworld: this.getWorlds()){
            this.profiler.startSection(()->{
                return serverworld + " " + serverworld.getDimensionKey().getLocation();
            });
            if(this.tickCounter%20 == 0){
                this.profiler.startSection("timeSync");
                this.playerList.func_232642_a_(new SUpdateTimePacket(serverworld.getGameTime(),serverworld.getDayTime(),serverworld.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)),serverworld.getDimensionKey());
                this.profiler.endSection();
            }

            this.profiler.startSection("tick");

            try{
                serverworld.tick(hasTimeLeft);
            }catch(Throwable throwable){
                CrashReport crashreport = CrashReport.makeCrashReport(throwable,"Exception ticking world");
                serverworld.fillCrashReport(crashreport);
                throw new ReportedException(crashreport);
            }

            this.profiler.endSection();
            this.profiler.endSection();
        }

        this.profiler.endStartSection("connection");
        this.getNetworkSystem().tick();
        this.profiler.endStartSection("players");
        this.playerList.tick();
        if(SharedConstants.developmentMode){
            TestCollection.field_229570_a_.func_229574_b_();
        }

        this.profiler.endStartSection("server gui refresh");

        for(int i = 0; i < this.tickables.size(); ++i){
            this.tickables.get(i).run();
        }

        this.profiler.endSection();
    }

    public ServerWorld getWorld(RegistryKey<IWorld> dimension){
        return this.worlds.get(dimension);
    }

    public Iterable<ServerWorld> getWorlds(){
        return this.worlds.values();
    }

    private void sendDifficultyToPlayer(ServerPlayerEntity playerIn){
        IWorldInfo iworldinfo = playerIn.getServerWorld().getWorldInfo();
        playerIn.connection.sendPacket(new SServerDifficultyPacket(iworldinfo.getDifficulty(),iworldinfo.isDifficultyLocked()));
    }

    public PlayerList getPlayerList(){
        return this.playerList;
    }

    public void setPlayerList(PlayerList list){
        this.playerList = list;
    }

    public abstract boolean getPublic();

    public NetworkSystem getNetworkSystem(){
        return this.networkSystem;
    }

    public abstract boolean shareToLAN(int port);

    public int getTickCounter(){
        return this.tickCounter;
    }

    public long getServerTime(){
        return this.serverTime;
    }

    public GameRules getGameRules(){
        return this.func_241755_D_().getGameRules();
    }

    public float getTickTime(){
        return this.tickTime;
    }

    public void dumpDebugInfo(Path p_223711_1_) throws IOException{
        Path path = p_223711_1_.resolve("levels");

        for(Map.Entry<RegistryKey<World>, ServerWorld> entry: this.worlds.entrySet()){
            ResourceLocation resourcelocation = entry.getKey().getLocation();
            Path path1 = path.resolve(resourcelocation.getNamespace()).resolve(resourcelocation.getPath());
            Files.createDirectories(path1);
            entry.getValue().writeDebugInfo(path1);
        }

        this.dumpGameRules(p_223711_1_.resolve("gamerules.txt"));
        this.dumpClasspath(p_223711_1_.resolve("classpath.txt"));
        this.dumpDummyCrashReport(p_223711_1_.resolve("example_crash.txt"));
        this.dumpStats(p_223711_1_.resolve("stats.txt"));
        this.dumpThreads(p_223711_1_.resolve("threads.txt"));
    }

    private void dumpStats(Path p_223710_1_) throws IOException{
        try(Writer writer = Files.newBufferedWriter(p_223710_1_)){
            writer.write(String.format("pending_tasks: %d\n",this.getQueueSize()));
            writer.write(String.format("average_tick_time: %f\n",this.getTickTime()));
            writer.write(String.format("tick_times: %s\n",Arrays.toString(this.tickTimeArray)));
            writer.write(String.format("queue: %s\n",Util.getServerExecutor()));
        }

    }

    private void dumpDummyCrashReport(Path p_223709_1_) throws IOException{
        CrashReport crashreport = new CrashReport("Server dump",new Exception("dummy"));
        this.addServerInfoToCrashReport(crashreport);

        try(Writer writer = Files.newBufferedWriter(p_223709_1_)){
            writer.write(crashreport.getCompleteReport());
        }
    }

    private void dumpGameRules(Path p_223708_1_) throws IOException{
        try(Writer writer = Files.newBufferedWriter(p_223708_1_)){
            final List<String> list = Lists.newArrayList();
            final GameRules gamerules = this.getGameRules();
            GameRules.visitAll(new GameRules.IRuleEntryVisitor(){
                public <T extends GameRules.RuleValue<T>> void visit(GameRules.RuleKey<T> key,GameRules.RuleType<T> type){
                    list.add(String.format("%s=%s\n",key.getName(),gamerules.<T> get(key).toString()));
                }
            });

            for(String s: list){
                writer.write(s);
            }
        }

    }

    private void dumpThreads(Path p_223712_1_) throws IOException{
        ThreadMXBean threadmxbean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] athreadinfo = threadmxbean.dumpAllThreads(true,true);
        Arrays.sort(athreadinfo,Comparator.comparing(ThreadInfo::getThreadName));

        try(Writer writer = Files.newBufferedWriter(p_223712_1_)){
            for(ThreadInfo threadinfo: athreadinfo){
                writer.write(threadinfo.toString());
                writer.write(10);
            }
        }

    }
*/
}
