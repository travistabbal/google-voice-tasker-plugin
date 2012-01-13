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

/**
 * Class of constants used by this Locale plug-in.
 */
public final class Constants
{
    /**
     * Private constructor prevents instantiation
     * 
     * @throws UnsupportedOperationException because this class cannot be instantiated.
     */
    private Constants()
    {
        throw new UnsupportedOperationException("This class is non-instantiable"); //$NON-NLS-1$
    }

    /**
     * Log tag for logcat messages
     */
    // TODO: Change this to your application's own log tag
    public static final String LOG_TAG = "GoogleVoiceTaskerPlugin"; //$NON-NLS-1$

    /**
     * Flag to enable logcat messages.
     */
    public static final boolean IS_LOGGABLE = false;

    /**
     * Flag to enable runtime checking of method parameters.
     */
    public static final boolean ENABLE_PARAMETER_CHECKING = false;

    /**
     * Flag to enable runtime checking of whether a method is called on the correct thread.
     */
    public static final boolean ENABLE_CORRECT_THREAD_CHECKING = false;
}