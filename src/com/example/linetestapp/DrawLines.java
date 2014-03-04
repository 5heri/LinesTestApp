package com.example.linetestapp;

import org.apache.http.client.CircularRedirectException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class DrawLines extends View {

	public DrawLines(Context context) {
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Rect rectangle = new Rect();
		rectangle.set(0, 0, canvas.getWidth(), canvas.getHeight()/2);
		
		Paint red = new Paint();
		red.setColor(Color.RED);
		red.setStyle(Paint.Style.FILL);
		
		Paint blue = new Paint();
		blue.setColor(Color.BLUE);
		blue.setStyle(Paint.Style.FILL);
		
		canvas.drawRect(rectangle, red);
		canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/4, canvas.getHeight()/8, blue);
		
	}

	
	
	

}
