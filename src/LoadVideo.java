
public class LoadVideo implements Runnable {

	private double videoDuration;
	private double loaded; //the loaded units
	private double loadingSpeed;
	
	private long lastLoopTime;
	
	/**
	   * @param videoDuration The duration of the video
	   * @param loadingSpeed The buffering speed
	 */
	public LoadVideo(double videoDuration, double loadingSpeed) {
		this.videoDuration = videoDuration;
		this.loadingSpeed = loadingSpeed;
		loaded = 0;
	}
	
	public void run() {
		lastLoopTime = System.currentTimeMillis();
		while(loaded != videoDuration) {
			if(System.currentTimeMillis()-lastLoopTime < PlayVideo.OneUnitTimeInMillis) //we need that to make the console slow
				continue;
			
			loaded += loadingSpeed; //it's loading			
			if(loaded > videoDuration)
				loaded = videoDuration;
			
			lastLoopTime = System.currentTimeMillis();
		}
	}
	
	public double getLoaded() {
		return this.loaded;
	}

}
