/* Copyright (C) 2010-2011, Mamadou Diop.
*  Copyright (C) 2011, Doubango Telecom.
*
* Contact: Mamadou Diop <diopmamadou(at)doubango(dot)org>
*	
* This file is part of imsdroid Project (http://code.google.com/p/imsdroid)
*
* imsdroid is free software: you can redistribute it and/or modify it under the terms of 
* the GNU General Public License as published by the Free Software Foundation, either version 3 
* of the License, or (at your option) any later version.
*	
* imsdroid is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
* See the GNU General Public License for more details.
*	
* You should have received a copy of the GNU General Public License along 
* with this program; if not, write to the Free Software Foundation, Inc., 
* 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/
package org.doubango.utils;

import org.doubango.ngn.NgnApplication;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class NgnGraphicsUtils {
	
	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		final int w = bm.getWidth();
		final int h = bm.getHeight();
		final float sw = ((float) newWidth) / w;
		final float sh = ((float) newHeight) / h;

		Matrix matrix = new Matrix();
		matrix.postScale(sw, sh);
		return Bitmap.createBitmap(bm, 0, 0, w, h, matrix, false);
	}
	
	public static int getSizeInPixel(int dp){
		final float scale = NgnApplication.getContext().getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
}
