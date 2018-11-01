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

import android.content.Intent;
import android.os.Bundle;

public class MainMenuActivity extends TwitterGestureActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_menu);
		speak(getString(R.string.mainMenu));
	}
	
	
	@Override
	protected void upGestureAction() {
		Intent listTweetsIntent = new Intent(this, ListTweetsActivity.class);
		startActivity(listTweetsIntent);
		
		overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
		finish();
	}

	@Override
	protected void downGestureAction() {

	}

	@Override
	protected void leftGestureAction() {
		// TODO Auto-generated method stub
		Intent updateStatusIntent = new Intent(this, UpdateStatusActivity.class);
		startActivity(updateStatusIntent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
	}

	@Override
	protected void rightGestureAction() {

	}

	@Override
	protected void singleTapAction() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doubleTapAction() {

	}

	@Override
	protected void longPressAction() {
		// TODO Auto-generated method stub
		
	}
	
}
