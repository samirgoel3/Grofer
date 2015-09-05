package com.spinno.buzczar;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import imageloading.ImageLoader;

/**
 * Created by samir on 18/02/15.
 */
public  class ViewAdderToPanel {

    ImageLoader iml ;

    Context con ;

    Producttable pt ;
    List<Producttable> data_in_list ;



    public View foraddigview(final Context con , int layout_name , final String productname , final String productprice , String imageurl , int units , final String productid , String saleprice , String discountprice   , String perunit , String unittype) {

        iml=new ImageLoader(con.getApplicationContext());

        this.con = con;
        LayoutInflater layoutInflater = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(layout_name, null);
        final TextView produtc_name , Price_of_product  , resulted_amount , pricethat_in_multipied , hidden_product_id  , salepricetext , hiddendiscountpricetext , visiblediscountedpricetext , perunittext , unittypetext ;
        ImageView Product_image  ;
        final EditText no_of_units ;
        ImageButton   upbutton , downbutton;


           no_of_units = (EditText) addView.findViewById(R.id.no_of_unit_getting_in_item_for_cartlayut);
                           no_of_units.setInputType(InputType.TYPE_NULL);

           produtc_name = (TextView) addView.findViewById(R.id.productnamein_item_for_cart_layout);
           Price_of_product = (TextView) addView.findViewById(R.id.price_in_item_for_cart_layout);
           resulted_amount = (TextView) addView.findViewById(R.id.resultte_amount_of_product_in_cart_layout);
           Product_image = (ImageView) addView.findViewById(R.id.imageVieweee_in_item_for_cart_layout);
           pricethat_in_multipied = (TextView) addView.findViewById(R.id.price_that_is_multiplied_unit_cart_layout);
           upbutton = (ImageButton) addView.findViewById(R.id.up_button_item_for_cartpannel);
           downbutton = (ImageButton) addView.findViewById(R.id.down_button_item_for_cartpannel);
           hidden_product_id = (TextView) addView.findViewById(R.id.productid_in_item_for_cart);
           salepricetext = (TextView) addView.findViewById(R.id.saleprice_in_item_for_cart_layout);
           hiddendiscountpricetext = (TextView) addView.findViewById(R.id.hidden_discount_price_in_item_for_cart);
           perunittext = (TextView) addView.findViewById(R.id.perunit_in_cart_pannel);
           unittypetext = (TextView) addView.findViewById(R.id.unit_type_cart_pannel);
           visiblediscountedpricetext = (TextView) addView.findViewById(R.id.visible_discounted_price_in_cart_layout);

        double productpricing_in_double  =  Integer.parseInt(saleprice);
        double result_after_calculation =   new PriceCalculatorClass().calculateprice(productpricing_in_double , units);

        //////////Discount calculation  //////////////


        double discountproductpricing_in_double  =  Integer.parseInt(discountprice);
        double discountresult_after_calculation =   new PriceCalculatorClass().calculateprice(discountproductpricing_in_double , units);




        produtc_name.setText(productname);
        Price_of_product.setText(productprice);
        salepricetext.setText(saleprice);
        hiddendiscountpricetext.setText(discountprice);
        resulted_amount.setText(String.valueOf(result_after_calculation));
        no_of_units.setText(String.valueOf(units));
        pricethat_in_multipied.setText(saleprice);
        hidden_product_id.setText(productid);
        perunittext.setText(perunit);
        unittypetext.setText(unittype);
        visiblediscountedpricetext.setText(String.valueOf(discountresult_after_calculation));
        iml.DisplayImage(imageurl, Product_image);


        upbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int noofunits = Integer.parseInt(no_of_units.getText().toString());

                data_in_list = Producttable.find(Producttable.class, "Product_Id = ?", productid);
                long id_of_row = data_in_list.get(0).getId();
                pt = Producttable.findById(Producttable.class, id_of_row);
                pt.NumberOfUnits = noofunits + 1 ;
                pt.save();
                no_of_units.setText("" + (noofunits + 1));
                int units = Integer.parseInt(no_of_units.getText().toString());

                int discountpribe  = Integer.parseInt(hiddendiscountpricetext.getText().toString());
                int sellingprice = Integer.parseInt(salepricetext.getText().toString());

                visiblediscountedpricetext.setText(""+(units * discountpribe));
                resulted_amount.setText(""+(units * sellingprice));

                ((CartActivity)con).loadDataInPannel();

            }
        });





        downbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int noofunits = Integer.parseInt(no_of_units.getText().toString());

                if(noofunits > 1){
                    data_in_list = Producttable.find(Producttable.class, "Product_Id = ?", productid);
                    long id_of_row = data_in_list.get(0).getId();
                    pt = Producttable.findById(Producttable.class, id_of_row);
                    pt.NumberOfUnits = noofunits - 1 ;
                    pt.save();
                    no_of_units.setText("" + (noofunits - 1));
                    int units = Integer.parseInt(no_of_units.getText().toString());

                    int discountpribe  = Integer.parseInt(hiddendiscountpricetext.getText().toString());
                    int sellingprice = Integer.parseInt(salepricetext.getText().toString());

                    visiblediscountedpricetext.setText(""+(units * discountpribe));
                    resulted_amount.setText(""+(units * sellingprice));

                    ((CartActivity)con).loadDataInPannel();


                    }else if (noofunits == 1) {

                    new SweetAlertDialog(con, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("You want to remove this item from cart")
                            .setConfirmText("Yes,remove it!")
                            .setCancelText("No ")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {



                                    data_in_list = Producttable.find(Producttable.class, "Product_Id = ?", productid);
                                    long id_of_row = data_in_list.get(0).getId();
                                    pt = Producttable.findById(Producttable.class, id_of_row);
                                    pt.delete();


                                    ((CartActivity)con).loadDataInPannel();
                                    sDialog.dismiss();
                                    new SweetAlertDialog(con  , SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Item Removed From Cart")
                                            .setConfirmText("Ok")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                }
                                            }).show();
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
            }
        });

        return  addView ;
    }
}

