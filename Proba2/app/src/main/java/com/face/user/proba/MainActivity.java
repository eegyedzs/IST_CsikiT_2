package com.face.user.proba;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }
    static class MyView extends View {
        int imageHeight, imageWidth;
        int numberOfFaces = 5;
        int foundFaces;
        float eyeDistance;
        Bitmap bitmap;
        Paint paint;
        FaceDetector faceDetect;
        FaceDetector.Face faces[];


        MyView(Context context) {
            super(context);


            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);

            BitmapFactory.Options bitmapOptionsInfo = new BitmapFactory.Options();
            bitmapOptionsInfo.inPreferredConfig = Bitmap.Config.RGB_565;
            bitmap = new BitmapFactory().decodeResource(getResources(), R.drawable.picture_2, bitmapOptionsInfo);

            imageHeight = bitmap.getHeight();
            imageWidth = bitmap.getWidth();

            faceDetect = new FaceDetector(imageWidth, imageHeight, numberOfFaces);
            faces = new FaceDetector.Face[numberOfFaces];
            foundFaces = faceDetect.findFaces(bitmap, faces);

        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(bitmap, 0, 0, null);
            for(int i = 0; i< foundFaces; ++i){
                FaceDetector.Face face = faces[i];
                PointF midPoint = new PointF();
                face.getMidPoint(midPoint);
                eyeDistance = face.eyesDistance();
                canvas.drawRect((int)midPoint.x-eyeDistance, (int)midPoint.y-eyeDistance, (int)midPoint.x+eyeDistance, (int)midPoint.y+eyeDistance, paint);




            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
