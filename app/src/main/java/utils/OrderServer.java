package utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bintang5.supremie.activity.ChooseDiningMethod;
import com.bintang5.supremie.activity.OrderSummary;
import com.bintang5.supremie.activity.State;
import com.cashlez.android.sdk.model.CLPrintObject;
import com.cashlez.android.sdk.service.CLPrintAlignEnum;
import com.cashlez.android.sdk.service.CLPrintEnum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Drink;
import model.Mie;
import model.Order;
import model.Topping;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.responses.POSTResponseOrder;

/**
 * Exposes {@link OrderService}.
 */
public class OrderServer extends Server {
    /**
     * The singleton instance.
     */
    private static OrderServer instance;

    /**
     * The service api conf.
     */
    private OrderService service;

    /**
     * Constructor.
     */
    private OrderServer(Context c) {
        super(c);
        service = createService(OrderService.class);
    }

    /**
     * Obtains singleton instance.
     *
     * @return The singleton instance.
     */
    public static OrderServer getInstance(Context c) {
        if (instance == null)
            instance = new OrderServer(c);
        return instance;
    }

    /**
     * Create a new permintaan.
     *
     * @param order The Order model to be created.
     */
    public void createOrder(final CashlezPayment cashlezPayment, final Activity callingActivity, final Order order) {
        service.createOrder(order).enqueue(new Callback<POSTResponseOrder>() {
            @Override
            public void onResponse(Call<POSTResponseOrder> call, Response<POSTResponseOrder> response) {
                POSTResponseOrder r = response.body();
                //TODO print the id to Cashlez's printer here
                if (r != null) {
                    ArrayList<CLPrintObject> freeText = new ArrayList<>();
                    CLPrintObject clPrintObject = new CLPrintObject();
                    String currentTimeString = new SimpleDateFormat("dd/MM/yyy").format(new Date());
                    clPrintObject.setFreeText(currentTimeString+"\n\n");
                    clPrintObject.setFreeText(clPrintObject.getFreeText()+"Nomor Anda: " + r.getId()+"\n");
                    clPrintObject.setFormat(CLPrintEnum.TITLE);
                    clPrintObject.setAlign(CLPrintAlignEnum.LEFT);
                    if (order.mies.size() > 0 && !order.mies.get(0).brand.equals("NO")) {
                        for (Mie m : order.mies) {
                            clPrintObject.setFreeText(clPrintObject.getFreeText() + "\n" + m.quantityMie + " " +
                                    m.brand + " " +
                                    m.flavour + "\nRp. " +
                                    m.price * State.getInstance().getQuantityMie());
                            //print pedas level
                            clPrintObject.setFreeText(clPrintObject.getFreeText() + "\nLevel Pedas: " + State.getInstance().getPedasLevel() +
                                    "\nRp. " + State.getInstance().getPedasPrice(State.getInstance().getPedasLevel()));
                            //print toppings
                            for (Topping t : m.toppings) {
                                clPrintObject.setFreeText(clPrintObject.getFreeText() + "\n" + t.quantity + " " + t.name + "\nRp. " +
                                        t.quantity * t.price);
                            }
                        }
                    } else if (order.mies.size() == 0) { //drinks only
                        //do nothing
                    } else {
                        Mie m = order.mies.get(0);
                        for (Topping t : m.toppings) {
                            clPrintObject.setFreeText(clPrintObject.getFreeText() + "\n" + t.quantity + " " + t.name + "\nRp. " +
                                    t.quantity * t.price);
                        }
                    }
                    //add new line inbetween toppings and drinks
                    clPrintObject.setFreeText(clPrintObject.getFreeText()+"\n");
                    if (order.drinks != null) {
                        for (Drink d : order.drinks) {
                            clPrintObject.setFreeText(clPrintObject.getFreeText() + "\n" + d.quantity + " " + d.brand + "\nRp. " +
                                    d.quantity * d.price);
                        }
                    }
                    clPrintObject.setFreeText(clPrintObject.getFreeText()+"\n\nTax&Service:\n"+State.getInstance().getTaxServiceString());
                    String totalPrice = State.getInstance().addDot(order.totalPrice.toString());
                    clPrintObject.setFreeText(clPrintObject.getFreeText()+"\n\nJumlah: \n\nRp. "+totalPrice);
                    freeText.add(clPrintObject);
                    cashlezPayment.doPrintFreeText(freeText);
                    cashlezPayment.unregisterReceiver();
                    State.getInstance().clear();
                    Intent intent = new Intent(callingActivity, ChooseDiningMethod.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    callingActivity.startActivity(intent);
                } else {
                    Log.v("OH SHIT!", "Something bad happened");
                }
            }
            @Override
            public void onFailure(Call<POSTResponseOrder> call, Throwable t) {
                Log.v("OH SHIT!", "Something went wrong");
                Intent i = new Intent(callingActivity, OrderSummary.class);
                callingActivity.finish();
                callingActivity.startActivity(i);
            }
        });

    }

}
