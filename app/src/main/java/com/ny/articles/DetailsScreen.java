package com.ny.articles;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;
import com.ny.articles.WebView.WebviewActivity;
import com.squareup.picasso.Picasso;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class DetailsScreen extends AppCompatActivity {

    QRGEncoder qrgEncoder;
    Bitmap bitmap;

    TextView title;
    TextView description;
    TextView byLine;
    ImageView thumbnailMedium;

    String TAG = "DetailsScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_screen);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        byLine = findViewById(R.id.byLine);
        thumbnailMedium = findViewById(R.id.thumbnailMedium);

        title.setText(Utils.Companion.getTitle());
        description.setText(Utils.Companion.getArticleDescription());
        byLine.setText(Utils.Companion.getByLine());
        Picasso.get()
                .load(Utils.Companion.getThumbnailURL())
                .into(thumbnailMedium);
    }

    public void website(View v) {
        Intent detailActivity = new Intent(this, WebviewActivity.class);
        startActivity(detailActivity);
    }

    public void showQR(View v) {
        {
            //below line is for getting the windowmanager service.
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            //initializing a variable for default display.
            Display display = manager.getDefaultDisplay();
            //creating a variable for point which is to be displayed in QR Code.
            Point point = new Point();
            display.getSize(point);
            //getting width and height of a point
            int width = point.x;
            int height = point.y;
            //generating dimension from width and height.
            int dimen = width < height ? width : height;
            dimen = dimen * 3 / 4;
            //setting this dimensions inside our qr code encoder to generate our qr code.
            qrgEncoder = new QRGEncoder(Utils.Companion.getArticleURL(), null, QRGContents.Type.TEXT, dimen);
            try {
                //getting our qrcode in the form of bitmap.
                bitmap = qrgEncoder.encodeAsBitmap();
                // the bitmap is set inside our image view using .setimagebitmap method.
                alertDialog(bitmap);
            } catch (WriterException e) {
                //this method is called for exception handling.
                Log.e(TAG + "Tag", e.toString());
            }
        }
    }

    public void alertDialog(Bitmap bitmap) {
        // Get screen size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

        // Get target image size
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();

        // Scale the image down to fit perfectly into the screen
        // The value (250 in this case) must be adjusted for phone/tables displays
        while (bitmapHeight > (screenHeight - 250) || bitmapWidth > (screenWidth - 250)) {
            bitmapHeight = bitmapHeight / 2;
            bitmapWidth = bitmapWidth / 2;
        }

        // Create resized bitmap image
        BitmapDrawable resizedDialogImage = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, false));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Open Link", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                website(null);
            }
        }).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Without this line there is a very small border around the image (1px)
        dialog.getWindow().setBackgroundDrawable(null);

        dialog.show();
        ImageView qr = (ImageView) dialog.findViewById(R.id.qr);
        qr.setBackground(resizedDialogImage);
    }

}