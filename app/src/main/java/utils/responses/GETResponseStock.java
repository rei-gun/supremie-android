package utils.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import domain.DrinkStock;
import domain.MieStock;
import domain.ToppingStock;

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

    public ArrayList<MieStock> getMieStocks() {
        return mieStocks;
    }

    public ArrayList<DrinkStock> getDrinkStocks() {
        return drinkStocks;
    }

    public ArrayList<ToppingStock> getToppingStocks() {
        return toppingStocks;
    }

    public GETResponseStock(ArrayList<MieStock> mieStocks,
                            ArrayList<ToppingStock> toppingStocks,
                            ArrayList<DrinkStock> drinkStocks) {
        this.mieStocks = mieStocks;
        this.drinkStocks = drinkStocks;
        this.toppingStocks = toppingStocks;
    }
}