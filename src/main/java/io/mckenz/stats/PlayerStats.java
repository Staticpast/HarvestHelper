package io.mckenz.stats;

public class PlayerStats {
    
    private int wheatHarvested;
    private int carrotsHarvested;
    private int potatoesHarvested;
    private int beetrootHarvested;
    private int netherWartHarvested;
    private int cocoaBeansHarvested;
    private int sweetBerriesHarvested;
    
    public PlayerStats() {
        this.wheatHarvested = 0;
        this.carrotsHarvested = 0;
        this.potatoesHarvested = 0;
        this.beetrootHarvested = 0;
        this.netherWartHarvested = 0;
        this.cocoaBeansHarvested = 0;
        this.sweetBerriesHarvested = 0;
    }
    
    public int getWheatHarvested() {
        return wheatHarvested;
    }
    
    public void setWheatHarvested(int wheatHarvested) {
        this.wheatHarvested = wheatHarvested;
    }
    
    public void incrementWheatHarvested() {
        this.wheatHarvested++;
    }
    
    public int getCarrotsHarvested() {
        return carrotsHarvested;
    }
    
    public void setCarrotsHarvested(int carrotsHarvested) {
        this.carrotsHarvested = carrotsHarvested;
    }
    
    public void incrementCarrotsHarvested() {
        this.carrotsHarvested++;
    }
    
    public int getPotatoesHarvested() {
        return potatoesHarvested;
    }
    
    public void setPotatoesHarvested(int potatoesHarvested) {
        this.potatoesHarvested = potatoesHarvested;
    }
    
    public void incrementPotatoesHarvested() {
        this.potatoesHarvested++;
    }
    
    public int getBeetrootHarvested() {
        return beetrootHarvested;
    }
    
    public void setBeetrootHarvested(int beetrootHarvested) {
        this.beetrootHarvested = beetrootHarvested;
    }
    
    public void incrementBeetrootHarvested() {
        this.beetrootHarvested++;
    }
    
    public int getNetherWartHarvested() {
        return netherWartHarvested;
    }
    
    public void setNetherWartHarvested(int netherWartHarvested) {
        this.netherWartHarvested = netherWartHarvested;
    }
    
    public void incrementNetherWartHarvested() {
        this.netherWartHarvested++;
    }
    
    public int getCocoaBeansHarvested() {
        return cocoaBeansHarvested;
    }
    
    public void setCocoaBeansHarvested(int cocoaBeansHarvested) {
        this.cocoaBeansHarvested = cocoaBeansHarvested;
    }
    
    public void incrementCocoaBeansHarvested() {
        this.cocoaBeansHarvested++;
    }
    
    public int getSweetBerriesHarvested() {
        return sweetBerriesHarvested;
    }
    
    public void setSweetBerriesHarvested(int sweetBerriesHarvested) {
        this.sweetBerriesHarvested = sweetBerriesHarvested;
    }
    
    public void incrementSweetBerriesHarvested() {
        this.sweetBerriesHarvested++;
    }
    
    public int getTotalHarvests() {
        return wheatHarvested + carrotsHarvested + potatoesHarvested + beetrootHarvested + 
               netherWartHarvested + cocoaBeansHarvested + sweetBerriesHarvested;
    }
} 