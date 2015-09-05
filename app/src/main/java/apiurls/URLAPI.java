package apiurls;

/**
 * Created by samir on 07/05/15.
 */
public  class URLAPI {
    public static  String allcategory = "http://spinnosolutions.com/ecommerce_api/api/category.php";
    public static  String allcities = "http://spinnosolutions.com/ecommerce_api/api/city.php";
    public static  String zonesincity = "http://spinnosolutions.com/ecommerce_api/api/location.php?city_id=";

    public static String locationbasedcategory = "http://spinnosolutions.com/ecommerce_api/api/category.php?location_id=";

    public  static  String subcategory = "http://spinnosolutions.com/ecommerce_api/api/product.php?cat=";
    public static  String imageurlproduct = "http://spinnosolutions.com/ecommerce_api/product_image/";
    public static  String imageurlcategory = "http://spinnosolutions.com/ecommerce_api/category_image/";

    public static  String loginstart = "http://spinnosolutions.com/ecommerce_api/api/login.php?phone=";
    public static String  loginmid = "&password=";

    public static String previousorders = "http://spinnosolutions.com/ecommerce_api/api/view_order.php?user_id=";


    public static String registrationstart ="http://spinnosolutions.com/ecommerce_api/api/register.php?first_name=";

    public static String viewaddress ="http://spinnosolutions.com/ecommerce_api/api/view_address.php?user_id=";

    public static String addaddress ="http://spinnosolutions.com/ecommerce_api/api/add_address.php?user_id=";

    public static String searchLocation = "http://spinnosolutions.com/ecommerce_api/api/location_search.php?s=";
    public static String deleteaddress = "http://spinnosolutions.com/ecommerce_api/api/dele_address.php?id=";

    public static String enquiry = "http://spinnosolutions.com/ecommerce_api/api/enquiry.php?name=";
    public static String adminaddress = "http://wscubetech.org/ecommerce_api/api/contact_uss.php";

    public static String forgetpass ="http://spinnosolutions.com/ecommerce_api/api/forgot_pass.php?email=";
    public static  String faq = "http://spinnosolutions.com/ecommerce_api/api/faq.php";
    public static String aboutus = "http://spinnosolutions.com/ecommerce_api/api/about.php";
    public static String timeFrom_To = "http://spinnosolutions.com/ecommerce_api/api/working_time.php";



    public  static String message_user_name = "sumit1152";
    public  static String message_user_password = "maliksaab";
    public static String message_sid = "WEBSMS";


    public static String cancelOrder = "http://wscubetech.org/ecommerce_api/api/cancelled_order.php?id=";

    public static  String  promotionalSMS_Single = "http://cloud.smsindiahub.in/vendorsms/pushsms.aspx?user="+message_user_name+"password="+message_user_password+"&msisdn="+"919898xxxxxx&sid=SenderId&msg=test%20message&fl=0";
  }
