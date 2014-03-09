package com.example.linetestapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/*
 * 
 * Implemented using a list to keep track of all previous positions.
 * TODO: Look into using SurfaceView 
 * TODO: TEST scaling values
 * 
 */

public class DrawLines extends View {
	
	private final static String LOG_OUT = "LOG";
	private final static float SCALE = 100000;     // TEST SCALE
	private final static float POS_MOVING_SCALE = 1.0f;
	private final static float NEG_MOVING_SCALE = -1.0f;
	
	private List<PointPair>  path;
	
	private float x_p_prev;
	private float y_p_prev;
	private float x_p_curr;
	private float y_p_curr;
	
	private float x_draw_first;
	private float y_draw_first;
	
	private boolean drawNow;
	
	private Context context;

	public DrawLines(Context context) {
		super(context);
		
		path  = new ArrayList<PointPair>();
		path.add(new PointPair(240, 400));      // Remove hard coded values
		
		x_p_prev = 0;
		y_p_prev = 0;
		x_p_curr = 0;
		y_p_curr = 0;
		
		x_draw_first = 0;
		y_draw_first = 0;
		
		drawNow = false;
		
		this.context = context;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		
		
		if (x_p_prev != 0 || y_p_prev != 0) {
			Log.d(LOG_OUT, "prevY: " + y_p_prev + "   prevX: " + x_p_prev);
			Log.d(LOG_OUT, "currY: " + y_p_curr + "   currX: " + x_p_curr);

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

			if (diff_x > POS_MOVING_SCALE || diff_x < NEG_MOVING_SCALE 
					|| diff_y > POS_MOVING_SCALE || diff_y < NEG_MOVING_SCALE) {
				
				Log.d(LOG_OUT, "FROM: "+x_draw_first+" "+y_draw_first);
				
				x_draw_first += diff_x;
				y_draw_first += diff_y;
				
				Log.d(LOG_OUT, "TO: "+x_draw_first+" "+y_draw_first);

				PointPair point = new PointPair(x_draw_first, y_draw_first);
				path.add(point);
			}
			canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, canvas.getHeight()/250, blue);
			int size = path.size();
			Log.d(LOG_OUT, "SIZE: " + size);
			if (size > 1) {
				for (int i = 1; i < size; i++) {
					canvas.drawLine(path.get(i-1).getX(), path.get(i-1).getY(), 
							path.get(i).getX(), path.get(i).getY(), blue);
				//canvas.drawCircle(path.get(i).getX(), path.get(i).getY(), canvas.getHeight()/250, blue);
				}				
			}
		} else {
			Log.d(LOG_OUT, "prevY: " + y_p_prev + "   prevX: " + x_p_prev);
			Log.d(LOG_OUT, "currY: " + y_p_curr + "   currX: " + x_p_curr);
			Toast.makeText(context, "lat or lng is zero", Toast.LENGTH_SHORT).show();
			x_draw_first = canvas.getWidth()/2;
			y_draw_first = canvas.getHeight()/2;
		}
		
		
		/*Paint blue = new Paint();
		blue.setColor(Color.BLUE);
		blue.setStyle(Paint.Style.FILL);
		
		Random rand = new Random();
		int randomX = rand.nextInt(480);
		int randomY = rand.nextInt(800);
		PointPair point = new PointPair(randomX, randomY);
		//canvas.save();
		path.add(point);
		int size = path.size();
		for (int i = 0; i < size; i++) {
			canvas.drawCircle(path.get(i).getX(), path.get(i).getY(), canvas.getHeight()/50, blue);			
		}*/
		//canvas.restore();
		//Toast.makeText(context, "Circle created", Toast.LENGTH_SHORT).show();
		//invalidate();
		//Log.d(LOG_OUT, "X: "+randomX + "  Y: "+randomY);
		if (drawNow) {
			invalidate();			
		}
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
		x_p_prev = x_p_curr;
		x_p_curr = x;
	}
	
	public void setPrevCurY(float y) {
		y_p_prev = y_p_curr;
		y_p_curr = y;
	}
	
	// Used to lock invalidate().
	public void setDrawTrue() {
		drawNow = true;
	}
	
	// Used to lock invalidate().
	public void setDrawFalse() {
		drawNow = false;
	}
	
}
