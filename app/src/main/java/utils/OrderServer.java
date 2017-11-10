package utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bintang5.supremie.R;
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
    private Context c;

    /**
     * Constructor.
     */
    private OrderServer(Context c) {
        super(c);
        this.c = c;
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
                    clPrintObject.setFormat(CLPrintEnum.NORMAL);
                    clPrintObject.setAlign(CLPrintAlignEnum.LEFT);
                    freeText.add(clPrintObject);

                    clPrintObject = new CLPrintObject();
                    Bitmap logoIcon = BitmapFactory.decodeResource(c.getResources(), c.getResources().getIdentifier("supremie_logo","drawable",c.getPackageName()));
                    clPrintObject.setBitmap(logoIcon);
                    clPrintObject.setFormat(CLPrintEnum.SMALL_LOGO);
                    clPrintObject.setAlign(CLPrintAlignEnum.CENTER);
                    freeText.add(clPrintObject);

                    clPrintObject = new CLPrintObject();
                    clPrintObject.setFreeText("Nomor Anda: " + r.getId());
                    clPrintObject.setFormat(CLPrintEnum.TITLE);
                    clPrintObject.setAlign(CLPrintAlignEnum.LEFT);
                    freeText.add(clPrintObject);
                    if (order.mies.size() > 0) {// && !order.mies.get(0).brand.equals("NO")) {
                        for (Mie m : order.mies) {
                            if (!m.brand.equals("NO")) {
                                clPrintObject = new CLPrintObject();
                                clPrintObject.setFormat(CLPrintEnum.NORMAL);
                                clPrintObject.setAlign(CLPrintAlignEnum.LEFT);
                                clPrintObject.setFreeText(m.quantityMie + " " +
                                        m.brand + " " +m.flavour);
                                freeText.add(clPrintObject);
                                clPrintObject = new CLPrintObject();
                                clPrintObject.setFormat(CLPrintEnum.NORMAL);
                                clPrintObject.setAlign(CLPrintAlignEnum.RIGHT);
                                clPrintObject.setFreeText("Rp. "+m.price);
                                freeText.add(clPrintObject);
                                if (!m.brand.equals("Roti") || !m.brand.equals("Pisang")) {
                                    //print pedas level if not Roti or Pisang
                                    clPrintObject = new CLPrintObject();
                                    clPrintObject.setFormat(CLPrintEnum.NORMAL);
                                    clPrintObject.setAlign(CLPrintAlignEnum.LEFT);
                                    clPrintObject.setFreeText("Level Pedas: " + m.extraChili);
                                    freeText.add(clPrintObject);
                                    clPrintObject = new CLPrintObject();
                                    clPrintObject.setFormat(CLPrintEnum.NORMAL);
                                    clPrintObject.setAlign(CLPrintAlignEnum.RIGHT);
                                    clPrintObject.setFreeText("Rp. " + State.getInstance().getPedasPrice(m.extraChili));
                                    freeText.add(clPrintObject);
                                }
                            }
                            //print toppings
                            for (Topping t : m.toppings) {
                                clPrintObject = new CLPrintObject();
                                clPrintObject.setFormat(CLPrintEnum.NORMAL);
                                clPrintObject.setAlign(CLPrintAlignEnum.LEFT);
                                clPrintObject.setFreeText(t.quantity + " " + t.name);
                                freeText.add(clPrintObject);
                                clPrintObject = new CLPrintObject();
                                clPrintObject.setFormat(CLPrintEnum.NORMAL);
                                clPrintObject.setAlign(CLPrintAlignEnum.RIGHT);
                                clPrintObject.setFreeText("Rp. "+t.quantity * t.price);
                                freeText.add(clPrintObject);
                            }
                        }
                    } else if (order.mies.size() == 0) { //drinks only
                        //do nothing
                    }
                    //add new line inbetween toppings and drinks
                    clPrintObject.setFreeText(clPrintObject.getFreeText()+"\n");
                    if (order.drinks != null) {
                        for (Drink d : order.drinks) {
                            clPrintObject = new CLPrintObject();
                            clPrintObject.setFormat(CLPrintEnum.NORMAL);
                            clPrintObject.setAlign(CLPrintAlignEnum.LEFT);
                            clPrintObject.setFreeText(d.quantity + " " + d.brand);
                            freeText.add(clPrintObject);
                            clPrintObject = new CLPrintObject();
                            clPrintObject.setFormat(CLPrintEnum.NORMAL);
                            clPrintObject.setAlign(CLPrintAlignEnum.RIGHT);
                            clPrintObject.setFreeText("Rp. "+d.quantity * d.price);
                            freeText.add(clPrintObject);
                        }
                    }

                    clPrintObject = new CLPrintObject();
                    clPrintObject.setFormat(CLPrintEnum.BOLD);
                    clPrintObject.setAlign(CLPrintAlignEnum.LEFT);
                    clPrintObject.setFreeText("\nSubtotal:");
                    freeText.add(clPrintObject);
                    clPrintObject = new CLPrintObject();
                    clPrintObject.setFormat(CLPrintEnum.NORMAL);
                    clPrintObject.setAlign(CLPrintAlignEnum.RIGHT);
                    clPrintObject.setFreeText(State.getInstance().subTotalString);
                    freeText.add(clPrintObject);
                    clPrintObject = new CLPrintObject();
                    clPrintObject.setFormat(CLPrintEnum.BOLD);
                    clPrintObject.setAlign(CLPrintAlignEnum.LEFT);
                    clPrintObject.setFreeText("\n\nTax & Service:");
                    freeText.add(clPrintObject);
                    clPrintObject = new CLPrintObject();
                    clPrintObject.setFormat(CLPrintEnum.NORMAL);
                    clPrintObject.setAlign(CLPrintAlignEnum.RIGHT);
                    clPrintObject.setFreeText("Rp. "+State.getInstance().getTaxServiceString());
                    freeText.add(clPrintObject);
                    clPrintObject = new CLPrintObject();
                    clPrintObject.setFormat(CLPrintEnum.BOLD);
                    clPrintObject.setAlign(CLPrintAlignEnum.LEFT);
                    String totalPrice = State.getInstance().addDot(order.totalPrice.toString());
                    clPrintObject.setFreeText("\nTotal:\n");
                    freeText.add(clPrintObject);
                    clPrintObject = new CLPrintObject();
                    clPrintObject.setFormat(CLPrintEnum.TITLE);
                    clPrintObject.setAlign(CLPrintAlignEnum.CENTER);
                    clPrintObject.setFreeText("Rp. "+totalPrice);
                    freeText.add(clPrintObject);
                    cashlezPayment.doPrintFreeText(freeText);
                    cashlezPayment.unregisterReceiver();
                } else {
                    Log.v("UH OH!", "Something bad happened");
                }
            }
            @Override
            public void onFailure(Call<POSTResponseOrder> call, Throwable t) {
                //Go back to Order Summary if POST fails
                Log.v("UH OH!", "Something went wrong");
                Intent i = new Intent(callingActivity, OrderSummary.class);
                callingActivity.finish();
                callingActivity.startActivity(i);
            }
        });
    }

}
