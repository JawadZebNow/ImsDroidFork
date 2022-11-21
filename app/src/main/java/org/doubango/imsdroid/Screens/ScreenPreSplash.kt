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
package org.doubango.imsdroid.Screens

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.ActivityResultLauncher
import android.content.Intent
import androidx.activity.result.ActivityResultCallback
import android.app.Activity
import android.os.Build
import android.os.Environment
import org.doubango.imsdroid.Screens.ScreenPreSplash
import android.os.Bundle
import android.content.pm.ActivityInfo
import org.doubango.imsdroid.R
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import org.doubango.imsdroid.Main
import java.lang.Exception
import java.util.ArrayList

class ScreenPreSplash : AppCompatActivity() {
    private var storageActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                //Android is 11(R) or above
                if (Environment.isExternalStorageManager()) {
                    //Manage External Storage Permission is granted
                    Log.d(
                        TAG,
                        "storageActivityResultLauncher: Manage External Storage Permission is granted"
                    )
                    val allAllowed = permissionsArray
                    if (allAllowed.isEmpty()) {
                        startActivity(Intent(this, Main::class.java))
                        finish()
                    }

                } else {
                    //Manage External Storage Permission is denied....
                    Log.d(
                        TAG,
                        "storageActivityResultLauncher: Manage External Storage Permission is denied...."
                    )
                }
            } else {
                //Android is below 11(R)
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    2
                );
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        val allAllowed = permissionsArray
        if (allAllowed.isEmpty()) {
            startActivity(Intent(this, Main::class.java))
            finish()
        }
        setContentView(R.layout.screen_presplash)
        findViewById<View>(R.id.allowPermissions).setOnClickListener { v: View? ->
            ActivityCompat.requestPermissions(
                this,
                allAllowed,
                1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val allAllowed = permissionsArray
        if (permissions.size == 1 && permissions[0] == Manifest.permission.WRITE_EXTERNAL_STORAGE && grantResults.size == 1 && grantResults[0] == -1) {
//Android R Write Storage Permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                //Android is 11(R) or above
                var intent: Intent
                try {
                    Log.d(TAG, "requestPermission: try")
                    intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                    val uri = Uri.fromParts("package", this.packageName, null)
                    intent.data = uri
                    storageActivityResultLauncher.launch(intent)
                } catch (e: Exception) {
                    Log.e(TAG, "requestPermission: ", e)
                    intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                    storageActivityResultLauncher.launch(intent)
                }
            } else {
                //Android is below 11(R)
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    2
                );
            }
        }
        if (allAllowed.isEmpty()) {
            startActivity(Intent(this, Main::class.java))
            finish()
        }
    }

    private val permissionsArray: Array<String?>
        get() {
            val list: MutableList<String?> = ArrayList()
            val networkState = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_NETWORK_STATE
            ) == PackageManager.PERMISSION_GRANTED
            if (!networkState) {
                list.add(Manifest.permission.ACCESS_NETWORK_STATE)
            }
            val changeNetworkState = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CHANGE_NETWORK_STATE
            ) == PackageManager.PERMISSION_GRANTED
            if (!changeNetworkState) {
                list.add(Manifest.permission.CHANGE_NETWORK_STATE)
            }
//            val externalStorage = ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ) == PackageManager.PERMISSION_GRANTED
//            if (!externalStorage) {
//                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            }
            val camera = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
            if (!camera) {
                list.add(Manifest.permission.CAMERA)
            }
            val audio = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
            if (!audio) {
                list.add(Manifest.permission.RECORD_AUDIO)
            }
            val reboot = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_BOOT_COMPLETED
            ) == PackageManager.PERMISSION_GRANTED
            if (!reboot) {
                list.add(Manifest.permission.REBOOT)
            }
            val keyguard = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.DISABLE_KEYGUARD
            ) == PackageManager.PERMISSION_GRANTED
            if (!keyguard) {
                list.add(Manifest.permission.DISABLE_KEYGUARD)
            }
            val rContacts = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
            if (!rContacts) {
                list.add(Manifest.permission.READ_CONTACTS)
            }
            val wContacts = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
            if (!wContacts) {
                list.add(Manifest.permission.WRITE_CONTACTS)
            }
            val phoneState = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
            if (!phoneState) {
                list.add(Manifest.permission.READ_PHONE_STATE)
            }
            val audioSettings = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.MODIFY_AUDIO_SETTINGS
            ) == PackageManager.PERMISSION_GRANTED
            if (!audioSettings) {
                list.add(Manifest.permission.MODIFY_AUDIO_SETTINGS)
            }
            val batteryOptimization = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!batteryOptimization) {
                list.add(Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            }
            val stringArray = list.toTypedArray()
            return if (stringArray.isEmpty()) {
                arrayOfNulls(0)
            } else {
                stringArray
            }
        }

    companion object {
        private val TAG = ScreenPreSplash::class.java.canonicalName
    }
}