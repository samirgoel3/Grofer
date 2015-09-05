package com.spinno.buzczar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.nineoldandroids.view.ViewHelper;
import com.orm.SugarRecord;

import net.simonvt.menudrawer.MenuDrawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import astuetz.PagerSlidingTabStrip;
import flavienlaurent.notboringactionbar.AlphaForegroundColorSpan;
import networkchecker.NetworkChecker;
import sessionmanager.LocationSesionManager;
import sessionmanager.SessionManager;

public class MainActivity extends FragmentActivity implements ScrollTabHolder, ViewPager.OnPageChangeListener {

	private static AccelerateDecelerateInterpolator sSmoothInterpolator = new AccelerateDecelerateInterpolator();



    private MenuDrawer mDrawer;


  //  private KenBurnsSupportView mHeaderPicture;
	private View mHeader , tutorialscreenone;

	private PagerSlidingTabStrip mPagerSlidingTabStrip;
	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;

	private int mActionBarHeight;
	private int mMinHeaderHeight;
	private int mHeaderHeight;
	private int mMinHeaderTranslation;


	private RectF mRect1 = new RectF();
	private RectF mRect2 = new RectF();

	private TypedValue mTypedValue = new TypedValue();
	private SpannableString mSpannableString;
	private AlphaForegroundColorSpan mAlphaForegroundColorSpan;


    LinearLayout cartpannnel  , locationchangeronbar;
    SliderLayout sl;



    ////////////////////
    TextView usenameinmenudrawer  , locattiontext;
    private LinearLayout locationmenubutton ;
    private LinearLayout loginmenubutton ;
    private LinearLayout addressmenubutton ;
    private LinearLayout addressunclickablebtn;
    private LinearLayout ordersmenubutton ;
    private LinearLayout cartmenubutton;
    private LinearLayout notificationmenubutton ;
    private LinearLayout logoutmenubutton ;
    private LinearLayout enquirymenubutton ;
    private LinearLayout sharemenubutton ;
    private LinearLayout faqmenubutton ;
    private LinearLayout aboutUsmenubtn ;

    ImageView openmenu  , gotocartbutton ;


    SessionManager sm ;
    LocationSesionManager lsm ;
    HashMap<String , String> data_from_sharedprefrences;
    String user_name_from_prefences;



    List<Producttable> data_in_list ;
    Producttable pt ;

    TextView totalpriceoncarttxt ;


    public static Activity fa;

    RelativeLayout nouselayout  , secondnouse;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        mDrawer = MenuDrawer.attach(this);
        mDrawer.setContentView(R.layout.activity_main);
        mDrawer.setMenuView(R.layout.activity_menu_drawer);
        mDrawer.setDropShadowColor(Color.rgb(192, 192, 192));
        mDrawer.setDropShadowSize(10);
        SugarRecord.deleteAll(Producttable.class);

        ActivityIdentifire.mainactivityopen = 1 ;

        fa = this;

        sm = new SessionManager(getApplicationContext());
        lsm = new LocationSesionManager(getApplicationContext());

		mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
		mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
		mMinHeaderTranslation = -mMinHeaderHeight + getActionBarHeight();





        tutorialscreenone = findViewById(R.id.screen_one_tutorial);

        usenameinmenudrawer = (TextView) findViewById(R.id.username_in_menu_drawer);
        locationmenubutton = (LinearLayout) findViewById(R.id.location_in_menu);
        loginmenubutton = (LinearLayout) findViewById(R.id.login_in_menu);
        logoutmenubutton = (LinearLayout) findViewById(R.id.logout_in_menu);
        addressmenubutton = (LinearLayout) findViewById(R.id.my_address_in_menu);
        addressunclickablebtn = (LinearLayout) findViewById(R.id.my_address_unclickable_in_menu);
        ordersmenubutton = (LinearLayout) findViewById(R.id.myorder_in_menu);
        cartmenubutton = (LinearLayout) findViewById(R.id.mycart_in_menu);
        enquirymenubutton = (LinearLayout) findViewById(R.id.enquiry_in_menu);
        sharemenubutton = (LinearLayout) findViewById(R.id.share_in_menu);
        notificationmenubutton = (LinearLayout) findViewById(R.id.notification_in_menu);
        totalpriceoncarttxt = (TextView) findViewById(R.id.total_price_on_cart);
        gotocartbutton  = (ImageView) findViewById(R.id.gotocart_cart_pannel_for_main);
        cartpannnel = (LinearLayout) findViewById(R.id.cartpannel_in_main);
        locationchangeronbar = (LinearLayout) findViewById(R.id.locationchanger_on_actionbar);
        faqmenubutton = (LinearLayout) findViewById(R.id.frequently_asked_uqestion_btn_in_main_activity);
        aboutUsmenubtn = (LinearLayout) findViewById(R.id.about_us_uqestion_btn_in_main_activity);

