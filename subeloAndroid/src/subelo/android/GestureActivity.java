/** Copyright 2011 UNED
*
*  Miguel Rivero
*  Juan Manuel Cigarran
*
* Licensed under the EUPL, Version 1.1 or – as soon they will be
approved by the European Commission – subsequent versions of the
EUPL (the "Licence");* you may not use this work except in
compliance with the Licence.
* You may obtain a copy of the Licence at:
*
* http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1
*
* Unless required by applicable law or agreed to in writing,
software distributed under the Licence is distributed on an "AS
IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.
* See the Licence for the specific language governing permissions
and limitations under the Licence.
*/

package subelo.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 
 * @author Miguel Rivero
 * 
 *         Base abstract class for Activities that support gesture detection.
 *         Any class that needs specific actions when gesture detected must
 *         implement the abstract method for each of them.
 * 
 */
public abstract class GestureActivity extends Activity {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    
//	// Minimum velocity to consider gesture
//	float MIN_VELOCITY = 150;
//	// Minimum ratio between the two axis to consider movement to one axis or the other
//	int MIN_RATIO_AXIS = 3;

	private GestureDetector mGestureDetector;

	protected abstract void upGestureAction();

	protected abstract void downGestureAction();

	protected abstract void leftGestureAction();

	protected abstract void rightGestureAction();

	protected abstract void singleTapAction();

	protected abstract void doubleTapAction();
	
	protected abstract void longPressAction();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create the Gesture detector
		mGestureDetector = new GestureDetector(this, new LearnGestureListener());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mGestureDetector.onTouchEvent(event))
			return true;

		else
			return false;
	}

	/**
	 * 
	 * @author Miguel Rivero
	 * 
	 *         Listener of the user's gesture. Calls the abstract method
	 *         corresponding the detected gesture
	 * 
	 */
	class LearnGestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onSingleTapUp(MotionEvent ev) {
			Log.d("onSingleTapUp", ev.toString());
			singleTapAction();
			return true;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			Log.d("---onDoubleTapEvent---", e.toString());
			doubleTapAction();
			return false;
		}

		@Override
		public void onShowPress(MotionEvent ev) {
			Log.d("onShowPress", ev.toString());
		}

		@Override
		public void onLongPress(MotionEvent ev) {
			Log.d("onLongPress", ev.toString());
			longPressAction();
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			Log.d("onScroll", e1.toString());
			return true;
		}

		@Override
		public boolean onDown(MotionEvent ev) {
			Log.d("onDownd", ev.toString());
			return true;
		}

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//	    Intent intent = new Intent(MainActivity.this.getBaseContext(), MainActivity.class);
 
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                return false;
            }
 
            // right to left swipe
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            	leftGestureAction();
//    		startActivity(intent);
//    		MainActivity.this.overridePendingTransition(
//			R.anim.slide_in_right,
//			R.anim.slide_out_left
//    		);
    	    // right to left swipe
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            	rightGestureAction();
//    		startActivity(intent);
//    		MainActivity.this.overridePendingTransition(
//			R.anim.slide_in_left, 
//			R.anim.slide_out_right
//    		);
            } else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            	upGestureAction();
	        } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
	        	downGestureAction();
	        }
 
            return false;
        }
    
	
//		@Override
//		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//				float velocityY) {
//			String debugMessage = "NOT RECOGNIZED GESTURE";
//
//			// Check that there is an actual gesture (over the MIN velocity)
//			if (Math.abs(velocityX) < MIN_VELOCITY
//					&& Math.abs(velocityY) < MIN_VELOCITY) {
//				debugMessage += (". NOT ENOUGH VELOCITY FOR A GESTURE");
//				return true;
//			}
//
//			// Movement Left or Right
//			if ((velocityY == 0)
//					|| ((Math.abs(velocityX) / Math.abs(velocityY)) > MIN_RATIO_AXIS)) {
//				if (velocityX > 0) {
//					rightGestureAction();
//					debugMessage = "RIGHT";
//				} else {
//					leftGestureAction();
//					debugMessage = "LEFT";
//				}
//			}
//
//			// Movement Up or Down
//			if ((velocityX == 0)
//					|| ((Math.abs(velocityY) / Math.abs(velocityX)) > MIN_RATIO_AXIS)) {
//				if (velocityY > 0) {
//					downGestureAction();
//					debugMessage = "DOWN";
//				} else {
//					upGestureAction();
//					debugMessage = "UP";
//				}
//			}
//
//			Log.d("onFling:", "X=" + String.valueOf(velocityX) + " | " + "Y="
//					+ String.valueOf(velocityY) + " | " + debugMessage);
//
//			return true;
//		}

	}
}