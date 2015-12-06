package com.polites.android;


/**
 * @author Jason Polites
 *
 */
public interface ZoomAnimationListener {
	public void onZoom(float scale, float x, float y);
	public void onComplete();
}
