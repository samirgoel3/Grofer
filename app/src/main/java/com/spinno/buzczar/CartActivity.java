package com.spinno.buzczar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class CartActivity extends Activity {

    LinearLayout lineaarlayoutUnderscrollViewe ;
    TextView totalsavingtxt , grosstotaltxt , totalitemtxt , proceedtopayment , shopmorebtn;

    View  layoutWhenDataIsExsist  , layoutwhencartisempty;

    Button startshopping   ;

    ImageView backbutton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ActivityIdentifire.cartactivity = 1 ;

        layoutWhenDataIsExsist = findViewById(R.id.when_there_is_data_in_cart);
        layoutwhencartisempty = findViewById(R.id.when_cart_is_empty);
        lineaarlayoutUnderscrollViewe = (LinearLayout)findViewById(R.id.linearlayoutinpannelblablablabla);
        totalsavingtxt = (TextView) findViewById(R.id.total_saving_in_cart);
        grosstotaltxt = (TextView) findViewById(R.id.gross_total_text_in_cart);
        totalitemtxt = (TextView) findViewById(R.id.total_no_of_item_in_cart);
        startshopping = (Button) findViewById(R.id.start_shopping_when_cart_is_empty);
        proceedtopayment = (TextView) findViewById(R.id.proceed_to_payment_in_main_activity);
        backbutton = (ImageView) findViewById(R.id.back_button_in_cart);
        shopmorebtn = (TextView) findViewById(R.id.shopmorebutton_in_ahenthereis_data_in_cart);


        layoutWhenDataIsExsist.setVisibility(View.GONE);
        layoutwhencartisempty.setVisibility(View.GONE);

          loadDataInPannel();


        startshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        proceedtopayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent in = new Intent(CartActivity.this , AddressActivity.class);
                   in.putExtra("fromwhichactivity","cartactivity");
                   startActivity(in);
            }
        });


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                ActivityIdentifire.cartactivity = 0 ;
            }
        });

        shopmorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void loadDataInPannel() {

        lineaarlayoutUnderscrollViewe.removeAllViews();
        List<Producttable> templist = Producttable.listAll(Producttable.class);

        if(templist.size()>0){
            layoutWhenDataIsExsist.setVisibility(View.VISIBLE);
            layoutwhencartisempty.setVisibility(View.GONE);
        }else if(templist.size() <=0){
            layoutwhencartisempty.setVisibility(View.VISIBLE);
            layoutWhenDataIsExsist.setVisibility(View.GONE);
        }

        lineaarlayoutUnderscrollViewe.removeAllViews();
        for (int i = 0; i < templist.size(); i++) {

            lineaarlayoutUnderscrollViewe.addView(new ViewAdderToPanel().foraddigview(CartActivity.this, R.layout.item_for_cart_layout,
                    templist.get(i).getProductName(),
                    templist.get(i).getProductPrice(),
                    templist.get(i).getProductimage(),
                    templist.get(i).getNumberOfUnits(),
                    templist.get(i).getProductId(),
                    templist.get(i).getSalePrice(),
                    templist.get(i).getDiscountPrice(),
                    templist.get(i).getPerUnit(),
                    templist.get(i).getUnitType()));
        }


        show_amount_on_cart();
    }

    public void show_amount_on_cart(){

        int temp1 , temp2 , temp3 , temp4 ,multiplier,multiplier2 , result_to_show_on_cart = 0 , result_to_show_on_cart_total_discount = 0 ;
        List<Producttable> templist = Producttable.listAll(Producttable.class);

        for(int i = 0 ; i<templist.size() ; i++){


            temp1 = Integer.parseInt(templist.get(i).getSalePrice());
            temp3 = Integer.parseInt(templist.get(i).getDiscountPrice());
            temp2 = templist.get(i).getNumberOfUnits();



            multiplier = temp1 * temp2 ;
            multiplier2 = temp3 * temp2 ;
            result_to_show_on_cart = result_to_show_on_cart +multiplier ;
            result_to_show_on_cart_total_discount = result_to_show_on_cart_total_discount + multiplier2  ;
        }
        grosstotaltxt.setText(String.valueOf(result_to_show_on_cart));
        totalitemtxt.setText(""+templist.size());
        totalsavingtxt.setText(String.valueOf(result_to_show_on_cart_total_discount));

    }

    public  void  makecartempty(){
        lineaarlayoutUnderscrollViewe.removeAllViews();
    }
}
