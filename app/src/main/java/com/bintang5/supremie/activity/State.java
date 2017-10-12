package com.bintang5.supremie.activity;


import java.util.ArrayList;

import model.Mie;
import model.MieStock;
import model.Order;
import utils.responses.GETResponseStock;

/**
 * Keeps key variables in memory.
 */
public class State  {

    private static State instance;

    private String diningMethod = null;

    private String brand = null;

    //id of the chosen flavour in chosen brand
    private Integer subMieId = null;

    private Integer quantityMie = null;

    //overall mie id
    private Integer mieId = null;

    private MieStock mieStock = null;

    private int[] drinkQuantities = null;

    private int[] toppingQuantities = null;

    private Integer pedasLevel = null;

    private int[] pedasPrices;

    private Integer grandTotal = null;

    private GETResponseStock allStock = null;

    ArrayList toppings = null;
    ArrayList drinks = null;

    public int getPedasPrice(int i) {
        return pedasPrices[i];
    }

    private String[] pedasDescriptions;

    private String[] pedasNasiDescriptions;

    private String taxChargeString = null;

//    private ArrayList<Mie> mies = null;

    private Order masterOrder;

    private State() {
        pedasPrices = new int[3];
//        pedasPrices[0] = 0;
        pedasPrices[0] = 0;
        pedasPrices[1] = 1000;
        pedasPrices[2] = 2000;
        toppings = new ArrayList();
        drinks = new ArrayList();
        pedasDescriptions = new String[3];
//        pedasDescriptions[0] = "RP 0\nLEVEL 0 (TANPA BUBUK CABAI DI DALAM PACKAGING)";
        pedasDescriptions[0] = "RP 0\nLEVEL 0 (HANYA BUBUK CABAI DI DALAM PACKAGING)";
        pedasDescriptions[1] = "RP 1.000\nLEVEL 1 (BUBUK CABAI DI DALAM PACKAGING + 5 CABAI RAWIT";
        pedasDescriptions[2] = "RP 2.000\nLEVEL 2 (BUBUK CABAI DI DALAM PACKAGAING + 20 CABAI RAWIT";
        pedasNasiDescriptions = new String[3];
        pedasNasiDescriptions[0] = "Rp 0\nLEVEL 0 (TANPA CABAI)";
        pedasNasiDescriptions[1] = "RP 1.000\nLEVEL 1 (5 CABAI RAWIT)";
//        pedasNasiDescriptions[2] = "RP 2.000\nLEVEL 2 (10 CABAI RAWIT)";
        pedasNasiDescriptions[2] = "RP 2.000\nLEVEL 2 (20 CABAI RAWIT)";
//        mies = new ArrayList<>();
        masterOrder = new Order();
    }

    public static State getInstance() {
        if (instance == null) {
            instance = new State();
        }
        return instance;
    }

    public void clear() {
        brand = null;
        subMieId = null;
        quantityMie = null;
        mieId = null;
        drinkQuantities = null;
        toppingQuantities = null;
        pedasLevel = null;
        toppings = null;
        drinks = null;
        allStock = null;
        grandTotal = null;
        mieStock = null;
    }

    public void clearChoices() {
        brand = null;
        subMieId = null;
        quantityMie = null;
        mieId = null;
        drinkQuantities = null;
        toppingQuantities = null;
        pedasLevel = null;
        grandTotal = null;
        mieStock = null;
//        mies = null;
    }

    public void clearNewMie() {
        brand = null;
        subMieId = null;
        quantityMie = null;
        mieId = null;
        pedasLevel = null;
        toppings = null;
        grandTotal = null;
        mieStock = null;
        toppingQuantities = null;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setChooseMieFragmentId(Integer subMieId, Integer quantityMie) {
        this.subMieId = subMieId;
        this.quantityMie = quantityMie;
    }

    public void setMieId(Integer mieId) {
        this.mieId = mieId;
    }

    public Integer getSubMieId() {
        return subMieId;
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

    public void setDiningMethod(String diningMethod) {
        this.diningMethod = diningMethod;
    }

    public String getDiningMethod() {
        return diningMethod;
    }

    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Integer getGrandTotal() {
        return grandTotal;
    }

    public ArrayList getToppings() {
        return toppings;
    }

    public ArrayList getDrinks() {
        return drinks;
    }

    public void setToppings(ArrayList toppings) {
        this.toppings = toppings;
    }

    public void setDrinks(ArrayList drinks) {
        this.drinks = drinks;
    }

    public void setAllStock(GETResponseStock allStock) {
        this.allStock = allStock;
    }

    public GETResponseStock getAllStock() {
        return allStock;
    }

    public String[] getPedasDescriptions() {
        return pedasDescriptions;
    }

    public String addDot(String s) {
        if (s.length() > 3) {
            return new StringBuffer(s).insert(s.length() - 3, ".").toString();
        } else {
            return s;
        }
    }

    public void setMieStock(MieStock ms) {
        this.mieStock = ms;
    }

    public MieStock getMieStock() {
        return mieStock;
    }

    public String getTaxServiceString() {
        return taxChargeString;
    }

    public void setTaxServiceString(String taxChargeString) {
        this.taxChargeString = taxChargeString;
    }

    public String[] getNasiPedasDescriptions() {
        return pedasNasiDescriptions;
    }

//    public ArrayList<Mie> getMies() { return mies; };

//    public void setMies(ArrayList<Mie> mies) {
//        this.mies = mies;
//    }

    public void setMasterOrder(Order masterOrder) {
        this.masterOrder = masterOrder;
    }

    public Order getMasterOrder() {
        return masterOrder;
    }
}