      //  secondnouse = (RelativeLayout) findViewById(R.id.secondnouse);
        locattiontext = (TextView) findViewById(R.id.locationtextid_in_main_activity);


      //  nouselayout = (RelativeLayout) findViewById(R.id.nouselayout);

        sl = (SliderLayout) findViewById(R.id.slider);

          startimageviewloader();


        locattiontext.setText(lsm.getlocation().get(LocationSesionManager.KEY_LOCATION_NAME));

        openmenu = (ImageView) findViewById(R.id.open_menu_icon);


        cartpannnel.setVisibility(View.INVISIBLE);


    //    pannelvisibility();

        setlisteners();
		mHeader = findViewById(R.id.header);

		mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(25);


		mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
		mPagerAdapter.setTabHolderScrollingContent(this);

		mViewPager.setAdapter(mPagerAdapter);



		mPagerSlidingTabStrip.setViewPager(mViewPager);
		mPagerSlidingTabStrip.setOnPageChangeListener(this);
        mPagerSlidingTabStrip.setTextColor(Color.WHITE);
        mPagerSlidingTabStrip.setTextSize(20);
        mPagerSlidingTabStrip.setTypeface(null, Typeface.BOLD);
        mPagerSlidingTabStrip.setIndicatorHeight(7);
		mSpannableString = new SpannableString(getString(R.string.actionbar_title));

		mAlphaForegroundColorSpan = new AlphaForegroundColorSpan(0xffffffff);

       //		ViewHelper.setAlpha(getActionBarIconView(), 0f);

       //		getSupportActionBar().setBackgroundDrawable(null);

           if(sm.getTuteone().get(SessionManager.KEY_SLIDE_TUTORIAL).equals("done")){
               tutorialscreenone.setVisibility(View.GONE);
           }



