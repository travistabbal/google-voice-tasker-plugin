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


package com.ko.googlevoice.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.yourcompany.yoursetting.R;
import com.ko.googlevoice.bundle.BundleScrubber;
import com.ko.googlevoice.bundle.PluginBundleManager;
import com.twofortyfouram.locale.BreadCrumber;

/**
 * This is the "Edit" activity for a Locale Plug-in.
 */
public final class EditActivity extends Activity
{

    /**
     * Help URL, used for the {@link com.twofortyfouram.locale.platform.R.id#twofortyfouram_locale_menu_help} menu item.
     */
    // TODO: Place a real help URL here
    private static final String HELP_URL = "https://github.com/ko/google-voice-tasker-plugin/wiki"; //$NON-NLS-1$

    /**
     * Flag boolean that can only be set to true via the "Don't Save"
     * {@link com.twofortyfouram.locale.platform.R.id#twofortyfouram_locale_menu_dontsave} menu item in
     * {@link #onMenuItemSelected(int, MenuItem)}.
     * <p>
     * If true, then this {@code Activity} should return {@link Activity#RESULT_CANCELED} in {@link #finish()}.
     * <p>
     * If false, then this {@code Activity} should generally return {@link Activity#RESULT_OK} with extras
     * {@link com.twofortyfouram.locale.Intent#EXTRA_BUNDLE} and {@link com.twofortyfouram.locale.Intent#EXTRA_STRING_BLURB}.
     * <p>
     * There is no need to save/restore this field's state when the {@code Activity} is paused.
     */
    private boolean mIsCancelled = false;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /*
         * A hack to prevent a private serializable classloader attack
         */
        BundleScrubber.scrub(getIntent());
        BundleScrubber.scrub(getIntent().getBundleExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE));

        setContentView(R.layout.main);

        setTitle(BreadCrumber.generateBreadcrumb(getApplicationContext(), getIntent(), getString(R.string.plugin_name)));

        final FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
        frame.addView(getLayoutInflater().cloneInContext(new ContextThemeWrapper(this, R.style.Theme_Locale_Light)).inflate(R.layout.frame, frame, false));

        /*
         * if savedInstanceState is null, then then this is a new Activity instance and a check for EXTRA_BUNDLE is needed
         */
        if (null == savedInstanceState)
        {
            final Bundle forwardedBundle = getIntent().getBundleExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE);

            if (PluginBundleManager.isBundleValid(forwardedBundle))
            {
                ((EditText) findViewById(R.id.messagebody)).setText(forwardedBundle.getString(PluginBundleManager.BUNDLE_EXTRA_STRING_MESSAGE));
                ((EditText) findViewById(R.id.phone)).setText(forwardedBundle.getString(PluginBundleManager.BUNDLE_EXTRA_STRING_PHONE));
                ((EditText) findViewById(R.id.gvuser)).setText(forwardedBundle.getString(PluginBundleManager.BUNDLE_EXTRA_STRING_USER));
                ((EditText) findViewById(R.id.gvpass)).setText(forwardedBundle.getString(PluginBundleManager.BUNDLE_EXTRA_STRING_PASS));
            }
        }
        /*
         * if savedInstanceState != null, there is no need to restore any Activity state directly via onSaveInstanceState()), as
         * the TextView object handles that automatically
         */
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void finish()
    {
        if (mIsCancelled)
        {
            setResult(RESULT_CANCELED);
        }
        else
        {
            final String message = ((EditText) findViewById(R.id.messagebody)).getText().toString();
            final String gvuser = ((EditText) findViewById(R.id.gvuser)).getText().toString();
            final String gvpass = ((EditText) findViewById(R.id.gvpass)).getText().toString();
            final String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
            
            /*
             * If the message is of 0 length, then there isn't a setting to save.
             */
            if (0 == message.length())
            {
                setResult(RESULT_CANCELED);
            }
            else
            {
                /*
                 * This is the return Intent, into which we'll put all the required extras
                 */
                final Intent returnIntent = new Intent();

                /*
                 * This extra is the data to ourselves: either for the Activity or the BroadcastReceiver. Note that anything
                 * placed in this Bundle must be available to Locale's class loader. So storing String, int, and other standard
                 * objects will work just fine. However Parcelable objects must also be Serializable. And Serializable objects
                 * must be standard Java objects (e.g. a private subclass to this plug-in cannot be stored in the Bundle, as
                 * Locale's classloader will not recognize it).
                 */
                final Bundle returnBundle = new Bundle();
                returnBundle.putString(PluginBundleManager.BUNDLE_EXTRA_STRING_MESSAGE, message);
                returnBundle.putString(PluginBundleManager.BUNDLE_EXTRA_STRING_PHONE, phone);
                returnBundle.putString(PluginBundleManager.BUNDLE_EXTRA_STRING_USER, gvuser);
                returnBundle.putString(PluginBundleManager.BUNDLE_EXTRA_STRING_PASS, gvpass);

                returnIntent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE, returnBundle);

                /*
                 * This is the blurb concisely describing what your setting's state is. This is simply used for display in the UI.
                 */
                if (message.length() > getResources().getInteger(R.integer.twofortyfouram_locale_maximum_blurb_length))
                {
                    returnIntent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_STRING_BLURB, message.substring(0, getResources().getInteger(R.integer.twofortyfouram_locale_maximum_blurb_length)));
                }
                else
                {
                    returnIntent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_STRING_BLURB, message);
                }

                setResult(RESULT_OK, returnIntent);
            }
        }

        super.finish();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        /*
         * inflate the default menu layout from XML
         */
        getMenuInflater().inflate(R.menu.twofortyfouram_locale_help_save_dontsave, menu);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onMenuItemSelected(final int featureId, final MenuItem item)
    {

        /*
         * Royal pain in the butt to support the home button in SDK 11's ActionBar
         */
        if (Build.VERSION.SDK_INT >= 11)
        {
            try
            {
                if (item.getItemId() == android.R.id.class.getField("home").getInt(null)) //$NON-NLS-1$
                {
                    // app icon in Action Bar clicked; go home
                    final Intent intent = new Intent(getPackageManager().getLaunchIntentForPackage(getCallingPackage()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }
            }
            catch (final NoSuchFieldException e)
            {
                // this should never happen on SDK 11 or greater
                throw new RuntimeException(e);
            }
            catch (final IllegalAccessException e)
            {
                // this should never happen on SDK 11 or greater
                throw new RuntimeException(e);
            }
        }

        switch (item.getItemId())
        {
            case R.id.twofortyfouram_locale_menu_help:
            {
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(HELP_URL)));
                }
                catch (final Exception e)
                {
                    Toast.makeText(getApplicationContext(), com.twofortyfouram.locale.platform.R.string.twofortyfouram_locale_application_not_available, Toast.LENGTH_LONG).show();
                }

                return true;
            }
            case R.id.twofortyfouram_locale_menu_dontsave:
            {
                mIsCancelled = true;
                finish();
                return true;
            }
            case R.id.twofortyfouram_locale_menu_save:
            {
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}