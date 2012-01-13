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

package com.ko.googlevoice.bundle;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.ko.googlevoice.Constants;

/**
 * Class for managing the {@link com.twofortyfouram.locale.Intent#EXTRA_BUNDLE} for this plug-in.
 */
public final class PluginBundleManager
{
    /**
     * Private constructor prevents instantiation
     * 
     * @throws UnsupportedOperationException because this class cannot be instantiated.
     */
    private PluginBundleManager()
    {
        throw new UnsupportedOperationException("This class is non-instantiable"); //$NON-NLS-1$
    }

    /**
     * Type: {@code String}
     * <p>
     * String message to display in a Toast message.
     */
    public static final String BUNDLE_EXTRA_STRING_MESSAGE = "com.ko.googlevoice.extra.STRING_MESSAGE"; //$NON-NLS-1$
    public static final String BUNDLE_EXTRA_STRING_PHONE = "com.ko.googlevoice.extra.STRING_PHONE"; //$NON-NLS-1$
    public static final String BUNDLE_EXTRA_STRING_USER = "com.ko.googlevoice.extra.STRING_USER"; //$NON-NLS-1$
    public static final String BUNDLE_EXTRA_STRING_PASS = "com.ko.googlevoice.extra.STRING_PASS"; //$NON-NLS-1$

    /**
     * Method to verify the content of the bundle are correct.
     * <p>
     * This method will not mutate {@code bundle}.
     * 
     * @param bundle bundle to verify. May be null, which will always return false.
     * @return true if the Bundle is valid, false if the bundle is invalid.
     */
    public static boolean isBundleValid(final Bundle bundle)
    {
    	Boolean retEarly = new Boolean(false);
    	
        if (null == bundle)
        {
            return false;
        }

        /*
         * Make sure the expected extras exist
         */
        if (!bundle.containsKey(BUNDLE_EXTRA_STRING_MESSAGE))
        {
            if (Constants.IS_LOGGABLE)
            {
                Log.e(Constants.LOG_TAG, String.format("bundle must contain extra %s", BUNDLE_EXTRA_STRING_MESSAGE)); //$NON-NLS-1$
            }
            retEarly = true;
        }
        if (!bundle.containsKey(BUNDLE_EXTRA_STRING_PHONE))
        {
            if (Constants.IS_LOGGABLE)
            {
                Log.e(Constants.LOG_TAG, String.format("bundle must contain extra %s", BUNDLE_EXTRA_STRING_PHONE)); //$NON-NLS-1$
            }
            retEarly = true;
        }
        if (!bundle.containsKey(BUNDLE_EXTRA_STRING_USER))
        {
            if (Constants.IS_LOGGABLE)
            {
                Log.e(Constants.LOG_TAG, String.format("bundle must contain extra %s", BUNDLE_EXTRA_STRING_USER)); //$NON-NLS-1$
            }
            retEarly = true;
        }
        if (!bundle.containsKey(BUNDLE_EXTRA_STRING_PASS))
        {
            if (Constants.IS_LOGGABLE)
            {
                Log.e(Constants.LOG_TAG, String.format("bundle must contain extra %s", BUNDLE_EXTRA_STRING_PASS)); //$NON-NLS-1$
            }
            retEarly = true;
        }
        
        if (retEarly) {
        	return false;
        }

        /*
         * Make sure the correct number of extras exist. Run this test after checking for specific Bundle extras above so that the
         * error message is more useful. (E.g. the caller will see what extras are missing, rather than just a message that there
         * is the wrong number).
         */
        if (bundle.keySet().size() != 4)
        {
            if (Constants.IS_LOGGABLE)
            {
                Log.e(Constants.LOG_TAG, String.format("bundle must contain 4 keys, but currently contains %d key(s): %s", Integer.valueOf(bundle.keySet().size()), bundle.keySet() //$NON-NLS-1$
                                                                                                                                                                       .toString()));
            }
            return false;
        }

        /*
         * Make sure the extra isn't null or empty
         */
        if (TextUtils.isEmpty(bundle.getString(BUNDLE_EXTRA_STRING_MESSAGE)))
        {
            if (Constants.IS_LOGGABLE)
            {
                Log.e(Constants.LOG_TAG, String.format("bundle extra %s appears to be null or empty.  It must be a non-empty string", BUNDLE_EXTRA_STRING_MESSAGE)); //$NON-NLS-1$
            }
            retEarly = true;
        }
        if (TextUtils.isEmpty(bundle.getString(BUNDLE_EXTRA_STRING_PHONE)))
        {
            if (Constants.IS_LOGGABLE)
            {
                Log.e(Constants.LOG_TAG, String.format("bundle extra %s appears to be null or empty.  It must be a non-empty string", BUNDLE_EXTRA_STRING_PHONE)); //$NON-NLS-1$
            }
            retEarly = true;
        }
        if (TextUtils.isEmpty(bundle.getString(BUNDLE_EXTRA_STRING_USER)))
        {
            if (Constants.IS_LOGGABLE)
            {
                Log.e(Constants.LOG_TAG, String.format("bundle extra %s appears to be null or empty.  It must be a non-empty string", BUNDLE_EXTRA_STRING_USER)); //$NON-NLS-1$
            }
            retEarly = true;
        }
        if (TextUtils.isEmpty(bundle.getString(BUNDLE_EXTRA_STRING_PASS)))
        {
            if (Constants.IS_LOGGABLE)
            {
                Log.e(Constants.LOG_TAG, String.format("bundle extra %s appears to be null or empty.  It must be a non-empty string", BUNDLE_EXTRA_STRING_PASS)); //$NON-NLS-1$
            }
            retEarly = true;
        }
        if (retEarly) {
        	return false;
        }

        return true;
    }
}