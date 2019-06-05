/**<ul>
 * <li>AsyncTaskTuto</li>
 * <li>com.android2ee.tuto.thread.asynctask.one</li>
 * <li>13 janv. 2012</li>
 * 
 * <li>======================================================</li>
 *
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 *
 /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br> 
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 *  Belongs to <strong>Mathias Seguy</strong></br>
 ****************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * 
 * *****************************************************************************************************************</br>
 *  Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 *  Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br> 
 *  Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 *  <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */
package com.android2ee.tuto.thread.asynctask.one;

import java.util.concurrent.atomic.AtomicBoolean;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to show how to link the life cycle of your activity with the one of your
 *        AsyncTask.
 *        <ul>
 *        <li></li>
 *        </ul>
 */
public class MyAsyncTask extends AsyncTask<Void, Integer, String> {
	/******************************************************************************************/
	/** Attributes **************************************************************************/
	/******************************************************************************************/

	/** * A simple counter */
	private Integer count = 0;

	/**
	 * The calling activity
	 */
	private AsyncTaskTutoActivity activity;
	/**
	 * 
	 */
	private String name;

	/**
	 * The logCat's tag
	 */
	private String tag = "MyAsyncTask";
	/******************************************************************************************/
	/** Managing the AsyncTask's life cycle **************************************************************************/
	/******************************************************************************************/

	/**
	 * The atomic boolean to set the thread run
	 */
	AtomicBoolean isThreadRunnning = new AtomicBoolean();
	/**
	 * The atomic boolean to set the thread pause
	 */
	AtomicBoolean isThreadPausing = new AtomicBoolean();

	/******************************************************************************************/
	/** Constructors **************************************************************************/
	/******************************************************************************************/

	/**
	 * @param activity
	 */
	public MyAsyncTask(AsyncTaskTutoActivity activity, String name) {
		super();
		this.activity = activity;
		this.name = name;
	}

	/******************************************************************************************/
	/** Threatment method **************************************************************************/
	/******************************************************************************************/

	// override of the method doInBackground (the one which is running in a separate thread)
	@Override
	protected String doInBackground(Void... unused) {
		try {
			// Manage your thread's life cycle using the AtomicBooleans
			while (count < 200) {
				if (isThreadRunnning.get()) {
					if (isThreadPausing.get()) {
						// Make a pause or something like that
						Thread.sleep(1000);
					} else {
						Log.e(tag, "doInBackground of the AsyncTask " + name);
						// increment the counter
						count++;
						// Make a pause
						Thread.sleep(1000);
						// talk to the onProgressUpdate method
						publishProgress(count,100,120,12);
					}
				} else {
					//just quit the while loop
					break;
				}
			}
		} catch (InterruptedException t) {
			// just end the background thread
			return ("The sleep operation failed");
		}
		return ("return object when task is finished");
	}

	// override of the onProgressUpdate method (runs in the GUI thread)
	@Override
	protected void onProgressUpdate(Integer... diff) {
		Log.e(tag, "onProgressUpdate of the AsyncTask " + name + " (" + count + ")");
		activity.updateProgress();
	}

	// override of the onPostExecute method (runs in the GUI thread)
	@Override
	protected void onPostExecute(String message) {
		Log.e(tag, "onPostExecute of the AsyncTask " + name + " (" + count + ")");
		Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
}