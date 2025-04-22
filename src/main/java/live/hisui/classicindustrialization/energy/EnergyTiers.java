package live.hisui.classicindustrialization.energy;

public enum EnergyTiers {

    LOW_VOLTAGE(32,32,64000,32,32),
    MED_VOLTAGE(128,128,256000,128,128),
    HIGH_VOLTAGE(256, 256, 1024000, 256, 256),
    EXTREME_VOLTAGE(1024,1024,256000000, 1024, 1024);


    private final int CONSUMPTION_RATE;
    private final int PRODUCTION_RATE;
    private final int STORAGE_SIZE;
    private final int MAX_EXTRACT;
    private final int MAX_INSERT;

    private EnergyTiers(int consume_rate, int produce_rate, int storage_size, int max_extract, int max_input) {
        this.CONSUMPTION_RATE = consume_rate;
        this.PRODUCTION_RATE = produce_rate;
        this.STORAGE_SIZE = storage_size;
        this.MAX_EXTRACT = max_extract;
        this.MAX_INSERT = max_input;
    }

    public int getConsumptionRate() {
        return CONSUMPTION_RATE;
    }

    public int getProductionRate() {
        return PRODUCTION_RATE;
    }

    public int getMaxEnergyStored() {
        return STORAGE_SIZE;
    }

    public int getMaxEnergyExtract() {
        return MAX_EXTRACT;
    }

    public int getMaxEnergyInsert() {
        return MAX_INSERT;
    }
}
