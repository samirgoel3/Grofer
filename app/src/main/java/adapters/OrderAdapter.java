package adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spinno.buzczar.CANCEL_ORDER_RESPONSE;
import com.spinno.buzczar.R;

import java.util.ArrayList;
import java.util.List;

import apiurls.URLAPI;
import cn.pedant.SweetAlert.SweetAlertDialog;
import imageloading.ImageLoader;
import pojo.PRODUCT_RECEIVED_IN_PREVOIUS_ORDERS;
import singleton.VolleySingleton;

/**
 * Created by samir on 20/03/15.
 */
public class OrderAdapter extends BaseAdapter {


    Context con ;
    List<String> order_status  = new ArrayList<>();
    List<String> timing_of_order = new ArrayList<>();
    List<String> date_of_order = new ArrayList<>();
    List <String> order_id = new ArrayList<>();
    List <String> status = new ArrayList<>();
    public ImageLoader imageLoader;
    public String TAG_orderscacel = "we are executing this link for canceling order";

    List<List<PRODUCT_RECEIVED_IN_PREVOIUS_ORDERS>> prepo = new ArrayList<>();

    List<PRODUCT_RECEIVED_IN_PREVOIUS_ORDERS> poto = new ArrayList<>();

    StringRequest sr ;
    RequestQueue queue ;
    CANCEL_ORDER_RESPONSE cor    ;
    ProgressDialog progressDialog ;




    public OrderAdapter(Context con, List<String> order_status, List<String> timing_of_order, List<String> date_of_order, List<String> order_id, List<List<PRODUCT_RECEIVED_IN_PREVOIUS_ORDERS>> prepo ,  List <String> status){
        this.con = con ;
        this.order_status = order_status;
        this.timing_of_order = timing_of_order;
        this.date_of_order = date_of_order;
        this.order_id = order_id ;
        this.prepo = prepo ;
        this.status = status ;
        queue = VolleySingleton.getInstance(con).getRequestQueue();
        imageLoader=new ImageLoader(con.getApplicationContext());

        cor = new CANCEL_ORDER_RESPONSE();
    }


    @Override
    public int getCount() {
        return order_status.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View row    ;


        row = inflater.inflate(R.layout.item_for_order_tracking, viewGroup, false);

        TextView time , date , order_id_text  , grosstotal ;
        ImageView pending_red , processing_red , received_red ;
        TextView  cancelOrderbtn;

        LinearLayout previosdtaillayout = (LinearLayout) row.findViewById(R.id.previousludoneorders);
        LinearLayout mainlayout = (LinearLayout) row.findViewById(R.id.tracking_layout);
        View cancellayout =  row.findViewById(R.id.cancel_order_layout);
        View statuslayout =  row.findViewById(R.id.status_layout_for_item_in_order_tracking);
        order_id_text = (TextView) row.findViewById(R.id.order_id_in_order_fragment);
        time = (TextView) row.findViewById(R.id.time_in_item_for_order_fragmenr);
        date = (TextView) row.findViewById(R.id.date_in_item_for_order_fragmenr);
        pending_red = (ImageView) row.findViewById(R.id.pending_red_order_fragment);
        processing_red = (ImageView) row.findViewById(R.id.processing_red_order_fragment);
        received_red = (ImageView) row.findViewById(R.id.delivered_red_order_fragment);
        grosstotal = (TextView) row.findViewById(R.id.gross_price_in_item_for_order_tracking);
        cancelOrderbtn = (TextView) row.findViewById(R.id.cancel_order);
        View cancelunderline = row.findViewById(R.id.cancel_underline);


        time.setText(timing_of_order.get(i));
        date.setText(date_of_order.get(i));
        order_id_text.setText(order_id.get(i));


        String s= order_status.get(i);

       if(status.get(i).equals("0")){
           cancelOrderbtn.setVisibility(View.GONE);
           cancelunderline.setVisibility(View.GONE);
          // cancellayout.setVisibility(View.VISIBLE);
           statuslayout.setVisibility(View.GONE);
       }else if(status.get(i).equals("1")){
  //         statuslayout.setVisibility(View.VISIBLE);
  //         cancelOrderbtn.setVisibility(View.VISIBLE);
  //         cancelunderline.setVisibility(View.VISIBLE);
           cancellayout.setVisibility(View.GONE);
       }

        if(s.equals("Pending")){
           pending_red.setVisibility(View.GONE);
        }else if(s.equals("Processing")){
           pending_red.setVisibility(View.GONE);
           processing_red.setVisibility(View.GONE);
        }else  if (s.equals("Completed")){
            pending_red.setVisibility(View.GONE);
            processing_red.setVisibility(View.GONE);
            received_red.setVisibility(View.GONE);
            cancelOrderbtn.setVisibility(View.GONE);
            cancelunderline.setVisibility(View.GONE);
            cancellayout.setVisibility(View.GONE);

        }


        double grossdouble = 0  ;

        poto = prepo.get(i);
        for(int j = 0 ; j< poto.size(); j++){
            previosdtaillayout.addView( ordersview(R.layout.item_for_previous_details , poto.get(j).product_name ,poto.get(j).product_image , poto.get(j).no_of_unit_booked ));
                  grossdouble = grossdouble + ((Double.parseDouble(poto.get(j).price)*(Double.parseDouble(poto.get(j).no_of_unit_booked))));
                   }
            grosstotal.setText(""+grossdouble);

        cancelOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertForCancelOrder(order_id.get(i) , i);


            }


        });


        return row;
    }



    private void showAlertForCancelOrder(final String orderid , final int position) {
        new SweetAlertDialog(con, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you Sure ")
                .setContentText("you want to cancel your order")
                .setConfirmText("Yes")
                .setCancelText("No")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        cancelorder(orderid , position);
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }
    private void cancelorder(String orderid , final int position) {
        Log.e(TAG_orderscacel , ""+URLAPI.cancelOrder.concat(orderid));

        sr = new StringRequest(Request.Method.GET, URLAPI.cancelOrder.concat(orderid), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                cor = gson.fromJson(response , CANCEL_ORDER_RESPONSE.class);
                Log.e("Cancel Response" , ""+cor.response);
                if(cor.response.equals("1")){
                    status.set(position , "0");
                    notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Cancel Response" , ""+error);
            }
        });
        sr.setRetryPolicy(new DefaultRetryPolicy(2500,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr);
        progressDialog = new ProgressDialog(con);
        progressDialog.setMessage("wait we are canceling your order...");
        progressDialog.show();
    }
    private View ordersview(int layout_name , String s  , String imageurl , String noofunitybooked ) {

        LayoutInflater layoutInflater = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View addView = layoutInflater.inflate(layout_name, null);
        final TextView pname = (TextView) addView.findViewById(R.id.itemname);
        final  TextView pnoofunits = (TextView) addView.findViewById(R.id.noofunitpreviously);
        final TextView punittype = (TextView) addView.findViewById(R.id.previouslyunittype);
        final ImageView pimage = (ImageView) addView.findViewById(R.id.previousorderimage);



        imageLoader.DisplayImage(imageurl ,pimage);
            pname.setText(s);
            pnoofunits.setText(noofunitybooked);
            punittype.setText("Units");

        return addView ;
    }
}
