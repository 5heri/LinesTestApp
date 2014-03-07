package com.example.linetestapp;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class DrawLines extends View {
	
	private final static String LOG_OUT = "LOG";
	private final static float SCALE = 100000;     // TEST SCALE
	
	private float x_p_prev;
	private float y_p_prev;
	private float x_p_curr;
	private float y_p_curr;
	
	private float x_draw_first;
	private float y_draw_first;
	
	private Context context;

	public DrawLines(Context context) {
		super(context);
		x_p_prev = 0;
		y_p_prev = 0;
		x_p_curr = 0;
		y_p_curr = 0;
		
		x_draw_first = 0;
		y_draw_first = 0;
		this.context = context;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		/*if (x_p_prev != 0 || y_p_prev != 0) {
		Log.d(LOG_OUT, "prevY: " + y_p_prev + "   prevX: " + x_p_prev);
		Log.d(LOG_OUT, "currY: " + y_p_curr + "   currX: " + x_p_curr);
		
		Rect rectangle = new Rect();
		rectangle.set(0, 0, canvas.getWidth(), canvas.getHeight()/2);
		
		Paint red = new Paint();
		red.setColor(Color.RED);
		red.setStyle(Paint.Style.FILL);
		
		Paint blue = new Paint();
		blue.setColor(Color.BLUE);
		blue.setStyle(Paint.Style.FILL);
		
		
		
		
		
		
		float diff_x = calculateDiffX(x_p_prev*SCALE, x_p_curr*SCALE);
		float diff_y = calculateDiffY(y_p_prev*SCALE, y_p_curr*SCALE);
		
		Log.d(LOG_OUT, "diffX: " + diff_x);
		Log.d(LOG_OUT, "diffY: " + diff_y);
		
		float x_to_next = x_draw_first+diff_x;
		float y_to_next = y_draw_first+diff_y;
		
		Log.d(LOG_OUT, "FROM: "+x_draw_first+" "+y_draw_first+"      "+"TO: "+x_to_next+" "+y_to_next);
		
		canvas.drawLine(x_draw_first, y_draw_first, 
				x_to_next, y_to_next, blue);
		
		x_draw_first = x_to_next;
		y_draw_first = y_to_next;
		
		
		
		
		
		
		} else {
			Log.d(LOG_OUT, "prevY: " + y_p_prev + "   prevX: " + x_p_prev);
			Log.d(LOG_OUT, "currY: " + y_p_curr + "   currX: " + x_p_curr);
			Toast.makeText(context, "lat or lng is zero", Toast.LENGTH_SHORT).show();
			x_draw_first = 240;
			y_draw_first = 400;
		}*/
		
		Paint blue = new Paint();
		blue.setColor(Color.BLUE);
		blue.setStyle(Paint.Style.FILL);
		
		Random rand = new Random();
		int randomX = rand.nextInt(480);
		int randomY = rand.nextInt(800);
		int divider = rand.nextInt(6)+1;
		canvas.save();
		canvas.drawCircle(randomX, randomY, canvas.getHeight()/divider, blue);
		canvas.restore();
		Toast.makeText(context, "Circle created", Toast.LENGTH_SHORT).show();
		//invalidate();
		Log.d(LOG_OUT, "X: "+randomX + "  Y: "+randomY);
		
	}
	
	
	
	
	private float calculateDiffX(float prev_x, float curr_x) {
		float diff = curr_x - prev_x;
		return diff;
	}
	
	
	private float calculateDiffY(float prev_y, float curr_y) {
		float diff = curr_y - prev_y;
		return diff;
	}
	
	public void setPrevCurX(float x) {
		if (x_p_prev == 0) {
			
		}
		x_p_prev = x_p_curr;
		x_p_curr = x;
		//x_p_curr = x + 10;  // for testing
	}
	
	public void setPrevCurY(float y) {
		y_p_prev = y_p_curr;
		y_p_curr = y;
	}
	
}
