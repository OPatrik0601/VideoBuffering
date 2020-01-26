/**
* <h1>Simple buffering simulation</h1>
*/


public class Main {
	public static void main(String[] args) {
		PlayVideo videoPlayer = new PlayVideo(10, 0.5, 3); //example video player with 10 unit duration, 0.5 unit loading speed and 3 unit minimum buffer load
		videoPlayer.Play();
	}

}
