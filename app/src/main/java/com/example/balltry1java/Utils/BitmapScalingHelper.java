package com.example.balltry1java.Utils;

public class BitmapScalingHelper
{
   /* public static Bitmap decodeResource(Resources res, int resId, int dstWidth, int dstHeight)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inJustDecodeBounds = false;

        //options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth,
                dstHeight);

        options = new BitmapFactory.Options();
        //May use null here as well. The funciton may interpret the pre-used options variable in ways hard to tell.
        Bitmap unscaledBitmap = BitmapFactory.decodeResource(res, resId, options);

        if(unscaledBitmap == null)
        {
            Log.e("ERR","Failed to decode resource - " + resId + " " + res.toString());
            return null;
        }

        return unscaledBitmap;
    }*/
}