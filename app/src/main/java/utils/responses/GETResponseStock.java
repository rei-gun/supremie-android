package utils.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import model.DrinkStock;
import model.MieStock;
import model.ToppingStock;

public class GETResponseStock {

    @SerializedName("mie")
    @Expose
    private ArrayList<MieStock> mieStocks;
    @SerializedName("drinks")
    @Expose
    private ArrayList<DrinkStock> drinkStocks;
    @SerializedName("toppings")
    @Expose
    private ArrayList<ToppingStock> toppingStocks;

    public GETResponseStock(ArrayList<MieStock> mieStocks,
                            ArrayList<ToppingStock> toppingStocks,
                            ArrayList<DrinkStock> drinkStocks) {
        this.mieStocks = mieStocks;
        this.drinkStocks = drinkStocks;
        this.toppingStocks = toppingStocks;
    }

    public ArrayList<MieStock> getMieStocks() {
        return mieStocks;
    }

    public ArrayList<DrinkStock> getDrinkStocks() {
        return drinkStocks;
    }

    public ArrayList<ToppingStock> getToppingStocks() {
        return toppingStocks;
    }

    public ArrayList<MieStock> getOneOfEachBrand() {
        ArrayList brands = new ArrayList<>();
        ArrayList output = new ArrayList();
        for (MieStock m : mieStocks) {
            if (!brands.contains(m.brand)) {
                brands.add(m.brand);
                output.add(m);
            }
        }
        return output;
    }

    public ArrayList<MieStock> getOfBrand(String brand) {
        ArrayList output = new ArrayList();
        for (MieStock m : mieStocks) {
            if (m.brand.equals(brand)) {
                output.add(m);
            }
        }
        return output;
    }
}