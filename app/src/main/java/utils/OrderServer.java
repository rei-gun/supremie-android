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
                    CLPrintObject date = new CLPrintObject();
                    String currentTimeString = new SimpleDateFormat("dd/MM/yyy").format(new Date());
                    date.setFreeText(currentTimeString+"\n\n");
                    date.setFormat(CLPrintEnum.TITLE);
                    date.setAlign(CLPrintAlignEnum.LEFT);
                    freeText.add(date);

                    CLPrintObject logo = new CLPrintObject();
                    Bitmap logoIcon = BitmapFactory.decodeResource(c.getResources(), c.getResources().getIdentifier("supremie_logo","drawable",c.getPackageName()));
                    logo.setBitmap(logoIcon);
                    logo.setFormat(CLPrintEnum.SMALL_LOGO);
                    logo.setAlign(CLPrintAlignEnum.CENTER);
                    freeText.add(logo);
//                    clPrintObject.setFreeText(clPrintObject.getFreeText()+"Nomor Anda: " + r.getId()+"\n");
//                    clPrintObject.setFormat(CLPrintEnum.TITLE);
//                    clPrintObject.setAlign(CLPrintAlignEnum.LEFT);
//                    if (order.mies.size() > 0) {// && !order.mies.get(0).brand.equals("NO")) {
//                        for (Mie m : order.mies) {
//                            if (!m.brand.equals("NO")) {
//                                clPrintObject.setFreeText(clPrintObject.getFreeText() + "\n" + m.quantityMie + " " +
//                                        m.brand + " " +
//                                        m.flavour + "\nRp. " +
//                                        m.price);
//                                if (!m.brand.equals("Roti") || !m.brand.equals("Pisang")) {
//                                    //print pedas level
//                                    clPrintObject.setFreeText(clPrintObject.getFreeText() + "\nLevel Pedas: " + m.extraChili +
//                                            "\nRp. " + State.getInstance().getPedasPrice(m.extraChili));
//                                }
//                            }
//                            //print toppings
//                            for (Topping t : m.toppings) {
//                                clPrintObject.setFreeText(clPrintObject.getFreeText() + "\n" + t.quantity + " " + t.name + "\nRp. " +
//                                        t.quantity * t.price);
//                            }
//                        }
//                    } else if (order.mies.size() == 0) { //drinks only
//                        //do nothing
//                    } else {
//                        Mie m = order.mies.get(0);
//                        for (Topping t : m.toppings) {
//                            clPrintObject.setFreeText(clPrintObject.getFreeText() + "\n" + t.quantity + " " + t.name + "\nRp. " +
//                                    t.quantity * t.price);
//                        }
//                    }
//                    //add new line inbetween toppings and drinks
//                    clPrintObject.setFreeText(clPrintObject.getFreeText()+"\n");
//                    if (order.drinks != null) {
//                        for (Drink d : order.drinks) {
//                            clPrintObject.setFreeText(clPrintObject.getFreeText() + "\n" + d.quantity + " " + d.brand + "\nRp. " +
//                                    d.quantity * d.price);
//                        }
//                    }
//                    clPrintObject.setFreeText(clPrintObject.getFreeText()+"\n\nTax&Service:\n"+State.getInstance().getTaxServiceString());
//                    String totalPrice = State.getInstance().addDot(order.totalPrice.toString());
//                    clPrintObject.setFreeText(clPrintObject.getFreeText()+"\n\nJumlah: \n\nRp. "+totalPrice);
//                    freeText.add(clPrintObject);
                    cashlezPayment.doPrintFreeText(freeText);
//                    cashlezPayment.unregisterReceiver();
                    //TODO: Place the below inside a callback from the printer when it finishes.
//                    c.getResources().getString(R.str)
//                    State.getInstance().clear();
//                    Intent intent = new Intent(callingActivity, ChooseDiningMethod.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    callingActivity.startActivity(intent);
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
