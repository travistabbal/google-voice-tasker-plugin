/*
 *  Google Voice Tasker Plugin - Tasker integration with Google Voice
 *  Copyright (C) 2012  Kenneth Ko <http://www.kenko.co>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ko.googlevoice.receiver;

import java.io.IOException;

import android.os.AsyncTask;

import com.techventus.server.voice.Voice;

public class GoogleVoiceSendSMSTask extends AsyncTask<String, Long, Integer> {
	protected Integer doInBackground(String... voicesms) {
		Integer retVal = 0;
		Voice voice;

		try {
			/*
			 * voicesms[0]:	username
			 * voicesms[1]: password
			 * voicesms[2]: phone number
			 * voicesms[3]: message
			 */
			voice = new Voice(voicesms[0], voicesms[1]);
			voice.sendSMS(voicesms[2], voicesms[3]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retVal;
	}
	
	protected void onProgressUpdate() {
		
	}
	
	protected void onPostExecute(Integer result) {
		
	}

}