        pannelvisibility();
       loginLogoutChecker();

	}

    public void pannelvisibility() {

        List<Producttable> templist = Producttable.listAll(Producttable.class);
        if(templist.size() == 0 ){
            cartpannnel.setVisibility(View.GONE);
        }
        else {
            cartpannnel.setVisibility(View.VISIBLE);
            show_amount_on_cart();
        }
    }



    public void show_amount_on_cart(){

        int temp1 , temp2 , temp3 , temp4 ,multiplier,multiplier2 , result_to_show_on_cart = 0 , result_to_show_on_cart_total_discount = 0 ;
        List<Producttable> templist = Producttable.listAll(Producttable.class);

       // changecolourofcart();


        for(int i = 0 ; i<templist.size() ; i++){


            temp1 = Integer.parseInt(templist.get(i).getSalePrice());
            temp3 = Integer.parseInt(templist.get(i).getDiscountPrice());
            temp2 = templist.get(i).getNumberOfUnits();



            multiplier = temp1 * temp2 ;
            multiplier2 = temp3 * temp2 ;
            result_to_show_on_cart = result_to_show_on_cart +multiplier ;
            result_to_show_on_cart_total_discount = result_to_show_on_cart_total_discount + multiplier2  ;
        }
        totalpriceoncarttxt.setText(String.valueOf(result_to_show_on_cart));
      //  total_discount.setText(String.valueOf(result_to_show_on_cart_total_discount));

    }


    @Override
    protected void onResume() {
        super.onResume();
        loginLogoutChecker();
        pannelvisibility();
    }

    private void loginLogoutChecker() {
        data_from_sharedprefrences = sm.getUserDetails();
        user_name_from_prefences = data_from_sharedprefrences.get(SessionManager.KEY_UserName);

        if(sm.cheskUseroginStatus() == false){
          //  Toast.makeText(MainActivity.this , "Hello Guest " , Toast.LENGTH_LONG).show();
                       logoutmenubutton.setVisibility(View.GONE);
                       loginmenubutton.setVisibility(View.VISIBLE);
                       addressmenubutton.setVisibility(View.GONE);
                      addressunclickablebtn.setVisibility(View.VISIBLE);
                      usenameinmenudrawer.setText("Guest");
        }else{
          //  Toast.makeText(MainActivity.this , "You are Logged In as "+ user_name_from_prefences , Toast.LENGTH_LONG).show();
                       loginmenubutton.setVisibility(View.GONE);
                       logoutmenubutton.setVisibility(View.VISIBLE);
                       addressmenubutton.setVisibility(View.VISIBLE);
                       addressunclickablebtn.setVisibility(View.GONE);
                       usenameinmenudrawer.setText(user_name_from_prefences);

        }
    }

    private void setlisteners() {


        locationchangeronbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this , LocationWithNoChange.class );
                mDrawer.closeMenu();
                startActivity(in);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

    /*    secondnouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });  */

        gotocartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this , CartActivity.class );
                mDrawer.closeMenu();
                startActivity(in);
            }
        });


        cartpannnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this , CartActivity.class );
                mDrawer.closeMenu();
                startActivity(in);
            }
        });


        openmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.toggleMenu();
            }
        });

        loginmenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this , LoginActivity.class );
                mDrawer.closeMenu();
                startActivity(in);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });


        logoutmenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // custom dialog
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog);
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialogButtoncancel);

                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sm.logoutUser();
                        loginLogoutChecker();
                        dialog.dismiss();
                    }
                });


                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        addressmenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIdentifire.cartactivity = 0 ;
                Intent in = new Intent(MainActivity.this , AddressActivity.class );
                mDrawer.closeMenu();
                in.putExtra("fromwhichactivity", "mainactivity");
                startActivity(in);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        ordersmenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this , MyPreviousOrdersActivity.class );
                mDrawer.closeMenu();
                startActivity(in);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });


        cartmenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this , CartActivity.class );
                mDrawer.closeMenu();
                startActivity(in);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        notificationmenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      Intent in = new Intent(MainActivity.this , NotificationActivity.class );
                            mDrawer.closeMenu();
                           startActivity(in);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });


        locationmenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this , LocationWithNoChange.class );
                mDrawer.closeMenu();
                startActivity(in);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        enquirymenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this , EnquiryActivity.class );
                mDrawer.closeMenu();
                if(new NetworkChecker().isNetworkConnected(MainActivity.this)){
                    startActivity(in);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }else{
                    Toast.makeText(MainActivity.this , "Please check your Internet connection" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        sharemenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt();
            }
        });




        tutorialscreenone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.createtuteone("done");
                tutorialscreenone.setVisibility(View.GONE);

            }
        });


        faqmenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this , FAQActivity.class );
                mDrawer.closeMenu();
                startActivity(in);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });


        aboutUsmenubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this , AboutUsActivity .class );
                mDrawer.closeMenu();
                startActivity(in);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });




    }

    private void shareIt() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "http://play.google.com/store/apps/details?id=<com.spinno.buzczar>";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
	public void onPageScrollStateChanged(int arg0) {
		// nothing
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		// nothing
	}

	@Override
	public void onPageSelected(int position) {

        sl.setCurrentPosition(position);
        SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
		ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);

		currentHolder.adjustScroll((int) (mHeader.getHeight() + ViewHelper.getTranslationY(mHeader)));
        currentHolder.adjustScroll((int) (sl.getHeight() + ViewHelper.getTranslationY(sl)));
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
		if (mViewPager.getCurrentItem() == pagePosition) {
			int scrollY = getScrollY(view);
			ViewHelper.setTranslationY(mHeader, Math.max(-scrollY, mMinHeaderTranslation));
			float ratio = clamp(ViewHelper.getTranslationY(mHeader) / mMinHeaderTranslation, 0.0f, 1.0f);

            ViewHelper.setTranslationY(sl, Math.max(-scrollY, mMinHeaderTranslation));
            float ratio2 = clamp(ViewHelper.getTranslationY(sl) / mMinHeaderTranslation, 0.0f, 1.0f);
//			interpolate(mHeaderLogo, getActionBarIconView(), sSmoothInterpolator.getInterpolation(ratio));
			setTitleAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
            setTitleAlpha(clamp(5.0F * ratio2 - 4.0F, 0.0F, 1.0F));
		}
	}

    private void startimageviewloader() {
        ////////////////////////////////////// IMAGE SLIDER ////////////////////////////////////////////////////////////////////////////

        for (int i = 0 ; i<MyArrayListForcategory.images.size() ;i++ ) {

            Log.d("images for categories   ****&*&*&*&*&*&*&*&*&*&*&*&*&*", MyArrayListForcategory.images.get(i));


            DefaultSliderView deafsliderview = new DefaultSliderView(MainActivity.this);


                        deafsliderview.image(MyArrayListForcategory.images.get(i)).setScaleType(BaseSliderView.ScaleType.CenterCrop);
                       sl.addSlider(deafsliderview);
        }

        sl.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        sl.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sl.setCustomAnimation(new DescriptionAnimation());
        sl.stopAutoCycle();
        sl.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);



        sl.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this));


        ////////////////////////////////////// IMAGE SLIDER ////////////////////////////////////////////////////////////////////////////

    }

	@Override
	public void adjustScroll(int scrollHeight) {
		// nothing
	}

	public int getScrollY(AbsListView view) {
		View c = view.getChildAt(0);
		if (c == null) {
			return 0;
		}

		int firstVisiblePosition = view.getFirstVisiblePosition();
		int top = c.getTop();

		int headerHeight = 0;
		if (firstVisiblePosition >= 1) {
			headerHeight = mHeaderHeight;
		}

		return -top + firstVisiblePosition * c.getHeight() + headerHeight;
	}

	public static float clamp(float value, float max, float min) {
		return Math.max(Math.min(value, min), max);
	}

	private void interpolate(View view1, View view2, float interpolation) {
		getOnScreenRect(mRect1, view1);
//		getOnScreenRect(mRect2, view2);

		float scaleX = 1.0F + interpolation * (mRect2.width() / mRect1.width() - 1.0F);
		float scaleY = 1.0F + interpolation * (mRect2.height() / mRect1.height() - 1.0F);
		float translationX = 0.5F * (interpolation * (mRect2.left + mRect2.right - mRect1.left - mRect1.right));
		float translationY = 0.5F * (interpolation * (mRect2.top + mRect2.bottom - mRect1.top - mRect1.bottom));

		ViewHelper.setTranslationX(view1, translationX);
		ViewHelper.setTranslationY(view1, translationY - ViewHelper.getTranslationY(mHeader));
        ViewHelper.setTranslationY(view1, translationY - ViewHelper.getTranslationY(sl));
		ViewHelper.setScaleX(view1, scaleX);
		ViewHelper.setScaleY(view1, scaleY);
	}

	private RectF getOnScreenRect(RectF rect, View view) {
//		rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
		return rect;
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public int getActionBarHeight() {
		if (mActionBarHeight != 0) {
			return mActionBarHeight;
		}
		
		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
			getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
		}else{
			getTheme().resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
		}
		
		mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, getResources().getDisplayMetrics());
		
		return mActionBarHeight;
	}

	private void setTitleAlpha(float alpha) {
		mAlphaForegroundColorSpan.setAlpha(alpha);
		mSpannableString.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		//getSupportActionBar().setTitle(mSpannableString);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
    private ImageView getActionBarIconView() {
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			return (ImageView)findViewById(android.R.id.home);
		}

		return (ImageView)findViewById(android.support.v7.appcompat.R.id.home);
	}




















	public class PagerAdapter extends FragmentStatePagerAdapter {

		private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
         ArrayList<String> name  = MyArrayListForcategory.name;

		private ScrollTabHolder mListener;

		public PagerAdapter(FragmentManager fm) {
			super(fm);
			mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();
		}

		public void setTabHolderScrollingContent(ScrollTabHolder listener) {
			mListener = listener;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return name.get(position);
		}

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
            Toast.makeText(MainActivity.this , "State restored " , Toast.LENGTH_SHORT).show();
            super.restoreState(state, loader);
        }


            /*   @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            FragmentManager manager = ((Fragment) object).getFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove((Fragment) object);
            trans.commit();
            super.destroyItem(container, position, object);
        }   */

        @Override
		public int getCount() {
			return name.size();
		}

		@Override
		public Fragment getItem(int position) {
			ScrollTabHolderFragment fragment = (ScrollTabHolderFragment) SampleListFragment.newInstance(position);

			mScrollTabHolders.put(position, fragment);
			if (mListener != null) {
				fragment.setScrollTabHolder(mListener);
			}

			return fragment;
		}

		public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
			return mScrollTabHolders;
		}

	}
}
