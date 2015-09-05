package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spinno.buzczar.MainActivity;
import com.spinno.buzczar.Producttable;
import com.spinno.buzczar.R;

import java.util.ArrayList;
import java.util.List;

import database.IDChecker;
import imageloading.ImageLoader;

/**
 * Created by samir on 08/05/15.
 */
public class FruitListAdapter extends BaseAdapter {
    Context con ;

    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> saleprice = new ArrayList<String>();
    ArrayList<String> unitsper = new ArrayList<String>();
    ArrayList<String> unittype = new ArrayList<String>();
    ArrayList<String> imageurls = new ArrayList<String>();
    ArrayList<String> discountedprice = new ArrayList<String>();
    ArrayList<String> pdescription = new ArrayList<String>();
    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<Integer> noofunits = new ArrayList<Integer>();




    public ImageLoader imageLoader;



    List<Producttable> data_in_list ;
    Producttable pt ;

    int tryr = 0 ;




    public  FruitListAdapter(ArrayList<String> name,
                             ArrayList<String> price,
                             ArrayList<String> saleprice,
                             ArrayList<String> unitsper ,
                             ArrayList<String> unittype ,
                             ArrayList<String> imageurls ,
                             ArrayList<String> discountedprice ,
                             ArrayList<String> pdescription,
                             ArrayList<String> ids,
                             ArrayList<Integer> noofunits,
                             Context con ){

             this.con = con ;
             this.name = name;
             this.price = price ;
             this.saleprice = saleprice ;
             this.unitsper = unitsper ;
             this.unittype = unittype  ;
             this.imageurls = imageurls ;
             this.discountedprice = discountedprice ;
             this.pdescription = pdescription ;
             this.ids = ids ;
             this.noofunits = noofunits ;

        imageLoader=new ImageLoader(con.getApplicationContext());

    }


    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolderItem viewHolder;



        if(convertView==null){

            // inflate the layout
            LayoutInflater inflater = (LayoutInflater) con.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(R.layout.items_for_list_xml, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.productnametxt = (TextView) convertView.findViewById(R.id.nameofproduct);
            viewHolder.pricetxt = (TextView) convertView.findViewById(R.id.priceofproduct);
            viewHolder.salepricetxt = (TextView) convertView.findViewById(R.id.saleprice_of_product_in_item_for_list);
            viewHolder.perunittxt = (TextView) convertView.findViewById(R.id.peruit_of_product);
            viewHolder.unittyprtxt = (TextView) convertView.findViewById(R.id.unittype_of_product);
            viewHolder.discountedpricetxt = (TextView) convertView.findViewById(R.id.discounted_price_hidden_in_item_for_list);
            viewHolder.productdescriptiontxt = (TextView) convertView.findViewById(R.id.product_description_in_item_for_list);
            viewHolder.imageurltxt = (TextView) convertView.findViewById(R.id.produtUrl_in_item_for_list);
            viewHolder.productidtxt = (TextView) convertView.findViewById(R.id.productidiniemlistofproduct);
            viewHolder.valueedt = (TextView) convertView.findViewById(R.id.valueeditinitemfirlist);

            viewHolder.plusbtn = (ImageView) convertView.findViewById(R.id.upinitemforlist);
            viewHolder.minusbtn = (ImageView) convertView.findViewById(R.id.downinitemforlist);

            viewHolder.productimage = (ImageView) convertView.findViewById(R.id.groceryimageofproduct);

            // store the holder with the view.
            convertView.setTag(viewHolder);

        }else{
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) convertView.getTag();
        }



            viewHolder.productnametxt.setText(name.get(position));
            viewHolder.pricetxt.setText(price.get(position));
            viewHolder.salepricetxt.setText(saleprice.get(position));
            viewHolder.perunittxt.setText(unitsper.get(position));
            viewHolder.unittyprtxt.setText(unittype.get(position));
            viewHolder.discountedpricetxt.setText(discountedprice.get(position));
            viewHolder.productdescriptiontxt.setText(pdescription.get(position));
            viewHolder.imageurltxt.setText(imageurls.get(position));
            viewHolder.productidtxt.setText(ids.get(position));
            viewHolder.valueedt.setText(String.valueOf(noofunits.get(position)));
        imageLoader.DisplayImage(imageurls.get(position) ,viewHolder.productimage);


