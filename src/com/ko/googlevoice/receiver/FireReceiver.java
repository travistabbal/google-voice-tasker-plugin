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

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ko.googlevoice.Constants;
import com.ko.googlevoice.bundle.BundleScrubber;
import com.ko.googlevoice.bundle.PluginBundleManager;
import com.ko.googlevoice.ui.EditActivity;
import com.ko.googlevoice.receiver.GoogleVoiceSendSMSTask;

/**
 * This is the "fire" BroadcastReceiver for a Locale Plug-in setting.
 */
public final class FireReceiver extends BroadcastReceiver
{

    /**
     * @param context {@inheritDoc}.
     * @param intent the incoming {@link com.twofortyfouram.locale.Intent#ACTION_FIRE_SETTING} Intent. This should contain the
     *            {@link com.twofortyfouram.locale.Intent#EXTRA_BUNDLE} that was saved by {@link EditActivity} and later broadcast
     *            by Locale.
     */
    @Override
    public void onReceive(final Context context, final Intent intent)
    {
        /*
         * Always be sure to be strict on input parameters! A malicious third-party app could always send an empty or otherwise
         * malformed Intent. And since Locale applies settings in the background, the plug-in definitely shouldn't crash in the
         * background.
         */

        /*
         * Locale guarantees that the Intent action will be ACTION_FIRE_SETTING
         */
        if (!com.twofortyfouram.locale.Intent.ACTION_FIRE_SETTING.equals(intent.getAction()))
        {
            if (Constants.IS_LOGGABLE)
            {
                Log.e(Constants.LOG_TAG, String.format("Received unexpected Intent action %s", intent.getAction())); //$NON-NLS-1$
            }
            return;
        }

        /*
         * A hack to prevent a private serializable classloader attack
         */
        BundleScrubber.scrub(intent);
        BundleScrubber.scrub(intent.getBundleExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE));

        final Bundle bundle = intent.getBundleExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE);

        /*
         * Final verification of the plug-in Bundle before firing the setting.
         */
        if (PluginBundleManager.isBundleValid(bundle))
        {
            String gvuser = bundle.getString(PluginBundleManager.BUNDLE_EXTRA_STRING_USER);
            String gvpass = bundle.getString(PluginBundleManager.BUNDLE_EXTRA_STRING_PASS);
            String phone = bundle.getString(PluginBundleManager.BUNDLE_EXTRA_STRING_PHONE);
            String message = bundle.getString(PluginBundleManager.BUNDLE_EXTRA_STRING_MESSAGE);
            new GoogleVoiceSendSMSTask().execute(gvuser, gvpass, phone, message);
        }
    }
    
    public void getAccountList(Context context) {
    	String name = "";
		Account account = new Account(name , "com.google");
    	Activity activity = null;
		AccountManagerFuture<Bundle> accFut = AccountManager.get(context).getAuthToken(account, "ah", null, activity, null, null);
    	Bundle authTokenBundle;
		try {
			authTokenBundle = accFut.getResult();
			Toast.makeText(context, "Asking", Toast.LENGTH_LONG).show();
			String authToken = authTokenBundle.get(AccountManager.KEY_AUTHTOKEN).toString();
			Toast.makeText(context, "Success: " + authToken, Toast.LENGTH_LONG).show();
		} catch (OperationCanceledException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "OperationCancelledException", Toast.LENGTH_LONG).show();
		} catch (AuthenticatorException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "AuthenticatorException", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "IOException", Toast.LENGTH_LONG).show();
		}
    	return;
    }
}