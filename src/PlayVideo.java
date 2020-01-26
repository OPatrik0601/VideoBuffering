
public class PlayVideo {
	
	public static final float OneUnitTimeInMillis = 1000; //one loop every second

	private double videoDuration;
	private double currentVideoPosition;
	
	private LoadVideo loadVideo;
	private Thread loadVideoThread;
	
	private double minimumBufferLoad;
	private boolean currentlyBuffering;
	
	private long lastLoopTime;
	/**
	   * @param videoDuration The duration of the video
	   * @param loadingSpeed The buffering speed
	   * @param minimumBufferLoad The minimum number of units to buffer when loading occurs
	 */
	public PlayVideo(double videoDuration, double loadingSpeed, double minimumBufferLoad) {
		
		//check the values
		if(videoDuration < 0)
			videoDuration = 0;
		
		if(loadingSpeed <= 0)
			loadingSpeed = 1;
		
		
		this.videoDuration = videoDuration;
		this.currentVideoPosition = 0;
		this.minimumBufferLoad = minimumBufferLoad;
		this.currentlyBuffering = true; //load at start
		
		loadVideo = new LoadVideo(videoDuration, loadingSpeed);
		loadVideoThread = new Thread(loadVideo);
	}
	
	/**
	   * This method is used to play the video.
	 */
	public void Play() {
		loadVideoThread.start(); //start the buffering thread	
		while(currentVideoPosition < videoDuration) { //start the loop, ends when the video has ended
			if(System.currentTimeMillis()-lastLoopTime < PlayVideo.OneUnitTimeInMillis) //we need that to make the console slow
				continue;
			
			if(loadVideo.getLoaded() < currentVideoPosition) //start the loading if the video position is more than the loaded units
				currentlyBuffering = true;
			
			if(currentlyBuffering) { //if it's currently buffering
				System.out.printf("Loading... [loaded: %.2f]\n", loadVideo.getLoaded());
				
				//if we loaded the minimum buffer amount or the full video, than the loading is done for now
				if(loadVideo.getLoaded() >= currentVideoPosition + minimumBufferLoad || loadVideo.getLoaded() >= videoDuration)
					currentlyBuffering = false;				
			} else {
				currentVideoPosition++; //the video is "playing"
				System.out.printf(" + Playing (%.2f out of %.2f [loaded: %.2f])\n", currentVideoPosition, videoDuration, loadVideo.getLoaded());
			}
			
			lastLoopTime = System.currentTimeMillis();
		}
	}
}
