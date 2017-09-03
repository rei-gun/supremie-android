package com.bintang5.supremie.activity;


/**
 * Keeps key variables in memory.
 */
public class State  {

    private static State instance;

    private Integer brandId = null;

    private Integer chooseMieFragmentId = null;

    private Integer quantityMie = null;

    private Integer mieId = null;

//    private Order order;

    private State() {}

    public static State getInstance() {
        if (instance == null) {
            instance = new State();
        }
        return instance;
    }

    public void clear() {
//        order = null;
    }

    public void setBrandId(Integer i) {
        brandId = i;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setChooseMieFragmentId(Integer chooseMieFragmentId, Integer quantityMie) {
        this.chooseMieFragmentId = chooseMieFragmentId;
        this.quantityMie = quantityMie;
    }

    public void setMieId(Integer mieId) {
        this.mieId = mieId;
    }

    public Integer getChooseMieFragmentId() {
        return chooseMieFragmentId;
    }

    public Integer getMieId() {
        return mieId;
    }

    public Integer getQuantityMie() {
        return quantityMie;
    }
}
