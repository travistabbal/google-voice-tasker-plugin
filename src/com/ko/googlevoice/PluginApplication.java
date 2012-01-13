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

package com.ko.googlevoice;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Implements an application object for the plug-in.
 * <p>
 * This application is non-essential for the plug-in's operation; it simply enables debugging options globally for the app.
 */
public final class PluginApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        if ((getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0)
        {
            if (Constants.IS_LOGGABLE)
            {
                Log.v(Constants.LOG_TAG, "Application is debuggable.  Enabling additional debug logging"); //$NON-NLS-1$
            }

            /*
             * If using the Fragment compatibility library, enable debug logging here
             */
            // FragmentManager.enableDebugLogging(true);
            // LoaderManager.enableDebugLogging(true);

            if (Build.VERSION.SDK_INT >= 9)
            {
                try
                {
                    final Class<?> strictModeClass = Class.forName("android.os.StrictMode"); //$NON-NLS-1$
                    final Method enableDefaultsMethod = strictModeClass.getMethod("enableDefaults"); //$NON-NLS-1$
                    enableDefaultsMethod.invoke(strictModeClass);
                }
                catch (final ClassNotFoundException e)
                {
                    throw new RuntimeException(e);
                }
                catch (final SecurityException e)
                {
                    throw new RuntimeException(e);
                }
                catch (final NoSuchMethodException e)
                {
                    throw new RuntimeException(e);
                }
                catch (final IllegalArgumentException e)
                {
                    throw new RuntimeException(e);
                }
                catch (final IllegalAccessException e)
                {
                    throw new RuntimeException(e);
                }
                catch (final InvocationTargetException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}