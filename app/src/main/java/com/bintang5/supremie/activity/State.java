package com.bintang5.supremie.activity;


/**
 * Keeps key variables in memory.
 */
public class State  {

    private static State instance;

    private String brand = null;

    private Integer chooseMieFragmentId = null;

    private Integer quantityMie = null;

    private Integer mieId = null;

    private int[] drinkQuantities = null;

    private int[] toppingQuantities = null;

    private Integer pedasLevel = null;

    private int[] pedasPrices;

    public int getPedasPrice(int i) {
        return pedasPrices[i];
    }
    //    private Order order;

    private State() {
        pedasPrices = new int[4];
        pedasPrices[0] = 0;
        pedasPrices[1] = 1000;
        pedasPrices[2] = 2000;
        pedasPrices[3] = 3000;
    }

    public static State getInstance() {
        if (instance == null) {
            instance = new State();
        }
        return instance;
    }

    public void clear() {
//        order = null;
        brand = null;
        chooseMieFragmentId = null;
        quantityMie = null;
        mieId = null;
        drinkQuantities = null;
        toppingQuantities = null;
        pedasLevel = null;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
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

    public int[] getDrinkQuantities() {
        return drinkQuantities;
    }

    public int[] getToppingQuantities() {
        return toppingQuantities;
    }

    public void setDrinkQuantity(Integer id, Integer quantity) {
        this.drinkQuantities[id] = quantity;
    }

    public void initDrinkQuantities(int size) {
        drinkQuantities = new int[size];
    }

    public void setToppingQuantity(Integer id, Integer quantity) {
        this.toppingQuantities[id] = quantity;
    }

    public void initToppingQuantities(int size) {
        toppingQuantities = new int[size];
    }

    public Integer getPedasLevel() {
        return pedasLevel;
    }

    public void setPedasLevel(Integer pedasLevel) {
        this.pedasLevel = pedasLevel;
    }
}
