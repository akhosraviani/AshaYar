package Answer;

/**
 * Created by jasmine on 6/27/2016.
 */
public class AnswerToResponse
{
    public static String Answer2Response(int response){
        switch (response){
            case 200:
                return "ذخیره شد";
            case 404:
                return "اطلاعات مورد نظر یافت نشد";
            case 400:
                return "خطای سیستمی لطفا با مدیر سیتم تماس بگیرید";
            case 501:
                return "خطای پیاده سازی لطفا با مدیر سیستم تماس بگیرید";
            case 401:
                return "نام کاربری یا گذرواژه اشتباه می باشد!";
            case 500:
                return "خطای داخلی سیستم لطفا با مدیر سیستم تماس بگیرید";
            case 403:
                return "اجازه دسترسی ندارین!";

        }
        return "error";

    }
}