        viewHolder.plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int test = Integer.parseInt(viewHolder.valueedt.getText().toString());

                if (test >= 0) {
                    if(test==0){
                       tryr = 1 ;
                    }
                    viewHolder.valueedt.setText("" + (test + 1));
                    noofunits.set(position ,test+1);



                    addtocart(test +1,
                            viewHolder.productnametxt.getText().toString(),
                            viewHolder.pricetxt.getText().toString(),
                            viewHolder.productdescriptiontxt.getText().toString(),
                            viewHolder.productidtxt.getText().toString(),
                            viewHolder.imageurltxt.getText().toString(),
                            viewHolder.salepricetxt.getText().toString(),
                            viewHolder.discountedpricetxt.getText().toString(),
                            viewHolder.perunittxt.getText().toString(),
                            viewHolder.unittyprtxt.getText().toString());
                }
            }
        });



        viewHolder.minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int test = Integer.parseInt(viewHolder.valueedt.getText().toString());
                if(test >0){

                    if(test == 1 ){
                        viewHolder.valueedt.setText("0");
                        noofunits.set(position ,0);
                    }
                    viewHolder.valueedt.setText(""+(test-1));
                    noofunits.set(position ,test-1);

   ////////////////////////////////////////////   working of databse  pannel adding     ///////////////////////////

                    if(Integer.parseInt(viewHolder.valueedt.getText().toString()) == 0){
                        data_in_list = Producttable.find(Producttable.class, "Product_Id = ?", viewHolder.productidtxt.getText().toString());
                        long id_of_row = data_in_list.get(0).getId();
                        pt = Producttable.findById(Producttable.class, id_of_row);
                        pt.delete();
                        loaddataincart();
                    }
                    else if(test > 0){

                        addtocart(Integer.parseInt(viewHolder.valueedt.getText().toString()),
                                viewHolder.productnametxt.getText().toString(),
                                viewHolder.pricetxt.getText().toString(),
                                viewHolder.productdescriptiontxt.getText().toString(),
                                viewHolder.productidtxt.getText().toString(),
                                viewHolder.imageurltxt.getText().toString(),
                                viewHolder.salepricetxt.getText().toString(),
                                viewHolder.discountedpricetxt.getText().toString(),
                                viewHolder.perunittxt.getText().toString(),
                                viewHolder.unittyprtxt.getText().toString());
                    }

    ////////////////////////////////////////////   working of databse  pannel addig     ///////////////////////////
                }
            }
        });


        return convertView;
    }

    private void addtocart(int value , String name , String price , String hiddenproductdescription , String id_of_product , String mage_url   , String saleprice , String discountprice , String PerUnit , String UnitType) {


        if(new IDChecker().chekid_if_already_exsist(id_of_product) == true){
            List<Producttable> temp =  Producttable.find(Producttable.class, "Product_Id = ?", id_of_product);

            long id_of_row_intable = temp.get(0).getId();

            pt = Producttable.findById(Producttable.class, id_of_row_intable);
            pt.NumberOfUnits = value ;
            pt.save();

        }else if(new IDChecker().chekid_if_already_exsist(id_of_product) == false){
           new Producttable(name,
                    price,
                    hiddenproductdescription,
                    id_of_product,
                    mage_url,
                    value,
                    saleprice,
                    discountprice ,
                    PerUnit ,
                    UnitType).save();
        }

        loaddataincart();


    }

    private void loaddataincart() {
        ((MainActivity)con).pannelvisibility();


    }



    static class ViewHolderItem {
         TextView productnametxt , pricetxt ,salepricetxt , perunittxt , unittyprtxt ,discountedpricetxt ,productdescriptiontxt ,imageurltxt ,productidtxt , valueedt ;
         ImageView plusbtn , minusbtn ,  productimage;
            }
}
