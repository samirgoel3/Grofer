package com.spinno.buzczar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapters.FruitListAdapter;
import apiurls.URLAPI;
import pojo.PRODUCT_LIST;
import singleton.VolleySingleton;

public class SampleListFragment extends ScrollTabHolderFragment implements OnScrollListener {

	private static final String ARG_POSITION = "position";

	private ListView mListView;
	private ArrayList<String> mListItems;

    List<PRODUCT_LIST> productlistlist = new ArrayList<PRODUCT_LIST>();


    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> imageurls = new ArrayList<String>();
    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> pdescription = new ArrayList<String>();
    ArrayList<String> unitsper = new ArrayList<String>();
    ArrayList<String> unittype = new ArrayList<String>();
    ArrayList<String> saleprice = new ArrayList<String>();
    ArrayList<String> discountedprice = new ArrayList<String>();
    ArrayList<Integer> noofunitsinfruitlistfragment = new ArrayList<Integer>();

    FruitListAdapter fdap ;





    StringRequest sr ;
    RequestQueue queue ;

	private int mPosition;

	public static Fragment newInstance(int position) {
		SampleListFragment f = new SampleListFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mPosition = getArguments().getInt(ARG_POSITION);

		mListItems = new ArrayList<String>();

        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        sr = new StringRequest(Request.Method.GET, URLAPI.subcategory.concat(MyArrayListForcategory.id.get(mPosition)), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.equals("null")){
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    productlistlist = Arrays.asList(gson.fromJson(response, PRODUCT_LIST[].class));


                    noofunitsinfruitlistfragment.clear();
                    for(int i = 0 ;i<productlistlist.size();i++){
                        name.add(productlistlist.get(i).name);
                        price.add(productlistlist.get(i).price);
                        imageurls.add(URLAPI.imageurlproduct.concat(productlistlist.get(i).image));
                        ids.add(productlistlist.get(i).id);
                        pdescription.add(productlistlist.get(i).description);
                        unitsper.add(productlistlist.get(i).unit);
                        unittype.add(productlistlist.get(i).unit_type);
                        saleprice.add(productlistlist.get(i).saleprice);
                        discountedprice.add(productlistlist.get(i).disount_price);
                        noofunitsinfruitlistfragment.add(0);
                    }

                    Log.d("images of product %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%", "" + imageurls);

                    /////////////////////// condition for replacing the exsisting  no of units //// /////////// / // / / ////
                    List<Producttable> templist = Producttable.listAll(Producttable.class);
                    for (int i = 0 ; i<templist.size() ; i++){
                        if(ids.contains(templist.get(i).getProductId())){
                            int et = ids.indexOf(templist.get(i).getProductId());
                            noofunitsinfruitlistfragment.set(et, templist.get(i).getNumberOfUnits());
                        }
                    }
                    /////////////////////// condition for replacing the exsisting  no of units //// /////////// / // / / ////
                    fdap = new FruitListAdapter(name ,price,saleprice ,unitsper,unittype,imageurls,discountedprice ,pdescription , ids , noofunitsinfruitlistfragment, getActivity());
                    mListView.setAdapter(fdap);
                }else if(response.equals("null")){
                  //  Toast.makeText(getActivity(), "No product in this category with position  " + mPosition, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();

            }
        });
        sr.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(sr);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_list, null);
        mListView = (ListView) v.findViewById(R.id.listView);
        View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, mListView, false);
        mListView.addHeaderView(placeHolderView);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


        mListView.setOnScrollListener(this);
	}

	@Override
	public void adjustScroll(int scrollHeight) {
		if (scrollHeight == 0 && mListView.getFirstVisiblePosition() >= 1) {
			return;
		}

		mListView.setSelectionFromTop(1, scrollHeight);

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (mScrollTabHolder != null){
			mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);

        }
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// nothing
	}








}